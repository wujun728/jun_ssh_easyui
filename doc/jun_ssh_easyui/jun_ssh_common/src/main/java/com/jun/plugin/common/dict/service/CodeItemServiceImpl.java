package com.jun.plugin.common.dict.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.common.dataaccess.BaseServiceImpl;
import com.jun.plugin.common.dict.dao.CodeItemDAO;
import com.jun.plugin.common.dict.entity.CodeItem;


@Service
public class CodeItemServiceImpl  extends BaseServiceImpl<CodeItem, Long> implements CodeItemService{
	@Autowired
	CodeItemDAO codeItemDAO;
}
