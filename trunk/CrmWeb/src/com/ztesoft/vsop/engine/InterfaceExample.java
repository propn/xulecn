package com.ztesoft.vsop.engine;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.access.SubscribeReqSVAccessService;

public class InterfaceExample {
	public String example(String requestXml){
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubscribeReqSVAccessService();
		//采用Map作为出入参，每个步骤调用后都会在原来的map递增新的key，
		//进入步骤1前：key includes :accessInObject accessCode   accessType
		//步骤1完成后进入步骤2前：key includes :accessInObject accessCode   accessType busiObject serviceCode
		//步骤2完成后进入步骤3前：key includes :accessInObject accessCode   accessType busiObject serviceCode retBusiObject resultCode resultMsg
		//步骤3完成后：key includes :accessInObject accessCode   accessType busiObject serviceCode  resultCode resultMsg accessOutObject
		//1接口入参对象转换成核心对象
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeReqSVAccessService");//CustomerOrder已经包含在返回的accessInRet中了
		//CustomerOrder aCustomerOrder=(CustomerOrder)ret.get("busiObject");
		//2服务处理
		OrderEngine aOrderEngine=new OrderEngine();
		//accessInRet.put("serviceCode", "0");
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3核心对象转换成接口出参
		Map accessOut=aCommonAccessService.out(serviceOut,"SubscribeReqSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4写接口日志
		return responseXml;
		
	}
}
