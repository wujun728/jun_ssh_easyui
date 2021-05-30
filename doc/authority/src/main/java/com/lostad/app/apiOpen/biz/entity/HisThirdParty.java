package com.lostad.app.apiOpen.biz.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author liangxy
 */
@Entity
@Table(name = "his_third_party")
public class HisThirdParty implements java.io.Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 主键id
	 */
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id", nullable = false)
//	private Long id;
	@Id
	@Column(name = "id", nullable = false, length = 32)
	private String id; // 用户的主键不再自动生成
	/** 第三方应用名称 */
	@Column(name = "app_name", nullable = true, length = 16,unique=true)
	private String appName;	
	@Column(columnDefinition=("varchar(128) comment 'sk：MD5数字签名的字符串,可双方约定' "))
	private String secretKey;	
	
	/** 第三方RSA公钥 ,用于验证签名*/
	@Column(name = "pub_key", nullable = true, length = 128)
	private String pubKey;
	
	/** AES数据加密的key */
	@Column(name = "aes_key", nullable = true, length = 16)
	private String aesKey;	
	
	/** 备注*/
	@Column(name = "remark", nullable = true, length = 50)
	private String remark;
	/** 创建时间 */
	@Column(name = "create_date", nullable = true,updatable=false)
	private Date createDate;
	/** 创建人ID */
	@Column(name = "create_by", nullable = true, length = 32,updatable=false)
	private String createBy;
	/** 修改时间 */
	@Column(name = "update_date", nullable = true)
	private Date updateDate;
	/** 修改人 */
	@Column(name = "update_by", nullable = true, length = 32)
	private String updateBy;
	/** 删除标识 */
	@Column(name = "deleted", nullable = true, columnDefinition = "tinyint(1) default 0")
	private Integer deleted = 0;
	
	
	/**
	 * 方法：取得 String
	 * 
	 * @return: String id
	 */
	public String getId() {
		return id;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String id
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the secretKey
	 */
	public String getSecretKey() {
		return secretKey;
	}
	/**
	 * @param secretKey the secretKey to set
	 */
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String appName
	 */
	public String getAppName() {
		return appName;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String appName
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String pubKey
	 */
	public String getPubKey() {
		return pubKey;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String pubKey
	 */
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String aesKey
	 */
	public String getAesKey() {
		return aesKey;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String aesKey
	 */
	public void setAesKey(String aesKey) {
		this.aesKey = aesKey;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String remark
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String remark
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 方法：取得 Date
	 * 
	 * @return: Date createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * 方法：设置Date
	 * 
	 * @param: Date createDate
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String createBy
	 */
	public String getCreateBy() {
		return createBy;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String createBy
	 */
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	/**
	 * 方法：取得 Date
	 * 
	 * @return: Date updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * 方法：设置Date
	 * 
	 * @param: Date updateDate
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * 方法：取得 String
	 * 
	 * @return: String updateBy
	 */
	public String getUpdateBy() {
		return updateBy;
	}
	/**
	 * 方法：设置String
	 * 
	 * @param: String updateBy
	 */
	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}
	/**
	 * 方法：取得 Integer
	 * 
	 * @return: Integer deleted
	 */
	public Integer getDeleted() {
		return deleted;
	}
	/**
	 * 方法：设置Integer
	 * 
	 * @param: Integer deleted
	 */
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	
	
	
}
