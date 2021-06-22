package com.jun.plugin.boot.web.controller;



import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.boot.core.entity.Permission;
import com.jun.plugin.boot.core.service.PermissionService;
import com.jun.plugin.common.log.LogModuleCode;
import com.jun.plugin.common.log.Logger;
import com.jun.plugin.common.model.JsonData;
import com.jun.plugin.common.tree.TreeHelper;



/**
 *
 * 
 * Generated by fieldmeta at 2018-09-20T21:15:17+08:00
 *
 */

@Controller("adminPermissionController")
@RequestMapping("/admin/action/boot/permission")
public class PermissionController {
	@Autowired
	PermissionService permissionService;
	
	@ResponseBody
	@PostMapping("/add")
	@RequiresPermissions("boot:permission:add")
	public JsonData add(
			@ModelAttribute Permission permission){
		
		permissionService.save(permission);
		return JsonData.success();
	}
	
	@Logger(name = "修改权限" , moduleCode = LogModuleCode.BOOT)
	@ResponseBody
	@PostMapping("/edit")
	@RequiresPermissions("boot:permission:edit")
	public JsonData edit(
			@ModelAttribute Permission permission){
		//二次代理排查		
//		MyPrinter.print(AopUtils.isAopProxy(permissionService));
//		MyPrinter.print(AopUtils.isCglibProxy(permissionService));
//		MyPrinter.print(AopUtils.isJdkDynamicProxy(permissionService));
		
		permissionService.update(permission);
		return JsonData.success();
	}	
	@Logger(name = "删除权限" , moduleCode = LogModuleCode.BOOT)
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("boot:permission:delete")
	public JsonData delete(
			@RequestParam Long id){
		
		permissionService.delete(id);
		return JsonData.success();
	}
	
	@ResponseBody
	@RequestMapping("/findAll")
	@RequiresPermissions(value ={"boot:permission:query","boot:rolePermissions:query"},logical = Logical.OR)

	public List<Permission> findAll(){
		Sort sort = new Sort(Direction.ASC,"orderNum");
		List<Permission> listData=permissionService.findList(sort);
		
		return TreeHelper.<Long,Permission>buildTree(null, listData);
	}
	
	@ResponseBody
	@RequestMapping("/getAllMenus")
	@RequiresPermissions(value ={"boot:permission:query","boot:rolePermissions:query"},logical = Logical.OR)
	public List<Permission> getAllMenus(){
		
		List<Permission> listData=permissionService.getAllMenus();
		
		return TreeHelper.<Long,Permission>buildTree(null, listData);
	}
	
	@ResponseBody
	@RequestMapping("/getOperations")
	@RequiresPermissions(value ={"boot:permission:query","boot:rolePermissions:query"},logical = Logical.OR)
	public List<Permission> getOperations(@RequestParam(required=false) Long parentId){
		
		if(null == parentId){
			return new ArrayList<>();
		}
		
		List<Permission> listData=permissionService.getOperations(parentId);;
		return listData;
	}
	
	
}
