package com.ztesoft.vsop.order;

import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopStreamNoHelper;

/**
 * �������ɷ���˵�����xml�ַ���
 * @author yi.chengfeng Apr 19, 2010 9:22:46 AM
 *
 */
public class ServiceRequestXmlHelper {
	private static ServiceRequestXmlHelper helper = new ServiceRequestXmlHelper();
	private ServiceRequestXmlHelper(){}
	public static ServiceRequestXmlHelper instance(){
		return helper;
	}
	
	/**
	 * ȫ���˶�
	 * @param systemId 
	 * @param actionType 
	 * @param orderId 
	 * @param productId 
	 * @param productNo 
	 * @return
	 */
	public String unSubscribeAllToVSOP(String systemId, String actionType, String orderId, String productId, String productNo){
		StringBuffer bf = new StringBuffer("");
		bf.append("<SubscribeToVSOPReq>")
			.append("<StreamingNo>").append(VsopStreamNoHelper.getInstance().genReqStreamNo()).append("</StreamingNo>")
			.append("<TimeStamp>").append(StringUtil.getInstance().getCurrentTimeStamp()).append("</TimeStamp>")
			.append("<SystemId>").append(systemId).append("</SystemId>")
			.append("<ActionType>").append(actionType).append("</ActionType>")
			.append("<OrderId>").append(orderId).append("</OrderId>")
			.append("<ProdSpecCode>").append(productId).append("</ProdSpecCode>")
			.append("<ProductNo>").append(productNo).append("</ProductNo>")
			.append("<ProductOfferInfo>")
			.append("</ProductOfferInfo>")
		.append("</SubscribeToVSOPReq>");
		return bf.toString();
	}
	
	
}
