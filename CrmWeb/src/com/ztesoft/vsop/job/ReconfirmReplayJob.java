package com.ztesoft.vsop.job;

import java.util.Iterator;
import java.util.List;

import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.vo.SecondConfirmMsg;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 二次确认下发短信定时任务
 * @author yi.chengfeng
 *
 */
public class ReconfirmReplayJob extends JobTransaction {
	
	private String threadId = "";
	private OrderBo bo;
	private String recordsPerOnce; //服务开通定时任务每次获取INF_MSG表记录条数
	private static JobLoging logger = JobLoging.getLogger();
	public ReconfirmReplayJob(){
		bo = new OrderBo();
		recordsPerOnce = "300"; //默认300
		String pageSize = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_PAGE_NUM);
		if(null != pageSize && !"".equals(pageSize)){
			recordsPerOnce = pageSize;
		}
	}
	public void process() {
		//获取待发送的短信信息
		List replayList = bo.getUndealReplay(recordsPerOnce,threadId);
		//调用接口下发短信
		for (Iterator iterator = replayList.iterator(); iterator.hasNext();) {
			SecondConfirmMsg replyMsg = (SecondConfirmMsg) iterator.next();
			boolean success = bo.sendReconfirmMsg(replyMsg);
			bo.updateReconfirmMsg(success,replyMsg);
		}
		
	}

	
	public IJobTrans getInstance(JobDataMap jobConext) {
		ReconfirmReplayJob job = new ReconfirmReplayJob();
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
