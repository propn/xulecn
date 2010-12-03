package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * 销售品鉴权
 */

public class Rule10 implements PartRuleinterface {
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");
		String systemId = (String)map.get("systemId");
		
		HashMap resultMap = new HashMap();
		boolean flag = true;
		String SPProdSpecName = serviceName;
		try{
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					ArrayList spInfoLst = new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);
						if(spProductVo.getServiceId().equals(serviceId)){
							if((spProductVo.getProductOfferId() != null && !spProductVo.getProductOfferId().equals(""))&&!systemId.equals("201")){//如果有捆绑的销售品并且非CRM发起的
								flag = false;
								break;
							}
						}
					}
				}
			}
		}
		
		
		if(!flag){//捆绑了销售品并且非CRM发起
			resultMap.put("Result","10");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","增值产品"+SPProdSpecName+"捆绑了优惠套餐，必须通过营业厅取消优惠套餐进行退订!");
		}
		else{//状态可用
			resultMap.put("Result","0");//结果成功
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","10");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","销售品鉴权:"+e.toString());
		}
		return resultMap;
	}
}
