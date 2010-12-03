package com.ztesoft.vsop.engine.vo;


import java.util.*;
/**
 * 业务能力实例
 * @author cooltan
 *
 */
public class BizCapabilityInstVO {

	private String servProdId ;
	private String prodId ;
	private String prodInstId ;
	private String lanId ;
	private String state ;
	private String dbActionType;
	public BizCapabilityInstVO() {}

	public BizCapabilityInstVO( String pservProdId, String pprodId, String pprodInstId, String planId, String pstate ) {
		servProdId = pservProdId;
		prodId = pprodId;
		prodInstId = pprodInstId;
		lanId = planId;
		state = pstate;
	}

	public String getServProdId() {
		return servProdId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getState() {
		return state;
	}

	public void setServProdId(String pServProdId) {
		servProdId = pServProdId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setProdInstId(String pProdInstId) {
		prodInstId = pProdInstId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("SERV_PRODUCT_ID",this.servProdId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("PROD_INST_ID",this.prodInstId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("STATE",this.state);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.servProdId = (String) hashMap.get("SERV_PRODUCT_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.prodInstId = (String) hashMap.get("PROD_INST_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.state = (String) hashMap.get("STATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("SERV_PRODUCT_ID");
		return arrayList;
	}

	public String getTableName() {
		return "BIZ_CAPABILITY_INST";
	}

	public String getDbActionType() {
		return dbActionType;
	}

	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}

}
