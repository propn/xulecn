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
    	��Ҫ�滻λ�� ˵�� ��
 		1. �����������,����ʵ������޸�
 		2. searchProductData �ķ����Ĳ�������
 		3. findProductByCond(String product_id,String seq) ������Ҫ����ʵ������޸�
 		4. ����Ҫ�ķ��������Ը���ʵ��������вü�
 		5. �˶Ά��»�����ɺ��滻��������ɾ����
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
				 
		//��������
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
	 * ���ݿ��������ѯ����
	 */
	public Map getProductById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto) ;
		ProductMainDAO dao = new ProductMainDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductByCond(DynamicDict dto ) throws Exception {
		//��������
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//��֯����DAO���������
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//����DAO����
		ProductMainDAO dao = new ProductMainDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(DynamicDict dto ) throws Exception {
		//����DAO����
		Map keyCondMap = Const.getParam(dto)  ;
		ProductMainDAO dao = new ProductMainDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
