package com.erp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.erp.dto.GridModel;
import com.erp.dto.Json;
import com.erp.model.Users;
import com.erp.service.UserService;
import com.erp.util.Constants;
import com.erp.util.PageUtil;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/user")
@Slf4j
public class UserAction extends BaseAction 
{
	private static final long serialVersionUID = -8188592660918385632L;
	private UserService userService;
	private String isCheckedIds;
	private Users users;
	
	public Users getUsers()
	{
		return users;
	}

	public void setUsers(Users users )
	{
		this.users = users;
	}

	@Autowired
	public void setUserService(UserService userService )
	{
		this.userService = userService;
	}
	
	public String getIsCheckedIds()
	{
		return isCheckedIds;
	}

	public void setIsCheckedIds(String isCheckedIds )
	{
		this.isCheckedIds = isCheckedIds;
	}
	/**  
	* 函数功能说明 TODO:查询所有用户
	* Administrator修改者名字
	* 2013-5-29修改日期
	* 修改内容
	* @Title: findAllUserList 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/findAllUserList")
	public String findAllUserList() throws Exception
	{
		Map<String, Object> map=new HashMap<String, Object>();
		if (null!=searchValue&&!"".equals(searchValue))
		{
			map.put(searchName, Constants.GET_SQL_LIKE+searchValue+Constants.GET_SQL_LIKE);
		}
		PageUtil pageUtil=new PageUtil(page, rows, searchAnds, searchColumnNames, searchConditions, searchVals);
		GridModel gridModel=new GridModel();
		gridModel.setRows(userService.findAllUserList(map,pageUtil));
		gridModel.setTotal(userService.getCount(map,pageUtil));
		OutputJson(gridModel);
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:持久化用户
	* Administrator修改者名字
	* 2013-5-29修改日期
	* 修改内容
	* @Title: persistenceUsers 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/persistenceUsers")
	public String persistenceUsers() throws Exception
	{
		Map<String, List<Users>> map=new HashMap<String, List<Users>>();
		map.put("addList", JSON.parseArray(inserted, Users.class));
		map.put("updList", JSON.parseArray(updated, Users.class));
		map.put("delList", JSON.parseArray(deleted, Users.class));
		Json json=new Json();
		if (userService.persistenceUsers(map)) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		}else {
			json.setMessage("提交失败了！");
		}
		OutputJson(json);
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:持久化用户弹窗模式
	* Administrator修改者名字
	* 2013-6-14修改日期
	* 修改内容
	* @Title: persistenceUsersDig 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/persistenceUsersDig")
	public String persistenceUsersDig() throws Exception
	{
		OutputJson(getMessage(userService.persistenceUsers(getModel())),Constants.TEXT_TYPE_PLAIN);
		return null;
	}
	
	/**  
	* 函数功能说明 TODO:删除users
	* Administrator修改者名字
	* 2013-6-14修改日期
	* 修改内容
	* @Title: delUsers 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/delUsers")
	public String delUsers() throws Exception
	{
		OutputJson(getMessage(userService.delUsers(getModel().getUserId())));
		return null;
	}
	/**  
	* 函数功能说明 TODO:查询用户拥有角色
	* Administrator修改者名字
	* 2013-5-29修改日期
	* 修改内容
	* @Title: findUsersRolesList 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/findUsersRolesList")
	public String findUsersRolesList() throws Exception
	{
		OutputJson(userService.findUsersRolesList(getModel().getUserId()));
		return null;
	}
	/**  
	* 函数功能说明 TODO:保存用户分配的角色
	* Administrator修改者名字
	* 2013-5-29修改日期
	* 修改内容
	* @Title: saveUserRoles 
	* @Description: 
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/saveUserRoles")
	public String saveUserRoles() throws Exception
	{
		Json json=new Json();
		if (userService.saveUserRoles(getModel().getUserId(), isCheckedIds)) {
			json.setStatus(true);
			json.setMessage("数据更新成功！");
		}else {
			json.setMessage("提交失败了！");
		}
		OutputJson(json);
		return null;
	}

	public Users getModel()
	{ 
		if (null==users)
		{
			users =new Users();
		}
		return users;
	}
}
