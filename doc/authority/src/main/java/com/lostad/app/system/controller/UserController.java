/**
 * Copyright &copy; 2015-2020 <a href="http://www.lostad.org/">JeePlus</a> All rights reserved.
 */
package com.lostad.app.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lostad.app.common.controller.BaseController;
import com.lostad.app.common.service.CommonService;
import com.lostad.app.common.util.StringUtils;
import com.lostad.app.common.util.Validator;
import com.lostad.app.common.vo.AjaxJson;
import com.lostad.app.common.vo.Page;
import com.lostad.app.security.util.MD5Utils;
import com.lostad.app.system.dao.UserDao;
import com.lostad.app.system.entity.User;
import com.lostad.app.system.service.RoleService;
import com.lostad.app.system.service.UserService;
import com.lostad.app.system.util.UserUtils;


@Controller
@RequestMapping(value = "/sys/user")
public class UserController extends BaseController {

	@Autowired
	private CommonService commonService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@ModelAttribute
	public User get(@RequestParam(required=false) String id) {
		if (StringUtils.isNotBlank(id)){
			return UserUtils.get(id);
		}else{
			return new User();
		}
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"/index"})
	public String index(User user, Model model) {
		return "sys/userIndex";
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = {"/list", ""})
	public String list(Page pageInfo,User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {
			String sql = " select * from sys_user ";
			pageInfo = commonService.datagridsql(sql, pageInfo, user, new ArrayList());
			model.addAttribute("page", pageInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sys/userList";
	}

	
	@RequiresPermissions(value={"sys:user:view","sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "/form")
	public String form(User user, Model model) {
		
		model.addAttribute("user", user);
		model.addAttribute("allRoles", roleService.findAll());
		return "sys/userForm";
	}

	@RequiresPermissions(value={"sys:user:add","sys:user:edit"},logical=Logical.OR)
	@RequestMapping(value = "/save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		
		// 如果新密码为空，则不更换密码
		if (StringUtils.isNotBlank(user.getPassword())) {
			user.setPassword(MD5Utils.md5Hex(user.getPassword()));
		}
///////////////////////////////////////////////////////////////////
//验证未通过 时
//		if (!beanValidator(model, user)){
//			return form(user, model);
//		}
//      角色数据有效性验证，过滤不在授权内的角色
///////////////////////////////////////////////////////////////////
		//生成用户二维码，使用登录名
//		String realPath = "";//Global.getUserfilesBaseDir() + Global.USERFILES_BASE_URL	+ user.getId() + "/qrcode/";
//		FileUtils.createDirectory(realPath);
//		String name= user.getId()+".png"; //encoderImgId此处二维码的图片名
//		String filePath = realPath + name;  //存放路径
//		TwoDimensionCode.encoderQRCode(user.getUsername(), filePath, "png");//执行生成二维码
//		user.setQrCode(request.getContextPath()+Global.USERFILES_BASE_URL+  user.getId()  + "/qrcode/"+name);
		// 保存用户信息
		userService.save(user);
		// 清除当前用户缓存
		if (user.getUsername().equals(UserUtils.getUser().getUsername())){
			UserUtils.clearCache();
			//UserUtils.getCacheMap().clear();
		}
		addMessage(redirectAttributes, "保存用户'" + user.getUsername() + "'成功");
		return "redirect:/sys/user/list?repage";
	}
	
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "/delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		
		if (UserUtils.getUser().getId().equals(user.getId())){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
		}else if (UserUtils.isCurrUserAdmin()){
			addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
		}else{
			userService.delete(user.getId());
			addMessage(redirectAttributes, "删除用户成功");
		}
		return "redirect:/sys/user/list?repage";
	}
	
	/**
	 * 批量删除用户
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "/deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String msg = "删除成功";
		try{
			String idArray[] =ids.split(",");
			for(String id : idArray){
				if (UserUtils.getUser().getId().equals(id)){
					throw new Exception("不能删除自己！");
				}else if (UserUtils.isCurrUserAdmin()){
					throw new Exception("删除用户失败, 不允许删除超级管理员用户!");
				}else{
					userService.delete(id);
				}
			}
		}catch(Exception e){
			msg = e.getMessage();
		}
		addMessage(redirectAttributes, msg);
		return "redirect:/sys/user/list?repage";
	}
	
//	/**
//	 * 导出用户数据
//	 * @param user
//	 * @param request
//	 * @param response
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequiresPermissions("sys:user:export")
//    @RequestMapping(value = "export", method=RequestMethod.POST)
//    public String exportFile(User user, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		try {
//            String fileName = "用户数据"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
//            Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
//    		new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导出用户失败！失败信息："+e.getMessage());
//		}
//		return "redirect:/sys/user/list?repage";
//    }

//	/**
//	 * 导入用户数据
//	 * @param file
//	 * @param redirectAttributes
//	 * @return
//	 */
//	@RequiresPermissions("sys:user:import")
//    @RequestMapping(value = "import", method=RequestMethod.POST)
//    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//		if(Global.isDemoMode()){
//			addMessage(redirectAttributes, "演示模式，不允许操作！");
//			return "redirect:" + adminPath + "/sys/user/list?repage";
//		}
//		try {
//			int successNum = 0;
//			int failureNum = 0;
//			StringBuilder failureMsg = new StringBuilder();
//			ImportExcel ei = new ImportExcel(file, 1, 0);
//			List<User> list = ei.getDataList(User.class);
//			for (User user : list){
//				try{
//					if ("true".equals(checkLoginName("", user.getLoginName()))){
//						user.setPassword(SystemService.entryptPassword("123456"));
//						BeanValidators.validateWithException(validator, user);
//						systemService.saveUser(user);
//						successNum++;
//					}else{
//						failureMsg.append("<br/>登录名 "+user.getLoginName()+" 已存在; ");
//						failureNum++;
//					}
//				}catch(ConstraintViolationException ex){
//					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败：");
//					List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//					for (String message : messageList){
//						failureMsg.append(message+"; ");
//						failureNum++;
//					}
//				}catch (Exception ex) {
//					failureMsg.append("<br/>登录名 "+user.getLoginName()+" 导入失败："+ex.getMessage());
//				}
//			}
//			if (failureNum>0){
//				failureMsg.insert(0, "，失败 "+failureNum+" 条用户，导入信息如下：");
//			}
//			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户"+failureMsg);
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入用户失败！失败信息："+e.getMessage());
//		}
//		return "redirect:" + adminPath + "/sys/user/list?repage";
//    }
//	
	/**
	 * 下载导入用户数据模板
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
//	@RequiresPermissions("sys:user:import")
//    @RequestMapping(value = "import/template")
//    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
//		try {
//            String fileName = "用户数据导入模板.xlsx";
//    		List<User> list = Lists.newArrayList(); list.add(UserUtils.getUser());
//    		new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
//    		return null;
//		} catch (Exception e) {
//			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
//		}
//		return "redirect:" + adminPath + "/sys/user/list?repage";
//    }



	/**
	 * 用户信息显示
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/info")
	public String info(HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "sys/userInfo";
	}
	
	/**
	 * 用户信息显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/infoEdit")
	public String infoEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if(Validator.isEmpty(user.getId())){
			userService.update(currentUser);
			model.addAttribute("user", currentUser);
			model.addAttribute("Global", new Global());
			model.addAttribute("message", "保存用户信息成功");
			return "sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "sys/userInfoEdit";
	}

	
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/imageEdit")
	public String imageEdit(User user,HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
//		if (StringUtils.isNotBlank(user.getName())){
//			model.addAttribute("message", "保存用户信息成功");
//			return "sys/userInfo";
//		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "sys/userImageEdit";
	}
	/**
	 * 用户头像显示编辑保存
	 * @param user
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/imageUpload")
	public String imageUpload( HttpServletRequest request, HttpServletResponse response,MultipartFile file) throws IllegalStateException, IOException {
		User currentUser = UserUtils.getUser();
		
		// 判断文件是否为空  
//        if (!file.isEmpty()) {  
//                // 文件保存路径  
//            	String realPath = Global.USERFILES_BASE_URL
//        		+ UserUtils.getPrincipal() + "/images/" ;
//                // 转存文件  
//            	FileUtils.createDirectory(Global.getUserfilesBaseDir()+realPath);
//            	file.transferTo(new File( Global.getUserfilesBaseDir() +realPath +  file.getOriginalFilename()));  
//            	currentUser.setPhoto(request.getContextPath()+realPath + file.getOriginalFilename());
//    			systemService.updateUserInfo(currentUser);
//        }  

		return "sys/userImageEdit";
	}

	/**
	 * 返回用户信息
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/infoData")
	public AjaxJson infoData() {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setErrorCode("-1");
		j.setMsg("获取个人信息成功!");
		j.put("data", UserUtils.getUser());
		return j;
	}
	
	
	
	/**
	 * 修改个人用户密码
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "/modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)){
//			if(Global.isDemoMode()){
//				model.addAttribute("message", "演示模式，不允许操作！");
//				return "sys/userInfo";
//			}
//			if (SystemService.validatePassword(oldPassword, user.getPassword())){
//				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
//				model.addAttribute("message", "修改密码成功");
//			}else{
//				model.addAttribute("message", "修改密码失败，旧密码错误");
//			}
			return "sys/userInfo";
		}
		model.addAttribute("user", user);
		return "sys/userModifyPwd";
	}
	
	/**
	 * 保存签名
	 */
	@ResponseBody
	@RequestMapping(value = "/saveSign")
	public AjaxJson saveSign(User user,HttpServletResponse response, Model model) throws Exception{
		AjaxJson j = new AjaxJson();
		User currentUser = UserUtils.getUser();
		currentUser.setSign(user.getSign());
		userService.update(currentUser);
		j.setMsg("设置签名成功");
		return j;
	}
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "/treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String officeId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = userService.findByDeptId(officeId);
		for (int i=0; i<list.size(); i++){
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_"+e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getUsername(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}
    
	@ResponseBody
	@RequestMapping(value = "/resetPassword")
	public AjaxJson resetPassword(String mobile, HttpServletResponse response, Model model) {
//		SystemConfig config = systemConfigService.get("1");//获取短信配置的用户名和密码
		AjaxJson j = new AjaxJson();
//		if(userDao.findUniqueByProperty("mobile", mobile) == null){
//			j.setSuccess(false);
//			j.setMsg("手机号不存在!");
//			j.setErrorCode("1");
//			return j;
//		}
//		User user =  userDao.findUniqueByProperty("mobile", mobile);
//		String newPassword = String.valueOf((int) (Math.random() * 900000 + 100000));
//		try {
//			String result = UserUtils.sendPass(config.getSmsName(), config.getSmsPassword(), mobile, newPassword);
//			if (!result.equals("100")) {
//				j.setSuccess(false);
//				j.setErrorCode("2");
//				j.setMsg("短信发送失败，密码重置失败，错误代码："+result+"，请联系管理员。");
//			}else{
//				j.setSuccess(true);
//				j.setErrorCode("-1");
//				j.setMsg("短信发送成功，密码重置成功!");
//				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
//			}
//		} catch (IOException e) {
//			j.setSuccess(false);
//			j.setErrorCode("3");
//			j.setMsg("因未知原因导致短信发送失败，请联系管理员。");
//		}
		return j;
	}
	
//	@InitBinder
//	public void initBinder(WebDataBinder b) {
//		b.registerCustomEditor(List.class, "roleList", new PropertyEditorSupport(){
//			@Autowired
//			private SystemService systemService;
//			@Override
//			public void setAsText(String text) throws IllegalArgumentException {
//				String[] ids = StringUtils.split(text, ",");
//				List<Role> roles = new ArrayList<Role>();
//				for (String id : ids) {
//					Role role = systemService.getRole(Long.valueOf(id));
//					roles.add(role);
//				}
//				setValue(roles);
//			}
//			@Override
//			public String getAsText() {
//				return Collections3.extractToString((List) getValue(), "id", ",");
//			}
//		});
//	}
}
