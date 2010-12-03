package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
/**
 * 付费类型变更服务
 * @author panshaohua
 *
 */
public class ProdInstPayModeModifyService extends AbstractBusinessService{
	public ProdInstPayModeModifyService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.新增订单；
	   2.通过订单对象写主产品订单项
	 * 3.根据产品实例修改付费类型；
	 * 4.用户状态同步服务；
	 * 5.服开回单服务
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			//1新增订单
			CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
			aCustOrderHelpDao.insertOrder(customerOrder);
			//2.通过订单对象写主产品订单项
			OrderItemHelpDao orderItemDao = new OrderItemHelpDao();
			orderItemDao.insertMainProductOrderItemByOrder(customerOrder);
			//3.根据所有附属产品新增订单项； 
			//panshaohua add by 2010-8-19
			orderItemDao.insertOrderItemsByAprodusts(customerOrder.getCustOrderId(), 
					customerOrder.getStatus(), customerOrder.getAccNbr(), customerOrder.getLanId(), 
					customerOrder.getProdInstId(), customerOrder.getAproductInfoList(),customerOrder.getCustOrderType());
			//3.根据产品实例修改付费类型；
			String paymode = customerOrder.getProdInst().getPaymentModeCd();
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstPayMode(paymode,customerOrder.getProdInstId());
			
			//4.批量新增业务能力实例；
			//panshaohua add by 2010-8-19
			ProdInstVO prodInst =customerOrder.getProdInst();
			BizCapInstHelpDao bizCapInstDao = new BizCapInstHelpDao();
			bizCapInstDao.modifyBizCapInstByAprodusts(prodInst.getProdInstId(),customerOrder.getAproductInfoList());

			//4.用户状态同步服务；暂时不同步状态
			UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
			userInfoService.service(in);
			//5.服开回单服务
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
