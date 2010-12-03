package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.order.vo.response.SubInstQueryResponse;
import com.ztesoft.vsop.web.DcSystemParamManager;

/**
 * 订购关系查询服务
 * @author panshaohua
 *
 */
public class OrderRelationQryService extends AbstractBusinessService{
	public OrderRelationQryService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 通过产品号码、产品类型查询用户有效的订购关系实例
	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter("DC_PROVINCE_CODE")) 
			   && null ==  customerOrder.getProdInstId()){
				in.put("resultCode", "-1");
				in.put("resultMsg", "用户不存在");
				return in;
			}
			OrderRelationHelpDao orderRelationDao = new OrderRelationHelpDao();
			String accNbr = customerOrder.getAccNbr();
			String prodType = customerOrder.getProdId(); //主产品
			List ls=orderRelationDao.qryORSByAccNbrAndType(accNbr, prodType);
			in.put("resultCode", "0");
			in.put("resultMsg", "成功");
			in.put("retBusiObject", ls);
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("UserInfoSynMsgService "+e.getMessage());
			throw e;
		}
			
		return in;
	}
	

}
