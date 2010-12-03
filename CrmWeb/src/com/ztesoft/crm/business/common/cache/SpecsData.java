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
	
	private static SqlMapExe  sqlMapExe=SqlMapExe.getInstance();//SQL查询工具
	/**
	 * 通过产品规格标识获取产品缓存对象数据
	 * @param productId 产品规格
	 * @return Product 产品规格数据
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
	 * 通过销售品规格标识获取销售品缓存对象数据
	 * @param offerId 销售品规格
	 * @return ProductOffer 销售品规格数据
	 * */
	public static ProductOffer getProductOffer(String offerId){
		ProductOffer productOffer=(ProductOffer) productOffers.getFromCache(PRODUCT_OFFER+offerId);
		if(productOffer==null){
			productOffer= new ProductOffer(offerId);
			productOffers.putCache(PRODUCT_OFFER+offerId, productOffer);	
			List details=productOffer.details;
			for(int i=0;i<details.size();i++ ){
				//缓存明细所对应的销售品规格
				productOfferDetails.putCache(PRODUCT_OFFER_DETAIL+details.get(i), productOffer.getOffer_id());
			}
		}
		return productOffer;
	}

	/**
	 * 通过销售品规格明细标识获取销售品明细规格数据对象
	 * @param offerDetailId 销售品明细规格
	 * @return ProductOfferDetail 销售品明细规格数据对象
	 * */
	public static ProductOfferDetail getProductOfferDetailById(String offerDetailId){
		
		Object object=productOfferDetails.getFromCache(PRODUCT_OFFER_DETAIL+offerDetailId);
		if(object==null){
			return null;
		}
		String offerId=object.toString();
		//获取productOffer对象
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
	 * 通过销售品规格明细标识获取销售品明细规格数据对象列表，针对同一产品加入多个销售品的情况
	 * @param offerDetailIds 销售品明细规格列表
	 * @return List 销售品明细规格数据对象列表
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
	 * 通过销售品规格明细标识列表去查询销售品对产品属性的约束列表
	 * @param offerDetailIds 销售品明细规格列表
	 * @return List 产品属性约束列表，封装的数据为AttrRestrict,对象是从缓存数据中克隆过来
	 * */
	public static List getServAttrRestricts(List offerDetailIds){	
		List resultAttrRestrict=new ArrayList();
		List offerDetails=getOfferDetailsByDetailIds(offerDetailIds);
		Iterator it=(Iterator)offerDetails.iterator();
		while(it.hasNext()){
			ProductOfferDetail productOfferDetail=(ProductOfferDetail)it.next();
			List attrRestrict=productOfferDetail.attrRestrict;
			//进行属性约束合并
			mergeAttrRestrict(attrRestrict,resultAttrRestrict);
			
		}
		return resultAttrRestrict;
		
	}
	
	
	/**
	 * 通过销售品规格明细标识列表去查询附属产品的约束列表
	 * @param offerDetailIds 销售品明细规格列表
	 * @return List 附属产品约束列表，封装的数据为ServProductRestrict,对象是从缓存数据中克隆过来
	 * */
	public static List getServProductRestricts(List offerDetailIds){
		
		List resultServProductRestrict=new ArrayList();
		List offerDetails=getOfferDetailsByDetailIds(offerDetailIds);
		Iterator it=(Iterator)offerDetails.iterator();
		while(it.hasNext()){
			ProductOfferDetail productOfferDetail=(ProductOfferDetail)it.next();
			List servProductRestrict=productOfferDetail.servProductRestrict;
			//合并servProductRestrict和resultServProductRestrict对象
			//进行附属产品约束合并
			mergeServProductRestrict(servProductRestrict,resultServProductRestrict);
			
		}
		return resultServProductRestrict;
		
	}
	/**
	 * 合并附属产品约束数据
	 * @param servProductRestrict 附属产品约束数据列表
	 * @param resultServProductRestrict 附属产品约束数据结果集列表
	 * */
	private static void mergeServProductRestrict(List servProductRestrict,List resultServProductRestrict){
		if(servProductRestrict==null)
			return ;
		Iterator servProductRestrictIt=servProductRestrict.iterator();
		while(servProductRestrictIt.hasNext()){
			ServProductRestrict servProductRestrictObject=(ServProductRestrict)servProductRestrictIt.next();
			//在结果集中进行遍历查找，是否含有相同的附属产品约束，包括约束类型
			boolean noRecord=true;
			for(int i=0;i<resultServProductRestrict.size();i++){
				ServProductRestrict _servProductRestrictObject=(ServProductRestrict)resultServProductRestrict.get(i);
				//附属产品约束相同的情况下，进行约束类型和属性取值的合并
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
	 * 合并约束属性数据
	 * @param attrRestrict 属性约束数据列表
	 * @param resultAttrRestrict 结果集属性约束数据列表
	 * */
	private static void mergeAttrRestrict(List attrRestrict,List resultAttrRestrict){
		if(attrRestrict==null)
			return ;
		Iterator attrRestrictIt=attrRestrict.iterator();
		while(attrRestrictIt.hasNext()){
			AttrRestrict attrRestrictObject=(AttrRestrict)attrRestrictIt.next();
			//在结果集中进行遍历查找，是否含有相同的属性约束
			boolean noRecord=true;
			for(int i=0;i<resultAttrRestrict.size();i++){
				AttrRestrict _attrRestrictObject=(AttrRestrict)resultAttrRestrict.get(i);
				//属性约束相同的情况下，进行最小值，最大值，缺省值，取值列表的合并
				if(attrRestrictObject.getAttr_id().equals(_attrRestrictObject.getAttr_id())){	
					//比较如果是下拉型的98A,如果是连续型的
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
	 * 合并约束属性数据
	 * @param attrRestrictObject 属性约束数据对象
	 * @param resultAttrRestrictObject 结果集属性约束数据对象
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
			//如果结果对象缺省值不在取值列表中，新对象缺省值是在取值列表中则设置缺省值为最新的值
			if(Strings.isEmptiy(resultAttrRestrictObject.getDefault_value())||result.containsKey(resultAttrRestrictObject.getDefault_value())){
				if(Strings.isEmptiy(attrRestrictObject.getDefault_value())||!result.containsKey(attrRestrictObject.getDefault_value())){
					//设置缺省第一条记录
					resultAttrRestrictObject.setDefault_value(firstValue);
				}else if(result.containsKey(attrRestrictObject.getDefault_value())){
					resultAttrRestrictObject.setDefault_value(attrRestrictObject.getDefault_value());
				}
			}	
		}else if(attrRestrictObject.getMin_value()!=null&&!"".equals(attrRestrictObject.getMin_value())){
			//如果当前的最小值比结果集中的最小值还大，则取当前的最小值为结果集中的最小值
			if(Strings.big(attrRestrictObject.getMin_value(), resultAttrRestrictObject.getMin_value())){
				resultAttrRestrictObject.setMin_value(attrRestrictObject.getMin_value());
			}
			//如果结果集中最大值比当前对象的最大值还大，则取当前的最大值为结果集中的最大值
			if(Strings.big(resultAttrRestrictObject.getMax_value(), attrRestrictObject.getMax_value())){
				resultAttrRestrictObject.setMax_value(attrRestrictObject.getMax_value());
			}
			String defaultValue=resultAttrRestrictObject.getDefault_value();
			String curdefaultValue=attrRestrictObject.getDefault_value();
			if(Strings.isEmptiy(defaultValue)||Strings.little(defaultValue,resultAttrRestrictObject.getMin_value())||Strings.big(defaultValue,resultAttrRestrictObject.getMax_value())){
				if(Strings.isEmptiy(curdefaultValue)||Strings.little(curdefaultValue,resultAttrRestrictObject.getMin_value())||Strings.big(curdefaultValue,resultAttrRestrictObject.getMax_value())){
					//设置缺省第一条记录
					resultAttrRestrictObject.setDefault_value(resultAttrRestrictObject.getMin_value());
				}else {
					resultAttrRestrictObject.setDefault_value(attrRestrictObject.getDefault_value());
				}
			}	
		}
	}
	

	/**获取互斥附属产品列表 获取全部
	 * @param servProds
	 * @param type 空表示获取全部 A表示 获取与A端互斥 Z表示与Z端互斥 
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
			List params = new ArrayList();//构建参数列表
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
	
	
	/**获取互斥销售品 
	 * @param offerInsts 销售品列表
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
			List params = new ArrayList();//构建参数列表
			params.add(product_offer_ids);
			params.add("10A");
			mutexOffers =  (ArrayList)sqlMapExe.queryForMapList("findMutexOffers_a", params);
		}	
		return mutexOffers;
	}
	
	
	/**
	 * 从servProdList中删除removeList中包含的附属产品
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
	 * 从compInsts中把相应的product_offer_id删除掉
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
						if(offerInst.get(OFFER_INST.product_offer_id).equals(product_offer_id)){//如果相等 就把删除掉
							String offer_inst_id = offerInst.get(OFFER_INST.product_offer_instance_id);
							if(OfferConsts.ACTION_TYPE_A.equals(offerInst.get(OFFER_INST.action_type))){
								offerInsts.remove(j);//如果是新装 直接删除掉
							}else{//在用的 就将其动作置成D
								offerInst.set(OFFER_INST.action_type, OfferConsts.ACTION_TYPE_D);
							}
							compInst.removeOfferInstDetail(offer_inst_id);//删除对应明细
							//如果该销售品为主销售品 那么需要将该主销售品下的所有销售品删除掉
							if(offerInst.get(OFFER_INST.product_offer_instance_id).equals(offerInst.get(OFFER_INST.comp_inst_id))){
								compInst.delAll();
							}
						}
					}
				}
			}
		}
	}
	
	
	
	//缓存产品的规格数据
	private  static CacheStorage products;
	//缓存销售品的规格数据
	private  static CacheStorage productOffers;
	//缓存销售品的明细规格数据
	private  static CacheStorage productOfferDetails;//记录的是销售品明细与销售品之间的关系
	
	static{
		products=new CacheStorage();
		productOffers=new CacheStorage();
		productOfferDetails=new CacheStorage(10000);
	}
	
	
}
