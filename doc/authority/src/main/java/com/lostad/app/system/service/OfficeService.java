/**
 * Copyright &copy; 2015-2020 <a href="http://www.lostad.org/">JeePlus</a> All rights reserved.
 */
package com.lostad.app.system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.Validator;
import com.lostad.app.system.dao.DictDao;
import com.lostad.app.system.entity.Office;
import com.lostad.app.system.util.UserUtils;

/**
 * 机构Service
 * @author lostad
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService  {
    @Autowired 
    private CommonService commonService;
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		String hql = " from Office ";
		String args = null;
		if(Validator.isNotEmpty(office.getCode())){
			hql+=" where code like ? " ;
			args = office.getCode()+"%";
		}
		return commonService.findHql(hql, args);
	}
	
	@Transactional(readOnly = true)
	public Office getByCode(String code){
		return commonService.findUniqueByProperty(Office.class, "code", code);
	}
	
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		commonService.save(office);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		office = commonService.find(Office.class,office.getId());
		commonService.delete(office);
	}
	
}
