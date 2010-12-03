package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
/**
 * ��װ����
 * @author cooltan
 *
 */
public class InstallService extends AbstractBusinessService {

	public InstallService() {
		this.setCommonBusinessOperationAfter(true);
		this.setCommonBusinessOperationBefore(true);
	}
	/**
	 *  //1.����������        
		//2���ݶ�������������������ݶ�������д����Ʒ�����
		//3.�������и�����Ʒ��������� 
		//4.������Ʒʵ�� ��   
		//5.��������ҵ������ʵ����
		//6.����������ֵ��Ʒ��������������ϵʵ������
		//7.�ͼ������       
		//8.�����ص�����
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		// TODO Auto-generated method stub
		
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		//1.���������� 
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(order);
		//2���ݶ�������������������ݶ�������д����Ʒ�����
		OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
		aOrderItemHelpDao.insertOrderItemsByOrder(order);
		aOrderItemHelpDao.insertMainProductOrderItemByOrder(order);
		//3.�������и�����Ʒ��������� 
		aOrderItemHelpDao.insertOrderItemsByAprodusts(order.getCustOrderId(), 
				order.getStatus(), order.getAccNbr(), order.getLanId(), 
				order.getProdInstId(), order.getAproductInfoList(),order.getCustOrderType());
		//4.������Ʒʵ�� �� 
		ProdInstVO prodInst =order.getProdInst();
		ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
		aProdInstHelpDao.insertProdInst(prodInst);
		//5.��������ҵ������ʵ����
		BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
		aBizCapInstHelpDao.insertBizCapInstByAprodusts(prodInst.getProdInstId(),order.getAproductInfoList());
		//6.����������ֵ��Ʒ��������������ϵʵ������
		OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
		aOrderRelationHelpDao.insertOrderRelaionsByCustomerOrder(order);
		//7.�ͼ������
		if(order.getProductOfferInfoList().size()>0){//�������ֵ��Ʒ�ͼ����������ɺ��ɼ���ģ��ص�
			SendActiveService aSendActiveService=new SendActiveService();
			aSendActiveService.service(in);
		}else{//������ͼ����ص�
		//8.�����ص�����
			SpOutMsgFeedbackService aSpOutMsgFeedbackService=new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}
		return in;
	}

	public int perform(DynamicDict dynamicDict) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
