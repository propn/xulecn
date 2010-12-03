package com.ztesoft.component.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

import com.ztesoft.component.job.vo.CrmJobVO;

public class CrmTriggerListener implements TriggerListener {

	public String getName() {
		return null;
	}

	public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
		JobDataMap jobDataMap = arg1.getJobDetail().getJobDataMap();
		CrmJobVO crmJobVo = (CrmJobVO) jobDataMap.get(CrmJob.JOB_ID_KEY);
		CrmJobBo crmJobBo = new CrmJobBo();
		crmJobBo.loging(crmJobVo, 1, crmJobVo.getJobName() + "开始运行");
	}

	public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	public void triggerMisfired(Trigger arg0) {
		// TODO Auto-generated method stub

	}

	public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
		JobDataMap jobDataMap = arg1.getJobDetail().getJobDataMap();
		CrmJobVO crmJobVo = (CrmJobVO) jobDataMap.get(CrmJob.JOB_ID_KEY);
		CrmJobBo crmJobBo = new CrmJobBo();
		crmJobBo.loging(crmJobVo, 1, crmJobVo.getJobName() + "运行结束");

	}

}
