package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * 用户状态变更服务
 * @author panshaohua
 *
 */
public class UserStateChangeService extends AbstractBusinessService{
	public UserStateChangeService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**1.新增订单；
		2.2.通过订单对象写主产品订单项
	 * 3.根据产品实例修改用户状态；
	   4.用户状态同步服务；
	   5.服开回单服务
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1新增订单
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.通过订单对象写主产品订单项
			OrderItemHelpDao orderItem = new OrderItemHelpDao();
			orderItem.insertMainProductOrderItemByOrder(customerOrder);
			//3.根据产品实例修改用户状态；
			String state = customerOrder.getProdInst().getStateCd();
			ProdInstHelpDao prodInst = new ProdInstHelpDao();
			prodInst.updateProdInstUserState(state,customerOrder.getProdInstId());
			//4.用户状态同步服务；一期广西和江西都不需要状态同步
			UserInfoSynMsgService userInfoSyn = new UserInfoSynMsgService();
			userInfoSyn.concreteBusinessOpertions(in);
			
			//4.服开回单服务；
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
