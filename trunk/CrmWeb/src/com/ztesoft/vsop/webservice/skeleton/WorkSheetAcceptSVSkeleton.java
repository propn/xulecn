/**
 * WorkSheetAcceptSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;
import com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse;


/**
 *  WorkSheetAcceptSVSkeleton java skeleton for the axisService
 */
public class WorkSheetAcceptSVSkeleton {
    /**
     * 服务开通工单接收服务
     * @param workListFKToVSOPReq
     * @throws Exception 
     */
    public com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse workListFKToVSOP(
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq workListFKToVSOPReq) throws Exception {
    	//1获取请求内容
    	String requestXml=workListFKToVSOPReq.getWorkListFKToVSOPReq().getRequest();
    	//2具体业务处理 cooltan 代码重构后，采用新的代码框架处理订购关系查询接口
    	//String responseXml=SubscribeServiceBo.getInstance().workListFKToVSOP(requestXml);
    	VariedServerEngine aVariedServerEngine=new VariedServerEngine();
    	String responseXml=aVariedServerEngine.workListFKToVSOP(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
    	//3返回结果
    	WorkListFKToVSOPReqResponse workListFKToVSOPReqResponse=new WorkListFKToVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	workListFKToVSOPReqResponse.setWorkListFKToVSOPReqResponse(param);
    	return workListFKToVSOPReqResponse;
    }
}
