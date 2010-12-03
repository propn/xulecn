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

import com.ztesoft.vsop.ordermonitor.dao.WoOrderXmlHisDAO ;

public class WoOrderXmlHisBO extends DictAction  {
	public boolean insertWoOrderXmlHis(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderXmlHis = (Map) param.get("WoOrderXmlHis") ;
		
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		boolean result = dao.insert(WoOrderXmlHis) ;
		return result ;
	}

	
	public boolean updateWoOrderXmlHis(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderXmlHis = (Map) param.get("WoOrderXmlHis") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(WoOrderXmlHis,  keyStr) ;
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		boolean result = dao.updateByKey( WoOrderXmlHis, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoOrderXmlHisData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "order_id")){
			whereCond.append(" and order_id = ? ");
			para.add(Const.getStrValue(param , "order_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		whereCond.append(" order by xml_seq ");
		
		
		//调用DAO代码
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderXmlHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoOrderXmlHisByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderXmlHisById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoOrderXmlHisDAO dao = new WoOrderXmlHisDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
