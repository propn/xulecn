package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DataTranslate;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.ServiceList;
import com.ztesoft.common.dict.ServiceManager;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderDAO ;

public class WoNeOrderBO extends DictAction  {
	public boolean insertWoNeOrder(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrder = (Map) param.get("WoNeOrder") ;
		
		WoNeOrderDAO dao = new WoNeOrderDAO();
		boolean result = dao.insert(WoNeOrder) ;
		return result ;
	}

	
	public boolean updateWoNeOrder(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrder = (Map) param.get("WoNeOrder") ;
		String keyStr = "ne_order_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoNeOrder,  keyStr) ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		boolean result = dao.updateByKey( WoNeOrder, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoNeOrderData(DynamicDict dto ) throws Exception {
		//条件处理
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

		//whereCond.append(" order by ne_order_id ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoNeOrderDAO dao = new WoNeOrderDAO();
		//PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		String querySQL = dao.getSelectSQL() + whereCond.toString() + " order by ne_order_id ";
		String countSQL = dao.getSelectCountSQL() + whereCond.toString();
		PageModel result=dao.searchByCond(querySQL, countSQL, para, pageIndex, pageSize);

		return result ;
	}
	public boolean successWoNeOrder(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		String ne_order_id = Const.getStrValue(keyCondMap , "ne_order_id");
		String state = Const.getStrValue(keyCondMap , "state");
		String is_success = Const.getStrValue(keyCondMap , "is_success");
		boolean result = dao.successWoNeOrder(ne_order_id,state,is_success);
		return result ;
	}
	public boolean changeState(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		boolean result = dao.changeState(Const.getStrValue(param , "ne_order_id"), Const.getStrValue(param , "state") );
		
		return result ;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoNeOrderById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoNeOrderByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoNeOrderDAO dao = new WoNeOrderDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoNeOrderById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 取要重送业务平台子工单信息表
	 * 
	 * @param threadId
	 * @param size
	 * @return
	 */
	public List getReSendWoNeOrderList(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto) ;
		String args=Const.getStrValue(keyCondMap , "args");
		String stateCodes="";
		String deviceId="";
		if(null!=args){
			int beginIndex = args.indexOf("|")+ "|".length();
			int endIndex = args.indexOf("|");
			if(endIndex>0){
				deviceId=args.substring(0, endIndex);
				stateCodes=args.substring(beginIndex, args.length());
			}
			
		}
		
		WoNeOrderDAO dao = new WoNeOrderDAO();
		List ret = dao.getReSendWoNeOrderList(stateCodes,deviceId);
		return ret;
	}
	
	public boolean updateWoNeOrderWhenFail(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderDAO dao = new WoNeOrderDAO();
		String neOrderId = Const.getStrValue(keyCondMap , "neOrderId");
		String sendTimes = Const.getStrValue(keyCondMap , "sendTimes");
		String stateCode = "";
		if(Const.containStrValue(keyCondMap , "stateCode")){
			stateCode = Const.getStrValue(keyCondMap , "stateCode");
		}
		
		 return dao.updateWoNeOrderSendTimesWhenFail(sendTimes, neOrderId,stateCode);
	}
}
