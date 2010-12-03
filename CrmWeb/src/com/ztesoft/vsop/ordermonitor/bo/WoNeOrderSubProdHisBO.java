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

import com.ztesoft.vsop.ordermonitor.dao.WoNeOrderSubProdHisDAO ;

public class WoNeOrderSubProdHisBO extends DictAction  {
	public boolean insertWoNeOrderSubProdHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderSubProdHis = (Map) param.get("WoNeOrderSubProdHis") ;
		
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		boolean result = dao.insert(WoNeOrderSubProdHis) ;
		return result ;
	}

	
	public boolean updateWoNeOrderSubProdHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoNeOrderSubProdHis = (Map) param.get("WoNeOrderSubProdHis") ;
		String keyStr = "ne_sub_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoNeOrderSubProdHis,  keyStr) ;
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		boolean result = dao.updateByKey( WoNeOrderSubProdHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoNeOrderSubProdHisData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "ne_order_id")){
			whereCond.append(" and ne_order_id = ? ");
			para.add(Const.getStrValue(param , "ne_order_id")) ;
		}	
		whereCond.append(" order by ne_sub_id ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoNeOrderSubProdHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoNeOrderSubProdHisByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoNeOrderSubProdHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoNeOrderSubProdHisDAO dao = new WoNeOrderSubProdHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
