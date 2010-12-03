package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.DictService;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ProductRelationDAO;
import com.ztesoft.crm.product.dao.ProductRelationDAO ;

public class ProductRelationBO extends DictAction  {
	
	 private   int etype;
	   private   int ecode;
	   private   String edesc;
	   ProductRelationDAO productRelationDAO = new ProductRelationDAO();
	   private String dbName = JNDINames.PM_DATASOURCE ;
	   
	   DictService dictService = new DictService();
		private String party_id ="";
		private String party_role_id ="";
		private String oper_region_id ="";
	 
		private void getGlobalData() throws Exception{
			this.party_id = dictService.getGlobalVar("vg_oper_code");
			this.party_role_id = dictService.getGlobalVar("vg_oper_id");		
			this.oper_region_id = dictService.getGlobalVar("vg_business_id");
		}
	
	public boolean insertProductRelation(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductRelation = (Map) param.get("ProductRelation") ;
		
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.insert(ProductRelation) ;
		return result ;
	}

	
	public boolean updateProductRelation(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductRelation = (Map) param.get("ProductRelation") ;
		String keyStr = "relation_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductRelation,  keyStr) ;
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.updateByKey( ProductRelation, keyCondMap );
		
		return result ;
	}
	
	/**
	 * 查询可选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationData(DynamicDict dto ) throws Exception {
				
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		String product_a_id  = Const.getStrValue(param,"id");
		String prod_cat_type  = Const.getStrValue(param,"prod_cat_type");
		String relation_type_id  = Const.getStrValue(param,"relation_type_id");
		String product_classification  = Const.getStrValue(param,"product_classification");
		
		//int product_id = Integer.parseInt(sproduct_id);
		
		para.add(prod_cat_type);
		para.add(product_id);	
		para.add(product_classification);		
		para.add(product_a_id);
		para.add(relation_type_id);
		
		
		ProductRelationDAO dao = new ProductRelationDAO();
		String sql ="select '' relation_id,a.product_id prod_z_id,a.product_name prod_name from product a where a.prod_cat_type = ? and   a.state <> '00X' " +
				"and   a.product_id <> ? and   a.product_classification = ? and  not exists (select 1 from  PRODUCT_RELATION c " +
				"where  c.prod_a_id = ? and  a.product_id = c.prod_z_id and c.relation_type_id = ? and c.state <> '00X' )";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	/**
	 * 查询已选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationData2(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		String prod_cat_type  = Const.getStrValue(param,"prod_cat_type");
		String relation_type_id  = Const.getStrValue(param,"relation_type_id");		
		
		para.add(product_id);		
		para.add(relation_type_id);
		//para.add(prod_cat_type);
			
		ProductRelationDAO dao = new ProductRelationDAO();
		String sql ="select b.relation_id,a.product_id,a.product_name prod_name, b.RELATION_ID,b.PROD_A_ID,b.PROD_Z_ID, " +
				"  b.RELATION_TYPE_ID,b.OPERATION_FLAG,b.ORDER_ID, " +
				"  to_char(b.eff_date,'YYYY-MM-DD HH24:MI:SS')  eff_date, " +
				"  to_char(b.exp_date,'YYYY-MM-DD HH24:MI:SS')  exp_date, " +
				"  b.PROD_A_PROVIDER_ID ,b.PROD_Z_PROVIDER_ID ,b.MAX_COUNT " +
				"from product a,PRODUCT_RELATION b " +
				"where b.prod_a_id = ? and   b.prod_z_id = a.product_id " +
				"and   a.state <> '00X' and   b.relation_type_id = ? " +
				"and     b.state <> '00X' ORDER BY ORDER_ID ASC";//a.prod_cat_type = ? and 
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	
	
	/**
	 * 查询产品所有关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationData3(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		String relation_type_id  = Const.getStrValue(param,"relation_type_id");		
		
		para.add(product_id);		
		para.add(relation_type_id);
			
		ProductRelationDAO dao = new ProductRelationDAO();
		String sql ="select b.relation_id,a.product_id,a.product_name prod_name, b.RELATION_ID,b.PROD_A_ID,(select product_name from product where product_id = b.prod_a_id and state <> '00X' ) prod_a_name," +
				"(select d.attr_value_desc from attribute c, attribute_value d where c.attr_code = 'PRODUCT_RELATION_RELATION_TYPE_ID' " +
				"       and c.attr_id = d.attr_id  and c.state = '00A' and d.state = '00A'" +
				"       and to_char(d.attr_value) = to_char(b.relation_type_id) ) relation_type_name,b.PROD_Z_ID, " +
				"  b.RELATION_TYPE_ID,b.OPERATION_FLAG,(select d.attr_value_desc from attribute c, attribute_value d where c.attr_code = 'PRODUCT_RELATION_OPERATION_FLAG'" +
				"       and c.attr_id = d.attr_id  and c.state = '00A' and d.state = '00A'" +
				"       and  to_char(d.attr_value )= to_char( b.OPERATION_FLAG )) OPERATION_FLAG_name,b.ORDER_ID, " +
				"  to_char(b.eff_date,'YYYY-MM-DD HH24:MI:SS')  eff_date, " +
				"  to_char(b.exp_date,'YYYY-MM-DD HH24:MI:SS')  exp_date, " +
				"  b.PROD_A_PROVIDER_ID ,b.PROD_Z_PROVIDER_ID ,b.MAX_COUNT " +
				"from product a,PRODUCT_RELATION b " +
				"where b.prod_a_id = ? and   b.prod_z_id = a.product_id " +
				"and   a.state <> '00X' and   b.relation_type_id = ? " +
				"and     b.state <> '00X' ORDER BY ORDER_ID ASC";//a.prod_cat_type = ? and 
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	/**
	 * 产品关系维护	
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean saveProductRelation(DynamicDict dto  ) throws Exception {	
		Map param = Const.getParam(dto) ;
		HashMap ProductRelationMap= (HashMap)param;		
		DynamicDict ProductRelationDict = new DynamicDict();
		ArrayList ProductRelation = (ArrayList) ProductRelationMap.get("ProductRelation") ;
		
		this.getGlobalData();		
		
		String PRODUCT_ID = "";
		String RELATION_TYPE_ID = "";
		String del_flag = "";
		for(int i = 0; i<ProductRelation.size(); i++){
			HashMap hm_temp = (HashMap)ProductRelation.get(i);
			Iterator it_temp = hm_temp.keySet().iterator();
			while (it_temp.hasNext())
			{
				String fieldname = (String)it_temp.next();
				String fieldvalue = "";
				if(hm_temp.get(fieldname)!=null){
					fieldvalue = (String)hm_temp.get(fieldname);
				}else{
					fieldvalue = "";
				}
				ProductRelationDict.setValueByName(fieldname, fieldvalue, 1);
			}
		}
		System.out.println(ProductRelationDict.toString());
		int ProductRelationDictCount = ProductRelationDict.getCountByName("prod_a_id");
		del_flag = (String)ProductRelationDict.getValueByName("del_flag");
		PRODUCT_ID = (String)ProductRelationDict.getValueByName("prod_a_id");
		RELATION_TYPE_ID = (String)ProductRelationDict.getValueByName("RELATION_TYPE_ID");
		dellOldData(PRODUCT_ID,RELATION_TYPE_ID);		
		if((ProductRelationDictCount==1)&&del_flag.equals("1")){
				
		}else{			
			for(int i=0;i<ProductRelationDict.getCountByName("PROD_A_ID");i++)
			{
				if(((String)ProductRelationDict.getValueByName("RELATION_ID",i)).equals("")){
					String RELATION_ID = Base.query_string(dbName, "select RELATION_ID.nextval from dual", ecode);
					Base.dict__set_val(ProductRelationDict,"RELATION_ID",i,RELATION_ID);
				}
				Base.dict__set_val(ProductRelationDict,"PARTY_ID",i,this.party_id);
				Base.dict__set_val(ProductRelationDict,"PARTY_ROLE_ID",i,this.party_role_id);
				Base.dict__set_val(ProductRelationDict,"OPER_REGION_ID",i,this.oper_region_id);
				Base.dict__set_val(ProductRelationDict,"seq",i,"0");
				Base.dict__set_val(ProductRelationDict,"state",i,"00A");
			}			
			for(int i=0;i<ProductRelationDict.getCountByName("RELATION_ID");i++)
			{
				Base.action__execute(dbName, ecode,ProductRelationDict,i," insert into PRODUCT_RELATION ($columns,OPER_DATE) values ($columns,sysdate) ",new String[]{});			
			}
		}
		return true ;
	}
	
	private void dellOldData(String product_id, String vRELATION_TYPE_ID) throws Exception{
		String PRODUCT_ID = product_id;
		String RELATION_TYPE_ID = vRELATION_TYPE_ID;
		DynamicDict ProductOldRelationDict = new DynamicDict();
		ProductOldRelationDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from product_Relation a where prod_a_id = ? and RELATION_TYPE_ID = ?  and state = '00A'   ", new String[]{PRODUCT_ID,RELATION_TYPE_ID}, ecode);		
		for(int i = 0;i<ProductOldRelationDict.getCountByName("RELATION_ID");i++){
			
			Base.update(JNDINames.PM_DATASOURCE, "update PRODUCT_RELATION set party_id = ? , party_role_id = ? , oper_region_id = ? where RELATION_ID = ? ", 
					new String[]{this.party_id,this.party_role_id,this.oper_region_id,(String)ProductOldRelationDict.getValueByName("RELATION_ID",i)}, ecode);
			
			
			String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_RELATION where RELATION_ID = ?", 
					new String[]{(String)ProductOldRelationDict.getValueByName("RELATION_ID",i)}, ecode);
			Base.dict__set_val(ProductOldRelationDict,"seq",i,seq);
			Base.action__execute(dbName, ecode,ProductOldRelationDict,i," insert into ARC_PRODUCT_RELATION ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}		
		Base.update(dbName," delete from PRODUCT_RELATION where prod_a_id=? AND RELATION_TYPE_ID= ?",new String []{PRODUCT_ID,vRELATION_TYPE_ID}, ecode, etype, edesc);
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductRelationDAO dao = new ProductRelationDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductRelationByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductRelationDAO dao = new ProductRelationDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductRelationDAO dao = new ProductRelationDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
