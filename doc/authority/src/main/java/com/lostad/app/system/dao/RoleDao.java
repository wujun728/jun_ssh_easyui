package com.lostad.app.system.dao;

import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.Role;

@Repository
public interface RoleDao extends IBaseSpringDataDao<Role, String> {
   Role findByRoleCode(String roleCode);
}
