package com.ecpss.mp.uc.service;

import java.util.List;

public class Sysmenu {
	private String id;
	private String menuid;
	private String menuno;
	private String text;
	private String menuname;
	private String menuparentno;
	private String menuurl;
	private String icon;
	private String menuicon;
	private Integer permissions;
	private String permissionobject;
	private List<Sysmenu> children;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMenuid() {
		return menuid;
	}
	public void setMenuid(String menuid) {
		this.menuid = menuid;
	}
	public String getMenuno() {
		return menuno;
	}
	public void setMenuno(String menuno) {
		this.menuno = menuno;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getMenuname() {
		return menuname;
	}
	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}
	public String getMenuparentno() {
		return menuparentno;
	}
	public void setMenuparentno(String menuparentno) {
		this.menuparentno = menuparentno;
	}
	public String getMenuurl() {
		return menuurl;
	}
	public void setMenuurl(String menuurl) {
		this.menuurl = menuurl;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getMenuicon() {
		return menuicon;
	}
	public void setMenuicon(String menuicon) {
		this.menuicon = menuicon;
	}
	public Integer getPermissions() {
		return permissions;
	}
	public void setPermissions(Integer permissions) {
		this.permissions = permissions;
	}
	public String getPermissionobject() {
		return permissionobject;
	}
	public void setPermissionobject(String permissionobject) {
		this.permissionobject = permissionobject;
	}
	public List<Sysmenu> getChildren() {
		return children;
	}
	public void setChildren(List<Sysmenu> children) {
		this.children = children;
	}	
}
