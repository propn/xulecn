package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.bsn.webservice.bsnSoapClient;
import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.client.SoapClient;

/**
 * rule5��ʵ�ַ�ʽ,����OCS��Ȩ����,���ʵʱ�������0���Ȩͨ��,�����Ȩ��ͨ��
 * @author qin.guoquan
 *
 */
public class Rule5 implements PartRuleinterface {
	
	public HashMap authentication(HashMap map) {
		
		SpProductVo spProductVoIn = (SpProductVo) map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String serviceName = spProductVoIn.getNameCn();
		String productNo = spProductVoIn.getProductNo();
		ArrayList productInfoLst = (ArrayList) map.get("productInfoLst");
		
		String systemId = (String) map.get("systemId");//���׷���ϵͳ��ʶ
		String contectType = "2";//ʵʱ����
		HashMap resultMap = new HashMap();
		
		try {
			if (null != productInfoLst && !productInfoLst.isEmpty()) {
				for (int i = 0; i < productInfoLst.size(); i++) {
					ProductVo productVo = (ProductVo) productInfoLst.get(i);
					int ocsSuccess = 0;//��Ȩ��־λ
					java.util.Calendar date = java.util.Calendar.getInstance();
					String dateNum = String.valueOf(date.get(java.util.Calendar.DAY_OF_MONTH));
					System.out.println("day:"+date.get(java.util.Calendar.DAY_OF_MONTH));
					//ÿ���ض�ĳЩ�첻�ò�ѯԤ���������ֻ����
					String dateStr = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("HB_SEAR_LIMIT_DAY");
					
					//���������Ǻ󸶷�(0),������rule5
					String paymentModeCd = productVo.getPaymentModeCd();//��������
					if (null != paymentModeCd && !"".equals(paymentModeCd) && !"0".equals(paymentModeCd)
					    && dateStr.indexOf(dateNum) == -1) {
						String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
						if(CrmConstants.CQ_PROV_CODE.equals(provinceCode)){
							String balan=bsnSoapClient.getInstance().QueryCashFee(productVo.getProductNo(), productVo.getProductSpec());
							if (null != balan && !"".equals(balan.trim()) && Integer.parseInt(balan) > 0) {
								ocsSuccess = -1;//δ�ɷѽ�����0���Ȩ��ͨ�� 
							} else {
								ocsSuccess = 1;//δ�ɷѽ��С�ڻ����0ʱ��Ȩͨ��
							}
						}else {
							String balance = bsnSoapClient.getInstance().getBalance(systemId, productVo.getProductSpec(), productVo.getProductNo(), contectType);
							if (null != balance && !"".equals(balance.trim()) && Integer.parseInt(balance) > 0) {
								ocsSuccess = 1;//ʵʱ������0���Ȩͨ�� 
							} else {
								ocsSuccess = -1;//ʵʱ���С�ڻ����0ʱ��Ȩ��ͨ��
							}
						}
							
						
					} else {//������������Ǻ󸶷ѣ���rule5��Ȩͨ��
						resultMap.put("Result", "0");//����ɹ�
						resultMap.put("ProductNo", productNo);//����
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS��Ȩͨ��");
					}
					
					if (1 == ocsSuccess) {
						resultMap.put("Result", "0");//����ɹ�
						resultMap.put("ProductNo", productNo);//����
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS��Ȩͨ��");
						
					} 
					if (-1 == ocsSuccess) {
						resultMap.put("Result", "5");
						resultMap.put("ProductNo", productNo);
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS��Ȩʧ��,ʵʱ����");
					}
				}
			}
			
		} catch (Exception e) {
			resultMap.put("Result", "5");//���ʧ��
			resultMap.put("ProductNo", "-1");//����
			resultMap.put("SPProdSpecId", "-1");
			resultMap.put("SPProdSpecName", "-1");
			resultMap.put("FailureNote", "OCS��Ȩʧ��:" + e.toString());
		}
		
		return resultMap;
	}
	
}
