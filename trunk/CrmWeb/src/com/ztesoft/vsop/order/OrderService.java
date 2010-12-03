package com.ztesoft.vsop.order;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订购、变更、退订Web接口
 * @author yi.chengfeng Mar 2, 2010 10:38:29 AM
 *
 */
public class OrderService implements Serializable{
	private static Logger logger = Logger.getLogger(OrderService.class);
	/**
	 * 订购、变更、退订接口，供外系统调用
	 * @param obj
	 * @return
	 */
	public String subscribeToVSOP(String requestXML){
		OrderBo bo = new OrderBo();
		boolean requireAuth = true; //需要鉴权
		boolean needReconfirm = false; //不需要二次确认
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
	 * 订购关系查询
	 */
	public String subInstQuery(String requestXml) {
		OrderBo bo = new OrderBo();
		String responseXML = bo.subInstQuery(requestXml);
		return responseXML;
	}
	
	/**
	 * 接收来自服务开通的工单请求，包括用户状态工单、用户能力工单及订购/退订工单
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
	 * 订购历史查询
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
