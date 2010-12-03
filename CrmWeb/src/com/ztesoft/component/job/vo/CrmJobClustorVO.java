package com.ztesoft.component.job.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CrmJobClustorVO extends ValueObject implements VO {

	private static final long serialVersionUID = 8939607334574788771L;
	private String clustorId = "";
	private String itime = "";
	private String otime = "";

	public CrmJobClustorVO() {}

	public CrmJobClustorVO( String pclustorId, String pitime, String potime ) {
		clustorId = pclustorId;
		itime = pitime;
		otime = potime;
	}

	public String getClustorId() {
		return clustorId;
	}

	public String getItime() {
		return itime;
	}

	public String getOtime() {
		return otime;
	}

	public void setClustorId(String pClustorId) {
		clustorId = pClustorId;
	}

	public void setItime(String pItime) {
		itime = pItime;
	}

	public void setOtime(String pOtime) {
		otime = pOtime;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CLUSTOR_ID",this.clustorId);
		hashMap.put("ITIME",this.itime);
		hashMap.put("OTIME",this.otime);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.clustorId = (String) hashMap.get("CLUSTOR_ID");
			this.itime = (String) hashMap.get("ITIME");
			this.otime = (String) hashMap.get("OTIME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CLUSTOR_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CRM_JOB_CLUSTOR";
	}

}
