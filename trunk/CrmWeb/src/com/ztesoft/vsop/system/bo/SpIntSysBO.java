package com.ztesoft.vsop.system.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.system.dao.SpIntSysDAO;

public class SpIntSysBO extends DictAction  {
	
	public boolean insertSpIntSys(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpIntSys = (Map) param.get("SpIntSys") ;
		
		SpIntSysDAO dao = new SpIntSysDAO();
		boolean result = dao.insert(SpIntSys) ;
		return result ;
	}

	
	public boolean updateSpIntSys(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map SpIntSys = (Map) param.get("SpIntSys") ;
		String keyStr = "int_sys_id";
		Map keyCondMap  = Const.getMapForTargetStr(SpIntSys,  keyStr) ;
		SpIntSysDAO dao = new SpIntSysDAO();
		boolean result = dao.updateByKey( SpIntSys, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchSpIntSysData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param, "sys_code")){
			String code = Const.getStrValue(param, "sys_code");
			whereCond.append(" and (sys_code like '%"+code+"%' or sys_code like '%"+code.toUpperCase()+"%')");
		}if(Const.containStrValue(param, "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (name like '%"+name+"%' or name like '%"+name.toUpperCase()+"%')");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		SpIntSysDAO dao = new SpIntSysDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getSpIntSysById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		SpIntSysDAO dao = new SpIntSysDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpIntSysByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		SpIntSysDAO dao = new SpIntSysDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpIntSysById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		SpIntSysDAO dao = new SpIntSysDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 新增、修改时校验系统编码是否唯一
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateUniqueForSysCode(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("sys_code"));
		SpIntSysDAO dao = new SpIntSysDAO();
		List l = dao.findBySql(dao.getSqlForValidateSysCode(), list);
		if (null != l && !l.isEmpty()) {
			return false;//即已存在相同的系统编码了
		}
		return true;
	}
}
