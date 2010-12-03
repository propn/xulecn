package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * �û�״̬�������
 * @author panshaohua
 *
 */
public class UserStateChangeService extends AbstractBusinessService{
	public UserStateChangeService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.����������
		2.2.ͨ����������д����Ʒ������
	 * 3.���ݲ�Ʒʵ���޸��û�״̬��
	   4.�û�״̬ͬ������
	   5.�����ص�����
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1��������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.ͨ����������д����Ʒ������
			OrderItemHelpDao orderItem = new OrderItemHelpDao();
			orderItem.insertMainProductOrderItemByOrder(customerOrder);
			//3.���ݲ�Ʒʵ���޸��û�״̬��
			String state = customerOrder.getProdInst().getStateCd();
			ProdInstHelpDao prodInst = new ProdInstHelpDao();
			prodInst.updateProdInstUserState(state,customerOrder.getProdInstId());
			//4.�û�״̬ͬ������һ�ڹ����ͽ���������Ҫ״̬ͬ��
			UserInfoSynMsgService userInfoSyn = new UserInfoSynMsgService();
			userInfoSyn.concreteBusinessOpertions(in);
			
			//4.�����ص�����
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
