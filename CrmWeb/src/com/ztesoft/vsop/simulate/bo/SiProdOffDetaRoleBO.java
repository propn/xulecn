package com.ztesoft.vsop.simulate.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.vsop.simulate.dao.SiProdOffDetaRoleDAO;

public class SiProdOffDetaRoleBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProdOffDetaRoleData 改方法的参数名称
 		3. findProdOffDetaRoleByCond(String prod_offer_role_cd) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProdOffDetaRole(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
		boolean result = dao.insert(ProdOffDetaRole) ;
		return result ;
	}

	
	public boolean updateProdOffDetaRole(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffDetaRole = (Map) param.get("ProdOffDetaRole") ;
		String keyStr = "prod_offer_role_cd";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffDetaRole,  keyStr) ;
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
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
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize);
		
		return result;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
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
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffDetaRoleById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		//ProdOffDetaRoleDAO dao = new ProdOffDetaRoleDAO();
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 根据销售品id查找销售品构成列表
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public List getProdOffDetaRoleByOfferId(DynamicDict dto) throws Exception {
		
		List list = null;
		Map map = Const.getParam(dto);
		List paramList = new ArrayList();
		paramList.add(map.get("prod_offer_id"));
		SiProdOffDetaRoleDAO dao = new SiProdOffDetaRoleDAO();
		list = dao.findBySql(dao.getSQLByProdOfferId(), paramList);
		
		return list;
	}
}
