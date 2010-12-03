package com.ztesoft.vsop.dispatchrule.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.dispatchrule.dao.WoDispatchRuleDAO;
import com.ztesoft.vsop.dispatchrule.dao.WoDispatchStartegyDAO;

public class WoDispatchRuleBO extends DictAction  {
	public boolean insertWoDispatchRule(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchRule = (Map) param.get("WoDispatchRule") ;
		
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		boolean result = dao.insert(WoDispatchRule) ;
		return result ;
	}

	
	public boolean updateWoDispatchRule(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map WoDispatchRule = (Map) param.get("WoDispatchRule") ;
		String keyStr = "dispatch_rule_id";
		Map keyCondMap  = Const.getMapForTargetStr(WoDispatchRule,  keyStr) ;
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		boolean result = dao.updateByKey( WoDispatchRule, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchWoDispatchRuleData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param, "code")){
			String code = Const.getStrValue(param, "code");
			whereCond.append(" and (code like '%"+code+"%' or code like '%"+code.toUpperCase()+"%')");
		}if(Const.containStrValue(param, "name")){
			String name = Const.getStrValue(param, "name");
			whereCond.append(" and (name like '%"+name+"%' or name like '%"+name.toUpperCase()+"%')");
		}if(Const.containStrValue(param, "state")){
			whereCond.append(" and state = ?");
			para.add(Const.getStrValue(param, "state"));
		}if(Const.containStrValue(param, "dispatch_rule")){
			whereCond.append(" and dispatch_rule = ?");
			para.add(Const.getStrValue(param, "dispatch_rule"));
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getWoDispatchRuleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findWoDispatchRuleByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteWoDispatchRuleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		String dispatch_rule_id = Const.getStrValue(keyCondMap , "dispatch_rule_id");
		
		Map param = new HashMap();
		param.put("dispatch_rule_id", dispatch_rule_id);
		WoDispatchStartegyDAO sdao = new WoDispatchStartegyDAO();
		sdao.deleteByRuleId(dispatch_rule_id); //先删除对应调度策略项
		

		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;

		return result ;
	}
	public boolean changeStateWoDispatchRuleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		boolean result = dao.changeStateWoDispatchRuleById(Const.getStrValue(keyCondMap , "dispatch_rule_id"),Const.getStrValue(keyCondMap , "state"));
		
		return result ;
	}
	public boolean isRelate(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		boolean result = dao.isRelate(Const.getStrValue(keyCondMap , "dispatch_rule_id"));
		
		return result ;
	}
	public boolean validateCode(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		String dispatch_rule_id = Const.getStrValue(keyCondMap , "dispatch_rule_id");
		WoDispatchRuleDAO dao = new WoDispatchRuleDAO();
		List list = new ArrayList();
		list.add(Const.getStrValue(keyCondMap , "code"));
		String sql = dao.getSqlCountCode();
		if(null !=dispatch_rule_id && !"".equals(dispatch_rule_id)){
			sql += " and dispatch_rule_id != " +dispatch_rule_id;
		}
			
		List result = dao.findBySql(sql, list);
		if(null!=result &&result.size()>0)
			return true;
		return false ;
	}
}
