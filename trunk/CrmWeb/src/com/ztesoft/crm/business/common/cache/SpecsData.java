package com.ztesoft.crm.business.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ztesoft.crm.business.common.consts.OfferConsts;
import com.ztesoft.crm.business.common.consts.BusiTables.OFFER_INST;
import com.ztesoft.crm.business.common.logic.auto.ProcessParameter;
import com.ztesoft.crm.business.common.logic.model.CompInst;
import com.ztesoft.crm.business.common.logic.model.OfferInst;
import com.ztesoft.crm.business.common.logic.model.Serv;
import com.ztesoft.crm.business.common.logic.model.ServProduct;
import com.ztesoft.crm.business.common.query.SqlMapExe;
import com.ztesoft.crm.business.common.utils.Strings;



public class SpecsData {

	private final static String PRODUCT="product";
	
	private final static String PRODUCT_OFFER="product_offer";
	
	private final static String PRODUCT_OFFER_DETAIL="product_offer_detail";	
	
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL��ѯ����
	/**
	 * ͨ����Ʒ����ʶ��ȡ��Ʒ�����������
	 * @param productId ��Ʒ���
	 * @return Product ��Ʒ�������
	 * */
	public static Product  getProduct(String productId){
		
		Product product=(Product) products.getFromCache(PRODUCT+productId);
		if(product==null){
			product= new Product(productId);
			products.putCache(PRODUCT+productId, product);
		}
		return product;
	}
	/**
	 * ͨ������Ʒ����ʶ��ȡ����Ʒ�����������
	 * @param offerId ����Ʒ���
	 * @return ProductOffer ����Ʒ�������
	 * */
	public static ProductOffer getProductOffer(String offerId){
		ProductOffer productOffer=(ProductOffer) productOffers.getFromCache(PRODUCT_OFFER+offerId);
		if(productOffer==null){
			productOffer= new ProductOffer(offerId);
			productOffers.putCache(PRODUCT_OFFER+offerId, productOffer);	
			List details=productOffer.details;
			for(int i=0;i<details.size();i++ ){
				//������ϸ����Ӧ������Ʒ���
				productOfferDetails.putCache(PRODUCT_OFFER_DETAIL+details.get(i), productOffer.getOffer_id());
			}
		}
		return productOffer;
	}

	/**
	 * ͨ������Ʒ�����ϸ��ʶ��ȡ����Ʒ��ϸ������ݶ���
	 * @param offerDetailId ����Ʒ��ϸ���
	 * @return ProductOfferDetail ����Ʒ��ϸ������ݶ���
	 * */
	public static ProductOfferDetail getProductOfferDetailById(String offerDetailId){
		
		Object object=productOfferDetails.getFromCache(PRODUCT_OFFER_DETAIL+offerDetailId);
		if(object==null){
			return null;
		}
		String offerId=object.toString();
		//��ȡproductOffer����
		ProductOffer productOffer=getProductOffer(offerId);	
		for(int i=0;i<productOffer.details.size();i++){
			
			ProductOfferDetail offerDetail=(ProductOfferDetail)productOffer.details.get(i);
			if(offerDetailId.equals(offerDetail.offer_detail_id)){
				
				return offerDetail;
			}	
		}
		return null;
		
	}
	/**
	 * ͨ������Ʒ�����ϸ��ʶ��ȡ����Ʒ��ϸ������ݶ����б����ͬһ��Ʒ����������Ʒ�����
	 * @param offerDetailIds ����Ʒ��ϸ����б�
	 * @return List ����Ʒ��ϸ������ݶ����б�
	 * */
	public static List getOfferDetailsByDetailIds(List offerDetailIds){
		
		List result=new ArrayList();
		Iterator it=offerDetailIds.iterator();
		while(it.hasNext()){		
			String offerDetailId=(String)it.next();
			Object object=getProductOfferDetailById(offerDetailId);
			if(object!=null)
				result.add(object);
		}
		return result;
		
	}
	/**
	 * ͨ������Ʒ�����ϸ��ʶ�б�ȥ��ѯ����Ʒ�Բ�Ʒ���Ե�Լ���б�
	 * @param offerDetailIds ����Ʒ��ϸ����б�
	 * @return List ��Ʒ����Լ���б���װ������ΪAttrRestrict,�����Ǵӻ��������п�¡����
	 * */
	public static List getServAttrRestricts(List offerDetailIds){	
		List resultAttrRestrict=new ArrayList();
		List offerDetails=getOfferDetailsByDetailIds(offerDetailIds);
		Iterator it=(Iterator)offerDetails.iterator();
		while(it.hasNext()){
			ProductOfferDetail productOfferDetail=(ProductOfferDetail)it.next();
			List attrRestrict=productOfferDetail.attrRestrict;
			//��������Լ���ϲ�
			mergeAttrRestrict(attrRestrict,resultAttrRestrict);
			
		}
		return resultAttrRestrict;
		
	}
	
	
	/**
	 * ͨ������Ʒ�����ϸ��ʶ�б�ȥ��ѯ������Ʒ��Լ���б�
	 * @param offerDetailIds ����Ʒ��ϸ����б�
	 * @return List ������ƷԼ���б���װ������ΪServProductRestrict,�����Ǵӻ��������п�¡����
	 * */
	public static List getServProductRestricts(List offerDetailIds){
		
		List resultServProductRestrict=new ArrayList();
		List offerDetails=getOfferDetailsByDetailIds(offerDetailIds);
		Iterator it=(Iterator)offerDetails.iterator();
		while(it.hasNext()){
			ProductOfferDetail productOfferDetail=(ProductOfferDetail)it.next();
			List servProductRestrict=productOfferDetail.servProductRestrict;
			//�ϲ�servProductRestrict��resultServProductRestrict����
			//���и�����ƷԼ���ϲ�
			mergeServProductRestrict(servProductRestrict,resultServProductRestrict);
			
		}
		return resultServProductRestrict;
		
	}
	/**
	 * �ϲ�������ƷԼ������
	 * @param servProductRestrict ������ƷԼ�������б�
	 * @param resultServProductRestrict ������ƷԼ�����ݽ�����б�
	 * */
	private static void mergeServProductRestrict(List servProductRestrict,List resultServProductRestrict){
		if(servProductRestrict==null)
			return ;
		Iterator servProductRestrictIt=servProductRestrict.iterator();
		while(servProductRestrictIt.hasNext()){
			ServProductRestrict servProductRestrictObject=(ServProductRestrict)servProductRestrictIt.next();
			//�ڽ�����н��б������ң��Ƿ�����ͬ�ĸ�����ƷԼ��������Լ������
			boolean noRecord=true;
			for(int i=0;i<resultServProductRestrict.size();i++){
				ServProductRestrict _servProductRestrictObject=(ServProductRestrict)resultServProductRestrict.get(i);
				//������ƷԼ����ͬ������£�����Լ�����ͺ�����ȡֵ�ĺϲ�
				if(servProductRestrictObject.product_id.equals(_servProductRestrictObject.product_id)){
					noRecord=false;
					if(servProductRestrictObject.restrict_type>_servProductRestrictObject.restrict_type)
						_servProductRestrictObject.setRestrict_type(servProductRestrictObject.restrict_type);
					
					mergeAttrRestrict(servProductRestrictObject.attrRestrict,_servProductRestrictObject.attrRestrict);
					break;
				}	
			}
			if(noRecord){
				resultServProductRestrict.add(servProductRestrictObject.clone());
			}
		}
	}
	/**
	 * �ϲ�Լ����������
	 * @param attrRestrict ����Լ�������б�
	 * @param resultAttrRestrict ���������Լ�������б�
	 * */
	private static void mergeAttrRestrict(List attrRestrict,List resultAttrRestrict){
		if(attrRestrict==null)
			return ;
		Iterator attrRestrictIt=attrRestrict.iterator();
		while(attrRestrictIt.hasNext()){
			AttrRestrict attrRestrictObject=(AttrRestrict)attrRestrictIt.next();
			//�ڽ�����н��б������ң��Ƿ�����ͬ������Լ��
			boolean noRecord=true;
			for(int i=0;i<resultAttrRestrict.size();i++){
				AttrRestrict _attrRestrictObject=(AttrRestrict)resultAttrRestrict.get(i);
				//����Լ����ͬ������£�������Сֵ�����ֵ��ȱʡֵ��ȡֵ�б�ĺϲ�
				if(attrRestrictObject.getAttr_id().equals(_attrRestrictObject.getAttr_id())){	
					//�Ƚ�����������͵�98A,����������͵�
					noRecord=false;
					mergeAttrRestrict(attrRestrictObject,_attrRestrictObject);
					break;
				}	
			}
			if(noRecord){
				resultAttrRestrict.add(attrRestrictObject.clone());
			}
		}	
	}
	/**
	 * �ϲ�Լ����������
	 * @param attrRestrictObject ����Լ�����ݶ���
	 * @param resultAttrRestrictObject ���������Լ�����ݶ���
	 * */
	private static void mergeAttrRestrict(AttrRestrict attrRestrictObject,AttrRestrict resultAttrRestrictObject){
		
		if(!attrRestrictObject.getValue_name_map().isEmpty()){
			Map result=new HashMap();
			Map valueMap=attrRestrictObject.getValue_name_map();
			Iterator entryIt=valueMap.entrySet().iterator();
			String firstValue="";
			while(entryIt.hasNext()){
				Entry entry=(Entry)entryIt.next();
				if(resultAttrRestrictObject.getValue_name_map().containsKey(entry.getKey())){
					if("".equals(firstValue))
					firstValue=(String)entry.getKey();
					result.put(entry.getKey(), entry.getValue());
				}
			}
			resultAttrRestrictObject.setValue_name_map(result);
			//����������ȱʡֵ����ȡֵ�б��У��¶���ȱʡֵ����ȡֵ�б���������ȱʡֵΪ���µ�ֵ
			if(Strings.isEmptiy(resultAttrRestrictObject.getDefault_value())||result.containsKey(resultAttrRestrictObject.getDefault_value())){
				if(Strings.isEmptiy(attrRestrictObject.getDefault_value())||!result.containsKey(attrRestrictObject.getDefault_value())){
					//����ȱʡ��һ����¼
					resultAttrRestrictObject.setDefault_value(firstValue);
				}else if(result.containsKey(attrRestrictObject.getDefault_value())){
					resultAttrRestrictObject.setDefault_value(attrRestrictObject.getDefault_value());
				}
			}	
		}else if(attrRestrictObject.getMin_value()!=null&&!"".equals(attrRestrictObject.getMin_value())){
			//�����ǰ����Сֵ�Ƚ�����е���Сֵ������ȡ��ǰ����СֵΪ������е���Сֵ
			if(Strings.big(attrRestrictObject.getMin_value(), resultAttrRestrictObject.getMin_value())){
				resultAttrRestrictObject.setMin_value(attrRestrictObject.getMin_value());
			}
			//�������������ֵ�ȵ�ǰ��������ֵ������ȡ��ǰ�����ֵΪ������е����ֵ
			if(Strings.big(resultAttrRestrictObject.getMax_value(), attrRestrictObject.getMax_value())){
				resultAttrRestrictObject.setMax_value(attrRestrictObject.getMax_value());
			}
			String defaultValue=resultAttrRestrictObject.getDefault_value();
			String curdefaultValue=attrRestrictObject.getDefault_value();
			if(Strings.isEmptiy(defaultValue)||Strings.little(defaultValue,resultAttrRestrictObject.getMin_value())||Strings.big(defaultValue,resultAttrRestrictObject.getMax_value())){
				if(Strings.isEmptiy(curdefaultValue)||Strings.little(curdefaultValue,resultAttrRestrictObject.getMin_value())||Strings.big(curdefaultValue,resultAttrRestrictObject.getMax_value())){
					//����ȱʡ��һ����¼
					resultAttrRestrictObject.setDefault_value(resultAttrRestrictObject.getMin_value());
				}else {
					resultAttrRestrictObject.setDefault_value(attrRestrictObject.getDefault_value());
				}
			}	
		}
	}
	

	/**��ȡ���⸽����Ʒ�б� ��ȡȫ��
	 * @param servProds
	 * @param type �ձ�ʾ��ȡȫ�� A��ʾ ��ȡ��A�˻��� Z��ʾ��Z�˻��� 
	 * @return
	 */
	public static List getMutexServProds(List servProds,String type){
		List mutexServProd = new ArrayList();
		String serv_product_ids = "";
		if(servProds!=null&&servProds.size()!=0){
			for(int i =0;i<servProds.size();i++){
				ServProduct servProd =  (ServProduct)servProds.get(i);
				if(i==servProds.size()-1){
					serv_product_ids += servProd.get(Serv.PRODUCT_ID);
					break;
				}
				serv_product_ids += servProd.get(Serv.PRODUCT_ID)+",";
			}
			List params = new ArrayList();//���������б�
			if("A".equals(type)){
				params.add(serv_product_ids);
				params.add("1");
				mutexServProd =  (ArrayList)sqlMapExe.queryForMapList("findMutexServProds_a", params);
			}else if("Z".equals(type)){
				params.add(serv_product_ids);
				params.add("1");
				mutexServProd =  (ArrayList)sqlMapExe.queryForMapList("findMutexServProds_z", params);
			}else{
				params.add(serv_product_ids);
				params.add("1");
				params.add(serv_product_ids);
				params.add("1");
				mutexServProd =  (ArrayList)sqlMapExe.queryForMapList("findMutexServProds", params);
			}
			
			
		}	
		return mutexServProd;
	}
	
	
	/**��ȡ��������Ʒ 
	 * @param offerInsts ����Ʒ�б�
	 * @return
	 */
	public static List getMutexOffers(List offerInsts){
		List mutexOffers = new ArrayList();
		String product_offer_ids = "";
		if(offerInsts!=null&&offerInsts.size()!=0){
			for(int i =0;i<offerInsts.size();i++){
				OfferInst offerInst =  (OfferInst)offerInsts.get(i);
				if(i==offerInsts.size()-1){
					product_offer_ids += "'"+offerInst.get(OFFER_INST.product_offer_id)+"'";
					break;
				}
				product_offer_ids += "'"+offerInst.get(OFFER_INST.product_offer_id)+"'"+",";
			}
			List params = new ArrayList();//���������б�
			params.add(product_offer_ids);
			params.add("10A");
			mutexOffers =  (ArrayList)sqlMapExe.queryForMapList("findMutexOffers_a", params);
		}	
		return mutexOffers;
	}
	
	
	/**
	 * ��servProdList��ɾ��removeList�а����ĸ�����Ʒ
	 * @param removeList
	 * @param servProdList
	 * @param parameter
	 * @throws Exception
	 */
	public static void removeMutexServProd(List removeList,List servProdList,ProcessParameter parameter)throws Exception  {
		if(removeList!=null && removeList.size()!=0){
			for(int i =0;i<removeList.size();i++){
				ServProduct removeProd = (ServProduct)removeList.get(i);
				if(servProdList!=null && servProdList.size()!=0){
					for(int j = 0;j<servProdList.size();j++){
						ServProduct servProd = (ServProduct)servProdList.get(j);
						if(servProd.get(Serv.PRODUCT_ID).equals(removeProd.get(Serv.PRODUCT_ID))){
							parameter.serv.delServProductByID(servProd.get(Serv.PRODUCT_ID));
						}
					}
				}
			}
		}
	}
	
	


	/**
	 * ��compInsts�а���Ӧ��product_offer_idɾ����
	 * @param product_offer_id
	 * @param compInsts
	 * @throws Exception
	 */
	public static void removeMutexOffers(String product_offer_id,List compInsts)throws Exception  {
		if(compInsts!=null && compInsts.size()!=0){
			for(int i =0;i<compInsts.size();i++){
				CompInst compInst = (CompInst)compInsts.get(i);
				List offerInsts = compInst.getOfferInsts();
				if(offerInsts!=null && offerInsts.size()!=0){
					for(int j = 0;j<offerInsts.size();j++){
						OfferInst offerInst = (OfferInst)offerInsts.get(j);
						if(offerInst.get(OFFER_INST.product_offer_id).equals(product_offer_id)){//������ �Ͱ�ɾ����
							String offer_inst_id = offerInst.get(OFFER_INST.product_offer_instance_id);
							if(OfferConsts.ACTION_TYPE_A.equals(offerInst.get(OFFER_INST.action_type))){
								offerInsts.remove(j);//�������װ ֱ��ɾ����
							}else{//���õ� �ͽ��䶯���ó�D
								offerInst.set(OFFER_INST.action_type, OfferConsts.ACTION_TYPE_D);
							}
							compInst.removeOfferInstDetail(offer_inst_id);//ɾ����Ӧ��ϸ
							//���������ƷΪ������Ʒ ��ô��Ҫ����������Ʒ�µ���������Ʒɾ����
							if(offerInst.get(OFFER_INST.product_offer_instance_id).equals(offerInst.get(OFFER_INST.comp_inst_id))){
								compInst.delAll();
							}
						}
					}
				}
			}
		}
	}
	
	
	
	//�����Ʒ�Ĺ������
	private  static CacheStorage products;
	//��������Ʒ�Ĺ������
	private  static CacheStorage productOffers;
	//��������Ʒ����ϸ�������
	private  static CacheStorage productOfferDetails;//��¼��������Ʒ��ϸ������Ʒ֮��Ĺ�ϵ
	
	static{
		products=new CacheStorage();
		productOffers=new CacheStorage();
		productOfferDetails=new CacheStorage(10000);
	}
	
	
}
