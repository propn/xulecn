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
	//DICACTION������
	private final static String DICT_NAME_ACCEPT = "PRODUCTACCEPTBO";
	private final static String DICT_NAME_NBR = "RcNoBo";//ѡ��
	//DICACTION��ִ�з�����
	private final static String GET_PRODUCT_ATTR = "getProductAttr";
	private final static String GET_OTHER_PRODUCT = "getOtherProduct"; 
	private final static String GET_PRODUCT_ATTR_INST = "getProductAttrInst";
	private final static String GET_PRODUCT_ATTR_VALUES = "getProductAttrValues";
	private final static String GET_OTHER_PRODUCT_AND_SERV_PRODUCT = "getOtherProductAndServProduct";
	private final static String GET_CUST_ID = "getCustId";
	private final static String GET_SERV_PRODUCT_ATTR = "getServProductAttr";
	private final static String GET_ACCT_ITEM_TYPE = "getAcctItemType";
	private final static String GET_SERV_ACCT = "getServAcct";//��ѯ�����ϵ
	private final static String GET_RC_NO = "getRcNo";//��ѯ����
	
	
	/**
	 * ��ò�Ʒ��������
	 * @param map   
	 * 		  element_id       ��Ʒ��ʶ
	 * 		  region_id        ����
	 * 		  service_offer_id ҵ������
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
	 * ��ø�����Ʒ����
	 * @param map   
	 * 		  element_id       ��Ʒ��ʶ
	 * 		  region_id        ����
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
	 * ��ø�����Ʒ��������
	 * @param map   
	 * 		  element_id       ��Ʒ��ʶ
	 * 		  region_id        ����
	  * 		  service_offer_id ҵ������
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList getOtherProductAttrData(HashMap map) throws Exception{
		if(!map.containsKey("element_id"))  return new ArrayList();
		String action_type = (String)map.get("action_type");
		
		DynamicDict dto = null;
		ArrayList attrList = null;
		if("k".equals(action_type.toLowerCase()))//��ѯ������Ʒ����ʵ��
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
			if(PropCache.dropDown.equals(inputMethod))//������
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
	 * ��ø�����Ʒ��ʵ��
	 * @param map   
	 * 		  element_id       ��Ʒ��ʶ
	 * 		  region_id        ����
	 * 		  serv_id          ����Ʒʵ��ID
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
	 * ����serv_id��ѯ�����ϵʵ��
	 * @param map
	 * 		  serv_id          ����Ʒʵ��ID
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
	 * ���ݿͻ���Ų�ѯ���ͻ���������Ϣ��
	 * @param map   
	 * 		  cust_id       ��Ʒ��ʶ
	 *        pageIndex     ҳ��
	 *        pageSize      ÿҳ��¼��
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel getAcctList(String lan_id, String search_type, String search_value, int pageIndex, int pageSize) throws Exception{
		//���洫�����
		String cust_id = search_value;
		if( "2".equals(search_type) )//����
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
		
		
		if( "3".equals(search_type) || "2".equals(search_type))//�ͻ�ID
		{
			HashMap map = new HashMap();
			map.put("cust_id", cust_id);
			map.put("pageIndex", new Integer(pageIndex));
			map.put("pageSize", new Integer(pageSize));
			//��̨����
			Object result = ServiceManager.callJavaBeanService(
					ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_ACCT_LIST, map);
			return (PageModel) result ;
		}
		else if( "1".equals(search_type) )//�ͻ���
		{
			
		}
		
		return new PageModel();
	}
	
	/**
	 * ����ʵ��ID��ѯ��Ƭ��Ϣ��
	 * @param map   
	 * 		  serv_ids       ��Ʒʵ����ʶ
	 * @return List
	 * @throws Exception
	 */
	public ArrayList getMainProdInst(HashMap hashmap) throws Exception
	{
		//���洫�����
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
	 * ��ѯ������Ϣ��
	 * @param lan_id       ������
	 * 		  business_id  Ӫҵ��
	 * 		  product_id   ��ƷID
	 * 		  nbr_level    ����ȼ�
	 * 		  search_type  ��ѯ��ʽ  1������β��  2����ȷ��ѯ
	 * 		  search_value ��ѯ����
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel getAccNbr(String lan_id,String business_id, String product_id,String nbr_level,String search_type,String search_value,int pageIndex, int pageSize) throws Exception
	{
		//����Ա��Ϣ
		GlobalVariableHelper globalHelper = new GlobalVariableHelper(RequestContext.getContext().getHttpRequest());
		String staff_no = globalHelper.getVariable(globalHelper.OPER_ID);
		//String site_no = globalHelper.getVariable(globalHelper.OPER_ORG_CODE);  //����Ա���ڵĲ��š�
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
	 * �����Ŀ����
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
