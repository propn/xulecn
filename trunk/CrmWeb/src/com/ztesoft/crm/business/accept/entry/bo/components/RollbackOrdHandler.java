package com.ztesoft.crm.business.accept.entry.bo.components;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
import com.ztesoft.crm.business.common.sem.engine.BusiEngine;

public class RollbackOrdHandler implements BusiComponent {

	public Object execute() {
		// TODO Auto-generated method stub
//		Map para = 
		try{
		String serv_product_id = (String)BusiEngine.get("serv_product_id");
		Serv serv = new Serv();
		ServProduct servProduct = serv.getServProductById(serv_product_id);
		if (servProduct != null){
			if (servProduct.get ("action_ttype").equals("A") ) serv.delServProductByID(serv_product_id);
			servProduct.set("action_type", "D");
		}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//2.	�����һ���޸Ĳ�������ô�����ݻ��ˣ������Ը�����Ʒʵ��Ϊ���ӣ�
//		ServProduct servProduct = serv.getServProductById(serv_product_id);
//		if(servProduct!= null)//����ÿһ���ֶα��(field_code, old_valuel, new_value)���������£�
//			{
//			servProduct.set(field_code, old_value);
//		}
		//3.	�����һ��ɾ�������������Ӳ����������Ը�����Ʒʵ��Ϊ���ӣ�
//		ServProduct servProduct = serv.getServProduct(product_id);
//		if(servProuct != null){
//			servProduct.set("action_type", "A");
//			//3.2�����ΰ�ordServProduct���������ȡֵ���õ�servProduct�ϡ�
//		}

		return null;
	}

}
