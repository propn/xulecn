package com.ztesoft.crm.business.accept.product.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.crm.business.common.cache.PropCache;
import com.ztesoft.crm.business.common.consts.ReceptionActions;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class ProductAcceptService extends DictService
{
	//DICACTION的名称
	private final static String DICT_NAME_ACCEPT = "PRODUCTACCEPTBO";
	private final static String DICT_NAME_NBR = "RcNoBo";//选号
	//DICACTION的执行方法。
	private final static String GET_PRODUCT_ATTR = "getProductAttr";
	private final static String GET_OTHER_PRODUCT = "getOtherProduct"; 
	private final static String GET_PRODUCT_ATTR_INST = "getProductAttrInst";
	private final static String GET_PRODUCT_ATTR_VALUES = "getProductAttrValues";
	private final static String GET_OTHER_PRODUCT_AND_SERV_PRODUCT = "getOtherProductAndServProduct";
	private final static String GET_CUST_ID = "getCustId";
	private final static String GET_SERV_PRODUCT_ATTR = "getServProductAttr";
	private final static String GET_ACCT_ITEM_TYPE = "getAcctItemType";
	private final static String GET_SERV_ACCT = "getServAcct";//查询帐务关系
	private final static String GET_RC_NO = "getRcNo";//查询号码
	
	
	/**
	 * 获得产品属性数据
	 * @param map   
	 * 		  element_id       产品标识
	 * 		  region_id        区域
	 * 		  service_offer_id 业务类型
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getProductAttrData(HashMap map) throws Exception{
		if(!map.containsKey("element_id"))  return new ArrayList();
		DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_PRODUCT_ATTR);
		dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		   
		return ((ArrayList)dto.getValueByName("result"));
	}
	
	/**
	 * 获得附属产品数据
	 * @param map   
	 * 		  element_id       产品标识
	 * 		  region_id        区域
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getOtherProductData(HashMap map) throws Exception
	{
		if(!map.containsKey("element_id"))  return new ArrayList();
		if(!map.containsKey("region_id"))  return new ArrayList();
		DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_OTHER_PRODUCT);
		dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		
		return ((ArrayList)dto.getValueByName("result"));
	}
	
	/**
	 * 获得附属产品属性数据
	 * @param map   
	 * 		  element_id       产品标识
	 * 		  region_id        区域
	  * 		  service_offer_id 业务类型
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getOtherProductAttrData(HashMap map) throws Exception{
		if(!map.containsKey("element_id"))  return new ArrayList();
		String action_type = (String)map.get("action_type");
		
		DynamicDict dto = null;
		ArrayList attrList = null;
		if("k".equals(action_type.toLowerCase()))//查询附属产品属性实例
		{
			dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_PRODUCT_ATTR_INST);
			dto.setValueByName("parameter",map) ;
			dto = ActionDispatch.dispatch(dto);
			attrList = (ArrayList)dto.getValueByName("result");
		}
		else
		{
			dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_PRODUCT_ATTR);
			dto.setValueByName("parameter",map) ;
			dto = ActionDispatch.dispatch(dto);
			attrList = (ArrayList)dto.getValueByName("result");
		}
		int attrSize = attrList.size();
		for(int i=0; i<attrSize; i++)
		{
			HashMap hm = (HashMap)attrList.get(i);
			String inputMethod=((String)hm.get("input_method")).trim();
			if(PropCache.dropDown.equals(inputMethod))//下拉框
			{
				DynamicDict dto1 = getServiceDTO(DICT_NAME_ACCEPT ,GET_PRODUCT_ATTR_VALUES);
				map.put("attr_id", hm.get("attr_id"));
				dto1.setValueByName("parameter",map) ;
				dto1 = ActionDispatch.dispatch(dto1);
				ArrayList values = (ArrayList)dto1.getValueByName("result");
				
				hm.put("attrValuesDesc",values.get(0));
				hm.put("attrValues",values.get(1));
			}
			attrList.set(i, hm);
		}
		return attrList;
	}
	
	/**
	 * 获得附属产品和实例
	 * @param map   
	 * 		  element_id       产品标识
	 * 		  region_id        区域
	 * 		  serv_id          主产品实例ID
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getOtherProductAndInstance(HashMap map) throws Exception
	{
		if(!map.containsKey("element_id"))  return new ArrayList();
		if(!map.containsKey("region_id"))  return new ArrayList();
		
		DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_OTHER_PRODUCT_AND_SERV_PRODUCT);
		dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		
		return ((ArrayList)dto.getValueByName("result"));
	}
	
	/**
	 * 根据serv_id查询帐务关系实例
	 * @param map
	 * 		  serv_id          主产品实例ID
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getServAcct(HashMap map) throws Exception
	{
		if(!map.containsKey("serv_id"))  return new ArrayList();
		
		DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_SERV_ACCT);
		dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		
		return ((ArrayList)dto.getValueByName("result"));
	}
	
	
	/**
	 * 根据客户编号查询出客户的帐务信息。
	 * @param map   
	 * 		  cust_id       产品标识
	 *        pageIndex     页码
	 *        pageSize      每页记录数
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel getAcctList(String lan_id, String search_type, String search_value, int pageIndex, int pageSize) throws Exception{
		//界面传入参数
		String cust_id = search_value;
		if( "2".equals(search_type) )//号码
		{
			HashMap map = new HashMap();
			map.put("lan_id", lan_id);
			map.put("acc_nbr", search_value);

			DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_CUST_ID);
			dto.setValueByName("parameter",map) ;
			dto = ActionDispatch.dispatch(dto);
			   
			ArrayList list = ((ArrayList)dto.getValueByName("result"));
			if(list.size() < 1) return new PageModel();
			cust_id = (String) ( (HashMap) list.get(0) ).get("cust_id");
		}
		
		
		if( "3".equals(search_type) || "2".equals(search_type))//客户ID
		{
			HashMap map = new HashMap();
			map.put("cust_id", cust_id);
			map.put("pageIndex", new Integer(pageIndex));
			map.put("pageSize", new Integer(pageSize));
			//后台服务
			Object result = ServiceManager.callJavaBeanService(
					ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_ACCT_LIST, map);
			return (PageModel) result ;
		}
		else if( "1".equals(search_type) )//客户名
		{
			
		}
		
		return new PageModel();
	}
	
	/**
	 * 根据实例ID查询卡片信息。
	 * @param map   
	 * 		  serv_ids       产品实例标识
	 * @return List
	 * @throws Exception
	 */
	public ArrayList getMainProdInst(HashMap hashmap) throws Exception
	{
		//界面传入参数
		String keys1 = (String)hashmap.get("serv_ids");
		String keys2 = (String)hashmap.get("product_ids");
		String[] serv_ids = keys1.split(",");
		String[] product_ids = keys2.split(",");
		ArrayList list = new ArrayList();
		ArrayList curList = null;
		for(int i=0;i<serv_ids.length;i++)
		{
			HashMap map = new HashMap();
			map.put("serv_id", serv_ids[i]);
			map.put("product_id", product_ids[i]);
			DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_SERV_PRODUCT_ATTR);
			dto.setValueByName("parameter",map) ;
			dto = ActionDispatch.dispatch(dto);
			curList = ((ArrayList)dto.getValueByName("result"));
			
			for(int j=0; j<curList.size(); j++)
			{
				HashMap hm = (HashMap) curList.get(j);
				list.add(hm);
			}
		}
		return list ;
	}
	
	/**
	 * 查询号码信息。
	 * @param lan_id       本地网
	 * 		  business_id  营业区
	 * 		  product_id   产品ID
	 * 		  nbr_level    号码等级
	 * 		  search_type  查询方式  1、号码尾数  2、精确查询
	 * 		  search_value 查询数据
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel getAccNbr(String lan_id,String business_id, String product_id,String nbr_level,String search_type,String search_value,int pageIndex, int pageSize) throws Exception
	{
		//操作员信息
		GlobalVariableHelper globalHelper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
		String staff_no = globalHelper.getVariable(globalHelper.OPER_ID);
		//String site_no = globalHelper.getVariable(globalHelper.OPER_ORG_CODE);  //操作员所在的部门。
		//String ask_source = globalHelper.getVariable(globalHelper.ACCPET_SOURCE);
		HashMap map = new HashMap();
		map.put("lan_id", lan_id);
		//map.put("business_id", business_id);
		map.put("product_id", product_id);
		map.put("nbr_level", nbr_level);
		map.put("search_type", search_type);
		map.put("search_value", search_value);
		map.put("oper_id", staff_no);
		map.put("pageIndex", new Integer(pageIndex));
		map.put("pageSize", new Integer(pageSize));
		
		DynamicDict dto = getServiceDTO(DICT_NAME_NBR ,GET_RC_NO);
		dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		
		PageModel pm = (PageModel)dto.getValueByName("result");
		return pm;
	}
	
	/**
	 * 获得帐目类型
	 * @param map   
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getAcctItemType() throws Exception
	{
		DynamicDict dto = getServiceDTO(DICT_NAME_ACCEPT ,GET_ACCT_ITEM_TYPE);
		//dto.setValueByName("parameter",map) ;
		dto = ActionDispatch.dispatch(dto);
		
		return ((ArrayList)dto.getValueByName("result"));
	}
}
