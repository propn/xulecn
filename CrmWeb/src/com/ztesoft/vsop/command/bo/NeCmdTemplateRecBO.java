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

import com.ztesoft.vsop.command.dao.NeCmdTemplateParaDAO;
import com.ztesoft.vsop.command.dao.NeCmdTemplateRecDAO ;

public class NeCmdTemplateRecBO extends DictAction  {
	public boolean insertNeCmdTemplateRec(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplateRec = (Map) param.get("NeCmdTemplateRec") ;
		
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		boolean result = dao.insert(NeCmdTemplateRec) ;
		return result ;
	}

	
	public boolean updateNeCmdTemplateRec(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplateRec = (Map) param.get("NeCmdTemplateRec") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(NeCmdTemplateRec,  keyStr) ;
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		boolean result = dao.updateByKey( NeCmdTemplateRec, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCmdTemplateRecData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "template_id")){
			whereCond.append(" and t.template_id = ? ");
			para.add(Const.getStrValue(param , "template_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCmdTemplateRecById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCmdTemplateRecByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCmdTemplateRecById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		boolean result = dao.deleteById( keyCondMap ) ;
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "template_id"));
		
		return result ;
	}
	
	/**
	 * 根据模板标识、数据项记录标识在 ne_cmd_template_rec中检查模板输入数据项记录唯一性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateTemplateRecord(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("template_id"));
		list.add(map.get("record_id"));
		NeCmdTemplateRecDAO dao = new NeCmdTemplateRecDAO();
		List l = dao.findBySql(dao.getSqlForValidateTemplateRecord(), list);
		if (null != l && !l.isEmpty()) {
			return false;
		}
		return true;
	}
}
