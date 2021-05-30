package com.lostad.app.system.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lostad.app.common.dao.IBaseSpringDataDao;
import com.lostad.app.system.entity.Dict;

@Repository
public interface DictDao extends IBaseSpringDataDao<Dict, String> {

	Dict findByCode(String code);
    List<Dict> findByType(String type);
}
