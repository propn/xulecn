package com.ztesoft.vsop.order.vo.response;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.ztesoft.vsop.order.exception.VerifyException;

public class SubscribeToVSOPResp extends BaseResponse {
	private static Logger logger = Logger.getLogger(SubscribeToVSOPResp.class);
	private List resultInfo = new ArrayList();
	
	public SubscribeToVSOPResp(String resultCode,String resultDesc,String streamingNo) {
		setResultCode(resultCode);
		setResultDesc(resultDesc);
		setStreamingNo(streamingNo);
	}
	
	public SubscribeToVSOPResp(Exception ex,String streamingNo) {
		setStreamingNo(streamingNo);
		if (ex instanceof DocumentException) {
			setResultCode(SubscribeToVSOPResp.RESULT_FAILURE);
			setResultDesc("DocumentException,please check xml format.");
		}
		if (ex instanceof VerifyException) {
			VerifyException vex = (VerifyException) ex;
			setResultCode(SubscribeToVSOPResp.RESULT_FAILURE);
			setResultDesc(vex.getMessage());
		}
		else{
			setResultCode(SubscribeToVSOPResp.RESULT_FAILURE);
			setResultDesc("Exception " + ex.getMessage());
		}
		logger.error("",ex);
	}


	public SubscribeToVSOPResp() {
		super();
	}
	
	public String toXml(){
		StringBuffer bf = new StringBuffer();
		bf.append("<Response>");
		bf.append("<").append("SubscribeToVSOPResp").append(">")
				.append("<StreamingNo>").append(getStreamingNo()).append("</StreamingNo>")
				.append("<ResultCode>").append(getResultCode()).append("</ResultCode>")
				.append("<ResultDesc>").append(getResultDesc()).append("</ResultDesc>");
				if(resultInfo != null && resultInfo.size() > 0){
					for (Iterator iterator = resultInfo.iterator(); iterator
							.hasNext();) {
						Map	 tmp = (Map) iterator.next();
						bf.append("<resultInfo>")
							.append("<ProductOfferID>").append((String)tmp.get("ProductOfferID")).append("</ProductOfferID>")
							.append("<OPResult>").append((String)tmp.get("OPResult")).append("</OPResult>")
							.append("<OPDesc>").append((String)tmp.get("OPDesc")).append("</OPDesc>")
						.append("</resultInfo>");
					}
				}
		bf.append("</").append("SubscribeToVSOPResp").append(">");
		bf.append("</Response>");
		return bf.toString();
	}
	
	
	public List getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(List resultInfo) {
		this.resultInfo = resultInfo;
	}
	
}
