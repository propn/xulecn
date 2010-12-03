package com.ztesoft.vsop.engine.service.business;

import java.util.Map;

import com.ztesoft.vsop.engine.dao.CustOrderHelpDao;
import com.ztesoft.vsop.engine.dao.OrderItemHelpDao;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;

/**
 * <pre>
 * Title:集团规范 X平台同步订购关系到VSOP
 * Description: 订购关系同步(X平台-VSOP)
 * </pre>
 * 
 * @author xulei xu.lei55@zte.com.cn
 * @version R7.8
 * 
 * <pre>
 * 修改记录
 * 修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class OrderRelationSynServiceFromX extends AbstractBusinessService{
	public OrderRelationSynServiceFromX() {
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
			
			//1.新增订单；
			CustOrderHelpDao custOrderHelpDao=new CustOrderHelpDao();
			custOrderHelpDao.insertOrderHis(customerOrder);
			//2.根据所有增值产品新增订单项；
			OrderItemHelpDao orderItemDao  = new OrderItemHelpDao();
			orderItemDao.insertOrderItemsHisByOrder(customerOrder);
			//3.根据订单对象批量修改订购关系实例；
			customerOrder.setSendActiveFlag(false);//X平台同步不送激活
			orderRelationDao.modifyORSByCustomerOrder(customerOrder);
		
			//4.全网订购关系同步服务。
//			OrderRelationSynWholeService orderRelationSynWholeService = new OrderRelationSynWholeService();
//			orderRelationSynWholeService.service(in);
			
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
