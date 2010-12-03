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

import com.ztesoft.vsop.paras.dao.WoParaCatgDAO;

public class WoParaCatgBo extends DictAction  {
	
	public boolean insertWoParaCatg(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoParaCatg = (Map) param.get("WoParaCatg") ;
		
		WoParaCatgDAO dao = new WoParaCatgDAO();
		boolean result = dao.insert(WoParaCatg) ;
		return result ;
	}

	
	public boolean updateWoParaCatg(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoParaCatg = (Map) param.get("WoParaCatg") ;
		String keyStr = "para_dir_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoParaCatg,  keyStr) ;
		WoParaCatgDAO dao = new WoParaCatgDAO();
		boolean result = dao.updateByKey( WoParaCatg, keyCondMap );
		
		return result ;
	}
	
	
	public List searchWoParaCatgData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;

		//调用DAO代码
		WoParaCatgDAO dao = new WoParaCatgDAO();
		List result = dao.findByCond(whereCond.toString()) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoParaCatgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoParaCatgDAO dao = new WoParaCatgDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoParaCatgByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoParaCatgDAO dao = new WoParaCatgDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoParaCatgById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoParaCatgDAO dao = new WoParaCatgDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoParaCatgDAO dao = new WoParaCatgDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "para_dir_id"));
		
		return result ;
	}
}
