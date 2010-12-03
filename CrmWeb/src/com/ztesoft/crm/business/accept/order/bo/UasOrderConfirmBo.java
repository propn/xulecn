/**
 * 
 */
package com.ztesoft.crm.business.accept.order.bo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.business.accept.reception.dao.CustReceptionDAO;
import com.ztesoft.crm.business.common.order.dao.OrdAskDAO;
import com.ztesoft.crm.business.common.order.dao.OrdCustOrderDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServDAO;
import com.ztesoft.crm.product.dao.ServOffDAO;

/**
 * @author Administrator
 *
 */
public class UasOrderConfirmBo extends DictAction{
	
	   private   int etype;
	   private   int ecode;
	   private   String edesc;
	
//查询客户下的服务订单
	 public PageModel executeQuery(DynamicDict dto) throws Exception {
		 ArrayList resultLst = new ArrayList();
		 Map m = (Map)dto.getValueByName("parameter") ;
		 String custId  = (String)m.get("custId");
		 String pageIndex =  (String)m.get("pageIndex");
		 String pageSize =  (String)m.get("pageSize");
		 PageModel askListPage = new PageModel();
		 String stateWhere = "and state = '100'";
		 ArrayList ordOrderList = new ArrayList();
		 if(custId != null && !"".equals(custId)){
				ArrayList params = new ArrayList();
				params.add(custId);
				OrdCustOrderDAO ordOrderDao = new OrdCustOrderDAO();
				//根据客户编号，查询出客户未确认的客户订单。
				ordOrderList = (ArrayList) ordOrderDao.getCustOrderByWhere(" and cust_id = ? "+stateWhere,params);
				
			}
		 if(ordOrderList==null||ordOrderList.size()==0)
			 return askListPage;
		 
		 //for()
		 HashMap custOrderMap  = (HashMap)ordOrderList.get(0);//获取客户订单
		 String cust_ord_id =  (String)custOrderMap.get("cust_ord_id");
		
		 ArrayList paramsAsk = new ArrayList();//组建查询服务订单参数
		 paramsAsk.add(cust_ord_id);
		 
		
		 OrdAskDAO askListDao = new OrdAskDAO();
			//根据客户订单编号，查出客户订单下的订单。
		ArrayList askList = new ArrayList();//修改原先的内存分页方式为数据库分页  提高性能
		  askListPage = askListDao.searchByCond(" and cust_ord_id in ? " + stateWhere +
					" order by ord_id desc ", paramsAsk, Integer.parseInt(pageIndex), Integer.parseInt(pageSize)); /*PageHelper.popupPageModel(askListDao, " and cust_ord_id in ? " + stateWhere +
					" order by instance_type desc ", Integer.parseInt(pageIndex), );*/
		 
		 /*askList = (ArrayList) askListDao.getAskListByWhere(" and cust_ord_id in ? " + stateWhere +
					" order by instance_type desc ", paramsAsk);*/
		 
		 askList = (ArrayList)askListPage.getList();
		 
		 if(askList==null||askList.size()==0)
			 return askListPage;
		 
		 Iterator askListIter = askList.iterator();
			while(askListIter.hasNext()){
				HashMap askListMap = (HashMap) askListIter.next();
				String ord_type = (String) askListMap.get("ord_type");
				String ord_id = (String) askListMap.get("ord_id");
				String instance_type = (String) askListMap.get("instance_type");
				String instance_type_id = (String) askListMap.get("instance_type_id");
				String instance_id = (String) askListMap.get("instance_id");
				String service_offer_id = (String)askListMap.get("service_offer_id");
				String ord_state = (String)askListMap.get("state");
				String acc_nbr = "";
				String offer_name = "";  //销售品\产品名称。
				
				if("10A".equals(instance_type)){
					//产品定单，查产品类别、产品号码信息。
					OrdServDAO ordServDao = new OrdServDAO();
					ArrayList ordServList = new ArrayList();
					//获取产品订单项标识最大的数据。
					ordServList = (ArrayList)ordServDao.getOrdServByServId(instance_id, ord_id);
					Iterator ordServIter = ordServList.iterator();
					if(ordServIter.hasNext()){
						HashMap ordServMap = (HashMap)ordServIter.next();
						acc_nbr = (String) ordServMap.get("acc_nbr");
						offer_name = (String)ordServMap.get("product_name");
					}
					
				}else if("10B/10C/".indexOf(instance_type) > -1){
					//销售品类定单,根据销售品规格id查出销售品名称。
					CustReceptionDAO custReceptionDao = new CustReceptionDAO();
					ArrayList offerList = new ArrayList();
					offerList = custReceptionDao.queryProductOfferByOfferId(instance_type_id);
					Iterator offerIter = offerList.iterator();
					if(offerIter.hasNext()){
						HashMap offerMap = (HashMap)offerIter.next();
						offer_name = (String)offerMap.get("offer_name");
					}
				}
				
				//查询出服务提供名称。
				ServOffDAO servOffDao = new ServOffDAO();
				HashMap keyCondMap = new HashMap();
				keyCondMap.put("service_offer_id",service_offer_id);
				HashMap serviceMap = new HashMap();
				serviceMap = servOffDao.findByPrimaryKey(keyCondMap);
				String service_offer_name = (String) serviceMap.get("service_offer_name"); 
				
				//定义定单数据的节点。
				HashMap resultMap = new HashMap();
				resultMap.put("ord_id", ord_id);
				resultMap.put("service_offer_name", service_offer_name);
				resultMap.put("offer_name", offer_name);
				resultMap.put("acc_nbr", acc_nbr);
				resultMap.put("service_offer_id", service_offer_id);
				resultMap.put("ord_type", ord_type);
				resultMap.put("cust_ord_id", cust_ord_id);
				resultMap.put("instance_type", instance_type);
				resultMap.put("instance_type_id", instance_type_id);
				resultMap.put("instance_id", instance_id);
				resultMap.put("state", ord_state);
				
				resultLst.add(resultMap);
			}
			//askListPage.
			askListPage.setList(resultLst);
		 return askListPage;
	 }
	
	 
	 
		public List translateWhereCondMap(Map keyCondMap) throws FrameException{
			if(keyCondMap == null || keyCondMap.isEmpty())
				return null ;
			
			List params = new ArrayList() ;	
			params.add((String)keyCondMap.get("parent_catalog_item_id")) ;
			params.add((String)keyCondMap.get("catalog_id")) ;
			return params  ;
		}
}
