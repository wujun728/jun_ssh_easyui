package org.coderfun.boot.core.dao;

import java.util.List;

import org.coderfun.boot.core.entity.UserRole;
import org.springframework.data.repository.query.Param;

import com.slyak.spring.jpa.TemplateQuery;

import klg.common.dataaccess.BaseRepository;


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
