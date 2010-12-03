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
 * 拆机服务
 * @author panshaohua
 *
 */
public class ProdInstDisassembleService extends AbstractBusinessService{
	public ProdInstDisassembleService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1.新增订单；
	   2.通过订单对象写主产品订单项
	 * 3.根据产品实例修改用户状态；
	   4.根据产品实例修改订购关系实例状态为失效
	   5.用户状态同步服务；
	   6.服开回单服务。
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
			
			String state = ProdInstVO.StateOfDisassemble;//拆机状态00X
			String prodInstId = customerOrder.getProdInstId();
			//4.根据产品实例id删除业务能力
			BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
			aBizCapInstHelpDao.deleteBizCapInstByAprodusts(prodInstId,customerOrder.getAproductInfoList());
			//3.根据产品实例修改用户状态；
			ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
			prodInstDao.updateProdInstUserState(state,customerOrder.getProdInstId());
			//4.根据产品实例修改订购关系实例状态为失效
			OrderRelationHelpDao orderRelation = new OrderRelationHelpDao();
			orderRelation.updateORSExpireByProdInstId(prodInstId);
			
			//5.用户状态同步服务；暂时不同步
			UserInfoSynMsgService userInfoSyn = new UserInfoSynMsgService();
			userInfoSyn.service(in);
			//6.服开回单服务。
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
