package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class WorkingOfficeVO extends ValueObject implements VO {

	private String officeId = "";
	private String officeDesc = "";
	private String officeAddr = "";

	public WorkingOfficeVO() {}

	public WorkingOfficeVO( String pofficeId, String pofficeDesc, String pofficeAddr ) {
		officeId = pofficeId;
		officeDesc = pofficeDesc;
		officeAddr = pofficeAddr;
	}

	public String getOfficeId() {
		return officeId;
	}

	public String getOfficeDesc() {
		return officeDesc;
	}

	public String getOfficeAddr() {
		return officeAddr;
	}

	public void setOfficeId(String pOfficeId) {
		officeId = pOfficeId;
	}

	public void setOfficeDesc(String pOfficeDesc) {
		officeDesc = pOfficeDesc;
	}

	public void setOfficeAddr(String pOfficeAddr) {
		officeAddr = pOfficeAddr;
	}
	
	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("OFFICE_ID",this.officeId);
		hashMap.put("OFFICE_DESC",this.officeDesc);
		hashMap.put("OFFICE_ADDR",this.officeAddr);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.officeId = (String) hashMap.get("OFFICE_ID");
			this.officeDesc = (String) hashMap.get("OFFICE_DESC");
			this.officeAddr = (String) hashMap.get("OFFICE_ADDR");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("OFFICE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "WORKING_OFFICE";
	}

}
