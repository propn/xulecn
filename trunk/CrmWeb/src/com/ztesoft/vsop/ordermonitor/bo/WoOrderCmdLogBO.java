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

import com.ztesoft.vsop.ordermonitor.dao.WoOrderCmdLogDAO ;

public class WoOrderCmdLogBO extends DictAction  {
	public boolean insertWoOrderCmdLog(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderCmdLog = (Map) param.get("WoOrderCmdLog") ;
		
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		boolean result = dao.insert(WoOrderCmdLog) ;
		return result ;
	}

	
	public boolean updateWoOrderCmdLog(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderCmdLog = (Map) param.get("WoOrderCmdLog") ;
		String keyStr = "order_cmd_log_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoOrderCmdLog,  keyStr) ;
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		boolean result = dao.updateByKey( WoOrderCmdLog, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoOrderCmdLogData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "ne_order_id")){
			whereCond.append(" and ne_order_id = ? ");
			para.add(Const.getStrValue(param , "ne_order_id")) ;
		}
		whereCond.append(" order by order_cmd_log_id ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderCmdLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoOrderCmdLogByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderCmdLogById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoOrderCmdLogDAO dao = new WoOrderCmdLogDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
