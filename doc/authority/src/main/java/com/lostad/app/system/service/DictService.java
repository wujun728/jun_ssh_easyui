/**
 * Copyright &copy; 2015-2020 <a href="http://www.lostad.org/">JeePlus</a> All rights reserved.
 */
package com.lostad.app.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lostad.app.system.dao.DictDao;
import com.lostad.app.system.entity.Dict;

/**
 * 字典Service
 * @author lostad
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService  {
	@Autowired
	private DictDao dictDao;

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		dictDao.save(dict);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		dictDao.delete(dict);
	}

}
