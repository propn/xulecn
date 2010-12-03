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

import com.ztesoft.vsop.command.dao.WoServCmdDAO ;
import com.ztesoft.vsop.paras.dao.NeCommandCatgDAO;

public class WoServCmdBO extends DictAction  {
	public boolean insertWoServCmd(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoServCmd = (Map) param.get("WoServCmd") ;
		
		WoServCmdDAO dao = new WoServCmdDAO();
		boolean result = dao.insert(WoServCmd) ;
		return result ;
	}

	
	public boolean updateWoServCmd(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		WoServCmdDAO dao = new WoServCmdDAO();
		boolean result = dao.updateByMap(param);
		
		return result ;
	}
	
	
	public PageModel searchWoServCmdData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "command_collect_id")){
			whereCond.append(" and m.command_collect_id = ? ");
			para.add(Const.getStrValue(param , "command_collect_id")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoServCmdDAO dao = new WoServCmdDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoServCmdById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoServCmdDAO dao = new WoServCmdDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoServCmdByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoServCmdDAO dao = new WoServCmdDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoServCmdById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoServCmdDAO dao = new WoServCmdDAO();
		boolean result = dao.deleteByMap( keyCondMap);
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoServCmdDAO dao = new WoServCmdDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap, "command_collect_id"));
		
		return result ;
	}
	
	public boolean validTemplateId(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		List list = new ArrayList();
		list.add(param.get("command_collect_id"));
		list.add(param.get("template_id"));
		WoServCmdDAO dao = new WoServCmdDAO();
		List l = dao.findBySql(dao.getSQLValidateTemplateId(), list);
		if (null != l && !l.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean validSeq(DynamicDict dto) throws Exception {
		Map param = Const.getParam(dto);
		List list = new ArrayList();
		list.add(param.get("command_collect_id"));
		list.add(param.get("seq"));
		WoServCmdDAO dao = new WoServCmdDAO();
		List l = dao.findBySql(dao.getSQLValidateSeq(), list);
		if (null != l && !l.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
