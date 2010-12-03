package com.ztesoft.vsop.job;

import org.quartz.JobDataMap;

import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.vsop.web.InitConfigServlet;

public class RefreshCacheJob extends JobTransaction {

	public void process() {
		this.setLocked(true);
		InitConfigServlet configServlet = new InitConfigServlet();
		configServlet.refreshCache();
		this.setLocked(false);
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		return new RefreshCacheJob();
	}

}
