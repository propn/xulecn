package com.ztesoft.vsop.engine.service.business;

import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.engine.help.OrderRelationToGroupHelp;
import com.ztesoft.vsop.engine.service.AbstractBusinessService;
import com.ztesoft.vsop.engine.vo.CustomerOrder;
import com.ztesoft.vsop.webservice.client.SoapClient;

/**
 * ȫ��������ϵͬ������
 * ʡ������VSOP��ISMP-BMW�����ȫ��Xҵ��ƽ̨������ϵͬ����ʡ������VSOP
 * @author panshaohua
 *
 */
public class OrderRelationSynWholeService extends AbstractBusinessService{
	public OrderRelationSynWholeService() {
		this.setCommonBusinessOperationBefore(true);
		this.setCommonBusinessOperationAfter(true);
	}
	/**
	 * 1:����CustomerOrderƴװͬ����ȫ����XML��
	 * 2:����ͬ���ӿڣ�ͬ��������ϵ

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
			in.put("resultMsg", "�ɹ�");
		}catch (Exception e) {
			in.put("resultCode", "-1");
			in.put("resultMsg", "ʧ��");
			logger.error("OrderRelationSynWholeService "+e.getMessage());
			throw e;
		}
			
		return in;
	}
}
