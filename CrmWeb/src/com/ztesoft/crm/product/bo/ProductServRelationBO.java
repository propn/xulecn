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

import com.ztesoft.crm.product.dao.ProductServRelationDAO ;

public class ProductServRelationBO extends DictAction  {
	
	private   int etype;
    private   int ecode;
    private   String edesc;
    ProductServRelationDAO productServRelationDAO = new ProductServRelationDAO();
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
	
	public boolean insertProductServRelation(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductServRelation = (Map) param.get("ProductServRelation") ;
		
		ProductServRelationDAO dao = new ProductServRelationDAO();
		boolean result = dao.insert(ProductServRelation) ;
		return result ;
	}

	
	public boolean updateProductServRelation(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductServRelation = (Map) param.get("ProductServRelation") ;
		String keyStr = "product_id,service_offer_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductServRelation,  keyStr) ;
		ProductServRelationDAO dao = new ProductServRelationDAO();
		boolean result = dao.updateByKey( ProductServRelation, keyCondMap );
		
		return result ;
	}
	
	/**
	 * 可选业务
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductServRelationData(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");		
		String product_cat  = Const.getStrValue(param,"product_cat");
		String action_type  = Const.getStrValue(param,"action_type");		
			
		para.add(product_cat);		
		para.add(action_type);	
		para.add(product_id);	
				
		ProductServRelationDAO dao = new ProductServRelationDAO();
		String sql ="select a.SERVICE_OFFER_ID, a.SERVICE_OFFER_NAME " +
				" from  SERVICE_OFFER a where a.prod_cat_type=? and  a.action_type=? " +
				"and  a.state <> '00X' and  not exists (select  1 from  PRODUCT_SERVICE_RELATION b where  b.PRODUCT_ID=?" +
				" and  a.SERVICE_OFFER_ID=b.SERVICE_OFFER_ID and b.state <> '00X' ) order by a.service_offer_type";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;	
	}
	
	/**
	 * 已选业务
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductServRelationData2(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		para.add(product_id);	
			
		ProductServRelationDAO dao = new ProductServRelationDAO();
		String sql ="select   to_char(b.eff_date,'YYYY-MM-DD HH24:MI:SS')  eff_date," +
				"  to_char(b.exp_date,'YYYY-MM-DD HH24:MI:SS')  exp_date," +
				"  b.PRODUCT_ID      ,  b.SERVICE_OFFER_ID,  a.service_offer_name,  b.FLAG            , " +
				" b.STATE           ,  b.ORDER_ID from  SERVICE_OFFER a,PRODUCT_SERVICE_RELATION b where  b.PRODUCT_ID=? " +
				" and  a.SERVICE_OFFER_ID=b.SERVICE_OFFER_ID and  a.state <> '00X' and b.state <> '00X' ORDER BY b.ORDER_ID ASC  ";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	
	
	/**
	 * 产品业务关系维护	
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean saveProductServRelation(DynamicDict dto  ) throws Exception {	
		Map param = Const.getParam(dto) ;
		HashMap ProductServRelationMap= (HashMap)param;		
		DynamicDict ProductServRelationDict = new DynamicDict();
		ArrayList ProductServRelation = (ArrayList) ProductServRelationMap.get("ProductServRelation") ;
		
		this.getGlobalData();			
		String PRODUCT_ID = "";
		String del_flag = "";
		for(int i = 0; i<ProductServRelation.size(); i++){
			HashMap hm_temp = (HashMap)ProductServRelation.get(i);
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
				ProductServRelationDict.setValueByName(fieldname, fieldvalue, 1);
			}
		}
		//System.out.println(ProductServRelationDict.toString());
		int ProductServRelationDictCount = ProductServRelationDict.getCountByName("PRODUCT_ID");
		del_flag = (String)ProductServRelationDict.getValueByName("DEL_FLAG");
		PRODUCT_ID = (String)ProductServRelationDict.getValueByName("PRODUCT_ID");
		dellOldData(PRODUCT_ID);		
		if((ProductServRelationDictCount==1)&&del_flag.equals("1")){
				
		}else{			
			for(int i=0;i<ProductServRelationDict.getCountByName("SERVICE_OFFER_ID");i++)
			{				
				Base.dict__set_val(ProductServRelationDict,"PARTY_ID",i,this.party_id);
				Base.dict__set_val(ProductServRelationDict,"PARTY_ROLE_ID",i,this.party_role_id);
				Base.dict__set_val(ProductServRelationDict,"OPER_REGION_ID",i,this.oper_region_id);
				Base.dict__set_val(ProductServRelationDict,"SEQ",i,"0");
				Base.dict__set_val(ProductServRelationDict,"STATE",i,"00A");
			}			
			for(int i=0;i<ProductServRelationDict.getCountByName("SERVICE_OFFER_ID");i++)
			{
				Base.action__execute(dbName, ecode,ProductServRelationDict,i," insert into PRODUCT_SERVICE_RELATION ($columns,OPER_DATE) values ($columns,sysdate) ",new String[]{});			
			}
		}
		return true ;
	}
	
	private void dellOldData(String product_id) throws Exception{
		String PRODUCT_ID = product_id;
		DynamicDict ProductOldServRelationDict = new DynamicDict();
		ProductOldServRelationDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from product_service_relation a where product_id = ? and state = '00A'   ", new String[]{PRODUCT_ID}, ecode);		
		for(int i = 0;i<ProductOldServRelationDict.getCountByName("SERVICE_OFFER_ID");i++){
			
			Base.update(JNDINames.PM_DATASOURCE, "update product_service_relation set party_id = ? , party_role_id = ? , oper_region_id = ? where product_id = ? and SERVICE_OFFER_ID = ?", 
					new String[]{this.party_id,this.party_role_id,this.oper_region_id,product_id,(String)ProductOldServRelationDict.getValueByName("SERVICE_OFFER_ID",i)}, ecode);
			
			
			String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_SERVICE_RELATION where  product_id = ? and SERVICE_OFFER_ID = ?", 
					new String[]{product_id,(String)ProductOldServRelationDict.getValueByName("SERVICE_OFFER_ID",i)}, ecode);
			
			Base.dict__set_val(ProductOldServRelationDict,"seq",i,seq);
			Base.action__execute(dbName, ecode,ProductOldServRelationDict,i," insert into ARC_PRODUCT_SERVICE_RELATION ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}		
		Base.update(dbName," delete from PRODUCT_SERVICE_RELATION where product_id=? ",new String []{PRODUCT_ID}, ecode, etype, edesc);
	}
	
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductServRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductServRelationDAO dao = new ProductServRelationDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductServRelationByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductServRelationDAO dao = new ProductServRelationDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductServRelationById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductServRelationDAO dao = new ProductServRelationDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
