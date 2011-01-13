package com.ztesoft.component.job.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.valueobject.ValueObject;

public class CrmJobVO extends ValueObject implements Comparable, VO {

	private static final long serialVersionUID = 1L;

	private String jobId = "";

	private String jobName = "";

	private String jobGrpName = "";

	private String jobClassName = "";

	private String jobType = "";

	private String jobInterval = "";

	private String jobMethod = "";

	private String jobRule = "";

	private String jobRuntime = "";

	private String jobStartDay = "";

	private String jobTerminateDay = "";

	private String jobDesc = "";

	private String jobState = "";

	private String jobArgs = "";

	// 最近执行开始时间
	private String lastExeuteBeginTime = "";

	// 最近执行结束时间
	private String lastExeuteEndTime = "";

	// 最近执行结果
	private String lastExeuteReSult = "";

	// 当前运行状态
	private String currentState = "";

	private String jobStartRun = "0";

	private String jobClustored = "0";
    
	private String clustorId ="";
	

	public String getClustorId() {
		return clustorId;
	}

	public void setClustorId(String clustorId) {
		this.clustorId = clustorId;
	}

	public String getJobClustored() {
		return jobClustored;
	}

	public void setJobClustored(String jobClustored) {
		this.jobClustored = jobClustored;
	}

	public CrmJobVO() {
	}

	public CrmJobVO(String pjobId, String pjobName, String pjobGrpName, String pjobClassName, String pjobType, String pjobInterval, String pjobMethod, String pjobRule, String pjobRuntime,
			String pjobStartDay, String pjobTerminateDay, String pjobDesc, String pjobState, String pjobArgs) {
		jobId = pjobId;
		jobName = pjobName;
		jobGrpName = pjobGrpName;
		jobClassName = pjobClassName;
		jobType = pjobType;
		jobInterval = pjobInterval;
		jobMethod = pjobMethod;
		jobRule = pjobRule;
		jobRuntime = pjobRuntime;
		jobStartDay = pjobStartDay;
		jobTerminateDay = pjobTerminateDay;
		jobDesc = pjobDesc;
		jobState = pjobState;
		jobArgs = pjobArgs;
	}

	public String getJobId() {
		return jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public String getJobGrpName() {
		return jobGrpName;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public String getJobType() {
		return jobType;
	}

	public String getJobInterval() {
		return jobInterval;
	}

	public String getJobMethod() {
		return jobMethod;
	}

	public String getJobRule() {
		return jobRule;
	}

	public String getJobRuntime() {
		return jobRuntime;
	}

	public String getJobStartDay() {
		return jobStartDay;
	}

	public String getJobTerminateDay() {
		return jobTerminateDay;
	}

	public String getJobDesc() {
		return jobDesc;
	}

	public String getJobState() {
		return jobState;
	}

	public void setJobId(String pJobId) {
		jobId = pJobId;
	}

	public void setJobName(String pJobName) {
		jobName = pJobName;
	}

	public void setJobGrpName(String pJobGrpName) {
		jobGrpName = pJobGrpName;
	}

	public void setJobClassName(String pJobClassName) {
		jobClassName = pJobClassName;
	}

	public void setJobType(String pJobType) {
		jobType = pJobType;
	}

	public void setJobInterval(String pJobInterval) {
		jobInterval = pJobInterval;
	}

	public void setJobMethod(String pJobMethod) {
		jobMethod = pJobMethod;
	}

	public void setJobRule(String pJobRule) {
		jobRule = pJobRule;
	}

	public void setJobRuntime(String pJobRuntime) {
		jobRuntime = pJobRuntime;
	}

	public void setJobStartDay(String pJobStartDay) {
		jobStartDay = pJobStartDay;
	}

	public void setJobTerminateDay(String pJobTerminateDay) {
		jobTerminateDay = pJobTerminateDay;
	}

	public void setJobDesc(String pJobDesc) {
		jobDesc = pJobDesc;
	}

	public void setJobState(String pJobState) {
		jobState = pJobState;
	}

	public HashMap unloadToHashMap() {
		HashMap hashMap = new HashMap();
		hashMap.put("JOB_ID", this.jobId);
		hashMap.put("JOB_NAME", this.jobName);
		hashMap.put("JOB_GROUP_NAME", this.jobGrpName);
		hashMap.put("JOB_CLASS_NAME", this.jobClassName);
		hashMap.put("JOB_TYPE", this.jobType);
		hashMap.put("JOB_INTERVAL", this.jobInterval);
		hashMap.put("JOB_METHOD", this.jobMethod);
		hashMap.put("JOB_RULE", this.jobRule);
		hashMap.put("JOB_RUNTIME", this.jobRuntime);
		hashMap.put("JOB_START_DAY", this.jobStartDay);
		hashMap.put("JOB_TERMINATE_DAY", this.jobTerminateDay);
		hashMap.put("JOB_DESC", this.jobDesc);
		hashMap.put("JOB_STATE", this.jobState);
		hashMap.put("JOB_ARGS", this.jobArgs);
		hashMap.put("JOB_START_RUN", this.jobStartRun);
		hashMap.put("JOB_CLUSTORED", this.jobClustored);
		return hashMap;
	}

	public void loadFromHashMap(HashMap hashMap) {
		if (hashMap != null) {
			this.jobId = (String) hashMap.get("JOB_ID");
			this.jobName = (String) hashMap.get("JOB_NAME");
			this.jobGrpName = (String) hashMap.get("JOB_GROUP_NAME");
			this.jobClassName = (String) hashMap.get("JOB_CLASS_NAME");
			this.jobType = (String) hashMap.get("JOB_TYPE");
			this.jobInterval = (String) hashMap.get("JOB_INTERVAL");
			this.jobMethod = (String) hashMap.get("JOB_METHOD");
			this.jobRule = (String) hashMap.get("JOB_RULE");
			this.jobRuntime = (String) hashMap.get("JOB_RUNTIME");
			this.jobStartDay = (String) hashMap.get("JOB_START_DAY");
			this.jobTerminateDay = (String) hashMap.get("JOB_TERMINATE_DAY");
			this.jobDesc = (String) hashMap.get("JOB_DESC");
			this.jobState = (String) hashMap.get("JOB_STATE");
			this.jobArgs = (String) hashMap.get("JOB_ARGS");
			this.jobStartRun = (String) hashMap.get("JOB_START_RUN");
			this.jobClustored = (String) hashMap.get("JOB_CLUSTORED");
		}
	}

	public List getKeyFields() {
		ArrayList arrayList = new ArrayList();
		arrayList.add("JOB_ID");
		return arrayList;
	}

	public String getTableName() {
		return "CRM_JOB";
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof CrmJobVO)) {
			return false;
		}
		CrmJobVO rhs = (CrmJobVO) object;

		if (!this.jobState.equals(rhs.jobState)) {
			return false;
		}
		if (!this.jobMethod.equals(rhs.jobMethod)) {
			return false;
		}
		if (!this.jobRuntime.equals(rhs.jobRuntime)) {
			return false;
		}
		if (!this.jobRule.equals(rhs.jobRule)) {
			return false;
		}
		if (!this.jobId.equals(rhs.jobId)) {
			return false;
		}
		// if (!this.jobStartDay.equals(rhs.jobStartDay)) {
		// // return false;
		// }
		if (!this.jobClassName.equals(rhs.jobClassName)) {
			return false;
		}
		if (!this.jobGrpName.equals(rhs.jobGrpName)) {
			return false;
		}
		if (!this.jobType.equals(rhs.jobType)) {
			return false;
		}
		if (!this.jobStartRun.equals(rhs.jobStartRun)) {
			return false;
		}

		if (!this.jobTerminateDay.equals(rhs.jobTerminateDay)) {
			return false;
		}
		if (!this.jobInterval.equals(rhs.jobInterval)) {
			return false;
		}
		if (!this.jobName.equals(rhs.jobName)) {
			return false;
		}
		if (!this.jobArgs.equals(rhs.jobArgs)) {
			return false;
		}
		if (!this.jobClustored.equals(rhs.jobClustored)) {
			return false;
		}
		if (!this.clustorId.equals(rhs.clustorId)) {
			return false;
		}
		return true;
	}

	public int compareTo(Object o) {
		return 0;
	}

	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	public String getLastExeuteBeginTime() {
		return lastExeuteBeginTime;
	}

	public void setLastExeuteBeginTime(String lastExeuteBeginTime) {
		this.lastExeuteBeginTime = lastExeuteBeginTime;
	}

	public String getLastExeuteEndTime() {
		return lastExeuteEndTime;
	}

	public void setLastExeuteEndTime(String lastExeuteEndTime) {
		this.lastExeuteEndTime = lastExeuteEndTime;
	}

	public String getLastExeuteReSult() {
		return lastExeuteReSult;
	}

	public void setLastExeuteReSult(String lastExeuteReSult) {
		this.lastExeuteReSult = lastExeuteReSult;
	}

	public String getJobArgs() {
		return jobArgs;
	}

	public void setJobArgs(String jobArgs) {
		this.jobArgs = jobArgs;
	}

	public String getJobStartRun() {
		return jobStartRun;
	}

	public void setJobStartRun(String jobStartRun) {
		this.jobStartRun = jobStartRun;
	}

}
