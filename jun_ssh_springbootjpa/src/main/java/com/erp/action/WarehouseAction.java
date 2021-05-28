package com.erp.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.erp.model.Warehouse;
import com.erp.service.WarehouseService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/warehouse")
public class WarehouseAction extends BaseAction 
{
	private static final long serialVersionUID = -4202679640252934032L;
	private Warehouse warehouse;
	private WarehouseService warehouseService;
	@Autowired
	public void setWarehouseService(WarehouseService warehouseService )
	{
		this.warehouseService = warehouseService;
	}

	public Warehouse getWarehouse()
	{
		return warehouse;
	}

	public void setWarehouse(Warehouse warehouse )
	{
		this.warehouse = warehouse;
	}
	
	/**  
	* 函数功能说明
	* Administrator修改者名字
	* 2013-7-1修改日期
	* 修改内容
	* @Title: findWarehouseListCombobox 
	* @Description: TODO:查询所有仓库下拉框格式
	* @param @return
	* @param @throws Exception    设定文件 
	* @return String    返回类型 
	* @throws 
	*/
	@ResponseBody
	@GetMapping(value = "/findWarehouseListCombobox")
	public String findWarehouseListCombobox() throws Exception
	{
		OutputJson(warehouseService.findWarehouseListCombobox());
		return null;
	}

	public Warehouse getModel()
	{
		if (null==warehouse)
		{
			warehouse=new Warehouse();
		}
		return warehouse;
	}

}
