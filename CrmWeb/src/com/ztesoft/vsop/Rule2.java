package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * SP/CP鉴权，根据传入的产品判断SP/CP是否存在，如果不存在，返回错误原因，如果存在判断状态是否可用
 */

public class Rule2  implements PartRuleinterface{
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		ArrayList spProductLst = (ArrayList)map.get("spProductLst");
		HashMap resultMap = new HashMap();
		boolean flag = true;
		String SPProdSpecName = "";
		try{
		if(spProductLst!=null){
			for(int i=0;i<spProductLst.size();i++){
				SpProductVo spProductVo = (SpProductVo)spProductLst.get(i);
				if(spProductVo.getServiceId().equals(serviceId)){
					if(spProductVo.getSpState()==null||!spProductVo.getSpState().equals("0")){
						flag = false;
						SPProdSpecName = spProductVo.getNameCn();
						break;
					}
				}
			}
		}
		
		
		if(!flag){//状态不可用
			resultMap.put("Result","2");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","产品"+SPProdSpecName+"所属的SP/CP状态不可用");
		}
		else{//状态可用
			resultMap.put("Result","0");//结果成功
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","2");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","SP/CP鉴权:"+e.toString());
		}
		return resultMap;
	}
}
