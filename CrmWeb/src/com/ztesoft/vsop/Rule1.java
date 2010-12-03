package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * 产品鉴权，根据传入的产品判断产品是否存在，如果不存在，返回错误原因，如果存在判断状态是否可用
 */

public class Rule1 implements PartRuleinterface{
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		ArrayList spProductLst = (ArrayList)map.get("spProductLst");
		HashMap resultMap = new HashMap();
		boolean flag = true;
		boolean isExist = false;
		String SPProdSpecName = spProductVoIn.getNameCn();
		try{
		if(spProductLst!=null){
			for(int i=0;i<spProductLst.size();i++){
				SpProductVo spProductVo = (SpProductVo)spProductLst.get(i);
				if(spProductVo.getServiceId().equals(serviceId)){
					isExist = true;
					if(!spProductVo.getState().equals("0")){
						flag = false;
						SPProdSpecName = spProductVo.getNameCn();
						break;
					}
				}
			}
		}
		
		if(!isExist){//增值产品不存在
			resultMap.put("Result","1");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName","");//名称为空
			resultMap.put("FailureNote","产品不存在，对应的标识为"+serviceId);
		}
		
		if(!flag&&isExist){//存在但是状态不可用
			resultMap.put("Result","1");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","产品"+SPProdSpecName+"状态不可用");
		}
		
		if(flag&&isExist){//存在并且状态可用
			resultMap.put("Result","0");//结果成功
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","1");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","产品鉴权:"+e.toString());
		}
		return resultMap;
	}
}
