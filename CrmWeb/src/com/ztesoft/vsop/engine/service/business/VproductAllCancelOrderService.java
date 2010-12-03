package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * ȫ���˶�����
 * @author cooltan
 *
 */

public class VproductAllCancelOrderService extends AbstractBusinessService {
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//ʡ�ݴ���

	public VproductAllCancelOrderService() {
		// TODO Auto-generated constructor stub
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.������Ȩ����
		2.����������
		3.���ݶ����������������
		4.���ݲ�Ʒʵ��������Ʒ�б������޸Ķ�����ϵʵ��״̬
		5.�ͼ������

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		//*******
		//�������ػ�����ȫ���˶�ֻ�У�����+��ֵ ������£�ֱ�ӷ��� yuanyang
		//*******
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && order.isBreakScream()){
			return in;
		}
		//1.������Ȩ����
		OrderAuthService aOrderAuthService=new OrderAuthService();
		Map ret=aOrderAuthService.service(in);
		String resultCode=(String)ret.get("resultCode");
		String resultMsg=(String)ret.get("resultMsg");
		
		
		if("0".equals(resultCode)){//��Ȩͨ��
			//2.����������
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(order);
			//3.���ݶ����������������
			OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
			aOrderItemHelpDao.insertOrderItemsByOrder(order);
			//4.���ݲ�Ʒʵ��������Ʒ�б������޸Ķ�����ϵʵ��״̬��
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			String prodInstId = order.getProdInstId();
			List prodOfferInfos = order.getProductOfferInfoList();
			orderRelationDao.batchUpdateORStateByProductOfferInfos(prodInstId,prodOfferInfos);
			//5.�ͼ������
			SendActiveService aSendActiveService=new SendActiveService();
			aSendActiveService.service(in);
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
