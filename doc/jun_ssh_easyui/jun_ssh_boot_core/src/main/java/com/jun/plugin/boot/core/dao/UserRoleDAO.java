package com.jun.plugin.boot.core.dao;

import java.util.List;

import org.springframework.data.repository.query.Param;

import com.jun.plugin.boot.core.entity.UserRole;
import com.jun.plugin.common.dataaccess.BaseRepository;
import com.slyak.spring.jpa.TemplateQuery;


/**
 *
 * 
 * Generated by fieldmeta at 2018-10-23T14:08:29+08:00
 *
 */

public interface UserRoleDAO extends BaseRepository<UserRole, Long> {

	
	@TemplateQuery
	public List<Long> findRoleIdsByUserId(@Param("userId") Long userId);
	
}