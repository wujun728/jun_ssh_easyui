package com.jun.plugin.boot.web.controller;



import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.boot.core.entity.Role;
import com.jun.plugin.boot.core.service.RoleService;
import com.jun.plugin.common.model.EasyUIPage;
import com.jun.plugin.common.model.JsonData;



/**
 *
 * 
 * Generated by fieldmeta at 2018-09-20T21:15:17+08:00
 *
 */

@Controller("adminRoleController")
@RequestMapping("/admin/action/boot/role")
public class RoleController {
	@Autowired
	RoleService roleService;
	
	@ResponseBody
	@PostMapping("/add")
	@RequiresPermissions("boot:role:add")
	public JsonData add(
			@ModelAttribute Role role){
		
		roleService.save(role);
		return JsonData.success();
	}
	
	@ResponseBody
	@PostMapping("/edit")
	@RequiresPermissions("boot:role:edit")
	public JsonData edit(
			@ModelAttribute Role role){
		
		roleService.update(role);
		return JsonData.success();
	}
	
	@ResponseBody
	@RequestMapping("/findpage")
	@RequiresPermissions("boot:role:query")
	public EasyUIPage findpage(
			@ModelAttribute Role role,
			@RequestParam int page,
			@RequestParam int rows){
		Pageable pageable=new PageRequest(page<1?0:page-1, rows, new Sort(Direction.DESC,"id"));
		Page<Role> pageData=roleService.findPage(role, pageable);
		return new EasyUIPage(pageData);
	}
	
	@ResponseBody
	@RequestMapping("/findlist")
	@RequiresPermissions("boot:role:query")
	public JsonData findlist(
			@ModelAttribute Role role){
		
		List<Role> listData=roleService.findList(role, new Sort(Direction.DESC,"id"));
		return JsonData.success(listData);
	}		
	
}