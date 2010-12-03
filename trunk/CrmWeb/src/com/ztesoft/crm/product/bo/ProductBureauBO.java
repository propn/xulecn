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
import com.ztesoft.common.util.XMLSegBuilder;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ProductBureauDAO ;
import com.ztesoft.crm.product.dao.ProductDAO;
import com.ztesoft.crm.product.vo.ProductBureauVO;

public class ProductBureauBO extends DictAction  {
	   private   int etype;
	   private   int ecode;
	   private   String edesc;
	   ProductBureauDAO productBureauDAO = new ProductBureauDAO();
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
	
	 public String getBureau(DynamicDict dto) throws Exception{
		  Map param = Const.getParam(dto) ;
		  Map ProductBureau = (Map) param.get("ProductBureau") ;  	   	 
	      String  product_id =(String)ProductBureau.get("product_id");
	      String  parent_region_id =(String)ProductBureau.get("parent_region_id");
	      List  whereCondParams=new ArrayList();
	      whereCondParams.add(parent_region_id);
	      whereCondParams.add(product_id);
	      List list=productBureauDAO.findBySql(" select region_id,parent_region_id,region_name from region where parent_region_id = ? and region_id not in ( " +
	      		"select region_id from product_bureau where product_id = ? and state = '00A')",whereCondParams,
	  			  etype,ecode,edesc);
	      List resultLst=new ArrayList();
	     if (list!=null && !list.isEmpty()){	    	 
	   	      for(int i=0;i<list.size();i++){
	   	    	  HashMap map=(HashMap)list.get(i);
	   	    	  ProductBureauVO  productBureauVO=new ProductBureauVO();
	   	    	  productBureauVO.loadFromHashMap(map);
	   	    	  resultLst.add(productBureauVO);
	   	      }
	      }
	      System.out.println(((ArrayList)resultLst).toString());
	      String str= XMLSegBuilder.toXmlItems((ArrayList)resultLst);	      
	      return str;
     }
	 
	 /*public List getProductSeledBureau(DynamicDict dto) throws Exception{
		  Map param = Const.getParam(dto) ;
		  Map ProductBureau = (Map) param.get("ProductBureau") ;  	   	 
	      String  product_id =(String)ProductBureau.get("product_id");
	      List  whereCondParams=new ArrayList();
	      whereCondParams.add(product_id);
	      List list=productBureauDAO.findBySql(" select a.product_id,a.region_id,b.region_name,a.eff_date,a.exp_date,a.default_flag" +
	      		"  from product_bureau a, region b" +
	      		" where a.product_id = ? and a.region_id = b.region_id and a.state = '00A'",whereCondParams,
	  			  etype,ecode,edesc);	     
	      if (list==null) list=new ArrayList();
    	  return list;
    }*/
	 
	 
	
	/*public boolean insertProductBureau(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductBureau = (Map) param.get("ProductBureau") ;
		
		ProductBureauDAO dao = new ProductBureauDAO();
		boolean result = dao.insert(ProductBureau) ;
		return result ;
	}*/
	/**
	 * 产品发布区域维护
	 * */
	public boolean saveProductBureau(DynamicDict dto  ) throws Exception {		
		Map param = Const.getParam(dto) ;
		HashMap ProductBureauMap= (HashMap)param;		
		DynamicDict ProductBureauDict = new DynamicDict();
		ArrayList ProductBureau = (ArrayList) ProductBureauMap.get("ProductBureau") ;
		
		this.getGlobalData();		
		String PRODUCT_ID = "";
		String del_flag = "";
		for(int i = 0; i<ProductBureau.size(); i++){
			HashMap hm_temp = (HashMap)ProductBureau.get(i);
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
				ProductBureauDict.setValueByName(fieldname, fieldvalue, 1);
			}
		}
		System.out.println(ProductBureauDict.toString());
		int ProductBureauDictCount = ProductBureauDict.getCountByName("PRODUCT_ID");
		del_flag = (String)ProductBureauDict.getValueByName("del_flag");
		PRODUCT_ID = (String)ProductBureauDict.getValueByName("PRODUCT_ID");
		dellOldData(PRODUCT_ID);		
		if((ProductBureauDictCount==1)&&del_flag.equals("1")){
				
		}else{			
			for(int i=0;i<ProductBureauDict.getCountByName("region_id");i++)
			{				
				Base.dict__set_val(ProductBureauDict,"PARTY_ID",i,this.party_id);
				Base.dict__set_val(ProductBureauDict,"PARTY_ROLE_ID",i,this.party_role_id);
				Base.dict__set_val(ProductBureauDict,"OPER_REGION_ID",i,this.oper_region_id);
				Base.dict__set_val(ProductBureauDict,"seq",i,"0");
				Base.dict__set_val(ProductBureauDict,"state",i,"00A");
			}			
			for(int i=0;i<ProductBureauDict.getCountByName("region_id");i++)
			{
				Base.action__execute(dbName, ecode,ProductBureauDict,i," insert into PRODUCT_BUREAU ($columns,OPER_DATE) values ($columns,sysdate) ",new String[]{});			
			}
		}
		return true ;
	}
	
	private void dellOldData(String product_id) throws Exception{
		String PRODUCT_ID = product_id;
		this.getGlobalData();
		DynamicDict ProductOldBureauDict = new DynamicDict();
		ProductOldBureauDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from product_bureau a where product_id = ? and state = '00A'   ", new String[]{PRODUCT_ID}, ecode);		
		for(int i = 0;i<ProductOldBureauDict.getCountByName("region_id");i++){
			
			Base.update(JNDINames.PM_DATASOURCE, "update product_bureau set party_id = ? , party_role_id = ? , oper_region_id = ? where product_id = ? and  region_id = ?", 
					new String[]{this.party_id,this.party_role_id,this.oper_region_id,product_id,(String)ProductOldBureauDict.getValueByName("region_id",i)}, ecode);
			
			String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_BUREAU where product_id = ? and  region_id = ?", 
					new String[]{PRODUCT_ID,(String)ProductOldBureauDict.getValueByName("region_id",i)}, ecode);
			Base.dict__set_val(ProductOldBureauDict,"seq",i,seq);
			Base.action__execute(dbName, ecode,ProductOldBureauDict,i," insert into ARC_PRODUCT_BUREAU ($columns,state,OPER_DATE) values ($columns,'00A',sysdate) ",new String[]{});
		}		
		Base.update(dbName," delete from PRODUCT_BUREAU where PRODUCT_ID=? ",new String []{PRODUCT_ID}, ecode, etype, edesc);
	}

	
	/*public boolean updateProductBureau(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductBureau = (Map) param.get("ProductBureau") ;
		String keyStr = "product_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductBureau,  keyStr) ;
		ProductBureauDAO dao = new ProductBureauDAO();
		boolean result = dao.updateByKey( ProductBureau, keyCondMap );
		
		return result ;
	}
	
	public Map getProductById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductDAO dao = new ProductDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}	*/
	

	
	public PageModel searchProductBureauData(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		para.add(product_id);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		ProductBureauDAO dao = new ProductBureauDAO();
		String sql ="select a.product_id,a.region_id,b.region_name,a.eff_date,a.exp_date,a.default_flag" +
	      		"  from product_bureau a, region b" +
	      		" where a.product_id = ? and a.region_id = b.region_id and a.state = '00A'";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductBureauById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductBureauDAO dao = new ProductBureauDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	
	/*
	public List findProductBureauByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductBureauDAO dao = new ProductBureauDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductBureauById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductBureauDAO dao = new ProductBureauDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}*/
}
