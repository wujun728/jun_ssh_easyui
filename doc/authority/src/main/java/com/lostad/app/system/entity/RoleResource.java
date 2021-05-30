package com.lostad.app.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.lostad.app.common.entity.BaseEntity;

/**
 * 医生角色表
 * HisRoleDoctor entity.
 * @author  sszvip
 */
@Entity
@Table(name = "sys_role_resource")
public class RoleResource  {
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")      
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")  
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "resource_id")
	private Menu menu;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id")
	private Role role;
	
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RoleResource(Menu menu, Role r) {
		this.menu = menu;
		this.role = r;
	}
	
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}
	/**
	 * @param role the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	

}