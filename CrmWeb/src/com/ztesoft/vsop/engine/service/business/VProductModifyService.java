package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * �޸���ֵ��Ʒ����
 * @author panshaohua
 *
 */
public class VProductModifyService extends AbstractBusinessService{
	public VProductModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.����������
	 *  2.����������ֵ��Ʒ���������
		3.���ݶ������������޸Ķ�����ϵʵ����
		4.�ͼ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1.����������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.����������ֵ��Ʒ���������
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsByOrder(customerOrder);
			//3.���ݶ������������޸Ķ�����ϵʵ����
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			orderRelationDao.modifyORSByCustomerOrder(customerOrder);
			//4.�ͼ������
			SendActiveService sendActiveService = new SendActiveService();
			sendActiveService.service(in);
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}

}
