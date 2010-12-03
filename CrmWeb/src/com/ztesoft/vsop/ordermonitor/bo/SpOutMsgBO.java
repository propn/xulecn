package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgDAO ;

public class SpOutMsgBO extends DictAction  {
	public boolean insertSpOutMsg(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsg = (Map) param.get("SpOutMsg") ;
		
		SpOutMsgDAO dao = new SpOutMsgDAO();
		boolean result = dao.insert(SpOutMsg) ;
		return result ;
	}

	
	public boolean updateSpOutMsg(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsg = (Map) param.get("SpOutMsg") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SpOutMsg,  keyStr) ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		boolean result = dao.updateByKey( SpOutMsg, keyCondMap );
		
		return result ;
	}
	
	public Map getInitPara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		Map result = dao.getInitPara(param.get("refresh_time").toString(),param.get("alarm").toString()) ;
		return result;
	}
	public PageModel searchSpOutMsgData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "prod_no")){
			whereCond.append(" and t.prod_no = ? ");
			para.add(Const.getStrValue(param , "prod_no")) ;
		}
		if(Const.containStrValue(param , "lan_id")){
			whereCond.append(" and t.lan_id = ? ");
			para.add(Const.getStrValue(param , "lan_id")) ;
		}
		if(Const.containStrValue(param , "in_start_time")){
			String in_start_time = Const.getStrValue(param , "in_start_time");
			in_start_time += " 00:00:00";
			whereCond.append(" and t.in_time > to_date('"+in_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "in_end_time")){
			String in_end_time = Const.getStrValue(param , "in_end_time");
			in_end_time += " 23:59:59";
			whereCond.append(" and t.in_time < to_date('"+in_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "order")){
			whereCond.append(" and t.order_id = ? ");
			para.add(Const.getStrValue(param , "order")) ;
		}
		if(Const.containStrValue(param , "sys")){
			String sys = Const.getStrValue(param , "sys");
			whereCond.append(" and t.sys in ( "+sys+")");
		}
		if(Const.containStrValue(param , "deal_start_time")){
			String deal_start_time = Const.getStrValue(param , "deal_start_time");
			deal_start_time += " 00:00:00";
			whereCond.append(" and t.deal_time > to_date('"+deal_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "deal_end_time")){
			String deal_end_time = Const.getStrValue(param , "deal_end_time");
			deal_end_time += " 23:59:59";
			whereCond.append(" and t.deal_time < to_date('"+deal_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");

		}
		if(Const.containStrValue(param , "state")){
			String state = Const.getStrValue(param , "state");
			whereCond.append(" and t.state in ( "+state+")");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpOutMsgDAO dao = new SpOutMsgDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	public List searchMsgStateData(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		List result = dao.searchState(Const.getStrValue(keyCondMap , "style"));
		return result;
	}
	public boolean changeState(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto);
		SpOutMsgDAO dao = new SpOutMsgDAO();
		boolean result = dao.changeState(Const.getStrValue(keyCondMap , "id"));
		return result;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOutMsgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOutMsgByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOutMsgDAO dao = new SpOutMsgDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutMsgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgDAO dao = new SpOutMsgDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
