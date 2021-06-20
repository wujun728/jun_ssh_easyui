package org.coderfun.boot.core.service;

import java.util.ArrayList;
import java.util.List;

import org.coderfun.boot.core.BootDict;
import org.coderfun.boot.core.dao.PermissionDAO;
import org.coderfun.boot.core.entity.Permission;
import org.coderfun.boot.core.entity.Permission_;
import org.coderfun.boot.core.entity.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import klg.common.dataaccess.BaseServiceImpl;
import klg.query.jpa.expr.AExpr;



/**
 *
 * 
 * Generated by fieldmeta at 2018-09-20T21:15:17+08:00
 *
 */
 
@Service
public class PermissionServiceImpl  extends BaseServiceImpl<Permission, Long> implements PermissionService{
	@Autowired
	PermissionDAO permissionDAO;

	@Autowired
	RolePermissionService rolePermissionService;
	
//	@Transactional
//	@Override
//	public Permission update(Permission entity) {
//		// TODO Auto-generated method stub
//		super.update(entity);
//		throw new RuntimeException("测试事务！");
//	}
	
	@Transactional
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		rolePermissionService.deleteInBatchByPermissionId(id);
		super.delete(id);
	}
	
	@Override
	public List<Permission> getAllMenus() {
		// TODO Auto-generated method stub
		return permissionDAO.findList(new Sort(Direction.ASC,"orderNum"),
				AExpr.eq(Permission_.type, BootDict.Permission.Type.MENU));
	}

	@Override
	public List<Permission> getOperations(Long parentId) {
		// TODO Auto-generated method stub
		return permissionDAO.findList(new Sort(Direction.ASC,"orderNum"),
				AExpr.eq(Permission_.type, BootDict.Permission.Type.OPERATION),
				AExpr.eq(Permission_.parentId, parentId));
	}

	@Override
	public void addBaseOperations(Long parentId, String pClassName,String moduleCode) {
		// TODO Auto-generated method stub
		List<Permission> pList=new ArrayList<Permission>();
		pList.add(new Permission(parentId, "添加", BootDict.Permission.Type.OPERATION, null, moduleCode+":"+pClassName+":add"));
		pList.add(new Permission(parentId, "删除", BootDict.Permission.Type.OPERATION, null, moduleCode+":"+pClassName+":delete"));
		pList.add(new Permission(parentId, "修改", BootDict.Permission.Type.OPERATION, null, moduleCode+":"+pClassName+":edit"));
		pList.add(new Permission(parentId, "查询", BootDict.Permission.Type.OPERATION, null, moduleCode+":"+pClassName+":query"));
		
		//添加没有的基本操作
		List<Permission> existPList=getOperations(parentId);
		for(Permission permission:pList){
			boolean exist=false;
			for(Permission existPermission:existPList){
				if(permission.getPermCode().equals(existPermission.getPermCode())){
					exist=true;
					break;
				}else{
					exist=false;
				}
			}
			if(!exist)
				save(permission);
		}
	}
}
