package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * ��ֵ��Ʒ��������
 * @author cooltan
 *
 */

public class VproductOrderService extends AbstractBusinessService {

	public VproductOrderService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.������Ȩ����
		2.����������
		3.����������ֵ��Ʒ���������
		4.���ݶ�������������������ϵʵ����
		5.����ȷ�Ϸ���
		6.�ͼ������

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		//1.������Ȩ����1111
		OrderAuthService aOrderAuthService=new OrderAuthService();
		Map ret=aOrderAuthService.service(in);
		String resultCode=(String)ret.get("resultCode");
		String resultMsg=(String)ret.get("resultMsg");
		
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		if("0".equals(resultCode)){//��Ȩͨ��
			//2.����������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(order);
			//3.����������ֵ��Ʒ���������
			OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
			aOrderItemHelpDao.insertOrderItemsByOrder(order);
			//4.���ݶ�������������������ϵʵ����
			OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
			aOrderRelationHelpDao.insertOrderRelaionsByCustomerOrder(order);
			if(OrderConstant.orderStateOfCreated.equals(order.getStatus())){
				//6.�ͼ������
				SendActiveService aSendActiveService=new SendActiveService();
				aSendActiveService.service(in);
			}else{
				//5.����ȷ�Ϸ���         �°�淶�Ѿ�ȥ������ȷ�ϣ��ݲ�����
			}
		}else{//��Ȩʧ��
			
		}
		return in;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
