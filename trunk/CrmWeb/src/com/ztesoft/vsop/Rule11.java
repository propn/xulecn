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
							//����Ѿ�����������Ʒ�����˾ͼ�Ȩ��ͨ��
							if(!"".equals(packageId)||!"".equals(productOfferId)){
								restraintName = serviceName;
								break;
							}
							//���ԭ���ǵ���Ʒ,�����µ������ǲ���CRM����������ƷҲ��Ȩ��ͨ��
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
			resultMap.put("Result","11");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��ֵ��Ʒ"+restraintName+"�Ѿ�����");
		}
		else{//״̬����
			resultMap.put("Result","0");//����ɹ�
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","11");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","ͬ��Ʒ��Ȩ:"+e.toString());
			logger.error("", e);
		}
		return resultMap;
	}
}
