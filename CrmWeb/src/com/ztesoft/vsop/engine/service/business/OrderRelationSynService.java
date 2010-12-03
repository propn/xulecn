package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.VsopConstants;
import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.engine.vo.ProductOfferInfo;
import com.ztesoft.vsop.engine.vo.VproductInfo;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订购关系同步服务
 * @author panshaohua
 *
 */
public class OrderRelationSynService extends AbstractBusinessService{
	public OrderRelationSynService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 *  1.新增订单；
		2.根据订单对象新增订单项；
		3.根据订单对象批量修改订购关系实例；
		4.全网订购关系同步服务。
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			
			// 广西本地化处理
			String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
//			if (CrmConstants.GX_PROV_CODE.equals(provinceCode)) {
//				// 过滤在ORDER_RELATION表中有相同ACC_NBR,PRODUCT_ID,STATE的记录
//				// 如果增值产品已经在VSOP加入了传统+增值套餐则订购关系以IT侧为准，不做更新
//				List productOfferInfoList = customerOrder.getProductOfferInfoList();
//				ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferInfoList.get(0);
//				VproductInfo vproductInfo = (VproductInfo) productOfferInfo.getVproductInfoList().get(0);
//				String productId = vproductInfo.getVProductId();
//				String accNbr = customerOrder.getAccNbr();
////				String state = customerOrder.getProdInst().getStateCd();
//				String ppid = orderRelationDao.isExistsOrderRelation(accNbr, productId, "00A");
//				if (ppid != null && !"".equals(ppid)) {
//					in.put("resultCode", "0");
//					in.put("resultMsg", "成功");
//					return in;
//				}
//			}
			
			//******
			//广西本地化
			//**********
			if (CrmConstants.GX_PROV_CODE.equals(provinceCode)) {
				List productOfferInfoList = customerOrder.getProductOfferInfoList();
				ProductOfferInfo productOfferInfo = (ProductOfferInfo) productOfferInfoList.get(0);
				VproductInfo vproductInfo = (VproductInfo) productOfferInfo.getVproductInfoList().get(0);
				String productId = vproductInfo.getVProductId();
				String accNbr = customerOrder.getAccNbr();
				String orderType=customerOrder.getCustOrderType();
				//values返回一个以','分割的串，第一个是序列主键表示是不是存在，第二个是pprodofferId，表示是不是存在传统+增值
				String values=orderRelationDao.isExistsOrderRelationOnOrderRelatID(accNbr, productId, "00A");
				
				//-1表示此用户信息服未同步到VSOP,先用中间表order_relation_middle操作用户的订购关系实例
				if(null!=customerOrder.getProdInstId() && "-1".equals(customerOrder.getProdInstId())){
					values=orderRelationDao.isExistsOrderRelationMiddleOnOrderRelatID(accNbr, productId, "00A");
				}
				
				if(values != null){
					String[] vals = values.split(",");
					String ppid="";
					if(vals.length>1){
						ppid=vals[1];
					}
//					广西，如果是订购关系同步过来的，即使此增值产品在传统+增值里，也让退订，但不同步到CRM,GX_PPROD_TYPE为标志
					if(null!=ppid && !"".equals(ppid)){
						in.put("GX_PPROD_TYPE", "1");
					}

//					无效(过滤在ORDER_RELATION表中有相同ACC_NBR,PRODUCT_ID,STATE的记录, 如果增值产品已经在VSOP加入了传统+增值套餐则订购关系以IT侧为准，不做更新)
//					if (ppid != null && !"".equals(ppid)) {
//						in.put("resultCode", "0");
//						in.put("resultMsg", "成功");
//						return in;
//					}
					//过滤由彩铃和ISMP过来的订购关系同步时，查accNbr, productId, "00A"，如果存在，且过来的动作是非失效的，则同步到VSOP库，否则直接返回成功
					if(!"1".equals(orderType)){//动作已转内部订购动作. 1:退订操作
						in.put("resultCode", "0");
						in.put("resultMsg", "成功");
						return in;
					}
				//values为null的话，序列主键不存在，此时若订单过来的是退订的，订购关系同步直接返回成功
				}else if("1".equals(orderType)){//作已转内部订购动作. 1:退订操作
					in.put("resultCode", "0");
					in.put("resultMsg", "成功");
					return in;
				}
			}
			
			//1.新增订单；
			CustOrderHelpDao custOrderHelpDao=new CustOrderHelpDao();
			custOrderHelpDao.insertOrderHis(customerOrder);
			//2.根据所有增值产品新增订单项；
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsHisByOrder(customerOrder);
			//3.根据订单对象批量修改订购关系实例；
			customerOrder.setSendActiveFlag(false);//ismp同步不送激活
			//*****
			//广西本地化,如果订购关系同步过来时，VSOP还无收到服开同步过来的用户信息，则把订购关系写到ORDER_RELATION_MIDDLE表,后续定时任务处理
			//*****
			if (provinceCode.equals(CrmConstants.GX_PROV_CODE)
					&& !customerOrder.isExistProdInst()) {
				orderRelationDao.modifyORSMIDDByCustomerOrder(customerOrder);
			} else {
				orderRelationDao.modifyORSByCustomerOrder(customerOrder);
			}
			//广西本地化，一阶段广西暂不需要同步全网业务给集团VSOP liuyuming 2010-09-08 start
			if(!CrmConstants.GX_PROV_CODE.equals(provinceCode)){
				//4.全网订购关系同步服务。
				OrderRelationSynWholeService orderRelationSynWholeService = new OrderRelationSynWholeService();
				orderRelationSynWholeService.service(in);
			}
			//广西本地化，一阶段广西暂不需要同步全网业务给集团VSOP liuyuming 2010-09-08 end
			in.put("resultCode", "0");
			in.put("resultMsg", "成功");
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}

}
