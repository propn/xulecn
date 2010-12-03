package com.ztesoft.vsop.engine.vo;

import java.util.*;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;
/**
 * ¶©¹º¹ØÏµÊµÀý
 * @author cooltan
 *
 */

public class OrderRelationVO extends ValueObject implements VO  {

	private String orderRelationId ;
	private String prodInstId ;
	private String prodId ;
	private String prodOffInstId ;
	private String prodOffId ;
	private String orderChn ;
	private String state ;
	private String effDate ;
	private String expDate ;
	private String packageId ;
	private String lanId ;
	private String stateDate ;
	private String pprodOffId ;
	private String activeState ;
	private String accNbr ;
	private String createDate ;
	private String prodTypeCd ;
	private String dbActionType;
	


	public OrderRelationVO() {}

	public OrderRelationVO( String porderRelationId, String pprodInstId, String pprodId, String pprodOffInstId, String pprodOffId, String porderChn, String pstate, String peffDate, String pexpDate, String ppackageId, String planId, String pstateDate, String ppprodOffId, String pactiveState, String paccNbr, String pcreateDate, String pprodTypeCd ) {
		orderRelationId = porderRelationId;
		prodInstId = pprodInstId;
		prodId = pprodId;
		prodOffInstId = pprodOffInstId;
		prodOffId = pprodOffId;
		orderChn = porderChn;
		state = pstate;
		effDate = peffDate;
		expDate = pexpDate;
		packageId = ppackageId;
		lanId = planId;
		stateDate = pstateDate;
		pprodOffId = ppprodOffId;
		activeState = pactiveState;
		accNbr = paccNbr;
		createDate = pcreateDate;
		prodTypeCd = pprodTypeCd;
	}

	public String getOrderRelationId() {
		return orderRelationId;
	}

	public String getProdInstId() {
		return prodInstId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getProdOffInstId() {
		return prodOffInstId;
	}

	public String getProdOffId() {
		return prodOffId;
	}

	public String getOrderChn() {
		return orderChn;
	}

	public String getState() {
		return state;
	}

	public String getEffDate() {
		return effDate;
	}

	public String getExpDate() {
		return expDate;
	}

	public String getPackageId() {
		return packageId;
	}

	public String getLanId() {
		return lanId;
	}

	public String getStateDate() {
		return stateDate;
	}

	public String getPprodOffId() {
		return pprodOffId;
	}

	public String getActiveState() {
		return activeState;
	}

	public String getAccNbr() {
		return accNbr;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getProdTypeCd() {
		return prodTypeCd;
	}

	public void setOrderRelationId(String pOrderRelationId) {
		orderRelationId = pOrderRelationId;
	}

	public void setProdInstId(String pProdInstId) {
		prodInstId = pProdInstId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setProdOffInstId(String pProdOffInstId) {
		prodOffInstId = pProdOffInstId;
	}

	public void setProdOffId(String pProdOffId) {
		prodOffId = pProdOffId;
	}

	public void setOrderChn(String pOrderChn) {
		orderChn = pOrderChn;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setEffDate(String pEffDate) {
		effDate = pEffDate;
	}

	public void setExpDate(String pExpDate) {
		expDate = pExpDate;
	}

	public void setPackageId(String pPackageId) {
		packageId = pPackageId;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public void setPprodOffId(String pPprodOffId) {
		pprodOffId = pPprodOffId;
	}

	public void setActiveState(String pActiveState) {
		activeState = pActiveState;
	}

	public void setAccNbr(String pAccNbr) {
		accNbr = pAccNbr;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setProdTypeCd(String pProdTypeCd) {
		prodTypeCd = pProdTypeCd;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORDER_RELATION_ID",this.orderRelationId);
		hashMap.put("PROD_INST_ID",this.prodInstId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("PROD_OFFER_INST_ID",this.prodOffInstId);
		hashMap.put("PROD_OFFER_ID",this.prodOffId);
		hashMap.put("ORDER_CHANNEL",this.orderChn);
		hashMap.put("STATE",this.state);
		hashMap.put("EFF_DATE",this.effDate);
		hashMap.put("EXP_DATE",this.expDate);
		hashMap.put("PACKAGE_ID",this.packageId);
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("STATE_DATE",this.stateDate);
		hashMap.put("PPROD_OFFER_ID",this.pprodOffId);
		hashMap.put("ACTIVE_STATE",this.activeState);
		hashMap.put("ACC_NBR",this.accNbr);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("PROD_TYPE_CD",this.prodTypeCd);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orderRelationId = (String) hashMap.get("ORDER_RELATION_ID");
			this.prodInstId = (String) hashMap.get("PROD_INST_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.prodOffInstId = (String) hashMap.get("PROD_OFFER_INST_ID");
			this.prodOffId = (String) hashMap.get("PROD_OFFER_ID");
			this.orderChn = (String) hashMap.get("ORDER_CHANNEL");
			this.state = (String) hashMap.get("STATE");
			this.effDate = (String) hashMap.get("EFF_DATE");
			this.expDate = (String) hashMap.get("EXP_DATE");
			this.packageId = (String) hashMap.get("PACKAGE_ID");
			this.lanId = (String) hashMap.get("LAN_ID");
			this.stateDate = (String) hashMap.get("STATE_DATE");
			this.pprodOffId = (String) hashMap.get("PPROD_OFFER_ID");
			this.activeState = (String) hashMap.get("ACTIVE_STATE");
			this.accNbr = (String) hashMap.get("ACC_NBR");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.prodTypeCd = (String) hashMap.get("PROD_TYPE_CD");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORDER_RELATION_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORDER_RELATION";
	}

	public String getDbActionType() {
		return dbActionType;
	}

	public void setDbActionType(String dbActionType) {
		this.dbActionType = dbActionType;
	}

}
