/**
 * 
 */
package com.lostad.app.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lostad.app.apiOpen.biz.entity.HisThirdParty;
import com.lostad.app.common.dao.JpaDao;
import com.lostad.app.common.service.CacheService;
import com.lostad.app.common.util.Validator;
import com.lostad.app.system.dao.RoleDao;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;

/**
 * 用于处理人员信息等缓存数据,
 * 其它缓存数据，如HisDepart直接在类上加的缓存注解。
 * 未提供更新缓存的方法
 * @author sszvip@qq.com
 * 
 */
@Service("cacheService")
@Transactional
public class CacheServiceImpl implements CacheService {
	@Autowired
    private UserDao userDao;
	@Autowired
    private RoleDao roleDao;
	@Autowired
	private JpaDao jpaDao;
	
	//============用户信息===============================================================
	@org.springframework.cache.annotation.Cacheable(value = "basicCache", key = "#roleCode.concat('_role')")
	public Role findRoleByCode(String roleCode){
		Role p = null;
		if(Validator.isNotEmpty(roleCode)){
			p = roleDao.findByRoleCode(roleCode);
		}
		
		return p;
	}
	@org.springframework.cache.annotation.CacheEvict(value = "basicCache", key = "#role.roleCode.concat('_role')")
	public void updateRole(Role role) {
		roleDao.saveAndFlush(role);
	}
	
	//============用户信息===============================================================
	@org.springframework.cache.annotation.Cacheable(value = "basicCache", key = "#id.concat('_user')")
	public User findUser(String id){
		User p = null;
		if(Validator.isNotEmpty(id)){
			p = userDao.findOne(id);
		}
		return p;
	}
	@org.springframework.cache.annotation.CacheEvict(value = "basicCache", key = "#user.id.concat('_user')")
	public void saveUser(User user) {
		userDao.saveAndFlush(user);
	}
	
	@org.springframework.cache.annotation.CacheEvict(value = "basicCache", key = "#user.id.concat('_user')")
	public void delUser(User user) {
		userDao.delete(user);
	}
	
	//============第三方平台对接===============================================================
	@org.springframework.cache.annotation.Cacheable(value = "basicCache", key = "#appId.concat('_app')")
	public HisThirdParty getHisThirdParty(String appId) {
		return jpaDao.find(HisThirdParty.class,appId);
	}
	@org.springframework.cache.annotation.CacheEvict(value = "basicCache", key = "#user.id.concat('_app')")
	public void updateThirdParty(HisThirdParty thirdParty) {
		jpaDao.merge(thirdParty);
	}
}
