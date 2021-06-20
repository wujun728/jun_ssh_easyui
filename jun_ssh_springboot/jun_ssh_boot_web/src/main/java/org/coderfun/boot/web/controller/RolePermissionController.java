package org.coderfun.boot.web.controller;



import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.coderfun.boot.core.service.RolePermissionService;
import org.coderfun.boot.web.model.RolePermissionRequest;
import org.coderfun.common.log.LogModuleCode;
import org.coderfun.common.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import klg.common.model.JsonData;



/**
 *
 * 
 * Generated by fieldmeta at 2018-10-23T14:08:29+08:00
 *
 */

@Controller("adminRolePermissionController")
@RequestMapping("/admin/action/boot/rolepermission")
public class RolePermissionController {
	@Autowired
	RolePermissionService rolePermissionService;

	
	@ResponseBody
	@RequestMapping("/findPermissionIdsByRoleId")
	@RequiresPermissions("boot:rolePermissions:query")
	public JsonData findPermissionIdsByRoleId(
			@RequestParam Long roleId){
		
		List<Long> data=rolePermissionService.findPermissionIdsByRoleId(roleId);
		return JsonData.success(data);
	}	
	
	@Logger(name = "更新角色权限" , moduleCode = LogModuleCode.BOOT)
	@ResponseBody
	@RequestMapping("/updateRolePermissions")
	@RequiresPermissions("boot:rolePermissions:update")
	public JsonData updateRolePermissions(@RequestBody RolePermissionRequest rolePermissionRequest){
		
		rolePermissionService.updateRolePermissions(rolePermissionRequest.getRoleId(),
				rolePermissionRequest.getPermissionIds());
		return JsonData.success();
	}	
}