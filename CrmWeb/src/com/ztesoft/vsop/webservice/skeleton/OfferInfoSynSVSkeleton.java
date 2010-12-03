/**
 * OfferInfoSynSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;
import com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  OfferInfoSynSVSkeleton java skeleton for the axisService
 */
public class OfferInfoSynSVSkeleton {
	private static Logger logger = Logger.getLogger(OfferInfoSynSVSkeleton.class);
    /**
     * Auto generated method signature
     * @param pPOfferInfoSyncReq
     */
    public com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReqResponse PPOfferInfoSync(
        com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReq pPOfferInfoSyncReq) {
        //TODO : fill this with the necessary business logic
    	//1��ȡ��������
    	String requestXml=pPOfferInfoSyncReq.getPPOfferInfoSyncReq().getRequest();
    	//2����ҵ����

    	Map xparam = new HashMap() ;
    	xparam.put("xml", requestXml) ;
    	xparam.put("sourceSystem", "0") ; //��ʵ�ǲ����жϵ��ǣ�Ŀǰ��д��0 Xƽ̨
		//System.out.println("*************************HashMap Product************************"+Product.toString());
		String responseXml = null;
		Map map=null;
		try {
			map = DataTranslate._Map(
					ServiceManager.callJavaBeanService("PPMOfferSynManager",
							"prodOffInfoSysn" ,xparam));
			responseXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		//2.2���洦��
		String prod_offer_id=(String)map.get("prod_offer_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("1", prod_offer_id);
		
    	//3���ؽ��
    	PPOfferInfoSyncReqResponse pPOfferInfoSyncReqResponse=new PPOfferInfoSyncReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	pPOfferInfoSyncReqResponse.setPPOfferInfoSyncReqResponse(param);
    	return pPOfferInfoSyncReqResponse;
    }
}
