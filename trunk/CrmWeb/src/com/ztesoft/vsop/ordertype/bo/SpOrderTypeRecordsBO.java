package com.ztesoft.vsop.ordertype.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.ordertype.dao.SpOrderTypeCommandsDAO;
import com.ztesoft.vsop.ordertype.dao.SpOrderTypeRecordsDAO ;

public class SpOrderTypeRecordsBO extends DictAction  {
	public boolean insertSpOrderTypeRecords(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOrderTypeRecords = (Map) param.get("SpOrderTypeRecords") ;
		
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		boolean result = dao.insert(SpOrderTypeRecords) ;
		return result ;
	}

	
	public boolean updateSpOrderTypeRecords(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOrderTypeRecords = (Map) param.get("SpOrderTypeRecords") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(SpOrderTypeRecords,  keyStr) ;
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		boolean result = dao.updateByKey( SpOrderTypeRecords, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpOrderTypeRecordsData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "out_order_type_id")){
			whereCond.append(" and out_order_type_id = ? ");
			para.add(Const.getStrValue(param , "out_order_type_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOrderTypeRecordsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOrderTypeRecordsByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOrderTypeRecordsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		boolean result = dao.deleteById( keyCondMap);
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "out_order_type_id"));
		
		return result ;
	}
	public boolean validateSpOrderTypeRecord(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeRecordsDAO dao = new SpOrderTypeRecordsDAO();
		List list = new ArrayList();
		list.add(Const.getStrValue(keyCondMap , "out_order_type_id"));
		list.add(Const.getStrValue(keyCondMap , "record_id"));
		List result = dao.findBySql(dao.SQL_COUNT_RECORDS, list);
		if(result.size()>0)
			return false;
		return true ;
	}
}
