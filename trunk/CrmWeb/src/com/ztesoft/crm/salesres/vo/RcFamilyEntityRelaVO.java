package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcFamilyEntityRelaVO extends ValueObject implements VO {

	private String familyId = "";
	private String entityTabName = "";

	public RcFamilyEntityRelaVO() {}

	public RcFamilyEntityRelaVO( String pfamilyId, String pentityTabName ) {
		familyId = pfamilyId;
		entityTabName = pentityTabName;
	}

	public String getFamilyId() {
		return familyId;
	}

	public String getEntityTabName() {
		return entityTabName;
	}

	public void setFamilyId(String pFamilyId) {
		familyId = pFamilyId;
	}

	public void setEntityTabName(String pEntityTabName) {
		entityTabName = pEntityTabName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("FAMILY_ID",this.familyId);
		hashMap.put("ENTITY_TAB_NAME",this.entityTabName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.familyId = (String) hashMap.get("FAMILY_ID");
			this.entityTabName = (String) hashMap.get("ENTITY_TAB_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ENTITY_TAB_NAME");
		arrayList.add("FAMILY_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_FAMILY_ENTITY_RELA";
	}

}
