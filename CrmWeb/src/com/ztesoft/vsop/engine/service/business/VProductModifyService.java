package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * 修改增值产品服务
 * @author panshaohua
 *
 */
public class VProductModifyService extends AbstractBusinessService{
	public VProductModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.新增订单；
	 *  2.根据所有增值产品新增订单项；
		3.根据订单对象批量修改订购关系实例；
		4.送激活服务
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1.新增订单；
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.根据所有增值产品新增订单项；
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsByOrder(customerOrder);
			//3.根据订单对象批量修改订购关系实例；
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			orderRelationDao.modifyORSByCustomerOrder(customerOrder);
			//4.送激活服务
			SendActiveService sendActiveService = new SendActiveService();
			sendActiveService.service(in);
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}

}
