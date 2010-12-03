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

import com.ztesoft.vsop.paras.dao.NeParaMapTypeDAO ;

public class NeParaMapTypeBO extends DictAction  {
	public boolean insertNeParaMapType(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeParaMapType = (Map) param.get("NeParaMapType") ;
		
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		boolean result = dao.insert(NeParaMapType) ;
		return result ;
	}

	
	public boolean updateNeParaMapType(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeParaMapType = (Map) param.get("NeParaMapType") ;
		String keyStr = "map_type_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeParaMapType,  keyStr) ;
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		boolean result = dao.updateByKey( NeParaMapType, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeParaMapTypeData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "name")){
			whereCond.append(" and name like '%"+Const.getStrValue(param , "name")+"%' ");
			//para.add(Const.getStrValue(param , "param1")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeParaMapTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeParaMapTypeByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeParaMapTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeParaMapTypeDAO dao = new NeParaMapTypeDAO();
		boolean result = dao.deleteById( keyCondMap );
		
		return result ;
	}
}
