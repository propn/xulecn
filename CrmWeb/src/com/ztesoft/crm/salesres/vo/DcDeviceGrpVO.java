package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class DcDeviceGrpVO extends ValueObject implements VO {

	private String ncResCode = "";
	private String dcDeviceScode = "";
	private String grpCode = "";
	private String grpName = "";
	private String operDate = "";

	public DcDeviceGrpVO() {}

	public DcDeviceGrpVO( String pncResCode, String pdcDeviceScode, String pgrpCode, String pgrpName, String poperDate ) {
		ncResCode = pncResCode;
		dcDeviceScode = pdcDeviceScode;
		grpCode = pgrpCode;
		grpName = pgrpName;
		operDate = poperDate;
	}

	public String getNcResCode() {
		return ncResCode;
	}

	public String getDcDeviceScode() {
		return dcDeviceScode;
	}

	public String getGrpCode() {
		return grpCode;
	}

	public String getGrpName() {
		return grpName;
	}

	public String getOperDate() {
		return operDate;
	}

	public void setNcResCode(String pNcResCode) {
		ncResCode = pNcResCode;
	}

	public void setDcDeviceScode(String pDcDeviceScode) {
		dcDeviceScode = pDcDeviceScode;
	}

	public void setGrpCode(String pGrpCode) {
		grpCode = pGrpCode;
	}

	public void setGrpName(String pGrpName) {
		grpName = pGrpName;
	}

	public void setOperDate(String pOperDate) {
		operDate = pOperDate;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("NC_RES_CODE",this.ncResCode);
		hashMap.put("DC_DEVICE_SCODE",this.dcDeviceScode);
		hashMap.put("GROUP_CODE",this.grpCode);
		hashMap.put("GROUP_NAME",this.grpName);
		hashMap.put("OPER_DATE",this.operDate);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.ncResCode = (String) hashMap.get("NC_RES_CODE");
			this.dcDeviceScode = (String) hashMap.get("DC_DEVICE_SCODE");
			this.grpCode = (String) hashMap.get("GROUP_CODE");
			this.grpName = (String) hashMap.get("GROUP_NAME");
			this.operDate = (String) hashMap.get("OPER_DATE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("DC_DEVICE_SCODE");
		arrayList.add("GROUP_CODE");
		arrayList.add("NC_RES_CODE");
		return arrayList;
	}

	public String getTableName() {
		return "DC_DEVICE_GROUP";
	}

}
