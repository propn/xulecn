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

import com.ztesoft.vsop.paras.dao.NeValueMapListDAO ;

public class NeValueMapListBO extends DictAction  {
	public boolean insertNeValueMapList(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeValueMapList = (Map) param.get("NeValueMapList") ;
		
		NeValueMapListDAO dao = new NeValueMapListDAO();
		boolean result = dao.insert(NeValueMapList) ;
		return result ;
	}

	
	public boolean updateNeValueMapList(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		NeValueMapListDAO dao = new NeValueMapListDAO();
		boolean result = dao.updateByMap(param);
		
		return result ;
	}
	
	
	public PageModel searchNeValueMapListData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "map_type_id")){
			whereCond.append(" and map_type_id = ? ");
			para.add(Const.getStrValue(param , "map_type_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeValueMapListDAO dao = new NeValueMapListDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeValueMapListById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeValueMapListDAO dao = new NeValueMapListDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeValueMapListByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeValueMapListDAO dao = new NeValueMapListDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeValueMapListById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeValueMapListDAO dao = new NeValueMapListDAO();
		boolean result = dao.deleteByMap( keyCondMap );
		
		return result ;
	}
	public boolean validateRepart(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeValueMapListDAO dao = new NeValueMapListDAO();
		Map map = dao.findByPrimaryKey( keyCondMap ) ;
		if(map.isEmpty())
			return false;
		return true ;
	}
}
