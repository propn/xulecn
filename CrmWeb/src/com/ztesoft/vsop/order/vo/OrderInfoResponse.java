package com.ztesoft.vsop.order.vo;

import java.io.Serializable;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.ztesoft.vsop.order.XMLUtils;

public class OrderInfoResponse implements Serializable{
	private String orderId;
	private String resultCode;
	private String resultMessage;
	
	private static final String ROOT_NODE_NAME = "ResultInfo";
	public static final String RESULT_SUCCESS_CODE = "0"; 
	public static final String RESULT_ERROR_CODE9 = "9";
	public static final String RESULT_ERROR_CODE8 = "8";
	public static final String RESULT_ERROR_CODE10 = "10";
	public static final String SUCCESS = "success";
	
	public OrderInfoResponse() {
		super();
	}
	public OrderInfoResponse(String resultCode,String orderId) {
		super();
		setOrderId(orderId);
		setResultCode(resultCode);
	}
	public OrderInfoResponse(String orderId){
		setOrderId(orderId);
	}
	public OrderInfoResponse(Exception ex,String orderId) {
		super();
		setOrderId(orderId);
		if (ex instanceof NullPointerException) {
			setResultCode(RESULT_ERROR_CODE9);
			setResultMessage("Èë²ÎÎª¿Õ!");
		}else if (ex instanceof DocumentException) {
			setResultCode(RESULT_ERROR_CODE8);
			setResultMessage(ex.getMessage());
		}
		
		else if (ex instanceof Exception) {
			setResultCode(RESULT_ERROR_CODE10);
			setResultMessage(ex.getMessage());
		}
			
	}
	public OrderInfoResponse success(){
		setResultCode(RESULT_SUCCESS_CODE);
		setResultMessage(SUCCESS);
		return this;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getResultMessage() {
		return resultMessage;
	}
	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}
	
	public String toXML() {
		StringBuffer bf = new StringBuffer();
		bf.append("<").append(ROOT_NODE_NAME).append(">")
		.append("<orderId>").append(getOrderId()).append("</orderId>")
		.append("<resultCode>").append(getResultCode()).append("</resultCode>")
		.append("<resultMessage>").append(getResultMessage()).append("</resultMessage>")
		.append("</").append(ROOT_NODE_NAME).append(">");
		return bf.toString();
	}
	
	public OrderInfoResponse fromXML(String xml) throws DocumentException{
		Document doc = XMLUtils.parse(xml);
		Element root = doc.getRootElement();
		setOrderId(XMLUtils.getElementStringValue(root,"orderId"));
		setResultCode(XMLUtils.getElementStringValue(root,"resultCode"));
		setResultMessage(XMLUtils.getElementStringValue(root,"resultMessage"));
		return this;
	}
}
