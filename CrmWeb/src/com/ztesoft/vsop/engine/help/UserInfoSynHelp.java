package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.ConstantsState;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;

/**
 * 用户状态同步辅助类
 * 
 * @author cooltan
 * 
 */
public class UserInfoSynHelp {
	/**
	 * 根据订单对象、产品实例对象创建用户状态同步XML信息
	 * 
	 * @param order
	 * @param prodInstvo
	 * @return
	 */
	public String createUserSynXml(CustomerOrder order,ProdInstVO prodInstvo){
		return this.createUserInfoSyncFromVSOPRequestBodyXml(order, prodInstvo);
	}

	private String createUserInfoSyncFromVSOPRequestBodyXml(			CustomerOrder order, ProdInstVO prodInstvo) {
		String streamNo = VsopStreamNoHelper.getInstance().genReqStreamNo();// 流水号
		String timestamp = StringUtil.getInstance().getCurrentTimeStamp();// 时间
		String systemId = VsopConstants.VSOP_SYSTEMID;// VSOP系统标识
		String actionType = order.getCustOrderType();// 订单服务类型
		String prodSpecCode = order.getProdId();// 产品类型
		String productNo = order.getAccNbr();// 产品号码
		String oldProductNo = null;// 原产品号码
		String userState = null;// 产品实例状态
		String userPayType = null;// 产品实例付费类型

		String uimNO = order.getUimNO();// UIM卡号码
		String oldUimNO = order.getOldUimNO();// 用户旧UIM卡号码

		if (OrderConstant.orderTypeOfModifyState.equals(actionType)) { // 用户状态变更
			userState = prodInstvo.getStateCd();
			if(ConstantsState.USER_STATE_USED.equals(userState)){
				userState = "0";
			}else if(ConstantsState.USER_STATE_STOP.equals(userState)){
				userState = "1";
			}else if(ConstantsState.USER_STATE_00C.equals(userState)){
				userState = "2";
			}else if(ConstantsState.USER_STATE_UNUSED.equals(userState)){
				userState = "3";
			}
		}else if(OrderConstant.orderTypeOfModifyNo.equals(actionType)){ //改号
			oldProductNo = order.getOldAccNbr();
		}else if(OrderConstant.orderTypeOfUninstall.equals(actionType)){ //拆机
			
		}else if(OrderConstant.orderTypeOfModifyPayType.equals(actionType)){ //付费类型变更
			userPayType = prodInstvo.getPaymentModeCd();
		} else if (OrderConstant.orderTypeOfChangeUIM.equals(actionType)) {// 更换UIM卡
																			// xulei
			uimNO = order.getUimNO();
			oldUimNO = order.getOldUimNO();
		}
		String requestBodyXml = this.createUserInfoSyncFromVSOPRequestBodyXml(
				streamNo, timestamp, systemId, actionType, prodSpecCode,
				productNo, oldProductNo, userState, userPayType,uimNO,oldUimNO);
		return StringUtil.getInstance().getVsopRequest(streamNo, timestamp,
				requestBodyXml);
	}
	private String createUserInfoSyncFromVSOPRequestBodyXml(String streamingNo,
			String timeStamp, String systemId, String actionType,
			String prodSpecCode, String productNo, String oldProductNo,
			String userState, String userPayType,String uimNO,String oldUimNO) {
		StringBuffer bf = new StringBuffer("");
		bf.append("<UserInfoSyncFromVSOPReq>").append("<StreamingNo>").append(
				streamingNo).append("</StreamingNo>").append("<TimeStamp>")
				.append(timeStamp).append("</TimeStamp>").append("<SystemId>")
				.append(systemId).append("</SystemId>").append("<ActionType>")
				.append(actionType).append("</ActionType>").append(
						"<ProdSpecCode>").append(prodSpecCode).append(
						"</ProdSpecCode>").append("<ProductNo>").append(
						productNo).append("</ProductNo>");
		if (oldProductNo != null)
			bf.append("<OldProductNo>").append(oldProductNo).append(
					"</OldProductNo>");
		if (userState != null)
			bf.append("<UserState>").append(userState).append("</UserState>");
		if (userPayType != null)
			bf.append("<UserPayType>").append(userPayType).append(
					"</UserPayType>");
		//增加UIM信息
		if (uimNO != null)
			bf.append("<UIMNo>").append(uimNO).append(
					"</UIMNo>");
		if (oldUimNO != null)
			bf.append("<OldUIMNo>").append(oldUimNO).append(
					"</OldUIMNo>");
		bf.append("</UserInfoSyncFromVSOPReq>");
		return bf.toString();
	}
}
