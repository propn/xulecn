/**
 * ReconfirmSVSkeleton.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.3  Built on : Aug 10, 2007 (04:45:47 LKT)
 */
package com.ztesoft.vsop.webservice.skeleton;

import java.io.StringReader;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.webservice.bo.AllOutSystemSimulateBo;
import com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceResponse;

/**
 * ReconfirmSVSkeleton java skeleton for the axisService
 */
public class ReconfirmSVSkeleton {

	/**
	 * Auto generated method signature
	 * 
	 * 
	 * @param sendRQMessageReq
	 * @throws Exception 
	 */

	public com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse sendRQMessage(
			com.ztesoft.vsop.webservice.vo.SendRQMessageReq sendRQMessageReq) throws Exception {
		// 1获取请求内容
		String requestXml = sendRQMessageReq.getSendRQMessageReq().getRequest();
		System.out.println("requestXml:"+requestXml);
		// 2具体业务处理
		String responseXml = getResultByMes(requestXml, "0", "处理成功!");
		System.out.println("responseXml:"+responseXml);
		// 3返回结果
		SendRQMessageReqResponse sendRQMessageReqResponse = new SendRQMessageReqResponse();
		VsopServiceResponse param = new VsopServiceResponse();
		param.setResponse(responseXml);
		sendRQMessageReqResponse.setSendRQMessageReqResponse(param);
		return sendRQMessageReqResponse;
	}

	public static String getResultByMes(String xml, String resultCode,
			String resultDesc) throws DocumentException {
		String streamingNo = StringUtil.getInstance().getTagValue("StreamingNo", xml);
		String retXml = "";
		StringBuffer bf = new StringBuffer(
				"<Response>");
		bf.append("<SendRQMsgFromVSOPResp>").append("<StreamingNo>").append(
				streamingNo).append("</StreamingNo>").append("<ResultCode>")
				.append(resultCode).append("</ResultCode>").append(
						"<ResultDesc>").append(resultDesc).append(
						"</ResultDesc>").append("</SendRQMsgFromVSOPResp>").append("</Response>");
		retXml = bf.toString();
		return retXml;
	}
}
