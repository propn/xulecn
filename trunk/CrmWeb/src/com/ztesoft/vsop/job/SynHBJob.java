package com.ztesoft.vsop.job;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobDataMap;

import com.ztesoft.component.job.CrmJob;
import com.ztesoft.component.job.IJobTrans;
import com.ztesoft.component.job.JobLoging;
import com.ztesoft.component.job.JobTransaction;
import com.ztesoft.component.job.vo.CrmJobVO;
import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.order.dao.OrderDao;
import com.ztesoft.vsop.order.vo.MsgSynHB;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.SubsInstSynSVStub;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;

/**
 * �Ʒ�ͬ������,VSOP��Ʒ�ϵͳͬ��������ϵ
 * @author yi.chengfeng May 10, 2010 7:32:50 PM
 *
 */
public class SynHBJob extends JobTransaction {
	private static Logger log4j = Logger.getLogger(SynHBJob.class);
	private String threadId = "";  //�̱߳�ţ���ͬ�߳�ȡ��ͬ������
	private static JobLoging logger = JobLoging.getLogger();
	private OrderDao dao;
	private String recordsPerOnce; //����ͨ��ʱ����ÿ�λ�ȡSP_OUT_MSG_SYNHB���¼����
	private String HBServiceUrl = "";
	public void process() {
		setLocked(true);
		logger.loging("start SynBHJob.");
		if(recordsPerOnce == null){
			recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_FEEDBACK_JOB_PAGE_NUM);
			if(recordsPerOnce == null) recordsPerOnce = "240"; //Ĭ��240
		}
		//ȡ��¼
		List records = null;
		try {
			records = dao.getUnDealHBRecords(Integer.parseInt(recordsPerOnce), getThreadId());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			LegacyDAOUtil.commitAndCloseConnection();
		}
		if(records == null || (records != null && records.size() == 0)){
			logger.loging("can not find any undeal hb record,sleep.");
		}else{
			//��ͬ���ӿ�
			for (Iterator iterator = records.iterator(); iterator.hasNext();) {
				MsgSynHB synHb = (MsgSynHB) iterator.next();
				try {
					String resp = callSynHbService(synHb);
					synHb.setFeebackMsg(resp);
					String resultCode = StringUtil.getInstance().getTagValue("ResultCode", resp);
					synHb.setFeebackResult(resultCode);
					String resultDesc = StringUtil.getInstance().getTagValue("ResultDesc", resp);
					synHb.setFeebackDesc(resultDesc);
					//����״̬�͹鵵
					dao.updateSynHbMsgArchive(synHb);
				} catch (RemoteException e) {
					log4j.error("", e);
					synHb.setFeebackDesc(e.getMessage());
					LegacyDAOUtil.rollbackOnException();
					try {
						dao.failSynHbMsg(synHb);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				} catch (SQLException e) {
					log4j.error("", e);
					LegacyDAOUtil.rollbackOnException();
					synHb.setFeebackDesc(e.getMessage());
					try {
						dao.failSynHbMsg(synHb);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}finally{
					LegacyDAOUtil.commitAndCloseConnection();
				}
			}
		}
		setLocked(false);
	}
	
	private String callSynHbService(MsgSynHB synHb) throws RemoteException {
		if(HBServiceUrl == null || "".equals(HBServiceUrl)){
			HBServiceUrl = DcSystemParamManager.getParameter(VsopConstants.SYNHB_SERVICE_URL);
		}
		SubsInstSynSVStub endPoint = new SubsInstSynSVStub(HBServiceUrl);
		SubsInstSynToVSOPReq subsInstSynBWVSOPReq12 = new SubsInstSynToVSOPReq();
		VsopServiceRequest param = new VsopServiceRequest();
		String req = synHb.getMsg();
		log4j.info("SubsInstSynToVSOPReq xml:"+req);
		param.setRequest(req);
		subsInstSynBWVSOPReq12.setSubsInstSynToVSOPReq(param);//setSubsInstSynBWVSOPReq(param );
		SubsInstSynToVSOPResp respObj = endPoint.subsInstSynToGroup(subsInstSynBWVSOPReq12 );
		String respXml = respObj.getSubsInstSynToVSOPResp().getResponse();
		log4j.info("SubsInstSynToVSOPResp xml:" + respXml);
		return respXml;
	}

	public SynHBJob(){
		dao = new OrderDao();
		recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_FEEDBACK_JOB_PAGE_NUM);
		
	}
	public IJobTrans getInstance(JobDataMap jobConext) {
		SynHBJob job = new SynHBJob();
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

	public String getRecordsPerOnce() {
		return recordsPerOnce;
	}

	public void setRecordsPerOnce(String recordsPerOnce) {
		this.recordsPerOnce = recordsPerOnce;
	}

	public String getHBServiceUrl() {
		return HBServiceUrl;
	}

	public void setHBServiceUrl(String serviceUrl) {
		HBServiceUrl = serviceUrl;
	}

}
