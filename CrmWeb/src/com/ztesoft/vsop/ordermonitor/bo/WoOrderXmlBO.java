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

import com.ztesoft.vsop.ordermonitor.dao.WoOrderXmlDAO ;

public class WoOrderXmlBO extends DictAction  {
	public boolean insertWoOrderXml(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderXml = (Map) param.get("WoOrderXml") ;
		
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		boolean result = dao.insert(WoOrderXml) ;
		return result ;
	}

	
	public boolean updateWoOrderXml(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoOrderXml = (Map) param.get("WoOrderXml") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(WoOrderXml,  keyStr) ;
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		boolean result = dao.updateByKey( WoOrderXml, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoOrderXmlData(DynamicDict dto ) throws Exception {
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
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoOrderXmlById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoOrderXmlByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoOrderXmlById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoOrderXmlDAO dao = new WoOrderXmlDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
