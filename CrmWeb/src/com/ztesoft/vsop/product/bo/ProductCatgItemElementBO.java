package com.ztesoft.vsop.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.vsop.product.dao.prodItemElement.ProductCatgItemElementDAO ;

public class ProductCatgItemElementBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProductCatgItemElementData 改方法的参数名称
 		3. findProductCatgItemElementByCond(String catalog_item_id,String element_type,String element_id) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	
	public boolean insertProductCatgItemElement(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItemElement = (Map) param.get("ProductCatgItemElement") ;
		
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.insert(ProductCatgItemElement) ;
		return result ;
	}

	
	public boolean updateProductCatgItemElement(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductCatgItemElement = (Map) param.get("ProductCatgItemElement") ;
		String keyStr = "catalog_item_id,element_type,element_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductCatgItemElement,  keyStr) ;
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.updateByKey( ProductCatgItemElement, keyCondMap );
		
		return result ;
	}
	
	
	public PageModel searchProductCatgItemElementData(DynamicDict dto ) throws Exception {
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
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		PageModel result = dao.searchByCond( whereCond.toString() , para, pageIndex,  pageSize) ;
		
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductCatgItemElementById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductCatgItemElementByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductCatgItemElementById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		Map param = (Map)keyCondMap.get("productCatgItemElement");
		ProductCatgItemElementDAO dao = new ProductCatgItemElementDAO();
		boolean result = dao.deleteByKey( param ) > 0 ;
		
		return result ;
	}
	
	/**
	 * 批量新增
	 * @param para
	 * @return
	 * @throws Exception
	 */
	public int batchInsert(DynamicDict dto ) throws Exception{
		int succ = 0;//成功插入数据条数
		ProductCatgItemElementDAO pciDAO = new ProductCatgItemElementDAO();
		Map keyCondMap = Const.getParam(dto)  ;
		List list = (List)keyCondMap.get("selList");
		List condParam = new ArrayList();
		for(int i =0 ; i<list.size();i++){
			Map map = (Map) list.get(i);
			List param = new ArrayList();
			param.add(map.get("catalog_item_id"));
			param.add(map.get("element_type"));
			param.add(map.get("element_id"));//element_id
			//在这里判断选择的该记录是否已经存在数据库中了
			if(pciDAO.findByPrimaryKey(map).isEmpty())
			{
				succ++;
				condParam.add(param);
			}
		}
		
		String batchInsertSQL = "insert into product_catalog_item_element(catalog_item_id,element_type,element_id) values(?,?,?)";
		String dbName = JNDINames.VSOP_DATASOURCE ;
		Base.batchUpdate(dbName , batchInsertSQL, condParam,  Const.UN_JUDGE_ERROR,  Const.UN_JUDGE_ERROR, null) ;
		return succ;
	}
}
