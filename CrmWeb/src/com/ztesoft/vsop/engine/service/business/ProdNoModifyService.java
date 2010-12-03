package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * �ĺŷ���
 * @author panshaohua
 *
 */
public class ProdNoModifyService extends AbstractBusinessService{
	public ProdNoModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.����������
	   2.ͨ����������д����Ʒ������
	 * 3.���ݲ�Ʒʵ���޸Ĳ�Ʒʵ�����롢��Ʒ���ͣ�
	 * 4.���ݲ�Ʒʵ���޸Ķ�����ϵʵ�����롢��Ʒ���ͣ�
	 * 5.�û�״̬ͬ������
	 * 6.�����ص�����

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
		//1��������
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(customerOrder);
		//2.ͨ����������д����Ʒ������
		OrderItemHelpDao orderItem = new OrderItemHelpDao();
		orderItem.insertMainProductOrderItemByOrder(customerOrder);
		//3.���ݲ�Ʒʵ���޸Ĳ�Ʒʵ�����롢��Ʒ���ͣ�
		String prodInstId = customerOrder.getProdInstId();
		String newAccNbr =customerOrder.getAccNbr();
		String newProdId = customerOrder.getProdId();
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
		//4.���ݲ�Ʒʵ���޸Ķ�����ϵʵ�����롢��Ʒ���ͣ�
		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
		orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
		//5.�û�״̬ͬ��������ʱ����ͬ���û�״̬
		UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
		userInfoService.service(in);
		//6.�����ص�����
		SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
		feedbackService.service(in);

		return in;
	}


}
