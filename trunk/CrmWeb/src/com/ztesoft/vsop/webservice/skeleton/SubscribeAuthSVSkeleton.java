/**
 * SubscribeAuthSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.sql.SQLException;

import org.dom4j.DocumentException;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  SubscribeAuthSVSkeleton java skeleton for the axisService
 */
public class SubscribeAuthSVSkeleton {

	/**
	 * Auto generated method signature
	 
	 
	 * @param subscribeAuthReq
	 */

	public com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse subscribeAuth(
			com.ztesoft.vsop.webservice.vo.SubscribeAuthReq subscribeAuthReq) {
		SubscribeAuthReqResponse respObj = new SubscribeAuthReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = subscribeAuthReq.getSubscribeAuthReq().getRequest();
		String respXml = null;
		VariedServerEngine engine = new VariedServerEngine();
		respXml = engine.SubscribeAuthSV(requestXml);
		//respXml = SubscribeServiceBo.getInstance().subscribeAuth(requestXml );
		param.setResponse(respXml );
		respObj.setSubscribeAuthReqResponse(param);
		return respObj;
	}

}
