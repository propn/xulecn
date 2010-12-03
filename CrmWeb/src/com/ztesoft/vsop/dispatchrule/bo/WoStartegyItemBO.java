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

import com.ztesoft.vsop.dispatchrule.dao.WoStartegyItemDAO ;

public class WoStartegyItemBO extends DictAction  {	
	public boolean insertWoStartegyItem(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoStartegyItem = (Map) param.get("WoStartegyItem") ;
		
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		boolean result = dao.insert(WoStartegyItem) ;
		return result ;
	}

	
	public boolean updateWoStartegyItem(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoStartegyItem = (Map) param.get("WoStartegyItem") ;
		String keyStr = "item_code";
		Map keyCondMap  = Const.getMapForTargetStr(WoStartegyItem,  keyStr) ;
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		boolean result = dao.updateByKey( WoStartegyItem, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoStartegyItemData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoStartegyItemById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoStartegyItemByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoStartegyItemById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoStartegyItemDAO dao = new WoStartegyItemDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
