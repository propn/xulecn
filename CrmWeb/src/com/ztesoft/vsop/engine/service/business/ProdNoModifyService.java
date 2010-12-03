package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * 改号服务
 * @author panshaohua
 *
 */
public class ProdNoModifyService extends AbstractBusinessService{
	public ProdNoModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.新增订单；
	   2.通过订单对象写主产品订单项
	 * 3.根据产品实例修改产品实例号码、产品类型；
	 * 4.根据产品实例修改订购关系实例号码、产品类型；
	 * 5.用户状态同步服务；
	 * 6.服开回单服务

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
		//1新增订单
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(customerOrder);
		//2.通过订单对象写主产品订单项
		OrderItemHelpDao orderItem = new OrderItemHelpDao();
		orderItem.insertMainProductOrderItemByOrder(customerOrder);
		//3.根据产品实例修改产品实例号码、产品类型；
		String prodInstId = customerOrder.getProdInstId();
		String newAccNbr =customerOrder.getAccNbr();
		String newProdId = customerOrder.getProdId();
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
		//4.根据产品实例修改订购关系实例号码、产品类型；
		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
		orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
		//5.用户状态同步服务；暂时不用同步用户状态
		UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
		userInfoService.service(in);
		//6.服开回单服务
		SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
		feedbackService.service(in);

		return in;
	}


}
