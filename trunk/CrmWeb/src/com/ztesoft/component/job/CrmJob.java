package com.ztesoft.component.job;

import java.util.Map;

import org.quartz.InterruptableJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

public interface CrmJob extends InterruptableJob {
	// 任务对象
	public static String JOB_ID_KEY = "JOB_OBJECT";
	public static String JOB_STATE = "JOB_STATE";
	public static String JOB_STATE_RUNNING = "1";
	public static String JOB_STATE_STOP = "0";
	/**
	 * 初始化任务调度信息
	 * 
	 * @param crmScheduler
	 * @param jobContext
	 * @return
	 */
	public void createInstance(Scheduler crmScheduler, Map jobContext) throws SchedulerException;
}
