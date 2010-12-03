package com.ztesoft.vsop.job;

import java.util.List;

import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.InfOrderRelationVo;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.ismpSyn.SoapClient;
import com.ztesoft.vsop.ismpSyn.vo.Response;
import com.ztesoft.vsop.order.InfOrderRelationBo;
import com.ztesoft.vsop.web.DcSystemParamManager;
import common.Logger;

/**
 * ����ͬ��crm������ϵ
 * �����������Ը���
 *
 */
public class InfOrderRelationJob extends JobTransaction {

	private static JobLoging log = JobLoging.getLogger();
	private Logger logger = Logger.getLogger(InfOrderRelationJob.class);
	private String threadId = "";
	private InfOrderRelationBo infOrderRelationBo;
	public InfOrderRelationJob() {
		infOrderRelationBo = new InfOrderRelationBo();
	}

	public void process() {
		this.setLocked(true);
		try {
			//log.loging("start InfOrderRelationJob...");
			//��ҳ��ȡ���ݺ󽫻�ȡ������״̬��Ϊ�Ѵ���״̬
			List infOrderRelationList = infOrderRelationBo.getInfOrderRelation();
			if (null != infOrderRelationList && !infOrderRelationList.isEmpty()) {
				for (int i = 0; i < infOrderRelationList.size(); i++) {
					InfOrderRelationVo vo = (InfOrderRelationVo) infOrderRelationList.get(i);
					//�жϸ���ֵ��Ʒ�Ƿ���Ҫ��,�����ڲ����ѹ�����productNbr,��ȡʱ���ٹ���
					String pacakgeId=vo.getPackageId();
					if(null == pacakgeId || "".equals(pacakgeId)){//packageIDΪnull����null�����������ǿմ�����
					try{
							//uesr_id_typeҪ��crm�Ĳ�Ʒ���ͱ���ת����0:�ֻ�1��С��ͨ2���̻�
							String dcM = DcSystemParamManager.getParameter("DC_MSISDN");
							String dcPhs = DcSystemParamManager.getParameter("DC_PHS");
							String dcPstn = DcSystemParamManager.getParameter("DC_PSTN");
							String userIdTypeManual=vo.getUserIdType();
							String userIdType ="0";
							if(dcM.indexOf(userIdTypeManual) > -1){
								//�ֻ�
								userIdType = "0";
							}
							if(dcPhs.indexOf(userIdTypeManual) > -1){
								//С��ͨ
								userIdType = "1";
							}
							if(dcPstn.indexOf(userIdTypeManual) > -1){
								//�̻�
								userIdType = "2";
							}
							//���ö�����ϵͬ������������ͬ����CRM
							SoapClient client = SoapClient.getInstance(); 
							
							//ͬ����ͬ��crm
							Response res = null;
							if (null != vo.getPackageId()// packageID��Ϊ�գ�ֱ��ת����offerNbr��crm
									&& !"".equals(vo.getPackageId())) {
								String offerNbr = DcSystemParamManager
										.getInstance().getProdOfferNbrById(vo.getPackageId());
								res = client.subscriptionSync(vo.getOpType(),offerNbr, "", vo.getInfOrderRelationId(), vo.getUserId(), userIdType);
							} else if (null != vo.getProductId()// packageIDΪ�գ�productID��Ϊ�գ�ֱ����productNbr
									&& !"".equals(vo.getProductId())) {
								String productNbr = DcSystemParamManager
										.getInstance().getProductNbrById(vo.getProductId());
								res = client.subscriptionSync(vo.getOpType(),"", productNbr, vo.getInfOrderRelationId(), vo.getUserId(), userIdType);
							} else {
								return;
							}
							//���ؽ���ɹ�,������д�뵽���INF_ORDER_RELATION_HIS,ͬʱɾ��INF_ORDER_RELATION�ļ�¼
							if (null!=res && 0 == res.getResultCode()) {
								infOrderRelationBo.writeInfOrderRelationHis(vo);
								infOrderRelationBo.delInfOrderRelation(vo.getInfOrderRelationId());
								
							//���صĽ��Ϊʧ����״̬�ĳ�F,���ʹ�����1
							} else {
								if(res!=null){
									vo.setResultMsg(res.getResultCode()+"");
								}
								infOrderRelationBo.updateInfOrderRelationWhenFail(vo);
							}
						}catch(Exception e){
							e.printStackTrace();
							logger.error("syncrm1:", e);
							log.loging(e.getMessage());
							vo.setResultMsg(e.toString());
							infOrderRelationBo.updateInfOrderRelationWhenFail(vo);
						}
					
					} else { //��ֵ��Ʒ�����ڿ��crm_ismp_code_map��,״̬�ĳɳɹ�S�鵵����ʷ��,ͬʱɾ��INF_ORDER_RELATION�ļ�¼
						vo.setState("S");
						infOrderRelationBo.writeInfOrderRelationHis(vo);
						infOrderRelationBo.delInfOrderRelation(vo.getInfOrderRelationId());
					}
				}
			} else {
				log.loging("can not find any InfOrderRelationVo,sleep.");
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.loging(e.toString());
			logger.error("syncrm2:", e);
			LegacyDAOUtil.rollbackOnException();
		} finally {
			LegacyDAOUtil.commitAndCloseConnection();
			//LegacyDAOUtil.releaseConnection();
			this.setLocked(false);
		}
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		InfOrderRelationJob job = new InfOrderRelationJob();
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
