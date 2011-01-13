package com.ztesoft.component.job;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;

public class CrmParamsRefreshJob extends JobTransaction{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(CrmParamsRefreshJob.class);
	
	public CrmParamsRefreshJob(){
		super();
	}
	
	public void process() {
		this.setLocked(true);
		//配置参数重新刷新载入
		CrmParamsConfig.getInstance().initParams(CrmConstants.WEB_INF_PATH);
		logger.debug("配置参数重新刷新载入成功。");
		this.setLocked(false);
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		return new CrmParamsRefreshJob();
	}

	
}
