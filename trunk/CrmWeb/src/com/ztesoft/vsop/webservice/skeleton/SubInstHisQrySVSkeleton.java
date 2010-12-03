/**
 * SubInstHisQrySVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.SubInstHisQryFromVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * SubInstHisQrySVSkeleton java skeleton for the axisService
 */
public class SubInstHisQrySVSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * 
	 * @param subInstHisQryFromVSOPReq
	 */

	public com.ztesoft.vsop.webservice.vo.SubInstHisQryFromVSOPResp subInstHisQryFromVSOP(
			com.ztesoft.vsop.webservice.vo.SubInstHisQryFromVSOPReq subInstHisQryFromVSOPReq) {
		String requestXml = subInstHisQryFromVSOPReq.getSubInstHisQryFromVSOPReq().getRequest();
		String responseXml = SubscribeServiceBo.getInstance().subInstHisQryFromVSOP(requestXml);
		SubInstHisQryFromVSOPResp respObj = new SubInstHisQryFromVSOPResp();
		VsopServiceResponse param=new VsopServiceResponse();
		param.setResponse(responseXml);
		respObj.setSubInstHisQryFromVSOPResp(param);
		return respObj;
	}

}
