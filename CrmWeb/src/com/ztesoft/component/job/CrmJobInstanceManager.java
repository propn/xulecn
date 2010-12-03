package com.ztesoft.component.job;

import org.quartz.SchedulerException;

/**
 * CRM系统任务调度器 用于任务调度实例管理，系统全局只有一个实例，管理所有的任务调度程序
 * 
 * @author nep
 * 
 */
public class CrmJobInstanceManager {

	public static boolean isRunning = false;

	public static boolean isShutDonw = false;

	public static boolean isStopped = false;

	public static String JobLogingPath = "";

	public static String JobFileMaxSize = "";

	public static String clustorId = "Default";

	public static JobLoging logger = JobLoging.getLogger();

	/**
	 * 启动任务调度
	 * 
	 */
	public static void startup() {
		if (isRunning == false) {

			try {
				shutDownThread = null;
				CrmJobBo.clustorRegistor(clustorId);
				CrmJobUtilies.JOB_INSTANCE_MAP.clear();
				CrmDefaultJob.isShutDown = false;
				isShutDonw = false;
				isStopped = false;
				CrmJobInstanceCache.registor();
				logger.loging("任务注册完成");
				if (!CrmJobInstanceCache.isCreateSchedule) {
					CrmJobInstanceCache.getSheduleInstance().start();
					CrmJobInstanceCache.isCreateSchedule = true;
					logger.loging("调度启动成功");
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.loging("调度启动失败" + e.getMessage());
			}
			isRunning = true;
		}

	}

	public static ShutDownThread shutDownThread = null;

	/**
	 * 停止任务
	 * 
	 */
	public static void shutdown() {
		if (isRunning == true) {
			CrmDefaultJob.isShutDown = true;
			isShutDonw = true;
			isStopped = false;
//			if (shutDownThread == null) {
//				shutDownThread = new ShutDownThread();
//				shutDownThread.start();
//			}
			try {
				CrmJobInstanceCache.removeAllJob();
				if(CrmJobInstanceCache.crmScheduler != null){
			 	   CrmJobInstanceCache.crmScheduler.pauseAll();
				}
				CrmJobBo.clustorDestroy(CrmJobInstanceManager.clustorId);
			} catch (SchedulerException e) {
				logger.loging("调度停止失败" + e.getMessage());
			}
			CrmDefaultJob.isShutDown = true;
			logger.loging("调度停止成功");
			CrmJobInstanceManager.isRunning = false;
			CrmJobInstanceManager.isStopped = true;

		}

	}

	/**
	 * 停止任务
	 * 
	 */
	public static void shutdownServlet() {
		try {

			shutDownThread.shutdown();

		} catch (Exception e) {
		}

	}

}
