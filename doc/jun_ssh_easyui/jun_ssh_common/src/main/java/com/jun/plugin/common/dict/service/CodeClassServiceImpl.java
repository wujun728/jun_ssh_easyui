package com.jun.plugin.common.dict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.common.dataaccess.BaseServiceImpl;
import com.jun.plugin.common.dict.dao.CodeClassDAO;
import com.jun.plugin.common.dict.entity.CodeClass;


@Service
public class CodeClassServiceImpl  extends BaseServiceImpl<CodeClass, Long> implements CodeClassService{
	@Autowired
	CodeClassDAO codeClassDAO;
}
