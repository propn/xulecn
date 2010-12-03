/**
 * ServerActionToISMPSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.webservice.bo.ActionServerBo;
import com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  ServerActionToISMPSVSkeleton java skeleton for the axisService
 */
public class ServerActionToISMPSVSkeleton {

	/**
	 * Auto generated method signature
	 
	 
	 * @param subActionFromVSOPReq
	 */

	public com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse subActionFromVSOP(
			com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq subActionFromVSOPReq) {
		SubActionFromVSOPReqResponse respObj = new SubActionFromVSOPReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = subActionFromVSOPReq.getSubActionFromVSOPReq().getRequest();
		String respXml = ActionServerBo.getInstance().subActionFromVSOP(requestXml );
		param.setResponse(respXml );
		respObj.setSubActionFromVSOPReqResponse(param );
		return respObj;
	}

	/**
	 * Auto generated method signature
	 
	 
	 * @param chgActionFromVSOPReq
	 */

	public com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse chgActionFromVSOP(
			com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq chgActionFromVSOPReq) {
		ChgActionFromVSOPReqResponse respObj = new ChgActionFromVSOPReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = chgActionFromVSOPReq.getChgActionFromVSOPReq().getRequest();
		String respXml = ActionServerBo.getInstance().chgActionFromVSOP(requestXml  );
		param.setResponse(respXml );
		respObj.setChgActionFromVSOPReqResponse(param );
		return respObj;
	}

	/**
	 * Auto generated method signature
	 
	 
	 * @param unSubActionFromVSOPReq
	 */

	public com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse unSubActionFromVSOP(
			com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq unSubActionFromVSOPReq) {
		UnSubActionFromVSOPReqResponse respObj = new UnSubActionFromVSOPReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = unSubActionFromVSOPReq.getUnSubActionFromVSOPReq().getRequest();
		String respXml = ActionServerBo.getInstance().unSubActionFromVSOP(requestXml);
		param.setResponse(respXml );
		respObj.setUnSubActionFromVSOPReqResponse(param );
		return respObj;
	}

}
