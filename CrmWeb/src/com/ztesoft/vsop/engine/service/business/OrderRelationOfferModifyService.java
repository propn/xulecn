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
 * CRMͬ����ͳ����ֵ��Э������Ʒ
 * @author Administrator
 *
 */
public class OrderRelationOfferModifyService extends AbstractBusinessService {

	public OrderRelationOfferModifyService() {
		this.setCommonBusinessOperationAfter(false);
		this.setCommonBusinessOperationBefore(false);
	}
	/**
	 *  //1.�޸�����Ʒ(����������Ļ����޸Ķ�����ϵ����ƷpprodofferId),�������pprodofferId)        
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		CustomerOrder order=(CustomerOrder)in.get("busiObject");
		try{
			ProdInstHelpDao prodInstHelpDao = new ProdInstHelpDao();
			boolean isExit =prodInstHelpDao.isExitProdInst(order.getProdInstId());
			if(!isExit)
				throw new Exception("��Ʒʵ��������");
			//1;�ж�����Ʒ�Ƿ����,�����ھ�����һ������Ʒ��������������Ʒ�Ͳ�Ʒ�Ĺ�ϵ
			List offerList = order.getProductOfferInfoList();
			if(null != offerList && offerList.size()>0){
				ProductOfferInfo offer = (ProductOfferInfo) offerList.get(0);
				String offerId = offer.getOfferId();
				if(null == offerId || "".equals(offerId)){
					OfferDao offerDao = new OfferDao();
					offerId =offerDao.createProdOff(offer);
					offer.setOfferId(offerId);
					//���� offerId ���ύ����֮��ˢ�»���
					in.put("offerId", offerId);
				}
				//2.�޸Ķ�����ϵ �� 
				OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
				orderRelationDao.updateProdInstOfferByOrder(offer);
				
				in.put("resultCode", "0");
				in.put("resultMsg", "�ɹ�");
			}else {
				in.put("resultCode", "-1");
				in.put("resultMsg", "ʧ��");
			}
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("OrderRelationOfferModifyService "+e.getMessage());
			throw e;
		}
		return in;
	}
}
