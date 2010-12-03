package com.ztesoft.crm.business.common.model;

public class OrderResponse {
	private String result=null;//处理结果:0 – 成功 1 – 提示 2 – 失败	
	
	private 	Error errors =null;//	错误信息	否
	private 	Hint  hints = null;//	提示信息	否
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
