/**
 * SubsInstSynFromISMPSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;


/**
 *  SubsInstSynFromISMPSkeleton java skeleton for the axisService
 */
public class SubsInstSynFromISMPSkeleton {
	private static Logger logger = Logger.getLogger(SubsInstSynFromISMPSkeleton.class);
    /**
     * Auto generated method signature
     * @param subsInstSynFromISMPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReqResponse subsInstSynToVSOP(
        com.ztesoft.vsop.webservice.vo.SubsInstSynFromISMPReq subsInstSynFromISMPReq) {
    	logger.info("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP start");
		System.out.println("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP start");
    	//1获取请求内容
    	String requestXml=subsInstSynFromISMPReq.getSubsInstSynFromISMPReq().getRequest();
    	logger.info("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP requestXml:"+requestXml);
		System.out.println("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP requestXml:"+requestXml);
    	//2具体业务处理
    	//responseXml = SubscribeServiceBo.getInstance().subsInstSynToVSOP(requestXml);
    	String responseXml=null;
		VariedServerEngine engine = new VariedServerEngine();
		responseXml = engine.SubsInstSynFromISMP(requestXml);
    	//3返回结果
    	SubsInstSynFromISMPReqResponse subsInstSynFromISMPReqResponse=new SubsInstSynFromISMPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subsInstSynFromISMPReqResponse.setSubsInstSynFromISMPReqResponse(param);
		System.out.println("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP end");
    	logger.info("SubsInstSynFromISMPSkeleton.subsInstSynToVSOP end");
    	return subsInstSynFromISMPReqResponse;
    }
}
