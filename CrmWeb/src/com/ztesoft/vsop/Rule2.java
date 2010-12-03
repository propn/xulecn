package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * SP/CP��Ȩ�����ݴ���Ĳ�Ʒ�ж�SP/CP�Ƿ���ڣ���������ڣ����ش���ԭ����������ж�״̬�Ƿ����
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
		
		
		if(!flag){//״̬������
			resultMap.put("Result","2");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��Ʒ"+SPProdSpecName+"������SP/CP״̬������");
		}
		else{//״̬����
			resultMap.put("Result","0");//����ɹ�
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","2");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","SP/CP��Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
