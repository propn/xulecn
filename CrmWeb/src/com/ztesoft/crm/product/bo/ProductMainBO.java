package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ProductCatgItemDAO;
import com.ztesoft.crm.product.dao.ProductMainDAO ;

public class ProductMainBO extends DictAction  {
	/**
    	需要替换位置 说明 ：
 		1. 错误参数处理,根据实际情况修改
 		2. searchProductData 改方法的参数名称
 		3. findProductByCond(String product_id,String seq) 参数需要根据实际情况修改
 		4. 不需要的方法，可以根据实际情况进行裁剪
 		5. 此段啰嗦话，完成后替换工作后，请删除！
	 */
	 private   int etype;
	 private   int ecode;
	 private   String edesc;
	 private ProductMainDAO  productMainDAO=new ProductMainDAO();
	
	public boolean insertProduct(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Product = (Map) param.get("Product") ;
		
		ProductMainDAO dao = new ProductMainDAO();
		boolean result = dao.insert(Product) ;
		return result ;
	}

	
	public boolean updateProduct(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Product = (Map) param.get("Product") ;
		String keyStr = "product_id,seq";
		Map keyCondMap  = Const.getMapForTargetStr(Product,  keyStr) ;
		ProductMainDAO dao = new ProductMainDAO();
		boolean result = dao.updateByKey( Product, keyCondMap );
		
		return result ;
	}
	
	
	public List searchProductData(DynamicDict dto ) throws Exception {
				 
		//条件处理
		Map param = Const.getParam(dto) ;
		String  CATALOG_ITEM_ID=(String)param.get("CATALOG_ITEM_ID");
	      List  whereCondParams=new ArrayList();
	      whereCondParams.add(CATALOG_ITEM_ID);
	      
	      List list=productMainDAO.findBySql("SELECT  A.CATALOG_ITEM_ID        ,  A.ELEMENT_TYPE           ,  A.ELEMENT_ID             ," +
	      		"  P.PRODUCT_ID             ,  P.PRODUCT_PROVIDER_ID    ,  P.PRICING_PLAN_ID        ,  P.PRODUCT_FAMILY_ID      ,  P.PRODUCT_NAME           ," +
	      		"  P.PRODUCT_COMMENTS       ,  P.PRODUCT_TYPE           ,  P.PRODUCT_CLASSIFICATION ,  P.PRODUCT_CODE           ,  P.STATE                  ," +
	      		"  TO_CHAR(P.EFF_DATE, 'YYYY-MM-DD HH24:MI:SS') EFF_DATE,  TO_CHAR(P.EXP_DATE, 'YYYY-MM-DD HH24:MI:SS') EXP_DATE,  P.LIMIT_NUM              ," +
	      		"  P.PROD_CAT_TYPE          ,  P.PROD_ADSC              ,  P.MANAGE_GRADE           ,  P.BUREAU_NO              ,  P.SEQ                    ," +
	      		"  P.OPER_BUREAU_NO         ,  P.SITE_NO                ,  P.STAFF_NO               ,  P.OPER_DATE              ," +
	      		"  (select t.prod_cat_name from  product_cat t where t.prod_cat_type = P.Prod_Cat_Type) prod_cat_name," +
	      		" (select s.sm_disp_view  from tsm_paravalue s  where s.sm_table_ename ='PRODUCT'" +
	      		"  and   s.sm_field_ename = 'STATE'  and   s.sm_used_view   = P.state  )  state_name ," +
	      		"  (select s.sm_disp_view  from tsm_paravalue s  where s.sm_table_ename ='PRODUCT'" +
	      		"  and   s.sm_field_ename = 'PRODUCT_CLASSIFICATION'  and   s.sm_used_view   = P.PRODUCT_CLASSIFICATION  )  PRODUCT_CLASSIFICATION_NAME," +
	      		"  (SELECT '('||P.SITE_NO||')'||T.SITE_NAME FROM TSM_SITE T WHERE T.SITE_NO = P.SITE_NO AND T.STATE = '1' AND ROWNUM<2) SITE_NAME, " +
	      		"  (SELECT '('||P.STAFF_NO||')'||T.STAFF_NAME FROM TSM_STAFF T WHERE T.STAFF_NO = P.STAFF_NO AND T.STATE = '1' AND ROWNUM<2) STAFF_NAME," +
	      		"  TO_CHAR(P.OPER_DATE, 'YYYY-MM-DD HH24:MI:SS') OPER_DATE " +
	      		"FROM PRODUCT_CATALOG_ITEM_ELEMENT A,PRODUCT P WHERE A.CATALOG_ITEM_ID = ? AND A.ELEMENT_TYPE = '10A' AND A.ELEMENT_ID = P.PRODUCT_ID " +
	      		"AND P.STATE <> '00X'",whereCondParams,etype,ecode,edesc);
	      
		
	      if (list==null) list=new ArrayList();
    	  return list;	
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductMainDAO dao = new ProductMainDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductMainDAO dao = new ProductMainDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductMainDAO dao = new ProductMainDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
