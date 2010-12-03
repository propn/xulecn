/**
 * ServerActionSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.webservice.bo.ActionServerBo;
import com.ztesoft.vsop.webservice.vo.ServerActionSVReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  ServerActionSVSkeleton java skeleton for the axisService
 */
public class ServerActionSVSkeleton {

	/**
	 * Auto generated method signature
	 
	 
	 * @param serverActionSVReq
	 */

	public com.ztesoft.vsop.webservice.vo.ServerActionSVReqResponse serverActionSV(
			com.ztesoft.vsop.webservice.vo.ServerActionSVReq serverActionSVReq) {
		ServerActionSVReqResponse respObj = new ServerActionSVReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = serverActionSVReq.getServerActionSVReq().getRequest();
		String respXml = ActionServerBo.getInstance().serverActionSV(requestXml);
		param.setResponse(respXml );
		respObj.setServerActionSVReqResponse(param );
		return respObj;
	}

}
