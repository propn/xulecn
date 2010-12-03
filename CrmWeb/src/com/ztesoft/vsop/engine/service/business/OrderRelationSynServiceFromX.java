package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * <pre>
 * Title:���Ź淶 Xƽ̨ͬ��������ϵ��VSOP
 * Description: ������ϵͬ��(Xƽ̨-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version R7.8
 * 
 * <pre>
 * �޸ļ�¼
 * �޸ĺ�汾:     �޸��ˣ�  �޸�����:     �޸�����:
 * </pre>
 */
public class OrderRelationSynServiceFromX extends AbstractBusinessService{
	public OrderRelationSynServiceFromX() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 *  1.����������
		2.���ݶ����������������
		3.���ݶ������������޸Ķ�����ϵʵ����
		4.ȫ��������ϵͬ������
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			
			//1.����������
			CustOrderHelpDao custOrderHelpDao=new CustOrderHelpDao();
			custOrderHelpDao.insertOrderHis(customerOrder);
			//2.����������ֵ��Ʒ���������
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsHisByOrder(customerOrder);
			//3.���ݶ������������޸Ķ�����ϵʵ����
			customerOrder.setSendActiveFlag(false);//Xƽ̨ͬ�����ͼ���
			orderRelationDao.modifyORSByCustomerOrder(customerOrder);
		
			//4.ȫ��������ϵͬ������
//			OrderRelationSynWholeService orderRelationSynWholeService = new OrderRelationSynWholeService();
//			orderRelationSynWholeService.service(in);
			
			in.put("resultCode", "0");
			in.put("resultMsg", "�ɹ�");
			
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}

}
