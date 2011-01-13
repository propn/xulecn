package com.ztesoft.component.job;

import org.quartz.SchedulerException;

/**
 * CRMϵͳ��������� �����������ʵ������ϵͳȫ��ֻ��һ��ʵ�����������е�������ȳ���
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
	 * �����������
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
				logger.loging("����ע�����");
				if (!CrmJobInstanceCache.isCreateSchedule) {
					CrmJobInstanceCache.getSheduleInstance().start();
					CrmJobInstanceCache.isCreateSchedule = true;
					logger.loging("���������ɹ�");
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.loging("��������ʧ��" + e.getMessage());
			}
			isRunning = true;
		}

	}

	public static ShutDownThread shutDownThread = null;

	/**
	 * ֹͣ����
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
				logger.loging("����ֹͣʧ��" + e.getMessage());
			}
			CrmDefaultJob.isShutDown = true;
			logger.loging("����ֹͣ�ɹ�");
			CrmJobInstanceManager.isRunning = false;
			CrmJobInstanceManager.isStopped = true;

		}

	}

	/**
	 * ֹͣ����
	 * 
	 */
	public static void shutdownServlet() {
		try {

			shutDownThread.shutdown();

		} catch (Exception e) {
		}

	}

}
