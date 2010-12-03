package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * 固话移机服务
 * @author Administrator
 *
 */
public class TransferUserService extends AbstractBusinessService{

	public TransferUserService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	1.新增订单；
	 2.通过订单对象写主产品订单项
	 3.根据所有附属产品新增订单项； 
	 4.根据订单对象新增增值产品订单项； 
	 5.批量修改业务能力实例；
	 6.根据订单对象批量修改订购关系实例； 
	 7.根据产品实例修改产品实例号码、产品类型；
	 8.根据产品实例修改订购关系实例号码、产品类型；
	 9.用户状态同步服务；
	 10.如果订单未包含增值产品，则调用服开回单服务，否则调用送激活服务。
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
		//1新增订单
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(customerOrder);
		//2.通过订单对象写主产品订单项
		OrderItemHelpDao orderItemDao = new OrderItemHelpDao();
		orderItemDao.insertMainProductOrderItemByOrder(customerOrder);
		
		//3.根据所有附属产品新增订单项； 
		orderItemDao.insertOrderItemsByAprodusts(customerOrder.getCustOrderId(), 
				customerOrder.getStatus(), customerOrder.getAccNbr(), customerOrder.getLanId(), 
				customerOrder.getProdInstId(), customerOrder.getAproductInfoList(),customerOrder.getCustOrderType());

		// 4.根据订单对象新增增值产品订单项；
		orderItemDao.insertOrderItemsByOrder(customerOrder);

		String prodInstId = customerOrder.getProdInstId();
		String oldAccNbr = customerOrder.getOldAccNbr();
		//5.批量新增业务能力实例；
		BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
		aBizCapInstHelpDao.insertBizCapInstByAprodusts(prodInstId,customerOrder.getAproductInfoList());
		// 6.根据订单对象批量修改订购关系实例；
		OrderRelationHelpDao aOrderRelationHelpDao = new OrderRelationHelpDao();
		aOrderRelationHelpDao.modifyORSByCustomerOrder(customerOrder);
		//7.根据产品实例修改产品实例号码、产品类型；
		if(null!=oldAccNbr && !"".equals(oldAccNbr) && !oldAccNbr.equals(customerOrder.getAccNbr())){
			String newAccNbr =customerOrder.getAccNbr();
			String newProdId = customerOrder.getProdId();
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
			
			//8.根据产品实例修改订购关系实例号码、产品类型；
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
			//9.用户状态同步服务；
			UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
			userInfoService.service(in);
		}
		
		// 10.送激活服务；
		if (customerOrder.getProductOfferInfoList().size() > 0) {// 如果有增值产品送激活，待激活完成后由激活模块回单
			SendActiveService aSendActiveService = new SendActiveService();
			aSendActiveService.service(in);
		} else {// 如果不送激活，则回单
			// 10.1服开回单服务
			SpOutMsgFeedbackService aSpOutMsgFeedbackService = new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}

		return in;
	}


}
