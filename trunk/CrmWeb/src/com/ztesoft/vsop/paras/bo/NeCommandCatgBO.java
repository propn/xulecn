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
import com.ztesoft.vsop.paras.dao.WoParaCatgDAO;

public class NeCommandCatgBO extends DictAction  {
	   private   int etype;
	   private   int ecode;
	   private   String edesc;
	public boolean insertNeCommandCatg(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandCatg = (Map) param.get("NeCommandCatg") ;
		
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		boolean result = dao.insert(NeCommandCatg) ;
		return result ;
	}

	
	public boolean updateNeCommandCatg(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandCatg = (Map) param.get("NeCommandCatg") ;
		String keyStr = "command_catalog_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeCommandCatg,  keyStr) ;
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		boolean result = dao.updateByKey( NeCommandCatg, keyCondMap );
		
		return result ;
	}
	
	
	public List searchNeCommandCatgData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;

		
		//调用DAO代码
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		List result = dao.findByCond( whereCond.toString()) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCommandCatgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCommandCatgByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandCatgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandCatgDAO dao = new NeCommandCatgDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "command_catalog_id"));
		
		return result ;
	}
}
