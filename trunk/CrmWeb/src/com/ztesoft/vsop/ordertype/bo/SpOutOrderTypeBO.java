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

import com.ztesoft.vsop.ordertype.dao.SpOutOrderTypeDAO ;

public class SpOutOrderTypeBO extends DictAction  {

	public boolean insertSpOutOrderType(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutOrderType = (Map) param.get("SpOutOrderType") ;
		
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		boolean result = dao.insert(SpOutOrderType) ;
		return result ;
	}

	
	public boolean updateSpOutOrderType(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpOutOrderType = (Map) param.get("SpOutOrderType") ;
		String keyStr = "out_order_type_id";
		Map keyCondMap  = Const.getMapForTargetStr(SpOutOrderType,  keyStr) ;
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		boolean result = dao.updateByKey( SpOutOrderType, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpOutOrderTypeData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "order_type_name")){
			String name = Const.getStrValue(param , "order_type_name");
			whereCond.append(" and (order_type_name like '%"+name+"%' or order_type_name like '%"+name.toUpperCase()+"%' )");
		}
		if(Const.containStrValue(param , "order_type_code")){
			String code = Const.getStrValue(param , "order_type_code");
			whereCond.append(" and ( out_order_type_code like '%"+code+"%' or out_order_type_code like '%"+code.toUpperCase()+"%')");

		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpOutOrderTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpOutOrderTypeByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpOutOrderTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public boolean checkCode(DynamicDict dto) throws Exception{
		Map keyCondMap = Const.getParam(dto)  ;
		SpOutOrderTypeDAO dao = new SpOutOrderTypeDAO();
		boolean result = dao.checkCode( keyCondMap );
		
		return result ;
	}
}
