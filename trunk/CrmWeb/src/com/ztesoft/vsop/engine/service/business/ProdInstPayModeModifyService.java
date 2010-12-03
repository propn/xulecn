package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
/**
 * �������ͱ������
 * @author panshaohua
 *
 */
public class ProdInstPayModeModifyService extends AbstractBusinessService{
	public ProdInstPayModeModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.����������
	   2.ͨ����������д����Ʒ������
	 * 3.���ݲ�Ʒʵ���޸ĸ������ͣ�
	 * 4.�û�״̬ͬ������
	 * 5.�����ص�����
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1��������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.ͨ����������д����Ʒ������
			OrderItemHelpDao orderItemDao = new OrderItemHelpDao();
			orderItemDao.insertMainProductOrderItemByOrder(customerOrder);
			//3.�������и�����Ʒ��������� 
			//panshaohua add by 2010-8-19
			orderItemDao.insertOrderItemsByAprodusts(customerOrder.getCustOrderId(), 
					customerOrder.getStatus(), customerOrder.getAccNbr(), customerOrder.getLanId(), 
					customerOrder.getProdInstId(), customerOrder.getAproductInfoList(),customerOrder.getCustOrderType());
			//3.���ݲ�Ʒʵ���޸ĸ������ͣ�
			String paymode = customerOrder.getProdInst().getPaymentModeCd();
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstPayMode(paymode,customerOrder.getProdInstId());
			
			//4.��������ҵ������ʵ����
			//panshaohua add by 2010-8-19
			ProdInstVO prodInst =customerOrder.getProdInst();
			BizCapInstHelpDao bizCapInstDao = new BizCapInstHelpDao();
			bizCapInstDao.modifyBizCapInstByAprodusts(prodInst.getProdInstId(),customerOrder.getAproductInfoList());

			//4.�û�״̬ͬ��������ʱ��ͬ��״̬
			UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
			userInfoService.service(in);
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
