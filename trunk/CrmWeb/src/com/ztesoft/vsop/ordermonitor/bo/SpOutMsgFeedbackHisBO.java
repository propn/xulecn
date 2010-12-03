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

import com.ztesoft.vsop.ordermonitor.dao.SpOutMsgFeedbackHisDAO ;

public class SpOutMsgFeedbackHisBO extends DictAction  {
	
	public boolean insertSpOutMsgFeedbackHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgFeedbackHis = (Map) param.get("SpOutMsgFeedbackHis") ;
		
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		boolean result = dao.insert(SpOutMsgFeedbackHis) ;
		return result ;
	}

	
	public boolean updateSpOutMsgFeedbackHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutMsgFeedbackHis = (Map) param.get("SpOutMsgFeedbackHis") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(SpOutMsgFeedbackHis,  keyStr) ;
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		boolean result = dao.updateByKey( SpOutMsgFeedbackHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpOutMsgFeedbackHisData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "prod_no")){
			whereCond.append(" and t.prod_no like ? ");
			para.add("%"+Const.getStrValue(param , "prod_no")+"%") ;
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
			whereCond.append(" and t.int_sys_id in ( "+sys+")");
		}
		if(Const.containStrValue(param , "deal_start_time")){
			String deal_start_time = Const.getStrValue(param , "deal_start_time");
			deal_start_time += " 00:00:00";
			whereCond.append(" and t.feeback_time > to_date('"+deal_start_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "deal_end_time")){
			String deal_end_time = Const.getStrValue(param , "deal_end_time");
			deal_end_time += " 23:59:59";
			whereCond.append(" and t.feeback_time < to_date('"+deal_end_time+"',"+DatabaseFunction.getDataFormat(2)+") ");
		}
		if(Const.containStrValue(param , "state")){
			String state = Const.getStrValue(param , "state");
			whereCond.append(" and t.state in ( "+state+")");
		}

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOutMsgFeedbackHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOutMsgFeedbackHisByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutMsgFeedbackHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutMsgFeedbackHisDAO dao = new SpOutMsgFeedbackHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
