/**
 * AllOutSystemServiceSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import com.ztesoft.vsop.webservice.bo.AllOutSystemSimulateBo;
import com.ztesoft.vsop.webservice.vo.*;

/**
 *  AllOutSystemServiceSkeleton java skeleton for the axisService
 *  cooltan 20100603 !!!!!!  旧的skeleton，已废弃，仅仅模拟器使用，非生产类 !!!!!!!!
 */
public class AllOutSystemServiceSkeleton {
    /**
     * Auto generated method signature
     * @param subResultFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReqResponse subResultFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubResultFromVSOPReq subResultFromVSOPReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=subResultFromVSOPReq.getSubResultFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().subResultFromVSOP(requestXml);
    	//3返回结果
    	SubResultFromVSOPReqResponse subResultFromVSOPReqResponse=new SubResultFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subResultFromVSOPReqResponse.setSubResultFromVSOPReqResponse(param);
    	return subResultFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param sendRQMessageReq
     */
    public com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse sendRQMessage(
        com.ztesoft.vsop.webservice.vo.SendRQMessageReq sendRQMessageReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=sendRQMessageReq.getSendRQMessageReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().sendRQMessage(requestXml);
    	//3返回结果
    	SendRQMessageReqResponse sendRQMessageReqResponse=new SendRQMessageReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	sendRQMessageReqResponse.setSendRQMessageReqResponse(param);
    	return sendRQMessageReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subResulToUserReq
     */
    public com.ztesoft.vsop.webservice.vo.SubResulToUserReqResponse subResulToUser(
        com.ztesoft.vsop.webservice.vo.SubResulToUserReq subResulToUserReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=subResulToUserReq.getSubResulToUserReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().subResulToUser(requestXml);
    	//3返回结果
    	SubResulToUserReqResponse subResulToUserReqResponse=new SubResulToUserReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subResulToUserReqResponse.setSubResulToUserReqResponse(param);
    	return subResulToUserReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param workListVSOPToFKReq
     */
    public com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReqResponse workListVSOPToFK(
        com.ztesoft.vsop.webservice.vo.WorkListVSOPToFKReq workListVSOPToFKReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=workListVSOPToFKReq.getWorkListVSOPToFKReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().workListVSOPToFK(requestXml);
    	//3返回结果
    	WorkListVSOPToFKReqResponse workListVSOPToFKReqResponse=new WorkListVSOPToFKReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	workListVSOPToFKReqResponse.setWorkListVSOPToFKReqResponse(param);
    	return workListVSOPToFKReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param feeCheckReq
     */
    public com.ztesoft.vsop.webservice.vo.FeeCheckResp feeCheck(
        com.ztesoft.vsop.webservice.vo.FeeCheckReq feeCheckReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=feeCheckReq.getFeeCheckReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().feeCheck(requestXml);
    	//3返回结果
    	FeeCheckResp feeCheckReqResponse=new FeeCheckResp();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	feeCheckReqResponse.setFeeCheckResp(param);
    	return feeCheckReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subsInstSynToHBReq
     */
    public com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReqResponse subsInstSynToHB(
        com.ztesoft.vsop.webservice.vo.SubsInstSynToHBReq subsInstSynToHBReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=subsInstSynToHBReq.getSubsInstSynToHBReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().subsInstSynToHB(requestXml);
    	//3返回结果
    	SubsInstSynToHBReqResponse subsInstSynToHBReqResponse=new SubsInstSynToHBReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subsInstSynToHBReqResponse.setSubsInstSynToHBReqResponse(param);
    	return subsInstSynToHBReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param subActionFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReqResponse subActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.SubActionFromVSOPReq subActionFromVSOPReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=subActionFromVSOPReq.getSubActionFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().subActionFromVSOP(requestXml);
    	//3返回结果
    	SubActionFromVSOPReqResponse subActionFromVSOPReqResponse=new SubActionFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	subActionFromVSOPReqResponse.setSubActionFromVSOPReqResponse(param);
    	return subActionFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param chgActionFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReqResponse chgActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.ChgActionFromVSOPReq chgActionFromVSOPReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=chgActionFromVSOPReq.getChgActionFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().chgActionFromVSOP(requestXml);
    	//3返回结果
    	ChgActionFromVSOPReqResponse chgActionFromVSOPReqResponse=new ChgActionFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	chgActionFromVSOPReqResponse.setChgActionFromVSOPReqResponse(param);
    	return chgActionFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param unSubActionFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReqResponse unSubActionFromVSOP(
        com.ztesoft.vsop.webservice.vo.UnSubActionFromVSOPReq unSubActionFromVSOPReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=unSubActionFromVSOPReq.getUnSubActionFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().unSubActionFromVSOP(requestXml);
    	//3返回结果
    	UnSubActionFromVSOPReqResponse unSubActionFromVSOPReqResponse=new UnSubActionFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	unSubActionFromVSOPReqResponse.setUnSubActionFromVSOPReqResponse(param);
    	return unSubActionFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }

    /**
     * Auto generated method signature
     * @param staffAuthFromVSOPReq
     */
    public com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReqResponse staffAuthFromVSOP(
        com.ztesoft.vsop.webservice.vo.StaffAuthFromVSOPReq staffAuthFromVSOPReq) {
        //TODO : fill this with the necessary business logic
       	//1获取请求内容
    	String requestXml=staffAuthFromVSOPReq.getStaffAuthFromVSOPReq().getRequest();
    	//2具体业务处理
    	String responseXml=AllOutSystemSimulateBo.getInstance().staffAuthFromVSOP(requestXml);
    	//3返回结果
    	StaffAuthFromVSOPReqResponse staffAuthFromVSOPReqResponse=new StaffAuthFromVSOPReqResponse();
    	VsopServiceResponse param=new VsopServiceResponse();
    	param.setResponse(responseXml);
    	staffAuthFromVSOPReqResponse.setStaffAuthFromVSOPReqResponse(param);
    	return staffAuthFromVSOPReqResponse;
        //throw new java.lang.UnsupportedOperationException("Please implement " +
            //this.getClass().getName() + "#subscribeToVSOP");
    }
}
