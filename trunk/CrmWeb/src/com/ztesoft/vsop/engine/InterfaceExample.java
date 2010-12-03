package com.ztesoft.vsop.engine;

import java.util.HashMap;
import java.util.Map;

import com.ztesoft.vsop.engine.service.CommonAccessService;
import com.ztesoft.vsop.engine.service.access.SubscribeReqSVAccessService;

public class InterfaceExample {
	public String example(String requestXml){
		String responseXml=null;
		CommonAccessService aCommonAccessService=new SubscribeReqSVAccessService();
		//����Map��Ϊ����Σ�ÿ��������ú󶼻���ԭ����map�����µ�key��
		//���벽��1ǰ��key includes :accessInObject accessCode   accessType
		//����1��ɺ���벽��2ǰ��key includes :accessInObject accessCode   accessType busiObject serviceCode
		//����2��ɺ���벽��3ǰ��key includes :accessInObject accessCode   accessType busiObject serviceCode retBusiObject resultCode resultMsg
		//����3��ɺ�key includes :accessInObject accessCode   accessType busiObject serviceCode  resultCode resultMsg accessOutObject
		//1�ӿ���ζ���ת���ɺ��Ķ���
		Map accessIn =new HashMap();
		accessIn.put("accessInObject", requestXml);
		accessIn.put("accessCode", "R1.1");
		accessIn.put("accessType", "");
		Map accessInRet=aCommonAccessService.in(accessIn,"SubscribeReqSVAccessService");//CustomerOrder�Ѿ������ڷ��ص�accessInRet����
		//CustomerOrder aCustomerOrder=(CustomerOrder)ret.get("busiObject");
		//2������
		OrderEngine aOrderEngine=new OrderEngine();
		//accessInRet.put("serviceCode", "0");
		Map serviceOut=aOrderEngine.engine(accessInRet);
		//3���Ķ���ת���ɽӿڳ���
		Map accessOut=aCommonAccessService.out(serviceOut,"SubscribeReqSVAccessService");
		responseXml=(String)accessOut.get("accessOutObject");
		//4д�ӿ���־
		return responseXml;
		
	}
}
