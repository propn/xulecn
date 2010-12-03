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
 * 全部退订服务
 * @author cooltan
 *
 */

public class VproductAllCancelOrderService extends AbstractBusinessService {
	protected String provinceCode = DcSystemParamManager.getParameter("DC_PROVINCE_CODE");//省份代码

	public VproductAllCancelOrderService() {
		// TODO Auto-generated constructor stub
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.订购鉴权服务；
		2.新增订单；
		3.根据订单对象新增订单项；
		4.根据产品实例、销售品列表批量修改订购关系实例状态
		5.送激活服务。

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		//*******
		//广西本地化，在全部退订只有，捆绑+增值 的情况下，直接返回 yuanyang
		//*******
		if (CrmConstants.GX_PROV_CODE.equals(this.provinceCode) && order.isBreakScream()){
			return in;
		}
		//1.订购鉴权服务；
		OrderAuthService aOrderAuthService=new OrderAuthService();
		Map ret=aOrderAuthService.service(in);
		String resultCode=(String)ret.get("resultCode");
		String resultMsg=(String)ret.get("resultMsg");
		
		
		if("0".equals(resultCode)){//鉴权通过
			//2.新增订单；
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(order);
			//3.根据订单对象新增订单项；
			OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
			aOrderItemHelpDao.insertOrderItemsByOrder(order);
			//4.根据产品实例、销售品列表批量修改订购关系实例状态；
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			String prodInstId = order.getProdInstId();
			List prodOfferInfos = order.getProductOfferInfoList();
			orderRelationDao.batchUpdateORStateByProductOfferInfos(prodInstId,prodOfferInfos);
			//5.送激活服务
			SendActiveService aSendActiveService=new SendActiveService();
			aSendActiveService.service(in);
		}else{//鉴权失败
			
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
