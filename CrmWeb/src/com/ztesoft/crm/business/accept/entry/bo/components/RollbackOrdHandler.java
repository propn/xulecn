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
		//2.	如果是一个修改操作，那么作数据回退（这里以附属产品实例为例子）
//		ServProduct servProduct = serv.getServProductById(serv_product_id);
//		if(servProduct!= null)//对于每一个字段变更(field_code, old_valuel, new_value)，处理如下：
//			{
//			servProduct.set(field_code, old_value);
//		}
		//3.	如果是一个删除操作，作增加操作（这里以附属产品实例为例子）
//		ServProduct servProduct = serv.getServProduct(product_id);
//		if(servProuct != null){
//			servProduct.set("action_type", "A");
//			//3.2并依次把ordServProduct上面的属性取值设置到servProduct上。
//		}

		return null;
	}

}
