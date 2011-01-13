package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class ProdFamilyVO extends ValueObject implements VO {

	private String prodFamilyId = "";
	private String prodFamilyName = "";
	private String prodFamilyDesc = "";
	private String prodCode = "";

	public ProdFamilyVO() {}

	public ProdFamilyVO( String pprodFamilyId, String pprodFamilyName, String pprodFamilyDesc, String pprodCode ) {
		prodFamilyId = pprodFamilyId;
		prodFamilyName = pprodFamilyName;
		prodFamilyDesc = pprodFamilyDesc;
		prodCode = pprodCode;
	}

	public String getProdFamilyId() {
		return prodFamilyId;
	}

	public String getProdFamilyName() {
		return prodFamilyName;
	}

	public String getProdFamilyDesc() {
		return prodFamilyDesc;
	}

	public String getProdCode() {
		return prodCode;
	}

	public void setProdFamilyId(String pProdFamilyId) {
		prodFamilyId = pProdFamilyId;
	}

	public void setProdFamilyName(String pProdFamilyName) {
		prodFamilyName = pProdFamilyName;
	}

	public void setProdFamilyDesc(String pProdFamilyDesc) {
		prodFamilyDesc = pProdFamilyDesc;
	}

	public void setProdCode(String pProdCode) {
		prodCode = pProdCode;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("PRODUCT_FAMILY_ID",this.prodFamilyId);
		hashMap.put("PRODUCT_FAMILY_NAME",this.prodFamilyName);
		hashMap.put("PRODUCT_FAMILY_DESC",this.prodFamilyDesc);
		hashMap.put("PRODUCT_CODE",this.prodCode);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.prodFamilyId = (String) hashMap.get("PRODUCT_FAMILY_ID");
			this.prodFamilyName = (String) hashMap.get("PRODUCT_FAMILY_NAME");
			this.prodFamilyDesc = (String) hashMap.get("PRODUCT_FAMILY_DESC");
			this.prodCode = (String) hashMap.get("PRODUCT_CODE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("PRODUCT_FAMILY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "PRODUCT_FAMILY";
	}

}
