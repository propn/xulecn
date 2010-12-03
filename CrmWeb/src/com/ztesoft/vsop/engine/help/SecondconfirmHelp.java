package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * ����ȷ�ϸ�����
 * @author cooltan
 *
 */
public class SecondconfirmHelp {
	/**
	 * ��������ȷ��XML��Ϣ
	 * @param order
	 * @param rqCode ����ȷ�������
	 * @param rqContent ��������
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
	 * ���ݶ����������ɶ���ȷ�϶������ݣ� ��δʵ��
	 * @param order
	 * @return
	 */
	public String createSecondfirmSms(CustomerOrder order){
		
		return "";
	}
}
