package com.ztesoft.vsop.module.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.module.dao.PbBusinessObjDAO ;

public class PbBusinessObjBO extends DictAction  {
	public boolean insertPbBusinessObj(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PbBusinessObj = (Map) param.get("PbBusinessObj") ;
		
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		boolean result = dao.insert(PbBusinessObj) ;
		return result ;
	}

	
	public boolean updatePbBusinessObj(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map PbBusinessObj = (Map) param.get("PbBusinessObj") ;
		String keyStr = "business_obj_id";
		Map keyCondMap  = Const.getMapForTargetStr(PbBusinessObj,  keyStr) ;
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		boolean result = dao.updateByKey( PbBusinessObj, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchPbBusinessObjData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "business_obj_name")){
			String name = Const.getStrValue(param , "business_obj_name");
			whereCond.append(" and (business_obj_name like '%"+name+"%' or business_obj_name like '%"+name+"%')");
		}
		if(Const.containStrValue(param , "obj_type_code")){
			whereCond.append(" and obj_type_code = ? ");
			para.add(Const.getStrValue(param , "obj_type_code")) ;
		}
		if(Const.containStrValue(param , "business_obj_id")){
			whereCond.append(" and business_obj_id = ? ");
			para.add(Const.getStrValue(param , "business_obj_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getPbBusinessObjById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findPbBusinessObjByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deletePbBusinessObjById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		PbBusinessObjDAO dao = new PbBusinessObjDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
