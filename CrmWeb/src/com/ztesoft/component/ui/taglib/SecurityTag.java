package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class SecurityTag extends TagSupport {
	
	private String privilegeCode;
	private String controlId;
	private String controlType;
	private String desc;
	
	public String getControlId() {
		return controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	public String getControlType() {
		return (controlType!=null ? controlType : "dsEnable");
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getPrivilegeCode() {
		return privilegeCode;
	}
	public void setPrivilegeCode(String privilegeCode) {
		this.privilegeCode = privilegeCode;
	}
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		if(this.getPrivilegeCode()!=null && !"".equalsIgnoreCase(this.getPrivilegeCode())
		&& this.getControlId()!=null && !"".equalsIgnoreCase(this.getControlId())){	
			Tag permission = this.getParent();
			if(permission!=null){
				PermissionTag perm = (PermissionTag)permission;
				perm.setSecurity(new SecurityVo(this.getPrivilegeCode(),this.getControlId(),this.getControlType(),this.getDesc()));
			}
		}
		return super.doEndTag();
	}
	public void release() {
		// TODO Auto-generated method stub
		super.release();
		
		this.privilegeCode = null;
		this.controlId = null;
		this.controlType = null;
		this.desc = null;
	}
	
	
	
}
