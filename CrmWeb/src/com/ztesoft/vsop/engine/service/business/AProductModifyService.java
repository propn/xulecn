package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * �޸ĸ�����Ʒ����
 * @author panshaohua
 *
 */
public class AProductModifyService extends AbstractBusinessService{
	public AProductModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 *  1.��������
	 *  2.�������и�����Ʒ���������
	 *  3 ���ݶ�������д����Ʒ�����
		4.�����޸�ҵ������ʵ����
		5.�����ص�����

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1��������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.�������и�����Ʒ���������
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			String custOrderId=customerOrder.getCustOrderId();
			String orderSatus=customerOrder.getStatus();
			String accNbr=customerOrder.getAccNbr();
			String lanId=customerOrder.getLanId();
			String prodInstId=customerOrder.getProdInstId(); 
			List aprodList=customerOrder.getAproductInfoList();
			orderItemDao.insertOrderItemsByAprodusts(custOrderId,orderSatus,accNbr,
					lanId,prodInstId,aprodList,customerOrder.getCustOrderType());
			//3.���ݶ�������д����Ʒ�����
			OrderItemHelpDao orderItem = new OrderItemHelpDao();
			orderItem.insertMainProductOrderItemByOrder(customerOrder);
			//4.�����޸�ҵ������ʵ����
			BizCapInstHelpDao bizCapInstDao = new BizCapInstHelpDao();
			bizCapInstDao.modifyBizCapInstByAprodusts(prodInstId, aprodList);
			//5.�����ص�����
			SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
			feedbackService.service(in);
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			//logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}
	

}
