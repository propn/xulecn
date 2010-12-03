package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoOrderInfoDAO;
import com.ztesoft.vsop.ordermonitor.dao.WoOrderInfoHisDAO ;

public class WoOrderInfoHisBO extends DictAction  {
	public boolean insertWoOrderInfoHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderInfoHis = (Map) param.get("WoOrderInfoHis") ;
		
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		boolean result = dao.insert(WoOrderInfoHis) ;
		return result ;
	}

	
	public boolean updateWoOrderInfoHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderInfoHis = (Map) param.get("WoOrderInfoHis") ;
		String keyStr = "order_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoOrderInfoHis,  keyStr) ;
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		boolean result = dao.updateByKey( WoOrderInfoHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoOrderInfoHisData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "user_type")){
			whereCond.append(" and t.prod_id = ? ");
			para.add(Const.getStrValue(param , "user_type")) ;
		}
		if(Const.containStrValue(param , "prod_no")){
			whereCond.append(" and t.nbr = ? ");
			para.add(Const.getStrValue(param , "prod_no")) ;
		}
		if(Const.containStrValue(param , "lan_code")){
			whereCond.append(" and t.area_id = ? ");
			para.add(Const.getStrValue(param , "lan_code")) ;
		}
		if(Const.containStrValue(param , "in_start_time")){
			String in_start_time = Const.getStrValue(param , "in_start_time");
			in_start_time += " 00:00:00";
			whereCond.append(" and t.receive_date > to_date('"+in_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "in_end_time")){
			String in_end_time = Const.getStrValue(param , "in_end_time");
			in_end_time += " 23:59:59";
			whereCond.append(" and t.receive_date < to_date('"+in_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "order")){
			whereCond.append(" and t.order_id = ? ");
			para.add(Const.getStrValue(param , "order")) ;
		}
		if(Const.containStrValue(param , "sys")){
			String sys = Const.getStrValue(param , "sys");
			whereCond.append(" and t.int_sys_id in ( "+sys+")");
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
		
		
		//调用DAO代码
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	public String getBatchList(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		Map order = dao.findByPrimaryKey( keyCondMap ) ;
		if(order.isEmpty())
			return "";
		String control_str = Const.getStrValue(order , "control_str");
		String order_id = Const.getStrValue(order , "order_id");
		String[] batch = control_str.split(";");
		StringBuffer sbf = new StringBuffer("<items>");
		for (int i = 0; i < batch.length; i++) {
			String batch_str = batch[i];
			batch_str = "'"+batch_str.replaceAll(",", "','")+"'";
			sbf.append("<item batch='激活批次"+(i+1)+"' state=''>");
			List list = dao.getBatchList(batch_str,order_id);
			for (int j = 0; j < list.size(); j++) {
				Map data = (Map) list.get(j);
				String state = "已激活";
				String name = Const.getStrValue(data , "name");
				sbf.append("<item batch='"+name+"' state='"+state+"'></item>");;
			}
			sbf.append("</item>");
		}
		sbf.append("</items>");
		return sbf.toString() ;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderInfoHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoOrderInfoHisByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderInfoHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoOrderInfoHisDAO dao = new WoOrderInfoHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
