package com.ztesoft.vsop.ordermonitor.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordermonitor.dao.WoOrderErrorLogDAO ;

public class WoOrderErrorLogBO extends DictAction  {
	public boolean insertWoOrderErrorLog(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderErrorLog = (Map) param.get("WoOrderErrorLog") ;
		
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		boolean result = dao.insert(WoOrderErrorLog) ;
		return result ;
	}

	
	public boolean updateWoOrderErrorLog(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderErrorLog = (Map) param.get("WoOrderErrorLog") ;
		String keyStr = "error_serial_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoOrderErrorLog,  keyStr) ;
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		boolean result = dao.updateByKey( WoOrderErrorLog, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoOrderErrorLogData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "order_id")){
			whereCond.append(" and order_id = ? ");
			para.add(Const.getStrValue(param , "order_id")) ;
		}
		whereCond.append(" order by error_serial_id ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderErrorLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoOrderErrorLogByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderErrorLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoOrderErrorLogDAO dao = new WoOrderErrorLogDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
