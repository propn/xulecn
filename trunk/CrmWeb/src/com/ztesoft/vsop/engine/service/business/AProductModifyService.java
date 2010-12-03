package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * 修改附属产品服务
 * @author panshaohua
 *
 */
public class AProductModifyService extends AbstractBusinessService{
	public AProductModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 *  1.新增订单
	 *  2.根据所有附属产品新增订单项；
	 *  3 根据订单对象写主产品订单项；
		4.批量修改业务能力实例；
		5.服开回单服务。

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1新增订单
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.根据所有附属产品新增订单项；
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			String custOrderId=customerOrder.getCustOrderId();
			String orderSatus=customerOrder.getStatus();
			String accNbr=customerOrder.getAccNbr();
			String lanId=customerOrder.getLanId();
			String prodInstId=customerOrder.getProdInstId(); 
			List aprodList=customerOrder.getAproductInfoList();
			orderItemDao.insertOrderItemsByAprodusts(custOrderId,orderSatus,accNbr,
					lanId,prodInstId,aprodList,customerOrder.getCustOrderType());
			//3.根据订单对象写主产品订单项；
			OrderItemHelpDao orderItem = new OrderItemHelpDao();
			orderItem.insertMainProductOrderItemByOrder(customerOrder);
			//4.批量修改业务能力实例；
			BizCapInstHelpDao bizCapInstDao = new BizCapInstHelpDao();
			bizCapInstDao.modifyBizCapInstByAprodusts(prodInstId, aprodList);
			//5.服开回单服务。
			SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
			feedbackService.service(in);
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			//logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}
	

}
