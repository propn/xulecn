package com.ztesoft.vsop.paras.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.paras.dao.NeCommandCatgDAO;
import com.ztesoft.vsop.paras.dao.SpParaRecordListDAO ;

public class SpParaRecordListBO extends DictAction  {
	
	public boolean insertSpParaRecordList(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecordList = (Map) param.get("SpParaRecordList") ;
		
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		boolean result = dao.insert(SpParaRecordList) ;
		return result ;
	}

	
	public boolean updateSpParaRecordList(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaRecordList = (Map) param.get("SpParaRecordList") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(SpParaRecordList,  keyStr) ;
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		boolean result = dao.updateByKey( SpParaRecordList, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpParaRecordListData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "record_id")){
			whereCond.append(" and record_id = ? ");
			para.add(Const.getStrValue(param , "record_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpParaRecordListById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpParaRecordListByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaRecordListById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		boolean result = dao.deleteById( keyCondMap );
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpParaRecordListDAO dao = new SpParaRecordListDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "record_id"));
		
		return result ;
	}

}
