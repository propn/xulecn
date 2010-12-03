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
	private String recordsPerOnce = null;//ÿ�λ�ȡ��¼��
	public OfferSyncJob(){}
	private OfferSyncJob(CrmJobVO crmJobVos){
		super();
		this.args = crmJobVos.getJobArgs();
	    System.out.println("����:"+this.args+"  a");
	}
	public void process() {
		this.setLocked(true);
		try{
			long star = System.currentTimeMillis();
			logger.loging("OfferSyncJob.process start");
			if(recordsPerOnce == null){
				recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_ORDER_JOB_PAGE_NUM);
				if(recordsPerOnce == null) recordsPerOnce = "240"; //Ĭ��240
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
							//����ʧ�ܵĸ���ʧ�ܲ�����������������+1��ת��ȴ���
							bo.dealOperWait(vo);
							throw new RuntimeException("����ʧ��,���½ӿڱ�.");
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
