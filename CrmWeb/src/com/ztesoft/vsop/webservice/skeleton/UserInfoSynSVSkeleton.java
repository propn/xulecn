/**
 * UserInfoSynSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.webservice.vo.UserInfoSynSVReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;


/**
 *  UserInfoSynSVSkeleton java skeleton for the axisService
 */
public class UserInfoSynSVSkeleton {
    /**
     * Auto generated method signature
     * @param userInfoSynSVReq
     */
    public com.ztesoft.vsop.webservice.vo.UserInfoSynSVReqResponse userInfoSynSV(
        com.ztesoft.vsop.webservice.vo.UserInfoSynSVReq userInfoSynSVReq) {
    	//1获取请求内容
    	String requestXml=userInfoSynSVReq.getUserInfoSynSVReq().getRequest();
    	System.out.println("userInfoSynSV requestXml:" + requestXml);
    	//2具体业务处理
    	//.........
    	String responseXml=null;
    	String streamNo=StringUtil.getInstance().getTagValue("StreamingNo",requestXml );
		responseXml=StringUtil.getInstance().getVsopResponse("UserInfoSyncFromVSOPReq", streamNo, 
				VsopConstants.VSOP_SUCCESS_RESPONSECODE, "success", 
				"");
		System.out.println("userInfoSynSV responseXml:" + responseXml);
    	//3返回结果
		UserInfoSynSVReqResponse userInfoSynSVReqResponse=new UserInfoSynSVReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	userInfoSynSVReqResponse.setUserInfoSynSVReqResponse(param);
    	return userInfoSynSVReqResponse;
    }
}
