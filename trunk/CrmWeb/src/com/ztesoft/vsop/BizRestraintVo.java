package com.ztesoft.vsop;

public class BizRestraintVo {
	private String aObjectId ="";
	private String aObjectName ="";
	private String zObjectId ="";
	private String zObjectName ="";
	private String restraintType ="";
	
	public String getaObjectId(){
		return aObjectId;
	}
	
	public String getzObjectId(){
		return zObjectId;
	}

	public String getaObjectName(){
		return aObjectName;
	}
	
	public String getzObjectName(){
		return zObjectName;
	}	
	
	public String getRestraintType(){
		return restraintType;
	}
	
	public void setaObjectId(String aObjectId){
		this.aObjectId = aObjectId;
	}
	
	public void setzObjectId(String zObjectId){
		this.zObjectId = zObjectId;
	}
	
	public void setaObjectName(String aObjectName){
		this.aObjectName = aObjectName;
	}
	
	public void setzObjectName(String zObjectName){
		this.zObjectName = zObjectName;		
	}
	
	public void setRestraintType(String restraintType){
		this.restraintType = restraintType;
	}	
}
