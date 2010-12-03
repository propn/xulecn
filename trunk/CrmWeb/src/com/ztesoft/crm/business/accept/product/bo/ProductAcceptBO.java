package com.ztesoft.crm.business.accept.product.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.crm.business.accept.product.dao.ProductAcceptDAO;

public class ProductAcceptBO extends DictAction{
	
	private ProductAcceptDAO productAcceptDao = new ProductAcceptDAO();
	
	/**
	 * ��ò�Ʒ��������
	 * @param dto   
	 * 		  element_id       ��Ʒ��ʶ
	 * @return List
	 * @throws Exception
	 */
	 public List getProductAttr(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	     String  element_id=(String)hashMap.get("element_id");
	     List  params = new ArrayList();
	     params.add(element_id);
	      List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_PRODUCT_ATTR,params);
	     
		 return list;
	 }
	 
	 /**
	 * ��ò�Ʒ����ʵ������
	 * @param dto   
	 * 		  element_id       ��Ʒ��ʶ
	 * 		  serv_product_id  ������Ʒʵ��
	 * @return List
	 * @throws Exception
	 */
	 public List getProductAttrInst(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	     String  element_id = (String)hashMap.get("element_id");
	     String serv_product_id = (String)hashMap.get("serv_product_id");
	     List  params = new ArrayList();
	     params.add(serv_product_id);
	     params.add(element_id);
	     
	     List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_PRODUCT_ATTR_INST,params);
	     
		 return list;
	 }
	 
	 /**
	 * ��ò�Ʒ����ȡֵ
	 * @param dto   
	 * 		  attr_id       ����ID
	 * @return List
	 * @throws Exception
	 */
	 public List getProductAttrValues(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	     String  attr_id=(String)hashMap.get("attr_id");
	     List  params = new ArrayList();
	     params.add(attr_id);
	     
	     List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_PRODUCT_ATTR_VALUE,params);
	     int listSize = list.size();
	     String values = "";
	     String descs = "";
	     for(int i=0; i<listSize; i++)
	     {
	    	 HashMap hm = (HashMap)list.get(i);
	    	 descs += (String) hm.get("attr_value_desc") + "/";
	    	 values += (String) hm.get("attr_value") + "/";
	     }
	     
	     ArrayList retList = new ArrayList(2);
	     retList.add(descs.substring(0, descs.length()-1));
	     retList.add(values.substring(0, values.length()-1));
		 return retList;
	 }
	 
	 /**
	 * �������Ʒ�ĸ�����Ʒ
	 * @param dto   
	 * 		  element_id    ��Ʒ��ʶ
	 * 		  region_id     ����
	 * @return List
	 * @throws Exception
	 */
	 public List getOtherProduct(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	     String  element_id = (String)hashMap.get("element_id");
	     String  region_id = (String)hashMap.get("region_id");
	     List  params = new ArrayList();
	     params.add(region_id);
	     params.add(element_id);
	     
	     List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_OTHER_PRODUCT,params);
		 return list;
	 }
	 
	 /**
	 * ����serv_id��ø�����Ʒʵ��
	 * @param dto   
	 * 		  serv_id    ��Ʒʵ��ID
	 * @return List
	 * @throws Exception
	 */
	 public List getServProduct(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
	     String  serv_id = (String)hashMap.get("serv_id");
	     List  params = new ArrayList();
	     params.add(serv_id);
	     
	     List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_SERV_PRODUCT,params);
		 return list;
	 }
	 
	 /**
	 * ���ݲ�Ʒ��ʶ����Ʒʵ��ID ��ÿ�ѡ������Ʒ��������Ʒʵ��
	 * @param dto   
	 * 		  serv_id    ��Ʒʵ��ID
	 * 		  element_id    ��Ʒ��ʶ
	 * @return List
	 * @throws Exception
	 */
	 public List getOtherProductAndServProduct(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		 String  serv_id = (String)hashMap.get("serv_id");
		 List list = new ArrayList();
		 HashMap hm = new HashMap(2);
		 List list1 = null;
		 List list2 = null;
		
		 list1 = getOtherProduct(dto);
		 if(serv_id != null)
			 list2 = getServProduct(dto);
		 
	     if(list1 != null) hm.put("product", list1);
	     if(list2 != null) hm.put("instance", list2);
	     list.add(hm);
		 return list;
	 }
	 
	 /**
	 * ��ѯ��Ʒ����ʵ������
	 * @param dto   
	 * 		  serv_id    ��Ʒʵ��ID
	 * 		  element_id    ��Ʒ��ʶ
	 * @return List
	 * @throws Exception
	 */
	 public List getServProductAttr(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		 String serv_id = (String)hashMap.get("serv_id");
		// String product_id = (String)hashMap.get("product_id");
		 List  params = new ArrayList();
		 //params.add(product_id);
		// List tables = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_TABLE,params);
		 //if(tables != null)
		// {
			// for(int i=0; i<tables.size(); i++)
			// {
				 //String table_name = ((HashMap)tables.get(i)).get("table").toString();
			 //}
		 //}
		 
		 
		 //params.clear();
		 params.add(serv_id);
	     List list=productAcceptDao.findBySql(ProductAcceptDAO.SELECT_SERV_ATTR,params);
		 return list;
	 }
	 
	/**
	* ���ݱ������������ѯ�ͻ�ID
    * @param dto   
	* 		  acc_nbr     ����
	* 		  lan_id      ������
	* @return List
	* @throws Exception
	*/
	 public List getCustId(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		 String  acc_nbr = (String)hashMap.get("acc_nbr");
		 String  lan_id = (String)hashMap.get("lan_id");
		 List  params = new ArrayList();
		 params.add(lan_id);
		 params.add(acc_nbr);
		 

		 List list = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_CUST_ID,params);
		 return list;
	 }
	 
	/**
	* �����Ŀ����
	* @param dto   
	* 		  acc_nbr     ����
	* 		  lan_id      ������
	* @return List
	* @throws Exception
	*/
	 public List getAcctItemType(DynamicDict dto) throws Exception
	 {
		 //HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		 //List  params = new ArrayList();
		 
		 List list = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_ACCT_TYPE,null);
		 return list;
	 }
	 
	 /**
	 * ����serv_id��ѯ�����ϵʵ��
	 * @param dto
	 * 		  serv_id          ����Ʒʵ��ID
	 * @return ArrayList
	 * @throws Exception
	 */
	 public List getServAcct(DynamicDict dto) throws Exception
	 {
		 HashMap hashMap=(HashMap)dto.getValueByName("parameter");
		 String  serv_id = (String)hashMap.get("serv_id");
		 List  params = new ArrayList();
		 params.add(serv_id);
		 List list=null;
			//�������ݿ����ͷ��� Ĭ��oracle���ݿ�
			if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
				list = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_SERV_ACCT_INFORMIX,params);
			}
			if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
				list = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_SERV_ACCT_ORACLE,params);
			}
		 //List list = productAcceptDao.findBySql(ProductAcceptDAO.SELECT_SERV_ACCT,params);
		 return list;
	 }
}
