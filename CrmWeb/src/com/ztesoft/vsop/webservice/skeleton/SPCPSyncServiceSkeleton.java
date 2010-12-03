/**
 * SPCPSyncServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.webservice.bo.SPCPSyncServiceBo;
import com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse;
import com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  SPCPSyncServiceSkeleton java skeleton for the axisService
 *  
 * cooltan 20100603 !!!!!!  �ɵ�skeleton���ѷ���������ģ����ʹ�ã��������࣬���Ʋ���SCPInfoSynSVSkeleton !!!!!!!!
 */
public class SPCPSyncServiceSkeleton {
    /**
     * Auto generated method signature
     * @param cPSPInfoSyncReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReqResponse CPSPInfoSync(
        com.ztesoft.vsop.webservice.vo.CPSPInfoSyncReq cPSPInfoSyncReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
    	//1��ȡ��������
    	String requestXml=cPSPInfoSyncReq.getCPSPInfoSyncReq().getRequest();
    	//2����ҵ����
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXml) ;
		String responseXml = null ;
		try {
			responseXml = DataTranslate._String(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_CRMSPSynManager,
							"spcpInfoSyn" ,cparam));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//3���ؽ��
    	CPSPInfoSyncReqResponse cPSPInfoSyncReqResponse=new CPSPInfoSyncReqResponse();
    	com.ztesoft.vsop.webservice.vo.VsopServiceResponse vsopServiceResponse=new VsopServiceResponse();
    	vsopServiceResponse.setResponse(responseXml);
    	cPSPInfoSyncReqResponse.setCPSPInfoSyncReqResponse(vsopServiceResponse);
    	return cPSPInfoSyncReqResponse;
    }

    /**
     * Auto generated method signature
     * @param cPSPCapabilitySyncReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReqResponse CPSPCapabilitySync(
        com.ztesoft.vsop.webservice.vo.CPSPCapabilitySyncReq cPSPCapabilitySyncReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
        //throw new java.lang.UnsupportedOperationException("Please implement " +
          //  this.getClass().getName() + "#CPSPCapabilitySync");
    	//1��ȡ��������
    	String requestXml=cPSPCapabilitySyncReq.getCPSPCapabilitySyncReq().getRequest();
    	//2����ҵ����
    	String responseXml=SPCPSyncServiceBo.getInstance().CPSPCapabilitySync(requestXml);
    	//3���ؽ��
    	CPSPCapabilitySyncReqResponse cPSPCapabilitySyncReqResponse=new CPSPCapabilitySyncReqResponse();
    	com.ztesoft.vsop.webservice.vo.VsopServiceResponse vsopServiceResponse=new VsopServiceResponse();
    	vsopServiceResponse.setResponse(responseXml);
    	cPSPCapabilitySyncReqResponse.setCPSPCapabilitySyncReqResponse(vsopServiceResponse);
    	return cPSPCapabilitySyncReqResponse;
    }
}
