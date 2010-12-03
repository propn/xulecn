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
import com.ztesoft.vsop.order.OrderCheckBo;
import com.ztesoft.vsop.order.vo.InfMsg;
import com.ztesoft.vsop.order.vo.OrderInfoResponse;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 订购关系比对自动任务
 * @author liuyuming
 *
 */
public class orderCheckJob extends JobTransaction {
	private static JobLoging logger = JobLoging.getLogger();
	private String args = "";
	private OrderBo bo = new OrderBo();
	public orderCheckJob() {
		super();
	}
	public orderCheckJob(CrmJobVO crmJobVos) {
		super();
		this.args = crmJobVos.getJobArgs();
	    System.out.println("参数:"+this.args+"  a");
	}

	public void process() {
		logger.loging("start orderCheckJob.");
		this.setLocked(true);
		try {
			new OrderCheckBo().checkOrder(args);
			logger.loging("orderCheckJob done.");
		} catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            this.setLocked(false);
        }
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		return new orderCheckJob(crmJobVo);
	}
	

}