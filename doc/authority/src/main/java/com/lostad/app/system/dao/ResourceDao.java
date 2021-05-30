package com.lostad.app.system.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.Menu;

@Repository
public interface ResourceDao extends IBaseSpringDataDao<Menu, String> {

	@Modifying
	@Query(nativeQuery = true,value = "DELETE FROM sys_role_resource WHERE resource_id = :id")
	void deleteGrant(@Param("id") String id);
	
	@Query(" FROM Menu WHERE isShow ='1' ")
	List<Menu> findAllMenu();

}
