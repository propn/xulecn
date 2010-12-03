/**
 * ProdInfoSynSVSkeleton.java
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
import com.ztesoft.vsop.webservice.vo.ProductInfoSyncReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 *  ProdInfoSynSVSkeleton java skeleton for the axisService
 */
public class ProdInfoSynSVSkeleton {
	private static Logger logger = Logger.getLogger(ProdInfoSynSVSkeleton.class);
    /**
     * Auto generated method signature
     * @param productInfoSyncReq
     */
    public com.ztesoft.vsop.webservice.vo.ProductInfoSyncReqResponse productInfoSync(
        com.ztesoft.vsop.webservice.vo.ProductInfoSyncReq productInfoSyncReq) {
    	//1获取请求内容
    	String requestXml=productInfoSyncReq.getProductInfoSyncReq().getRequest();
    	System.out.println("Server receive xml ========================\n"+requestXml) ;
    	//2具体业务处理
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXml) ;
		String responseXml = null ;
		Map map=null;
		try {
			map = DataTranslate._Map(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"productInfoSys" ,cparam));
			responseXml=(String)map.get("responseXml");
		} catch (Exception e) {
			logger.error("", e);
			e.printStackTrace();
		}
		//2.2缓存处理
		String product_id=(String)map.get("product_id");
		RefreshCacheHttpClient.getInstance().refreshAllServerCaches("0", product_id);
    	//3返回结果
    	ProductInfoSyncReqResponse productInfoSyncReqResponse=new ProductInfoSyncReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	productInfoSyncReqResponse.setProductInfoSyncReqResponse(param);
    	return productInfoSyncReqResponse;
    }
}
