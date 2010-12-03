package com.ztesoft.oaas.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class OfficeMachineVO extends ValueObject implements VO {

	private String machineId = "";
	private String officeId = "";
	private String macAddr = "";
	private String ipAddr = "";

	public OfficeMachineVO() {}

	public OfficeMachineVO( String pmachineId, String pofficeId, String pmacAddr, String pipAddr ) {
		machineId = pmachineId;
		officeId = pofficeId;
		macAddr = pmacAddr;
		ipAddr = pipAddr;
	}

	public String getMachineId() {
		return machineId;
	}

	public String getOfficeId() {
		return officeId;
	}

	public String getMacAddr() {
		return macAddr;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public void setMachineId(String pMachineId) {
		machineId = pMachineId;
	}

	public void setOfficeId(String pOfficeId) {
		officeId = pOfficeId;
	}

	public void setMacAddr(String pMacAddr) {
		macAddr = pMacAddr;
	}

	public void setIpAddr(String pIpAddr) {
		ipAddr = pIpAddr;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("MACHINE_ID",this.machineId);
		hashMap.put("OFFICE_ID",this.officeId);
		hashMap.put("MAC_ADDRESS",this.macAddr);
		hashMap.put("IP_ADDRESS",this.ipAddr);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.machineId = (String) hashMap.get("MACHINE_ID");
			this.officeId = (String) hashMap.get("OFFICE_ID");
			this.macAddr = (String) hashMap.get("MAC_ADDRESS");
			this.ipAddr = (String) hashMap.get("IP_ADDRESS");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("MACHINE_ID");
		return arrayList;
	}

	public String getTableName() {
		return "OFFICE_MACHINE";
	}

}
