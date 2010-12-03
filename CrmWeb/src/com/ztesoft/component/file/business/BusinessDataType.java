package com.ztesoft.component.file.business;

public class BusinessDataType {
//	processType/fileType/command/moduleId
	private static final String processType = "BusinessData" ; 
	
	public static final String TXT = "TXT" ; 
	public static final String EXCEL = "EXCEL" ; 
	
	
	private String fileType = null ;//txt /excel
	private String command = null ;//处理类
	private String moduleId = null ;//所属模型
	
	public static String getProcessType() {
		return processType;
	}
	
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

}
