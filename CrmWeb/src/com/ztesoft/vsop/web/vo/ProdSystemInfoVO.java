package com.ztesoft.vsop.web.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ProdSystemInfoVO extends ValueObject implements VO {

	private String partnerSystemInfoId = "";
	private String prodId = "";
	private String systemCode = "";
	private String createDate = "";
	private String state = "";
	private String stateDate = "";

	public ProdSystemInfoVO() {}

	public ProdSystemInfoVO( String ppartnerSystemInfoId, String pprodId, String psystemCode, String pcreateDate, String pstate, String pstateDate ) {
		partnerSystemInfoId = ppartnerSystemInfoId;
		prodId = pprodId;
		systemCode = psystemCode;
		createDate = pcreateDate;
		state = pstate;
		stateDate = pstateDate;
	}

	public String getPartnerSystemInfoId() {
		return partnerSystemInfoId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getSystemCode() {
		return systemCode;
	}

	public String getCreateDate() {
		return createDate;
	}

	public String getState() {
		return state;
	}

	public String getStateDate() {
		return stateDate;
	}

	public void setPartnerSystemInfoId(String pPartnerSystemInfoId) {
		partnerSystemInfoId = pPartnerSystemInfoId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setSystemCode(String pSystemCode) {
		systemCode = pSystemCode;
	}

	public void setCreateDate(String pCreateDate) {
		createDate = pCreateDate;
	}

	public void setState(String pState) {
		state = pState;
	}

	public void setStateDate(String pStateDate) {
		stateDate = pStateDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PARTNER_SYSTEM_INFO_ID",this.partnerSystemInfoId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("SYSTEM_CODE",this.systemCode);
		hashMap.put("CREATE_DATE",this.createDate);
		hashMap.put("STATE",this.state);
		hashMap.put("STATE_DATE",this.stateDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.partnerSystemInfoId = (String) hashMap.get("PARTNER_SYSTEM_INFO_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.systemCode = (String) hashMap.get("SYSTEM_CODE");
			this.createDate = (String) hashMap.get("CREATE_DATE");
			this.state = (String) hashMap.get("STATE");
			this.stateDate = (String) hashMap.get("STATE_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PARTNER_SYSTEM_INFO_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_SYSTEM_INFO";
	}

}
