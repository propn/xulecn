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

import com.ztesoft.vsop.command.dao.NeCommandTemplateDAO ;
import com.ztesoft.vsop.command.dao.WoServCmdDAO;

public class NeCommandTemplateBO extends DictAction  {
	public boolean insertNeCommandTemplate(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandTemplate = (Map) param.get("NeCommandTemplate") ;
		
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		boolean result = dao.insert(NeCommandTemplate) ;
		return result ;
	}

	
	public boolean updateNeCommandTemplate(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCommandTemplate = (Map) param.get("NeCommandTemplate") ;
		String keyStr = "template_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeCommandTemplate,  keyStr) ;
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		boolean result = dao.updateByKey( NeCommandTemplate, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCommandTemplateData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "name")){
			whereCond.append(" and t.name like '%"+Const.getStrValue(param,"name")+"%' ");
			//para.add(Const.getStrValue(param , "name")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCommandTemplateById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCommandTemplateByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCommandTemplateById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	public String isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCommandTemplateDAO dao = new NeCommandTemplateDAO();
		String result = dao.isRelate(Const.getStrValue(keyCondMap, "template_id"));
		
		return result ;
	}
}
