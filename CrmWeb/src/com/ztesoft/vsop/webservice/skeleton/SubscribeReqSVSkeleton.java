/**
 * SubscribeReqSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;


/**
 *  SubscribeReqSVSkeleton java skeleton for the axisService
 */
public class SubscribeReqSVSkeleton {
    /**
     * Auto generated method signature
     * @param subscribeToVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse subscribeToVSOP(
        com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq subscribeToVSOPReq) {
    	String requestXml = subscribeToVSOPReq.getSubscribeToVSOPReq().getRequest();
    	//2具体业务处理
    	//String responseXml = SubscribeServiceBo.getInstance().subscribeToVSOP(requestXml);
    	VariedServerEngine engine = new VariedServerEngine();
    	String responseXml = engine.subscribeToVSOP(requestXml);
    	//3返回结果
    	SubscribeToVSOPReqResponse subscribeToVSOPReqResponse = new SubscribeToVSOPReqResponse();
    	VsopServiceResponse param = new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subscribeToVSOPReqResponse.setSubscribeToVSOPReqResponse(param);
    	return subscribeToVSOPReqResponse;
    }
    
}
