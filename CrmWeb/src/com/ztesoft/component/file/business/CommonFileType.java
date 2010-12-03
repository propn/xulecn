package com.ztesoft.component.file.business;

public class CommonFileType {
//	processType/command/store/ftp/moduleId
	private static final String processType = "CommonFile"; 
	
	private String command = null ;
	private String tempDir = "/" ;//临时目录
	private String store = null ;//db ,dir
	
	private String ftp = null ;//数据库ftp配置信息ID
	
	private String moduleId = null ;//所属模块/页面ID

	public static String getProcessType() {
		return processType;
	}


	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getFtp() {
		return ftp;
	}

	public void setFtp(String ftp) {
		this.ftp = ftp;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public String getTempDir() {
		return tempDir;
	}

	public void setTempDir(String tempDir) {
		this.tempDir = tempDir;
	}
	
}
