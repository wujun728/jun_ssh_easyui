package com.lostad.app.system.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lostad.app.common.entity.BaseEntity;

/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {
	// Fields
	private String deptId;
	private String username;
	private String nickname;
	private String password;
	private String realName;
	private String email;
	private String mobile;
	private String photo;
	private String qrcode;
	private String sign;
	private Date loginDate;
	private String loginIp;
	private String roleCodes;//冗余设置'role1,role2,'
	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	@JsonIgnore
	private java.util.Set<Role> roles;
	private String no;//工号
	private String phone;//电话
	private String loginFlag;//登录标识
	// Constructors

	public String getLoginFlag() {
		return loginFlag;
	}




	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}




	public String getPhone() {
		return phone;
	}




	public void setPhone(String phone) {
		this.phone = phone;
	}




	public String getNo() {
		return no;
	}




	public void setNo(String no) {
		this.no = no;
	}




	/** default constructor */
	public User() {
	}

	
	

	public String getUsername() {
		return username;
	}





	public String getRoleCodes() {
		return roleCodes;
	}




	public void setRoleCodes(String roleCodes) {
		this.roleCodes = roleCodes;
	}




	public void setUsername(String username) {
		this.username = username;
	}




	public String getNickname() {
		return nickname;
	}




	public void setNickname(String nickname) {
		this.nickname = nickname;
	}




	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}


	public String getPassword() {
		return password;
	}

	public java.util.Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(java.util.Set<Role> roles) {
		this.roles = roles;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}


	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	
	// Property accessors
	

}