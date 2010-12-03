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

	
	/* 当创建一个主产品实例，或者主产品实例发生产品变迁、资费变更、加入新的组合销售品的时候，可以调用该方法，自动增加必须的附属产品，并默认附属产品的属性
	 * Serv serv List offerDetailIds; String priceId; Map common;
	 */
	public void execute(ProcessParameter parameter) throws Exception{
		//进行附属产品约束默认处理
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		//根据产品关联的销售品列表获取销售品约束列表
		List servProductRestricts  = SpecsData.getServProductRestricts(parameter.offerDetailIds);
		if(servProductRestricts!=null&&servProductRestricts.size()!=0){
			for(int i = 0;i<servProductRestricts.size();i++){
				
				ServProductRestrict servProductRestrict = (ServProductRestrict)servProductRestricts.get(i);
				//从已经购买的附属产品中获取当前附属产品信息
				ServProduct servProduct = parameter.serv.getServProductById(servProductRestrict.product_id);
				
				ServProduct needSetValueProd = null;//需要进行约束默认处理的附属产品
				
				if(servProduct==null){
					ServProduct  servproductNew  = parameter.serv.addServProduct(servProductRestrict.product_id);//如果没有购买 就为他购买 并需要进行默认约束处理
					needSetValueProd =  servproductNew;
				}else if(ServConsts.ACTION_TYPE_A.equals(servProduct.get(Serv.ACTION_TYPE))){//已经购买 但是为新增 则需要进行默认约束处理
					needSetValueProd = servProduct;
				}
				//获取约束列表
				List attrRestricts = servProductRestrict.attrRestrict;
				//根据约束设置默认值
				for(int j = 0;i<attrRestricts.size();j++){
					AttrRestrict attrRestrict = (AttrRestrict)attrRestricts.get(j);
					String fieldname = attrRestrict.getField_name();
					String nValue = needSetValueProd.get(fieldname);//获取当前值
					
					HashMap checkMap = (HashMap)attrRestrict.isMeet(nValue);//进行约束校验
					//获取默认值
					String defValue  = (String)checkMap.get("defvalue");
					String check =  (String)checkMap.get("check");
					if ("false".equals(check)){//如果当前值为空 或者不满足约束 那么设置成默认值
						needSetValueProd.set(fieldname,defValue);
					}
				}
			}
		}
		
	}

}
