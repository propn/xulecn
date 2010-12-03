package com.ztesoft.component.job;

import org.quartz.SchedulerException;

public class ShutDownThread extends Thread {
	public void run() {
		JobLoging logger = JobLoging.getLogger(CrmJobInstanceManager.JobLogingPath, CrmJobInstanceManager.JobFileMaxSize);
		while (true) {
			if (CrmJobInstanceCache.removeAllJob()) {
				break;
			} else {
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				}
			}
		}
		CrmJobUtilies.JOB_INSTANCE_MAP.clear();
		CrmDefaultJob.isShutDown = true;
		try {
			CrmJobInstanceCache.crmScheduler.pauseAll();
			CrmJobBo.clustorDestroy(CrmJobInstanceManager.clustorId);
		} catch (SchedulerException e) {
			logger.loging("调度停止失败" + e.getMessage());
		}
		CrmDefaultJob.isShutDown = true;
		logger.loging("调度停止成功");
		CrmJobInstanceManager.isRunning = false;
		CrmJobInstanceManager.isStopped = true;

	}
	
	public void shutdown(){

		JobLoging logger = JobLoging.getLogger(CrmJobInstanceManager.JobLogingPath, CrmJobInstanceManager.JobFileMaxSize);
		CrmJobUtilies.JOB_INSTANCE_MAP.clear();
		CrmDefaultJob.isShutDown = true;
		try {
			CrmJobBo.clustorDestroy(CrmJobInstanceManager.clustorId);
			CrmJobInstanceCache.crmScheduler.pauseAll();
		} catch (SchedulerException e) {
			logger.loging("调度停止失败" + e.getMessage());
		}
		CrmDefaultJob.isShutDown = true;
		logger.loging("调度停止成功");
		CrmJobInstanceManager.isRunning = false;
		CrmJobInstanceManager.isStopped = true;

	}
}
