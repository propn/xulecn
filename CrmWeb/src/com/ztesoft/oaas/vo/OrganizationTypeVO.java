package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OrganizationTypeVO extends ValueObject implements VO {

	private String orgTypeId = "";
	private String orgTypeName = "";
	private String orgClass = "";

	public OrganizationTypeVO() {}

	public OrganizationTypeVO( String porgTypeId, String porgTypeName, String porgClass ) {
		orgTypeId = porgTypeId;
		orgTypeName = porgTypeName;
		orgClass = porgClass;
	}

	public String getOrgTypeId() {
		return orgTypeId;
	}

	public String getOrgTypeName() {
		return orgTypeName;
	}

	public String getOrgClass() {
		return orgClass;
	}

	public void setOrgTypeId(String pOrgTypeId) {
		orgTypeId = pOrgTypeId;
	}

	public void setOrgTypeName(String pOrgTypeName) {
		orgTypeName = pOrgTypeName;
	}

	public void setOrgClass(String pOrgClass) {
		orgClass = pOrgClass;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("ORG_TYPE_ID",this.orgTypeId);
		hashMap.put("ORG_TYPE_NAME",this.orgTypeName);
		hashMap.put("ORG_CLASS",this.orgClass);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.orgTypeId = (String) hashMap.get("ORG_TYPE_ID");
			this.orgTypeName = (String) hashMap.get("ORG_TYPE_NAME");
			this.orgClass = (String) hashMap.get("ORG_CLASS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("ORG_TYPE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "ORGANIZATION_TYPE";
	}

}
