package com.ztesoft.component.file.business;

public class FileInfo {
	//�ļ�ID
	private String fileId = "" ;
	//�ļ���
	private String fileName = "" ;
	//����
	private String aliasName = "" ;
	//�ļ�����
	private String fileCode = "" ;
	//�ļ�����
	private String fileDesc = "" ;
	//�ļ���С
	private long fileSize = 0 ;
	//�ļ�����
	private String fileExtension = "" ;
	//�洢URI
	private String uri = "" ;
	

	//�ļ�������ģ��(����˵��ҳ��)
	private String modelId = "" ;
	private String modelName = "" ;
	
	//�������ڣ�״̬����Ϣ
	private String createDate = "" ;
	private String createUser = "" ;
	private String updateDate = "" ;
	private String updateUser = "" ;
	private String status = "" ;
	
	
	
	
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getFileCode() {
		return fileCode;
	}
	public void setFileCode(String fileCode) {
		this.fileCode = fileCode;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileType() {
		return fileExtension;
	}
	public void setFileType(String fileType) {
		this.fileExtension = fileType;
	}
	public String getModelId() {
		return modelId;
	}
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	
}
