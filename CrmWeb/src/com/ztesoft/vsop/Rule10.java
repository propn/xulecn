package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * ����Ʒ��Ȩ
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
							if((spProductVo.getProductOfferId() != null && !spProductVo.getProductOfferId().equals(""))&&!systemId.equals("201")){//��������������Ʒ���ҷ�CRM�����
								flag = false;
								break;
							}
						}
					}
				}
			}
		}
		
		
		if(!flag){//����������Ʒ���ҷ�CRM����
			resultMap.put("Result","10");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��ֵ��Ʒ"+SPProdSpecName+"�������Ż��ײͣ�����ͨ��Ӫҵ��ȡ���Ż��ײͽ����˶�!");
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
			resultMap.put("Result","10");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","����Ʒ��Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
