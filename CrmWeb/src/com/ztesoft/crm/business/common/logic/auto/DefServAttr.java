package com.ztesoft.crm.business.common.logic.auto;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.crm.business.common.cache.AttrRestrict;
import com.ztesoft.crm.business.common.cache.SpecsData;
import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.tools.DataLoader;

/**
 * 默认主产品实例信息处理
 * @author liupeidawn
 *
 */
public class DefServAttr implements AutoProcessor {

	/*
	 * @author lp
	 * 当创建一个主产品实例，或者主产品实例发生产品变迁、资费变更、加入新的组合销售品的时候，可以调用该方法，根据约束的要求重新设置属性的取值。
	 * 【输入】Serv serv List offerDetailIds Map common 
	 */
	public void execute(ProcessParameter parameter)throws Exception {
		String servAction = parameter.serv.get(Serv.ACTION_TYPE);
		if(!ServConsts.ACTION_TYPE_A.equals(servAction)){//如果不为新增 需要查询用户最新实例数据
			HashMap servMap = (HashMap)DataLoader.getInst().selectServData(parameter.serv.get(Serv.SERV_ID));
			//如果是在用 需要将最新表基本信息设置到SERV对象中
			//HashMap cloneMap=new HashMap();
			Iterator  uIte=servMap.keySet().iterator();
			while(uIte.hasNext()){
				 String uKey=(String)uIte.next();//最新表数据字段名
				 String uValue = (String)servMap.get(uKey);//最新表数据字段值
				 
				 String nValue = parameter.serv.get(uKey);//前台对应字段的值
				 String[] feilds = BusiTables.SERV.TABLE_FIELDS;
				 boolean continuFlag = true;
				 for(int i = 0;i<feilds.length;i++){
					 if(feilds[i].equals(uKey)){//如果SERV表不包含的KEY值 就是属性信息 这里不对属性信息作处理
						 continuFlag = false;
						 break;
					 }
				 }
				 if(continuFlag) continue;
				 if(nValue==null||"".equals(nValue)){
					 parameter.serv.set(uKey, uValue);
				 }
			}
		}
		//对属性信息进行约束默认处理
		if(parameter.offerDetailIds!=null && parameter.offerDetailIds.size()!=0){
			List servAttrRestricts  = SpecsData.getServAttrRestricts(parameter.offerDetailIds);
			if(servAttrRestricts!=null&&servAttrRestricts.size()!=0){
				for(int i = 0;i<servAttrRestricts.size();i++){
					AttrRestrict servAttrRestrict = (AttrRestrict)servAttrRestricts.get(i);
					String fieldname = servAttrRestrict.getField_name();
					String nValue = parameter.serv.get(fieldname);//获取当前值
					
					HashMap checkMap = (HashMap)servAttrRestrict.isMeet(nValue);//进行约束校验
					
					String defValue  = (String)checkMap.get("defvalue");
					String check =  (String)checkMap.get("check");
					if ("false".equals(check)){//如果当前值为空 那么设置成默认值
						parameter.serv.set(fieldname,defValue);
					}
				}
			}
		}

	}
	

}
