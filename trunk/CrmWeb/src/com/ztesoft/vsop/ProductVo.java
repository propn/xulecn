package com.ztesoft.vsop;

import java.util.ArrayList;


public class ProductVo {
	private String productNo ="";
	private String productId ="";
	private String userState ="";
	private String lanId ="";
	private ArrayList spProductInfo = new ArrayList(); //�����Ӧ�Ķ�����ϵʵ���б�
	private ArrayList serviceCapability = new ArrayList(); //�����Ӧ�Ĵ�ͳ������Ʒ��ҵ�������б�
	private String paymentModeCd = "";//���������ֶ�
	private String productSpec = "";//��Ʒ���,��prod_inst�����product_id�ֶ�
	
	public String getProductSpec() {
		return productSpec;
	}

	public void setProductSpec(String productSpec) {
		this.productSpec = productSpec;
	}

	public String getPaymentModeCd() {
		return paymentModeCd;
	}

	public void setPaymentModeCd(String paymentModeCd) {
		this.paymentModeCd = paymentModeCd;
	}
	
	public String getProductNo(){
		return productNo;
	}
	
	public String getProductId(){
		return productId;
	}
	
	public String getUserState(){
		return userState;
	}
	
	public String getLanId(){
		return lanId;
	}
	
	public ArrayList getSpProductInfo(){
		return spProductInfo;
	}
	
	public ArrayList getServiceCapability(){
		return serviceCapability;
	}
	
	public void setProductNo(String productNo){
		this.productNo = productNo;
	}
	
	public void setProductId(String productId){
		this.productId = productId;
	}
	
	public void setUserState(String userState){
		this.userState = userState;
	}
	
	public void setLanId(String lanId){
		this.lanId = lanId;
	}
	
	public void setSpProductInfo(ArrayList spProductInfo){
		this.spProductInfo = spProductInfo;
	}
	
	public void setServiceCapability(ArrayList serviceCapability){
		this.serviceCapability = serviceCapability;
	}
	
}
