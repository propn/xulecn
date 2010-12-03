package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;

/**
 * ���������ӿ��������񣺸ķ�����
 * 
 * @author cooltan
 * 
 */
public class ModifyServiceFunctionService extends AbstractBusinessService {
	public ModifyServiceFunctionService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}

	/**
	 * 1.���������� 
	 * 2���ݶ�������д����Ʒ����� 
	 * 3.�������и�����Ʒ��������� 
	 * 4.���ݶ�������������ֵ��Ʒ����� 
	 * 5.�����޸�ҵ������ʵ����
	 * 6.���ݶ������������޸Ķ�����ϵʵ���� 
	 * 7.�������δ������ֵ��Ʒ������÷����ص����񣬷�������ͼ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order = (CustomerOrder) in.get("busiObject");
		// 1.����������
		CustOrderHelpDao aCustOrderHelpDao = new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(order);
		// 2.���ݶ�������д����Ʒ�����
		OrderItemHelpDao aOrderItemHelpDao = new OrderItemHelpDao();
		aOrderItemHelpDao.insertMainProductOrderItemByOrder(order);
		// 3.�������и�����Ʒ���������
		aOrderItemHelpDao.insertOrderItemsByAprodusts(order.getCustOrderId(),
				order.getStatus(), order.getAccNbr(), order.getLanId(), order
						.getProdInstId(), order.getAproductInfoList(), order
						.getCustOrderType());
		// 4.���ݶ�������������ֵ��Ʒ�����
		aOrderItemHelpDao.insertOrderItemsByOrder(order);
		// 5.�����޸�ҵ������ʵ����
		ProdInstVO prodInst = order.getProdInst();
		BizCapInstHelpDao aBizCapInstHelpDao = new BizCapInstHelpDao();
		aBizCapInstHelpDao.modifyBizCapInstByAprodusts(
				prodInst.getProdInstId(), order.getAproductInfoList());
		// 6.���ݶ������������޸Ķ�����ϵʵ����
		OrderRelationHelpDao aOrderRelationHelpDao = new OrderRelationHelpDao();
		aOrderRelationHelpDao.modifyORSByCustomerOrder(order);
		// 7.�ͼ������
		if (order.getProductOfferInfoList().size() > 0) {// �������ֵ��Ʒ�ͼ����������ɺ��ɼ���ģ��ص�
			SendActiveService aSendActiveService = new SendActiveService();
			aSendActiveService.service(in);
		} else {// ������ͼ����ص�
			// 8.�����ص�����
			SpOutMsgFeedbackService aSpOutMsgFeedbackService = new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}

		return in;
	}

}
