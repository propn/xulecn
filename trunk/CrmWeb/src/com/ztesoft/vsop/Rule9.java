package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * 被依赖鉴权
 */

public class Rule9 implements PartRuleinterface{
	public HashMap authentication(HashMap map){
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");
		String serviceId = spProductVoIn.getServiceId();
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");
		ArrayList bizRestraintLst = (ArrayList)map.get("bizRestraintLst");
		HashMap resultMap = new HashMap();
		String spProductStr = "";
		String restraintName = "";
		boolean flag = false;
		String SPProdSpecName = serviceName;
		try{
		//将该号码下的所有增值产品拼成一个串，用,分割。
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					ArrayList spInfoLst = new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);
						if(!spProductVo.getState().equalsIgnoreCase("00X"))
						spProductStr = spProductStr + spProductVo.getServiceId()+",";
					}
					
				}
			}
		}
		//获取该增值业务依赖的增值业务，判断是否在之前拼装的串中
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo= null;//new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("1")){//如果是互斥
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//当前增值产品有依赖的增值产品
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//在列表中存在
							restraintName = bizRestraintVo.getaObjectName();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(!restraintName.equals("")){//状态不可用
			resultMap.put("Result","9");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","增值产品"+restraintName+"依赖"+SPProdSpecName+",不能取消");
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
			resultMap.put("Result","9");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","被依赖鉴权:"+e.toString());
		}
		return resultMap;
	}
}
