package com.lostad.app.system.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.jni.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lostad.app.common.controller.BaseController;
import com.lostad.app.common.util.StringUtils;
import com.lostad.app.system.entity.Menu;
import com.lostad.app.system.service.ResourceService;
import com.lostad.app.system.util.UserUtils;

/**
 * 资源管理
 * @author sszvip
 *  
 */
@Controller
@RequestMapping(value = "/sys/menu")
public class MenuController extends BaseController {

	@Autowired
	private ResourceService resourceService;
	

	// @RequiresPermissions("sys:menu:list")
	@RequestMapping(value = {"/indexMenu",""})
	public String list(Model model) {
		List<Menu> list = Lists.newArrayList();
		List<Menu> sourcelist = resourceService.findAll();
		Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
		return "/sys/indexMenu";
	}

	@RequiresPermissions(value={"sys:menu:view","sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "/form")
	public String form(Menu menu, Model model) {
		if (menu.getParent()==null||menu.getParent().getId()==null){
			menu.setParent(new Menu(Menu.getRootId()));
		}
		menu.setParent(resourceService.find(menu.getParent().getId()));
		// 获取排序号，最末节点排序号+30
		if (StringUtils.isBlank(menu.getId())){
			List<Menu> list = Lists.newArrayList();
			List<Menu> sourcelist = resourceService.findAll();
			Menu.sortList(list, sourcelist, menu.getParentId(), false);
			if (list.size() > 0){
				menu.setSort(list.get(list.size()-1).getSort() + 30);
			}
		}
		model.addAttribute("menu", menu);
		return "/sys/menuForm";
	}
	
	@RequiresPermissions(value={"sys:menu:add","sys:menu:edit"},logical=Logical.OR)
	@RequestMapping(value = "/save")
	public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
		if(!UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "越权操作，只有超级管理员才能添加或修改数据！");
			return "redirect:" + "/sys/role/?repage";
		}
		resourceService.save(menu);
		addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
		return "redirect:" + "/sys/menu/indexMenu";
	}
	
	@RequiresPermissions("sys:menu:del")
	@RequestMapping(value = "/delete")
	public String delete(Menu menu, RedirectAttributes redirectAttributes) {
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
			resourceService.delete(menu.getId());
			addMessage(redirectAttributes, "删除菜单成功");
//		}
		return "redirect:"  + "/sys/menu/indexMenu";
	}

	@RequiresPermissions("sys:menu:del")
	@RequestMapping(value = "/deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
//		if (Menu.isRoot(id)){
//			addMessage(redirectAttributes, "删除菜单失败, 不允许删除顶级菜单或编号为空");
//		}else{
		String idArray[] =ids.split(",");
		for(String id : idArray){
			resourceService.delete(id);
		}
			
		addMessage(redirectAttributes, "删除菜单成功");
//		}
		return "redirect:"  + "/sys/menu/indexMenu";
	}
	@RequiresPermissions("user")
	@RequestMapping(value = "tree")
	public String tree() {
		return "/sys/menuTree";
	}

	@RequiresPermissions("user")
	@RequestMapping(value = "/treeselect")
	public String treeselect(String parentId, Model model) {
		model.addAttribute("parentId", parentId);
		return "/sys/menuTreeselect";
	}
	
	/**
	 * 批量修改菜单排序
	 */
	@RequiresPermissions("sys:menu:updateSort")
	@RequestMapping(value = "/updateSort")
	public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
    	for (int i = 0; i < ids.length; i++) {
    		Menu menu = resourceService.find(ids[i]);
    		menu.setSort(sorts[i]);
    		resourceService.update(menu);
    	}
    	addMessage(redirectAttributes, "保存菜单排序成功!");
		return "redirect:"  + "/sys/menu/indexMenu";
	}
	
	/**
	 * isShowHide是否显示隐藏菜单
	 * @param extId
	 * @param isShowHidden
	 * @param response
	 * @return
	 */
	//@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,@RequestParam(required=false) String isShowHide, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Menu> list = resourceService.findAll();
		for (int i=0; i<list.size(); i++){
			Menu e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				if(isShowHide != null && isShowHide.equals("0") && e.getIsShow().equals("0")){
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
}
