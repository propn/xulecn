package com.ztesoft.component.common.log.vo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class StaffOnlineVO extends ValueObject implements VO {

	private String logInfoId = "";
	private String staffId = "";
	private String staffName = "";
	private String ip = "";
	private String machineName = "";
	private String mac = "";
	private String logonDate = "";
	private String logoutDate = "";
	private String onlineState = "";
	private String addtion = "";

	public StaffOnlineVO() {}

	public StaffOnlineVO( String plogInfoId, String pstaffId, String pstaffName, String pip, String pmachineName, String pmac, String plogonDate, String plogoutDate, String ponlineState, String paddtion ) {
		logInfoId = plogInfoId;
		staffId = pstaffId;
		staffName = pstaffName ;
		ip = pip;
		machineName = pmachineName;
		mac = pmac;
		logonDate = plogonDate;
		logoutDate = plogoutDate;
		onlineState = ponlineState;
		addtion = paddtion;
	}

	public String getLogInfoId() {
		return logInfoId;
	}

	public String getStaffId() {
		return staffId;
	}

	public String getIp() {
		return ip;
	}

	public String getMachineName() {
		return machineName;
	}

	public String getMac() {
		return mac;
	}

	public String getLogonDate() {
		return logonDate;
	}

	public String getLogoutDate() {
		return logoutDate;
	}

	public String getOnlineState() {
		return onlineState;
	}

	public String getAddtion() {
		return addtion;
	}

	public void setLogInfoId(String pLogInfoId) {
		logInfoId = pLogInfoId;
	}

	public void setStaffId(String pStaffId) {
		staffId = pStaffId;
	}

	public void setIp(String pIp) {
		ip = pIp;
	}

	public void setMachineName(String pMachineName) {
		machineName = pMachineName;
	}

	public void setMac(String pMac) {
		mac = pMac;
	}

	public void setLogonDate(String pLogonDate) {
		logonDate = pLogonDate;
	}

	public void setLogoutDate(String pLogoutDate) {
		logoutDate = pLogoutDate;
	}

	public void setOnlineState(String pOnlineState) {
		onlineState = pOnlineState;
	}

	public void setAddtion(String pAddtion) {
		addtion = pAddtion;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_INFO_ID",this.logInfoId);
		hashMap.put("STAFF_ID",this.staffId);
		hashMap.put("STAFF_NAME",this.staffName);
		hashMap.put("IP",this.ip);
		hashMap.put("MACHINE_NAME",this.machineName);
		hashMap.put("MAC",this.mac);
		hashMap.put("LOGON_DATE",this.logonDate);
		hashMap.put("LOGOUT_DATE",this.logoutDate);
		hashMap.put("ONLINE_STATE",this.onlineState);
		hashMap.put("ADDTION",this.addtion);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logInfoId = (String) hashMap.get("LOG_INFO_ID");
			this.staffId = (String) hashMap.get("STAFF_ID");
			this.staffName = (String) hashMap.get("STAFF_NAME");
			this.ip = (String) hashMap.get("IP");
			this.machineName = (String) hashMap.get("MACHINE_NAME");
			this.mac = (String) hashMap.get("MAC");
			this.logonDate = (String) hashMap.get("LOGON_DATE");
			this.logoutDate = (String) hashMap.get("LOGOUT_DATE");
			this.onlineState = (String) hashMap.get("ONLINE_STATE");
			this.addtion = (String) hashMap.get("ADDTION");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOG_INFO_ID");
		return arrayList;
	}

	public String getTableName() {
		return "STAFF_ONLINE";
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

}

