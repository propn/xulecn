package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.vsop.engine.dao.BizCapInstHelpDao;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProdInstVO;
/**
 * 新装服务
 * @author cooltan
 *
 */
public class InstallService extends AbstractBusinessService {

	public InstallService() {
		this.setCommonBusinessOperationAfter(true);
		this.setCommonBusinessOperationBefore(true);
	}
	/**
	 *  //1.新增订单；        
		//2根据订单对象新增订单项；根据订单对象写主产品订单项；
		//3.根据所有附属产品新增订单项； 
		//4.新增产品实例 ；   
		//5.批量新增业务能力实例；
		//6.根据所有增值产品批量新增订购关系实例；；
		//7.送激活服务；       
		//8.服开回单服务。
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		// TODO Auto-generated method stub
		
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		//1.新增订单； 
		CustOrderHelpDao aCustOrderHelpDao=new CustOrderHelpDao();
		aCustOrderHelpDao.insertOrder(order);
		//2根据订单对象新增订单项；根据订单对象写主产品订单项；
		OrderItemHelpDao aOrderItemHelpDao=new OrderItemHelpDao();
		aOrderItemHelpDao.insertOrderItemsByOrder(order);
		aOrderItemHelpDao.insertMainProductOrderItemByOrder(order);
		//3.根据所有附属产品新增订单项； 
		aOrderItemHelpDao.insertOrderItemsByAprodusts(order.getCustOrderId(), 
				order.getStatus(), order.getAccNbr(), order.getLanId(), 
				order.getProdInstId(), order.getAproductInfoList(),order.getCustOrderType());
		//4.新增产品实例 ； 
		ProdInstVO prodInst =order.getProdInst();
		ProdInstHelpDao aProdInstHelpDao=new ProdInstHelpDao();
		aProdInstHelpDao.insertProdInst(prodInst);
		//5.批量新增业务能力实例；
		BizCapInstHelpDao aBizCapInstHelpDao=new BizCapInstHelpDao();
		aBizCapInstHelpDao.insertBizCapInstByAprodusts(prodInst.getProdInstId(),order.getAproductInfoList());
		//6.根据所有增值产品批量新增订购关系实例；；
		OrderRelationHelpDao aOrderRelationHelpDao=new OrderRelationHelpDao();
		aOrderRelationHelpDao.insertOrderRelaionsByCustomerOrder(order);
		//7.送激活服务；
		if(order.getProductOfferInfoList().size()>0){//如果有增值产品送激活，待激活完成后由激活模块回单
			SendActiveService aSendActiveService=new SendActiveService();
			aSendActiveService.service(in);
		}else{//如果不送激活，则回单
		//8.服开回单服务
			SpOutMsgFeedbackService aSpOutMsgFeedbackService=new SpOutMsgFeedbackService();
			aSpOutMsgFeedbackService.service(in);
		}
		return in;
	}

	public int perform(DynamicDict dynamicDict) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
