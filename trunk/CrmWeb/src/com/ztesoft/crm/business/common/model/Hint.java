package com.ztesoft.crm.business.common.model;

public class Hint {
	private String code	 =null;//	��ʾ���룺Ҫ�����������������������͹������
	private String msg	= null;//	��������
	private String detail_msg	= null;//	��ϸ��������
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDetail_msg() {
		return detail_msg;
	}
	public void setDetail_msg(String detail_msg) {
		this.detail_msg = detail_msg;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

}
