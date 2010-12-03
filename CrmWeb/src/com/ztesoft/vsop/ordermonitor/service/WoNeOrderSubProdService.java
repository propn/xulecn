package com.ztesoft.vsop.ordermonitor.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class WoNeOrderSubProdService  {
	public PageModel searchWoNeOrderSubProdData(String ne_order_id , String query_type, 
			int pageIndex , int pageSize) throws Exception {
		
		Map param = new HashMap() ;
		param.put("ne_order_id", ne_order_id) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;
		if("1".equals(query_type)){
			PageModel result = DataTranslate._PageModel(
					ServiceManager.callJavaBeanService(ServiceList.WoNeOrderSubProdHisBO,
							"searchWoNeOrderSubProdHisData" ,param)) ;
			
			return result ;
		}
		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService(ServiceList.WoNeOrderSubProdBO,
						"searchWoNeOrderSubProdData" ,param)) ;
		
		return result ;
	}
	/**
	 * ��ֵ��Ʒ�˹��ص�
	 * 
	 * @param ne_order_id
	 * @return String
	 * @throws Exception
	 */
	public String woNeOrderSubProdFeedBack(String ne_order_id,
			String product_code) throws Exception {
		Map param = new HashMap();
		param.put("ne_order_id", ne_order_id);
		param.put("product_code", product_code);
		String result = DataTranslate._String(ServiceManager
				.callJavaBeanService(ServiceList.WoNeOrderSubProdBO,
						"woNeOrderSubProdFeedBack", param));
		return result;
	}

	/**
	 * ��ֵ��Ʒ����ҵ��ƽ̨
	 * 
	 * @param ne_order_id
	 * @return String
	 * @throws Exception
	 */
	public String woNeOrderSubProdReSentPlatForms(String ne_order_id,
			String product_code, String str_xml) throws Exception {
		Map param = new HashMap();
		String subproduct_xml = getSendWoNeOrderSubProXmlById(str_xml, product_code);
	
		param.put("ne_order_id", ne_order_id);
		param.put("product_code", product_code);
		param.put("subproduct_xml", subproduct_xml);

		String result = DataTranslate._String(ServiceManager
				.callJavaBeanService(ServiceList.WoNeOrderSubProdBO,
						"woNeOrderSubProdReSentPlatForms", param));
		return result;
	}

	/**
	 * ����������ֵ��Ʒxml
	 * 
	 * @param str_xml,product_code
	 * @return String
	 * @throws Exception
	 */
	public String getSendWoNeOrderSubProXmlById(String strXml,
			String productCode) {
		String startXml = "";
		String subProXmls = "";
		String subProXml = "";
		String tempSubProXml = "";
		String endXml = "";
		String searchStr1 = "<sub_product>";
		String searchStr2 = "</sub_product>";
		String searchId = "<sub_product_code>" + productCode
				+ "</sub_product_code>";
		int findIndex = 0;
		int endIndex = 0;
		int findFlag = 0;
		findIndex = strXml.indexOf(searchStr1);
		// ��ȡ��һ��<sub_product>֮ǰ��xml
		startXml = strXml.substring(0, findIndex);
		endIndex = strXml.lastIndexOf(searchStr2);
		// ��ȡ���</sub_product>֮���xml
		endXml = strXml.substring(endIndex + searchStr2.length());
		// ��ȡ��һ��<sub_product>�����һ�� </sub_product>֮���xml�������ж����ֵ��Ʒ�ı���
		subProXmls = strXml
				.substring(findIndex, endIndex + searchStr2.length());

		findIndex = subProXmls.indexOf(searchStr1);
		endIndex = subProXmls.indexOf(searchStr2);
		// �ݹ鷽��subProXmls��ͨ����ֵ��Ʒid���ҵ�ѡ����ֵ��Ʒ�ı���
		while (findIndex != -1) {
			tempSubProXml = subProXmls.substring(findIndex, endIndex
					+ searchStr2.length());
			subProXmls = subProXmls.substring(endIndex + searchStr2.length());
			findFlag = tempSubProXml.indexOf(searchId);
			if (findFlag != -1) {
				// �ҵ�������ѭ��
				subProXml = tempSubProXml;
				break;
			}
			findIndex = subProXmls.indexOf(searchStr1);
			endIndex = subProXmls.indexOf(searchStr2);
		}
		// ��������ֵ��Ʒ��xml
		return startXml + subProXml + endXml;

	}
}
