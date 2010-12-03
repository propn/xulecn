package com.ztesoft.vsop.product.bo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.ztesoft.crm.business.common.utils.SeqUtil;
import com.ztesoft.vsop.product.dao.prodCatgItem.ProductCatgItemDAO;

public class ProductCatgItemBO extends DictAction  {
	public String insertProductCatgItem(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItem = (Map) param.get("ProductCatgItem") ;
		String catalog_item_id = SeqUtil.getInst().getNext("PRODUCT_CATALOG_ITEM", "CATALOG_ITEM_ID");
		ProductCatgItem.put("catalog_item_id", catalog_item_id);
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		boolean result = dao.insert(ProductCatgItem) ;
		if (result) {
			return catalog_item_id;
		}
		
		return "";
	}

	public boolean updateProductCatgItem(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItem = (Map) param.get("ProductCatgItem") ;
		String keyStr = "catalog_item_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductCatgItem,  keyStr) ;
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		boolean result = dao.updateByKey( ProductCatgItem, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProductCatgItemData(DynamicDict dto ) throws Exception {
		//条件处理
		Map param = Const.getParam(dto) ;
		StringBuffer whereCond = new StringBuffer() ;
		List para = new ArrayList() ;
		if(Const.containStrValue(param , "param1")){
			whereCond.append(" and param1 = ? ");
			para.add(Const.getStrValue(param , "param1")) ;
		}
		if(Const.containStrValue(param , "param2")){
			whereCond.append(" and param2 = ? ");
			para.add(Const.getStrValue(param , "param2")) ;
		}
		if(Const.containStrValue(param , "param3")){
			whereCond.append(" and param3 = ? ");
			para.add(Const.getStrValue(param , "param3")) ;
		}
		
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		
		
		//调用DAO代码
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductCatgItemById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductCatgItemByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductCatgItemById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductCatgItemDAO dao = new ProductCatgItemDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
