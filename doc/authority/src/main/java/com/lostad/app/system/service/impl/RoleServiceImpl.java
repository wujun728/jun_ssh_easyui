package com.lostad.app.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.common.dao.JdbcDao;
import com.lostad.app.common.dao.JpaDao;
import com.lostad.app.common.exception.ServiceException;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.service.impl.BaseServiceImpl;
import com.lostad.app.common.util.HqlUtil;
import com.lostad.app.system.dao.RoleDao;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.RoleResource;
import com.lostad.app.system.service.RoleService;
import com.lostad.app.system.service.ResourceService;

/**
 * <p>
 * 角色表  服务实现类
 * </p>
 *
 * @author SPPan
 * @since 2016-12-28
 */
@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, String> implements RoleService {

	@Autowired
	private RoleDao roleDao;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private CommonService commonService;
	@Autowired
	private JpaDao jpaDao;
	@Autowired
	private JdbcDao jdbcDao;
	
	@Override
	public void saveOrUpdate(Role role) {
		if(role.getId() != null){
			Role dbRole = find(role.getId());
			dbRole.setName(role.getName());
			dbRole.setRemarks(role.getRemarks());
			update(dbRole);
		}else{
			save(role);
		}
	}

	
	
	@Override
	public void delete(String id) {
		Role role = find(id);
		Assert.state(!"administrator".equals(role.getRoleCode()),"超级管理员角色不能删除");
		super.delete(id);
	}

	@Override
	public void grant(String roleId, String[] resourceIds) {
		Role role = find(roleId);
		Assert.notNull(role, "角色不存在");
		Assert.state(!"administrator".equals(role.getRoleCode()),"超级管理员角色不能进行资源分配");
		if(resourceIds != null&& resourceIds.length>0){
			try{
				jdbcDao.excuteSql(" delete from sys_role_resource where role_id=? ", roleId);
				List<RoleResource> entitys = new ArrayList<>();
				for (int i = 0; i < resourceIds.length; i++) {
					String mId = resourceIds[i];
					if(StringUtils.isBlank(resourceIds[i]) || "0".equals(resourceIds[i])){
						continue;
					}
					Menu m = jpaDao.find(Menu.class,mId);
					entitys.add(new RoleResource(m, role));
				}
				jpaDao.batchSave(entitys);
			}catch(Exception e){
				e.printStackTrace();
				throw new ServiceException("保存失败！");
			}
			
		}
	}



	@Override
	public IBaseSpringDataDao<Role, String> getBaseDao() {
		return roleDao;
	}

	@Override
	public List<Role> findRoles(Role param) {
		 StringBuilder hql = new StringBuilder();
		 hql.append(" from Role where delFlag=0 ");
		 List<Object> params = new ArrayList<>();
		 HqlUtil.genWhereCondition(hql, params, param);
		 List<Role> list = commonService.findHql(hql.toString(), params.toArray());
		return list;
	}



	@Override
	public List<Menu> listResources(String roleId) {
		String hql = " select menu from RoleResource rr where rr.role.id=?";
		return jpaDao.findHql(hql, roleId);
	}
	
}
