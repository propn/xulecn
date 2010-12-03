package com.ztesoft.vsop.engine.service.business;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * ��ֵ��Ʒ�˶�����
 * @author panshaohua
 *
 */
public class VProductCancelService extends AbstractBusinessService{
	public VProductCancelService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.������Ȩ����
		2.����������
	 *  3.����������ֵ��Ʒ���������
		4.���ݲ�Ʒʵ��������Ʒ�б������޸Ķ�����ϵʵ��״̬��
		5.�ͼ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		Map rets = new HashMap();
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1.������Ȩ����
			OrderAuthService aOrderAuthService=new OrderAuthService();
			Map ret=aOrderAuthService.service(in);
			String resultCode=(String)ret.get("resultCode");
			String resultMsg=(String)ret.get("resultMsg");
			rets.put("resultCode", resultCode);
			rets.put("resultMsg", resultMsg);
			if("0".equals(resultCode)){//��Ȩͨ��
				//2.����������
				CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
				aCustOrderHelpDao.insertOrder(customerOrder);
				//3.����������ֵ��Ʒ���������
				OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
				orderItemDao.insertOrderItemsByOrder(customerOrder);
				//4.���ݲ�Ʒʵ��������Ʒ�б������޸Ķ�����ϵʵ��״̬��
				OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
				String prodInstId = customerOrder.getProdInstId();
				List prodOfferInfos = customerOrder.getProductOfferInfoList();
				orderRelationDao.batchUpdateORStateByProductOfferInfos(prodInstId,prodOfferInfos);
				//5.�ͼ������
				SendActiveService sendActiveService = new SendActiveService();
				sendActiveService.service(in);
			}else{//��Ȩʧ��
				
			}
		}catch (Exception e) {
			//logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return rets;
	}

}
