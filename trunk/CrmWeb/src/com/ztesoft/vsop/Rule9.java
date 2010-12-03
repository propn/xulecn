package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * ��������Ȩ
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
		//���ú����µ�������ֵ��Ʒƴ��һ��������,�ָ
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
		//��ȡ����ֵҵ����������ֵҵ���ж��Ƿ���֮ǰƴװ�Ĵ���
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo= null;//new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("1")){//����ǻ���
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//��ǰ��ֵ��Ʒ����������ֵ��Ʒ
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//���б��д���
							restraintName = bizRestraintVo.getaObjectName();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(!restraintName.equals("")){//״̬������
			resultMap.put("Result","9");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��ֵ��Ʒ"+restraintName+"����"+SPProdSpecName+",����ȡ��");
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
			resultMap.put("Result","9");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","��������Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
