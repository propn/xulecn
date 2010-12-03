package com.ztesoft.crm.business.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.crm.business.common.consts.KeyValues;
import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST_ATTR;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST_DETAIL;
import com.ztesoft.crm.business.common.consts.BusiTables.SERV;
import com.ztesoft.crm.business.common.inst.dao.OffInstDAO;
import com.ztesoft.crm.business.common.inst.dao.OffInstDetaDAO;
import com.ztesoft.crm.business.common.inst.dao.ServDAO;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInstDetail;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.model.VServ;
import com.ztesoft.crm.business.common.query.SqlMapExe;

public final class DataUtil {

	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL查询工具
	public static boolean isEmptiy(List list){
		
		
		if(list==null)
			return true;
		
		
		return list.size()==0;
		
	}
	
	public static DataUtil instance=null;
		
		private DataUtil(){
			
		}
		public static DataUtil getInst() {
	
			if(instance==null){
				synchronized(SeqUtil.class){
					
					if(instance==null){
						instance=new DataUtil();
					}	
				}
			}
			return instance;
		}
	
		/**
		 * 将前台传回的销售品实例数据转换成后台需要的格式
		 * map 销售品包实例数据（前台）
		 * loginInfo 登陆信息
		 * comp_inst_id 销售品包父销售品ID
		 * custId 客户ID
		 * */
		public   HashMap parseOffer(HashMap map,HashMap loginInfo,String comp_inst_id,String custId,String cust_ord_id) {
			HashMap resMap = new HashMap();
			
			resMap.put(OFFER_INST.ord_item_id, "");
			resMap.put(OFFER_INST.cust_ord_id, cust_ord_id);
			resMap.put(OFFER_INST.ord_id, "");
			resMap.put(OFFER_INST.action_type, map.get(OFFER_INST.action_type)); 
			resMap.put(OFFER_INST.state_date, "");
			resMap.put(OFFER_INST.end_time, "");
			resMap.put(OFFER_INST.beg_time,"");
			resMap.put(OFFER_INST.staff_no, loginInfo.get("vg_oper_id"));
			resMap.put(OFFER_INST.site_no, loginInfo.get("vg_depart_id"));
			resMap.put(OFFER_INST.comp_inst_id, comp_inst_id);
			resMap.put(OFFER_INST.product_offer_instance_id, map.get("productOfferInstanceId"));
			resMap.put(OFFER_INST.cust_id, custId);
			resMap.put(OFFER_INST.product_offer_id, map.get("offerId"));
			resMap.put(OFFER_INST.offer_kind, map.get("offerKind"));
			resMap.put(OFFER_INST.create_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.eff_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.exp_date, DAOUtils.getFormatedDate());
			//resMap.put(OFFER_INST.state, "");
			
			return resMap;
		}
		/**
		 * 将前台传回的已选包外优惠实例数据转换成后台需要的格式
		 * map 已选包外优惠实例数据（前台）
		 * loginInfo 登陆信息
		 * custId 客户ID
		 * */
		public  HashMap parseOfferSale(HashMap map,HashMap loginInfo,String custId,String cust_ord_id) {
			HashMap resMap = new HashMap();
			
			resMap.put(OFFER_INST.ord_item_id, "");
			resMap.put(OFFER_INST.cust_ord_id, cust_ord_id);
			resMap.put(OFFER_INST.ord_id, "");
			resMap.put(OFFER_INST.action_type,map.get(OFFER_INST.action_type));
			resMap.put(OFFER_INST.state_date, "");
			resMap.put(OFFER_INST.end_time, "");
			resMap.put(OFFER_INST.beg_time,"");
			resMap.put(OFFER_INST.staff_no, loginInfo.get("vg_oper_id"));
			resMap.put(OFFER_INST.site_no, loginInfo.get("vg_depart_id"));
			resMap.put(OFFER_INST.comp_inst_id, map.get("rela_offer_instance_id"));
			resMap.put(OFFER_INST.product_offer_instance_id, map.get("product_offer_instance_id"));
			resMap.put(OFFER_INST.cust_id, custId);
			resMap.put(OFFER_INST.product_offer_id, map.get("package_id"));
			resMap.put(OFFER_INST.offer_kind, map.get("offer_kind"));
			resMap.put(OFFER_INST.create_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.eff_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.exp_date, DAOUtils.getFormatedDate());
			//resMap.put(OFFER_INST.state, "");
			
			return resMap;
		}
		
		/**
		 * 将前台传回的基础销售品数据转换成后台需要的格式
		 * map 基础销售品实例数据（前台）
		 * loginInfo 登陆信息
		 * custId 客户ID
		 * */
		public   HashMap parseBaseOffer(HashMap map,HashMap loginInfo,String custId,String cust_ord_id) {
			HashMap resMap = new HashMap();
			
			resMap.put(OFFER_INST.ord_item_id, "");
			resMap.put(OFFER_INST.cust_ord_id, cust_ord_id);
			resMap.put(OFFER_INST.ord_id, "");
			resMap.put(OFFER_INST.action_type,map.get(OFFER_INST.action_type));
			resMap.put(OFFER_INST.state_date, "");
			resMap.put(OFFER_INST.end_time, "");
			resMap.put(OFFER_INST.beg_time,"");
			resMap.put(OFFER_INST.staff_no,loginInfo.get("vg_oper_id"));
			resMap.put(OFFER_INST.site_no, loginInfo.get("vg_depart_id"));
			resMap.put(OFFER_INST.comp_inst_id,map.get("product_offer_instance_id"));
			resMap.put(OFFER_INST.product_offer_instance_id, map.get("product_offer_instance_id"));
			resMap.put(OFFER_INST.cust_id, custId);
			resMap.put(OFFER_INST.product_offer_id, map.get("offer_id"));
			resMap.put(OFFER_INST.offer_kind, map.get("offer_kind"));
			resMap.put(OFFER_INST.create_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.eff_date, DAOUtils.getFormatedDate());
			resMap.put(OFFER_INST.exp_date, DAOUtils.getFormatedDate());
			//resMap.put(OFFER_INST.state, "");
			
			return resMap;
		}
		
		/**
		 * 将前台传回的销售品属性数据转换成后台需要的格式
		 * map 销售品属性实例数据（前台）
		 * loginInfo 登陆信息
		 * */
		public   HashMap parseOfferAttr(HashMap map,HashMap loginInfo,String cust_ord_id) {
			HashMap resMap = new HashMap();
			resMap.put(OFFER_INST_ATTR.ord_item_id, "");
			resMap.put(OFFER_INST_ATTR.cust_ord_id, cust_ord_id);
			resMap.put(OFFER_INST_ATTR.ord_id, "");
			resMap.put(OFFER_INST_ATTR.action_type, KeyValues.ACTION_TYPE_A);
			resMap.put(OFFER_INST_ATTR.state_date, "");
			resMap.put(OFFER_INST_ATTR.end_time, "");
			resMap.put(OFFER_INST_ATTR.beg_time,"");
			resMap.put(OFFER_INST_ATTR.product_offer_instance_id, map.get(OFFER_INST_ATTR.product_offer_instance_id));
			resMap.put(OFFER_INST_ATTR.product_offer_id, map.get("offer_id"));
			resMap.put(OFFER_INST_ATTR.attr_id, map.get("attr_id"));
			resMap.put(OFFER_INST_ATTR.field_name, map.get(OFFER_INST_ATTR.field_name));
			resMap.put(OFFER_INST_ATTR.comp_inst_id, map.get(OFFER_INST_ATTR.comp_inst_id));
			resMap.put(OFFER_INST_ATTR.attr_val, map.get("attr_value"));
			return resMap;
		}
		
		/**
		 * 将前台传回的包外优惠数据转换成后台需要的销售品明细数据格式 
		 * map 销售品明细规格数据（前台）
		 * product_offer_instance_id 销售品实例ID
		 * serv_id 产品实例ID
		 * */
		public   HashMap parseOfferDetail(HashMap map,HashMap loginInfo,String product_offer_instance_id,String serv_id,String cust_ord_id) {
			HashMap resMap = new HashMap();
			resMap.put(OFFER_INST_DETAIL.ord_item_id, "");
			resMap.put(OFFER_INST_DETAIL.cust_ord_id, cust_ord_id);
			resMap.put(OFFER_INST_DETAIL.ord_id, "");
			resMap.put(OFFER_INST_DETAIL.action_type, map.get(OFFER_INST.action_type));
			resMap.put(OFFER_INST_DETAIL.state_date, "");
			resMap.put(OFFER_INST_DETAIL.end_time, "");
			resMap.put(OFFER_INST_DETAIL.beg_time,"");
			resMap.put(OFFER_INST_DETAIL.staff_no, loginInfo.get("vg_oper_id"));
			resMap.put(OFFER_INST_DETAIL.site_no, loginInfo.get("vg_depart_id"));
			resMap.put(OFFER_INST_DETAIL.product_offer_instance_id, product_offer_instance_id);
			resMap.put(OFFER_INST_DETAIL.offer_detail_id, map.get("offer_detail_id"));
			resMap.put(OFFER_INST_DETAIL.comp_role_id, map.get("comp_role_id"));
			resMap.put(OFFER_INST_DETAIL.comp_inst_id, map.get("comp_inst_id"));
			resMap.put(OFFER_INST_DETAIL.instance_type, map.get("element_type"));
			resMap.put(OFFER_INST_DETAIL.instance_id, serv_id);
			return resMap;
		}
		
		/**
		 * 获取已选销售品包的父销售品实例
		 * @param 前台销售品实例
		 * */
		public   HashMap getOfferCompInst(List offers) {
			
			HashMap resMap = new HashMap();
			for(int i = 0;i<offers.size();i++)
			{
				HashMap offerMap = (HashMap)offers.get(i);
				String offer_kind = (String)offerMap.get("offerKind");
				if("0".equals(offer_kind)||"1".equals(offer_kind))
				{
					return offerMap;
				}
			}
			
			return resMap;
		}
		
		
		/**
		 * 获取父销售品实例ID
		 * List 前台销售品实例
		 * String 子销售品实例ID
		 * */
		public   String getOfferCompInstId(List offers,String offer_inst_id) {
			
			HashMap resMap = new HashMap();
			for(int i = 0;i<offers.size();i++)
			{
				HashMap offerMap = (HashMap)offers.get(i);
				String product_offer_instance_id = (String)offerMap.get(OFFER_INST.product_offer_instance_id);
				if(offer_inst_id.equals(product_offer_instance_id))
				{
					return (String)offerMap.get(OFFER_INST.comp_inst_id);
				}
			}
			
			return null;
		}
		
		
		/**
		 * 获取产品关联的基础销售品规格ID
		 * @param 产品规格ID
		 * */
		public HashMap getRelaOffer(String product_id)
		 {
			
			 String[] sqlParams = new String[1];//构建SQL工具查询参数
			 sqlParams[0] = product_id;
			 HashMap rsMap = new HashMap();
			 ArrayList offerList = (ArrayList)sqlMapExe.queryForMapList("findReleaOfferSql", sqlParams);
			 if(offerList!=null&&offerList.size()!=0) {
				 rsMap = (HashMap)offerList.get(0);
				 return rsMap;
			 }
			 
			 return rsMap;
		 }

		/**
		 * 获取销售品的明细规格数据
		 * @String 销售品规格ID
		 * @String 产品规格id
		 * @String 角色id
		 * */
		public HashMap getOfferDetail(String offerId,String product_id,String comp_role_id)
		 {
			
			 String[] sqlParams = new String[2];//构建SQL工具查询参数
			 sqlParams[0] = offerId;
			 sqlParams[1] = product_id;
			// sqlParams[2] = comp_role_id;
			 HashMap rsMap = new HashMap();
			 ArrayList offerDetailList = (ArrayList)sqlMapExe.queryForMapList("findOfferDetailSql", sqlParams);
			 if(offerDetailList!=null&&offerDetailList.size()!=0)
				 rsMap  = (HashMap)offerDetailList.get(0);
			 return rsMap;
		 }
		/**
		 * 获取基础销售品实例关联的产品实例ID
		 * @String 基础销售品实例ID
		 * @List 销售品明细数据集（前台）
		 * */
		public HashMap getServIdByBaseOff(String rela_offer_instance_id,List offerDetails)
		 {
			String serv_id = "";
			HashMap servMap = new HashMap();
			for(int i = 0;i<offerDetails.size();i++){
				HashMap detailMap = (HashMap)offerDetails.get(i);
				String rela_offer_inst_id = (String)detailMap.get("rela_offer_inst_id");
				if(rela_offer_inst_id.equals(rela_offer_instance_id)){
					return detailMap;
				}
					
			}
			
			return servMap;
		 }
		
		/**
		 * 获取销售品实例关联的产品实例ID
		 * @String 销售品实例ID
		 * @List 销售品明细数据集（前台）
		 * */
		public HashMap getServIdByOff(String product_offer_instance_id,List offerDetails)
		 {
			String serv_id = "";
			HashMap servMap = new HashMap();
			for(int i = 0;i<offerDetails.size();i++){
				HashMap detailMap = (HashMap)offerDetails.get(i);
				String offer_instance_id = (String)detailMap.get(OFFER_INST_DETAIL.product_offer_instance_id);
				if(product_offer_instance_id.equals(offer_instance_id)){
					return detailMap;
				}
			}
			return servMap;
		 }
		
		
		/**
		 * 获取包外优惠关联的基础销售品实例ID
		 * @String 包外优惠销售品实例ID
		 * @List 包外优惠数据集（前台）
		 * */
		public HashMap getBaseInstBySaleInst(String product_offer_instance_id,List offerSales)
		 {
			String serv_id = "";
			HashMap servMap = new HashMap();
			for(int i = 0;i<offerSales.size();i++){
				HashMap saleMap = (HashMap)offerSales.get(i);
				String offer_instance_id = (String)saleMap.get(OFFER_INST.product_offer_instance_id);
				if(product_offer_instance_id.equals(offer_instance_id)){
					return saleMap;
				}
			}
			return servMap;
		 }
		
		/**
		 * 将MAP转换成LIST 
		 * @param busiMap
		 * @param type 决定其中包含何对象
		 * @return
		 */
		public List parseMapToList(HashMap busiMap,String type){
			List resList = new ArrayList();
			Iterator  ite=busiMap.keySet().iterator();
			while(ite.hasNext()){
				String key = (String)ite.next();
				if("Serv".equals(type)){
					Serv serv = new Serv();
					serv.set(Serv.SERV_ID, key);
					resList.add(serv);
				}else if("VServ".equals(type)){
					VServ serv = new VServ();
					HashMap serv_attrs = new HashMap();
					serv_attrs.put(Serv.SERV_ID, key);
					HashMap servInfo =  (HashMap)busiMap.get(key);
					serv_attrs.put(Serv.COMP_INST_ID, servInfo.get(Serv.COMP_INST_ID));
					serv_attrs.put(Serv.PRODUCT_ID, servInfo.get(Serv.PRODUCT_ID));
					serv.setServ_attrs(serv_attrs);
					resList.add(serv);
				}else{
					resList.add(key);
				}
			}
			return resList;
		 }
		
		
		/**
		 * 获取主产品实例有效的销售品明细ID
		 * @param compInsts
		 * @param serv_id
		 * @return
		 */
		public List getOfferDetailIds(List compInsts, String serv_id) throws Exception{
			List offerDetailIds = new ArrayList();
			HashMap offerDetailIdMap = new HashMap();
			List params = new ArrayList();//参数列表
			params.add(serv_id);
			params.add("10A");
			//从数据库中先获取该主产品有效的销售品明细实例数据
			List offerInstDetails =  (ArrayList)sqlMapExe.queryForMapList("findOfferDetailIds", params);
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					HashMap offerInstDetail = (HashMap)offerInstDetails.get(i);
					String offer_detail_id =  (String)offerInstDetail.get(OFFER_INST_DETAIL.offer_detail_id);
					//将数据放入到offerDetailIdMap中 方便以后进行剔除
					offerDetailIdMap.put(offer_detail_id, offer_detail_id);
				}
			}
			
			if(compInsts!=null && compInsts.size()!=0){
				for(int i =0;i<compInsts.size();i++){
					CompInst compInst = (CompInst)compInsts.get(i);
					List offerDetails = compInst.getOfferInstDetails();
					if(offerDetails!=null && offerDetails.size()!=0){
						for(int j =0;j<offerDetails.size();j++){
							OfferInstDetail offerDetail = (OfferInstDetail)offerDetails.get(j);
							String instance_id = offerDetail.get(OFFER_INST_DETAIL.instance_id);//获取明细对应的SERV_ID
							if(instance_id.equals(serv_id)){//如果与传入的主产品实例ID相等 则判断该明细是否有效
								String offerDetailId = offerDetail.get(OFFER_INST_DETAIL.offer_detail_id);
								//如果为删除 那么该明细无效
								if(OfferConsts.ACTION_TYPE_D.equals(offerDetail.get(OFFER_INST_DETAIL.action_type))){
									offerDetailIdMap.remove(offerDetailId);//从MAP中将该明细删除掉
									continue;
								}
								offerDetailIdMap.put(offerDetailId, offerDetailId);//使用MAP存放 方便剔除重复数据
							}
						}
					}
				}
			}
			//将MAP转换成LIST数据
			offerDetailIds = this.parseMapToList(offerDetailIdMap,"String");
			return offerDetailIds;
		}
		

		/**
		 * 前台传入的销售品动作不准确 需要此方法进行更正
		 * @param busiObjs 业务对象列表
		 * @param type 对象类型
		 */
		public void refreshActionType(List busiObjs,String type) throws Exception
		{
			
			if(busiObjs!=null&&busiObjs.size()!=0){
				for(int i = 0;i<busiObjs.size();i++){
				
					List params = new ArrayList();//查询参数列表
					if("OfferInst".equals(type)){//如果是销售品实例信息
						HashMap busiObj = (HashMap)busiObjs.get(i);
						String product_offer_instance_id = (String)busiObj.get(OFFER_INST.product_offer_instance_id);
						if(product_offer_instance_id!=null && !"".equals(product_offer_instance_id)){
							params.add(product_offer_instance_id);
							//List offer = (ArrayList)sqlMapExe.queryForMapList("findOfferDetailSql", params);
							OffInstDAO offInstDao = new OffInstDAO();
							List offer  = offInstDao.findByCond(" and product_offer_instance_id = ? ",params);//判断实例是否已经在库表中存在
							if(offer!=null && offer.size()!=0){//如果已经存在 那么设置成M
								busiObj.put(OFFER_INST.action_type, OfferConsts.ACTION_TYPE_M);
							}else{//不存在就设置成A
								busiObj.put(OFFER_INST.action_type, OfferConsts.ACTION_TYPE_A);
							}
						}
					}else if("OfferDetail".equals(type)){
						HashMap busiObj = (HashMap)busiObjs.get(i);
						String product_offer_instance_id = (String)busiObj.get(OFFER_INST_DETAIL.product_offer_instance_id);
						String offer_detail_id = (String)busiObj.get(OFFER_INST_DETAIL.offer_detail_id);
						String instance_id = (String)busiObj.get(OFFER_INST_DETAIL.instance_id);
						params.add(product_offer_instance_id);
						params.add(offer_detail_id);
						params.add(instance_id);
						OffInstDetaDAO offInstDetaDao = new OffInstDetaDAO();
						List offerDeta  = offInstDetaDao.findByCond(" and product_offer_instance_id = ? and offer_detail_id = ?" +
								" and instance_id = ? ",params);//判断明细实例是否已经在库表中存在
						if(offerDeta!=null && offerDeta.size()!=0){//如果已经存在 那么设置成M
							busiObj.put(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_M);
						}else{//不存在就设置成A
							busiObj.put(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_A);
						}
					}else if("Serv".equals(type)){
						Serv busiObj = (Serv)busiObjs.get(i);
						String serv_id = busiObj.get(SERV.serv_id);
						params.add(serv_id);
						ServDAO servDao = new ServDAO();
						List servs  = servDao.findByCond(" and serv_id = ? ",params);//判断明细实例是否已经在库表中存在
						if(servs!=null && servs.size()!=0){//如果已经存在 那么设置成M
							busiObj.set(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_M);
						}else{//不存在就设置成A
							busiObj.set(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_A);
						}
					}
					
				}
			}
		}
		
}
