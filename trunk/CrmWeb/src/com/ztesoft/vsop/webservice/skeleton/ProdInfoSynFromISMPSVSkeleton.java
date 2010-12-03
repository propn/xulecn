/**
 * ProdInfoSynFromISMPSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.vsop.web.RefreshCacheHttpClient;
import com.ztesoft.vsop.webservice.vo.ProdInfoSynFromISMPSVReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  ProdInfoSynFromISMPSVSkeleton java skeleton for the axisService
 */
public class ProdInfoSynFromISMPSVSkeleton {
	private static Logger logger = Logger.getLogger(ProdInfoSynFromISMPSVSkeleton.class);
    /**
     * Auto generated method signature
     * @param prodInfoSynFromISMPSVReq
     */
    public com.ztesoft.vsop.webservice.vo.ProdInfoSynFromISMPSVReqResponse prodInfoSynFromISMPSV(
        com.ztesoft.vsop.webservice.vo.ProdInfoSynFromISMPSVReq prodInfoSynFromISMPSVReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=prodInfoSynFromISMPSVReq.getProdInfoSynFromISMPSVReq().getRequest();
    	logger.info("Server receive xml ========================\n"+requestXml) ;
    	//2具体业务处理
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXml) ;
		String responseXml = null ;
		Map map=null;
		try {
			 map = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"productInfoSysIsmp" ,cparam));
			responseXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		//2.2缓存处理
		String product_id=(String)map.get("product_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("0", product_id);
    	//3返回结果
		ProdInfoSynFromISMPSVReqResponse ismpReqResponse=new ProdInfoSynFromISMPSVReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	ismpReqResponse.setProdInfoSynFromISMPSVReqResponse(param);
    	return ismpReqResponse;
    }
}
