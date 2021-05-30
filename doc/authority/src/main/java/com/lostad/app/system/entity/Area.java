package com.lostad.app.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lostad.app.common.entity.BaseEntity;

/**
 * SysArea entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_area")
public class Area extends BaseEntity {
	// Fields
	private String parentId;
	private String name;
	private Integer sort;
	private String code;
	private String type;

	// Constructors

	/** default constructor */
	public Area() {
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	

}