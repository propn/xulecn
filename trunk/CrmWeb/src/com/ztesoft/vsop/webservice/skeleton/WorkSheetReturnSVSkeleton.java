/**
 * WorkSheetReturnSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;
import com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse;

/**
 *  WorkSheetReturnSVSkeleton java skeleton for the axisService
 */
public class WorkSheetReturnSVSkeleton {
    /**
     * Auto generated method signature
     * @param workListVSOPToFKReq
     */
    public com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse workListVSOPToFK(
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq workListVSOPToFKReq) {
    	//1获取请求内容
    	String requestXml=workListVSOPToFKReq.getWorkListVSOPToFKReq().getRequest();
    	//2具体业务处理
    	//.........
    	String responseXml=null;
    	String streamNo=StringUtil.getInstance().getTagValue("StreamingNo",requestXml );
		responseXml=StringUtil.getInstance().getVsopResponse("WorkListVSOPToPFResp", streamNo, 
				VsopConstants.VSOP_SUCCESS_RESPONSECODE, "success", 
				"");
    	//3返回结果
		com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse workListVSOPToFKReqResponse=
			new com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	workListVSOPToFKReqResponse.setWorkListVSOPToFKReqResponse(param);
    	return workListVSOPToFKReqResponse;
    }
}
