package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
/**
 * <pre>
 * Title:用户更换UIM卡
 * Description: 向服务开通系统PF提供更换UIM卡webservice服务
 * </pre>
 * @author xulei  xu.lei55@zet.com.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ChangeUIMCardService extends AbstractBusinessService{
	public ChangeUIMCardService() {
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
//		OrderItemHelpDao orderItem = new OrderItemHelpDao();
//		orderItem.insertMainProductOrderItemByOrder(customerOrder);
		
		//3.根据产品实例修改产品实例号码、产品类型；
//		String prodInstId = customerOrder.getProdInstId();
//		String newAccNbr =customerOrder.getAccNbr();
//		String newProdId = customerOrder.getProdId();
//		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
//		prodInstDao.updateProdInstAccNbr(prodInstId,newAccNbr , newProdId);
		
		//3.根据产品实例修改
		String newAccNbr =customerOrder.getAccNbr();
		ProdInstHelpDao prodInstDao = new ProdInstHelpDao();
		prodInstDao.updateProdInstUIMNO(customerOrder.getUimNO(),customerOrder.getOldUimNO(),newAccNbr);
		
		//4.根据产品实例修改订购关系实例号码、产品类型；
//		OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
//		orderRelationDao.updateORAccNbrProductByProdInstId(prodInstId,newAccNbr , newProdId);
		
		//5.用户状态同步服务0
		//X平台同步
		UserInfoSynMsgService userInfoService = new UserInfoSynMsgService();
		userInfoService.service(in);
		//ismp同步
		
		
		
		//6.服开回单服务
		SpOutMsgFeedbackService feedbackService = new SpOutMsgFeedbackService();
		feedbackService.service(in);

		return in;
	}

}
