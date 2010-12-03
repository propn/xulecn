package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcPublicLogVO extends ValueObject implements VO {

	private String logId = "";
	private String reworkTime = "";
	private String reworkWen = "";
	private String reworkIp = "";
	private String reworkTable = "";
	private String act = "";
	private String oldNote = "";
	private String newNote = "";

	public RcPublicLogVO() {}

	public RcPublicLogVO( String plogId, String preworkTime, String preworkWen, String preworkIp, String preworkTable, String pact, String poldNote, String pnewNote ) {
		logId = plogId;
		reworkTime = preworkTime;
		reworkWen = preworkWen;
		reworkIp = preworkIp;
		reworkTable = preworkTable;
		act = pact;
		oldNote = poldNote;
		newNote = pnewNote;
	}

	public String getLogId() {
		return logId;
	}

	public String getReworkTime() {
		return reworkTime;
	}

	public String getReworkWen() {
		return reworkWen;
	}

	public String getReworkIp() {
		return reworkIp;
	}

	public String getReworkTable() {
		return reworkTable;
	}

	public String getAct() {
		return act;
	}

	public String getOldNote() {
		return oldNote;
	}

	public String getNewNote() {
		return newNote;
	}

	public void setLogId(String pLogId) {
		logId = pLogId;
	}

	public void setReworkTime(String pReworkTime) {
		reworkTime = pReworkTime;
	}

	public void setReworkWen(String pReworkWen) {
		reworkWen = pReworkWen;
	}

	public void setReworkIp(String pReworkIp) {
		reworkIp = pReworkIp;
	}

	public void setReworkTable(String pReworkTable) {
		reworkTable = pReworkTable;
	}

	public void setAct(String pAct) {
		act = pAct;
	}

	public void setOldNote(String pOldNote) {
		oldNote = pOldNote;
	}

	public void setNewNote(String pNewNote) {
		newNote = pNewNote;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("LOG_ID",this.logId);
		hashMap.put("REWORK_TIME",this.reworkTime);
		hashMap.put("REWORK_WEN",this.reworkWen);
		hashMap.put("REWORK_IP",this.reworkIp);
		hashMap.put("REWORK_TABLE",this.reworkTable);
		hashMap.put("ACTION",this.act);
		hashMap.put("OLD_NOTE",this.oldNote);
		hashMap.put("NEW_NOTE",this.newNote);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.logId = (String) hashMap.get("LOG_ID");
			this.reworkTime = (String) hashMap.get("REWORK_TIME");
			this.reworkWen = (String) hashMap.get("REWORK_WEN");
			this.reworkIp = (String) hashMap.get("REWORK_IP");
			this.reworkTable = (String) hashMap.get("REWORK_TABLE");
			this.act = (String) hashMap.get("ACTION");
			this.oldNote = (String) hashMap.get("OLD_NOTE");
			this.newNote = (String) hashMap.get("NEW_NOTE");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("LOG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "RC_PUBLIC_LOG";
	}

}
