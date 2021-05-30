package com.lostad.app.common.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * $.ajax后需要接受的JSON 一体机接口返回值
 * 
 * @author sszvip
 * @param <T>
 * 
 */
public class JsonRe<T> {
	@ApiModelProperty(value="错误码0:正常，1：异常",required=true)   
	private int error = 0;// 是(0),否(1),没有结果集(2) ,凭证不正常（-1）
	@ApiModelProperty(value="提示信息",required=true)   
	private String msg = "操作成功";// 提示信息
	
	@ApiModelProperty(value="业务数据:可能是对象或数组或null",required=false)   
	private T obj = null;// 其他信息
	
	public JsonRe() {
	}

	public JsonRe(int error, String msg, T obj) {
		this.error = error;
		this.msg = msg;
		this.obj = obj;
	}

	/**
	 * @return the error
	 */
	public int getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(int error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObj() {
		return obj;
	}

	public void setObj(T obj) {
		this.obj = obj;
	}

}
