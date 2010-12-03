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
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.order.OrderBo;
import com.ztesoft.vsop.order.vo.InfMsg;
import com.ztesoft.vsop.order.vo.OrderInfoResponse;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ��ʱɨ��ӿ���Ϣ��������ϵͳ�Ķ���
 * ����ͨ�û���Ϣͬ������
 * @author yi.chengfeng Mar 6, 2010 10:05:37 AM
 *
 */
public class OrderJob extends JobTransaction {
	protected static Logger log = Logger.getLogger(JobTransaction.class);
	private String threadId = "";
	private OrderBo bo;
	private String recordsPerOnce; //����ͨ��ʱ����ÿ�λ�ȡINF_MSG���¼����
	private static JobLoging logger = JobLoging.getLogger();
	private boolean requireAuth = true; //��Ҫ��Ȩ
	public OrderJob() {
		bo = new OrderBo();
		recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_ORDER_JOB_PAGE_NUM);
		String requireAuthValue = DcSystemParamManager.getParameter(VsopConstants.NEED_AUTHENTICATE);
		if(requireAuthValue != null){
			if("false".equals(requireAuthValue)) requireAuth = false;
		}
		log.info("#subscribeToVSOP requireAuth->" + requireAuth);
	}
	
	public void process() {
//		log.info("start orderJob.");
		this.setLocked(true);
		try{
			long s = System.currentTimeMillis();
			if(recordsPerOnce == null){
				recordsPerOnce = DcSystemParamManager.getParameter(VsopConstants.FK_ORDER_JOB_PAGE_NUM);
				if(recordsPerOnce == null) recordsPerOnce = "240"; //Ĭ��240
			}
			
			// ��ȡδ����Ķ���
			List orders = bo.getUnDealOrders(Integer.parseInt(recordsPerOnce), getThreadId());
			log.info("��ǰ��ȡ���̺߳ţ� ->" + getThreadId());
			//���û�д���������ݣ���ȴ�һ����ʱ����ȥ��ȡ����
			if(orders == null || (orders != null && orders.size() <= 0)) {
				log.info("can not find any undeal order,sleep.");
			}else{ //����Ҫ��������ݣ�����
				log.info("get undeal orders,all have " + orders.size() + " orders.");
				// ������
				if(orders != null && orders.size() > 0){
	//				log.info("process each order:");
					for (Iterator itr = orders.iterator(); itr.hasNext();) {
						s = System.currentTimeMillis();
						InfMsg infMsg = (InfMsg) itr.next();
						String requestXML = infMsg.getInfMsg();
						try {
							//cooltan �����ع��󣬲����µĴ����ܴ�������ϵ��ѯ�ӿ�
//							String responseXML = bo.sendOrderToActive_FK(requestXML);
							VariedServerEngine aVariedServerEngine=new VariedServerEngine();
					    	String responseXML=aVariedServerEngine.workListFKToVSOPJob(requestXML);
							OrderInfoResponse resp = null;
							resp = new OrderInfoResponse().fromXML(responseXML);
							if(resp.getResultCode().equals(OrderInfoResponse.RESULT_SUCCESS_CODE)){
								bo.successInfMsg(infMsg);
							}else{
								throw new RuntimeException(resp.getResultMessage());
							}
						} catch (Exception e) {
							log.info(e.getMessage());
							infMsg.setResultMsg(e.getMessage());
							bo.failInfMsg(infMsg);
						}
						
	//					log.info("process eachcost:" + (System.currentTimeMillis()-s));
					}
				}
			}
		log.info("orderPerOnce ->" + recordsPerOnce + " use " + (System.currentTimeMillis() -s ) + "s");
		}catch(Exception e){
			e.printStackTrace();
			log.info("Exception:"+e.getMessage());
		}
	}

	public IJobTrans getInstance(JobDataMap jobConext) {
		OrderJob job = new OrderJob();
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
