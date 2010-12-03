package com.ztesoft.component.file.business;

public class HandlerMsg {
	//������ true �ɹ����� ,false ����ʧ��
	private boolean result = false ;
	//������ʾ��Ϣ
	private String tipMsg = "" ;
	
	//��¼����
	private String recordName = "" ;
	//��¼���
	private String recordCode = "" ;
	//��¼��������
	private long lineNumber = 0L ;
	
	
	public HandlerMsg(){
		
	}
	
	public HandlerMsg(boolean result ,String tipMsg  ,  String recordName,String recordCode , long lineNumber  ){
		this.result = result ;
		this.tipMsg = tipMsg ;
		this.recordName = recordName ;
		this.recordCode = recordCode ;
		this.lineNumber = lineNumber ;
	}

	public long getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(long lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getRecordCode() {
		return recordCode;
	}

	public void setRecordCode(String recordCode) {
		this.recordCode = recordCode;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getTipMsg() {
		return tipMsg;
	}

	public void setTipMsg(String tipMsg) {
		this.tipMsg = tipMsg;
	}
	
	
	
}
