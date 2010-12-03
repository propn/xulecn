package com.ztesoft.crm.product.service;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.dict.ServiceManager;
import com.ztesoft.common.dict.DataTranslate;

import com.ztesoft.common.util.PageModel;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dict.ServiceList;


public class AttrService  {
	/**
	     ��Ҫ�滻λ�� ˵�� ��
	  1. ServiceList.MyService  �滻ΪServiceListע��ķ���
	  2. searchAttrData �ķ����Ĳ�������
	  3. findAttrByCond(String attr_id) ������Ҫ����ʵ������޸�
	  4. ����Ҫ�ķ��������Ը���ʵ��������вü�
	  5. �˶Ά��»�����ɺ��滻��������ɾ����
	 */

	/**
	 * ������������
	 */
	public boolean insertAttr(HashMap Attr ) throws Exception {
		Map param = new HashMap() ;
		param.put("Attr", Attr) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrBO",
						"insertAttr" ,param)) ;
		return result ;
	}

/**
 * �����޸ķ���
 * @param Attr
 * @return
 * @throws Exception
 */
	public boolean updateAttr(HashMap Attr ) throws Exception {
		Map param = new HashMap() ;
		param.put("Attr", Attr) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrBO",
						"updateAttr" ,param)) ;

		return result ;
	}

/**
 * ���Բ�ѯ����
 * @param queryway
 * @param querydata
 * @param pageIndex
 * @param pageSize
 * @return
 * @throws Exception
 */
	public PageModel searchAttrData(String queryway , String querydata , int pageIndex , int pageSize ) throws Exception {

		Map param = new HashMap() ;
		param.put("query_way", queryway) ;
		param.put("query_data", querydata) ;
		param.put("pageIndex", new Integer(pageIndex)) ;
		param.put("pageSize", new Integer(pageSize)) ;

		PageModel result = DataTranslate._PageModel(
				ServiceManager.callJavaBeanService("AttrBO",
						"searchAttrData" ,param)) ;

		return result ;
	}

/**
 * ��ѯ
 * @param attr_id
 * @return
 * @throws Exception
 */
	public Map getAttrById(String attr_id) throws Exception {
		Map param = getAttrKeyMap(attr_id) ;

		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("AttrBO",
						"getAttrById" ,param)) ;

		return result ;
	}

/**
 * ��ѯ
 * @param attr_id
 * @return
 * @throws Exception
 */
	public List findAttrByCond(String attr_id) throws Exception {
		Map param = getAttrKeyMap(attr_id) ;

		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("AttrBO",
						"findAttrByCond" ,param)) ;

		return result ;
	}

/**
 * ɾ��
 * @param attr_id
 * @return
 * @throws Exception
 */
	public boolean deleteAttrById(String attr_id) throws Exception {
		Map param = getAttrKeyMap(attr_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrBO",
						"deleteAttrById" ,param)) ;

		return result ;
	}
/**
 * ѹ����
 * @param attr_id
 * @return
 */
	private Map getAttrKeyMap(String attr_id){
		Map param = new HashMap() ;

		param.put("attr_id", attr_id) ;

		return param ;
	}
/**
 * ����ֵ����
 * @param AttrValue
 * @return
 * @throws Exception
 */
	public boolean insertAttrValue(HashMap AttrValue ) throws Exception {
		Map param = new HashMap() ;
		param.put("AttrValue", AttrValue) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrValueBO",
						"insertAttrValue" ,param)) ;
		return result ;
	}

/**
 * ����ֵ�޸�
 * @param AttrValue
 * @return
 * @throws Exception
 */
	public boolean updateAttrValue(HashMap AttrValue ) throws Exception {
		Map param = new HashMap() ;
		param.put("AttrValue", AttrValue) ;
		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrValueBO",
						"updateAttrValue" ,param)) ;

		return result ;
	}
	/**
	 * ɾ��
	 * @param attr_value_id
	 * @return
	 * @throws Exception
	 */
	public boolean deleteAttrValueById(String attr_value_id) throws Exception {
		Map param = getAttrValueKeyMap(attr_value_id) ;

		boolean result = DataTranslate._boolean(
				ServiceManager.callJavaBeanService("AttrValueBO",
						"deleteAttrValueById" ,param)) ;

		return result ;
	}
	private Map getAttrValueKeyMap(String attr_value_id){
		Map param = new HashMap() ;

		param.put("attr_value_id", attr_value_id) ;

		return param ;
	}
	/**
	 * ��ѯ ��ҳ
	 * @param attrId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 * @throws Exception
	 */
	public List searchAttrValueData(String attrId) throws Exception {

		Map param = new HashMap() ;
		param.put("attr_id", attrId) ;

		List result = DataTranslate._List(
				ServiceManager.callJavaBeanService("AttrValueBO",
						"searchAttrValueData" ,param)) ;

		return result ;
	}
	/**
	 * ��ѯ
	 * @param attr_value_id
	 * @return
	 * @throws Exception
	 */
	public Map getAttrValueById(String attr_value_id) throws Exception {
		Map param = getAttrValueKeyMap(attr_value_id) ;

		Map result = DataTranslate._Map(
				ServiceManager.callJavaBeanService("AttrValueBO",
						"getAttrValueById" ,param)) ;

		return result ;
	}

	/**
	 * ���Թ�ϵ���� ��ѯ
	 * @param queryWay
	 * @param queryData
	 * @return
	 * @throws Exception
	 */
	public List getAttrValue(String queryWay,String queryData) throws Exception {
		Map param = new HashMap();
		param.put("queryWay",queryWay);
		param.put("queryData",queryData);
		List result = DataTranslate._List(ServiceManager.callJavaBeanService("AttrBO",	"getAttrValue" ,param));
		return result;
	}
	public List getAttrValueRelaHis(String attrValueId) throws Exception {
		Map param = new HashMap();
		param.put("a_attr_value_id",attrValueId);
		List result = DataTranslate._List(ServiceManager.callSQLService("QPM_ATTR_VALUE_RELA_0001" ,param));
		return result;
	}
	/**
	 * ���Թ�ϵ���� ����
	 * @param list
	 * @return
	 * @throws Exception
	 */
	public boolean saveAttrValueRela(List list) throws Exception {
		Map param = new HashMap();
		param.put("attrValueRela",list);
		boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("AttrBO","saveAttrValueRela",param));
		return result;
	}
	public boolean deleteAttrValueRelaById(Map param) throws Exception {
		boolean result = DataTranslate._boolean(ServiceManager.callJavaBeanService("AttrBO","deleteAttrValueRelaById",param));
		return result;
	}
}
