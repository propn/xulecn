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
		//��������
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
		
		
		//����DAO����
		SpIntSysDAO dao = new SpIntSysDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getSpIntSysById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		SpIntSysDAO dao = new SpIntSysDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findSpIntSysByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		SpIntSysDAO dao = new SpIntSysDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteSpIntSysById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		SpIntSysDAO dao = new SpIntSysDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * �������޸�ʱУ��ϵͳ�����Ƿ�Ψһ
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
			return false;//���Ѵ�����ͬ��ϵͳ������
		}
		return true;
	}
}
