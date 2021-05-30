package com.lostad.app.system.dao;

import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.Menu;

@Repository
public interface MenuDao extends IBaseSpringDataDao<Menu, String> {


}
