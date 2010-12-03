package com.ztesoft.vsop.order;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * ������������˶�Web�ӿ�
 * @author yi.chengfeng Mar 2, 2010 10:38:29 AM
 *
 */
public class OrderService implements Serializable{
	private static Logger logger = Logger.getLogger(OrderService.class);
	/**
	 * ������������˶��ӿڣ�����ϵͳ����
	 * @param obj
	 * @return
	 */
	public String subscribeToVSOP(String requestXML){
		OrderBo bo = new OrderBo();
		boolean requireAuth = true; //��Ҫ��Ȩ
		boolean needReconfirm = false; //����Ҫ����ȷ��
		String requireAuthValue = DcSystemParamManager.getParameter(VsopConstants.NEED_AUTHENTICATE);
		if(requireAuthValue != null){
			if("false".equalsIgnoreCase(requireAuthValue)) requireAuth = false;
		}
		String needReConfirm = DcSystemParamManager.getParameter(VsopConstants.NEED_RECONFIRM);
		if(needReConfirm != null){
			if("true".equalsIgnoreCase(needReConfirm)) needReconfirm = true;
		}
		logger.info("#subscribeToVSOP requireAuth->" + requireAuth + ",needReconfirm->" + needReconfirm);
		String responseXML = bo.subscribeToVSOP(requestXML,requireAuth, needReconfirm);
		return responseXML;
	}
	
	/**
	 * ������ϵ��ѯ
	 */
	public String subInstQuery(String requestXml) {
		OrderBo bo = new OrderBo();
		String responseXML = bo.subInstQuery(requestXml);
		return responseXML;
	}
	
	/**
	 * �������Է���ͨ�Ĺ������󣬰����û�״̬�������û���������������/�˶�����
	 * @param requestXml
	 * @return
	 */
	public String workListFKToVSOP(String requestXml){
		OrderBo bo = new OrderBo();
		String responseXml = "";
		try {
			responseXml = bo.workListFKToVSOP(requestXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return responseXml;
	}

	/**
	 * ������ʷ��ѯ
	 * @param requestXml
	 * @return
	 */
	public String subInstHisQryFromVSOP(String requestXml) {
		OrderBo bo = new OrderBo();
		String responseXml = "";
		responseXml = bo.subInstHisQryFromVSOP(requestXml);
		return responseXml;
	}
	
}
