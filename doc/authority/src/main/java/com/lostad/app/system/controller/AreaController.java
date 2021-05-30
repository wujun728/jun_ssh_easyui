/**
 * 
 */
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
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.StringUtils;
import com.lostad.app.common.util.Validator;
import com.lostad.app.system.entity.Area;
import com.lostad.app.system.util.UserUtils;

/**
 * 区域Controller
 * @author sszvip
 * @version 2017-5-15
 */
@Controller
@RequestMapping(value = "/sys/area")
public class AreaController extends com.lostad.app.common.controller.BaseController {

	@Autowired
	private CommonService commonService;
	
	@ModelAttribute("area")
	public Area get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return commonService.find(Area.class,id);
		}else{
			return new Area();
		}
	}

	@RequiresPermissions("sys:area:list")
	@RequestMapping(value = {"list", ""})
	public String list(Area area, Model model) {
		model.addAttribute("list", commonService.findHql(" from Area ", null ));
		return "sys/areaList";
	}

	@RequiresPermissions(value={"sys:area:view","sys:area:add","sys:area:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Area area, Model model) {
		if (Validator.isNotEmpty(area.getParentId())){
			//area = commonService.find(Area.class,area.getParentId());
		}
		model.addAttribute("area", area);
		return "sys/areaForm";
	}
	
	@RequiresPermissions(value={"sys:area:add","sys:area:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Area area, Model model, RedirectAttributes redirectAttributes) {
		commonService.save(area);
		addMessage(redirectAttributes, "保存区域'" + area.getName() + "'成功");
		return "redirect:/sys/area/";
	}
	
	@RequiresPermissions("sys:area:del")
	@RequestMapping(value = "delete")
	public String delete(Area area, RedirectAttributes redirectAttributes) {
			commonService.delete(commonService.find(Area.class,area.getId()));
		return "redirect:/sys/area/";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Area> list = commonService.findHql(" from Area ", null);
		for (int i=0; i<list.size(); i++){
			Area e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", e.getParentId());
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}
}
