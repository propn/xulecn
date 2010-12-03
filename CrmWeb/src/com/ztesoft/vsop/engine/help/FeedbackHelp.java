package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * 服务开通回单辅助类
 * @author cooltan
 *
 */
public class FeedbackHelp {
	/**
	 * 创建服开回单XML信息；
	 * @param order
	 * @return
	 */
	public String createFeedbackXml(CustomerOrder order){
		StringBuffer bf = new StringBuffer("");
		bf.append("<Request><SessionBody><WorkListVSOPToPFReq>")
				.append("<StreamingNo>").append(order.getCustSoNumber()).append("</StreamingNo>")
				.append("<TimeStamp>").append(System.currentTimeMillis()).append("</TimeStamp>")
				.append("<SystemId>").append(VsopConstants.VSOP_SYSTEMID).append("</SystemId>")
				.append("<OrigOrderId>").append(order.getOtherSysOrderId()).append("</OrigOrderId>")
				.append("<OrderResultCode>0</OrderResultCode>")
				.append("<OrderResultDesc>").append("success").append("</OrderResultDesc>")
			.append("</WorkListVSOPToPFReq></SessionBody></Request>")
		;
		return bf.toString();
	}
}
