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
import com.ztesoft.crm.product.dao.ProductConfGrpProdDAO ;

public class ProductConfGrpProdBO extends DictAction  {
	
	private   int etype;
	   private   int ecode;
	   private   String edesc;
	   private String dbName = JNDINames.PM_DATASOURCE ;
	   
	   DictService dictService = new DictService();
		private String party_id ="";
		private String party_role_id ="";
		private String oper_region_id ="";
		
		private void getOperData() throws Exception{
			this.party_id = dictService.getGlobalVar("vg_oper_code");
			this.party_role_id = dictService.getGlobalVar("vg_oper_id");		
			this.oper_region_id = dictService.getGlobalVar("vg_business_id");
		}
	/**
	 * 群增加
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean insertProductConfGrp(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductConfGrp = (Map) param.get("ProductConfGrp") ;
		
		String conf_group_id = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(conf_group_id),0)+1 from PRODUCT_CONF_GROUP", 
				new String[]{}, ecode);
		String current_date = Base.query_string(JNDINames.PM_DATASOURCE, "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss' ) from dual", 
				new String[]{}, ecode);
		
		ProductConfGrp.put("conf_group_id", conf_group_id);
		ProductConfGrp.put("state", "00A");
		ProductConfGrp.put("state_date", current_date);
		ProductConfGrp.put("CREATE_DATE", current_date);
		
		this.getOperData();
		ProductConfGrp.put("party_id",this.party_id);
		ProductConfGrp.put("party_role_id",this.party_role_id);
		ProductConfGrp.put("oper_region_id",this.oper_region_id);	
		
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		boolean result = dao.insert(ProductConfGrp) ;
		return result ;
	}

	/**
	 * 群修改
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean updateProductConfGrp(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductConfGrp = (Map) param.get("ProductConfGrp") ;
		String current_date = Base.query_string(JNDINames.PM_DATASOURCE, "select to_char(sysdate,'yyyy-mm-dd hh24:mi:ss' ) from dual", 
				new String[]{}, ecode);
		
		ProductConfGrp.put("state_date", current_date);
		this.getOperData();
		ProductConfGrp.put("party_id",this.party_id);
		ProductConfGrp.put("party_role_id",this.party_role_id);
		ProductConfGrp.put("oper_region_id",this.oper_region_id);
		
		String keyStr = "conf_group_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductConfGrp,  keyStr) ;
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		boolean result = dao.updateByKey( ProductConfGrp, keyCondMap );
		
		return result ;
	}
	
	/**
	 * 查询群
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductConfGrpData(DynamicDict dto ) throws Exception {
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String query_type  = Const.getStrValue(param,"query_type");
		String query_data  = Const.getStrValue(param,"query_data");
		String group_type  = Const.getStrValue(param,"group_type");
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
				
		para.add(query_data);
		para.add(group_type);
		String sql = "";
		if(query_type.equals("1")){
			sql ="select * from PRODUCT_CONF_GROUP where conf_group_id = ? and group_type=? and  state = '00A' ";
		}else if(query_type.equals("2")){
			sql ="select * from PRODUCT_CONF_GROUP where conf_group_name like '%'||?||'%' and group_type=? and state = '00A'";
		}

		//调用DAO代码
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();		
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		
		return result ;
	}
	
	
	
	/**
	 * 群间关系,查询可选群
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductGroupRelationData(DynamicDict dto ) throws Exception {
				
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String group_a_id  = Const.getStrValue(param,"group_a_id");
		String group_type  = Const.getStrValue(param,"group_type");
		
		//int product_id = Integer.parseInt(sproduct_id);
		
		para.add(group_type);
		para.add(group_a_id);	
		para.add(group_a_id);	
		
		
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		String sql ="select a.*,a.conf_group_id group_z_id,a.conf_group_name group_z_name from product_conf_group a where a.group_type = ? " +
				" and a.state = '00A' and a.conf_group_id != ? and a.conf_group_id not in( " +
				" select group_z_id from product_group_relation where group_a_id = ? and state = '00A')";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	/**
	 * 群间关系,查询已选群
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public PageModel searchProductGroupRelationData2(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		Map param = Const.getParam(dto) ;
		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;
		List para = new ArrayList() ;
		String group_a_id  = Const.getStrValue(param,"group_a_id");
		String group_type  = Const.getStrValue(param,"group_type");	
		
		para.add(group_a_id);		
		para.add(group_type);
			
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		String sql ="select a.*,b.conf_group_name group_z_name from product_group_relation a,product_conf_group b where a.group_a_id = ? " +
				"and b.group_type = ? and a.group_z_id = b.conf_group_id and a.state = '00A' and b.state = '00A' ";
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		return result ;		
	}
	
	/**
	 * 产品群间关系维护	
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean saveProductGroupRelation(DynamicDict dto  ) throws Exception {	
		Map param = Const.getParam(dto) ;
		HashMap ProductGroupRelationMap= (HashMap)param;		
		DynamicDict ProductGroupRelationDict = new DynamicDict();
		ArrayList ProductGroupRelation = (ArrayList) ProductGroupRelationMap.get("ProductGroupRelation") ;
		
		this.getOperData();
		
		String group_a_id = "";
		String del_flag = "";
		for(int i = 0; i<ProductGroupRelation.size(); i++){
			HashMap hm_temp = (HashMap)ProductGroupRelation.get(i);
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
				ProductGroupRelationDict.setValueByName(fieldname, fieldvalue, 1);
			}
		}
		System.out.println(ProductGroupRelationDict.toString());
		int ProductGroupRelationDictCount = ProductGroupRelationDict.getCountByName("GROUP_A_ID");
		del_flag = (String)ProductGroupRelationDict.getValueByName("DEL_FLAG");
		group_a_id = (String)ProductGroupRelationDict.getValueByName("GROUP_A_ID");
		dellOldData(group_a_id);		
		if((ProductGroupRelationDictCount==1)&&del_flag.equals("1")){
				
		}else{			
			for(int i=0;i<ProductGroupRelationDict.getCountByName("GROUP_A_ID");i++)
			{				
				Base.dict__set_val(ProductGroupRelationDict,"PARTY_ID",i,this.party_id);
				Base.dict__set_val(ProductGroupRelationDict,"PARTY_ROLE_ID",i,this.party_role_id);
				Base.dict__set_val(ProductGroupRelationDict,"OPER_REGION_ID",i,this.oper_region_id);
				Base.dict__set_val(ProductGroupRelationDict,"seq",i,"0");
				Base.dict__set_val(ProductGroupRelationDict,"state",i,"00A");
			}			
			for(int i=0;i<ProductGroupRelationDict.getCountByName("GROUP_A_ID");i++)
			{
				Base.action__execute(dbName, ecode,ProductGroupRelationDict,i," insert into PRODUCT_GROUP_RELATION ($columns,OPER_DATE) values ($columns,sysdate) ",new String[]{});			
			}
		}
		return true ;
	}
	
	private void dellOldData(String sgroup_a_id) throws Exception{
		String group_a_id = sgroup_a_id;
		DynamicDict ProductGroupOldRelationDict = new DynamicDict();
		ProductGroupOldRelationDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.eff_date,'yyyy-mm-dd hh24:mi:ss') eff_date, " +
				"to_char(a.exp_date,'yyyy-mm-dd hh24:mi:ss') exp_date  " +
				"from PRODUCT_GROUP_RELATION a where group_a_id = ? and state = '00A'   ", new String[]{group_a_id}, ecode);		
		for(int i = 0;i<ProductGroupOldRelationDict.getCountByName("GROUP_Z_ID");i++){
			
			Base.update(JNDINames.PM_DATASOURCE, "update PRODUCT_GROUP_RELATION set party_id = ? , party_role_id = ? , oper_region_id = ? where  group_a_id = ? and group_z_id = ? ", 
					new String[]{this.party_id,this.party_role_id,this.oper_region_id,group_a_id,(String)ProductGroupOldRelationDict.getValueByName("GROUP_Z_ID",i)}, ecode);
						
			String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_GROUP_RELATION where group_a_id = ? and group_z_id = ?", 
					new String[]{group_a_id,(String)ProductGroupOldRelationDict.getValueByName("GROUP_Z_ID",i)}, ecode);
			Base.dict__set_val(ProductGroupOldRelationDict,"seq",i,seq);
			Base.action__execute(dbName, ecode,ProductGroupOldRelationDict,i," insert into ARC_PRODUCT_GROUP_RELATION ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}		
		Base.update(dbName," delete from PRODUCT_GROUP_RELATION where group_a_id=? ",new String []{group_a_id}, ecode, etype, edesc);
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductConfGrpById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductConfGrpByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	
	public boolean deleteProductConfGrpById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductConfGrpProdDAO dao = new ProductConfGrpProdDAO();
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
