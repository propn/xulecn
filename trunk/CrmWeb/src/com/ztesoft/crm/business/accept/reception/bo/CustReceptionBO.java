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
 * �ͻ��Ӵ���ҵ���߼�������
 * @author lindy
 *
 */
public class CustReceptionBO extends DictAction {

	public CustReceptionBO(){
		
	}
	
	/**
	 * ��ȡ�Ƽ����ͻ�������Ʒ
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
			recommendMap.put("offerName", "����Ʒ����"+i);
			recommendMap.put("offerId", "100"+i);
			recommendMap.put("offerDesc", "����Ʒ����"+i);
			recommendList.add(recommendMap);
		}
		
		return recommendList;
	}
	
	/**
	 * չʾ�Ƽ���Ʒ��������
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
	 * ��ѯ�ͻ�������Ʒʵ����Ϣ��
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstance(DynamicDict dto)throws Exception{
		//��ȡ����Ĳ���MAP
		HashMap paramMap = (HashMap) Const.getParam(dto);
		
		Integer pageCount = (Integer)paramMap.get("pageCount");
		Integer currentPages = (Integer) paramMap.get("currentPages");
		String custId= (String) paramMap.get("custId");
		String accNbr = (String) paramMap.get("accNbr");
		
		//��ѯ������Ʒ
		ArrayList offerList = new ArrayList();
		
		//�жϴ���ľ�ȷ��λ�ĺ����Ƿ�Ϊ�գ������Ϊ�գ���Ҫ�����Ҹú��롣
		if(accNbr != null && !"".equals(accNbr)){
			//�Ժ�����е�����ѯ��
			offerList = showOffInstanceForAccNbr(paramMap);
			
		}else{
			String queryNbr = (String) paramMap.get("queryNbr");
			if(queryNbr != null && !"".equals(queryNbr)){
				//���ݲ�ѯ�����ѯ�ͻ����ڲ�Ʒʵ��������չʾ��ǰ���롣
				//�����ѯ�ͻ�����Ʒʵ�������
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
				//��ѯ�ͻ�������Ʒʵ����Ϣ��
				offerList = custReceptionDao.showOffInstanceByAccNbr(paramsList);
				
			}else{
				//�����ѯ�ͻ�����Ʒʵ�������
				ArrayList paramsList = new ArrayList();
				paramsList.add(custId);
				paramsList.add(custId);
				paramsList.add(custId);
				//paramsList.add((currentPages*pageCount));     
				//paramsList.add(((currentPages-1)*pageCount)); 
				
				CustReceptionDAO custReceptionDao = new CustReceptionDAO();
				//��ѯ�ͻ�������Ʒʵ����Ϣ��
				offerList = custReceptionDao.showOffInstance( paramsList);
				
				
			}
		}
		
		//���巵�ص����ṹ�����顣
		ArrayList instanceList = new ArrayList();
		instanceList = setOfferInstanceTree(offerList);
		
		HashMap resultMap = new HashMap();
		resultMap.put("instanceData",instanceList);
		//resultMap.put("currentPages", currentPages);
		//resultMap.put("ageCount", pageCount);
		
		return resultMap;
	}
	
	/**
	 * ��ѯ������Ʒ�ķ����ṩ
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffService(DynamicDict dto) throws Exception{
		//��ȡ����Ĳ���MAP
		HashMap param = (HashMap) Const.getParam(dto);
		//�������
		String source = (String) param.get("source");
		//��ȡ����
		HashMap paramIn = (HashMap) param.get("param");
		
		//�����ѯ���Ĳ�����
		ArrayList paramsList = new ArrayList();
		
		ArrayList serviceList = new ArrayList();
		if("0".equals(source)){
			//����Ʒʵ����Ϣ���롣
			String offerId = (String) paramIn.get("offerId");
			if(offerId !=null && !"".equals(offerId)){
				paramsList.add(offerId);
			}
			//������ҵ��
			HashMap commonService = new HashMap();
			commonService.put("id", "1");
			commonService.put("name", "�¹�");
			serviceList.add(commonService);
			
			CustReceptionDAO custReceptionDao = new CustReceptionDAO();
			
			//�ж�����Ʒʵ����Ϣѡ�еĽڵ�����͡�
			String instanceType = (String) paramIn.get("instanceType");
			//���ѡ�еĽڵ����ײ�����Ʒ����ֻ����ײ�����Ʒ��ҵ��
			if("10C".equals(instanceType)){
				if(paramsList.size() > 0){
					//��ѯ����Ʒ�����ṩ���ݡ�
					serviceList.addAll(custReceptionDao.showOffService( paramsList));
				}
			}else{
				//ѡ�еĽڵ��ǻ�������Ʒ���߲�Ʒ������Ҫ�����������Ʒ��ҵ��Ͳ�Ʒ��ҵ��
				//ȡ����Ʒ���
				String productId = (String) paramIn.get("productId");
				paramsList.add(productId);
				if(paramsList.size() > 1){
					//��ѯ����Ʒ�����ṩ���ݡ�
					serviceList.addAll(custReceptionDao.showOffAndProdService( paramsList));
				}
				
			}
		}else if("1/2/".indexOf(source) > -1){
			//��ȡ���ڴ���������;�����������ҵ��
			serviceList= getServiceFromOrd(paramIn, source);
			
		}
		
		ArrayList returnList = new ArrayList();
		ArrayList subList = null;
		//��ѯ�������ṩ�ķ������0���Ž���ƴװ���ݷ��ء�
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
	 * ��ȡ���ڴ���������;�����������ҵ��
	 * @param params
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public ArrayList getServiceFromOrd(HashMap params,String source)throws Exception{
		//��ʱд�����Ժ��ٸĳ����á�
		ArrayList servList = new ArrayList();
		
		String instanceId = (String) params.get("instanceId");
		
		if("1".equals(source)){
			//���ڴ�����
			if(instanceId ==null || "".equals(instanceId)){
				//Ϊ�գ�ѡ�е��ǿͻ������Ľڵ㡣
				//ȡ��ҵ��
				HashMap deleteMap = new HashMap();
				deleteMap.put("id", "-3");
				deleteMap.put("name", "ȡ������");
				servList.add(deleteMap);
			}else{
				//����ҵ��
				HashMap changeMap = new HashMap();
				changeMap.put("id", "-1");
				changeMap.put("name", "����");
				servList.add(changeMap);
				//ȡ��ҵ��
				HashMap deleteMap = new HashMap();
				deleteMap.put("id", "-3");
				deleteMap.put("name", "ȡ������");
				servList.add(deleteMap);
			}
		}else if("2".equals(source)){
			//��;����
			if(instanceId ==null || "".equals(instanceId)){
				//Ϊ�գ�ѡ�е��ǿͻ������Ľڵ㡣
				//����ҵ��
				HashMap cancelMap = new HashMap();
				cancelMap.put("id", "-2");
				cancelMap.put("name", "����");
				servList.add(cancelMap);
			}else{
				//����ҵ��
				HashMap changeMap = new HashMap();
				changeMap.put("id", "-1");
				changeMap.put("name", "����");
				servList.add(changeMap);
				//����ҵ��
				HashMap cancelMap = new HashMap();
				cancelMap.put("id", "-2");
				cancelMap.put("name", "����");
				servList.add(cancelMap);
			}
		}
		
		return servList;
	}
	
	/**
	 * ��ѯ�ͻ��Ĺ��̵���Ϣ�� 
	 * source��1��������������2��������;������
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showCustOrdInfo(DynamicDict dto) throws Exception{
		//��ȡ����Ĳ���
		HashMap paramMap = (HashMap) Const.getParam(dto);
		//�������
		String source = (String) paramMap.get("source");
		//��ȡ����
		String custId = (String) paramMap.get("custId");
		//��ǰ��ѯҳ��
		int currentPages =  ((Integer)paramMap.get("currentPages")).intValue();
		//ÿҳ��ʾ����
		int pageCount =  ((Integer)paramMap.get("pageCount")).intValue();
		
		HashMap resultMap = queryCustAccept(custId,source,currentPages,pageCount);
		
		return resultMap;
	}
	
	/**
	 * ��ѯ���ڴ���������Ϣ��
	 * @param custId
	 * @param stateWhere  ����״̬��ѯ��������
	 * @return
	 * @throws Exception
	 */
	public HashMap queryCustAccept(String custId,String source,int currentPages,int pageCount) throws Exception{
		//���巵�ص����ṹ�����顣 
		ArrayList orderTreeList = new ArrayList();
		
		ArrayList paramsAsk = new ArrayList();
		paramsAsk.add(custId);
		paramsAsk.add((currentPages*pageCount)+"");     //��ѯ����λ��
		paramsAsk.add(((currentPages-1)*pageCount)+""); //��ѯ��ʼλ��
		ArrayList askList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//���ݿͻ�������ţ�����ͻ������µ���Ҫ������
		String stateWhere = "";
		if("1".equals(source)){
			//���ڴ�����
			askList = (ArrayList) custReceptionDao.queryAcceptAskByCustId(paramsAsk);
			stateWhere = " and state = '100' ";
		}else if("2".equals(source)){
			//��;����
			askList = (ArrayList) custReceptionDao.queryNotFinishedAskByCustId(paramsAsk);
			stateWhere = " and state <> '100' ";
		}
		Iterator askListIter = askList.iterator();
		while(askListIter.hasNext()){
			//���嶨�����ݵĽڵ㡣
			HashMap childrenMap = new HashMap();
			//��ȡ�ڵ���Ϣ��
			ordNotesInfo(askListIter,childrenMap);
			
			//�����ӽڵ��е��ӽڵ㡣
			ArrayList subChildrenList = new ArrayList();
			childrenMap.put("children", subChildrenList);
			
			ArrayList subParamsAsk = new ArrayList();
			subParamsAsk.add(childrenMap.get("ask_id"));
			
			ArrayList subAskList = new ArrayList();
			OrdAskDAO askListDao= new OrdAskDAO();
			//���ݿͻ�������ţ�����ͻ������µ���Ҫ������
			subAskList = (ArrayList) askListDao.getAskListByWhere(" and ord_id <> ask_id and ask_id = ? " + stateWhere +
							" order by instance_type desc ", subParamsAsk);
			Iterator subAskListIter = subAskList.iterator();
			while(subAskListIter.hasNext()){
				//���嶨�����ݵĽڵ㡣
				HashMap subChildrenMap = new HashMap();
				//��ȡ�ڵ���Ϣ��
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
	 * ��װ��ѯ�����Ľڵ���Ϣ��
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
		String offer_name = "";  //����Ʒ\��Ʒ���ơ�
		
		if("10A".equals(instance_type)){
			//��Ʒ���������Ʒ��𡢲�Ʒ������Ϣ��
			OrdServDAO ordServDao = new OrdServDAO();
			ArrayList ordServList = new ArrayList();
			//��ȡ��Ʒ�������ʶ�������ݡ�
			ordServList = (ArrayList)ordServDao.getOrdServByServId(instance_id, ord_id);
			Iterator ordServIter = ordServList.iterator();
			if(ordServIter.hasNext()){
				HashMap ordServMap = (HashMap)ordServIter.next();
				acc_nbr = (String) ordServMap.get("acc_nbr");
				offer_name = (String)ordServMap.get("product_name");
			}
			
		}else if("10B/10C/".indexOf(instance_type) > -1){
			//����Ʒ�ඨ��,��������Ʒ���id�������Ʒ���ơ�
			CustReceptionDAO custReceptionDao = new CustReceptionDAO();
			ArrayList offerList = new ArrayList();
			offerList = custReceptionDao.queryProductOfferByOfferId(instance_type_id);
			Iterator offerIter = offerList.iterator();
			if(offerIter.hasNext()){
				HashMap offerMap = (HashMap)offerIter.next();
				offer_name = (String)offerMap.get("offer_name");
			}
		}
		
		//��ѯ�������ṩ���ơ�
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
	 * ����ҳ�洫�����Ϣ����ѯ�ͻ���
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getCustList(DynamicDict dto)throws Exception{
		//��ȡ����Ĳ���
		HashMap paramMap = (HashMap) Const.getParam(dto);
		String lanId = (String) paramMap.get("lanId");
		// D��U����ͨ�������ѯ
		String searchType = (String) paramMap.get("searchType");
		String searchValue = (String) paramMap.get("searchValue");
		Integer pageIndex = (Integer) paramMap.get("pageIndex");
		Integer pageSize = (Integer) paramMap.get("pageSize");
		
		//���ÿͻ�����ģ��Ĵ��������
		String search_type = "";
		String search_value = "";
		//�жϲ�ѯ���ͣ��Ƿ���Ҫ������Ӧ��ת����
		if("/B/C".indexOf(searchType) == -1){
			//ҳ���д���Ĳ�ѯ���Ͳ��ǿͻ����ơ�֤�����룬����Ҫ������ת���ɶ�Ӧ�Ĳ�ѯֵ��
			HashMap searchMap = getSearchCon(lanId,searchType,searchValue);
			if( searchMap.isEmpty() || ("".equals((String) searchMap.get("searchValue")))){
				//�����ѯ������ֱ�ӷ��ؿյĲ�ѯ�������
				return new PageModel();
			}else{
				search_type = (String) searchMap.get("searchType");
				search_value = (String) searchMap.get("searchValue");
			}
			
		}else{
			//����Ҫת����ֱ������ֵ��
			search_type = searchType;
			search_value = searchValue;
		}
		
		HashMap params = new HashMap();
		params.put("lan_id", lanId);
		params.put("search_type", search_type);
		params.put("search_value", search_value);
		params.put("pageIndex", pageIndex);
		params.put("pageSize", pageSize);
		//���ÿͻ�����ģ�飬��ѯ�ͻ���
		Object result = ServiceManager.callJavaBeanService(
				CustomerActions.CUST_ACTION, CustomerActions.QRY_CUST_INFO, params);
		return (PageModel) result ;
		
	}
	
	/**
	 * ����ҳ�洫��Ĳ�ѯ������ת���ɿ��Ե��ÿͻ�����ģ��Ĳ�ѯ������
	 * @param lanId
	 * @param searchType
	 * @param searchValue
	 * @return
	 * @throws Exception
	 */
	public HashMap getSearchCon(String lanId,String searchType,String searchValue)throws Exception{
		//���壬��Ҫ���صĲ�ѯ���ͺͲ�ѯֵ��
		HashMap searchMap = new HashMap();
		searchMap.put("searchType", "");
		searchMap.put("searchValue", "");
		
		if("D/E/".indexOf(searchType) >-1 ){
			//ͨ�����롢�����ʺŽ��в�ѯ,��Ҫ�����Ӧ�Ŀͻ���ʶ��
			ArrayList whereCondParams = new ArrayList();
			whereCondParams.add(searchValue);
			whereCondParams.add(lanId);
			ServDAO servDao = new ServDAO();
			//��ѯ�����µ����ݡ�
			ArrayList servList = (ArrayList) servDao.findByCond(" and acc_nbr =? and lan_id = ? ", whereCondParams);
			
			//�ж��Ƿ���Բ�ѯ����Ӧ�Ŀͻ���ʶ
			Iterator servIter = servList.iterator();
			if(servIter.hasNext()){
				Map servMap = (Map) servIter.next();
				String custId = (String) servMap.get("cust_id");
				//������Ҫ��ѯ���ͺͲ�ѯֵ��
				searchMap.put("searchValue", custId);
				searchMap.put("searchType", "A");
			}
			return searchMap;
			
		}else if("F/".indexOf(searchType) > -1){
			//ͨ�������Ž��в�ѯ����Ҫ�����Ӧ�Ŀͻ���ʶ	
			OrdAskDAO ordAskListDao = new OrdAskDAO();
			OrdCustOrderVO ordCustOrderVo = ordAskListDao.getCustOrderByOrdId(searchValue);
			
			if( ordCustOrderVo != null){
				//������Ҫ��ѯ���ͺͲ�ѯֵ��
				searchMap.put("searchValue", ordCustOrderVo.getCust_id());
				searchMap.put("searchType", "A");
			}
			
			return searchMap;
		}
		
		return searchMap;
	}
	
	/**
	 * ���ݿͻ���Ų�ѯ���ͻ���������Ϣ��
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel getAcctList(DynamicDict dto)throws Exception{
		//��ȡ����Ĳ���
		HashMap paramMap = (HashMap) Const.getParam(dto);
		
		//���ÿͻ�����ģ�飬��ѯ�ͻ����ʻ���
		PageModel acctPageModel = (PageModel) ServiceManager.callJavaBeanService(
				CustomerActions.ACCT_ACTION, CustomerActions.QRY_ACCT_INFO, paramMap);
		
		//��Ҫ��acctPageModel�еĴ������ı�ʶ������ɴ�����롣
		ArrayList acctList = (ArrayList) acctPageModel.getList();
		ServDAO servDao = new ServDAO();
		Iterator acctIter = acctList.iterator();
		while(acctIter.hasNext()){
			Map acctMap = (Map) acctIter.next();
			String servId = (String) acctMap.get("serv_id");
			//����绰�ĺ���
			String acc_nbr = ""; 
			//����绰�ĵ绰��ʶ���ڲŲ����绰�ĺ��롣
			if(servId != null && !"".equals(servId)){
				HashMap keyCondMap = new HashMap();
				keyCondMap.put("serv_id", servId);
				//��ѯ�����µ����ݡ�
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
	 * �Ժ�����е�����ѯ
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
		//�Ȳ��ײ��Ż�����Ʒ
		ArrayList offerList = new ArrayList(); 
		offerList = custReceptionDao.queryYHOfferByAccNbr(accNbr, custId, pageCount, currentPages,"1");
		if(offerList.size() == 0 ){
			//�ײ��Ż�����ƷΪ�գ�����ײͻ�������Ʒ
			offerList = custReceptionDao.queryYHOfferByAccNbr(accNbr, custId, pageCount, currentPages,"0");
		}
		
		return offerList;
	}
	
	/**
	 * ��������Ʒʵ���������ݡ�
	 * @param offerList
	 * @return
	 * @throws Exception
	 */
	public ArrayList setOfferInstanceTree(ArrayList offerList) throws Exception{
		//���巵�ص����ṹ�����顣
		ArrayList instanceList = new ArrayList();
		
		//ѭ��ȡ������Ʒ��Ϣ������ȡ������Ʒ�µĲ�Ʒ
		Iterator offerIter = offerList.iterator();
		while(offerIter.hasNext()){
			HashMap offerInfoMap = (HashMap) offerIter.next();
			
			String product_offer_instance_id = (String) offerInfoMap.get("product_offer_instance_id");
			String offer_id = (String) offerInfoMap.get("offer_id");
			String offer_kind =(String) offerInfoMap.get("offer_kind");
			String offer_name =(String) offerInfoMap.get("offer_name");
			String prod_id = (String) offerInfoMap.get("product_id"); //��������Ʒ���ж�Ӧ�Ĳ�Ʒ���ID
			String serv_id = (String) offerInfoMap.get("serv_id"); //��������Ʒ���ж�Ӧ�Ĳ�Ʒʵ��ID
			String instance_type = "10C"; //Ĭ�����ײ�����Ʒ
			if("0".equals(offer_kind)){
				instance_type = "10B";  //��������Ʒ
			}
			
			//����ʵ��������Ʒ�ڵ�����ݡ�
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
			
			//�����ӽڵ㡣
			ArrayList childrenList = new ArrayList();
			instanceMap.put("children", childrenList);
			
			instanceList.add(instanceMap);
			
			ArrayList offerInstanceList = new ArrayList();
			offerInstanceList.add(product_offer_instance_id);
			
			CustReceptionDAO custReceptionDao = new CustReceptionDAO();
			//��ѯ�ͻ�������Ʒ�µ��û���ʵ����Ϣ��
			ArrayList servList = custReceptionDao.showServInstance(offerInstanceList);
			Iterator servIter = servList.iterator();
			while (servIter.hasNext()){
				HashMap servMap = (HashMap) servIter.next();
				String instance_id = (String) servMap.get("instance_id");
				String product_id = (String) servMap.get("product_id");
				String product_name = (String) servMap.get("product_name");
				String acc_nbr = (String) servMap.get("acc_nbr");
				String comp_inst_id = (String) servMap.get("comp_inst_id"); //��Ʒ�ĸ�����Ʒʵ����ʶ������������Ʒʵ������
				String product_offer_id = (String) servMap.get("product_offer_id"); //��Ʒ�Ļ�������Ʒ�Ĺ���ʶ
				
				//��ȡ��Ʒ״̬********************************************
				
				//�����Ʒ���ڵ�����ݡ�
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
	 * ��ѯʵ������ϸ��Ϣ
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap showOffInstanceDetail(DynamicDict dto)throws Exception{
		HashMap returnMap = new HashMap();
		//��ȡ����Ĳ���
		HashMap paramMap = (HashMap) Const.getParam(dto);
		String instanceType = (String) paramMap.get("instanceType");
		if("10A/10B/".indexOf(instanceType) > -1){
			//��Ʒ�ڵ㣬ȡ����Ʒ����ϸ��Ϣ��
			showServDetail(paramMap,returnMap);
			
		}
		
		if("10B/10C/".indexOf(instanceType) > -1){
			//����Ʒ����ѯ����Ӧ�Ŀ�ѡ����Ϣ��
			showOffDetail(paramMap,returnMap);
		}
			
		return returnMap;
	}
	
	/**
	 * ��ѯ��Ʒ����ϸ��Ϣ��
	 * @param paramMap
	 * @return
	 * @throws Exception
	 */
	public void showServDetail(HashMap paramMap,HashMap detailMap)throws Exception{
		//��ѯ��Ʒ�ĸ�����Ʒ��
		ArrayList whereCondParams = new ArrayList();
		String servId = (String) paramMap.get("servId");
		whereCondParams.add(servId);
		ArrayList servProdList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//��ȡ��Ʒ�ĸ�����Ʒ��
		servProdList = custReceptionDao.queryServProductName(whereCondParams);
		//���ڴ�Ÿ�����Ʒ
		StringBuffer servProdBuf = new StringBuffer();
		if(servProdList != null && servProdList.size() >0){
			Iterator servProdIter = servProdList.iterator();
			servProdBuf.append("������Ʒ��");
			while(servProdIter.hasNext()){
				Map servProdMap = (Map) servProdIter.next();
				String product_name = (String) servProdMap.get("product_name");
				servProdBuf.append(product_name+";   ");
			}
		}
		
		detailMap.put("serv_product", servProdBuf.toString());
	}
	
	/**
	 * ��ѯ����Ʒ����ϸ��Ϣ
	 * @param paramMap
	 * @param detailMap
	 * @throws Exception
	 */
	public void showOffDetail(HashMap paramMap,HashMap detailMap)throws Exception{
		//��ѯ����Ʒ�Ŀ�ѡ��
		String offerInstanceId = (String) paramMap.get("instanceId");
		ArrayList whereCondParams = new ArrayList();
		whereCondParams.add(offerInstanceId);
		ArrayList selectPackageList = new ArrayList();
		CustReceptionDAO custReceptionDao = new CustReceptionDAO();
		//��ȡ����Ʒ�Ŀ�ѡ��
		selectPackageList = custReceptionDao.queryOfferSelectPackage(whereCondParams);
		//���ڴ�ſ�ѡ����
		StringBuffer selectPackageBuf = new StringBuffer();
		if(selectPackageList != null && selectPackageList.size() > 0){
			Iterator selectPackageIter = selectPackageList.iterator();
			selectPackageBuf.append("��ѡ��ѡ����");
			while(selectPackageIter.hasNext()){
				Map selectPackageMap = (Map) selectPackageIter.next();
				String offer_name = (String) selectPackageMap.get("offer_name");
				selectPackageBuf.append(offer_name+";   ");
			}
		}
		detailMap.put("offer_name", selectPackageBuf.toString());
	}
	
	/**
	 * ��ȡ���С�
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
	 * ��ѯ�ͻ�����Ҫ��ȡ�Ŀͻ������Ϣ.
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public HashMap getInitInfoByCustId(DynamicDict dto)throws Exception{
		Map paramMap = (Map)dto.getValueByName("parameter") ;
		String cust_id = (String) paramMap.get("cust_id");
		
		//���ݿͻ���ţ���ȡδȷ�ϵĿͻ������š�
		String cust_ord_id = getCustOrdId(cust_id);
		
		HashMap initMap = new HashMap();
		initMap.put("cust_ord_id", cust_ord_id);
		return initMap;
	}
	
	/**
	 * ���ݿͻ���ʶ�����߿ͻ��������
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
		//��ѯ��δȷ�ϵĿͻ�������
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
