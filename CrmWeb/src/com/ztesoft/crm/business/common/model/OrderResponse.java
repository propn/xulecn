package com.ztesoft.crm.business.common.model;

public class OrderResponse {
	private String result=null;//������:0 �C �ɹ� 1 �C ��ʾ 2 �C ʧ��	
	
	private 	Error errors =null;//	������Ϣ	��
	private 	Hint  hints = null;//	��ʾ��Ϣ	��
	public Error getErrors() {
		return errors;
	}
	public void setErrors(Error errors) {
		this.errors = errors;
	}
	public Hint getHints() {
		return hints;
	}
	public void setHints(Hint hints) {
		this.hints = hints;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}

}
