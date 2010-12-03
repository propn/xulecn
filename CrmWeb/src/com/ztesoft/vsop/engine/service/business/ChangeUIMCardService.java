package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * <pre>
 * Title:�û�����UIM��
 * Description: �����ͨϵͳPF�ṩ����UIM��webservice����
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * �޸ļ�¼
 *    �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����: 
 * </pre>
 */
public class ChangeUIMCardService extends AbstractBusinessService{
	public ChangeUIMCardService() {
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
//		OrderItemHelpDao orderItem = new OrderItemHelpDao();
//		orderItem.insertMainProductOrderItemByOrder(customerOrder);
		
		//3.���ݲ�Ʒʵ���޸Ĳ�Ʒʵ�����롢��Ʒ���ͣ�
//		String prodInstId = customerOrder.getProdInstId();
//		String newAccNbr =customerOrder.getAccNbr();
//		String newProdId = customerOrder.getProdId();
//		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
//		prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
		
		//3.���ݲ�Ʒʵ���޸�
		String newAccNbr =customerOrder.getAccNbr();
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		prodInstDao.updateProdInstUIMNO(customerOrder.getUimNO(),customerOrder.getOldUimNO(),newAccNbr);
		
		//4.���ݲ�Ʒʵ���޸Ķ�����ϵʵ�����롢��Ʒ���ͣ�
//		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
//		orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
		
		//5.�û�״̬ͬ������0
		//Xƽ̨ͬ��
		UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
		userInfoService.service(in);
		//ismpͬ��
		
		
		
		//6.�����ص�����
		SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
		feedbackService.service(in);

		return in;
	}

}
