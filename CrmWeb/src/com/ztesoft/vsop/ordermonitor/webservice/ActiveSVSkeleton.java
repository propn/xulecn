/**
 * ActiveSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.ordermonitor.webservice;

import com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveResp;
import com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveResponse;

/**
 * ActiveSVSkeleton java skeleton for the axisService
 */
public class ActiveSVSkeleton {
	/**
	 * Auto generated method signature
	 * 
	 * @param activeReq
	 */
	public com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveResp active(
			com.ztesoft.vsop.ordermonitor.webservice.vo.ActiveReq activeReq) {
		// TODO : fill this with the necessary business logic
		// throw new java.lang.UnsupportedOperationException("Please implement "
		// +
		// this.getClass().getName() + "#active");

		ActiveResponse resultResponse = new ActiveResponse();
		resultResponse.setResponse("收到的消息是" + activeReq.getActiveReq().getActivetype());
		ActiveResp activeResp = new ActiveResp();
		activeResp.setActiveResp(resultResponse);
		return activeResp;
	}
}
