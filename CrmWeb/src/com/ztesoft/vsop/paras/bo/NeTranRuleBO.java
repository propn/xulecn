package com.ztesoft.vsop.paras.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.paras.dao.NeTranRuleDAO;
import com.ztesoft.vsop.paras.dao.WoParaCatgDAO;

public class NeTranRuleBO extends DictAction  {

	public boolean insertNeTranRule(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeTranRule = (Map) param.get("NeTranRule") ;
		
		NeTranRuleDAO dao = new NeTranRuleDAO();
		boolean result = dao.insert(NeTranRule) ;
		return result ;
	}

	
	public boolean updateNeTranRule(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeTranRule = (Map) param.get("NeTranRule") ;
		String keyStr = "tran_rule_id";
		Map keyCondMap  = Const.getMapForTargetStr(NeTranRule,  keyStr) ;
		NeTranRuleDAO dao = new NeTranRuleDAO();
		boolean result = dao.updateByKey( NeTranRule, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchNeTranRuleData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		if(Const.containStrValue(param , "name")){
			whereCond.append(" and r.name like '%"+Const.getStrValue(param , "name")+"%' ");
		}
		
		//支持INFROMIX
		
		
		if (CrmConstants.DB_TYPE_INFORMIX.equalsIgnoreCase(CrmConstants.DATABASE_TYPE)) {
			
		
		    if(Const.containStrValue(param , "create_date")){
			    whereCond.append(" and r.create_date >= to_date('"+Const.getStrValue(param,"create_date")+"','%Y-%m-%d')");
			    //para.add(Const.getStrValue(param , "create_date")) ;
		     }
	     	if(Const.containStrValue(param , "end_date")){
		    	whereCond.append(" and r.create_date <= to_date('"+Const.getStrValue(param , "end_date")+"','%Y-%m-%d')");
		    	
		     }
		}
		else {
			if (Const.containStrValue(param, "create_date")) {
				if (CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig
						.getInstance().getParamValue("DATABASE_TYPE"))) {
					whereCond.append(" and r.create_date >= to_date('"
							+ Const.getStrValue(param, "create_date")
							+ "','%Y-%m-%d')");
				} else {
					whereCond.append(" and r.create_date >= to_date('"
							+ Const.getStrValue(param, "create_date")
							+ "','YYYY-MM-DD')");
				}
				// para.add(Const.getStrValue(param , "create_date")) ;
			}
			if (Const.containStrValue(param, "end_date")) {
				// whereCond.append(" and r.create_date <=
				// to_date('"+Const.getStrValue(param ,
				// "end_date")+"','%Y-%m-%d')");
				if (CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig
						.getInstance().getParamValue("DATABASE_TYPE"))) {
					whereCond.append(" and r.create_date <= to_date('"
							+ Const.getStrValue(param, "end_date")
							+ "','%Y-%m-%d')");
				} else {
					whereCond.append(" and r.create_date <= to_date('"
							+ Const.getStrValue(param, "end_date")
							+ "','YYYY-MM-DD')");
				}

			}
			
		}
		
		
		//调用DAO代码
		NeTranRuleDAO dao = new NeTranRuleDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeTranRuleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeTranRuleDAO dao = new NeTranRuleDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeTranRuleByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeTranRuleDAO dao = new NeTranRuleDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeTranRuleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeTranRuleDAO dao = new NeTranRuleDAO();
		boolean result = dao.deleteById( Const.getStrValue(keyCondMap, "tran_rule_id") );
		
		return result ;
	}
	public String isRelateByCommPara(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeTranRuleDAO dao = new NeTranRuleDAO();
		String result = dao.isRelateByCommPara(Const.getStrValue(keyCondMap, "tran_rule_id"));
		
		return result ;
	}
	public boolean isRelateByRulePara(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeTranRuleDAO dao = new NeTranRuleDAO();
		boolean result = dao.isRelateByRulePara(Const.getStrValue(keyCondMap, "tran_rule_id"));
		
		return result ;
	}
}
