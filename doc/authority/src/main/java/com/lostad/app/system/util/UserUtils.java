/**
 * Copyright &copy; 2015-2020 <a href="http://www.lostad.org/">JeePlus</a> All rights reserved.
 */
package com.lostad.app.system.util;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.lostad.app.common.util.SMSUtils;
import com.lostad.app.common.util.SpringUtil;
import com.lostad.app.system.dao.AreaDao;
import com.lostad.app.system.dao.DictDao;
import com.lostad.app.system.dao.OfficeDao;
import com.lostad.app.system.dao.RoleDao;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.Area;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Office;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.ResourceService;

/**
 * 用户工具类
 * @author lostad
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringUtil.getBean(UserDao.class);
	private static RoleDao roleDao = SpringUtil.getBean(RoleDao.class);
	private static ResourceService resService = SpringUtil.getBean(ResourceService.class);
	private static AreaDao areaDao = SpringUtil.getBean(AreaDao.class);
	private static OfficeDao officeDao = SpringUtil.getBean(OfficeDao.class);
	
	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";
	
	/**
	 * 根据ID获取用户
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		return userDao.getOne(id);
	}
	
//	/**
//	 * 根据登录名获取用户
//	 * @param loginName
//	 * @return 取不到返回null
//	 */
//	public static User getByLoginName(String loginName){
//		User user = (User)CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
////		if (user == null){
////			user = userDao.getByLoginName(new User(null, loginName));
////			if (user == null){
////				return null;
////			}
////			user.setRoleList(roleDao.findList(new Role(user)));
////			CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
////			CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
////		}
//		return user;
//	}
	
	/**
	 * 清除当前用户缓存
	 */
	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST);
		removeCache(CACHE_MENU_LIST);
		removeCache(CACHE_AREA_LIST);
		removeCache(CACHE_OFFICE_LIST);
		removeCache(CACHE_OFFICE_ALL_LIST);
	}
	
	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Subject subject = SecurityUtils.getSubject();
		User u = (User)subject.getPrincipal();
		return u;
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		@SuppressWarnings("unchecked")
		List<Role> roleList = (List<Role>)getCache(CACHE_ROLE_LIST);
		if (roleList == null){
			User user = getUser();
//			if (user.isAdmin()){
//				roleList = roleDao.findAllList(new Role());
//			}else{
//				Role role = new Role();
//				role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
//				roleList = roleDao.findList(role);
//			}
			putCache(CACHE_ROLE_LIST, roleList);
		}
		return roleList;
	}
	
	public static User getCurrUser(){
		 Subject subject = SecurityUtils.getSubject();
		 User user = (User)subject.getPrincipal();
		 return user;
	}
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		User u = getCurrUser();
		List<Menu> menuList = resService.findMyMenus();
		return menuList;
	}
	
	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static Menu getTopMenu(){
		Menu topMenu =  resService.find("1");
		return topMenu;
	}
	/**
	 * 获取当前用户授权的区域
	 * @return
	 */
	public static List<Area> getAreaList(){
		@SuppressWarnings("unchecked")
		List<Area> areaList = (List<Area>)getCache(CACHE_AREA_LIST);
		if (areaList == null){
			areaList = areaDao.findAll();
			putCache(CACHE_AREA_LIST, areaList);
		}
		return areaList;
	}
	
	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(){
		@SuppressWarnings("unchecked")
		List<Office> officeList = officeDao.findAll();
		
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(){
		List<Office> officeList = officeDao.findAll();
		return officeList;
	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}
	
	
	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){
			
		}
		return null;
	}
	
	// ============== User Cache ==============
	
	public static Object getCache(String key) {
		return getCache(key, null);
	}
	
	public static Object getCache(String key, Object defaultValue) {
//		Object obj = getCacheMap().get(key);
		Object obj = getSession().getAttribute(key);
		return obj==null?defaultValue:obj;
	}

	public static void putCache(String key, Object value) {
//		getCacheMap().put(key, value);
		getSession().setAttribute(key, value);
	}

	public static void removeCache(String key) {
//		getCacheMap().remove(key);
		getSession().removeAttribute(key);
	}
	
	public static String getTime(Date date){
		StringBuffer time = new StringBuffer();
        Date date2 = new Date();
        long temp = date2.getTime() - date.getTime();    
        long days = temp / 1000 / 3600/24;                //相差小时数
        if(days>0){
        	time.append(days+"天");
        }
        long temp1 = temp % (1000 * 3600*24);
        long hours = temp1 / 1000 / 3600;                //相差小时数
        if(days>0 || hours>0){
        	time.append(hours+"小时");
        }
        long temp2 = temp1 % (1000 * 3600);
        long mins = temp2 / 1000 / 60;                    //相差分钟数
        time.append(mins + "分钟");
        return  time.toString();
	}


	//发送注册码
	public static String sendRandomCode(String uid, String pwd, String tel, String randomCode) throws IOException {
		//发送内容
		String content = "您的验证码是："+randomCode+"，有效期30分钟，请在有效期内使用。"; 
		
		return SMSUtils.send(uid, pwd, tel, content);

	}
	
	//注册用户重置密码
	public static String sendPass(String uid, String pwd, String tel, String password) throws IOException {
		//发送内容
		String content = "您的新密码是："+password+"，请登录系统，重新设置密码。"; 
		return SMSUtils.send(uid, pwd, tel, content);

	}
	
	/**
	 * 导出Excel调用,根据姓名转换为ID
	 */
	public static User getByUserName(String username){
		return  userDao.findByUsername(username);
	}
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Office getByOfficeName(String name){
//		Office o = new Office();
//		o.setName(name);
//		List<Office> list = officeDao.findList(o);
//		if(list.size()>0){
//			return list.get(0);
//		}else{
			return new Office();
	//	}
	}
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Area getByAreaName(String code){
		return areaDao.findByCode(code);
	}

	/**
	 * 当前用户是否为超级管理员
	 * @return
	 */
	public static boolean isCurrUserAdmin() {
		User u = getCurrUser();
		if(u.getRoleCodes().contains("Administrator")){
			return true;
		}
		return false;
	}
	
}
