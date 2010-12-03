package com.ztesoft.component.job.service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.buffalo.BaseService;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.job.CrmJobBo;
import com.ztesoft.component.job.CrmJobInstanceCache;
import com.ztesoft.component.job.CrmJobInstanceManager;
import com.ztesoft.component.job.vo.CrmJobVO;

public class JobService extends BaseService {
	/**
	 * 查询任务列表
	 * 
	 * @param jobtype
	 * @param endBeginTime
	 * @param endEndTime
	 * @param jobState
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel getJobPageModal(String jobtype, String endBeginTime, String endEndTime, String jobState, int pageIndex, int pageSize) {
		Map param = new HashMap();
		param.put("jobtype", jobtype);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		param.put("jobState", jobState);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.getJobPageModal(param, pageIndex, pageSize);
	}

	/**
	 * 查询日志
	 * 
	 * @param jobid
	 * @param endBeginTime
	 * @param endEndTime
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public PageModel getJobLoging(String jobid, String endBeginTime, String endEndTime, int pageIndex, int pageSize) {
		Map param = new HashMap();
		param.put("jobid", jobid);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.getJobLogingPageModal(param, pageIndex, pageSize);
	}

	/**
	 * 清除日志
	 * 
	 * @param jobid
	 * @param endBeginTime
	 * @param endEndTime
	 * @return
	 */
	public int clearLog(String jobid, String endBeginTime, String endEndTime) {
		Map param = new HashMap();
		param.put("jobid", jobid);
		param.put("endBeginTime", endBeginTime);
		param.put("endEndTime", endEndTime);
		CrmJobBo crmJobBo = new CrmJobBo();
		return crmJobBo.deleteJobLoging(param);
	}

	public String saveCrmJobVO(CrmJobVO crmJobVO, int saveType) {
		CrmJobBo crmJobBo = new CrmJobBo();

		return crmJobBo.saveCrmJob(crmJobVO, saveType);
	}

	/**
	 * 激活任务
	 * 
	 * @param jobList
	 * @return
	 */
	public int activeJob(String jobList) {
		String[] offers = jobList.split(",");
		CrmJobBo crmJobBo = new CrmJobBo();
		int count = 0;
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "1");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * 失效
	 * 
	 * @param jobList
	 * @return
	 */
	public int invalidJob(String jobList) {
		String[] offers = jobList.split(",");
		int count = 0;
		CrmJobBo crmJobBo = new CrmJobBo();
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "2");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * 重新有效
	 * 
	 * @param jobList
	 * @return
	 */
	public int revalidJob(String jobList) {
		String[] offers = jobList.split(",");
		int count = 0;
		CrmJobBo crmJobBo = new CrmJobBo();
		for (int i = 0, cnt = offers.length; i < cnt; i++) {
			if (offers[i].trim().length() > 0) {
				crmJobBo.modifyState(offers[i], "0");
				count = count + 1;
			}
		}

		return count;
	}

	/**
	 * 获取当前运行的任务
	 * 
	 * @return
	 */
	public List getCurrentRuningJob(String state) {

		return CrmJobInstanceCache.getRunningJob();
	}

	/**
	 * 启动任务调度
	 * 
	 * @return 0 任务已启动，1任务启动成功 2任务正在停止中
	 */
	public int startup() {
//		int port =  this.getRequest().getServerPort();
		//更改为buffalo 2.0的方法
		int port = RequestContext.getContext().getHttpRequest().getServerPort();
		String ipAddress = "127.0.0.1";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ipAddress = inet.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		CrmJobInstanceManager.clustorId = ipAddress + ":" + port;

		if ((CrmJobInstanceManager.isShutDonw) && (!CrmJobInstanceManager.isStopped)) {
			return 2;
		}

		if (CrmJobInstanceManager.isRunning) {
			return 0;
		} else {
			CrmJobInstanceManager.startup();
		}
		return 1;
	}

	/**
	 * 停止任务调度
	 * 
	 * @return 0任务已停止 1任务停止成功
	 */
	public int shutdown() {

		if (CrmJobInstanceManager.isRunning) {
			CrmJobInstanceManager.shutdown();
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 获取系统运行状态
	 * 
	 * @return 1运行中,0任务已停止
	 */
	public int getCurrentState() {

		if ((CrmJobInstanceManager.isShutDonw) && (!CrmJobInstanceManager.isStopped)) {
			return 2;
		}

		if (CrmJobInstanceManager.isRunning) {
			return 1;
		} else {
			return 0;
		}
	}
}
