package com.ztesoft.crm.business.common.model;

public class Hint {
	private String code	 =null;//	提示代码：要求包含报错的类名，方法名和规则编码
	private String msg	= null;//	错误描述
	private String detail_msg	= null;//	详细错误描述
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
