package com.lostad.app.system.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lostad.app.common.service.IBaseService;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;

/**
 * <p>
 * 用户服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface UserService extends IBaseService<User, String> {

	/**
	 * 根据用户名查找用户
	 * @param username
	 * @return
	 */
	User findByUserName(String username);

	/**
	 * 增加或者修改用户
	 * @param user
	 */
	void saveOrUpdate(User user);

	/**
	 * 给用户分配角色
	 * @param id 用户ID
	 * @param roleIds 角色Ids
	 */
	void grant(String id, String[] roleIds);

	Set<String>  findMyPermitions();

	Set<String> findMyRoles();

	List<User> findByRoleId(String id);

	List<User> findByDeptId(String officeId);

	Boolean outUserInRole(String roleId, String userId);

	int assignUserToRole(String roleId, String[] idsArr);
}
