package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * �̻��ƻ�����
 * @author Administrator
 *
 */
public class TransferUserService extends AbstractBusinessService{

	public TransferUserService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	1.����������
	 2.ͨ����������д����Ʒ������
	 3.�������и�����Ʒ��������� 
	 4.���ݶ�������������ֵ��Ʒ����� 
	 5.�����޸�ҵ������ʵ����
	 6.���ݶ������������޸Ķ�����ϵʵ���� 
	 7.���ݲ�Ʒʵ���޸Ĳ�Ʒʵ�����롢��Ʒ���ͣ�
	 8.���ݲ�Ʒʵ���޸Ķ�����ϵʵ�����롢��Ʒ���ͣ�
	 9.�û�״̬ͬ������
	 10.�������δ������ֵ��Ʒ������÷����ص����񣬷�������ͼ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
		//1��������
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(customerOrder);
		//2.ͨ����������д����Ʒ������
		OrderItemHelpDao orderItemDao = new OrderItemHelpDao();
		orderItemDao.insertMainProductOrderItemByOrder(customerOrder);
		
		//3.�������и�����Ʒ��������� 
		orderItemDao.insertOrderItemsByAprodusts(customerOrder.getCustOrderId(), 
				customerOrder.getStatus(), customerOrder.getAccNbr(), customerOrder.getLanId(), 
				customerOrder.getProdInstId(), customerOrder.getAproductInfoList(),customerOrder.getCustOrderType());

		// 4.���ݶ�������������ֵ��Ʒ�����
		orderItemDao.insertOrderItemsByOrder(customerOrder);

		String prodInstId = customerOrder.getProdInstId();
		String oldAccNbr = customerOrder.getOldAccNbr();
		//5.��������ҵ������ʵ����
		BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
		aBizCapInstHelpDao.insertBizCapInstByAprodusts(prodInstId,customerOrder.getAproductInfoList());
		// 6.���ݶ������������޸Ķ�����ϵʵ����
		OrderRelationHelpDao aOrderRelationHelpDao = new OrderRelationHelpDao();
		aOrderRelationHelpDao.modifyORSByCustomerOrder(customerOrder);
		//7.���ݲ�Ʒʵ���޸Ĳ�Ʒʵ�����롢��Ʒ���ͣ�
		if(null!=oldAccNbr && !"".equals(oldAccNbr) && !oldAccNbr.equals(customerOrder.getAccNbr())){
			String newAccNbr =customerOrder.getAccNbr();
			String newProdId = customerOrder.getProdId();
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
			
			//8.���ݲ�Ʒʵ���޸Ķ�����ϵʵ�����롢��Ʒ���ͣ�
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
			//9.�û�״̬ͬ������
			UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
			userInfoService.service(in);
		}
		
		// 10.�ͼ������
		if (customerOrder.getProductOfferInfoList().size() > 0) {// �������ֵ��Ʒ�ͼ����������ɺ��ɼ���ģ��ص�
			SendActiveService aSendActiveService = new SendActiveService();
			aSendActiveService.service(in);
		} else {// ������ͼ����ص�
			// 10.1�����ص�����
			SpOutMsgFeedbackService aSpOutMsgFeedbackService = new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}

		return in;
	}


}
