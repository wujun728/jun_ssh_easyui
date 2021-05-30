package com.lostad.app.common.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class IdAutoEntity {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false,columnDefinition=("bigint(11) comment '主键' "))
	private Long id;

	/** 删除标识 */
	@Column(nullable = true, columnDefinition = "tinyint(1) default 0 comment '是否已删除0/1' ")
	private Integer delFlag = 0;
	@Column(columnDefinition = "datetime default 0 comment '创建日期' ")
	public Date createDate;	// 
	/**
	 * 方法：取得 int
	 * 
	 * @return: int 主键id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * 方法：设置int
	 * 
	 * @param: int 主键id
	 */
	public void setId(Long id) {
		this.id = id;
	}


	

	/**
	 * @return the delFlag
	 */
	public Integer getDelFlag() {
		return delFlag;
	}

	/**
	 * @param delFlag the delFlag to set
	 */
	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


}
