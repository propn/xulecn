package com.ztesoft.component.job.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CrmJobRunLogVO extends ValueObject implements VO {

	private static final long serialVersionUID = 1L;

	private String jobLogId = "";
	private String jobName = "";
	private String jobId = "";

	private String jobLogTime = "";

	private String jobRunState = "";

	private String jobRunMsg = "";

	public CrmJobRunLogVO() {
	}

	public CrmJobRunLogVO(String pjobLogId, String pjobId, String pjobLogTime, String pjobRunState, String pjobRunMsg) {
		jobLogId = pjobLogId;
		jobId = pjobId;
		jobLogTime = pjobLogTime;
		jobRunState = pjobRunState;
		jobRunMsg = pjobRunMsg;
	}

	public String getJobLogId() {
		return jobLogId;
	}

	public String getJobId() {
		return jobId;
	}

	public String getJobLogTime() {
		return jobLogTime;
	}

	public String getJobRunState() {
		return jobRunState;
	}

	public String getJobRunMsg() {
		return jobRunMsg;
	}

	public void setJobLogId(String pJobLogId) {
		jobLogId = pJobLogId;
	}

	public void setJobId(String pJobId) {
		jobId = pJobId;
	}

	public void setJobLogTime(String pJobLogTime) {
		jobLogTime = pJobLogTime;
	}

	public void setJobRunState(String pJobRunState) {
		jobRunState = pJobRunState;
	}

	public void setJobRunMsg(String pJobRunMsg) {
		jobRunMsg = pJobRunMsg;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("JOB_LOG_ID", this.jobLogId);
		hashMap.put("JOB_ID", this.jobId);
		hashMap.put("JOB_LOG_TIME", this.jobLogTime);
		hashMap.put("JOB_RUN_STATE", this.jobRunState);
		hashMap.put("JOB_RUN_MSG", this.jobRunMsg);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.jobLogId = (String) hashMap.get("JOB_LOG_ID");
			this.jobId = (String) hashMap.get("JOB_ID");
			this.jobLogTime = (String) hashMap.get("JOB_LOG_TIME");
			this.jobRunState = (String) hashMap.get("JOB_RUN_STATE");
			this.jobRunMsg = (String) hashMap.get("JOB_RUN_MSG");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("JOB_LOG_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CRM_JOB_RUN_LOG";
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

}
