package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * 互斥鉴权
 */
public class Rule6 implements PartRuleinterface{
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
		String restraintId = "";
		String prodInstId = "";
		boolean flag = false;
		String SPProdSpecName = serviceName;
		try{
		//将该号码下的所有增值产品拼成一个串，用,分割。
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					prodInstId = productVo.getProductId();
					ArrayList spInfoLst = null;//new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);
						if(!spProductVo.getState().equalsIgnoreCase("00X"))
						spProductStr = spProductStr + spProductVo.getServiceId()+",";
					}
					
				}
			}
		}
		//获取该增值业务互斥的增值业务，判断是否在之前拼装的串中
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo=null;// new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("0")){//如果是互斥
					if(bizRestraintVo.getaObjectId().equals(serviceId)){//当前增值产品互斥
						if(spProductStr.indexOf(bizRestraintVo.getzObjectId())>-1){//在列表中存在
							restraintName = bizRestraintVo.getzObjectName();
							restraintId = bizRestraintVo.getzObjectId();
							break;
						}
					}
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//当前增值产品互斥	
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//在列表中存在
							restraintName = bizRestraintVo.getaObjectName();
							restraintId = bizRestraintVo.getaObjectId();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(restraintName!=null&&!restraintName.equals("")){//状态不可用		
			//判断是否都是新增的,互斥不能选
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					prodInstId = productVo.getProductId();
					ArrayList spInfoLst = null;//new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);//判断互斥的产品是否也是新增或者在用的捆绑了销售品
						if(!spProductVo.getState().equalsIgnoreCase("00X")&&restraintId.equals(spProductVo.getServiceId()))
							if(spProductVo.getType().equals("0")){
								resultMap.put("Result","6");//结果失败
								resultMap.put("SPProdSpecId",serviceId);
								resultMap.put("ProductNo",productNo);//号码
								resultMap.put("SPProdSpecName",SPProdSpecName);
								resultMap.put("FailureNote","增值产品"+SPProdSpecName+"与增值产品"+restraintName+"互斥");
							}

							if(spProductVo.getProductOfferId()!=null&&!spProductVo.getProductOfferId().equals("")){
								resultMap.put("Result","6");//结果失败
								resultMap.put("SPProdSpecId",serviceId);
								resultMap.put("ProductNo",productNo);//号码
								resultMap.put("SPProdSpecName",SPProdSpecName);
								resultMap.put("FailureNote",SPProdSpecName+"存在互斥的增值产品"+restraintName+" 并且该增值产品已经捆绑了销售品,需要注销了整个销售品才能进行订购增值产品"+SPProdSpecName);
							}
					}
					
				}
			}
			
			if(resultMap==null||((String)resultMap.get("Result"))==null||((String)resultMap.get("Result")).equals("")){
				//resultMap.put("Result","-1");//结果失败
				resultMap.put("Result","6");
				resultMap.put("ProductNo",productNo);//号码
				resultMap.put("SPProdSpecId",serviceId);
				resultMap.put("SPProdSpecName",SPProdSpecName);
				//resultMap.put("FailureNote","存在互斥的增值产品"+restraintName+",订购"+SPProdSpecName+"将会自动退订"+restraintName);
				resultMap.put("FailureNote",SPProdSpecName+"存在互斥的增值产品"+restraintName);
			}
		}
		else{//状态可用
			resultMap.put("Result","0");//结果成功
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","6");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","互斥鉴权:"+e.toString());
		}
		return resultMap;
	}
}
