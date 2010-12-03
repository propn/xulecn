package com.ztesoft.component.job;

import org.quartz.JobDataMap;

public class CheckUpdateMsg extends JobTransaction {

	public CheckUpdateMsg() {
		super();
	}

	public void process() {
		CrmJobInstanceCache.checkChange();
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		return new CheckUpdateMsg();
	}

}
