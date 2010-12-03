/**
 * SubsInstSynSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  SubsInstSynSVSkeleton java skeleton for the axisService
 */
public class SubsInstSynSVSkeleton {
    /**
     * Auto generated method signature
     * @param subsInstSynToVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPResp subsInstSynToGroup(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq subsInstSynToVSOPReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=subsInstSynToVSOPReq.getSubsInstSynToVSOPReq().getRequest();
    	//System.out.println("subsInstSynToGroup request:" + requestXml);
    	//2具体业务处理
    	String responseXml=null;
    	VariedServerEngine engine = new VariedServerEngine();
		responseXml = engine.subsInstSynSV(requestXml);
    	
		//System.out.println("subsInstSynToGroup responseXml:" + responseXml);
    	//3返回结果
		SubsInstSynToVSOPResp subsInstSynToVSOPResp=new SubsInstSynToVSOPResp();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subsInstSynToVSOPResp.setSubsInstSynToVSOPResp(param);
    	return subsInstSynToVSOPResp;
    }
}
