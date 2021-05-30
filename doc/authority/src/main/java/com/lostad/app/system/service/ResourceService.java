package com.lostad.app.system.service;

import java.util.List;

import com.lostad.app.common.service.IBaseService;
import com.lostad.app.common.vo.ZtreeView;
import com.lostad.app.system.entity.Menu;

/**
 * <p>
 * 资源服务类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
public interface ResourceService extends IBaseService<Menu, String> {

	/**
	 * 获取角色的权限树
	 * @param roleId
	 * @return
	 */
	List<ZtreeView> tree(String roleId);

	/**
	 * 修改或者新增资源
	 * @param resource
	 */
	void saveOrUpdate(Menu resource);

	List<Menu> findMyMenus();

}
