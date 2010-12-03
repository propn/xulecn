package com.ztesoft.crm.business.accept.entry.bo.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.accept.entry.bo.BusiComponent;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV;
import com.ztesoft.crm.business.common.logic.auto.AddServProduct;
import com.ztesoft.crm.business.common.logic.auto.DefServAttr;
import com.ztesoft.crm.business.common.logic.auto.DelMutexOfferInst;
import com.ztesoft.crm.business.common.logic.auto.DelServAcct;
import com.ztesoft.crm.business.common.logic.auto.DelServProduct;
import com.ztesoft.crm.business.common.logic.auto.ProcessParameter;
import com.ztesoft.crm.business.common.logic.model.CommonData;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.utils.DataUtil;


/**
 * 调用/VsopWeb/src/com/ztesoft/crm/business/common/logic
 * 销售品订购/变更自动受理器
 * */
public class AcceptHandler implements BusiComponent{

	public Object execute() throws Exception{

		CommonData data = CommonData.getData();
		HashMap common = data.getAttributes();
		List compInsts = data.getCompInsts();//获取销售品实例数据
		List servs = data.getServs();//获取主产品实例ID
		ProcessParameter parameter = new ProcessParameter();
		parameter.setCompInsts(compInsts);//设置销售品实例参数
		
		DelMutexOfferInst delMutexOfferInst = new DelMutexOfferInst();//删除无效/互斥销售品处理类
		//删除无效/互斥销售的实现方法
		delMutexOfferInst.execute(parameter);
		
		if(servs!=null && servs.size()!=0){
			//前台传入的动作不准确 需要此方法进行更正
			DataUtil.getInst().refreshActionType(servs, "Serv");
			for(int i =0;i<servs.size();i++){
				List offerDetailIds = new ArrayList();//存放有效销售品明细数据容器
				Serv serv = (Serv)servs.get(i);
				String serv_id = serv.get(Serv.SERV_ID);
				if(ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){
					serv.set(SERV.acc_nbr, "-100000");//如果为新增 则为其默认一个号码
				}
				//获取有效销售品明细数据
				offerDetailIds = DataUtil.getInst().getOfferDetailIds(compInsts, serv_id);
				/****************默认主产品基本信息********************/
				//实例化 默认主产品基本信息自动处理类
				DefServAttr defServAttr = new DefServAttr();
				parameter = new ProcessParameter();//重新实例化参数对象
				parameter.setServ(serv);
				parameter.setOfferDetailIds(offerDetailIds);
				parameter.setCommon(data.getAttributes());
				// 默认主产品基本信息自动处理类 执行方法 
				defServAttr.execute(parameter);
				/****************增加必须附属产品********************/
				AddServProduct addServProduct = new AddServProduct();
				addServProduct.execute(parameter);
				/***************删除互斥附属产品（针对于非增加的产品）************************/
				if(!ServConsts.ACTION_TYPE_A.equals(serv.get(SERV.action_type))){
					DelServProduct delServProduct = new DelServProduct();
					delServProduct.execute(parameter);
				}
				
			}
		} 
		/***************默认账务定制（如果是组合销售品类业务， 那么针对组合销售品（一个）调用默认帐；
		 * 否则如果是基础销售品类业务，那么调用相应的（增加或者修改）的基础销售进行账务定制默认
		(说明，任何一次业务受理，都是针对一个主销售品，其他的销售品是该主销售品引发的自动处理)）*****/
		if(compInsts!=null && compInsts.size()!=0){
			for(int i = 0;i<compInsts.size();i++){
				CompInst compInst = (CompInst)compInsts.get(i);
				parameter = new ProcessParameter();//重新实例化参数对象
				parameter.setServs(servs);
				parameter.setCompInst(compInst);
				DelServAcct delServAcct = new DelServAcct();
				delServAcct.execute(parameter);
			}
		}
		return null;
	}
	
	

}
