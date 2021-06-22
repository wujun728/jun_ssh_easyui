package com.jun.plugin.common.dict.controller.admin;



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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jun.plugin.common.dict.entity.CodeItem;
import com.jun.plugin.common.dict.entity.CodeItem_;
import com.jun.plugin.common.dict.service.CodeItemService;
import com.jun.plugin.common.exception.AppException;
import com.jun.plugin.common.exception.ErrorCodeEnum;
import com.jun.plugin.common.log.LogModuleCode;
import com.jun.plugin.common.log.Logger;
import com.jun.plugin.common.model.EasyUIPage;
import com.jun.plugin.common.model.JsonData;
import com.jun.plugin.query.jpa.expr.AExpr;


@Controller("adminCodeItemController")
@RequestMapping("/admin/action/codeitem")
public class CodeItemController {
	@Autowired
	CodeItemService codeItemService;
	
	@Logger(name = "添加字典子类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/add")
	@RequiresPermissions("common:dict:add")
	public JsonData add(
			@ModelAttribute CodeItem codeItem){
		
		if(codeItemService.getOne(
				AExpr.eq(CodeItem_.classCode, codeItem.getClassCode()),
				AExpr.eq(CodeItem_.code, codeItem.getCode()))==null){
			codeItemService.save(codeItem);
		}else{
			throw new AppException(ErrorCodeEnum.DATA_EXISTED,"重复的代码！");
		}
		return JsonData.success();
	}
	
	@Logger(name = "修改字典子类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/edit")
	@RequiresPermissions("common:dict:edit")
	public JsonData edit(
			@ModelAttribute CodeItem codeItem){
		
		codeItemService.update(codeItem, "code");
		return JsonData.success();
	}
	
	@Logger(name = "删除字典子类", moduleCode = LogModuleCode.DICT)
	@ResponseBody
	@RequestMapping("/delete")
	@RequiresPermissions("common:dict:delete")
	public JsonData delete(
			@RequestParam Long id){
		
		codeItemService.delete(id);
		return JsonData.success();
	}
	
	@ResponseBody
	@RequestMapping("/findpage")
	@RequiresPermissions("common:dict:query")
	public EasyUIPage findpage(
			@ModelAttribute CodeItem codeItem,
			@RequestParam int page,
			@RequestParam int rows){
		Pageable pageable=new PageRequest(page<1?0:page-1, rows, new Sort(Direction.DESC,"orderNum"));
		Page<CodeItem> pageData=codeItemService.findPage(codeItem, pageable);
		return new EasyUIPage(pageData);
	}
	
	@ResponseBody
	@RequestMapping("/findlist")
	@RequiresPermissions("common:dict:query")
	public JsonData findlist(
			@ModelAttribute CodeItem codeItem){
		
		List<CodeItem> listData=codeItemService.findList(codeItem, new Sort(Direction.DESC,"orderNum"));
		return JsonData.success(listData);
	}
	
	@ResponseBody
	@RequestMapping("/datalist")
	@RequiresPermissions("common:dict:query")
	public List datalist(
			@ModelAttribute CodeItem codeItem){
		List<CodeItem> listData=codeItemService.findList(codeItem, new Sort(Direction.DESC,"orderNum"));
		return listData;
	}
}
