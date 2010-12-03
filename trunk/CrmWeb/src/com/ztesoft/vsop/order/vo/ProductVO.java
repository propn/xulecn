package com.ztesoft.vsop.order.vo;

import java.util.ArrayList;

public class ProductVO {
	String streamingNo="";
	String OPFlag="";
	String ProductID="";
	String ProductName="";
	String Status="";
	String chargingPolicyCN="";
	String chargingPolicyID="";
	String subOption="";
	String present="";
	String corpOnly="";
	String packageOnly="";
	String productOPCode="";
	String settlementCycle="";
	String settlementPayType="";
	String settlementPercent="";
	String Scope="";
	String ProductHost="";
	String SPID="";
	String PnameCN="";
	String PnameEN="";
	String PdescriptionCN="";
	String PdescriptionEn="";
	ArrayList serviceAblityLst = new ArrayList();
	ArrayList productOPCodeLst = new ArrayList();
	ArrayList PlatFormLst = new ArrayList();
	ArrayList ProdRelationLst = new ArrayList();
	
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
	public String getPackageOnly() {
		return packageOnly;
	}
	public void setPackageOnly(String packageOnly) {
		this.packageOnly = packageOnly;
	}
	public String getPdescriptionCN() {
		return PdescriptionCN;
	}
	public void setPdescriptionCN(String pdescriptionCN) {
		PdescriptionCN = pdescriptionCN;
	}
	public String getPdescriptionEn() {
		return PdescriptionEn;
	}
	public void setPdescriptionEn(String pdescriptionEn) {
		PdescriptionEn = pdescriptionEn;
	}
	public ArrayList getPlatFormLst() {
		return PlatFormLst;
	}
	public void setPlatFormLst(ArrayList platFormLst) {
		PlatFormLst = platFormLst;
	}
	public String getPnameCN() {
		return PnameCN;
	}
	public void setPnameCN(String pnameCN) {
		PnameCN = pnameCN;
	}
	public String getPnameEN() {
		return PnameEN;
	}
	public void setPnameEN(String pnameEN) {
		PnameEN = pnameEN;
	}
	public String getPresent() {
		return present;
	}
	public void setPresent(String present) {
		this.present = present;
	}
	public ArrayList getProdRelationLst() {
		return ProdRelationLst;
	}
	public void setProdRelationLst(ArrayList prodRelationLst) {
		ProdRelationLst = prodRelationLst;
	}
	public String getProductHost() {
		return ProductHost;
	}
	public void setProductHost(String productHost) {
		ProductHost = productHost;
	}
	public String getProductID() {
		return ProductID;
	}
	public void setProductID(String productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getProductOPCode() {
		return productOPCode;
	}
	public void setProductOPCode(String productOPCode) {
		this.productOPCode = productOPCode;
	}
	public ArrayList getProductOPCodeLst() {
		return productOPCodeLst;
	}
	public void setProductOPCodeLst(ArrayList productOPCodeLst) {
		this.productOPCodeLst = productOPCodeLst;
	}
	public String getScope() {
		return Scope;
	}
	public void setScope(String scope) {
		Scope = scope;
	}
	public ArrayList getServiceAblityLst() {
		return serviceAblityLst;
	}
	public void setServiceAblityLst(ArrayList serviceAblityLst) {
		this.serviceAblityLst = serviceAblityLst;
	}
	public String getSettlementCycle() {
		return settlementCycle;
	}
	public void setSettlementCycle(String settlementCycle) {
		this.settlementCycle = settlementCycle;
	}
	public String getSettlementPayType() {
		return settlementPayType;
	}
	public void setSettlementPayType(String settlementPayType) {
		this.settlementPayType = settlementPayType;
	}
	public String getSettlementPercent() {
		return settlementPercent;
	}
	public void setSettlementPercent(String settlementPercent) {
		this.settlementPercent = settlementPercent;
	}
	public String getSPID() {
		return SPID;
	}
	public void setSPID(String spid) {
		SPID = spid;
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
	
}
