package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;
import com.ztesoft.vsop.order.PartRuleinterface;
/*
 * �����Ȩ
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
		//���ú����µ�������ֵ��Ʒƴ��һ��������,�ָ
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
		//��ȡ����ֵҵ�񻥳����ֵҵ���ж��Ƿ���֮ǰƴװ�Ĵ���
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo=null;// new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("0")){//����ǻ���
					if(bizRestraintVo.getaObjectId().equals(serviceId)){//��ǰ��ֵ��Ʒ����
						if(spProductStr.indexOf(bizRestraintVo.getzObjectId())>-1){//���б��д���
							restraintName = bizRestraintVo.getzObjectName();
							restraintId = bizRestraintVo.getzObjectId();
							break;
						}
					}
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//��ǰ��ֵ��Ʒ����	
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//���б��д���
							restraintName = bizRestraintVo.getaObjectName();
							restraintId = bizRestraintVo.getaObjectId();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(restraintName!=null&&!restraintName.equals("")){//״̬������		
			//�ж��Ƿ���������,���ⲻ��ѡ
			for(int i=0;i<productInfoLst.size();i++){
				ProductVo productVo = (ProductVo)productInfoLst.get(i);
				if(productVo.getProductNo().equals(productNo)){
					prodInstId = productVo.getProductId();
					ArrayList spInfoLst = null;//new  ArrayList();
					spInfoLst = productVo.getSpProductInfo();
					for(int j=0;j<spInfoLst.size();j++){
						SpProductVo spProductVo= (SpProductVo)spInfoLst.get(j);//�жϻ���Ĳ�Ʒ�Ƿ�Ҳ�������������õ�����������Ʒ
						if(!spProductVo.getState().equalsIgnoreCase("00X")&&restraintId.equals(spProductVo.getServiceId()))
							if(spProductVo.getType().equals("0")){
								resultMap.put("Result","6");//���ʧ��
								resultMap.put("SPProdSpecId",serviceId);
								resultMap.put("ProductNo",productNo);//����
								resultMap.put("SPProdSpecName",SPProdSpecName);
								resultMap.put("FailureNote","��ֵ��Ʒ"+SPProdSpecName+"����ֵ��Ʒ"+restraintName+"����");
							}

							if(spProductVo.getProductOfferId()!=null&&!spProductVo.getProductOfferId().equals("")){
								resultMap.put("Result","6");//���ʧ��
								resultMap.put("SPProdSpecId",serviceId);
								resultMap.put("ProductNo",productNo);//����
								resultMap.put("SPProdSpecName",SPProdSpecName);
								resultMap.put("FailureNote",SPProdSpecName+"���ڻ������ֵ��Ʒ"+restraintName+" ���Ҹ���ֵ��Ʒ�Ѿ�����������Ʒ,��Ҫע������������Ʒ���ܽ��ж�����ֵ��Ʒ"+SPProdSpecName);
							}
					}
					
				}
			}
			
			if(resultMap==null||((String)resultMap.get("Result"))==null||((String)resultMap.get("Result")).equals("")){
				//resultMap.put("Result","-1");//���ʧ��
				resultMap.put("Result","6");
				resultMap.put("ProductNo",productNo);//����
				resultMap.put("SPProdSpecId",serviceId);
				resultMap.put("SPProdSpecName",SPProdSpecName);
				//resultMap.put("FailureNote","���ڻ������ֵ��Ʒ"+restraintName+",����"+SPProdSpecName+"�����Զ��˶�"+restraintName);
				resultMap.put("FailureNote",SPProdSpecName+"���ڻ������ֵ��Ʒ"+restraintName);
			}
		}
		else{//״̬����
			resultMap.put("Result","0");//����ɹ�
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","");
		}
		}
		catch(Exception e){
			resultMap.put("Result","6");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","�����Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
