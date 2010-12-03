package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.ConstantsState;
import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderHisDAO;
import com.ztesoft.vsop.ordermonitor.webservice.client.ActiveSVClient;

public class WoNeOrderHisBO extends DictAction  {
	public boolean insertWoNeOrderHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderHis = (Map) param.get("WoNeOrderHis") ;
		
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		boolean result = dao.insert(WoNeOrderHis) ;
		return result ;
	}

	
	public boolean updateWoNeOrderHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderHis = (Map) param.get("WoNeOrderHis") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(WoNeOrderHis,  keyStr) ;
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		boolean result = dao.updateByKey( WoNeOrderHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoNeOrderHisData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "user_type")){
			whereCond.append(" and o.prod_id = ? ");
			para.add(Const.getStrValue(param , "user_type")) ;
		}
		if(Const.containStrValue(param , "prod_no")){
			whereCond.append(" and o.nbr = ? ");
			para.add(Const.getStrValue(param , "prod_no")) ;
		}
		if(Const.containStrValue(param , "lan_code")){
			whereCond.append(" and o.area_id = ? ");
			para.add(Const.getStrValue(param , "lan_code")) ;
		}
		if(Const.containStrValue(param , "in_start_time")){
			String in_start_time = Const.getStrValue(param , "in_start_time");
			in_start_time += " 00:00:00";
			whereCond.append(" and t.create_date > to_date('"+in_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "in_end_time")){
			String in_end_time = Const.getStrValue(param , "in_end_time");
			in_end_time += " 23:59:59";
			whereCond.append(" and t.create_date < to_date('"+in_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "order")){
			whereCond.append(" and o.order_id = ? ");
			para.add(Const.getStrValue(param , "order")) ;
		}
		if(Const.containStrValue(param , "device_id")){
			String device_id = Const.getStrValue(param , "device_id");
			whereCond.append(" and d.device_id in ( "+device_id+")");
		}
		if(Const.containStrValue(param , "deal_start_time")){
			String deal_start_time = Const.getStrValue(param , "deal_start_time");
			deal_start_time += " 00:00:00";
			whereCond.append(" and t.finish_date > to_date('"+deal_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "deal_end_time")){
			String deal_end_time = Const.getStrValue(param , "deal_end_time");
			deal_end_time += " 23:59:59";
			whereCond.append(" and t.finish_date < to_date('"+deal_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");

		}
		if(Const.containStrValue(param , "state")){
			String state = Const.getStrValue(param , "state");
			state =state.replaceAll(",", "','");
			state="'"+state+"'";
			whereCond.append(" and t.state_code in ( "+state+")");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		//whereCond.append(" order by ne_order_id ");
		//����DAO����
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		//PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		String querySQL = dao.getSelectSQL() + whereCond.toString() + " order by ne_order_id ";
		String countSQL = dao.getSelectCountSQL() + whereCond.toString();
		PageModel result=dao.searchByCond(querySQL, countSQL, para, pageIndex, pageSize);
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getWoNeOrderHisById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoNeOrderHisByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	

	public boolean deleteWoNeOrderHisById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
/*	//�ӹ����ֹ��ص�
	public Map subOrderBackByMan(DynamicDict dto)  throws Exception {
		//����DAO���뼤��WEBSERVICE����ͳһ�ͻ���
		String searchStr = "<ResultCode>0</ResultCode>";
		String neOrderId="";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		Map keyCondMap = Const.getParam(dto)  ;
		Map result = new HashMap();
		String activeType = (String)keyCondMap.get("activeType");
		String mainMsg = (String)keyCondMap.get("mainMsg");
		String helpMsg = (String)keyCondMap.get("helpMsg");
		beginIndex = helpMsg.indexOf("<ne_order_id>")+ "<ne_order_id>".length();
		endIndex = helpMsg.indexOf("</ne_order_id>");
		neOrderId=helpMsg.substring(beginIndex, endIndex);
		try{
		ActiveSVClient activeSVClient = ActiveSVClient.getInstance();
		String res = activeSVClient.active(activeType, mainMsg, helpMsg);
		if(null!=res){
			successFlag = res.indexOf(searchStr);
			beginIndex = res.indexOf("<ResultMsg>")+ "<ResultMsg>".length();
			endIndex = res.indexOf("</ResultMsg>");
			if (successFlag == 0) {
				result.put("res", "�ӹ���:"+neOrderId+"-"+res.substring(beginIndex, endIndex));
			} else {
				result.put("res", "�ӹ���:"+neOrderId+"-" + res.substring(beginIndex, endIndex));
			}
			}
		}catch(Exception e){
			result.put("res", "�ӹ���:"+neOrderId +"-�ص�ʧ��"+ e.getMessage());
		}
		return result ;
	}
	//����ҵ��ƽ̨
	public Map reSendPlatforms(DynamicDict dto)  throws Exception {
		//����DAO���뼤��WEBSERVICE����ͳһ�ͻ���
		String neOrderId="";
		String searchStr = "<ResultCode>0</ResultCode>";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		Map keyCondMap = Const.getParam(dto)  ;
		Map result = new HashMap();
		String activeType = (String)keyCondMap.get("activeType");
		String mainMsg = (String)keyCondMap.get("mainMsg");
		String helpMsg = (String)keyCondMap.get("helpMsg");
		beginIndex = helpMsg.indexOf("<ne_order_id>")+ "<ne_order_id>".length();
		endIndex = helpMsg.indexOf("</ne_order_id>");
		neOrderId=helpMsg.substring(beginIndex, endIndex);
		String res ="";
		try{
		ActiveSVClient activeSVClient = ActiveSVClient.getInstance();
		 res = activeSVClient.active(activeType, mainMsg, helpMsg);
			if(null!=res){
				successFlag = res.indexOf(searchStr);
				beginIndex = res.indexOf("<ResultMsg>")+ "<ResultMsg>".length();
				endIndex = res.indexOf("</ResultMsg>");
				if (successFlag == 0) {
					result.put("res", "�ӹ���:"+neOrderId+"-"+res.substring(beginIndex, endIndex));
				} else {
					result.put("res", "�ӹ���:"+neOrderId +"-"+ res.substring(beginIndex, endIndex));
				}
			}
		}catch(Exception e){
			result.put("res", "�ӹ���:"+neOrderId +"-����ʧ��"+ e.getMessage());
		}

		return result ;
	}*/
	
	//�ӹ����ֹ��ص�
	public List subOrderBackByMan(DynamicDict dto)  throws Exception {
		
		String searchStr = "<ResultCode>0</ResultCode>";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;
		
		Map param = Const.getParam(dto)  ;
		String activeType = ConstantsState.Active_Type_NeOrderBack;//�ӹ����˹��ص�
		String helpMsg = "";
		String strTemp = "";
		ActiveSVClient asvClient = ActiveSVClient.getInstance();
		
		String neorderIds = Const.getStrValue(param, "neorderIds");
		String ruleStr[] = neorderIds.split(",");
		List resultList = new ArrayList();
		//����DAO���뼤��WEBSERVICE����ͳһ�ͻ���
		for (int i = 0; i < ruleStr.length; i++) {
			helpMsg = "<ne_order_id>" + ruleStr[i] + "</ne_order_id>";
			Map map = new HashMap();
			try{
				// ����ne_order_id ѭ�������˹��ص��ķ���
				strTemp = asvClient.active(activeType, "", helpMsg);
				successFlag = strTemp.indexOf(searchStr);

				map.put("neOrderId", ruleStr[i]);
				// �ڷ��ؽ���в���"<ResultCode>0</ResultCode>"���жϲ����Ƿ�ɹ�
			if (!(successFlag == -1)) {
				map.put("resultCode", "�����ɹ�");
			} else {
				map.put("resultCode", "����ʧ��");
			}
				beginIndex = strTemp.indexOf("<ResultMsg>")
				+ "<ResultMsg>".length();
				endIndex = strTemp.indexOf("</ResultMsg>");
				String tempMsg="";
			if(beginIndex!=-1&&endIndex!=-1&&endIndex>beginIndex){
				tempMsg=(String) strTemp.substring(beginIndex, endIndex);
			}
				map.put("resultMsg", tempMsg);
				resultList.add(map);
			}catch(Exception e){
				map.put("neOrderId", ruleStr[i]);
				map.put("resultCode", "����ʧ��");
				map.put("resultMsg", e.getMessage());
				resultList.add(map);
			}
			
		}
		return resultList;
	}
	//����ҵ��ƽ̨
	public List reSendPlatforms(DynamicDict dto)  throws Exception {

		Map param = Const.getParam(dto)  ;
		String neorderIds = Const.getStrValue(param , "neorderIds");
		if(neorderIds!=null){
			neorderIds=neorderIds.substring(0,neorderIds.lastIndexOf(","));
		}
		
		WoNeOrderHisDAO dao = new WoNeOrderHisDAO();
		StringBuffer whereCond = new StringBuffer() ;
		whereCond.append("SELECT ne_order_id,cmd_content FROM wo_ne_order WHERE 1=1");	
		whereCond.append(" and ne_order_id in ( ").append(neorderIds).append(")");
		List woneorderList = dao.findBySql(whereCond.toString(),null);
		Map woNeOrderMap=new HashMap();
		if(woneorderList!=null){
			for(int i=0;i<woneorderList.size();i++){
				Map tempMap=(Map) woneorderList.get(i);
				woNeOrderMap.put(tempMap.get("ne_order_id"), tempMap.get("cmd_content"));
			}	
		}
		//����DAO���뼤��WEBSERVICE����ͳһ�ͻ���	
		ActiveSVClient activeSVClient = ActiveSVClient.getInstance();
		String ruleStr[] = neorderIds.split(",");
		List resultList = new ArrayList();
		String searchStr = "<ResultCode>0</ResultCode>";
		String activeType = ConstantsState.Active_Type_NeOrderReSendPlatforms;//�ӹ��� ����ҵ��ƽ̨
		String mainMsg = "";
		String helpMsg = "";
		int successFlag = 0;
		int beginIndex = 0;
		int endIndex = 0;

		for (int i = 0; i < ruleStr.length; i++) {
			helpMsg = "<ne_order_id>" + ruleStr[i] + "</ne_order_id>";
			mainMsg=(String) woNeOrderMap.get(ruleStr[i]);
			Map map = new HashMap();
			String strTemp = "";
			try{
				// ����ne_order_id ѭ����������ҵ��ƽ̨�ķ���
				strTemp = activeSVClient.active(activeType, mainMsg, helpMsg);
				successFlag = strTemp.indexOf(searchStr);
				map.put("neOrderId", ruleStr[i]);
				// �ڷ��ؽ���в���"<ResultCode>0</ResultCode>"���жϲ����Ƿ�ɹ�
				if (!(successFlag == -1)) {
					map.put("resultCode", "�����ɹ�");
				} else {
					map.put("resultCode", "����ʧ��");
				}
				beginIndex = strTemp.indexOf("<ResultMsg>")+ "<ResultMsg>".length();
				endIndex = strTemp.indexOf("</ResultMsg>");
				String tempMsg="";
				if(beginIndex!=-1&&endIndex!=-1&&endIndex>beginIndex){
					tempMsg=(String) strTemp.substring(beginIndex, endIndex);
				}
				map.put("resultMsg", tempMsg);
				resultList.add(map);
			}catch (Exception e){
				map.put("neOrderId", ruleStr[i]);
				map.put("resultCode", "����ʧ��");
				map.put("resultMsg", e.getMessage());
				resultList.add(map);
			}
		}
		return resultList;
	}
}