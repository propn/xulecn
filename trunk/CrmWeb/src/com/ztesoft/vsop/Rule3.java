package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/**
 * 
 * 用户状态鉴权
 *
 */
public class Rule3 implements PartRuleinterface{
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");
		HashMap resultMap = new HashMap();
		boolean flag = true;
		String SPProdSpecName = "";
		try{
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					if(!productVo.getUserState().equals("00A")){
						flag = false;
						SPProdSpecName = serviceName;
						break;
					}
				}
			}
		}
		
		
		if(!flag){//状态不可用
			resultMap.put("Result","3");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","号码"+productNo+"状态不可用");
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
			resultMap.put("Result","3");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","用户鉴权:"+e.toString());
		}
		return resultMap;
	}
}
