/**
 * ProdOfferInfoSynCrmSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import org.apache.log4j.Logger;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.VariedServerEngine;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPResp;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  ProdOfferInfoSynCrmSVSkeleton java skeleton for the axisService
 */
public class ProdOfferInfoSynCrmSVSkeleton {
	private static Logger logger = Logger.getLogger(ProdOfferInfoSynCrmSVSkeleton.class);
    /**
     * Auto generated method signature
     * @param prodOfferSyncCrmToVSOPReq
     * @throws Exception 
     */
    public com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPResp ProdOfferInfoSynCrmSyn(
        com.ztesoft.vsop.webservice.vo.ProdOfferSyncCrmToVSOPReq prodOfferSyncCrmToVSOPReq) throws Exception {
    	logger.info("ProdOfferInfoSynCrmSVSkeleton.ProdOfferInfoSynCrmSyn start");
    	//获取请求内容
    	String requestXml = prodOfferSyncCrmToVSOPReq.getProdOfferSyncCrmToVSOPReq().getRequest();
    	logger.info("ProdOfferInfoSynCrmSyn requestXml:"+requestXml);
    	//具体业务逻辑处理
    	String responseXml="";
		//江西本地化修改: start panshaohua 2010-08-26
		String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
		if(CrmConstants.JX_PROV_CODE.equals(provinceCode)){
			VariedServerEngine varied = new VariedServerEngine();
			responseXml=varied.prodOfferCrmToVsopSyn(requestXml);
    	}else {
    		responseXml =SubscribeServiceBo.getInstance().prodOfferCrmToVsopSyn(requestXml);
    	}
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	ProdOfferSyncCrmToVSOPResp resp = new ProdOfferSyncCrmToVSOPResp();
    	resp.setProdOfferSyncCrmToVSOPResp(param);
    	logger.info("ProdOfferInfoSynCrmSVSkeleton.ProdOfferInfoSynCrmSyn end");
        return resp;
    }
}
