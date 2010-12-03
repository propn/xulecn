package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.order.PartRuleinterface;
public class Rule11 implements PartRuleinterface {
	private static Logger logger = Logger.getLogger(Rule11.class);
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		String newProductOfferId = spProductVoIn.getNewProductOfferId();
		String newProductOfferType = spProductVoIn.getNewProductOfferType();
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");
		HashMap resultMap = new HashMap();
		String spProductStr = "";
		String restraintName = "";
		boolean flag = false;
		String SPProdSpecName = serviceName;
		try{
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					ArrayList spInfoLst = null;//new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);
						String productOfferId = (String)spProductVo.getProductOfferId();
						if(productOfferId==null) productOfferId = "";
						String packageId = (String)spProductVo.getPackageId();
						if(packageId==null) packageId = "";
						
						if(spProductVo.getServiceId().equals(serviceId)&&spProductVo.getType().equals("M")){
							//如果已经在捆绑销售品里面了就鉴权不通过
							if(!"".equals(packageId)||!"".equals(productOfferId)){
								restraintName = serviceName;
								break;
							}
							//如果原来是单产品,并且新的类型是不是CRM的捆绑销售品也鉴权不通过
							if(!"2".equals(newProductOfferType)){
								restraintName = serviceName;
								break;
							}
						}
					}
					
				}
			}
		}
		
		
		if(!restraintName.equals("")){
			resultMap.put("Result","11");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","增值产品"+restraintName+"已经存在");
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
			resultMap.put("Result","11");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","同产品鉴权:"+e.toString());
			logger.error("", e);
		}
		return resultMap;
	}
}
