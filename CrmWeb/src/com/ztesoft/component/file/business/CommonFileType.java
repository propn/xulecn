package com.ztesoft.component.file.business;

public class CommonFileType {
//	processType/command/store/ftp/moduleId
	private static final String processType = "CommonFile"; 
	
	private String command = null ;
	private String tempDir = "/" ;//��ʱĿ¼
	private String store = null ;//db ,dir
	
	private String ftp = null ;//���ݿ�ftp������ϢID
	
	private String moduleId = null ;//����ģ��/ҳ��ID

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
