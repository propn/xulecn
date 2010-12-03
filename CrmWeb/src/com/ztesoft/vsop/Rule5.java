package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.bsn.webservice.bsnSoapClient;
import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
import com.ztesoft.vsop.webservice.client.SoapClient;

/**
 * rule5的实现方式,调用OCS鉴权方法,如果实时结余大于0则鉴权通过,否则鉴权不通过
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
		
		String systemId = (String) map.get("systemId");//交易发起系统标识
		String contectType = "2";//实时结余
		HashMap resultMap = new HashMap();
		
		try {
			if (null != productInfoLst && !productInfoLst.isEmpty()) {
				for (int i = 0; i < productInfoLst.size(); i++) {
					ProductVo productVo = (ProductVo) productInfoLst.get(i);
					int ocsSuccess = 0;//鉴权标志位
					java.util.Calendar date = java.util.Calendar.getInstance();
					String dateNum = String.valueOf(date.get(java.util.Calendar.DAY_OF_MONTH));
					System.out.println("day:"+date.get(java.util.Calendar.DAY_OF_MONTH));
					//每月特定某些天不用查询预付费天翼手机余额
					String dateStr = com.ztesoft.vsop.web.DcSystemParamManager.getParameter("HB_SEAR_LIMIT_DAY");
					
					//付费类型是后付费(0),不调用rule5
					String paymentModeCd = productVo.getPaymentModeCd();//付费类型
					if (null != paymentModeCd && !"".equals(paymentModeCd) && !"0".equals(paymentModeCd)
					    && dateStr.indexOf(dateNum) == -1) {
						String provinceCode = DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE);
						if(CrmConstants.CQ_PROV_CODE.equals(provinceCode)){
							String balan=bsnSoapClient.getInstance().QueryCashFee(productVo.getProductNo(), productVo.getProductSpec());
							if (null != balan && !"".equals(balan.trim()) && Integer.parseInt(balan) > 0) {
								ocsSuccess = -1;//未缴费金额大于0则鉴权不通过 
							} else {
								ocsSuccess = 1;//未缴费金额小于或等于0时鉴权通过
							}
						}else {
							String balance = bsnSoapClient.getInstance().getBalance(systemId, productVo.getProductSpec(), productVo.getProductNo(), contectType);
							if (null != balance && !"".equals(balance.trim()) && Integer.parseInt(balance) > 0) {
								ocsSuccess = 1;//实时余额大于0则鉴权通过 
							} else {
								ocsSuccess = -1;//实时余额小于或等于0时鉴权不通过
							}
						}
							
						
					} else {//如果付费类型是后付费，则rule5鉴权通过
						resultMap.put("Result", "0");//结果成功
						resultMap.put("ProductNo", productNo);//号码
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS鉴权通过");
					}
					
					if (1 == ocsSuccess) {
						resultMap.put("Result", "0");//结果成功
						resultMap.put("ProductNo", productNo);//号码
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS鉴权通过");
						
					} 
					if (-1 == ocsSuccess) {
						resultMap.put("Result", "5");
						resultMap.put("ProductNo", productNo);
						resultMap.put("SPProdSpecId", serviceId);
						resultMap.put("SPProdSpecName", serviceName);
						resultMap.put("FailureNote", "OCS鉴权失败,实时余额不足");
					}
				}
			}
			
		} catch (Exception e) {
			resultMap.put("Result", "5");//结果失败
			resultMap.put("ProductNo", "-1");//号码
			resultMap.put("SPProdSpecId", "-1");
			resultMap.put("SPProdSpecName", "-1");
			resultMap.put("FailureNote", "OCS鉴权失败:" + e.toString());
		}
		
		return resultMap;
	}
	
}
