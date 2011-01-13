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
	 * 获取任务调度器
	 * 
	 * @return
	 */
	public static synchronized Scheduler getSheduleInstance() {
		return crmScheduler;
	}

	/**
	 * 获取任务调度器
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
	 * 系统启动时注册任务
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
						// 新注册
						CrmJobUtilies.registor(crmScheduler, ctx);
						logger.loging("注册任务" + crmJobVo.getJobName() + "成功：");
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.loging("注册任务" + crmJobVo.getJobName() + "失败：" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 系统运行中进行调度规则调整
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

				// 是否存在调度
				if (isExistSheduleInstance(crmJobVo)) {
					if (!checkConstrain(crmScheduler, crmJobVo)) {
						CrmJobUtilies.registor(crmScheduler, ctx);
						logger.loging(crmJobVo.getJobName() + "任务调度规则被修改，重新调度");
					}
				} else {
					// 新注册
					CrmJobUtilies.registor(crmScheduler, ctx);
					logger.loging(crmJobVo.getJobName() + "任务调度新注册");
					crmScheduler.start();
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.loging(crmJobVo.getJobName() + "同步更新失败:" + e.getMessage());
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
					// 如果为空任务
					//if (detail.getJobDataMap().get(CrmJob.JOB_STATE) == null) {
						crmScheduler.interrupt(jobnames[j], groups[i]);
						crmScheduler.deleteJob(jobnames[j], groups[i]);
						logger.loging(crmJobVo.getJobName() + "运行已停止");
						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId, crmJobVo.getJobId());
						continue;
					//}
					// 如果为有运行状态任务
//					if (detail.getJobDataMap().get(CrmJob.JOB_STATE).toString().equalsIgnoreCase(CrmJob.JOB_STATE_STOP)) {
//						crmScheduler.interrupt(jobnames[j], groups[i]);
//						crmScheduler.deleteJob(jobnames[j], groups[i]);
//						logger.loging(crmJobVo.getJobName() + "运行已停止");
//						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId, crmJobVo.getJobId());
//					} else {
//						logger.loging(crmJobVo.getJobName() + "停止中");
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
	 * 获取当前运行的任务
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
		// jobVo.setCurrentState("运行中");
		// list.add(jobVo);
		// isRuning = true;
		// break;
		// }
		// }
		// } catch (Exception e) {
		//
		// }
		// if (!isRuning) {
		// newCrmJobVo.setCurrentState("空闲中");
		// list.add(newCrmJobVo);
		// }
		// }
		// }
		// } catch (Exception e) {
		// logger.loging("任务调度查询：" + e.getMessage());
		// }
		// return list;
	}

	/**
	 * 删除无效调度任务
	 * 
	 * @param jobList
	 */
	private static synchronized void deleteInvalidJob(List jobList) {
		// 删除任务中没有的job任务
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
						logger.loging(jobnames[j] + "调度无效被删除");
						CrmJobBo.jobDestroy(CrmJobInstanceManager.clustorId,jobKey);
					}
				}
			}
		} catch (Exception e) {
			logger.loging(e.getMessage());
		}

	}

	/**
	 * 判断新旧规则是否一致
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
