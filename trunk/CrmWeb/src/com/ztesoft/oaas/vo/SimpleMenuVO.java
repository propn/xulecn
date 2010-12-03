package com.ztesoft.oaas.vo;

import java.io.Serializable;

public class SimpleMenuVO  implements Serializable{
	private String menuId = "";//菜单ID
	private String menuCode = "";//菜单编码
	private String menuName = "";//菜单名称
	private String targetName = "";//菜单连接
	private String param = ""; //连接参数
	public String getMenuCode() {
		return menuCode;
	}
	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}
	public String getMenuId() {
		return menuId;
	}
	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	
}
