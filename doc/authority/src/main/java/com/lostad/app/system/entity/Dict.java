package com.lostad.app.system.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lostad.app.common.entity.BaseEntity;

/**
 * SysDict entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_dict")
public class Dict  extends BaseEntity {
	// Fields
	private String parentId;
	private String value;
	private String label;
	private String code;
	private String type;
	private Long sort;
	// Constructors
	/** default constructor */
	public Dict() {
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Long getSort() {
		return sort;
	}
	public void setSort(Long sort) {
		this.sort = sort;
	}


}