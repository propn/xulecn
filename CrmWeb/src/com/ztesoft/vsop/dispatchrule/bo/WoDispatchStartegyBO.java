package com.ztesoft.vsop.dispatchrule.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.dispatchrule.dao.WoDispatchStartegyDAO ;

public class WoDispatchStartegyBO extends DictAction  {
	
	public boolean insertWoDispatchStartegy(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchStartegy = (Map) param.get("WoDispatchStartegy") ;
		
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		boolean result = dao.insert(WoDispatchStartegy) ;
		return result ;
	}

	
	public boolean updateWoDispatchStartegy(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchStartegy = (Map) param.get("WoDispatchStartegy") ;
		String keyStr = "dispatch_rule_id,item_code";
		Map keyCondMap  = Const.getMapForTargetStr(WoDispatchStartegy,  keyStr) ;
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		boolean result = dao.updateByKey( WoDispatchStartegy, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchStartegyData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "dispatch_rule_id")){
			whereCond.append(" and d.dispatch_rule_id = ? ");
			para.add(Const.getStrValue(param , "dispatch_rule_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoDispatchStartegyById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoDispatchStartegyByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchStartegyById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoDispatchStartegyDAO dao = new WoDispatchStartegyDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
