package com.ztesoft.vsop.web.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class ProdServAbilityRelVO extends ValueObject implements VO {

	private String prodServAbilityRelId = "";
	private String prodId = "";
	private String servCode = "";
	private String relType = "";

	public ProdServAbilityRelVO() {}

	public ProdServAbilityRelVO( String pprodServAbilityRelId, String pprodId, String pservCode, String prelType ) {
		prodServAbilityRelId = pprodServAbilityRelId;
		prodId = pprodId;
		servCode = pservCode;
		relType = prelType;
	}

	public String getProdServAbilityRelId() {
		return prodServAbilityRelId;
	}

	public String getProdId() {
		return prodId;
	}

	public String getServCode() {
		return servCode;
	}

	public String getRelType() {
		return relType;
	}

	public void setProdServAbilityRelId(String pProdServAbilityRelId) {
		prodServAbilityRelId = pProdServAbilityRelId;
	}

	public void setProdId(String pProdId) {
		prodId = pProdId;
	}

	public void setServCode(String pServCode) {
		servCode = pServCode;
	}

	public void setRelType(String pRelType) {
		relType = pRelType;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRODUCT_SERVICE_ABILITY_REL_ID",this.prodServAbilityRelId);
		hashMap.put("PRODUCT_ID",this.prodId);
		hashMap.put("SERVICE_CODE",this.servCode);
		hashMap.put("REL_TYPE",this.relType);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodServAbilityRelId = (String) hashMap.get("PRODUCT_SERVICE_ABILITY_REL_ID");
			this.prodId = (String) hashMap.get("PRODUCT_ID");
			this.servCode = (String) hashMap.get("SERVICE_CODE");
			this.relType = (String) hashMap.get("REL_TYPE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRODUCT_SERVICE_ABILITY_REL_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_SERVICE_ABILITY_REL";
	}

}
