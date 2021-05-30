package com.lostad.app.conf.shiro;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.AllowAllCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lostad.app.security.util.MD5Utils;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.UserService;

/**
 * 
 * @author SPPan
 *
 */
@Component
public class MyRealm extends AuthorizingRealm {

	public MyRealm(){
		super(new AllowAllCredentialsMatcher());
        setAuthenticationTokenClass(UsernamePasswordToken.class);

        //FIXME: 暂时禁用Cache
        setCachingEnabled(false);
	}
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//User dbUser = userService.findByUserName(user.getUsername());
		Set<String> shiroPermissions = userService.findMyPermitions();
		Set<String> roleSet = userService.findMyRoles();
		authorizationInfo.setRoles(roleSet);
		authorizationInfo.setStringPermissions(shiroPermissions);
		// 添加用户权限
		authorizationInfo.addStringPermission("user");
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		
		User user = userService.findByUserName(username);
		if (user == null) {// 账号不存在
			throw new UnknownAccountException("账号不存在！");
		}
		// 账号锁定
		if (user.getDelFlag() == 1) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}
		// 验证密码		
		StringBuilder sb = new StringBuilder();
		Set<Role> roles = user.getRoles();
		for(Role r:roles){
			sb.append(r.getRoleCode()).append(",");
		}
		user.setRoleCodes(sb.toString());
		String password = new String((char[]) token.getCredentials());
		if (!MD5Utils.md5Hex(password).equals(user.getPassword())) {
			throw new IncorrectCredentialsException("账号或密码不正确");
		}
        
		
		return new SimpleAuthenticationInfo(user, password, getName());
	}

}
