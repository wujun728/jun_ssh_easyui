package com.jun.plugin.boot.core.service;

import java.util.List;
import java.util.Set;

import com.jun.plugin.boot.core.BootConst.KickoutMode;
import com.jun.plugin.boot.core.entity.Permission;
import com.jun.plugin.boot.core.entity.User;
import com.jun.plugin.common.dataaccess.BaseService;



/**
 *
 * 
 * Generated by fieldmeta at 2018-09-20T21:15:17+08:00
 *
 */
 
public interface UserService extends BaseService<User, Long>{

	
	public User getByLoginName(String loginName);
	
	
	/**
	 * 创建用户调用，设置盐，然后对密码进行加密
	 * 
	 * @param user
	 * @return
	 */
	public User create(User user);
	
	/**
	 * 踢出用户，可配置集群
	 * 
	 * @param userId
	 * @param kickoutMode
	 */
	public void kickout(Long userId, KickoutMode kickoutMode);
	
	/**
	 * 
	 * 并重新设置盐，并对密码进行加密，管理员调用
	 * 
	 * @param userId
	 * @param plainPassword
	 */
	
	public void updatePassword(Long userId,String plainPassword);
	
	public boolean checkPassword(Long userId,String plainPassword);
	
	/**
	 * 用户调用
	 * 
	 * @param userId
	 * @param oldPlainPassword
	 * @param newPlainPassword
	 */
	public void changePassword(Long userId,String oldPlainPassword,String newPlainPassword);
	
	public boolean checkLoginNameExist(String loginName);
	
	/**
	 * 查询用户菜单
	 * 
	 * @param userId
	 * @return
	 */
	public List<Permission> queryMenus(Long userId);
	
	
	/**
	 * 查询用户菜单url
	 * 
	 * @param userId
	 * @return
	 */
	public List<String> queryMenuUrls(Long userId);
	
	/**
	 * 查询用户权限
	 * 
	 * @param userId
	 * @return
	 */
	public Set<String> queryPermissions(Long userId);
}
