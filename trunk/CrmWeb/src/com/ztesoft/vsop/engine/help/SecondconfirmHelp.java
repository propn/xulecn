package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * 二次确认辅助类
 * @author cooltan
 *
 */
public class SecondconfirmHelp {
	/**
	 * 创建二次确认XML信息
	 * @param order
	 * @param rqCode 二次确认随机码
	 * @param rqContent 短信内容
	 * @return
	 */
	public String createSecondconfirmXml(CustomerOrder order,String rqCode,String rqContent){
		StringBuffer bf = new StringBuffer();
		StringUtil su = StringUtil.getInstance();
		String timeStamp = su.getCurrentTimeStamp();
		String streamNo=order.getCustSoNumber();
		String prodType=order.getProdId();
		String accNbr=order.getAccNbr();
		bf.append("<SendRQMsgFromVSOPReq>")
			.append("<StreamingNo>").append(streamNo).append("</StreamingNo>")
			.append("<TimeStamp>").append(timeStamp).append("</TimeStamp>")
			.append("<SystemId>").append(VsopConstants.VSOP_SYSTEMID).append("</SystemId>")
			.append("<ProdSpecCode>").append(prodType).append("</ProdSpecCode>")
			.append("<ProductNo>").append(accNbr).append("</ProductNo>")
			.append("<RQCode>").append(rqCode).append("</RQCode>")
			.append("<RQContent>").append(rqContent).append("</RQContent>")
			.append("</SendRQMsgFromVSOPReq>")
		;
		return su.getVsopRequest(streamNo, timeStamp, bf.toString());
	}
	/**
	 * 根据订单对象生成二次确认短信内容； 暂未实现
	 * @param order
	 * @return
	 */
	public String createSecondfirmSms(CustomerOrder order){
		
		return "";
	}
}
