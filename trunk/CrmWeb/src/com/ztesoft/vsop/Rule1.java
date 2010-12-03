package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * ��Ʒ��Ȩ�����ݴ���Ĳ�Ʒ�жϲ�Ʒ�Ƿ���ڣ���������ڣ����ش���ԭ����������ж�״̬�Ƿ����
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
		
		if(!isExist){//��ֵ��Ʒ������
			resultMap.put("Result","1");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName","");//����Ϊ��
			resultMap.put("FailureNote","��Ʒ�����ڣ���Ӧ�ı�ʶΪ"+serviceId);
		}
		
		if(!flag&&isExist){//���ڵ���״̬������
			resultMap.put("Result","1");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��Ʒ"+SPProdSpecName+"״̬������");
		}
		
		if(flag&&isExist){//���ڲ���״̬����
			resultMap.put("Result","0");//����ɹ�
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","1");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��Ʒ��Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
