package com.lostad.app.system.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lostad.app.common.entity.BaseEntity;

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity{
	// Fields
	@Column(columnDefinition="varchar(32) COMMENT ' 角色名称'")
	private String name;
	@Column(columnDefinition="varchar(32) COMMENT ' 角色编码'")
	private String roleCode;
	@Column(columnDefinition="varchar(32) COMMENT ' 数据权限'")
	private String dataScope;
	@Column(columnDefinition="char(32) COMMENT ' 是否系统角色1是0否'")
	private String isSys;

	
	// Constructors
	/** default constructor */
	public Role() {
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDataScope() {
		return dataScope;
	}

	public void setDataScope(String dataScope) {
		this.dataScope = dataScope;
	}

	public String getIsSys() {
		return isSys;
	}

	public void setIsSys(String isSys) {
		this.isSys = isSys;
	}


	
}