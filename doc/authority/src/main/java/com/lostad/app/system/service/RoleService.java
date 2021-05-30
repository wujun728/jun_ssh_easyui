package com.lostad.app.system.service;

import java.util.List;
import java.util.Set;

import com.lostad.app.common.service.IBaseService;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;

/**
 * <p>
 * 角色服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface RoleService extends IBaseService<Role,String> {

	/**
	 * 添加或者修改角色
	 * @param role
	 */
	void saveOrUpdate(Role role);

	/**
	 * 给角色分配资源
	 * @param id 角色ID
	 * @param resourceIds 资源ids
	 */
	void grant(String id, String[] resourceIds);

	List<Role> findRoles(Role role);

	List<Menu> listResources(String roleId);

	
}
