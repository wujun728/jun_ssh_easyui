package org.coderfun.fieldmeta.service;

import org.coderfun.fieldmeta.dao.EntityFieldDAO;
import org.coderfun.fieldmeta.dao.PageFieldDAO;
import org.coderfun.fieldmeta.entity.EntityField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klg.common.dataaccess.BaseServiceImpl;

@Service
public class EntityFieldServiceImpl extends BaseServiceImpl<EntityField, Long> implements EntityFieldService {
	@Autowired
	EntityFieldDAO entityFieldDAO;

	@Autowired
	PageFieldDAO pageFieldDAO;

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public EntityField save(EntityField entityField) {
		// TODO Auto-generated method stub

		return null;
	}
}
