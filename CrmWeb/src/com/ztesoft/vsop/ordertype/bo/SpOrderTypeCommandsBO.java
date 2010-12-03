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

import com.ztesoft.vsop.ordertype.dao.SpOrderTypeCommandsDAO ;
import com.ztesoft.vsop.paras.dao.SpParaRecordListDAO;

public class SpOrderTypeCommandsBO extends DictAction  {

	public boolean insertSpOrderTypeCommands(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOrderTypeCommands = (Map) param.get("SpOrderTypeCommands") ;
		
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		boolean result = dao.insert(SpOrderTypeCommands) ;
		return result ;
	}

	
	public boolean updateSpOrderTypeCommands(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOrderTypeCommands = (Map) param.get("SpOrderTypeCommands") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(SpOrderTypeCommands,  keyStr) ;
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		boolean result = dao.updateByKey( SpOrderTypeCommands, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpOrderTypeCommandsData(DynamicDict dto ) throws Exception {
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
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOrderTypeCommandsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOrderTypeCommandsByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOrderTypeCommandsById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		boolean result = dao.deleteById( keyCondMap );
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "out_order_type_id"));
		
		return result ;
	}
	public boolean validateSpOrderTypeCommond(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOrderTypeCommandsDAO dao = new SpOrderTypeCommandsDAO();
		List list = new ArrayList();
		list.add(Const.getStrValue(keyCondMap , "out_order_type_id"));
		list.add(Const.getStrValue(keyCondMap , "command_id"));
		List result = dao.findBySql(dao.SQL_COUNT_COMMANDS, list);
		if(result.size()>0)
			return false;
		return true ;
	}
}
