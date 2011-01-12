package com.powerise.ibss.framedata.vo;

import java.util.List;

public class SQLArgs {
	private String sql = "" ;
	private String actionType = "" ;
	private List args = null ;
	
	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public SQLArgs(){
		
	}
	
	public SQLArgs(String sql , List args , String actionType ){
		this.sql = sql ; 
		this.args = args ;
		this.actionType = actionType ;
	}
	
	
	public List getArgs() {
		return args;
	}
	public void setArgs(List args) {
		this.args = args;
	}
	public String getSQL() {
		return sql;
	}
	public void setSQL(String execSQL) {
		this.sql = execSQL;
	}
	
	

}
