package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.InfMsgHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * 服务开通工单异步处理服务 注意这个是特殊的服务，仅仅完成服务开通处理队列的写表。
 * @author cooltan
 *
 */
public class WorkSheetAcceptService extends AbstractBusinessService {
	public WorkSheetAcceptService(){
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	public Map concreteBusinessOpertions(Map in) throws Exception {
		// TODO Auto-generated method stub
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		String requestXml=(String)in.get("accessInObject");
		InfMsgHelpDao aInfMsgHelpDao=new InfMsgHelpDao();
		aInfMsgHelpDao.saveInfMsg(order.getOrderSystem(), requestXml, order.getAccNbr(),order.getOtherSysOrderId());
		in.put("resultCode", "0");
		in.put("resultMsg", "success");
		return in;
	}
	
	/*public String toXml(CustomerOrder order){
		StringBuffer bf = new StringBuffer();
		bf
		.append("<WorkListFKToVSOPReq>")
			.append("<StreamingNo>").append(order.getCustSoNumber())).append("</StreamingNo>")
			.append("<TimeStamp>").append(order.getReceiveDate()).append("</TimeStamp>")
			.append("<SystemId>").append(order.getOrderSystem()).append("</SystemId>")
			.append("<OrderId>").append(order.getOtherSysOrderId()).append("</OrderId>")
			.append("<InOrderId>").append(order.getCustOrderId()).append("</InOrderId>")
			.append("<ActionType>").append(order.getCustOrderType()).append("</ActionType>")
			.append("<PorductInstID>").append(order.getProdInstId()).append("</PorductInstID>")
			.append("<ReginID>").append(order.getLanId()).append("</ReginID>")
			.append("<ProdSpecCode>").append(order.getProdId()).append("</ProdSpecCode>")
			.append("<ProductNo>").append(order.getAccNbr()).append("</ProductNo>")
			.append("<OldProductNo>").append(order.getOldAccNbr()).append("</OldProductNo>")
			.append("<ProductOfferId>").append(getProductOfferId()).append("</ProductOfferId>")
			.append("<OffNbr>").append(getOfferNbr()).append("</OffNbr>")
			.append("<UserState>").append(getUserState()).append("</UserState>")
			.append("<UserPayType>").append(getUserPayType()).append("</UserPayType>");
		List vprodList = getVProductInfoList();
		if(vprodList != null){
			for (Iterator vprodItr = vprodList.iterator(); vprodItr.hasNext();) {
				VProductInfo vprod = (VProductInfo) vprodItr.next();
				bf.append(vprod.toXml());
			}
		}
		List aprodList = getAProductInfoList();
		if(aprodList != null){
			for (Iterator aprodItr = aprodList.iterator(); aprodItr.hasNext();) {
				AProductInfo aprod = (AProductInfo) aprodItr.next();
				bf.append(aprod.toXml());
			}
		}
		bf.append("</WorkListFKToVSOPReq>");
		return bf.toString();
	}*/

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
