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

	// �򵥵��ȷ�ʽ
	public static String TRIGER_TYPE_SIMPLE = "0";

	// ���ӵ��ȷ�ʽ
	public static String TRIGER_TYPE_COMPLEX = "1";

	// ���ӵ��ȷ�ʽСʱ��
	public static String TRIGER_TYPE_COMPLEX_HOUR = "1";

	// ���ӵ��ȷ�ʽ�գ�
	public static String TRIGER_TYPE_COMPLEX_DAY = "2";

	// ���ӵ��ȷ�ʽ�ܣ�
	public static String TRIGER_TYPE_COMPLEX_WEEK = "3";

	// ���ӵ��ȷ�ʽ�£�
	public static String TRIGER_TYPE_COMPLEX_MONTH = "4";

	// ���ӵ��ȷ�ʽ�꣺
	public static String TRIGER_TYPE_COMPLEX_YEAR = "5";

	/***************************************************************************
	 * ���������������
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
		// �����򵥵ĵ�������
		if (jobScheduleType.equalsIgnoreCase(TRIGER_TYPE_SIMPLE)) {
			trigger = createSimpleTriger(crmJobVo);
			return trigger;
		} else {
			// �����ӵ�������
			trigger = createCronTrigger(crmJobVo);
			return trigger;
		}
	}

	/**
	 * �����򵥵������������
	 * 
	 * @param jobContext
	 * @return
	 */
	private static synchronized Trigger createSimpleTriger(CrmJobVO crmJobVo) {
		Date startTime = null;
		Date endTime = null;
		String interval = "60";
		// ����ʼ����ʱ��
		if (crmJobVo.getJobStartRun().equalsIgnoreCase("0")) {
			startTime = new Date(1000 * 60 * (System.currentTimeMillis()/(60* 1000)) + 2000 * 60);
		} else {
			Date shouldRunDate = DateFormatUtils.parseStrToDateTime(crmJobVo.getJobStartDay());
			long shouldRunAt = shouldRunDate.getTime();
			long now = System.currentTimeMillis();
			if(now >= shouldRunAt){  //�����ǰʱ�䳬�������õĿ�ʼʱ�䣬����������ִ��
				startTime = new Date(now+3*1000); //����ִ��
			}else{  //�������������õ�ʱ��ִ��
				startTime = shouldRunDate;
			}
		}
		// ������������ʱ��
		if (crmJobVo.getJobTerminateDay() != null) {
			endTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobTerminateDay());
		}
		// ��������ʱ����
		if (crmJobVo.getJobInterval() != null) {
			interval = crmJobVo.getJobInterval();
		}
		
		Trigger trigger = new SimpleTrigger(TriggerKey + crmJobVo.getJobId(), TriggerKey + crmJobVo.getJobGrpName(), crmJobVo.getJobId(), crmJobVo.getJobGrpName(), startTime, endTime,
				SimpleTrigger.REPEAT_INDEFINITELY, Long.parseLong(interval) * 1000L);
		return trigger;
	}

	/**
	 * �������ӵ��ȹ���
	 * 
	 * @param jobContext
	 * @return
	 */
	private static synchronized Trigger createCronTrigger(CrmJobVO crmJobVo) {
		Date startTime = null;
		Date endTime = null;
		// ����ʼ����ʱ��
		if (crmJobVo.getJobStartRun().equalsIgnoreCase("0")) {
			startTime = new Date(1000 * 60 * (System.currentTimeMillis()/(60* 1000)) + 2000 * 60);
		} else {
			startTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobStartDay());
		}
		// ������������ʱ��
		if (crmJobVo.getJobTerminateDay() != null) {
			endTime = DateFormatUtils.parseStrToDate(crmJobVo.getJobTerminateDay());
		}
		// �������ʽ��ʽ������//���������ơ��������顢�������ơ�������
		Trigger trigger = null;
		try {
			trigger = new CronTrigger(TriggerKey + crmJobVo.getJobId(), TriggerKey + crmJobVo.getJobGrpName(), crmJobVo.getJobId(), crmJobVo.getJobGrpName(), startTime, endTime, crmJobVo.getJobRule());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// ���ɱ��ʽ
		return trigger;
	}

	/**
	 * ��ȡ�����ϴδ���ʱ��
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
