package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class MpStorageVO extends ValueObject implements VO {

	private String storageId = "";

	private String operId = "";

	private String state = "";

	private String operName = "";
	
	private String operCode = "";
	

	public String getOperCode() {
		return operCode;
	}

	public void setOperCode(String operCode) {
		this.operCode = operCode;
	}

	public MpStorageVO() {
	}

	public MpStorageVO(String pstorageId, String poperId, String pstate,
			String poperName) {
		storageId = pstorageId;
		operId = poperId;
		state = pstate;
		operName = poperName;
	}

	public String getStorageId() {
		return storageId;
	}

	public String getOperId() {
		return operId;
	}

	public String getState() {
		return state;
	}

	public void setStorageId(String pStorageId) {
		storageId = pStorageId;
	}

	public void setOperId(String pOperId) {
		operId = pOperId;
	}

	public void setState(String pState) {
		state = pState;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("STORAGE_ID", this.storageId);
		hashMap.put("OPER_ID", this.operId);
		hashMap.put("STATE", this.state);
		hashMap.put("OPER_NAME", this.operName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.storageId = (String) hashMap.get("STORAGE_ID");
			this.operId = (String) hashMap.get("OPER_ID");
			this.state = (String) hashMap.get("STATE");
			this.operName = (String) hashMap.get("OPER_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "MP_STORAGE";
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

}
