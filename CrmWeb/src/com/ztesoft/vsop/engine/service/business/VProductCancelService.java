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
 * 增值产品退订服务
 * @author panshaohua
 *
 */
public class VProductCancelService extends AbstractBusinessService{
	public VProductCancelService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.订购鉴权服务；
		2.新增订单；
	 *  3.根据所有增值产品新增订单项；
		4.根据产品实例、销售品列表批量修改订购关系实例状态；
		5.送激活服务
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		Map rets = new HashMap();
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1.订购鉴权服务；
			OrderAuthService aOrderAuthService=new OrderAuthService();
			Map ret=aOrderAuthService.service(in);
			String resultCode=(String)ret.get("resultCode");
			String resultMsg=(String)ret.get("resultMsg");
			rets.put("resultCode", resultCode);
			rets.put("resultMsg", resultMsg);
			if("0".equals(resultCode)){//鉴权通过
				//2.新增订单；
				CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
				aCustOrderHelpDao.insertOrder(customerOrder);
				//3.根据所有增值产品新增订单项；
				OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
				orderItemDao.insertOrderItemsByOrder(customerOrder);
				//4.根据产品实例、销售品列表批量修改订购关系实例状态；
				OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
				String prodInstId = customerOrder.getProdInstId();
				List prodOfferInfos = customerOrder.getProductOfferInfoList();
				orderRelationDao.batchUpdateORStateByProductOfferInfos(prodInstId,prodOfferInfos);
				//5.送激活服务
				SendActiveService sendActiveService = new SendActiveService();
				sendActiveService.service(in);
			}else{//鉴权失败
				
			}
		}catch (Exception e) {
			//logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return rets;
	}

}
