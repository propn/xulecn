package com.ztesoft.vsop.job;

import java.rmi.RemoteException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.vo.SpOutMsgFeedback;
import com.ztesoft.vsop.order.vo.returnSubVO;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.ReturnSubRTSVStub;
import com.ztesoft.vsop.webservice.skeleton.WorkSheetReturnSVStub;
import com.ztesoft.vsop.webservice.vo.SendSubRTFromVSOPReq;
import com.ztesoft.vsop.webservice.vo.SendSubRTFromVSOPResp;
import com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;
import com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq;
import com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse;

/**
 * ����ͨ�ص�
 * @author yi.chengfeng May 5, 2010 7:44:18 PM
 *
 */
public class OrderFeedbackJob extends JobTransaction {
	private static Logger log4j = Logger.getLogger(OrderFeedbackJob.class);
	private String threadId = "";  //�̱߳�ţ���ͬ�߳�ȡ��ͬ������
	private static JobLoging logger = JobLoging.getLogger();
	private OrderDao dao;
	private String recordsPerOnce; //����ͨ��ʱ����ÿ�λ�ȡsp_out_msg_feeback���¼����
	//����(��������ļ���������)
	private String WorkSheetReturnServiceUrl = "";
	//211 ���������ظ�
	private String ORDER_BACK_SERVICE_URL_211="";
	//212 10000��ϯ
	private String ORDER_BACK_SERVICE_URL_212="";
	//213 10000������
	private String ORDER_BACK_SERVICE_URL_213="";
	//214 ����Ӫҵ��
	private String ORDER_BACK_SERVICE_URL_214="";
	//215 WAPӪҵ��
	private String ORDER_BACK_SERVICE_URL_215="";
	
	public OrderFeedbackJob() {
		dao = new OrderDao();
		recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_FEEDBACK_JOB_PAGE_NUM);
	}

	public void process() {
		logger.loging("start OrderFeedbackJob.");
		if(recordsPerOnce == null){
			recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_FEEDBACK_JOB_PAGE_NUM);
			if(recordsPerOnce == null) recordsPerOnce = "240"; //Ĭ��240
		}
		logger.loging("feedbackPerOnce ->" + recordsPerOnce);
		String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");
		if(provinceCode.equals(CrmConstants.GX_PROV_CODE)){

			//������10000�ţ�wapӪҵ����������ص�
			try {
				processOrderReturn();
			}catch (Exception e) {
				logger.loging("getUnDealFeedbacks exception:" + e.getMessage());
			}
		}else{
			//�����ص��ɼ���ĵ����������
			List feedbackList = null;
			try {
				feedbackList = dao.getUnDealFeedbacks(Integer.parseInt(recordsPerOnce), getThreadId());
			} catch (Exception e) {
				logger.loging("getUnDealFeedbacks exception:" + e.getMessage());
			}finally{
				LegacyDAOUtil.commitAndCloseConnection();
			}
			if(feedbackList == null || (feedbackList != null && feedbackList.size()==0)){
				logger.loging("can not find any undeal feedback,sleep.");
			}else{//����Ҫ��������ݣ�����
				logger.loging("get undeal feedback,all have " + feedbackList.size() + " feedbacks to process.");
				for (Iterator iterator = feedbackList.iterator(); iterator
						.hasNext();) {
					try {
						SpOutMsgFeedback feedback = (SpOutMsgFeedback) iterator.next();
	//					������ͨ����Ӧ��ӿ�
						String resp = sendFeedback(feedback);
						feedback.setFeedbackMsg(resp);
						String resultCode = StringUtil.getInstance().getTagValue("ResultCode", resp);
						feedback.setFeedbackResult(resultCode);
						String resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", resp);
						feedback.setFeedbackDesc(resultDesc);
	//					����feedback״̬���鵵
						dao.updateFeedbackAndArchive(feedback);
						if("1".equals(feedback.getWriter())){  //OrderBo.java#fkFeedbackд����Ҫ�鵵
							dao.archiveOrder(feedback.getOrderId());
						}
					} catch (Exception e) {
						LegacyDAOUtil.rollbackOnException();
						log4j.info("", e);
					}finally{
						LegacyDAOUtil.commitAndCloseConnection();
					}
				}
				
			}
		}
	}

	private String sendFeedback(SpOutMsgFeedback feedback) throws RemoteException {
		if(WorkSheetReturnServiceUrl == null || "".equals(WorkSheetReturnServiceUrl)){
			WorkSheetReturnServiceUrl = DcSystemParamManager.getParameter(VsopConstants.WORK_SHEET_RETURN_SERVICE_URL);
		}
		log4j.info("WorkSheetReturnServiceUrl:" + WorkSheetReturnServiceUrl);
		WorkSheetReturnSVStub endpoint = new WorkSheetReturnSVStub(WorkSheetReturnServiceUrl);
		WorkListVSOPToFKReq workListVSOPToFKReq12 = new WorkListVSOPToFKReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String reqXml = feedback.getMsg();
		log4j.info("#sendFeedback req:" + reqXml);
		param.setRequest(reqXml );
		workListVSOPToFKReq12.setWorkListVSOPToFKReq(param );
		WorkListVSOPToFKReqResponse respObj = endpoint.workListVSOPToFK(workListVSOPToFKReq12 );
		String ret = respObj.getWorkListVSOPToFKReqResponse().getResponse();
		log4j.info("#sendFeedback resp:"+ret);
		return ret;
	}
	private void processOrderReturn()throws Exception{
		List feedbackList = null;
		try {
			feedbackList = dao.getUnDealOrderFeedback(Integer.parseInt(recordsPerOnce), getThreadId());
		} catch (Exception e) {
			logger.loging("getUnDealOrderFeedback exception:" + e.getMessage());
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		if(feedbackList == null || (feedbackList != null && feedbackList.size()==0)){
			logger.loging("can not find any undeal feedback,sleep.");
		}else{//����Ҫ��������ݣ�����
			logger.loging("get undeal feedback,all have " + feedbackList.size() + " feedbacks to process.");
			for (Iterator iterator = feedbackList.iterator(); iterator
					.hasNext();) {
				try {
					returnSubVO feedback = (returnSubVO) iterator.next();
//					����������ظ��ӿ�
					String resp = orderRelaFeedback(feedback);
					String resultCode = StringUtil.getInstance().getTagValue("ResultCode", resp);
					if("0".equals(resultCode)){
						//�ص��ɹ��鵵
						dao.backUpOrder(feedback.getOrderFeedId());
					}
				} catch (Exception e) {
					LegacyDAOUtil.rollbackOnException();
					log4j.info("", e);
				}finally{
					LegacyDAOUtil.commitAndCloseConnection();
				}
			}
			
		}
	}
	/**
	 * ��������ص�(Ŀǰֻ����ظ�10000����ϯ)
	 * @param feedback
	 * @return
	 * @throws RemoteException
	 */
	private String orderRelaFeedback(returnSubVO feedback) throws RemoteException {
		if(ORDER_BACK_SERVICE_URL_212 == null || "".equals(ORDER_BACK_SERVICE_URL_212)){
			ORDER_BACK_SERVICE_URL_212 = DcSystemParamManager.getParameter(VsopConstants.ORDER_BACK_SERVICE_URL_212);
		}
		log4j.info("ORDER_BACK_SERVICE_URL_212:" + ORDER_BACK_SERVICE_URL_212);
		ReturnSubRTSVStub endpoint = new ReturnSubRTSVStub(ORDER_BACK_SERVICE_URL_212);
		SendSubRTFromVSOPReq workListVSOPToFKReq12 = new SendSubRTFromVSOPReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String reqXml = feedback.toXML();
		log4j.info("#orderRelaFeedback req:" + reqXml);
		param.setRequest(reqXml );
		workListVSOPToFKReq12.setSendSubRTFromVSOPReq(param );
		SendSubRTFromVSOPResp respObj = endpoint.subResultFromVSOP(workListVSOPToFKReq12 );
		String ret = respObj.getSendSubRTFromVSOPResp().getResponse();
		log4j.info("#orderRelaFeedback resp:"+ret);
		return ret;
	}
	public IJobTrans getInstance(JobDataMap jobConext) {
		OrderFeedbackJob job = new OrderFeedbackJob();
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
