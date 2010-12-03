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
 * �������
 * @author panshaohua
 *
 */
public class ProdInstDisassembleService extends AbstractBusinessService{
	public ProdInstDisassembleService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.����������
	   2.ͨ����������д����Ʒ������
	 * 3.���ݲ�Ʒʵ���޸��û�״̬��
	   4.���ݲ�Ʒʵ���޸Ķ�����ϵʵ��״̬ΪʧЧ
	   5.�û�״̬ͬ������
	   6.�����ص�����
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
			
			String state = ProdInstVO.StateOfDisassemble;//���״̬00X
			String prodInstId = customerOrder.getProdInstId();
			//4.���ݲ�Ʒʵ��idɾ��ҵ������
			BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
			aBizCapInstHelpDao.deleteBizCapInstByAprodusts(prodInstId,customerOrder.getAproductInfoList());
			//3.���ݲ�Ʒʵ���޸��û�״̬��
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstUserState(state,customerOrder.getProdInstId());
			//4.���ݲ�Ʒʵ���޸Ķ�����ϵʵ��״̬ΪʧЧ
			OrderRelationHelpDao orderRelation = new OrderRelationHelpDao();
			orderRelation.updateORSExpireByProdInstId(prodInstId);
			
			//5.�û�״̬ͬ��������ʱ��ͬ��
			UserInfoSynMsgService userInfoSyn = new UserInfoSynMsgService();
			userInfoSyn.service(in);
			//6.�����ص�����
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
