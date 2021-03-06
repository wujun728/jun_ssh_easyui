package com.erp.service;

import java.util.List;

import com.erp.dto.TreeModel;
import com.erp.model.SystemCode;

public interface SystemCodeService
{

	List<SystemCode> findSystemCodeList(Integer id);

	List<TreeModel> findSystemCodeList();

	boolean persistenceSystemCodeDig(SystemCode systemCode,String permissionName,Integer codePid);

	boolean delSystemCode(Integer codeId );

	List<SystemCode> findSystemCodeByType(String codeMyid );

}
