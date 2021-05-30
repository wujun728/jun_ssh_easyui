package com.lostad.app.system.dao;

import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.Dict;
import com.lostad.app.system.entity.Office;

@Repository
public interface OfficeDao extends IBaseSpringDataDao<Office, String> {

	Office findByCode(String code);

}
