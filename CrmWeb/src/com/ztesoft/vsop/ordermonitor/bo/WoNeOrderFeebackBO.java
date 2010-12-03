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

import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderDAO;
import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderFeebackDAO ;

public class WoNeOrderFeebackBO extends DictAction  {
	public boolean insertWoNeOrderFeeback(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderFeeback = (Map) param.get("WoNeOrderFeeback") ;
		
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		boolean result = dao.insert(WoNeOrderFeeback) ;
		return result ;
	}

	
	public boolean updateWoNeOrderFeeback(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderFeeback = (Map) param.get("WoNeOrderFeeback") ;
		String keyStr = "ne_order_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoNeOrderFeeback,  keyStr) ;
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		boolean result = dao.updateByKey( WoNeOrderFeeback, keyCondMap );
		
		return result ;
	}
	public boolean successWoNeOrderFeeback(DynamicDict dto) throws Exception {
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		String ne_order_id = Const.getStrValue(keyCondMap , "ne_order_id");
		String state = Const.getStrValue(keyCondMap , "state");
		String feeback_info = Const.getStrValue(keyCondMap , "feeback_info");
		boolean result = dao.successWoNeOrderFeeback(ne_order_id,state,feeback_info);
		return result ;
	}
	
	public PageModel searchWoNeOrderFeebackData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "ne_order_id")){
			whereCond.append(" and ne_order_id = ? ");
			para.add(Const.getStrValue(param , "ne_order_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	public boolean changeState(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		boolean result = dao.changeState(Const.getStrValue(param , "ne_order_id"), Const.getStrValue(param , "state") );
		
		return result ;
	}
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoNeOrderFeebackById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoNeOrderFeebackByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoNeOrderFeebackById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoNeOrderFeebackDAO dao = new WoNeOrderFeebackDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
