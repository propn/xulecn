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

import com.ztesoft.vsop.paras.dao.NeJavaEngineDAO ;

public class NeJavaEngineBO extends DictAction  {
	public boolean insertNeJavaEngine(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeJavaEngine = (Map) param.get("NeJavaEngine") ;
		
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		boolean result = dao.insert(NeJavaEngine) ;
		return result ;
	}

	
	public boolean updateNeJavaEngine(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeJavaEngine = (Map) param.get("NeJavaEngine") ;
		String keyStr = "id";
		Map keyCondMap  = Const.getMapForTargetStr(NeJavaEngine,  keyStr) ;
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		boolean result = dao.updateByKey( NeJavaEngine, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeJavaEngineData(DynamicDict dto ) throws Exception {
		//��������
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "name")){
			whereCond.append(" and name like '%"+Const.getStrValue(param , "name")+"%' ");
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//����DAO����
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * ���ݿ��������ѯ����
	 */
	public Map getNeJavaEngineById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeJavaEngineByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeJavaEngineById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		NeJavaEngineDAO dao = new NeJavaEngineDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
