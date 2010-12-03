package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.dao.OfferDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.dao.ProdInstHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
/**
 * CRM同步传统加增值的协议销售品
 * @author Administrator
 *
 */
public class OrderRelationOfferModifyService extends AbstractBusinessService {

	public OrderRelationOfferModifyService() {
		this.setCommonBusinessOperationAfter(false);
		this.setCommonBusinessOperationBefore(false);
	}
	/**
	 *  //1.修改销售品(如果是新增的话，修改订购关系销售品pprodofferId),否则清空pprodofferId)        
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		try{
			ProdInstHelpDao prodInstHelpDao = new ProdInstHelpDao();
			boolean isExit =prodInstHelpDao.isExitProdInst(order.getProdInstId());
			if(!isExit)
				throw new Exception("产品实例不存在");
			//1;判断销售品是否存在,不存在就新增一个销售品，并且新增销售品和产品的关系
			List offerList = order.getProductOfferInfoList();
			if(null != offerList && offerList.size()>0){
				ProductOfferInfo offer = (ProductOfferInfo) offerList.get(0);
				String offerId = offer.getOfferId();
				if(null == offerId || "".equals(offerId)){
					OfferDao offerDao = new OfferDao();
					offerId =offerDao.createProdOff(offer);
					offer.setOfferId(offerId);
					//保存 offerId ，提交事务之后刷新缓存
					in.put("offerId", offerId);
				}
				//2.修改订购关系 ； 
				OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
				orderRelationDao.updateProdInstOfferByOrder(offer);
				
				in.put("resultCode", "0");
				in.put("resultMsg", "成功");
			}else {
				in.put("resultCode", "-1");
				in.put("resultMsg", "失败");
			}
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("OrderRelationOfferModifyService "+e.getMessage());
			throw e;
		}
		return in;
	}
}
