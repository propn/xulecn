package com.ztesoft.vsop;

public class SpProductVo {
	private String serviceId ="";
	private String serviceCapabilityId ="";
	private String state ="";
	private String spState ="";
	private String nameCn = "";
	private String productOfferId="";
	private String productOfferType = "";
	private String packageId="";
	private String type="";
	private String productNo="";
	private String newProductOfferId = "";
	private String newProductOfferType = "";
	private String productNbr = "";//产品外编码，20101009号新增，rule12 鉴权时用到

	public String getServiceId(){
		return serviceId;
	}
	
	public String getServiceCapabilityId(){
		return serviceCapabilityId;
	}
	
	public String getState(){
		return state;
	}
	
	public String getSpState(){
		return spState;
	}
	
	public String getNameCn(){
		return nameCn;
	}

	public String getProductOfferId(){
		return productOfferId;
	}	
	
	public String getPackageId(){
		return packageId;
	}
	
	public String getType(){
		return type;
	}
	
	public String getProductNo(){
		return productNo;
	}
	
	public void setServiceId(String serviceId){
		this.serviceId = serviceId;
	}
	
	public void setServiceCapabilityId(String serviceCapabilityId){
		this.serviceCapabilityId = serviceCapabilityId;
	}
	
	public void setState(String state){
		this.state = state;
	}
	
	public void setSpState(String spState){
		this.spState = spState;
	}
	
	public void setNameCn(String nameCn){
		this.nameCn = nameCn;
	}

	public void setProductOfferId(String productOfferId){
		this.productOfferId = productOfferId;
	}	
	
	public void setPackageId(String packageId){
		this.packageId = packageId;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void setProductNo(String productNo){
		this.productNo = productNo;
	}

	public String getNewProductOfferId() {
		return newProductOfferId;
	}

	public void setNewProductOfferId(String newProductOfferId) {
		this.newProductOfferId = newProductOfferId;
	}

	public String getNewProductOfferType() {
		return newProductOfferType;
	}

	public void setNewProductOfferType(String newProductOfferType) {
		this.newProductOfferType = newProductOfferType;
	}

	public String getProductOfferType() {
		return productOfferType;
	}

	public void setProductOfferType(String productOfferType) {
		this.productOfferType = productOfferType;
	}

	public String getProductNbr() {
		return productNbr;
	}

	public void setProductNbr(String productNbr) {
		this.productNbr = productNbr;
	}
	
	
}
