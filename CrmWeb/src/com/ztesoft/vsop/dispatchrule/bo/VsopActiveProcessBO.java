package com.ztesoft.vsop.dispatchrule.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.dispatchrule.dao.VsopActiveProcessDAO ;

public class VsopActiveProcessBO extends DictAction  {
	public boolean insertVsopActiveProcess(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopActiveProcess = (Map) param.get("VsopActiveProcess") ;
		
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		boolean result = dao.insert(VsopActiveProcess) ;
		return result ;
	}

	
	public boolean updateVsopActiveProcess(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map VsopActiveProcess = (Map) param.get("VsopActiveProcess") ;
		String keyStr = "process_code";
		Map keyCondMap  = Const.getMapForTargetStr(VsopActiveProcess,  keyStr) ;
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		boolean result = dao.updateByKey( VsopActiveProcess, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchVsopActiveProcessData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "process_type")){
			whereCond.append(" and process_type = ? ");
			para.add(Const.getStrValue(param , "process_type")) ;
		}

		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getVsopActiveProcessById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findVsopActiveProcessByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteVsopActiveProcessById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		VsopActiveProcessDAO dao = new VsopActiveProcessDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
