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
 * ����ȷ���·����Ŷ�ʱ����
 * @author yi.chengfeng
 *
 */
public class ReconfirmReplayJob extends JobTransaction {
	
	private String threadId = "";
	private OrderBo bo;
	private String recordsPerOnce; //����ͨ��ʱ����ÿ�λ�ȡINF_MSG���¼����
	private static JobLoging logger = JobLoging.getLogger();
	public ReconfirmReplayJob(){
		bo = new OrderBo();
		recordsPerOnce = "300"; //Ĭ��300
		String pageSize = DcSystemParamManager.getParameter(VsopConstants.RECONFIRM_PAGE_NUM);
		if(null != pageSize && !"".equals(pageSize)){
			recordsPerOnce = pageSize;
		}
	}
	public void process() {
		//��ȡ�����͵Ķ�����Ϣ
		List replayList = bo.getUndealReplay(recordsPerOnce,threadId);
		//���ýӿ��·�����
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
