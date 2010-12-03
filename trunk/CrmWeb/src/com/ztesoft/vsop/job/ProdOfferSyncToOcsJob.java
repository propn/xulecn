package com.ztesoft.vsop.job;

import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.product.bo.ProductSyncBO;
import com.ztesoft.vsop.product.vo.InfProdToOCSVO;
import com.ztesoft.vsop.product.vo.OCSRespVO;
import com.ztesoft.vsop.product.webservice.SyncToOcsSoapClient;

/**
 * @desc
 * @author qin.guoquan
 * @date Oct 11, 2010 3:00:45 PM
 */
public class ProdOfferSyncToOcsJob extends JobTransaction {

	private static Logger log = Logger.getLogger(ProdOfferSyncToOcsJob.class);
	private static JobLoging jobLog = JobLoging.getLogger();
	private String threadId = "";
	private ProductSyncBO productSyncBo;

	public ProdOfferSyncToOcsJob() {
		productSyncBo = new ProductSyncBO();
	}

	public void process() {

		this.setLocked(true);
		List ocsList = null;
		try {
			ocsList = productSyncBo.getInfProdToOCSInfo("1");//��ֵ����Ʒ
			if (null != ocsList && !ocsList.isEmpty()) {
				for (int i = 0; i < ocsList.size(); i++) {
					InfProdToOCSVO ocs = (InfProdToOCSVO) ocsList.get(i);
					OCSRespVO resp = null;
					try {
						resp = SyncToOcsSoapClient.getInst().syncProdOfferToOCS(ocs.getProdMsg());//��OCSͬ��
					} catch (Exception e) {
						if (null == resp)
							resp = new OCSRespVO();
						resp.setResultCode("-1");
						resp.setResultDesc("" + e);
					}

					//ͬ���ɹ���д��ʷ��
					if ("0".equals(resp.getResultCode())) {
						productSyncBo.witeInfProdToOCSHis(ocs);
					} else {
						ocs.setState("F");
						ocs.setResultMsg(resp.getResultDesc());
					}
					//ÿ����һ�Σ����ʹ�����1,���ʹ������ڴ���4���ٷ��ͣ�����¼״̬��F
					int sendTimes = Integer.parseInt(ocs.getSendTimes());
					int times = sendTimes + 1;
					if (4 <= times)
						ocs.setState("F");
					ocs.setSendTimes("" + times);
					productSyncBo.updateInfProdToOCS(ocs);
				}

			} else {
				log.info("Can not find any InfProdToOCSVO, sleep........");
				jobLog.loging("Can not find any InfProdToOCSVO, sleep........");
			}

		} catch (Exception e) {
			log.error("####ProdOfferSyncToOcsJob.process ex. " + e);
			jobLog.loging("####ProdOfferSyncToOcsJob.process ex. " + e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
			this.setLocked(false);
		}

	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		ProdOfferSyncToOcsJob job = new ProdOfferSyncToOcsJob();
		CrmJobVO crmJobVo = (CrmJobVO) jobConext.get(CrmJob.JOB_ID_KEY);
		String jobArgs = crmJobVo.getJobArgs();
		if (null != jobArgs && !"".equals(jobArgs)) {
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
