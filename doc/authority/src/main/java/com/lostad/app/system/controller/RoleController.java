package com.lostad.app.system.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Maps;
import com.lostad.app.common.controller.BaseController;
import com.lostad.app.common.service.CacheService;
import com.lostad.app.common.util.Collections3;
import com.lostad.app.common.util.StringUtils;
import com.lostad.app.common.util.Validator;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.entity.Role;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.RoleService;
import com.lostad.app.system.service.ResourceService;
import com.lostad.app.system.service.UserService;
import com.lostad.app.system.util.UserUtils;

/**
 * 角色管理
 * @author sszvip
 *
 */
@Controller
@RequestMapping(value = "/sys/role")
public class RoleController extends BaseController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private ResourceService resourceService;
	@Autowired
	private UserService userService;
	@Autowired
	private CacheService cacheService;
	
	@ModelAttribute("role")
	public Role get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return roleService.find(id);
		}else{
			return new Role();
		}
	}
	
	@RequiresPermissions("sys:role:list")
	@RequestMapping(value = {"/list", ""})
	public String list(Role role, Model model) {
		List<Role> list = roleService.findRoles(role);
		model.addAttribute("list", list);
		return "sys/roleList";
	}

	@RequiresPermissions(value={"sys:role:view","sys:role:add","sys:role:edit"},logical=Logical.OR)
	@RequestMapping(value = "/form")
	public String form(Role role, Model model) {
		model.addAttribute("role", role);
		model.addAttribute("menuList", resourceService.findAll());
		return "sys/roleForm";
	}
	
	@RequiresPermissions("sys:role:auth")
	@RequestMapping(value = "/auth")
	public String auth(RoleVO role, Model model) {
		model.addAttribute("role", role);
		List menuList = resourceService.findAll();
		model.addAttribute("menuList",menuList);
		List<Menu> list = roleService.listResources(role.getId());
		StringBuilder sb = new StringBuilder();
		for(Menu m:list){
			sb.append(m.getId()).append(",");
		}
		role.setMenuIds(sb.toString());
		
		return "sys/roleAuth";
	}
	
	
	@RequiresPermissions(value={"sys:role:assign","sys:role:auth","sys:role:add","sys:role:edit"},logical=Logical.OR)
	@RequestMapping(value = "/save")
	public String save(Role role, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		try{
			roleService.save(role);
			addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
			return "redirect:" + "/sys/role/?repage";
		}catch(Exception e){
			addMessage(model, "保存角色'" + role.getName() + "'失败, 数据不合法");
			return list(role, model);
		}
	
	}
	
	@RequiresPermissions(value={"sys:role:assign","sys:role:auth","sys:role:add","sys:role:edit"},logical=Logical.OR)
	@RequestMapping(value = "/grant")
	public String grant(@RequestParam("id") String roleId,@RequestParam String menuIds,Model model, RedirectAttributes redirectAttributes) {
		try{
			roleService.grant(roleId,menuIds.split(","));
			addMessage(redirectAttributes, "保存成功");
			return "redirect:" + "/sys/role/?repage";
		}catch(Exception e){
			addMessage(model, "保存失败, 数据不合法");
			return list(new Role(), model);
		}
	}
	
	@RequiresPermissions("sys:role:del")
	@RequestMapping(value = "/delete")
	public String delete(@RequestParam String id, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		Role role = roleService.find(id);
		try{
			roleService.delete(role);
			addMessage(redirectAttributes, "保存角色'" + role.getName() + "'成功");
		}catch(Exception e){
			addMessage(redirectAttributes, "删除角色失败!");
			return list(role, redirectAttributes);
		}
		return "redirect:/sys/role/?repage";
	}
	
	/**
	 * 批量删除角色
	 */
	@RequiresPermissions("sys:role:del")
	@RequestMapping(value = "/deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		String idArray[] =ids.split(",");
		StringBuffer msg = new StringBuffer();
		for(String id : idArray){
			roleService.delete(id);
		}
		addMessage(redirectAttributes, msg.toString());
		return "redirect:/sys/role/?repage";
	}
	
	/**
	 * 角色分配页面
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "/assign")
	public String assign(Role role, Model model) {
		List<User> userList = userService.findByRoleId(role.getId());
		model.addAttribute("userList", userList);
		return "modules/sys/roleAssign";
	}
	
	/**
	 * 角色分配 -- 打开角色分配对话框
	 * @param role
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "/usertorole")
	public String selectUserToRole(Role role, Model model) {
		List<User> userList = userService.findByRoleId(role.getId());
		model.addAttribute("role", role);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "name", ","));
//		model.addAttribute("officeList", officeService.findAll());
		return "modules/sys/selectUserToRole";
	}
//	
	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@ResponseBody
	@RequestMapping(value = "/users")
	public List<Map<String, Object>> users(String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = new ArrayList<>();
		User user = new User();
		user.setDeptId(officeId);
		List<User> list = userService.findByDeptId(officeId);
		for (User e : list) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getRealName());
			mapList.add(map);			
		}
		return mapList;
	}
	
	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "/outrole")
	public String outrole(String userId, String roleId, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		Role role = roleService.find(roleId);
		User user = userService.find(userId);
		if (UserUtils.getUser().getId().equals(userId)) {
			addMessage(redirectAttributes, "无法从角色【" + role.getName() + "】中移除用户【" + user.getRealName() + "】自己！");
		}else {
			if (user.getRoles().size() <= 1){
				addMessage(redirectAttributes, "用户【" + user.getRealName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			}else{
				Boolean flag = userService.outUserInRole(roleId, userId);
				if (!flag) {
					addMessage(redirectAttributes, "用户【" + user.getRealName() + "】从角色【" + role.getName() + "】中移除失败！");
				}else {
					addMessage(redirectAttributes, "用户【" + user.getRealName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}		
		}
		return "redirect:/sys/role/assign?id="+role.getId();
	}
	
	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:role:assign")
	@RequestMapping(value = "/assignrole")
	public String assignRole(Role role, String[] idsArr, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能修改此数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		StringBuilder msg = new StringBuilder();
		int newNum = userService.assignUserToRole(role.getId(), idsArr);
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" +  "/sys/role/assign?id="+role.getId();
	}


	/**
	 * 验证角色code是否合法
	 * @param oldName
	 * @param name
	 * @return
	 */
	///@RequiresPermissions("/user")
	@ResponseBody
	@RequestMapping(value = "/checkRoleCode")
	public String checkRoleCode(String id,String roleCode) {
		Role r = cacheService.findRoleByCode(roleCode);
		
		boolean ok = (r==null?true:false);//是否已经存在
		
		if(Validator.isNotEmpty(id)){//如果是更新操作
			ok = !ok;//与更新操作逻辑相反
		}
		
		return ok+"";
	}

}
