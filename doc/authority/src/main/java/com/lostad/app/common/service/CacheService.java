/**
 * 
 */
package com.lostad.app.common.service;

import com.lostad.app.apiOpen.biz.entity.HisThirdParty;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;

/**
 * 缓存实现 
 * @author ssz
 *
 */
public interface CacheService {
	public Role findRoleByCode(String roleCode);
	public void updateRole(Role role);
	public User findUser(String id);
	public void delUser(User user);
	public void saveUser(User user);
	public HisThirdParty getHisThirdParty(String appId);
}
