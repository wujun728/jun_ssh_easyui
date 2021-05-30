package com.lostad.app.system.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lostad.app.common.entity.BaseEntity;

/**
 * SysMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_resource")
public class Menu extends BaseEntity{
	// Fields
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id",columnDefinition="varchar(32) COMMENT '上级菜单，菜单类型有效 '")
	private Menu parent;
	@Column(columnDefinition="varchar(32) COMMENT '菜单名称'")
	private String name;
	@Column(columnDefinition="varchar(64) COMMENT '菜单链接'")
	private String href;
	private String target;
	@Column(columnDefinition="varchar(32) COMMENT '目标mainFrame _blank _self _parent _top'")
	private String icon;
	@Column(columnDefinition="varchar(32) COMMENT '权限 '")
	private String permission;
	@Column(columnDefinition="int COMMENT '排序字段'")
	private Integer sort;
	@Column(columnDefinition="char(1) COMMENT ' 是否在菜单中显示（1：显示；0：不显示）'")
	private String isShow; 	//
	@Column(columnDefinition="varchar(64) COMMENT ' 所有上级菜单 '")
	private String parentIds; 	//
	
	/** default constructor */
	public Menu(){
		super();
		this.sort = 20;
		this.isShow = "1";
	}
	
	
	public String getParentIds() {
		return parentIds;
	}


	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}


	public Menu(String id){
		this.id = id;
	}
	public Menu getParent() {
		return parent;
	}
	public void setParent(Menu parent) {
		this.parent = parent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getPermission() {
		return permission;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getParentId() {
		return parent != null && parent.getId() != null ? parent.getId() : "0";
	}

	

	@JsonIgnore
	public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade){
		for (int i=0; i<sourcelist.size(); i++){
			Menu e = sourcelist.get(i);
			if (e.getParent()!=null && e.getParent().getId()!=null
					&& e.getParent().getId().equals(parentId)){
				list.add(e);
				if (cascade){
					// 判断是否还有子节点, 有则继续获取子节点
					for (int j=0; j<sourcelist.size(); j++){
						Menu child = sourcelist.get(j);
						if (child.getParent()!=null && child.getParent().getId()!=null
								&& child.getParent().getId().equals(e.getId())){
							sortList(list, sourcelist, e.getId(), true);
							break;
						}
					}
				}
			}
		}
	}

	@JsonIgnore
	public static String getRootId(){
		return "1";
	}
}