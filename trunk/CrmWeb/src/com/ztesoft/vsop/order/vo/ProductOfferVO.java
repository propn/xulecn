package com.ztesoft.vsop.order.vo;

import java.util.ArrayList;

public class ProductOfferVO {
	String streamingNo="";
	String OPFlag="";
	String PackageID="";
	String PNameCN="";
	String PNameEN="";
	String PDesCN="";
	String PDesEn="";
	String Status="";
	String chargingPolicyCN="";
	String chargingPolicyID="";
	String subOption="";
	String presentOption="";
	String corpOnly="";
	String productID="";
	String Scope="";
	String PackageHost="";
	String ProdOfferRelation="";
	String feeSetFlag = "";
	String prodOfferSubType = "";
	
	ArrayList productLst = new ArrayList();
	ArrayList ProdOfferRelationLst = new ArrayList();
	
	public String getChargingPolicyCN() {
		return chargingPolicyCN;
	}
	public void setChargingPolicyCN(String chargingPolicyCN) {
		this.chargingPolicyCN = chargingPolicyCN;
	}
	public String getChargingPolicyID() {
		return chargingPolicyID;
	}
	public void setChargingPolicyID(String chargingPolicyID) {
		this.chargingPolicyID = chargingPolicyID;
	}
	public String getCorpOnly() {
		return corpOnly;
	}
	public void setCorpOnly(String corpOnly) {
		this.corpOnly = corpOnly;
	}
	public String getOPFlag() {
		return OPFlag;
	}
	public void setOPFlag(String flag) {
		OPFlag = flag;
	}
	public String getPackageHost() {
		return PackageHost;
	}
	public void setPackageHost(String packageHost) {
		PackageHost = packageHost;
	}
	public String getPackageID() {
		return PackageID;
	}
	public void setPackageID(String packageID) {
		PackageID = packageID;
	}
	public String getPDesCN() {
		return PDesCN;
	}
	public void setPDesCN(String desCN) {
		PDesCN = desCN;
	}
	public String getPDesEn() {
		return PDesEn;
	}
	public void setPDesEn(String desEn) {
		PDesEn = desEn;
	}
	public String getPNameCN() {
		return PNameCN;
	}
	public void setPNameCN(String nameCN) {
		PNameCN = nameCN;
	}
	public String getPNameEN() {
		return PNameEN;
	}
	public void setPNameEN(String nameEN) {
		PNameEN = nameEN;
	}
	public String getPresentOption() {
		return presentOption;
	}
	public void setPresentOption(String presentOption) {
		this.presentOption = presentOption;
	}
	public String getProdOfferRelation() {
		return ProdOfferRelation;
	}
	public void setProdOfferRelation(String prodOfferRelation) {
		ProdOfferRelation = prodOfferRelation;
	}
	public ArrayList getProdOfferRelationLst() {
		return ProdOfferRelationLst;
	}
	public void setProdOfferRelationLst(ArrayList prodOfferRelationLst) {
		ProdOfferRelationLst = prodOfferRelationLst;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String productID) {
		this.productID = productID;
	}
	public ArrayList getProductLst() {
		return productLst;
	}
	public void setProductLst(ArrayList productLst) {
		this.productLst = productLst;
	}
	public String getScope() {
		return Scope;
	}
	public void setScope(String scope) {
		Scope = scope;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getStreamingNo() {
		return streamingNo;
	}
	public void setStreamingNo(String streamingNo) {
		this.streamingNo = streamingNo;
	}
	public String getSubOption() {
		return subOption;
	}
	public void setSubOption(String subOption) {
		this.subOption = subOption;
	}
	public String getFeeSetFlag() {
		return feeSetFlag;
	}
	public void setFeeSetFlag(String feeSetFlag) {
		this.feeSetFlag = feeSetFlag;
	}
	public String getProdOfferSubType() {
		return prodOfferSubType;
	}
	public void setProdOfferSubType(String prodOfferSubType) {
		this.prodOfferSubType = prodOfferSubType;
	}
}
