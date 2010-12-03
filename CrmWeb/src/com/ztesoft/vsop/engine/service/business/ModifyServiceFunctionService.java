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
 * 江西服开接口新增服务：改服务功能
 * 
 * @author cooltan
 * 
 */
public class ModifyServiceFunctionService extends AbstractBusinessService {
	public ModifyServiceFunctionService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}

	/**
	 * 1.新增订单； 
	 * 2根据订单对象写主产品订单项； 
	 * 3.根据所有附属产品新增订单项； 
	 * 4.根据订单对象新增增值产品订单项； 
	 * 5.批量修改业务能力实例；
	 * 6.根据订单对象批量修改订购关系实例； 
	 * 7.如果订单未包含增值产品，则调用服开回单服务，否则调用送激活服务。
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order = (CustomerOrder) in.get("busiObject");
		// 1.新增订单；
		CustOrderHelpDao aCustOrderHelpDao = new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(order);
		// 2.根据订单对象写主产品订单项；
		OrderItemHelpDao aOrderItemHelpDao = new OrderItemHelpDao();
		aOrderItemHelpDao.insertMainProductOrderItemByOrder(order);
		// 3.根据所有附属产品新增订单项；
		aOrderItemHelpDao.insertOrderItemsByAprodusts(order.getCustOrderId(),
				order.getStatus(), order.getAccNbr(), order.getLanId(), order
						.getProdInstId(), order.getAproductInfoList(), order
						.getCustOrderType());
		// 4.根据订单对象新增增值产品订单项；
		aOrderItemHelpDao.insertOrderItemsByOrder(order);
		// 5.批量修改业务能力实例；
		ProdInstVO prodInst = order.getProdInst();
		BizCapInstHelpDao aBizCapInstHelpDao = new BizCapInstHelpDao();
		aBizCapInstHelpDao.modifyBizCapInstByAprodusts(
				prodInst.getProdInstId(), order.getAproductInfoList());
		// 6.根据订单对象批量修改订购关系实例；
		OrderRelationHelpDao aOrderRelationHelpDao = new OrderRelationHelpDao();
		aOrderRelationHelpDao.modifyORSByCustomerOrder(order);
		// 7.送激活服务；
		if (order.getProductOfferInfoList().size() > 0) {// 如果有增值产品送激活，待激活完成后由激活模块回单
			SendActiveService aSendActiveService = new SendActiveService();
			aSendActiveService.service(in);
		} else {// 如果不送激活，则回单
			// 8.服开回单服务
			SpOutMsgFeedbackService aSpOutMsgFeedbackService = new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}

		return in;
	}

}
