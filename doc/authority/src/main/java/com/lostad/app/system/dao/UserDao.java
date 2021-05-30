package com.lostad.app.system.dao;

import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.User;

@Repository
public interface UserDao extends IBaseSpringDataDao<User, String> {

	User findByUsername(String username);

}
