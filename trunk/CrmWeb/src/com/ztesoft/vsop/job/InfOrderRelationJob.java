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
 * 反向同步crm订购关系
 * 广西江西可以复用
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
			//分页获取数据后将获取的数据状态置为已处理状态
			List infOrderRelationList = infOrderRelationBo.getInfOrderRelation();
			if (null != infOrderRelationList && !infOrderRelationList.isEmpty()) {
				for (int i = 0; i < infOrderRelationList.size(); i++) {
					InfOrderRelationVo vo = (InfOrderRelationVo) infOrderRelationList.get(i);
					//判断该增值产品是否需要送,由于在插入已过滤了productNbr,读取时不再过滤
					String pacakgeId=vo.getPackageId();
					if(null == pacakgeId || "".equals(pacakgeId)){//packageID为null，或不null的情况下如果是空串才送
					try{
							//uesr_id_type要从crm的产品类型编码转换成0:手机1：小灵通2：固话
							String dcM = DcSystemParamManager.getParameter("DC_MSISDN");
							String dcPhs = DcSystemParamManager.getParameter("DC_PHS");
							String dcPstn = DcSystemParamManager.getParameter("DC_PSTN");
							String userIdTypeManual=vo.getUserIdType();
							String userIdType ="0";
							if(dcM.indexOf(userIdTypeManual) > -1){
								//手机
								userIdType = "0";
							}
							if(dcPhs.indexOf(userIdTypeManual) > -1){
								//小灵通
								userIdType = "1";
							}
							if(dcPstn.indexOf(userIdTypeManual) > -1){
								//固话
								userIdType = "2";
							}
							//调用订购关系同步方法将数据同步到CRM
							SoapClient client = SoapClient.getInstance(); 
							
							//同反向同步crm
							Response res = null;
							if (null != vo.getPackageId()// packageID不为空，直接转换成offerNbr送crm
									&& !"".equals(vo.getPackageId())) {
								String offerNbr = DcSystemParamManager
										.getInstance().getProdOfferNbrById(vo.getPackageId());
								res = client.subscriptionSync(vo.getOpType(),offerNbr, "", vo.getInfOrderRelationId(), vo.getUserId(), userIdType);
							} else if (null != vo.getProductId()// packageID为空，productID不为空，直接送productNbr
									&& !"".equals(vo.getProductId())) {
								String productNbr = DcSystemParamManager
										.getInstance().getProductNbrById(vo.getProductId());
								res = client.subscriptionSync(vo.getOpType(),"", productNbr, vo.getInfOrderRelationId(), vo.getUserId(), userIdType);
							} else {
								return;
							}
							//返回结果成功,则将数据写入到库表INF_ORDER_RELATION_HIS,同时删除INF_ORDER_RELATION的记录
							if (null!=res && 0 == res.getResultCode()) {
								infOrderRelationBo.writeInfOrderRelationHis(vo);
								infOrderRelationBo.delInfOrderRelation(vo.getInfOrderRelationId());
								
							//返回的结果为失败则将状态改成F,发送次数加1
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
					
					} else { //增值产品不存在库表crm_ismp_code_map中,状态改成成功S归档到历史表,同时删除INF_ORDER_RELATION的记录
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
