package com.ztesoft.component.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

import com.ztesoft.component.job.vo.CrmJobVO;

public class CrmJobInstanceCache {

	public static Scheduler crmScheduler = null;

	private static JobLoging logger = JobLoging.getLogger();

	public static boolean isCreateSchedule = false;
	static {
		StdSchedulerFactory schedFactory = new StdSchedulerFactory();
		try {
			schedFactory.initialize("quartz.properties");
			if ((crmScheduler == null) || (crmScheduler.isShutdown())) {
				crmScheduler = schedFactory.getScheduler();
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.loging(e.getMessage());
		}
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public static synchronized Scheduler getSheduleInstance() {
		return crmScheduler;
	}

	/**
	 * ��ȡ���������
	 * 
	 * @return
	 */
	public static synchronized boolean isExistSheduleInstance(CrmJobVO crmJobVo) {
		try {
			if (crmScheduler.getJobDetail(crmJobVo.getJobId(), crmJobVo.getJobGrpName()) == null) {
				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			logger.loging(e.getMessage());
			return false;
		}
	}

	/**
	 * ϵͳ����ʱע������
	 * 
	 * @throws SchedulerException
	 */
	public static synchronized void registor() {
		CrmJobBo crmJobBo = new CrmJobBo();
		List jobList = crmJobBo.getJobList();
		for (int i = 0, cnt = jobList.size(); i < cnt; i++) {
			Map ctx = (HashMap) jobList.get(i);
			CrmJobVO crmJobVo = (CrmJobVO) ctx.get(CrmJob.JOB_ID_KEY);
			Scheduler crmScheduler = getSheduleInstance();
			if (crmJobVo != null) {
				try {
					if (crmScheduler.getJobDetail(crmJobVo.getJobId(), crmJobVo.getJobGrpName()) == null) {
						// ��ע��
						CrmJobUtilies.registor(crmScheduler, ctx);
						logger.loging("ע������" + crmJobVo.getJobName() + "�ɹ���");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.loging("ע������" + crmJobVo.getJobName() + "ʧ�ܣ�" + e.getMessage());
				}
			}
		}
	}

	/**
	 * ϵͳ�����н��е��ȹ������
	 * 
	 * @throws SchedulerException
	 */
	public static synchronized void checkChange() {
		CrmJobBo crmJobBo = new CrmJobBo();
		List jobList = crmJobBo.getJobList();
		for (int i = 0, cnt = jobList.size(); i < cnt; i++) {
			Map ctx = (HashMap) jobList.get(i);
			CrmJobVO crmJobVo = (CrmJobVO) ctx.get(CrmJob.JOB_ID_KEY);
			try {
				Scheduler crmScheduler = getSheduleInstance();

				// �Ƿ���ڵ���
				if (isExistSheduleInstance(crmJobVo)) {
					if (!checkConstrain(crmScheduler, crmJobVo)) {
						CrmJobUtilies.registor(crmScheduler, ctx);
						logger.loging(crmJobVo.getJobName() + "������ȹ����޸ģ����µ���");
					}
				} else {
					// ��ע��
					CrmJobUtilies.registor(crmScheduler, ctx);
					logger.loging(crmJobVo.getJobName() + "���������ע��");
					crmScheduler.start();
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.loging(crmJobVo.getJobName() + "ͬ������ʧ��:" + e.getMessage());
			}
		}
		deleteInvalidJob(jobList);

	}

	public static synchronized boolean removeAllJob() {
		if(crmScheduler == null){
			return  true;
		}
		String[] groups;
		boolean stop = true;
		try {
			groups = crmScheduler.getJobGroupNames();
			for (int i = 0, cnt = groups.length; i < cnt; i++) {
				String[] jobnames = crmScheduler.getJobNames(groups[i]);
				for (int j = 0, cntj = jobnames.length; j < cntj; j++) {
					JobDetail detail = crmScheduler.getJobDetail(jobnames[j], groups[i]);
					CrmJobVO crmJobVo = (CrmJobVO) detail.getJobDataMap().get(CrmJob.JOB_ID_KEY);
					// ���Ϊ������
					//if (detail.getJobDataMap().get(CrmJob.JOB_STATE) == null) {
						crmScheduler.interrupt(jobnames[j], groups[i]);
						crmScheduler.deleteJob(jobnames[j], groups[i]);
						logger.loging(crmJobVo.getJobName() + "������ֹͣ");
						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId, crmJobVo.getJobId());
						continue;
					//}
					// ���Ϊ������״̬����
//					if (detail.getJobDataMap().get(CrmJob.JOB_STATE).toString().equalsIgnoreCase(CrmJob.JOB_STATE_STOP)) {
//						crmScheduler.interrupt(jobnames[j], groups[i]);
//						crmScheduler.deleteJob(jobnames[j], groups[i]);
//						logger.loging(crmJobVo.getJobName() + "������ֹͣ");
//						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId, crmJobVo.getJobId());
//					} else {
//						logger.loging(crmJobVo.getJobName() + "ֹͣ��");
//						stop = false;
//					}
				}
			}
		} catch (Exception e) {
			stop = false;
			logger.loging(e.getMessage());
		}
		return stop;
	}

	public static synchronized List getCurrentRunningJob() {
		List list = new ArrayList();
		try {
			list = crmScheduler.getCurrentlyExecutingJobs();
		} catch (Exception e) {
			logger.loging(e.getMessage());
		}
		return list;
	}

	/**
	 * ��ȡ��ǰ���е�����
	 * 
	 * @return
	 */
	public static synchronized List getRunningJob() {

		return CrmJobBo.getRuningInfo();
		// List list = new ArrayList();
		// List curRun = getCurrentRunningJob();
		// try {
		// if (crmScheduler.isShutdown()) {
		// return list;
		// }
		//
		// String[] groups = crmScheduler.getJobGroupNames();
		// for (int i = 0, cnt = groups.length; i < cnt; i++) {
		// String[] jobnames = crmScheduler.getJobNames(groups[i]);
		// for (int j = 0, cntj = jobnames.length; j < cntj; j++) {
		// JobDetail detail = crmScheduler.getJobDetail(jobnames[j], groups[i]);
		// CrmJobVO newCrmJobVo = (CrmJobVO)
		// detail.getJobDataMap().get(CrmJob.JOB_ID_KEY);
		// boolean isRuning = false;
		// try {
		// for (int x = 0, cntx = curRun.size(); x < cntx; x++) {
		// JobExecutionContext jobCtx = (JobExecutionContext) curRun.get(i);
		// CrmJobVO jobVo = (CrmJobVO)
		// jobCtx.getJobDetail().getJobDataMap().get(CrmJob.JOB_ID_KEY);
		// if (jobVo.getJobId().equals(newCrmJobVo.getJobId())) {
		// jobVo.setCurrentState("������");
		// list.add(jobVo);
		// isRuning = true;
		// break;
		// }
		// }
		// } catch (Exception e) {
		//
		// }
		// if (!isRuning) {
		// newCrmJobVo.setCurrentState("������");
		// list.add(newCrmJobVo);
		// }
		// }
		// }
		// } catch (Exception e) {
		// logger.loging("������Ȳ�ѯ��" + e.getMessage());
		// }
		// return list;
	}

	/**
	 * ɾ����Ч��������
	 * 
	 * @param jobList
	 */
	private static synchronized void deleteInvalidJob(List jobList) {
		// ɾ��������û�е�job����
		String[] groups;
		try {
			groups = crmScheduler.getJobGroupNames();
			for (int i = 0, cnt = groups.length; i < cnt; i++) {
				String[] jobnames = crmScheduler.getJobNames(groups[i]);
				
				for (int j = 0, cntj = jobnames.length; j < cntj; j++) {
					boolean isExist = false;
					String jobKey ="";
					for (int x = 0, cntx = jobList.size(); x < cntx; x++) {
						Map ctx = (HashMap) jobList.get(x);
						CrmJobVO newCrmJobVo = (CrmJobVO) ctx.get(CrmJob.JOB_ID_KEY);
						jobKey = newCrmJobVo.getJobId();
						if (newCrmJobVo.getJobId().equals(jobnames[j]) && newCrmJobVo.getJobGrpName().equals(groups[i])) {
							isExist = true;
						}
					}
					if (!isExist) {
						crmScheduler.deleteJob(jobnames[j], groups[i]);
						logger.loging(jobnames[j] + "������Ч��ɾ��");
						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId,jobKey);
					}
				}
			}
		} catch (Exception e) {
			logger.loging(e.getMessage());
		}

	}

	/**
	 * �ж��¾ɹ����Ƿ�һ��
	 * 
	 * @param crmScheduler
	 * @param ctx
	 * @return
	 * @throws SchedulerException
	 */
	public static synchronized boolean checkConstrain(Scheduler crmScheduler, CrmJobVO newCrmJobVo) throws SchedulerException {

		JobDetail detail = crmScheduler.getJobDetail(newCrmJobVo.getJobId(), newCrmJobVo.getJobGrpName());
		if (detail == null) {
			return true;
		}
		JobDataMap oldData = detail.getJobDataMap();
		if (oldData.get(CrmJob.JOB_ID_KEY) == null) {
			return false;
		}
		if ((detail.getJobDataMap().get(CrmJob.JOB_STATE) == null)
				&& (!detail.getJobDataMap().get(CrmJob.JOB_STATE).toString().equalsIgnoreCase(CrmJob.JOB_STATE_STOP))) {
			return false;
		}
		CrmJobVO oldCrmJobVo = (CrmJobVO) oldData.get(CrmJob.JOB_ID_KEY);
		if (!oldCrmJobVo.equals(newCrmJobVo)) {
			return false;
		}
		return true;
	}
}
