package com.jun.plugin.boot.core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.boot.core.BootConst.KickoutMode;
import com.jun.plugin.boot.core.dao.UserRoleDAO;
import com.jun.plugin.boot.core.entity.UserRole;
import com.jun.plugin.boot.core.entity.UserRole_;
import com.jun.plugin.common.dataaccess.BaseServiceImpl;
import com.jun.plugin.query.jpa.expr.AExpr;

/**
 *
 * 
 * Generated by fieldmeta at 2018-10-23T14:08:29+08:00
 *
 */

@Service
public class UserRoleServiceImpl extends BaseServiceImpl<UserRole, Long> implements UserRoleService {
	@Autowired
	UserRoleDAO userRoleDAO;

	@Autowired
	UserService userService;
	
	
	@Override
	public List<Long> findRoleIdsByUserId(Long userId) {
		// TODO Auto-generated method stub
		return userRoleDAO.findRoleIdsByUserId(userId);
	}

	@Transactional
	@Override
	public void deleteInBatchByUserId(Long userId) {
		// TODO Auto-generated method stub
		List<UserRole> userRoles = userRoleDAO.findList(AExpr.eq(UserRole_.userId, userId));
		userRoleDAO.deleteInBatch(userRoles);
	}

	@Transactional
	@Override
	public void updateUserRoles(Long userId, List<Long> roleIds) {
		// TODO Auto-generated method stub

		List<Long> oldRoleIds = findRoleIdsByUserId(userId);

		// 需要删除
		List<UserRole> needToBeDeleted = new ArrayList<>();
		for (Long oldRoleId : oldRoleIds) {
			if (!roleIds.contains(oldRoleId)) {
				UserRole userRole = userRoleDAO.getOne(AExpr.eq(UserRole_.userId, userId), AExpr.eq(UserRole_.roleId, oldRoleId));
				needToBeDeleted.add(userRole);
			}
		}
		// 批量删除
		userRoleDAO.delete(needToBeDeleted);

		// 需要添加
		List<UserRole> needToBeAdded = new ArrayList<>();

		for (Long newRoleId : roleIds) {
			if (!oldRoleIds.contains(newRoleId)) {
				UserRole userRole = new UserRole();
				userRole.setRoleId(newRoleId);
				userRole.setUserId(userId);
				needToBeAdded.add(userRole);
			}
		}
		// 批量保存
		userRoleDAO.save(needToBeAdded);
		userService.kickout(userId, KickoutMode.ALL);
	}
}
