package com.ztesoft.crm.business.common.logic.auto;

import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.common.cache.AttrRestrict;
import com.ztesoft.crm.business.common.cache.ServProductRestrict;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServProduct;

/**
 * @author liupeidawn
 *
 */
public class AddServProduct implements AutoProcessor {

	
	/* ������һ������Ʒʵ������������Ʒʵ��������Ʒ��Ǩ���ʷѱ���������µ��������Ʒ��ʱ�򣬿��Ե��ø÷������Զ����ӱ���ĸ�����Ʒ����Ĭ�ϸ�����Ʒ������
	 * Serv serv List offerDetailIds; String priceId; Map common;
	 */
	public void execute(ProcessParameter parameter) throws Exception{
		//���и�����ƷԼ��Ĭ�ϴ���
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		//���ݲ�Ʒ����������Ʒ�б��ȡ����ƷԼ���б�
		List servProductRestricts  = SpecsData.getServProductRestricts(parameter.offerDetailIds);
		if(servProductRestricts!=null&&servProductRestricts.size()!=0){
			for(int i = 0;i<servProductRestricts.size();i++){
				
				ServProductRestrict servProductRestrict = (ServProductRestrict)servProductRestricts.get(i);
				//���Ѿ�����ĸ�����Ʒ�л�ȡ��ǰ������Ʒ��Ϣ
				ServProduct servProduct = parameter.serv.getServProductById(servProductRestrict.product_id);
				
				ServProduct needSetValueProd = null;//��Ҫ����Լ��Ĭ�ϴ���ĸ�����Ʒ
				
				if(servProduct==null){
					ServProduct  servproductNew  = parameter.serv.addServProduct(servProductRestrict.product_id);//���û�й��� ��Ϊ������ ����Ҫ����Ĭ��Լ������
					needSetValueProd =  servproductNew;
				}else if(ServConsts.ACTION_TYPE_A.equals(servProduct.get(Serv.ACTION_TYPE))){//�Ѿ����� ����Ϊ���� ����Ҫ����Ĭ��Լ������
					needSetValueProd = servProduct;
				}
				//��ȡԼ���б�
				List attrRestricts = servProductRestrict.attrRestrict;
				//����Լ������Ĭ��ֵ
				for(int j = 0;i<attrRestricts.size();j++){
					AttrRestrict attrRestrict = (AttrRestrict)attrRestricts.get(j);
					String fieldname = attrRestrict.getField_name();
					String nValue = needSetValueProd.get(fieldname);//��ȡ��ǰֵ
					
					HashMap checkMap = (HashMap)attrRestrict.isMeet(nValue);//����Լ��У��
					//��ȡĬ��ֵ
					String defValue  = (String)checkMap.get("defvalue");
					String check =  (String)checkMap.get("check");
					if ("false".equals(check)){//�����ǰֵΪ�� ���߲�����Լ�� ��ô���ó�Ĭ��ֵ
						needSetValueProd.set(fieldname,defValue);
					}
				}
			}
		}
		
	}

}
