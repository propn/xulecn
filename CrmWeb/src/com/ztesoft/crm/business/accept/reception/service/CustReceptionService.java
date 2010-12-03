package com.ztesoft.crm.business.accept.reception.service;

import com.powerise.ibss.framework.ActionDispatch;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictService;
import com.ztesoft.common.dict.ServiceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.business.common.consts.ReceptionActions;
import com.ztesoft.crm.business.common.consts.Services;

/**
 * �ͻ��Ӵ����÷������
 * @author lindy
 *
 */
public class CustReceptionService {
	
	public CustReceptionService(){
		
	}
	
	public PageModel getCustList(String lanId,String searchType,String searchValue,
				int pageIndex, int pageSize)throws Exception{
		//���洫�����
		Map param = new HashMap();
		param.put("lanId", lanId);
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		//param.put("pageIndex", pageIndex);
		//param.put("pageSize", pageSize);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_CUST_LIST, param);
		return (PageModel) result ;
	}
	
	/**
	 * ��ȡ��Ҫ�Ƽ����ͻ�������Ʒ��
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public ArrayList getCustRecommend(String custId)throws Exception{
		//���洫�����
		Map param = new HashMap();
		param.put("custId", custId);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_OFFER_RECOMMEND, param);
		return (ArrayList) result ;
	}
	
	/**
	 * ��ѯ��������Ʒ��������Ϣ��
	 * @param offerId
	 * @return
	 * @throws Exception
	 */
	public String showOfferRecommend(String offerId)throws Exception{
		HashMap param = new HashMap();
		//���洫�����
		param.put("offerId", offerId);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_RECOMMEND, param);
		
		return (String) result;
	}
	
	/**
	 * ��ѯ�ͻ�������Ʒʵ����Ϣ��
	 * @param custId
	 * @param pageCount
	 * @param currentPages
	 * @param accNbr    //��ȷ��λ�ĺ���
	 * @param queryNbr  //����������ʾ�ĺ���
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstance(String custId, int pageCount, int currentPages, String accNbr,String queryNbr)throws Exception{
		//��ȡ����
		HashMap loginMap = (HashMap)RequestContext.getContext().getHttpSession().getAttribute("LoginRtnParamsHashMap");
		String operId = (String) loginMap.get("vg_oper_id");
		//���ò�����
		HashMap paramMap = new HashMap();
		paramMap.put("custId", custId);
		paramMap.put("operId", operId);
		paramMap.put("accNbr", accNbr);
		paramMap.put("queryNbr", queryNbr);
		//paramMap.put("pageCount", pageCount);
		//paramMap.put("currentPages", currentPages);
		
		//��̨��������
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_INSTANCE, paramMap);
		//���ض���
		return (HashMap) result;
	}
	
	/**
	 * ��ѯ����Ʒ����
	 * @param source  ���õ����
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffService(String source,HashMap paramIn) throws Exception{
		//���ò�����
		HashMap paramMap = new HashMap();
		paramMap.put("source", source);
		paramMap.put("param", paramIn);
		
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.SHOW_OFFER_SERVICE, paramMap);
		
		return (HashMap) result;
	}
	
	/**
	 * ��ѯ����Ʒʵ������ϸ��Ϣ��
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstanceDetail(HashMap paramIn) throws Exception{

		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_INSTANCE_DETAIL, paramIn);
		
		return (HashMap) result;
	}
	
	/**
	 * �ڿͻ��Ӵ�ҳ��չʾ�ͻ��Ĺ��̵���
	 * @param custId
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public HashMap showCustOrdInfo(String custId,String source,int currentPages, int pageCount) throws Exception{
		//���ò�����
		HashMap paramMap = new HashMap();
		paramMap.put("custId", custId);
		paramMap.put("source", source);
		//paramMap.put("currentPages", currentPages);
		//paramMap.put("pageCount", pageCount);
		
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_CUST_ORD_INFO, paramMap);
		
		return (HashMap) result;
	}
	
	/**
	 * ���ݿͻ���ʶ��ѯ�ͻ����ʻ���
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public PageModel getAcctList(String custId,int pageIndex, int pageSize) throws Exception{
		//���洫�����
		Map param = new HashMap();
		param.put("cust_id", custId);
		//param.put("pageIndex", pageIndex);
		//param.put("pageSize", pageSize);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_ACCT_LIST, param);
		return (PageModel) result ;
	}
	
	/**
	 * ���ݱ������ֶ�����ȡ���кš�
	 * @param tableCode
	 * @param fieldCode
	 * @return
	 * @throws Exception
	 */
	public String getSeqId(String tableCode,String fieldCode) throws Exception{
		//���洫�����
		Map param = new HashMap();
		param.put("table_code", tableCode);
		param.put("field_code", fieldCode);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.GET_SEQ_ID,param);
		return (String) result ;
	}
	
	/**
	 * ��ѯ�ͻ�����Ҫ��ȡ�Ŀͻ������Ϣ��
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public HashMap getInitInfoByCustId(String custId) throws Exception{
		//���洫�����
		Map param = new HashMap();
		param.put("cust_id", custId);
		//��̨����
		Object result = ServiceManager.callJavaBeanService(
				ReceptionActions.CUST_RECEPT_ACTION, ReceptionActions.QUERY_CUST_INIT_INFO,param);
		return (HashMap) result ;
	}
}
