package com.ztesoft.component.ui.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

public class ParameterTag extends TagSupport {
	
	private String parameterId;
	private String type;
	private String value;
	
	public String getParameterId() {
		return parameterId;
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public int doEndTag() throws JspException {
		// TODO Auto-generated method stub
		
		Tag dataset = this.getParent();
		if(dataset!=null){
			((DatasetTag)dataset).setParameter(
				new	ParameterVo(this.getParameterId(), 
						this.getType(), 
						this.getValue())
			);			
		}
		
		return super.doEndTag();
	}
	public void release() {
		// TODO Auto-generated method stub
		super.release();	
		
		this.parameterId = null;
		this.type = null;
		this.value = null;	
	}
	
	
}
