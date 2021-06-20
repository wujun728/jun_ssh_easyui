package org.coderfun.boot.core.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.coderfun.boot.core.BootConst;
import org.coderfun.boot.core.BootConst.KickoutMode;
import org.coderfun.boot.core.BootDict;
import org.coderfun.boot.core.dao.UserDAO;
import org.coderfun.boot.core.entity.Permission;
import org.coderfun.boot.core.entity.Permission_;
import org.coderfun.boot.core.entity.User;
import org.coderfun.boot.core.entity.User_;
import org.coderfun.boot.core.exception.BootErrorCode;
import org.coderfun.boot.core.shiro.SessionUser;
import org.coderfun.boot.core.shiro.SimplePasswordMatcher;
import org.coderfun.common.exception.AppException;
import org.crazycake.shiro.RedisSessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klg.common.dataaccess.BaseServiceImpl;
import klg.query.jpa.expr.AExpr;

/**
 *
 * 
 * Generated by fieldmeta at 2018-09-20T21:15:17+08:00
 *
 */

@Service
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	UserDAO userDAO;
	
	@Autowired
	PermissionService permissionService;
	

	private SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();

	@Autowired
	SimplePasswordMatcher passwordMatcher;

	private void encryptPassword(User user) {
		logger.debug("重新生成盐，并对密码进行加密");
		user.setSalt(randomNumberGenerator.nextBytes().toHex());
		String newPassword = encryptPassword(user.getPlainPassword(), user.getSalt());
		user.setPassword(newPassword);
	}

	private String encryptPassword(String plainPassword,String salt){
		return passwordMatcher.hashPassword(plainPassword, salt).toHex();
	}
	
	@Transactional
	@Override
	public User create(User user) {
		// TODO Auto-generated method stub
		if(checkLoginNameExist(user.getLoginName())){
			throw new AppException(BootErrorCode.USER_LOGIN_NAME_EXITS);
		}
		
		user.setCreateDate(new Date());
		user.setState(BootDict.User.State.NORMAL);
		encryptPassword(user);
		return userDAO.save(user);
	}
	
	@Transactional
	@Override
	public void updatePassword(Long userId, String plainPassword) {
		// TODO Auto-generated method stub
		User user = getById(userId);
		user.setPlainPassword(plainPassword);
		encryptPassword(user);
		userDAO.update(user);
		//全部踢出
		kickout(userId, KickoutMode.ALL);
	}

	@Override
	public boolean checkPassword(Long userId, String plainPassword) {
		// TODO Auto-generated method stub
		
		User user = getById(userId);
		String password = encryptPassword(plainPassword, user.getSalt());	
		return user.getPassword().equals(password);
	}
	
	@Transactional
	@Override
	public void changePassword(Long userId, String oldPlainPassword, String newPlainPassword) {
		// TODO Auto-generated method stub
		if(checkPassword(userId, oldPlainPassword)){
			updatePassword(userId, newPlainPassword);
		}else{
			throw new AppException(BootErrorCode.OLD_PASSWORD_WRONG);
		}
	}
	
	@Override
	public boolean checkLoginNameExist(String loginName) {
		// TODO Auto-generated method stub
		User user = userDAO.getOne(AExpr.eq(User_.loginName, loginName));
		return user != null;
	}

	@Override
	public User getByLoginName(String loginName) {
		// TODO Auto-generated method stub
				
		return userDAO.getOne(AExpr.eq(User_.loginName, loginName));
	}

	@Override
	public List<Permission> queryMenus(Long userId) {
		// TODO Auto-generated method stub
		if(userId.equals(BootConst.SUPER_ADMIN_ID)){
			return permissionService.findList(AExpr.eq(Permission_.type, BootDict.Permission.Type.MENU));
		}else{
			List<Long> menuIds = userDAO.queryMenuIds(userId);
			return permissionService.findAll(menuIds);
		}
	}

	@Override
	public Set<String> queryPermissions(Long userId) {
		// TODO Auto-generated method stub
		List<String> perms = null;
	
		if(userId.equals(BootConst.SUPER_ADMIN_ID)){
			List<Permission> permissions = permissionService.findAll();
			perms = new ArrayList<>();
			for(Permission permission:permissions){
				perms.add(permission.getPermCode());
			}
		}else{
			perms=userDAO.queryPermissions(userId);
		}
		
		Set<String> permSet = new HashSet<>();
		for(String perm:perms){
            if(StringUtils.isBlank(perm)){
                continue;
            }
			permSet.addAll(Arrays.asList(perm.trim().split(",")));
		}
		
		return permSet;
	}

	@Override
	public List<String> queryMenuUrls(Long userId) {
		// TODO Auto-generated method stub
		
		if(userId.equals(BootConst.SUPER_ADMIN_ID)){
			List<Permission> menus = permissionService.findList(AExpr.eq(Permission_.type, BootDict.Permission.Type.MENU));
			List<String> menuUrls = new ArrayList<>();
			for(Permission menu : menus){
	            if(StringUtils.isBlank(menu.getUrl())){
	                continue;
	            }
				menuUrls.add(menu.getUrl());
			}
			return menuUrls;
		}else{
			return userDAO.queryMenuUrls(userId);			
		}
	}

	
	
	
	
	
    @Autowired
    RedisSessionDAO redisSessionDAO;
    
	@Override
	public void kickout(Long userId,KickoutMode kickoutMode) {
		// TODO Auto-generated method stub

		List<Session> sessions = getByUserId(userId);
		
		if(kickoutMode.equals(KickoutMode.ALL)){
			logger.info("userId:{},删除全部session",userId);
			kickoutAll(sessions);	 
		}
		if(kickoutMode.equals(KickoutMode.BEFORE)){
			logger.info("userId:{},删除之前登录的session",userId);
			kickoutBefore(sessions);
		}
	}
	
	private void kickoutAll(List<Session> sessions){
		for(Session session:sessions){
			logger.debug("删除session：{}",session.getId());
			redisSessionDAO.delete(session);							
		}
	}
	
	private void kickoutBefore(List<Session> sessions){
		long maxStartTime = 0L;
		for(Session session:sessions){
			if(session.getStartTimestamp().getTime() > maxStartTime){
				maxStartTime = session.getStartTimestamp().getTime();
			}
		}
		
		for(Session session:sessions){
			if(maxStartTime != session.getStartTimestamp().getTime()){
				logger.debug("删除session：{}",session.getId());
				redisSessionDAO.delete(session);
			}
		}
	}
	
	private List<Session> getByUserId(Long userId){
		List<Session> sessions= new ArrayList<>();
		for(Session session:redisSessionDAO.getActiveSessions()){
			
			PrincipalCollection principalCollection = (PrincipalCollection) session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
			if(null == principalCollection){
				continue;
			}
			SessionUser sessionUser = (SessionUser) principalCollection.getPrimaryPrincipal();
			
			if(null == sessionUser || null == sessionUser.getId()){
				continue;
			}
			if(userId.equals(sessionUser.getId())){
				sessions.add(session);
			}
		}
		return sessions;
	}
	
}
