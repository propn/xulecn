package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * ����ͨ�ص�������
 * @author cooltan
 *
 */
public class FeedbackHelp {
	/**
	 * ���������ص�XML��Ϣ��
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
