package com.ztesoft.vsop.system.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.system.dao.SystemInfoDAO ;

public class SystemInfoBO extends DictAction  {
	
	public boolean insertSystemInfo(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SystemInfo = (Map) param.get("SystemInfo") ;
		
		SystemInfoDAO dao = new SystemInfoDAO();
		boolean result = dao.insert(SystemInfo) ;
		return result ;
	}

	
	public boolean updateSystemInfo(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SystemInfo = (Map) param.get("SystemInfo") ;
		String keyStr = "system_code";
		Map keyCondMap  = Const.getMapForTargetStr(SystemInfo,  keyStr) ;
		SystemInfoDAO dao = new SystemInfoDAO();
		boolean result = dao.updateByKey( SystemInfo, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSystemInfoData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "partner_id")){
			whereCond.append(" and  not exists ( select 1 from PARTNER_SYSTEM_INFO p " +
					"where p.system_code = s.system_code and p.partner_id = ?)  ");
			para.add(Const.getStrValue(param , "partner_id")) ;
		}
		/*
		 * 为支持informix
		 */
	   // whereCond.append(" order by system_code ");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SystemInfoDAO dao = new SystemInfoDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSystemInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SystemInfoDAO dao = new SystemInfoDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSystemInfoByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SystemInfoDAO dao = new SystemInfoDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSystemInfoById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SystemInfoDAO dao = new SystemInfoDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
