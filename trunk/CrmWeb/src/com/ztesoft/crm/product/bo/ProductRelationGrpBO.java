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

import com.ztesoft.crm.product.dao.ProductRelationGrpDAO ;

public class ProductRelationGrpBO extends DictAction  {
	 private   int etype;
	   private   int ecode;
	   private   String edesc;
	   ProductRelationGrpDAO productRelationGrpDAO = new ProductRelationGrpDAO();
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
	
	public boolean insertProductRelationGrp(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductRelationGrp = (Map) param.get("ProductRelationGrp") ;
		
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		boolean result = dao.insert(ProductRelationGrp) ;
		return result ;
	}

	
	public boolean updateProductRelationGrp(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductRelationGrp = (Map) param.get("ProductRelationGrp") ;
		String keyStr = "group_id,product_id,seq";
		Map keyCondMap  = Const.getMapForTargetStr(ProductRelationGrp,  keyStr) ;
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		boolean result = dao.updateByKey( ProductRelationGrp, keyCondMap );
		
		return result ;
	}
	
	/**
	 * 查询产品群可选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationGrpData(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String conf_group_id  = Const.getStrValue(param,"conf_group_id");
		String product_cat  = Const.getStrValue(param,"product_cat");
		
		para.add(product_cat);
		para.add(conf_group_id);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		String sql ="select a.product_id,a.product_name from product a where a.prod_cat_type = ? and a.state = '00A' and " +
				"a.product_id not in (select b.product_id from PRODUCT_RELATION_GROUP b where b.group_id = ? and b.state = '00A' )";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;
	}
	
	/**
	 * 查询产品群已选产品
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductRelationGrpData2(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String conf_group_id  = Const.getStrValue(param,"conf_group_id");
				
		para.add(conf_group_id);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		String sql ="select a.product_name,b.* from product a, PRODUCT_RELATION_GROUP b " +
				" where b.group_id = ? and b.product_id = a.product_id and b.state = '00A' and a.state = '00A'";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;
	}
	
	/**
	 * 保存产品群内关系
	 * @param dto
	 * @return
	 * @throws Exception
	 */	
	public boolean saveProductRelationGrp(DynamicDict dto  ) throws Exception {		
		Map param = Const.getParam(dto) ;
		HashMap ProductRelationGrpMap= (HashMap)param;		
		DynamicDict ProductRelationGrpDict = new DynamicDict();
		ArrayList ProductRelationGrp = (ArrayList) ProductRelationGrpMap.get("ProductRelationGrp") ;
		this.getGlobalData();
		
		String group_id = "";
		String del_flag = "";
		for(int i = 0; i<ProductRelationGrp.size(); i++){
			HashMap hm_temp = (HashMap)ProductRelationGrp.get(i);
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
				ProductRelationGrpDict.setValueByName(fieldname, fieldvalue, 1);
			}
		}
		//System.out.println(ProductRelationGrpDict.toString());
		int ProductRelationGrpDictCount = ProductRelationGrpDict.getCountByName("GROUP_ID");
		del_flag = (String)ProductRelationGrpDict.getValueByName("del_flag");
		group_id = (String)ProductRelationGrpDict.getValueByName("GROUP_ID");
		dellOldData(group_id);		
		if((ProductRelationGrpDictCount==1)&&del_flag.equals("1")){
				
		}else{			
			for(int i=0;i<ProductRelationGrpDict.getCountByName("GROUP_ID");i++)
			{				
				Base.dict__set_val(ProductRelationGrpDict,"PARTY_ID",i,this.party_id);
				Base.dict__set_val(ProductRelationGrpDict,"PARTY_ROLE_ID",i,this.party_role_id);
				Base.dict__set_val(ProductRelationGrpDict,"OPER_REGION_ID",i,this.oper_region_id);
				Base.dict__set_val(ProductRelationGrpDict,"seq",i,"0");
				Base.dict__set_val(ProductRelationGrpDict,"state",i,"00A");
				
				Base.action__execute(dbName, ecode,ProductRelationGrpDict,i," insert into PRODUCT_RELATION_GROUP ($columns,OPER_DATE) values ($columns,sysdate) ",new String[]{});
			}			
		}
		return true ;
	}
	
	private void dellOldData(String sgroup_id) throws Exception{
		String group_id = sgroup_id;
		DynamicDict ProductOldRelationGrpDict = new DynamicDict();
		ProductOldRelationGrpDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from PRODUCT_RELATION_GROUP a where group_id = ? and state = '00A'   ", new String[]{group_id}, ecode);		
		for(int i = 0;i<ProductOldRelationGrpDict.getCountByName("GROUP_ID");i++){

			Base.update(JNDINames.PM_DATASOURCE, "update PRODUCT_RELATION_GROUP set party_id = ? , party_role_id = ? , oper_region_id = ? where group_id = ? and  product_id = ?", 
					new String[]{this.party_id,this.party_role_id,this.oper_region_id,group_id,(String)ProductOldRelationGrpDict.getValueByName("product_id",i)}, ecode);
			
			String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_RELATION_GROUP where group_id = ? and  product_id = ?", 
					new String[]{group_id,(String)ProductOldRelationGrpDict.getValueByName("product_id",i)}, ecode);
			Base.dict__set_val(ProductOldRelationGrpDict,"seq",i,seq);
			Base.action__execute(dbName, ecode,ProductOldRelationGrpDict,i," insert into ARC_PRODUCT_RELATION_GROUP ($columns,state,OPER_DATE) values ($columns,'00A',sysdate) ",new String[]{});
		}		
		Base.update(dbName," delete from PRODUCT_RELATION_GROUP where group_id = ?  ",new String []{group_id}, ecode, etype, edesc);
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductRelationGrpById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductRelationGrpByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductRelationGrpById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductRelationGrpDAO dao = new ProductRelationGrpDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
