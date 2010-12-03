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

import com.ztesoft.vsop.paras.dao.SpParaInfoDAO;

public class SpParaInfoBo extends DictAction  {
	public boolean insertSpParaInfo(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaInfo = (Map) param.get("SpParaInfo") ;
		
		SpParaInfoDAO dao = new SpParaInfoDAO();
		boolean result = dao.insert(SpParaInfo) ;
		return result ;
	}

	
	public boolean updateSpParaInfo(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpParaInfo = (Map) param.get("SpParaInfo") ;
		String keyStr = "para_id";
		Map keyCondMap  = Const.getMapForTargetStr(SpParaInfo,  keyStr) ;
		SpParaInfoDAO dao = new SpParaInfoDAO();
		boolean result = dao.updateByKey( SpParaInfo, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpParaInfoData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "para_dir_id")){
			whereCond.append(" and para_dir_id = ? ");
			para.add(Const.getStrValue(param , "para_dir_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpParaInfoDAO dao = new SpParaInfoDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpParaInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpParaInfoDAO dao = new SpParaInfoDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpParaInfoByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpParaInfoDAO dao = new SpParaInfoDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpParaInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpParaInfoDAO dao = new SpParaInfoDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
