package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.help.OrderRelationToGroupHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.webservice.client.SoapClient;

/**
 * 全网订购关系同步服务
 * 省级集团VSOP将ISMP-BMW管理的全网X业务平台订购关系同步到省级集团VSOP
 * @author panshaohua
 *
 */
public class OrderRelationSynWholeService extends AbstractBusinessService{
	public OrderRelationSynWholeService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1:根据CustomerOrder拼装同步给全网的XML串
	 * 2:调用同步接口，同步订购关系

	 */
	public Map concreteBusinessOpertions(Map in) throws Exception {
		try {
			CustomerOrder customerOrder=(CustomerOrder)in.get("busiObject");
			OrderRelationToGroupHelp oRToGroup = new OrderRelationToGroupHelp();
			List xmlList =   oRToGroup.createToGroupXML(customerOrder);
			if(null != xmlList){
				SoapClient client = new SoapClient();
				for (int i = 0; i < xmlList.size(); i++) {
					client.subsInstSynSV(xmlList.get(i).toString());
				}
			}
			in.put("resultCode", "0");
			in.put("resultMsg", "成功");
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "失败");
			logger.error("OrderRelationSynWholeService "+e.getMessage());
			throw e;
		}
			
		return in;
	}
}
