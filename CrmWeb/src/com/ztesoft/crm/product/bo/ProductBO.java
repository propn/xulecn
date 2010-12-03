package com.ztesoft.crm.product.bo;



import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.buffalo.request.RequestContext;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.DictService;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ProductDAO ;
import com.ztesoft.oaas.common.util.GlobalVariableHelper;

public class ProductBO extends DictAction  {
	
	//Base __base = new Base();
	private   int ecode;
	DictService dictService = new DictService();
	private String party_id ="";
	private String party_role_id ="";
	private String oper_region_id ="";
	
	private void getOperData() throws Exception{
		this.party_id = dictService.getGlobalVar("vg_oper_code");
		this.party_role_id = dictService.getGlobalVar("vg_oper_id");		
		this.oper_region_id = dictService.getGlobalVar("vg_business_id");
	}
	public boolean insertProduct(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Product = (Map) param.get("Product") ;
		
		String sql="select S_PRODUCT_ID.nextval from dual ";
		String oper_date = Base.query_string(JNDINames.PM_DATASOURCE,"select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') oper_date from dual",-210020401);
		String product_id = Base.query_string(JNDINames.PM_DATASOURCE,sql,-210020402);
		Product.put("product_id",product_id);
		Product.put("pricing_plan_id",product_id);
		Product.put("state","00E");
		Product.put("seq","0");
		this.getOperData();	
		Product.put("party_id",this.party_id);
		Product.put("party_role_id",this.party_role_id);
		Product.put("oper_region_id",this.oper_region_id);
		Product.put("oper_date",oper_date);
		
			
		ProductDAO dao = new ProductDAO();
		boolean result = dao.insert(Product) ;
		
		String catalog_item_id = (String)Product.get("catalog_item_id");
		DynamicDict catalog_item_element= new DynamicDict();
		catalog_item_element.setValueByName("ELEMENT_ID",product_id);
		catalog_item_element.setValueByName("ELEMENT_TYPE","10A");
		catalog_item_element.setValueByName("CATALOG_ITEM_ID",catalog_item_id);
		catalog_item_element.setValueByName("STATE","00A");
		
		catalog_item_element.setValueByName("party_id",this.party_id);
		catalog_item_element.setValueByName("party_role_id",this.party_role_id);
		catalog_item_element.setValueByName("oper_region_id",this.oper_region_id);
		
		Base.action__execute("CRMDB", -210012201, catalog_item_element, 
				"insert into product_catalog_item_element ($columns,seq,OPER_DATE) values ($columns,0,sysdate)", new String[]{});
		return result ;
	}

	
	public boolean updateProduct(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map Product = (Map) param.get("Product") ;
		
		
		String product_id = (String)Product.get("product_id");
		String oper_date = Base.query_string(JNDINames.PM_DATASOURCE,"select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss') oper_date from dual",-210020401);
		this.getOperData();
		Product.put("party_id",this.party_id);
		Product.put("party_role_id",this.party_role_id);
		Product.put("oper_region_id",this.oper_region_id);		

		Product.put("oper_date",oper_date);
		
		saveOldData(product_id);	
		
		String keyStr = "product_id,seq";		
		Map keyCondMap  = Const.getMapForTargetStr(Product,  keyStr) ;
		ProductDAO dao = new ProductDAO();
		boolean result = dao.updateByKey( Product, keyCondMap );
		
		
		
		
		return result ;
	}
	
	private void saveOldData(String product_id)  throws Exception{		
		DynamicDict ProductOldDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from PRODUCT a where product_id = ? and state <> '00X'   ", new String[]{product_id}, ecode);	
		String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT where product_id = ? ", 
				new String[]{product_id}, ecode);
		for(int i = 0;i < ProductOldDict.getCountByName("PRODUCT_ID"); i++){
			Base.dict__set_val(ProductOldDict,"seq",i,seq);
			Base.action__execute(JNDINames.PM_DATASOURCE, ecode,ProductOldDict,i," insert into ARC_PRODUCT ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}	
	}
	
	
	public PageModel searchProductData(DynamicDict dto ) throws Exception {
		//条件处理
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		String query_type  = Const.getStrValue(param,"query_type");
		String query_data  = Const.getStrValue(param,"query_data");
		String product_class  = Const.getStrValue(param,"product_class");
		
		List para = new ArrayList();
		para.add(product_class);
		para.add(query_data);
		String SQL = "";
		if(query_type.equals("0")){
			SQL = "select product_id ,product_name  from product where product_classification = ? and product_name like '%'||?||'%' and state <> '00X'";
		}else if(query_data.equals("1")){
			SQL = "select product_id ,product_name  from product where product_classification = ? and product_id = ? and state <> '00X'";
		}
		
		
		//调用DAO代码
		ProductDAO dao = new ProductDAO();	
		
		List list = dao.findBySql(SQL,para);
		result.setList(list);
		
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductDAO dao = new ProductDAO();
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
		ProductDAO dao = new ProductDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto);
		
		String product_id = (String)keyCondMap.get("product_id");
		this.getOperData();
		
		Base.update(JNDINames.PM_DATASOURCE, "update product set party_id = ? , party_role_id = ? , oper_region_id = ? where product_id = ? ", 
				new String[]{this.party_id,this.party_role_id,this.oper_region_id,product_id}, ecode);			
		
		saveOldData(product_id);	
		ProductDAO dao = new ProductDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		//String catalog_item_id = (String)keyCondMap.get("catalog_item_id");
		
		
		Base.update("CRMDB","delete from product_catalog_item_element where ELEMENT_ID = ? and ELEMENT_TYPE = '10A'", 
				new String[]{product_id},-210012202);
		
		return result ;
	}
}
