package com.jun.plugin.jpa.service.impl;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.jpa.common.exception.BusinessException;
import com.jun.plugin.jpa.entity.Permission;
import com.jun.plugin.jpa.entity.User;
import com.jun.plugin.jpa.repository.PermissionRepository;
import com.jun.plugin.jpa.service.PermissionService;

/**
 * @author Vincent.wang
 *
 */
@Service
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public Collection<Permission> getPermissions(User user) {
		return permissionRepository.getPermissions(user);
	}

	@Override
	public void addPermission(Permission permission) {
		if (permission == null ||
				StringUtils.isBlank(permission.getKey()) ||
				StringUtils.isBlank(permission.getParentKey()) ||
				StringUtils.isBlank(permission.getName())) {
			throw new BusinessException("## 创建菜单出错；菜单项数据不完整，无法进行创建。");
		}
		permissionRepository.addPermission(permission);
	}

}
