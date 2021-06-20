package org.coderfun.fieldmeta.service;

import org.coderfun.fieldmeta.dao.ValidationDAO;
import org.coderfun.fieldmeta.entity.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import klg.common.dataaccess.BaseServiceImpl;

@Service
public class ValidationServiceImpl  extends BaseServiceImpl<Validation, Long> implements ValidationService{
	@Autowired
	ValidationDAO validationDAO;
}
