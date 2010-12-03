package com.ztesoft.crm.salesres.vo;

import com.ztesoft.common.valueobject.*;
import java.util.*;

public class RcOrderTacheVO extends ValueObject implements VO {

	private String tacheId = "";
	private String tacheName = "";

	public RcOrderTacheVO() {}

	public RcOrderTacheVO( String ptacheId, String ptacheName ) {
		tacheId = ptacheId;
		tacheName = ptacheName;
	}

	public String getTacheId() {
		return tacheId;
	}

	public String getTacheName() {
		return tacheName;
	}

	public void setTacheId(String pTacheId) {
		tacheId = pTacheId;
	}

	public void setTacheName(String pTacheName) {
		tacheName = pTacheName;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("TACHE_ID",this.tacheId);
		hashMap.put("TACHE_NAME",this.tacheName);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.tacheId = (String) hashMap.get("TACHE_ID");
			this.tacheName = (String) hashMap.get("TACHE_NAME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		return arrayList;
	}

	public String getTableName() {
		return "RC_ORDER_TACHE";
	}

}
