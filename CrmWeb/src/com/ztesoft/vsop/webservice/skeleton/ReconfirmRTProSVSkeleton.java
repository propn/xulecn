/**
 * ReconfirmRTProSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.order.OrderMessConfirmBO;
import com.ztesoft.vsop.order.OrderRelationProcess;
import com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * ReconfirmRTProSVSkeleton java skeleton for the axisService
 */
public class ReconfirmRTProSVSkeleton {
	private static Logger logger = Logger.getLogger(ReconfirmRTProSVSkeleton.class);
	/**
	 * Auto generated method signature
	 * 
	 * 
	 * @param recvRQMessageReq
	 * @throws Exception 
	 */

	public com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse recvRQMessage(
			com.ztesoft.vsop.webservice.vo.RecvRQMessageReq recvRQMessageReq) throws Exception {
		//1获取请求内容
    	String requestXml=recvRQMessageReq.getRecvRQMessageReq().getRequest();
    	logger.info("recvRQMessage requestXml: " + requestXml);
    	//2具体业务处理
    	String responseXml = new OrderRelationProcess().recvRQMessage(requestXml) ;
    	logger.info("recvRQMessage responseXml:" + responseXml);
    	//3返回结果
    	RecvRQMessageReqResponse recvRQMessageReqResponse=new RecvRQMessageReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	recvRQMessageReqResponse.setRecvRQMessageReqResponse(param);
    	return recvRQMessageReqResponse;
	}

}
