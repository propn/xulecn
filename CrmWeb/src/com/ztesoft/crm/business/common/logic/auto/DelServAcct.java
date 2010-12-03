package com.ztesoft.crm.business.common.logic.auto;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.crm.business.common.consts.BusiTables;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.ServConsts;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInstDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.customer.custinfo.dao.AcctDAO;
/**
 * @author liupeidawn
 *
 */	
public class DelServAcct implements AutoProcessor {

	/* 在保存销售品实例信息的时候，通过该功能来默认各个成员主产品的帐务定制信息
	 * 【输入】List servs ,CompInst compInst
	 */
	public void execute(ProcessParameter parameter) throws Exception {
		CompInst compInst = parameter.compInst;
		List servs = parameter.servs;
		
		String compAcctFlag = "0";//测试代码  1为合账 其他为不需要 ；真实环境需要从前台信息获取
		
		String offer_kind = compInst.get(CompInst.OFFER_KIND);
		//如果是一个基础销售品实例，而且操作类型为增加,那么获取归属客户的缺省账户创建账务定制信息，然后返回。
		if(OfferConsts.OFFER_KIND_0.equals(offer_kind)){
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//获取明细对应的产品实例ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								String custId = serv.get(BusiTables.SERV.cust_id);
								String defAcct = this.getDefAcct(custId);//根据客户ID获取默认的账户
								serv.addServAcct("-1",defAcct);
							}
						}
						
					}
				}
			}
		}else if("1".equals(compAcctFlag)) {//如果是一个组合销售品，而且要求合账
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//获取明细对应的产品实例ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							String custId = serv.get(BusiTables.SERV.cust_id);
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								//如果存在在用的serv明细，那么获取相应的合同号作为合账账务。获取的优先级为固话、ADSL，CDMA，小灵通。
								if(!ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){
									//此处暂时留空
								}else{
									String defAcct = this.getDefAcct(custId);
									serv.addServAcct("-1",defAcct);
								}
							}
						}
					}
				}
			}
		}else{//如果不要求合账
			List offerInstDetails = compInst.getOfferInstDetails();
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					OfferInstDetail offerInstDetail = (OfferInstDetail)offerInstDetails.get(i);
					//获取明细对应的产品实例ID
					String instanc_id = offerInstDetail.get(BusiTables.OFFER_INST_DETAIL.instance_id);
					if(servs!=null && servs.size()!=0){
						for(int j =0;j<servs.size();j++){
							Serv serv = (Serv)servs.get(j);
							if(serv.get(Serv.SERV_ID).equals(instanc_id)){
								if(!ServConsts.ACTION_TYPE_A.equals(serv.get(Serv.ACTION_TYPE))){//如果操作类型不为A，那么不处理。
									continue;
								}
								String custId = serv.get(BusiTables.SERV.cust_id);
								String defAcct = this.getDefAcct(custId);
								serv.addServAcct("-1",defAcct);
							}
						}
						
					}
				}
			}
		}
	}
	
	/**
	 * 获取客户缺省账户标志
	 * @param custId
	 * @return
	 */
	public String getDefAcct(String custId) throws Exception{
		String defAcct = "";
		AcctDAO acctDao = new AcctDAO();
		List whereCondParams = new ArrayList();
		whereCondParams.add(custId);
		List accList = acctDao.findByCond(" and cust_id = ? and def_acct_flag = 'Y' ", whereCondParams);
		if(accList!=null && accList.size()!=0){
			HashMap accMap = (HashMap)accList.get(0);
			defAcct = (String)accMap.get("acct_id");
		}
		return defAcct;
	}
}
