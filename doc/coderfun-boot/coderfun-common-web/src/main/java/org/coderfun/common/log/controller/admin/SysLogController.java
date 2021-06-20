package org.coderfun.common.log.controller.admin;



import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.coderfun.common.log.entity.SysLog;
import org.coderfun.common.log.entity.SysLog_;
import org.coderfun.common.log.service.SysLogService;
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

import klg.common.model.EasyUIPage;
import klg.query.jpa.expr.AExpr;



/**
 *
 * 
 * Generated by fieldmeta at 2018-11-23T15:14:20+08:00
 *
 */

@Controller("adminSysLogController")
@RequestMapping("/admin/action/syslog")
public class SysLogController {
	@Autowired
	SysLogService sysLogService;
	
	@ResponseBody
	@RequestMapping("/findpage")
	@RequiresPermissions("common:syslog:query")
	public EasyUIPage findpage(
			@ModelAttribute SysLog sysLog,
			@RequestParam int page,
			@RequestParam int rows){
		Pageable pageable=new PageRequest(page<1?0:page-1, rows, new Sort(Direction.DESC,"id"));
		Page<SysLog> pageData=sysLogService.findPage(pageable,
				AExpr.eq(SysLog_.moduleCode, sysLog.getModuleCode()).igEmpty(),
				AExpr.eq(SysLog_.successed, sysLog.getSuccessed()).igEmpty(),
				AExpr.contain(SysLog_.name, sysLog.getName()).igEmpty(),
				AExpr.contain(SysLog_.opusername, sysLog.getOpusername()).igEmpty());
		return new EasyUIPage(pageData);
	}
}
