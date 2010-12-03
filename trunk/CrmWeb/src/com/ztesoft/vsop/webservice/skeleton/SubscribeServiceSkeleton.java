/**
 * SubscribeServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;


import java.sql.SQLException;

import org.dom4j.DocumentException;

import com.ztesoft.vsop.webservice.bo.SubscribeServiceBo;
import com.ztesoft.vsop.webservice.vo.*;

/**
 *  SubscribeServiceSkeleton java skeleton for the axisService
 *  cooltan 20100603 !!!!!!  旧的skeleton，已废弃，仅仅模拟器使用，非生产类，请移步到SubscribeReqSVSkeleton等类 !!!!!!
 */
public class SubscribeServiceSkeleton {
    /**
     * Auto generated method signature
     * @param subscribeToVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReqResponse subscribeToVSOP(
        com.ztesoft.vsop.webservice.vo.SubscribeToVSOPReq subscribeToVSOPReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=subscribeToVSOPReq.getSubscribeToVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().subscribeToVSOP(requestXml);
    	//3返回结果
    	SubscribeToVSOPReqResponse subscribeToVSOPReqResponse=new SubscribeToVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subscribeToVSOPReqResponse.setSubscribeToVSOPReqResponse(param);
    	return subscribeToVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subInstQryFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReqResponse subInstQryFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubInstQryFromVSOPReq subInstQryFromVSOPReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=subInstQryFromVSOPReq.getSubInstQryFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().subInstQryFromVSOP(requestXml);
    	//3返回结果
    	SubInstQryFromVSOPReqResponse subInstQryFromVSOPReqResponse=new SubInstQryFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subInstQryFromVSOPReqResponse.setSubInstQryFromVSOPReqResponse(param);
    	return subInstQryFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subscribeAuthReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.SubscribeAuthReqResponse subscribeAuth(
        com.ztesoft.vsop.webservice.vo.SubscribeAuthReq subscribeAuthReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=subscribeAuthReq.getSubscribeAuthReq().getRequest();
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().subscribeAuth(requestXml);
    	//3返回结果
    	SubscribeAuthReqResponse subscribeAuthReqResponse=new SubscribeAuthReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subscribeAuthReqResponse.setSubscribeAuthReqResponse(param);
    	return subscribeAuthReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param workListFKToVSOPReq
     * @throws Exception 
     */
    public com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReqResponse workListFKToVSOP(
        com.ztesoft.vsop.webservice.vo.WorkListFKToVSOPReq workListFKToVSOPReq) throws Exception {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=workListFKToVSOPReq.getWorkListFKToVSOPReq().getRequest();
    	System.out.println("hi==============================\n"+requestXml) ;
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().workListFKToVSOP(requestXml);
    	//3返回结果
    	WorkListFKToVSOPReqResponse workListFKToVSOPReqResponse=new WorkListFKToVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	workListFKToVSOPReqResponse.setWorkListFKToVSOPReqResponse(param);
    	return workListFKToVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subsInstSynToVSOPReq
     * @throws SQLException 
     * @throws DocumentException 
     */
    public com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReqResponse subsInstSynToVSOP(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToVSOPReq subsInstSynToVSOPReq) throws DocumentException, SQLException {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=subsInstSynToVSOPReq.getSubsInstSynToVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().subsInstSynToVSOP(requestXml);
    	//3返回结果
    	SubsInstSynToVSOPReqResponse subsInstSynToVSOPReqResponse=new SubsInstSynToVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subsInstSynToVSOPReqResponse.setSubsInstSynToVSOPReqResponse(param);
    	return subsInstSynToVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param recvRQMessageReq
     */
    public com.ztesoft.vsop.webservice.vo.RecvRQMessageReqResponse recvRQMessage(
        com.ztesoft.vsop.webservice.vo.RecvRQMessageReq recvRQMessageReq) {
        //TODO : fill this with the necessary business logic
    	//1获取请求内容
    	String requestXml=recvRQMessageReq.getRecvRQMessageReq().getRequest();
    	//2具体业务处理
    	String responseXml=SubscribeServiceBo.getInstance().recvRQMessage(requestXml);
    	//3返回结果
    	RecvRQMessageReqResponse recvRQMessageReqResponse=new RecvRQMessageReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	recvRQMessageReqResponse.setRecvRQMessageReqResponse(param);
    	return recvRQMessageReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }
}
