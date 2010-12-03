package com.ztesoft.component.job.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CrmJobClustorStateVO extends ValueObject implements VO {

	private static final long serialVersionUID = 1L;

	private String clustorId = "";

	private String jobId = "";

	private String lastRuntime = "";

	private String lastRunEndtime = "";

	private String lastRunmsg = "";

	private String runState = "";

	private String jobChecktime = "";

	public CrmJobClustorStateVO() {
	}

	public CrmJobClustorStateVO(String pclustorId, String pjobId,
			String plastRuntime, String plastRunEndtime, String plastRunmsg,
			String prunState, String pjobChecktime) {
		clustorId = pclustorId;
		jobId = pjobId;
		lastRuntime = plastRuntime;
		lastRunEndtime = plastRunEndtime;
		lastRunmsg = plastRunmsg;
		runState = prunState;
		jobChecktime = pjobChecktime;
	}

	public String getClustorId() {
		return clustorId;
	}

	public String getJobId() {
		return jobId;
	}

	public String getLastRuntime() {
		return lastRuntime;
	}

	public String getLastRunEndtime() {
		return lastRunEndtime;
	}

	public String getLastRunmsg() {
		return lastRunmsg;
	}

	public String getRunState() {
		return runState;
	}

	public String getJobChecktime() {
		return jobChecktime;
	}

	public void setClustorId(String pClustorId) {
		clustorId = pClustorId;
	}

	public void setJobId(String pJobId) {
		jobId = pJobId;
	}

	public void setLastRuntime(String pLastRuntime) {
		lastRuntime = pLastRuntime;
	}

	public void setLastRunEndtime(String pLastRunEndtime) {
		lastRunEndtime = pLastRunEndtime;
	}

	public void setLastRunmsg(String pLastRunmsg) {
		lastRunmsg = pLastRunmsg;
	}

	public void setRunState(String pRunState) {
		runState = pRunState;
	}

	public void setJobChecktime(String pJobChecktime) {
		jobChecktime = pJobChecktime;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("CLUSTOR_ID", this.clustorId);
		hashMap.put("JOB_ID", this.jobId);
		hashMap.put("LAST_RUNTIME", this.lastRuntime);
		hashMap.put("LAST_RUN_ENDTIME", this.lastRunEndtime);
		hashMap.put("LAST_RUNMSG", this.lastRunmsg);
		hashMap.put("RUN_STATE", this.runState);
		hashMap.put("JOB_CHECKTIME", this.jobChecktime);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.clustorId = (String) hashMap.get("CLUSTOR_ID");
			this.jobId = (String) hashMap.get("JOB_ID");
			this.lastRuntime = (String) hashMap.get("LAST_RUNTIME");
			this.lastRunEndtime = (String) hashMap.get("LAST_RUN_ENDTIME");
			this.lastRunmsg = (String) hashMap.get("LAST_RUNMSG");
			this.runState = (String) hashMap.get("RUN_STATE");
			this.jobChecktime = (String) hashMap.get("JOB_CHECKTIME");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("CLUSTOR_ID");
		arrayList.add("JOB_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CRM_JOB_CLUSTOR_STATE";
	}

}
