/**
 * SubsInstQrySVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;


/**
 *  SubsInstQrySVSkeleton java skeleton for the axisService
 */
public class SubsInstQrySVSkeleton {
    /**
     * Auto generated method signature
     * @param subInstQryFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse subInstQryFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq subInstQryFromVSOPReq) {
    	String requestXml=subInstQryFromVSOPReq.getSubInstQryFromVSOPReq().getRequest();
    	//2����ҵ����  cooltan �����ع��󣬲����µĴ����ܴ�������ϵ��ѯ�ӿ�
    	//String responseXml=SubscribeServiceBo.getInstance().subInstQryFromVSOP(requestXml);
    	VariedServerEngine aVariedServerEngine=new VariedServerEngine();
    	String responseXml=aVariedServerEngine.subInstQryFromVSOP(StringUtil.getInstance().getTagValue("SessionBody", requestXml));
    	//3���ؽ��
    	SubInstQryFromVSOPReqResponse subInstQryFromVSOPReqResponse=new SubInstQryFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subInstQryFromVSOPReqResponse.setSubInstQryFromVSOPReqResponse(param);
    	return subInstQryFromVSOPReqResponse;
    }
}
