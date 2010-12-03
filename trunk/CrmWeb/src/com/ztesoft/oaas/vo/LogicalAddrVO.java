package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class LogicalAddrVO extends ValueObject implements VO {

	private String logicalAddrId = "";
	private String addrId = "";
	private String logicalAddrType = "";
	private String logicalAddrDeta = "";

	public LogicalAddrVO() {}

	public LogicalAddrVO( String plogicalAddrId, String paddrId, String plogicalAddrType, String plogicalAddrDeta ) {
		logicalAddrId = plogicalAddrId;
		addrId = paddrId;
		logicalAddrType = plogicalAddrType;
		logicalAddrDeta = plogicalAddrDeta;
	}

	public String getLogicalAddrId() {
		return logicalAddrId;
	}

	public String getAddrId() {
		return addrId;
	}

	public String getLogicalAddrType() {
		return logicalAddrType;
	}

	public String getLogicalAddrDeta() {
		return logicalAddrDeta;
	}

	public void setLogicalAddrId(String pLogicalAddrId) {
		logicalAddrId = pLogicalAddrId;
	}

	public void setAddrId(String pAddrId) {
		addrId = pAddrId;
	}

	public void setLogicalAddrType(String pLogicalAddrType) {
		logicalAddrType = pLogicalAddrType;
	}

	public void setLogicalAddrDeta(String pLogicalAddrDeta) {
		logicalAddrDeta = pLogicalAddrDeta;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOGICAL_ADDRESS_ID",this.logicalAddrId);
		hashMap.put("ADDRESS_ID",this.addrId);
		hashMap.put("LOGICAL_ADDRESS_TYPE",this.logicalAddrType);
		hashMap.put("LOGICAL_ADDRESS_DETAIL",this.logicalAddrDeta);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logicalAddrId = (String) hashMap.get("LOGICAL_ADDRESS_ID");
			this.addrId = (String) hashMap.get("ADDRESS_ID");
			this.logicalAddrType = (String) hashMap.get("LOGICAL_ADDRESS_TYPE");
			this.logicalAddrDeta = (String) hashMap.get("LOGICAL_ADDRESS_DETAIL");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOGICAL_ADDRESS_ID");
		return arrayList;
	}

	public String getTableName() {
		return "LOGICAL_ADDRESS";
	}

}
