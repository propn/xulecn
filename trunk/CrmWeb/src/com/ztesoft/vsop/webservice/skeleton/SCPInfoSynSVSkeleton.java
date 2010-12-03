/**
 * SCPInfoSynSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.sql.SQLException;

import org.dom4j.DocumentException;

import com.powerise.ibss.framework.Const;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.webservice.bo.SPCPSyncServiceBo;
import com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  SCPInfoSynSVSkeleton java skeleton for the axisService
 */
public class SCPInfoSynSVSkeleton {

	/**
	 * Auto generated method signature
	 
	 
	 * @param cPSPInfoSyncReq
	 */

	public com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse CPSPInfoSync(
			com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq cPSPInfoSyncReq) {
		CPSPInfoSyncReqResponse respObj = new CPSPInfoSyncReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		String requestXml = cPSPInfoSyncReq.getCPSPInfoSyncReq().getRequest();
		String respXml = null;
		try {
			respXml = SPCPSyncServiceBo.getInstance().CPSPInfoSync(requestXml);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			StringUtil su = StringUtil.getInstance();
			respXml=su.getVsopResponse("CSPInfoSyncToVSOPResp", 
									   su.getTagValue(requestXml, "StreamingNo"),
									   "-1",
									   e.getMessage().substring(0, 100), 
									   null);
		}
		param.setResponse(respXml);
		respObj.setCPSPInfoSyncReqResponse(param);
		//TODO : fill this with the necessary business logic
		return respObj;
	}

}
