package com.ztesoft.vsop.order;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.log4j.Logger;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.skeleton.ReconfirmSVStub;
import com.ztesoft.vsop.webservice.vo.SendRQMessageReq;
import com.ztesoft.vsop.webservice.vo.SendRQMessageReqResponse;
import com.ztesoft.vsop.webservice.vo.VsopServiceRequest;

public class OrderMessConfirmBO {
	private static Logger logger = Logger.getLogger(OrderMessConfirmBO.class);
	public static String mesUrl = VsopConstants.DC_MES_URL;
//	public static String URL_AllOutSystemService;
	
	public OrderMessConfirmBO() {
		mesUrl = DcSystemParamManager.getParameter(VsopConstants.DC_MES_URL);
	}
	/**
	 * 二次确认短信发送
	 * @param order
	 * @return
	 * @throws RemoteException 
	 */
	public String sendSecondMesComfirm(String streamNo, 
									   String SystemId,
									   String ProdSpecCode,
									   String ProductNo,
									   String RQCode,
									   String RQContent) throws RemoteException{
		String reqXml = createRequestXml(streamNo,SystemId,ProdSpecCode,ProductNo,RQCode,RQContent);
		return sendMessage(reqXml);
	}
	/**
	 * 
	 * @param requestXml
	 * @return
	 * @throws RemoteException 
	 */
	public String sendMessage(String requestXml) throws RemoteException{
		String ret = "";
		logger.info("#sms requestXml:" + requestXml);
		ReconfirmSVStub reconfirmStub = new ReconfirmSVStub(mesUrl);
		SendRQMessageReq sendRQMessageReq12 = new SendRQMessageReq();
		VsopServiceRequest param = new VsopServiceRequest();
		param.setRequest(requestXml);
		sendRQMessageReq12.setSendRQMessageReq(param );
		SendRQMessageReqResponse respObj = reconfirmStub.sendRQMessage(sendRQMessageReq12 );
		ret = respObj.getSendRQMessageReqResponse().getResponse();
		logger.info("#sms responseXml:" + ret);
		return ret;
	}
	/**
	 * 拼接XML请求串
	 * @param streamNo
	 * @param SystemId
	 * @param ProdSpecCode
	 * @param ProductNo
	 * @param RQCode
	 * @param RQContent
	 * @return
	 */
	public String createRequestXml(String streamNo,
									String SystemId,
									String ProdSpecCode,
									String ProductNo,
									String RQCode,
									String RQContent) {
		StringBuffer bf = new StringBuffer();
		StringUtil su = StringUtil.getInstance();
//		VsopStreamNoHelper vs = VsopStreamNoHelper.getInstance();
		String timeStamp = su.getCurrentTimeStamp();
//		String stremNo = vs.genReqStreamNo();
		bf.append("<SendRQMsgFromVSOPReq>")
			.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
			.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
			.append("<SystemId>").append(SystemId).append("</SystemId>")
			.append("<ProdSpecCode>").append(ProdSpecCode).append("</ProdSpecCode>")
			.append("<ProductNo>").append(ProductNo).append("</ProductNo>")
			.append("<RQCode>").append(RQCode).append("</RQCode>")
			.append("<RQContent>").append(RQContent).append("</RQContent>")
			.append("</SendRQMsgFromVSOPReq>")
		;
		return su.getVsopRequest(streamNo, timeStamp, bf.toString());
//		return bf.toString();
	}

	
	
}
