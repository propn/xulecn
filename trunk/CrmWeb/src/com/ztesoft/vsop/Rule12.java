package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
/**
 * 
 * 小灵通用户不能订购全国的产品 全国产品是135开头的，115开头是本省的产品 
 * c网不能定本网的超级QQ、c网可以订全国的超级qq
 *
 */
public class Rule12 implements PartRuleinterface{
	private static Logger logger = Logger.getLogger(Rule12.class);
	public HashMap authentication(HashMap map){
		
		
		//productId要从crm的产品类型编码转换成0:手机1：小灵通2：固话
		String dcM = DcSystemParamManager.getParameter(VsopConstants.DC_MSISDN);
		String dcPhs = DcSystemParamManager.getParameter(VsopConstants.DC_PHS);
		//String dcPstn = DcSystemParamManager.getParameter(VsopConstants.DC_PSTN);

	
		SpProductVo spProductVoIn = (SpProductVo)map.get("SpProductVo");//本次鉴权的增值产品
		String serviceId = spProductVoIn.getServiceId();//
		String productNo = spProductVoIn.getProductNo();
		String serviceName = spProductVoIn.getNameCn();
		String productNbr=spProductVoIn.getProductNbr();//增值产品外编码
		
		ArrayList productInfoLst = (ArrayList)map.get("productInfoLst");//用户信息
		HashMap resultMap = new HashMap();
		String SPProdSpecName = serviceName;
		try{
		String productNbr3=productNbr.substring(0,3);//增值产品前三位
	
		if(productInfoLst!=null){
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					String productId=productVo.getProductSpec();
					if(dcM.indexOf(productId) > -1){//C网手机	

						if(VsopConstants.PRODUCTNBR_115_QQ.equals(productNbr)){//C网的用户 不能订购全省的qq。
							resultMap.put("Result","12");//结果失败
							resultMap.put("ProductNo",productNo);//号码
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","C网用户不能订购本网增值业务："+serviceName);
						}	else{//可以订购
							resultMap.put("Result","0");//结果成功
							resultMap.put("ProductNo",productNo);//号码
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","");
						}
					}
					if(dcPhs.indexOf(productId) > -1){//小灵通用户
						if(VsopConstants.PRODUCTNBR_135.equals(productNbr3)){//小灵通不能订购全国增值产品。
							resultMap.put("Result","12");//结果失败
							resultMap.put("ProductNo",productNo);//号码
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","小灵通用户不能订购全网的增值业务："+serviceName);
						}else{//可以订购
							resultMap.put("Result","0");//结果成功
							resultMap.put("ProductNo",productNo);//号码
							resultMap.put("SPProdSpecId",serviceId);
							resultMap.put("SPProdSpecName",SPProdSpecName);
							resultMap.put("FailureNote","");
						}
					}
				}
			}
		}
					
		}
		catch(Exception e){
			resultMap.put("Result","12");//结果失败
			resultMap.put("ProductNo",productNo);//号码
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","本网全网增值产品产品鉴权:"+e.toString());
		}
		return resultMap;
	}
}
