package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.OrderConstant;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * 增值产品订购服务
 * @author cooltan
 *
 */

public class VproductOrderService extends AbstractBusinessService {

	public VproductOrderService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 	1.订购鉴权服务；
		2.新增订单；
		3.根据所有增值产品新增订单项；
		4.根据订单对象量新增订购关系实例；
		5.二次确认服务；
		6.送激活服务。

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		//1.订购鉴权服务；1111
		OrderAuthService aOrderAuthService=new OrderAuthService();
		Map ret=aOrderAuthService.service(in);
		String resultCode=(String)ret.get("resultCode");
		String resultMsg=(String)ret.get("resultMsg");
		
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		if("0".equals(resultCode)){//鉴权通过
			//2.新增订单；
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(order);
			//3.根据所有增值产品新增订单项；
			OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
			aOrderItemHelpDao.insertOrderItemsByOrder(order);
			//4.根据订单对象量新增订购关系实例；
			OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
			aOrderRelationHelpDao.insertOrderRelaionsByCustomerOrder(order);
			if(OrderConstant.orderStateOfCreated.equals(order.getStatus())){
				//6.送激活服务
				SendActiveService aSendActiveService=new SendActiveService();
				aSendActiveService.service(in);
			}else{
				//5.二次确认服务         新版规范已经去掉二次确认，暂不做。
			}
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
