package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
/*
 * 替换鉴权
 */

public class Rule8 implements PartRuleinterface{
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
		String oldVproductId = "";
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
		//获取该增值业务替换的增值业务，判断是否在之前拼装的串中
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo= null;//new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("2")){//如果是替换
					if(bizRestraintVo.getaObjectId().equals(serviceId)){//当前增值产品替换
						if(spProductStr.indexOf(bizRestraintVo.getzObjectId())>-1){//在列表中存在
							restraintName = bizRestraintVo.getzObjectName();
							oldVproductId = bizRestraintVo.getzObjectId();
							break;
						}
					}
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//当前增值产品替换	
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//在列表中存在
							restraintName = bizRestraintVo.getaObjectName();
							oldVproductId = bizRestraintVo.getaObjectId();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(!restraintName.equals("")){//状态不可用
			//江西替换做成互斥，提示不能订购
			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
				boolean isReplacable = new OrderRelationHelpDao().checkRepalceRelation(serviceId,oldVproductId);
				if(isReplacable){
					resultMap.put("Result","-65");//可以替换，-65标识自动替换
					resultMap.put("FailureNote","增值产品"+SPProdSpecName+"将替换当前的增值产品"+restraintName);
					resultMap.put("oldSPProdSpecId",oldVproductId);
				}else{
					resultMap.put("Result","2");//结果失败
					resultMap.put("FailureNote","增值产品"+SPProdSpecName+"和当前的增值产品"+restraintName + "互斥!");
				}
			}else{
				resultMap.put("Result","-2");//结果失败
				resultMap.put("FailureNote","增值产品"+SPProdSpecName+"将替换当前的增值产品"+restraintName);
			}
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
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
			resultMap.put("Result","8");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","替换鉴权:"+e.toString());
		}
		return resultMap;
	}
}
