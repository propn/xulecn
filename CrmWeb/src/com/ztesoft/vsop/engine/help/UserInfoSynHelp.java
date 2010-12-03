package com.ztesoft.vsop.engine.help;

import com.ztesoft.vsop.ConstantsState;
import com.ztesoft.vsop.StringUtil;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.VsopStreamNoHelper;
import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;

/**
 * �û�״̬ͬ��������
 * 
 * @author cooltan
 * 
 */
public class UserInfoSynHelp {
	/**
	 * ���ݶ������󡢲�Ʒʵ�����󴴽��û�״̬ͬ��XML��Ϣ
	 * 
	 * @param order
	 * @param prodInstvo
	 * @return
	 */
	public String createUserSynXml(CustomerOrder order,ProdInstVO prodInstvo){
		return this.createUserInfoSyncFromVSOPRequestBodyXml(order, prodInstvo);
	}

	private String createUserInfoSyncFromVSOPRequestBodyXml(			CustomerOrder order, ProdInstVO prodInstvo) {
		String streamNo = VsopStreamNoHelper.getInstance().genReqStreamNo();// ��ˮ��
		String timestamp = StringUtil.getInstance().getCurrentTimeStamp();// ʱ��
		String systemId = VsopConstants.VSOP_SYSTEMID;// VSOPϵͳ��ʶ
		String actionType = order.getCustOrderType();// ������������
		String prodSpecCode = order.getProdId();// ��Ʒ����
		String productNo = order.getAccNbr();// ��Ʒ����
		String oldProductNo = null;// ԭ��Ʒ����
		String userState = null;// ��Ʒʵ��״̬
		String userPayType = null;// ��Ʒʵ����������

		String uimNO = order.getUimNO();// UIM������
		String oldUimNO = order.getOldUimNO();// �û���UIM������

		if (OrderConstant.orderTypeOfModifyState.equals(actionType)) { // �û�״̬���
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
		}else if(OrderConstant.orderTypeOfModifyNo.equals(actionType)){ //�ĺ�
			oldProductNo = order.getOldAccNbr();
		}else if(OrderConstant.orderTypeOfUninstall.equals(actionType)){ //���
			
		}else if(OrderConstant.orderTypeOfModifyPayType.equals(actionType)){ //�������ͱ��
			userPayType = prodInstvo.getPaymentModeCd();
		} else if (OrderConstant.orderTypeOfChangeUIM.equals(actionType)) {// ����UIM��
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
		//����UIM��Ϣ
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
