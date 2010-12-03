package com.ztesoft.vsop.job;

import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.order.XFileSqlldrBatch;

public class sqlldrXFileJob extends JobTransaction {
	private static JobLoging logger = JobLoging.getLogger();
	private String args = "";
	public sqlldrXFileJob() {
		super();
	}
	public sqlldrXFileJob(CrmJobVO crmJobVos) {
		super();
		this.args = crmJobVos.getJobArgs();
	    System.out.println("²ÎÊý:"+this.args+"  a");
	}
	public void process() {
		// TODO Auto-generated method stub
		logger.loging("start sqlldrXFileJob.");
		this.setLocked(true);
		try {
			new XFileSqlldrBatch().execCompare(this.args);
			logger.loging("sqlldrXFileJob done.");
		} catch (Exception ex) {
            ex.printStackTrace();
        }finally{
            this.setLocked(false);
        }
		
	}
	public IJobTrans getInstance(JobDataMap jobConext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		return new sqlldrXFileJob(crmJobVo);
	}
}
