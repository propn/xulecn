package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.crm.product.dao.ProdOffRelDAO;

public class ProdOffRelBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProdOffRelData 改方法的参数名称
 		3. findProdOffRelByCond(String prod_offer_rel_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProdOffRel(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.insert(ProdOffRel) ;
		return result ;
	}

	
	public boolean updateProdOffRel(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProdOffRel = (Map) param.get("ProdOffRel") ;
		String keyStr = "prod_offer_rel_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProdOffRel,  keyStr) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.updateByKey( ProdOffRel, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProdOffRelData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto);
		StringBuffer whereCond = new StringBuffer();
		List para = new ArrayList();
		if(Const.containStrValue(param , "offer_a_id")){
			whereCond.append(" and offer_a_id = ? ");
			para.add(Const.getStrValue(param , "offer_a_id"));
		}
		if(Const.containStrValue(param , "offer_z_id")){
			whereCond.append(" or offer_z_id = ? ");
			para.add(Const.getStrValue(param , "offer_z_id"));
		}
		
		int pageSize = Const.getPageSize(param);
		int pageIndex = Const.getPageIndex(param);
		
		
		//调用DAO代码
		ProdOffRelDAO dao = new ProdOffRelDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProdOffRelById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProdOffRelByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProdOffRelById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
	
	public boolean deleteProdOffRel(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map map = Const.getParam(dto);
		
		List list = new ArrayList();
		list.add(map.get("offer_a_id"));
		list.add(map.get("relation_type_id"));
		list.add(map.get("offer_z_id"));
		
		ProdOffRelDAO dao = new ProdOffRelDAO();
		boolean result = dao.delete("", list) > 0;
		
		return result ;
	}
	
	public String getProdOffNameByKey(DynamicDict dto ) throws Exception {
		String productName = "";
		Map map = Const.getParam(dto);
		List list = new ArrayList();
		list.add(map.get("prod_offer_id"));
		ProdOffRelDAO dao = new ProdOffRelDAO();
		List l = dao.findBySql(dao.getSQLForProdOffName(), list);
		if (null != l && !l.isEmpty()) {
			productName = ((HashMap) l.get(0)).get("prod_offer_name").toString();
		}
		return productName;
	}
}
