package com.ztesoft.component.job;

import java.util.Iterator;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.UnableToInterruptJobException;

import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.component.job.vo.CrmJobVO;

/**
 * �����ȱʡʵ����
 */
public class CrmDefaultJob implements CrmJob {

	// �رձ�־
	public static boolean isShutDown = false;

	private static JobLoging logger = JobLoging.getLogger();

	public void createInstance(Scheduler crmScheduler, Map jobContext) throws SchedulerException {
		try {
			// ϵͳ�ر��˳�
			if (isShutDown) {
				return;
			}
			CrmJobVO crmJobVo = (CrmJobVO) jobContext.get(CrmJob.JOB_ID_KEY);

			JobDetail detail = null;
			boolean isExist = false;
			if (crmScheduler.getJobDetail(crmJobVo.getJobId(), crmJobVo.getJobGrpName()) == null) {
				detail = new JobDetail(crmJobVo.getJobId(), crmJobVo.getJobGrpName(), CrmDefaultJob.class);
			} else {
				detail = (JobDetail) crmScheduler.getJobDetail(crmJobVo.getJobId(), crmJobVo.getJobGrpName());
				isExist = true;
			}

			detail.getJobDataMap().clear();
			detail.getJobDataMap().put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
			Iterator keySet = jobContext.keySet().iterator();
			while (keySet.hasNext()) {
				String key = keySet.next().toString().trim();
				detail.getJobDataMap().put(key, jobContext.get(key));
			}

			Trigger trigger = null;
			try {
				trigger = CrmTrigerUtilies.createTriger(jobContext);
			} catch (Exception e) {
				e.printStackTrace();
				logger.loging(crmJobVo, "����ע��ʱ���ȹ������" + e.getMessage());
				return;
			}
			if (isExist) {
				crmScheduler.deleteJob(crmJobVo.getJobId(), crmJobVo.getJobGrpName());
			}
			crmScheduler.scheduleJob(detail, trigger);
			crmScheduler.resumeJob(crmJobVo.getJobId(), crmJobVo.getJobGrpName());
			crmJobVo.setLastExeuteReSult("������Ϣ��ע��");
			logger.loging(crmJobVo, "������Ϣ��ע��");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		JobDataMap jobDataMap = arg0.getJobDetail().getJobDataMap();

		try {
			/**
			 * �������ֹͣ��������δ����������ʱ����ִ�У�����ǿ���˳�
			 */
			if (isShutDown) {
				if (CrmJobUtilies.isRuning(jobDataMap)) {
					return;
				} else {
					jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
					return;
				}
			}
			CrmJobVO crmJobVo = (CrmJobVO) jobDataMap.get(CrmJob.JOB_ID_KEY);
			String beginTime = CrmTrigerUtilies.getLastTriggerTime(crmJobVo);
			try {
				if (!CrmJobBo.jobisCanRun(CrmJobInstanceManager.clustorId, crmJobVo, beginTime)) {
					jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
					return;
				}
			} catch (Exception e) {
				logger.loging(crmJobVo, "�Ƿ���������жϴ���" + e.getMessage());
				jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
				return;
			}

			IJobTrans jobInstance = null;
			try {
				jobInstance = CrmJobUtilies.createJobProcessInstance(jobDataMap);
			} catch (Exception e) {
				crmJobVo.setLastExeuteReSult("����ʧ�ܣ�" + e.getMessage());
			}
			if (jobInstance == null) {
				jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
				crmJobVo.setLastExeuteReSult("���񲻴��ڿ���ִ�е�������");
				return;
			}
			// ��ȡʵ�����
			if (jobInstance.isLocked()) {
				return;
			}
			crmJobVo.setLastExeuteEndTime("");
			crmJobVo.setLastExeuteBeginTime(beginTime);
			logger.loging(crmJobVo, "��ʼ����");
			jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_RUNNING);
			try {
				jobInstance.process();
				crmJobVo.setLastExeuteEndTime(DateFormatUtils.getFormatedDateTime());
				crmJobVo.setLastExeuteReSult("�ɹ�����");
				logger.loging(crmJobVo, "�ɹ�����");
			} catch (Exception e) {
				jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
				crmJobVo.setLastExeuteEndTime(DateFormatUtils.getFormatedDateTime());
				crmJobVo.setLastExeuteReSult("����ʧ�ܣ�" + e.getMessage());
				logger.loging(crmJobVo, "����ʧ�ܣ�" + e.getMessage());
			}
			jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
			jobInstance.setLocked(false);
			CrmJobUtilies.clear(jobDataMap);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jobDataMap.put(CrmJob.JOB_STATE, CrmJob.JOB_STATE_STOP);
			CrmJobUtilies.clear(jobDataMap);
		}
	}

	public void interrupt() throws UnableToInterruptJobException {

	}

}
