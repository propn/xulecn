/**
 * ProdOfferSyncServiceSkeleton.java
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
import com.ztesoft.vsop.webservice.bo.ProdOfferServiceSyncBo;
import com.ztesoft.vsop.webservice.vo.*;

/**
 *  ProdOfferSyncServiceSkeleton java skeleton for the axisService
 * cooltan  20100603 !!!!!!  旧的skeleton，已废弃，仅仅模拟器使用，非生产类，请移步到OfferInfoSynSVSkeleton ProdInfoSynSVSkeleton OfferInfoSynFromISMPSVSkeleton ProdInfoSynFromISMPSVSkeleton !!!!!!!!
 */
public class ProdOfferSyncServiceSkeleton {
    /**
     * Auto generated method signature
     * @param productInfoSyncReq
     */
    public com.ztesoft.vsop.webservice.vo.ProductInfoSyncReqResponse productInfoSync(
        com.ztesoft.vsop.webservice.vo.ProductInfoSyncReq productInfoSyncReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=productInfoSyncReq.getProductInfoSyncReq().getRequest();
    	System.out.println("Server receive xml ========================\n"+requestXml) ;
    	//2具体业务处理
    	Map cparam = new HashMap() ;
    	cparam.put("xml", requestXml) ;
		String responseXml = null ;
		try {
			responseXml = DataTranslate._String(
					ServiceManager.callJavaBeanService(ServiceList.INTERFACE_PPMProductSysManager,
							"productInfoSys" ,cparam));
		} catch (Exception e) {
			e.printStackTrace();
		}
    	//3返回结果
    	ProductInfoSyncReqResponse productInfoSyncReqResponse=new ProductInfoSyncReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	productInfoSyncReqResponse.setProductInfoSyncReqResponse(param);
    	return productInfoSyncReqResponse;
    }

    /**
     * Auto generated method signature
     * @param packageInfoSyncReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.PackageInfoSyncReqResponse packageInfoSync(
        com.ztesoft.vsop.webservice.vo.PackageInfoSyncReq packageInfoSyncReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=packageInfoSyncReq.getPackageInfoSyncReq().getRequest();
    	
    	Map xparam = new HashMap() ;
    	xparam.put("xml", requestXml) ;
    	xparam.put("sourceSystem", "0") ;
		//System.out.println("*************************HashMap Product************************"+Product.toString());
		String responseXml = null;
		try {
			responseXml = DataTranslate._String(
					ServiceManager.callJavaBeanService("PPMOfferSynManager",
							"prodOffInfoSysn" ,xparam));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	//3返回结果
    	PackageInfoSyncReqResponse packageInfoSyncReqResponse=new PackageInfoSyncReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	packageInfoSyncReqResponse.setPackageInfoSyncReqResponse(param);
    	return packageInfoSyncReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param pPOfferInfoSyncReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReqResponse PPOfferInfoSync(
        com.ztesoft.vsop.webservice.vo.PPOfferInfoSyncReq pPOfferInfoSyncReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=pPOfferInfoSyncReq.getPPOfferInfoSyncReq().getRequest();
    	//2具体业务处理

    	Map xparam = new HashMap() ;
    	xparam.put("xml", requestXml) ;
    	xparam.put("sourceSystem", "1") ;
		//System.out.println("*************************HashMap Product************************"+Product.toString());
		String responseXml = null;
		try {
			responseXml = DataTranslate._String(
					ServiceManager.callJavaBeanService("PPMOfferSynManager",
							"prodOffInfoSysn" ,xparam));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
    	//3返回结果
    	PPOfferInfoSyncReqResponse pPOfferInfoSyncReqResponse=new PPOfferInfoSyncReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	pPOfferInfoSyncReqResponse.setPPOfferInfoSyncReqResponse(param);
    	return pPOfferInfoSyncReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }
}
