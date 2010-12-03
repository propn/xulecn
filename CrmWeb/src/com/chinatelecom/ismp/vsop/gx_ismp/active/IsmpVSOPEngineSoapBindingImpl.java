/**
 * IsmpVSOPEngineSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.chinatelecom.ismp.vsop.gx_ismp.active;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.engine.VariedServerEngine;

public class IsmpVSOPEngineSoapBindingImpl implements com.chinatelecom.ismp.vsop.gx_ismp.active.IsmpVSOPEngine_PortType{
	private static Logger logger = Logger.getLogger(IsmpVSOPEngineSoapBindingImpl.class);
	public com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPResp subActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubActionToISMPReq req) throws java.rmi.RemoteException {
        return null;
    }

    public com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionFromVSOPResp unSubActionToISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.UnSubActionToISMPReq req) throws java.rmi.RemoteException {
        return null;
    }

    /**
     * ����ISMP�Ķ�����ϵͬ��
     */
    public com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp subsInstSynFromISMP(com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq req) throws java.rmi.RemoteException {
		logger.info("IsmpVSOPEngineSoapBindingImpl.subsInstSynFromISMP start");
    	//1��ȡ��������
    	String requestXml=this.convertSubsReqToXML(req);
    	//2����ҵ����
    	String responseXml=null;
		VariedServerEngine engine = new VariedServerEngine();
		responseXml = engine.SubsInstSynFromISMP(requestXml);
		com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp resp = new com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPResp();
		StringUtil su = StringUtil.getInstance();
		resp.setResultCode(su.getTagValue("ResultCode", responseXml));
		resp.setStreamingNo(su.getTagValue("StreamingNo", responseXml));
		logger.info("IsmpVSOPEngineSoapBindingImpl.subsInstSynFromISMP end");
    	return resp;
    }
    
    
    /**
     * �Ѷ�����ϵͬ���ĸ��Ӷ���ת��Ϊ
     * @param req
     * @return
     */
    private String convertSubsReqToXML(com.chinatelecom.ismp.vsop.gx_ismp.active.SubsInstSynFromISMPReq req){
		StringUtil su = StringUtil.getInstance();
    	String streamNo = req.getStreamingNo();
    	StringBuffer reqXml = new StringBuffer();
    	String userId = getString(req.getUserID());
    	if(userId.startsWith("86")){
    		userId = userId.substring(2);
    	}
    	reqXml.append("<Request><SessionBody><SubsInstSynFromISMPReq>");
    	reqXml.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
    		  .append("<TimeStamp>"+su.getCurrentTimeStamp()+"</TimeStamp>")
    		  .append("<SystemId>204</SystemId>")//Ĭ��ismp-mƽ̨����
    		  .append("<OPFlag>").append(req.getOpType()).append("</OPFlag>")
    		  .append("<UserID>").append(userId).append("</UserID>")
    		  .append("<UserIDType>").append(getString(req.getUserIDType())).append("</UserIDType>")
    		  .append("<ProductID>").append(getString(req.getProductID())).append("</ProductID>")
    		  .append("<OldProductID>").append(getString(req.getOldProductID())).append("</OldProductID>")
    		  .append("<PackageID>").append(getString(req.getPackageID())).append("</PackageID>")
    		  .append("<OldpackageID>").append(req.getOldpackageID()).append("</OldpackageID>")
    		  .append("<PProductOfferID></PProductOfferID>").append("<OldPProductOfferID></OldPProductOfferID>")
    		  .append("<OPType>").append(getString(req.getOpType())).append("</OPType>")
    		  .append("<EffDate></EffDate><ExpDate></ExpDate>")
    		  .append("<VerUserId>").append(req.getVerUserId()).append("</VerUserId>");
    	reqXml.append("</SubsInstSynFromISMPReq></SessionBody></Request>");
    	logger.info("subsinst syn convertSubsReqToXML requestXML:"+reqXml.toString());
    	return reqXml.toString();
    }
    private String getString(Object value){
    	if(value == null)return "";
    	return value.toString();
    }
}
