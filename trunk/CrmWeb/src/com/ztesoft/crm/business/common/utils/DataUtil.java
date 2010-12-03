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

	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL��ѯ����
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
		 * ��ǰ̨���ص�����Ʒʵ������ת���ɺ�̨��Ҫ�ĸ�ʽ
		 * map ����Ʒ��ʵ�����ݣ�ǰ̨��
		 * loginInfo ��½��Ϣ
		 * comp_inst_id ����Ʒ��������ƷID
		 * custId �ͻ�ID
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
		 * ��ǰ̨���ص���ѡ�����Ż�ʵ������ת���ɺ�̨��Ҫ�ĸ�ʽ
		 * map ��ѡ�����Ż�ʵ�����ݣ�ǰ̨��
		 * loginInfo ��½��Ϣ
		 * custId �ͻ�ID
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
		 * ��ǰ̨���صĻ�������Ʒ����ת���ɺ�̨��Ҫ�ĸ�ʽ
		 * map ��������Ʒʵ�����ݣ�ǰ̨��
		 * loginInfo ��½��Ϣ
		 * custId �ͻ�ID
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
		 * ��ǰ̨���ص�����Ʒ��������ת���ɺ�̨��Ҫ�ĸ�ʽ
		 * map ����Ʒ����ʵ�����ݣ�ǰ̨��
		 * loginInfo ��½��Ϣ
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
		 * ��ǰ̨���صİ����Ż�����ת���ɺ�̨��Ҫ������Ʒ��ϸ���ݸ�ʽ 
		 * map ����Ʒ��ϸ������ݣ�ǰ̨��
		 * product_offer_instance_id ����Ʒʵ��ID
		 * serv_id ��Ʒʵ��ID
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
		 * ��ȡ��ѡ����Ʒ���ĸ�����Ʒʵ��
		 * @param ǰ̨����Ʒʵ��
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
		 * ��ȡ������Ʒʵ��ID
		 * List ǰ̨����Ʒʵ��
		 * String ������Ʒʵ��ID
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
		 * ��ȡ��Ʒ�����Ļ�������Ʒ���ID
		 * @param ��Ʒ���ID
		 * */
		public HashMap getRelaOffer(String product_id)
		 {
			
			 String[] sqlParams = new String[1];//����SQL���߲�ѯ����
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
		 * ��ȡ����Ʒ����ϸ�������
		 * @String ����Ʒ���ID
		 * @String ��Ʒ���id
		 * @String ��ɫid
		 * */
		public HashMap getOfferDetail(String offerId,String product_id,String comp_role_id)
		 {
			
			 String[] sqlParams = new String[2];//����SQL���߲�ѯ����
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
		 * ��ȡ��������Ʒʵ�������Ĳ�Ʒʵ��ID
		 * @String ��������Ʒʵ��ID
		 * @List ����Ʒ��ϸ���ݼ���ǰ̨��
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
		 * ��ȡ����Ʒʵ�������Ĳ�Ʒʵ��ID
		 * @String ����Ʒʵ��ID
		 * @List ����Ʒ��ϸ���ݼ���ǰ̨��
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
		 * ��ȡ�����Żݹ����Ļ�������Ʒʵ��ID
		 * @String �����Ż�����Ʒʵ��ID
		 * @List �����Ż����ݼ���ǰ̨��
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
		 * ��MAPת����LIST 
		 * @param busiMap
		 * @param type �������а����ζ���
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
		 * ��ȡ����Ʒʵ����Ч������Ʒ��ϸID
		 * @param compInsts
		 * @param serv_id
		 * @return
		 */
		public List getOfferDetailIds(List compInsts, String serv_id) throws Exception{
			List offerDetailIds = new ArrayList();
			HashMap offerDetailIdMap = new HashMap();
			List params = new ArrayList();//�����б�
			params.add(serv_id);
			params.add("10A");
			//�����ݿ����Ȼ�ȡ������Ʒ��Ч������Ʒ��ϸʵ������
			List offerInstDetails =  (ArrayList)sqlMapExe.queryForMapList("findOfferDetailIds", params);
			if(offerInstDetails!=null && offerInstDetails.size()!=0){
				for(int i =0;i<offerInstDetails.size();i++){
					HashMap offerInstDetail = (HashMap)offerInstDetails.get(i);
					String offer_detail_id =  (String)offerInstDetail.get(OFFER_INST_DETAIL.offer_detail_id);
					//�����ݷ��뵽offerDetailIdMap�� �����Ժ�����޳�
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
							String instance_id = offerDetail.get(OFFER_INST_DETAIL.instance_id);//��ȡ��ϸ��Ӧ��SERV_ID
							if(instance_id.equals(serv_id)){//����봫�������Ʒʵ��ID��� ���жϸ���ϸ�Ƿ���Ч
								String offerDetailId = offerDetail.get(OFFER_INST_DETAIL.offer_detail_id);
								//���Ϊɾ�� ��ô����ϸ��Ч
								if(OfferConsts.ACTION_TYPE_D.equals(offerDetail.get(OFFER_INST_DETAIL.action_type))){
									offerDetailIdMap.remove(offerDetailId);//��MAP�н�����ϸɾ����
									continue;
								}
								offerDetailIdMap.put(offerDetailId, offerDetailId);//ʹ��MAP��� �����޳��ظ�����
							}
						}
					}
				}
			}
			//��MAPת����LIST����
			offerDetailIds = this.parseMapToList(offerDetailIdMap,"String");
			return offerDetailIds;
		}
		

		/**
		 * ǰ̨���������Ʒ������׼ȷ ��Ҫ�˷������и���
		 * @param busiObjs ҵ������б�
		 * @param type ��������
		 */
		public void refreshActionType(List busiObjs,String type) throws Exception
		{
			
			if(busiObjs!=null&&busiObjs.size()!=0){
				for(int i = 0;i<busiObjs.size();i++){
				
					List params = new ArrayList();//��ѯ�����б�
					if("OfferInst".equals(type)){//���������Ʒʵ����Ϣ
						HashMap busiObj = (HashMap)busiObjs.get(i);
						String product_offer_instance_id = (String)busiObj.get(OFFER_INST.product_offer_instance_id);
						if(product_offer_instance_id!=null && !"".equals(product_offer_instance_id)){
							params.add(product_offer_instance_id);
							//List offer = (ArrayList)sqlMapExe.queryForMapList("findOfferDetailSql", params);
							OffInstDAO offInstDao = new OffInstDAO();
							List offer  = offInstDao.findByCond(" and product_offer_instance_id = ? ",params);//�ж�ʵ���Ƿ��Ѿ��ڿ���д���
							if(offer!=null && offer.size()!=0){//����Ѿ����� ��ô���ó�M
								busiObj.put(OFFER_INST.action_type, OfferConsts.ACTION_TYPE_M);
							}else{//�����ھ����ó�A
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
								" and instance_id = ? ",params);//�ж���ϸʵ���Ƿ��Ѿ��ڿ���д���
						if(offerDeta!=null && offerDeta.size()!=0){//����Ѿ����� ��ô���ó�M
							busiObj.put(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_M);
						}else{//�����ھ����ó�A
							busiObj.put(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_A);
						}
					}else if("Serv".equals(type)){
						Serv busiObj = (Serv)busiObjs.get(i);
						String serv_id = busiObj.get(SERV.serv_id);
						params.add(serv_id);
						ServDAO servDao = new ServDAO();
						List servs  = servDao.findByCond(" and serv_id = ? ",params);//�ж���ϸʵ���Ƿ��Ѿ��ڿ���д���
						if(servs!=null && servs.size()!=0){//����Ѿ����� ��ô���ó�M
							busiObj.set(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_M);
						}else{//�����ھ����ó�A
							busiObj.set(OFFER_INST_DETAIL.action_type, OfferConsts.ACTION_TYPE_A);
						}
					}
					
				}
			}
		}
		
}
