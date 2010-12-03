package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RrBusinessVO extends ValueObject implements VO {

	private String lanId = "";
	private String businessId = "";
	private String businessCode = "";
	private String businessName = "";

	public RrBusinessVO() {}

	public RrBusinessVO( String planId, String pbusinessId, String pbusinessCode, String pbusinessName ) {
		lanId = planId;
		businessId = pbusinessId;
		businessCode = pbusinessCode;
		businessName = pbusinessName;
	}

	public String getLanId() {
		return lanId;
	}

	public String getBusinessId() {
		return businessId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setLanId(String pLanId) {
		lanId = pLanId;
	}

	public void setBusinessId(String pBusinessId) {
		businessId = pBusinessId;
	}

	public void setBusinessCode(String pBusinessCode) {
		businessCode = pBusinessCode;
	}

	public void setBusinessName(String pBusinessName) {
		businessName = pBusinessName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LAN_ID",this.lanId);
		hashMap.put("BUSINESS_ID",this.businessId);
		hashMap.put("BUSINESS_CODE",this.businessCode);
		hashMap.put("BUSINESS_NAME",this.businessName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.lanId = (String) hashMap.get("LAN_ID");
			this.businessId = (String) hashMap.get("BUSINESS_ID");
			this.businessCode = (String) hashMap.get("BUSINESS_CODE");
			this.businessName = (String) hashMap.get("BUSINESS_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RR_BUSINESS";
	}

}
