package com.lostad.app.common.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
@MappedSuperclass
public abstract class BaseEntity implements Serializable{
	@Id
	@GeneratedValue(generator = "paymentableGenerator")      
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")  
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String id;

	public String remarks;	//ע
	public String createBy;	// 
	@Column(columnDefinition = "datetime default 0 comment '创建日期' ")
	public Date createDate;	// 
	public String updateBy;	// 
	@Column(columnDefinition = "datetime default 0 comment '更新日期' ")
	public Date updateDate;	// 
	@Column(nullable=false,columnDefinition="tinyint default 0 comment '删除标识0：未删，1：已删'")
	public Integer delFlag; 	// 
	
	public BaseEntity() {
		super();
	}
	
	public String getId() {
		return this.id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public void setId(String id) {
		this.id = id;
	}

	
}
