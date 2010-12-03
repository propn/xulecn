package com.ztesoft.vsop.job;

import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.vo.UserInfoSyncMsg;
import com.ztesoft.vsop.web.DcSystemParamManager;

public class UserInfoSyncMsgJob extends JobTransaction {
	private static JobLoging logger = JobLoging.getLogger();
	private static Logger log4j = Logger.getLogger(UserInfoSyncMsgJob.class);
	private String threadId = "";
	private OrderBo bo;
	private String recordsPerOnce; //服务开通定时任务每次获取INF_MSG表记录条数
	public UserInfoSyncMsgJob() {
		bo = new OrderBo();
		recordsPerOnce = "300"; //默认300
		String pageSize = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_PAGE_NUM);
		if(null != pageSize && !"".equals(pageSize)){
			recordsPerOnce = pageSize;
		}
	}

	public void process() {
		log4j.info("UserInfoSyncMsgJob start");
		log4j.info("recordsPerOnce->" + recordsPerOnce+",threadId->"+threadId);
		List replayList = bo.getUndealUserInfoSyncMsg(recordsPerOnce,threadId);
		log4j.info("get ndealUserInfoSyncMsg size:" + replayList.size());
		//调用接口下发短信
		for (Iterator iterator = replayList.iterator(); iterator.hasNext();) {
			UserInfoSyncMsg userInfoSyncMsg = (UserInfoSyncMsg) iterator.next();
			boolean success = bo.sendUserInfoSyncFromVSOPMsg(userInfoSyncMsg);
			bo.updateUserInfoSyncMsg(success,userInfoSyncMsg);
		}
		log4j.info("UserInfoSyncMsgJob end");
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		UserInfoSyncMsgJob job = new UserInfoSyncMsgJob();
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		String jobArgs = crmJobVo.getJobArgs();
		if(null != jobArgs && !"".equals(jobArgs)){
			String threadId = jobArgs;
			job.setThreadId(threadId);
		}
		return job;
	}

	public String getThreadId() {
		return threadId;
	}

	public void setThreadId(String threadId) {
		this.threadId = threadId;
	}

	
}
