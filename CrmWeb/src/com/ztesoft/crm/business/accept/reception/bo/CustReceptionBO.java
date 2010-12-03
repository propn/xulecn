package com.ztesoft.crm.business.accept.reception.bo;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.util.conversion.Strim;
import com.ztesoft.crm.business.accept.reception.dao.CustReceptionDAO;
import com.ztesoft.crm.business.common.consts.ReceptionActions;
import com.ztesoft.crm.business.common.consts.CustomerActions;
import com.ztesoft.crm.business.common.inst.dao.ServDAO;
import com.ztesoft.crm.business.common.inst.dao.ServProductDAO;
import com.ztesoft.crm.business.common.order.dao.OrdAskDAO;
import com.ztesoft.crm.business.common.order.dao.OrdCustOrderDAO;
import com.ztesoft.crm.business.common.order.dao.OrdServDAO;
import com.ztesoft.crm.business.common.order.vo.OrdCustOrderVO;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.crm.product.dao.ServOffDAO;

import java.util.*;

/**
 * 客户接待的业务逻辑处理类
 * @author lindy
 *
 */
public class CustReceptionBO extends DictAction {

	public CustReceptionBO(){
		
	}
	
	/**
	 * 获取推荐给客户的销售品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCustRecommend(DynamicDict dto)throws Exception{
		HashMap custIdMap = (HashMap) Const.getParam(dto);
		if (custIdMap == null){
			return null;
		}
		String custId = (String) custIdMap.get("custId");
		
		ArrayList recommendList = new ArrayList();
		for (int i=0;i<10;i++){
			HashMap recommendMap = new HashMap();
			recommendMap.put("offerName", "销售品测试"+i);
			recommendMap.put("offerId", "100"+i);
			recommendMap.put("offerDesc", "销售品介绍"+i);
			recommendList.add(recommendMap);
		}
		
		return recommendList;
	}
	
	/**
	 * 展示推荐商品的描述。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String showOfferRecommend(DynamicDict dto) throws Exception{
		HashMap offerIdMap = (HashMap) Const.getParam(dto);
		if(offerIdMap == null){
			return "";
		}
		String offerId = (String) offerIdMap.get("offerId");
		String commonts = offerId;
		
		return commonts;
	}
	
	/**
	 * 查询客户的销售品实例信息。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstance(DynamicDict dto)throws Exception{
		//获取传入的参数MAP
		HashMap paramMap = (HashMap) Const.getParam(dto);
		
		Integer pageCount = (Integer)paramMap.get("pageCount");
		Integer currentPages = (Integer) paramMap.get("currentPages");
		String custId= (String) paramMap.get("custId");
		String accNbr = (String) paramMap.get("accNbr");
		
		//查询的销售品
		ArrayList offerList = new ArrayList();
		
		//判断传入的精确定位的号码是否不为空，如果不为空，则要单独找该号码。
		if(accNbr != null && !"".equals(accNbr)){
			//对号码进行单独查询。
			offerList = showOffInstanceForAccNbr(paramMap);
			
		}else{
			String queryNbr = (String) paramMap.get("queryNbr");
			if(queryNbr != null && !"".equals(queryNbr)){
				//根据查询号码查询客户，在产品实例中优先展示当前号码。
				//定义查询客户销售品实例的入参
				ArrayList paramsList = new ArrayList();
				paramsList.add(queryNbr);
				paramsList.add(custId);
				paramsList.add(queryNbr);
				paramsList.add(custId);
				paramsList.add(queryNbr);
				paramsList.add(custId);
				paramsList.add(custId);
				paramsList.add(custId);
				paramsList.add(custId);
				paramsList.add(queryNbr);
				paramsList.add(custId);
				//paramsList.add((currentPages*pageCount)+"");     
				//paramsList.add(((currentPages-1)*pageCount)+""); 
				
				CustReceptionDAO custReceptionDao = new CustReceptionDAO();
				//查询客户的销售品实例信息。
				offerList = custReceptionDao.showOffInstanceByAccNbr(paramsList);
				
			}else{
				//定义查询客户销售品实例的入参
				ArrayList paramsList = new ArrayList();
				paramsList.add(custId);
				paramsList.add(custId);
				paramsList.add(custId);
				//paramsList.add((currentPages*pageCount));     
				//paramsList.add(((currentPages-1)*pageCount)); 
				
				CustReceptionDAO custReceptionDao = new CustReceptionDAO();
				//查询客户的销售品实例信息。
				offerList = custReceptionDao.showOffInstance( paramsList);
				
				
			}
		}
		
		//定义返回的树结构的数组。
		ArrayList instanceList = new ArrayList();
		instanceList = setOfferInstanceTree(offerList);
		
		HashMap resultMap = new HashMap();
		resultMap.put("instanceData",instanceList);
		//resultMap.put("currentPages", currentPages);
		//resultMap.put("ageCount", pageCount);
		
		return resultMap;
	}
	
	/**
	 * 查询出销售品的服务提供
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffService(DynamicDict dto) throws Exception{
		//获取传入的参数MAP
		HashMap param = (HashMap) Const.getParam(dto);
		//调用入口
		String source = (String) param.get("source");
		//获取参数
		HashMap paramIn = (HashMap) param.get("param");
		
		//定义查询语句的参数。
		ArrayList paramsList = new ArrayList();
		
		ArrayList serviceList = new ArrayList();
		if("0".equals(source)){
			//销售品实例信息进入。
			String offerId = (String) paramIn.get("offerId");
			if(offerId !=null && !"".equals(offerId)){
				paramsList.add(offerId);
			}
			//公共的业务
			HashMap commonService = new HashMap();
			commonService.put("id", "1");
			commonService.put("name", "新购");
			serviceList.add(commonService);
			
			CustReceptionDAO custReceptionDao = new CustReceptionDAO();
			
			//判断销售品实例信息选中的节点的类型。
			String instanceType = (String) paramIn.get("instanceType");
			//如果选中的节点是套餐销售品，则只查出套餐销售品的业务。
			if("10C".equals(instanceType)){
				if(paramsList.size() > 0){
					//查询销售品服务提供数据。
					serviceList.addAll(custReceptionDao.showOffService( paramsList));
				}
			}else{
				//选中的节点是基础销售品或者产品，则需要查出基础销售品的业务和产品的业务。
				//取出产品规格
				String productId = (String) paramIn.get("productId");
				paramsList.add(productId);
				if(paramsList.size() > 1){
					//查询销售品服务提供数据。
					serviceList.addAll(custReceptionDao.showOffAndProdService( paramsList));
				}
				
			}
		}else if("1/2/".indexOf(source) > -1){
			//获取正在处理工单、在途工单可受理的业务。
			serviceList= getServiceFromOrd(paramIn, source);
			
		}
		
		ArrayList returnList = new ArrayList();
		ArrayList subList = null;
		//查询出可以提供的服务大于0，才进行拼装数据返回。
		for(int i=0;serviceList!=null && i < serviceList.size();i++){
			HashMap serviceMap = (HashMap) serviceList.get(i);
			
			if(i%8 ==0){
				subList = new ArrayList();
				returnList.add(subList);
			}
			subList.add(serviceMap);
		}
		
		HashMap resultMap = new HashMap();
		resultMap.put("serviceList", returnList);
		return resultMap;
	}
	
	/**
	 * 获取正在处理工单、在途工单可受理的业务。
	 * @param params
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public ArrayList getServiceFromOrd(HashMap params,String source)throws Exception{
		//暂时写死。以后再改成配置。
		ArrayList servList = new ArrayList();
		
		String instanceId = (String) params.get("instanceId");
		
		if("1".equals(source)){
			//正在处理工单
			if(instanceId ==null || "".equals(instanceId)){
				//为空，选中的是客户订单的节点。
				//取消业务。
				HashMap deleteMap = new HashMap();
				deleteMap.put("id", "-3");
				deleteMap.put("name", "取消工单");
				servList.add(deleteMap);
			}else{
				//修正业务。
				HashMap changeMap = new HashMap();
				changeMap.put("id", "-1");
				changeMap.put("name", "修正");
				servList.add(changeMap);
				//取消业务。
				HashMap deleteMap = new HashMap();
				deleteMap.put("id", "-3");
				deleteMap.put("name", "取消工单");
				servList.add(deleteMap);
			}
		}else if("2".equals(source)){
			//在途工单
			if(instanceId ==null || "".equals(instanceId)){
				//为空，选中的是客户订单的节点。
				//撤单业务。
				HashMap cancelMap = new HashMap();
				cancelMap.put("id", "-2");
				cancelMap.put("name", "撤单");
				servList.add(cancelMap);
			}else{
				//修正业务。
				HashMap changeMap = new HashMap();
				changeMap.put("id", "-1");
				changeMap.put("name", "修正");
				servList.add(changeMap);
				//撤单业务。
				HashMap cancelMap = new HashMap();
				cancelMap.put("id", "-2");
				cancelMap.put("name", "撤单");
				servList.add(cancelMap);
			}
		}
		
		return servList;
	}
	
	/**
	 * 查询客户的过程单信息。 
	 * source：1，来自正在受理。2，来自在途订单。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showCustOrdInfo(DynamicDict dto) throws Exception{
		//获取传入的参数
		HashMap paramMap = (HashMap) Const.getParam(dto);
		//调用入口
		String source = (String) paramMap.get("source");
		//获取参数
		String custId = (String) paramMap.get("custId");
		//当前查询页数
		int currentPages =  ((Integer)paramMap.get("currentPages")).intValue();
		//每页显示数量
		int pageCount =  ((Integer)paramMap.get("pageCount")).intValue();
		
		HashMap resultMap = queryCustAccept(custId,source,currentPages,pageCount);
		
		return resultMap;
	}
	
	/**
	 * 查询正在处理工单的信息。
	 * @param custId
	 * @param stateWhere  定单状态查询的条件。
	 * @return
	 * @throws Exception
	 */
	public HashMap queryCustAccept(String custId,String source,int currentPages,int pageCount) throws Exception{
		//定义返回的树结构的数组。 
		ArrayList orderTreeList = new ArrayList();
		
		ArrayList paramsAsk = new ArrayList();
		paramsAsk.add(custId);
		paramsAsk.add((currentPages*pageCount)+"");     //查询结束位置
		paramsAsk.add(((currentPages-1)*pageCount)+""); //查询开始位置
		ArrayList askList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//根据客户订单编号，查出客户订单下的主要订单。
		String stateWhere = "";
		if("1".equals(source)){
			//正在处理工单
			askList = (ArrayList) custReceptionDao.queryAcceptAskByCustId(paramsAsk);
			stateWhere = " and state = '100' ";
		}else if("2".equals(source)){
			//在途工单
			askList = (ArrayList) custReceptionDao.queryNotFinishedAskByCustId(paramsAsk);
			stateWhere = " and state <> '100' ";
		}
		Iterator askListIter = askList.iterator();
		while(askListIter.hasNext()){
			//定义定单数据的节点。
			HashMap childrenMap = new HashMap();
			//获取节点信息。
			ordNotesInfo(askListIter,childrenMap);
			
			//定义子节点中的子节点。
			ArrayList subChildrenList = new ArrayList();
			childrenMap.put("children", subChildrenList);
			
			ArrayList subParamsAsk = new ArrayList();
			subParamsAsk.add(childrenMap.get("ask_id"));
			
			ArrayList subAskList = new ArrayList();
			OrdAskDAO askListDao= new OrdAskDAO();
			//根据客户订单编号，查出客户订单下的主要订单。
			subAskList = (ArrayList) askListDao.getAskListByWhere(" and ord_id <> ask_id and ask_id = ? " + stateWhere +
							" order by instance_type desc ", subParamsAsk);
			Iterator subAskListIter = subAskList.iterator();
			while(subAskListIter.hasNext()){
				//定义定单数据的节点。
				HashMap subChildrenMap = new HashMap();
				//获取节点信息。
				ordNotesInfo(subAskListIter,subChildrenMap);
				
				subChildrenList.add(subChildrenMap);
			}				
			
			orderTreeList.add(childrenMap);
			
		}
	
		HashMap resultMap = new HashMap();
		resultMap.put("orderData", orderTreeList);
		
		return resultMap;
	}
	
	/**
	 * 组装查询出来的节点信息。
	 * @param askListIter
	 * @param childrenMap
	 * @param cust_ord_id
	 * @throws Exception
	 */
	public void ordNotesInfo(Iterator askListIter,HashMap childrenMap)throws Exception{
		HashMap askListMap = (HashMap) askListIter.next();
		String ord_type = (String) askListMap.get("ord_type");
		String ord_id = (String) askListMap.get("ord_id");
		String instance_type = (String) askListMap.get("instance_type");
		String instance_type_id = (String) askListMap.get("instance_type_id");
		String instance_id = (String) askListMap.get("instance_id");
		String service_offer_id = (String)askListMap.get("service_offer_id");
		String ord_state = (String)askListMap.get("state");
		String ask_id = (String)askListMap.get("ask_id");
		String cust_ord_id = (String) askListMap.get("cust_ord_id");
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
		
		
		childrenMap.put("ord_id", ord_id);
		childrenMap.put("service_offer_name", service_offer_name);
		childrenMap.put("offer_name", offer_name);
		childrenMap.put("acc_nbr", acc_nbr);
		childrenMap.put("service_offer_id", service_offer_id);
		childrenMap.put("ord_type", ord_type);
		childrenMap.put("cust_ord_id", cust_ord_id);
		childrenMap.put("instance_type", instance_type);
		childrenMap.put("instance_type_id", instance_type_id);
		childrenMap.put("instance_id", instance_id);
		childrenMap.put("state", ord_state);
		childrenMap.put("ask_id", ask_id);
		
	}
	
	/**
	 * 根据页面传入的信息，查询客户。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getCustList(DynamicDict dto)throws Exception{
		//获取传入的参数
		HashMap paramMap = (HashMap) Const.getParam(dto);
		String lanId = (String) paramMap.get("lanId");
		// D、U都是通过号码查询
		String searchType = (String) paramMap.get("searchType");
		String searchValue = (String) paramMap.get("searchValue");
		Integer pageIndex = (Integer) paramMap.get("pageIndex");
		Integer pageSize = (Integer) paramMap.get("pageSize");
		
		//调用客户管理模块的传入参数。
		String search_type = "";
		String search_value = "";
		//判断查询类型，是否需要进行相应的转换。
		if("/B/C".indexOf(searchType) == -1){
			//页面中传入的查询类型不是客户名称、证件号码，则需要将数据转换成对应的查询值。
			HashMap searchMap = getSearchCon(lanId,searchType,searchValue);
			if( searchMap.isEmpty() || ("".equals((String) searchMap.get("searchValue")))){
				//如果查询不到，直接返回空的查询结果集。
				return new PageModel();
			}else{
				search_type = (String) searchMap.get("searchType");
				search_value = (String) searchMap.get("searchValue");
			}
			
		}else{
			//不需要转换，直接设置值。
			search_type = searchType;
			search_value = searchValue;
		}
		
		HashMap params = new HashMap();
		params.put("lan_id", lanId);
		params.put("search_type", search_type);
		params.put("search_value", search_value);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		//调用客户管理模块，查询客户。
		Object result = ServiceManager.callJavaBeanService(
				CustomerActions.CUST_ACTION, CustomerActions.QRY_CUST_INFO, params);
		return (PageModel) result ;
		
	}
	
	/**
	 * 根据页面传入的查询条件，转换成可以调用客户管理模块的查询条件。
	 * @param lanId
	 * @param searchType
	 * @param searchValue
	 * @return
	 * @throws Exception
	 */
	public HashMap getSearchCon(String lanId,String searchType,String searchValue)throws Exception{
		//定义，需要返回的查询类型和查询值。
		HashMap searchMap = new HashMap();
		searchMap.put("searchType", "");
		searchMap.put("searchValue", "");
		
		if("D/E/".indexOf(searchType) >-1 ){
			//通过号码、上网帐号进行查询,需要查出对应的客户标识。
			ArrayList whereCondParams = new ArrayList();
			whereCondParams.add(searchValue);
			whereCondParams.add(lanId);
			ServDAO servDao = new ServDAO();
			//查询出最新的数据。
			ArrayList servList = (ArrayList) servDao.findByCond(" and acc_nbr =? and lan_id = ? ", whereCondParams);
			
			//判断是否可以查询到对应的客户标识
			Iterator servIter = servList.iterator();
			if(servIter.hasNext()){
				Map servMap = (Map) servIter.next();
				String custId = (String) servMap.get("cust_id");
				//设置需要查询类型和查询值。
				searchMap.put("searchValue", custId);
				searchMap.put("searchType", "A");
			}
			return searchMap;
			
		}else if("F/".indexOf(searchType) > -1){
			//通过定单号进行查询，需要查出对应的客户标识	
			OrdAskDAO ordAskListDao = new OrdAskDAO();
			OrdCustOrderVO ordCustOrderVo = ordAskListDao.getCustOrderByOrdId(searchValue);
			
			if( ordCustOrderVo != null){
				//设置需要查询类型和查询值。
				searchMap.put("searchValue", ordCustOrderVo.getCust_id());
				searchMap.put("searchType", "A");
			}
			
			return searchMap;
		}
		
		return searchMap;
	}
	
	/**
	 * 根据客户编号查询出客户的帐务信息。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getAcctList(DynamicDict dto)throws Exception{
		//获取传入的参数
		HashMap paramMap = (HashMap) Const.getParam(dto);
		
		//调用客户管理模块，查询客户的帐户。
		PageModel acctPageModel = (PageModel) ServiceManager.callJavaBeanService(
				CustomerActions.ACCT_ACTION, CustomerActions.QRY_ACCT_INFO, paramMap);
		
		//需要将acctPageModel中的代表号码的标识，翻译成代表号码。
		ArrayList acctList = (ArrayList) acctPageModel.getList();
		ServDAO servDao = new ServDAO();
		Iterator acctIter = acctList.iterator();
		while(acctIter.hasNext()){
			Map acctMap = (Map) acctIter.next();
			String servId = (String) acctMap.get("serv_id");
			//代表电话的号码
			String acc_nbr = ""; 
			//代表电话的电话标识存在才查代表电话的号码。
			if(servId != null && !"".equals(servId)){
				HashMap keyCondMap = new HashMap();
				keyCondMap.put("serv_id", servId);
				//查询出最新的数据。
				HashMap servMap = servDao.findByPrimaryKey(keyCondMap);
				if (!servMap.isEmpty()){
					acc_nbr = (String) servMap.get("acc_nbr");
				}
			}
			HashMap accNbrMap = new HashMap();
			acctMap.put("acc_nbr", acc_nbr);
		}
		
		return acctPageModel ;
		
	}
	
	/**
	 * 对号码进行单独查询
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public ArrayList showOffInstanceForAccNbr(HashMap paramMap) throws Exception{
		int pageCount = ((Integer)paramMap.get("pageCount")).intValue();
		int currentPages = ((Integer) paramMap.get("currentPages")).intValue();
		String custId= (String) paramMap.get("custId");
		String accNbr = (String) paramMap.get("accNbr");
		
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//先查套餐优惠销售品
		ArrayList offerList = new ArrayList(); 
		offerList = custReceptionDao.queryYHOfferByAccNbr(accNbr, custId, pageCount, currentPages,"1");
		if(offerList.size() == 0 ){
			//套餐优惠销售品为空，则查套餐基础销售品
			offerList = custReceptionDao.queryYHOfferByAccNbr(accNbr, custId, pageCount, currentPages,"0");
		}
		
		return offerList;
	}
	
	/**
	 * 设置销售品实例树的数据。
	 * @param offerList
	 * @return
	 * @throws Exception
	 */
	public ArrayList setOfferInstanceTree(ArrayList offerList) throws Exception{
		//定义返回的树结构的数组。
		ArrayList instanceList = new ArrayList();
		
		//循环取出销售品信息，并且取出销售品下的产品
		Iterator offerIter = offerList.iterator();
		while(offerIter.hasNext()){
			HashMap offerInfoMap = (HashMap) offerIter.next();
			
			String product_offer_instance_id = (String) offerInfoMap.get("product_offer_instance_id");
			String offer_id = (String) offerInfoMap.get("offer_id");
			String offer_kind =(String) offerInfoMap.get("offer_kind");
			String offer_name =(String) offerInfoMap.get("offer_name");
			String prod_id = (String) offerInfoMap.get("product_id"); //基础销售品才有对应的产品规格ID
			String serv_id = (String) offerInfoMap.get("serv_id"); //基础销售品才有对应的产品实例ID
			String instance_type = "10C"; //默认是套餐销售品
			if("0".equals(offer_kind)){
				instance_type = "10B";  //基础销售品
			}
			
			//定义实例的销售品节点的数据。
			HashMap instanceMap = new HashMap();
			instanceMap.put("offer_name", offer_name);
			instanceMap.put("offer_kind", offer_kind);
			instanceMap.put("offer_id", offer_id);
			instanceMap.put("product_offer_instance_id", product_offer_instance_id);
			instanceMap.put("acc_nbr", "");
			instanceMap.put("stop_type", "");
			instanceMap.put("product_id", prod_id);
			instanceMap.put("serv_id", serv_id);
			instanceMap.put("instance_type", instance_type);
			
			//定义子节点。
			ArrayList childrenList = new ArrayList();
			instanceMap.put("children", childrenList);
			
			instanceList.add(instanceMap);
			
			ArrayList offerInstanceList = new ArrayList();
			offerInstanceList.add(product_offer_instance_id);
			
			CustReceptionDAO custReceptionDao = new CustReceptionDAO();
			//查询客户的销售品下的用户的实例信息。
			ArrayList servList = custReceptionDao.showServInstance(offerInstanceList);
			Iterator servIter = servList.iterator();
			while (servIter.hasNext()){
				HashMap servMap = (HashMap) servIter.next();
				String instance_id = (String) servMap.get("instance_id");
				String product_id = (String) servMap.get("product_id");
				String product_name = (String) servMap.get("product_name");
				String acc_nbr = (String) servMap.get("acc_nbr");
				String comp_inst_id = (String) servMap.get("comp_inst_id"); //产品的父销售品实例标识（即基础销售品实例。）
				String product_offer_id = (String) servMap.get("product_offer_id"); //产品的基础销售品的规格标识
				
				//获取产品状态********************************************
				
				//定义产品级节点的数据。
				HashMap childrenMap = new HashMap();
				childrenMap.put("offer_name", product_name);
				childrenMap.put("offer_kind", "");
				childrenMap.put("offer_id", product_offer_id);
				childrenMap.put("product_offer_instance_id", comp_inst_id);
				childrenMap.put("acc_nbr", acc_nbr);
				childrenMap.put("stop_type", "");
				childrenMap.put("product_id", product_id);
				childrenMap.put("serv_id", instance_id);
				childrenMap.put("instance_type", "10A");
				
				childrenList.add(childrenMap);
				
			}
			
		}
		return instanceList;
	}
	
	/**
	 * 查询实例的详细信息
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstanceDetail(DynamicDict dto)throws Exception{
		HashMap returnMap = new HashMap();
		//获取传入的参数
		HashMap paramMap = (HashMap) Const.getParam(dto);
		String instanceType = (String) paramMap.get("instanceType");
		if("10A/10B/".indexOf(instanceType) > -1){
			//产品节点，取出产品的详细信息。
			showServDetail(paramMap,returnMap);
			
		}
		
		if("10B/10C/".indexOf(instanceType) > -1){
			//销售品，查询出对应的可选包信息。
			showOffDetail(paramMap,returnMap);
		}
			
		return returnMap;
	}
	
	/**
	 * 查询产品的详细信息。
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public void showServDetail(HashMap paramMap,HashMap detailMap)throws Exception{
		//查询产品的附属产品。
		ArrayList whereCondParams = new ArrayList();
		String servId = (String) paramMap.get("servId");
		whereCondParams.add(servId);
		ArrayList servProdList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//获取产品的附属产品。
		servProdList = custReceptionDao.queryServProductName(whereCondParams);
		//用于存放附属产品
		StringBuffer servProdBuf = new StringBuffer();
		if(servProdList != null && servProdList.size() >0){
			Iterator servProdIter = servProdList.iterator();
			servProdBuf.append("附属产品：");
			while(servProdIter.hasNext()){
				Map servProdMap = (Map) servProdIter.next();
				String product_name = (String) servProdMap.get("product_name");
				servProdBuf.append(product_name+";   ");
			}
		}
		
		detailMap.put("serv_product", servProdBuf.toString());
	}
	
	/**
	 * 查询销售品的详细信息
	 * @param paramMap
	 * @param detailMap
	 * @throws Exception
	 */
	public void showOffDetail(HashMap paramMap,HashMap detailMap)throws Exception{
		//查询销售品的可选包
		String offerInstanceId = (String) paramMap.get("instanceId");
		ArrayList whereCondParams = new ArrayList();
		whereCondParams.add(offerInstanceId);
		ArrayList selectPackageList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//获取销售品的可选包
		selectPackageList = custReceptionDao.queryOfferSelectPackage(whereCondParams);
		//用于存放可选包。
		StringBuffer selectPackageBuf = new StringBuffer();
		if(selectPackageList != null && selectPackageList.size() > 0){
			Iterator selectPackageIter = selectPackageList.iterator();
			selectPackageBuf.append("所选可选包：");
			while(selectPackageIter.hasNext()){
				Map selectPackageMap = (Map) selectPackageIter.next();
				String offer_name = (String) selectPackageMap.get("offer_name");
				selectPackageBuf.append(offer_name+";   ");
			}
		}
		detailMap.put("offer_name", selectPackageBuf.toString());
	}
	
	/**
	 * 获取序列。
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public String getSeqId(DynamicDict dto)throws Exception{
		Map paramMap = (Map)dto.getValueByName("parameter") ;
		String tableCode  = (String)paramMap.get("table_code");
		String fieldCode  = (String)paramMap.get("field_code");
		String seqId = "";
		seqId = SeqUtil.getInst().getNext(tableCode, fieldCode);
		return seqId;
	}
	
	/**
	 * 查询客户后，需要获取的客户相关信息.
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap getInitInfoByCustId(DynamicDict dto)throws Exception{
		Map paramMap = (Map)dto.getValueByName("parameter") ;
		String cust_id = (String) paramMap.get("cust_id");
		
		//根据客户编号，获取未确认的客户订单号。
		String cust_ord_id = getCustOrdId(cust_id);
		
		HashMap initMap = new HashMap();
		initMap.put("cust_ord_id", cust_ord_id);
		return initMap;
	}
	
	/**
	 * 根据客户标识，或者客户订单编号
	 * @param cust_id
	 * @return
	 * @throws FrameException
	 */
	public String getCustOrdId(String cust_id) throws FrameException{
		OrdCustOrderDAO custOrdDao = new OrdCustOrderDAO();
		String whereCond = " and cust_id = ? and state='100' ";
		List whereCondParams = new ArrayList();
		whereCondParams.add(cust_id);
		ArrayList custOrdList = new ArrayList(); 
		//查询出未确认的客户订单。
		custOrdList = (ArrayList) custOrdDao.findByCond(whereCond, whereCondParams);
		Iterator custOrdIter =custOrdList.iterator();
		String cust_ord_id = "";
		if(custOrdIter.hasNext()){
			Map voMap = (Map) custOrdIter.next();
			cust_ord_id = (String) voMap.get("cust_ord_id");
		}
		
		return cust_ord_id;
	}
}
