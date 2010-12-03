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
import com.ztesoft.vsop.order.ProdOfferSynBO;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.vo.InfProdOfferInst;

public class OfferSyncJob extends JobTransaction{
	private static JobLoging logger = JobLoging.getLogger();
	private String args = "";
	private String recordsPerOnce = null;//每次获取记录数
	public OfferSyncJob(){}
	private OfferSyncJob(CrmJobVO crmJobVos){
		super();
		this.args = crmJobVos.getJobArgs();
	    System.out.println("参数:"+this.args+"  a");
	}
	public void process() {
		this.setLocked(true);
		try{
			long star = System.currentTimeMillis();
			logger.loging("OfferSyncJob.process start");
			if(recordsPerOnce == null){
				recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_ORDER_JOB_PAGE_NUM);
				if(recordsPerOnce == null) recordsPerOnce = "240"; //默认240
			}
			logger.loging("orderPerOnce ->" + recordsPerOnce);
			ProdOfferSynBO bo = new ProdOfferSynBO();
			List orders = bo.getUnDealOrders(Integer.parseInt(recordsPerOnce));
			if(orders != null && orders.size() > 0){
				for (Iterator itr = orders.iterator(); itr.hasNext();) {
					InfProdOfferInst vo = (InfProdOfferInst)itr.next();
					try{
						boolean target = bo.dealOfferSync(vo);
						if(!target){
							//处理失败的更新失败操作，但操作次数不+1，转入等待。
							bo.dealOperWait(vo);
							throw new RuntimeException("处理失败,更新接口表.");
						}
					}catch(Exception e){
						logger.loging(e.getMessage());
						bo.dealOperException(vo);
					}
				}
			}
			long end = System.currentTimeMillis();
			long processTime = end - star;
			logger.loging("OfferSyncJob.process end");
			logger.loging("OfferSyncJob.process cost:"+processTime);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			this.setLocked(false);
		}
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		return new OfferSyncJob(crmJobVo);
	}

}
