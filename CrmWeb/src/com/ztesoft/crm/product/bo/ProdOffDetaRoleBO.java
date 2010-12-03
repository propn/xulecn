package com.ztesoft.crm.product.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffDetaRoleDAO;

public class ProdOffDetaRoleBO extends DictAction  {
	
	public boolean insertProdOffDetaRole(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		boolean result = dao.insertProdOffRel(ProdOffDetaRole);
		
		return result ;
	}

	
	public boolean updateProdOffDetaRole(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		String keyStr = "prod_offer_role_cd";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffDetaRole,  keyStr) ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		boolean result = dao.updateByKey( ProdOffDetaRole, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProdOffDetaRoleData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		if(Const.containStrValue(param , "prod_offer_id")){
			whereCond.append(" and prod_offer_id = ? ");
			para.add(Const.getStrValue(param , "prod_offer_id")) ;
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		//调用DAO代码
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize);
		
		return result;
	}	
	public PageModel searchProdOffDetaRoleDataPro(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		
		if(Const.containStrValue(param , "prod_offer_id")){
			para.add(Const.getStrValue(param , "prod_offer_id")) ;
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		PageModel result = dao.searchCompileInfoByPrimaryKey(whereCond.toString(), para, pageIndex, pageSize);
		return result;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffDetaRoleByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		boolean result = dao.deleteProdOffDetaRole(keyCondMap);
		return result ;
	}
}
