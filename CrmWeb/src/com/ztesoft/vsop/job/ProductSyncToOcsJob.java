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
 * @date Oct 11, 2010 3:00:40 PM
 */
public class ProductSyncToOcsJob extends JobTransaction {

	private static Logger log = Logger.getLogger(ProductSyncToOcsJob.class);
	private static JobLoging jobLog = JobLoging.getLogger();
	private String threadId = "";
	private ProductSyncBO productSyncBo;

	public ProductSyncToOcsJob() {
		productSyncBo = new ProductSyncBO();
	}

	public void process() {

		this.setLocked(true);
		List ocsList = null;
		try {
			ocsList = productSyncBo.getInfProdToOCSInfo("0");//增值产品
			if (null != ocsList && !ocsList.isEmpty()) {
				for (int i = 0; i < ocsList.size(); i++) {
					InfProdToOCSVO ocs = (InfProdToOCSVO) ocsList.get(i);
					OCSRespVO resp = null;
					try {
						resp = SyncToOcsSoapClient.getInst().syncProductToOCS(ocs.getProdMsg());
					} catch (Exception e) {
						if (null == resp)
							resp = new OCSRespVO();
						resp.setResultCode("-1");
						resp.setResultDesc("" + e);
					}

					//同步成功则写历史表
					if ("0".equals(resp.getResultCode())) {
						productSyncBo.witeInfProdToOCSHis(ocs);
					} else {
						ocs.setState("F");
						ocs.setResultMsg(resp.getResultDesc());
					}
					//每发送一次，重送次数加1,重送次数等于大于4则不再发送，将记录状态置F
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
			log.error("###ProductSyncToOcsJob.process ex. " + e);
			jobLog.loging("###ProductSyncToOcsJob.process ex. " + e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
			this.setLocked(false);
		}

	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		ProductSyncToOcsJob job = new ProductSyncToOcsJob();
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
