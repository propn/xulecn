package com.ztesoft.vsop;

import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.vsop.engine.dao.OrderRelationHelpDao;
import com.ztesoft.vsop.order.PartRuleinterface;
import com.ztesoft.vsop.web.DcSystemParamManager;
/*
 * �滻��Ȩ
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
		//��ȡ����ֵҵ���滻����ֵҵ���ж��Ƿ���֮ǰƴװ�Ĵ���
		if(bizRestraintLst!=null){
			for(int i=0;i<bizRestraintLst.size();i++){
				BizRestraintVo bizRestraintVo= null;//new BizRestraintVo(); 
				bizRestraintVo = (BizRestraintVo)bizRestraintLst.get(i);
				if(bizRestraintVo.getRestraintType().equals("2")){//������滻
					if(bizRestraintVo.getaObjectId().equals(serviceId)){//��ǰ��ֵ��Ʒ�滻
						if(spProductStr.indexOf(bizRestraintVo.getzObjectId())>-1){//���б��д���
							restraintName = bizRestraintVo.getzObjectName();
							oldVproductId = bizRestraintVo.getzObjectId();
							break;
						}
					}
					if(bizRestraintVo.getzObjectId().equals(serviceId)){//��ǰ��ֵ��Ʒ�滻	
						if(spProductStr.indexOf(bizRestraintVo.getaObjectId())>-1){//���б��д���
							restraintName = bizRestraintVo.getaObjectName();
							oldVproductId = bizRestraintVo.getaObjectId();
							break;
						}
					}
					
				}
			}
			
		}
		
		
		if(!restraintName.equals("")){//״̬������
			//�����滻���ɻ��⣬��ʾ���ܶ���
			if(CrmConstants.JX_PROV_CODE.equals(DcSystemParamManager.getParameter(VsopConstants.DC_PROVINCE_CODE))){
				boolean isReplacable = new OrderRelationHelpDao().checkRepalceRelation(serviceId,oldVproductId);
				if(isReplacable){
					resultMap.put("Result","-65");//�����滻��-65��ʶ�Զ��滻
					resultMap.put("FailureNote","��ֵ��Ʒ"+SPProdSpecName+"���滻��ǰ����ֵ��Ʒ"+restraintName);
					resultMap.put("oldSPProdSpecId",oldVproductId);
				}else{
					resultMap.put("Result","2");//���ʧ��
					resultMap.put("FailureNote","��ֵ��Ʒ"+SPProdSpecName+"�͵�ǰ����ֵ��Ʒ"+restraintName + "����!");
				}
			}else{
				resultMap.put("Result","-2");//���ʧ��
				resultMap.put("FailureNote","��ֵ��Ʒ"+SPProdSpecName+"���滻��ǰ����ֵ��Ʒ"+restraintName);
			}
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
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
			resultMap.put("Result","8");//���ʧ��
			resultMap.put("ProductNo",productNo);//����
			resultMap.put("SPProdSpecId",serviceId);
			resultMap.put("SPProdSpecName",SPProdSpecName);
			resultMap.put("FailureNote","�滻��Ȩ:"+e.toString());
		}
		return resultMap;
	}
}
