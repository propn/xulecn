package com.ztesoft.component.job;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.quartz.CronTrigger;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.component.job.vo.CrmJobVO;

public class CrmTrigerUtilies {
	public static String TriggerKey = "Triger";

	// 简单调度方式
	public static String TRIGER_TYPE_SIMPLE = "0";

	// 复杂调度方式
	public static String TRIGER_TYPE_COMPLEX = "1";

	// 复杂调度方式小时：
	public static String TRIGER_TYPE_COMPLEX_HOUR = "1";

	// 复杂调度方式日：
	public static String TRIGER_TYPE_COMPLEX_DAY = "2";

	// 复杂调度方式周：
	public static String TRIGER_TYPE_COMPLEX_WEEK = "3";

	// 复杂调度方式月：
	public static String TRIGER_TYPE_COMPLEX_MONTH = "4";

	// 复杂调度方式年：
	public static String TRIGER_TYPE_COMPLEX_YEAR = "5";

	/***************************************************************************
	 * 创建任务调度类型
	 * 
	 * @param jobContext
	 * @return
	 */
	public static synchronized Trigger createTriger(Map jobContext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);
		Trigger trigger = null;
		String jobScheduleType = crmJobVo.getJobType();
		if (jobScheduleType == null) {
			jobScheduleType = "-1";
		}
		// 创建简单的调度类型
		if (jobScheduleType.equalsIgnoreCase(TRIGER_TYPE_SIMPLE)) {
			trigger = createSimpleTriger(crmJobVo);
			return trigger;
		} else {
			// 处理复杂调度类型
			trigger = createCronTrigger(crmJobVo);
			return trigger;
		}
	}

	/**
	 * 创建简单的任务调度类型
	 * 
	 * @param jobContext
	 * @return
	 */
	private static synchronized Trigger createSimpleTriger(CrmJobVO crmJobVo) {
		Date startTime = null;
		Date endTime = null;
		String interval = "60";
		// 任务开始启动时间
		if (crmJobVo.getJobStartRun().equalsIgnoreCase("0")) {
			startTime = new Date(1000 * 60 * (System.currentTimeMillis()/(60* 1000)) + 2000 * 60);
		} else {
			Date shouldRunDate = DateFormatUtils.parseStrToDateTime(crmJobVo.getJobStartDay());
			long shouldRunAt = shouldRunDate.getTime();
			long now = System.currentTimeMillis();
			if(now >= shouldRunAt){  //如果当前时间超过了设置的开始时间，则让其马上执行
				startTime = new Date(now+3*1000); //马上执行
			}else{  //否则让它以设置的时间执行
				startTime = shouldRunDate;
			}
		}
		// 任务启动结束时间
		if (crmJobVo.getJobTerminateDay() != null) {
			endTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobTerminateDay());
		}
		// 任务运行时间间隔
		if (crmJobVo.getJobInterval() != null) {
			interval = crmJobVo.getJobInterval();
		}
		
		Trigger trigger = new SimpleTrigger(TriggerKey + crmJobVo.getJobId(), TriggerKey + crmJobVo.getJobGrpName(), crmJobVo.getJobId(), crmJobVo.getJobGrpName(), startTime, endTime,
				SimpleTrigger.REPEAT_INDEFINITELY, Long.parseLong(interval) * 1000L);
		return trigger;
	}

	/**
	 * 创建复杂调度规则
	 * 
	 * @param jobContext
	 * @return
	 */
	private static synchronized Trigger createCronTrigger(CrmJobVO crmJobVo) {
		Date startTime = null;
		Date endTime = null;
		// 任务开始启动时间
		if (crmJobVo.getJobStartRun().equalsIgnoreCase("0")) {
			startTime = new Date(1000 * 60 * (System.currentTimeMillis()/(60* 1000)) + 2000 * 60);
		} else {
			startTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobStartDay());
		}
		// 任务启动结束时间
		if (crmJobVo.getJobTerminateDay() != null) {
			endTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobTerminateDay());
		}
		// 创建表达式方式触发器//触发器名称、触发器组、任务名称、任务组
		Trigger trigger = null;
		try {
			trigger = new CronTrigger(TriggerKey + crmJobVo.getJobId(), TriggerKey + crmJobVo.getJobGrpName(), crmJobVo.getJobId(), crmJobVo.getJobGrpName(), startTime, endTime, crmJobVo.getJobRule());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// 生成表达式
		return trigger;
	}

	/**
	 * 获取任务上次触发时间
	 * 
	 * @param crmJobVo
	 * @return
	 */
	public static String getLastTriggerTime(CrmJobVO crmJobVo) {
		String triggerTime = "";
		try {
			Trigger trigger = CrmJobInstanceCache.getSheduleInstance().getTrigger(TriggerKey + crmJobVo.getJobId(), TriggerKey + crmJobVo.getJobGrpName());
			SimpleDateFormat dateFormator = new SimpleDateFormat(CrmConstants.DATE_TIME_FORMAT);
			triggerTime = dateFormator.format(trigger.getPreviousFireTime());
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		return triggerTime;
	}

}
