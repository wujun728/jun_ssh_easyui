package com.lostad.app.system.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.common.service.impl.BaseServiceImpl;
import com.lostad.app.common.vo.ZtreeView;
import com.lostad.app.system.dao.ResourceDao;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.RoleService;
import com.lostad.app.system.service.ResourceService;
import com.lostad.app.system.util.UserUtils;

/**
 * <p>
 * 资源表 服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class ResourceServiceImpl extends BaseServiceImpl<Menu, String>
		implements ResourceService {

	@Autowired
	private ResourceDao resourceDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;

	@Override
	@Cacheable(value = "resourceCache", key = "'tree' + #roleId")
	public List<ZtreeView> tree(String roleId) {
		List<ZtreeView> resulTreeNodes = new ArrayList<ZtreeView>();
		Role role = roleService.find(roleId);
		List<Menu> roleResources = roleService.listResources(roleId);
		resulTreeNodes.add(new ZtreeView(0L, null, "系统菜单", true));
		ZtreeView node;
		Sort sort = new Sort(Direction.ASC, "parent", "id", "sort");
		List<Menu> all = resourceDao.findAll(sort);
		for (Menu resource : all) {
			node = new ZtreeView();
			node.setId(Long.valueOf(resource.getId()));
			if (resource.getParent() == null) {
				node.setpId(0L);
			} else {
				node.setpId(Long.valueOf(resource.getParent().getId()));
			}
			node.setName(resource.getName());
			if (roleResources != null && roleResources.contains(resource)) {
				node.setChecked(true);
			}
			resulTreeNodes.add(node);
		}
		return resulTreeNodes;
	}

	@Override
	@CacheEvict(value = "resourceCache")
	public void saveOrUpdate(Menu resource) {
		if(resource.getId() != null){
			Menu dbResource = find(resource.getId());
			dbResource.setName(resource.getName());
			dbResource.setPermission(resource.getPermission());
			//dbResource.setType(resource.getType());
			dbResource.setHref(resource.getHref());
			dbResource.setSort(resource.getSort());
			dbResource.setIcon(resource.getIcon());
			dbResource.setRemarks(resource.getRemarks());
			dbResource.setParent(resource.getParent());
			update(dbResource);
		}else{
			save(resource);
		}
	}

	@Override
	@CacheEvict(value = "resourceCache")
	public void delete(String id) {
		resourceDao.deleteGrant(id);
		super.delete(id);
	}

	@Override
	public IBaseSpringDataDao<Menu, String> getBaseDao() {
		return resourceDao;
	}

	@Override
	public List<Menu> findMyMenus() {
		User currUser = UserUtils.getCurrUser();
		List<Menu> list = null ;
		//超级管理员
		if(currUser.getRoleCodes().contains("administrator")){
			list = resourceDao.findAllMenu();
		}else{
			list = resourceDao.findAllMenu();
		}
		
		return list;
	}
	
}
