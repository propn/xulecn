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

import com.ztesoft.vsop.paras.dao.NeTranRuleParaDAO ;
import com.ztesoft.vsop.paras.dao.NeValueMapListDAO;

public class NeTranRuleParaBO extends DictAction  {
	public boolean insertNeTranRulePara(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map NeTranRulePara = (Map) param.get("NeTranRulePara") ;
		
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		boolean result = dao.insert(NeTranRulePara) ;
		return result ;
	}

	
	public boolean updateNeTranRulePara(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		boolean result = dao.updateByMap(param);
		
		return result ;
	}
	
	
	public PageModel searchNeTranRuleParaData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "tran_rule_id")){
			whereCond.append(" and p.tran_rule_id = ? ");
			para.add(Const.getStrValue(param , "tran_rule_id")) ;
		}
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		//调用DAO代码
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getNeTranRuleParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findNeTranRuleParaByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteNeTranRuleParaById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		boolean result = dao.deleteByMap( keyCondMap );
		
		return result ;
	}
	public int validateRepartPara(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		Map NeTranRulePara = (Map) keyCondMap.get("paraMap") ;
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		List paraList = new ArrayList();
		paraList.add(Const.getStrValue(NeTranRulePara , "tran_rule_id"));
		paraList.add(Const.getStrValue(NeTranRulePara , "para_id"));
		List paraResList = dao.findBySql(dao.SQL_COUNT_PARA, paraList);
		if(null!=paraResList && paraResList.size()>0)
			return 1;
		return 0;
	}
	public int validateRepartSeq(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		Map NeTranRulePara = (Map) keyCondMap.get("paraMap") ;
		NeTranRuleParaDAO dao = new NeTranRuleParaDAO();
		List seqList = new ArrayList();
		seqList.add(Const.getStrValue(NeTranRulePara , "tran_rule_id"));
		seqList.add(NeTranRulePara.get("seq"));
		String para_id =  Const.getStrValue(NeTranRulePara , "para_id");
		String sql = dao.SQL_COUNT_SEQ;
		if(null != para_id && !"".equals(para_id)){
			sql += " and para_id != "+para_id;
			//seqList.add(para_id);
		}
		List seqResList = dao.findBySql(sql,seqList);
		if(null!=seqResList && seqResList.size()>0)
			return 2;
		return 0;
	}
}
