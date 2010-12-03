package com.ztesoft.component.ui.taglib;

public class ParameterVo {
	private String parameterId;
	private String type;
	private String value;
	
	public String getParameterId() {
		return (parameterId!=null ? parameterId : "");
	}
	public void setParameterId(String parameterId) {
		this.parameterId = parameterId;
	}
	public String getType() {
		return (type!=null ? type : "");
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return (value!=null ? value : "");
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ParameterVo(String parameterId, String type, String value) {
		super();
		// TODO Auto-generated constructor stub 
		this.parameterId = parameterId;
		this.type = type;
		this.value = value;
	}
	public String toString(){
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append(" var __p = __t.parameters(); ");
		sbuffer.append(" __p.setDataType('"+this.getParameterId()+"', '"+this.getType()+"'); ");
		sbuffer.append(" __p.setValue('"+this.getParameterId()+"', '"+this.getValue()+"'); ");
		return sbuffer.toString();		
	}
	
	
	/*
	public String toString(){
		StringBuffer sbuffer = new StringBuffer();
		
		sbuffer.append("<dt extra='parameter' parameterId='"+this.getParameterId()+"' type='"+this.getType()+"' value='"+this.getValue()+"'></dt>");
		
		return sbuffer.toString();		
	}
	*/		
}