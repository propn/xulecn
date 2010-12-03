package com.ztesoft.vsop.command.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.command.dao.WoCmdCollectTypeDAO ;

public class WoCmdCollectTypeBO extends DictAction  {
	public boolean insertWoCmdCollectType(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoCmdCollectType = (Map) param.get("WoCmdCollectType") ;
		
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		boolean result = dao.insert(WoCmdCollectType) ;
		return result ;
	}

	
	public boolean updateWoCmdCollectType(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoCmdCollectType = (Map) param.get("WoCmdCollectType") ;
		String keyStr = "cmd_collect_type_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoCmdCollectType,  keyStr) ;
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		boolean result = dao.updateByKey( WoCmdCollectType, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoCmdCollectTypeData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoCmdCollectTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	public Map getWoCmdCollectTypeByDeviceId(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map map = Const.getParam(dto) ;
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		Map result = dao.findByDeviceId(Const.getStrValue(map , "device_id")) ;
		
		return result ;
	}

	public List findWoCmdCollectTypeByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoCmdCollectTypeById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoCmdCollectTypeDAO dao = new WoCmdCollectTypeDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
