package com.ztesoft.vsop.command.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.command.dao.NeCmdTemplateParaDAO;

public class NeCmdTemplateParaBO extends DictAction  {
	public boolean insertNeCmdTemplatePara(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplatePara = (Map) param.get("NeCmdTemplatePara") ;
		
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.insert(NeCmdTemplatePara) ;
		return result ;
	}

	
	public boolean updateNeCmdTemplatePara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeCmdTemplatePara = (Map) param.get("NeCmdTemplatePara") ;
		String keyStr = "";
		Map keyCondMap  = Const.getMapForTargetStr(NeCmdTemplatePara,  keyStr) ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.updateByKey( NeCmdTemplatePara, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeCmdTemplateParaData(DynamicDict dto ) throws Exception {
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
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeCmdTemplateParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeCmdTemplateParaByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeCmdTemplateParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.deleteById( keyCondMap );
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "template_id"));
		
		return result ;
	}
	
	/**
	 * 根据模板标识、数据项标识在ne_cmd_template_para中检查模板输入数据项唯一性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean validateTemplateCommond(DynamicDict dto) throws Exception {
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("template_id"));
		list.add(map.get("command_id"));
		NeCmdTemplateParaDAO dao = new NeCmdTemplateParaDAO();
		List l = dao.findBySql(dao.getSqlForValidateTemplatePara(), list);
		if (null != l && !l.isEmpty()) {
			return false;//已存在相同记录
		}
		return true;
	}
}
