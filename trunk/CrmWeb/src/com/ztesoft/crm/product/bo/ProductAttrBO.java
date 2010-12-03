package com.ztesoft.crm.product.bo;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.DatabaseFunction;
import com.ztesoft.common.dao.SQLWhereClause;
import com.ztesoft.common.dict.DictAction;
import com.ztesoft.common.dict.DictService;
import com.powerise.ibss.framework.Const ;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.dao.SQLWhereClause ;
import com.powerise.ibss.framework.FrameException;

import com.ztesoft.crm.product.dao.ProductAttrDAO ;

public class ProductAttrBO extends DictAction  {
	
	private   int ecode;
	private   int etype;
	private   String edesc;
	
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
	 * 新增产品属性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean insertProductAttr(DynamicDict dto  ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductAttr = (Map) param.get("ProductAttr") ;
				
		ProductAttr.put("state","00A");
		ProductAttr.put("seq","0");
		ProductAttr.put("ord_no","1");
		ProductAttr.put("ord_action_type","0");
		ProductAttr.put("event_seq","1");
		this.getOperData();
		ProductAttr.put("party_id",this.party_id);
		ProductAttr.put("party_role_id",this.party_role_id);
		ProductAttr.put("oper_region_id",this.oper_region_id);	
		
		//System.out.println(ProductAttr.toString());
		ProductAttrDAO dao = new ProductAttrDAO();
		boolean result = dao.insert(ProductAttr) ;
		return result ;
	}

	/**
	 * 修改产品属性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean updateProductAttr(DynamicDict dto ) throws Exception {
		Map param = Const.getParam(dto) ;
		Map ProductAttr = (Map) param.get("ProductAttr") ;
		
		ProductAttr.put("ord_no","2");
		ProductAttr.put("ord_action_type","1");
		ProductAttr.put("event_seq","1");
		
		this.getOperData();
		ProductAttr.put("party_id",this.party_id);
		ProductAttr.put("party_role_id",this.party_role_id);
		ProductAttr.put("oper_region_id",this.oper_region_id);	
		
		String product_id = (String)ProductAttr.get("product_id");
		String attr_seq = (String)ProductAttr.get("attr_seq");
		
		saveOldData(product_id,attr_seq);	
		//System.out.println(ProductAttr.toString());
		
		String keyStr = "attr_seq,product_id";
		Map keyCondMap  = Const.getMapForTargetStr(ProductAttr,  keyStr) ;
		ProductAttrDAO dao = new ProductAttrDAO();
		boolean result = dao.updateByKey( ProductAttr, keyCondMap );
		
		return result ;
	}
	/**
	 * 保存老的产品属性数据
	 * @param product_id
	 * @param attr_seq
	 * @throws Exception
	 */
	private void saveOldData(String product_id,String attr_seq)  throws Exception{		
		DynamicDict ProductAttrOldDict = Base.query(JNDINames.PM_DATASOURCE, "select  to_char(a.$columns)," +
				"to_char(a.oper_date,"+DatabaseFunction.getDataFormat(2)+") oper_date  " +
				"from PRODUCT_ATTR a where product_id = ? and attr_seq = ? and state = '00A'   ", new String[]{product_id,attr_seq}, ecode);	
		String seq = Base.query_string(JNDINames.PM_DATASOURCE, "select nvl(max(seq),0)+1 from ARC_PRODUCT_ATTR where product_id = ? AND  attr_seq = ? ", 
				new String[]{product_id,attr_seq}, ecode);
		for (int i = 0; i < ProductAttrOldDict.getCountByName("ATTR_SEQ"); i++){
			Base.dict__set_val(ProductAttrOldDict,"seq",i,seq);
			Base.action__execute(JNDINames.PM_DATASOURCE, ecode,ProductAttrOldDict,i," insert into ARC_PRODUCT_ATTR ($columns,state,OPER_DATE) values ($columns,'00X',sysdate) ",new String[]{});
		}
	}
	
	/**
	 * 查询产品属性 
	 * @param dto
	 * @return PageModel
	 * @throws Exception
	 */
	public PageModel searchProductAttrData(DynamicDict dto ) throws Exception {
		
		PageModel result = new PageModel();
		//条件处理
		Map param = Const.getParam(dto) ;
		List para = new ArrayList() ;
		String product_id  = Const.getStrValue(param,"product_id");
		para.add(product_id);

		int pageSize = Const.getPageSize(param) ;
		int pageIndex = Const.getPageIndex(param) ;

		//调用DAO代码
		ProductAttrDAO dao = new ProductAttrDAO();
		String sql ="select a.ATTR_SEQ,a.CNAME,a.STATICPARAM_TYPE,a.PRODUCT_ID,a.TABLE_NAME,(select a.attr_value_desc table_cname from attribute_value a,attribute b " +
				"        where b.attr_code = 'PRODUCT_ATTR_TABLE_NAME' and a.attr_id = b.attr_id and a.attr_value = a.TABLE_NAME) table_cname," +
				"a.ATTR_VALUE,a.ALLOW_CUSTOMIZED_FLAG,a.ATTR_VALUE_TYPE_ID,a.ATTR_VALUE_UNIT_ID,a.ATTR_CHARACTER," +
				"a.IF_DEFAULT_VALUE,a.DEFAULT_VALUE,a.ORDER_ID,a.attr_length,a.is_null,a.action_id,a.is_check,a.FIELD_NAME,a.IS_MAKE_TO_TABLE,a.is_edit," +
				"a.IS_MAKE,a.CHECK_MESSAGE,a.ATTR_FORMAT,a.COLSPAN,a.PAGE_URL,a.PAGE_FIELD_NAME, a.PAGE_FIELD,a.MAKE_FIELD,a.PARAM_FIELD,a.PARAM_FIELD_NAME," +
				"a.IS_SEND_BILL,a.IS_RELA_PRICE,a.IS_PRINT,a.STATE,a.SEQ,a.OPER_DATE,a.PARTY_ID,a.PARTY_ROLE_ID,a.OPER_REGION_ID,a.ORD_ACTION_TYPE," +
				"a.ORD_NO,b.attr_id,b.attr_desc ATTR_NAME,b.SEND_BILL,b.input_method, b.value_type" +
				" from product_attr a, ATTRIBUTE b " +
				" where a.product_id = ? and a.attr_seq = b.attr_id and b.state <> '00X' and a.state <> '00X' " +
				"order by a.ORDER_ID asc";
		
		List list = dao.findBySql(sql,para);
		result.setList(list);
		result.setPageSize(pageSize);
		result.setPageIndex(pageIndex);
		
		return result ;
		
		/*String querySQL ="select a.ATTR_SEQ,a.PRODUCT_ID,a.ATTR_VALUE,a.ALLOW_CUSTOMIZED_FLAG,a.ATTR_VALUE_TYPE_ID,a.ATTR_VALUE_UNIT_ID,a.ATTR_CHARACTER," +
		"a.IF_DEFAULT_VALUE,a.DEFAULT_VALUE,a.ORDER_ID,a.attr_length,a.is_null,a.action_id,a.is_check,a.FIELD_NAME,a.IS_MAKE_TO_TABLE,a.is_edit," +
		"a.IS_MAKE,a.CHECK_MESSAGE,a.ATTR_FORMAT,a.COLSPAN,a.PAGE_URL,a.PAGE_FIELD_NAME, a.PAGE_FIELD,a.MAKE_FIELD,a.PARAM_FIELD,a.PARAM_FIELD_NAME," +
		"a.IS_SEND_BILL,a.IS_RELA_PRICE,a.IS_PRINT,a.STATE,a.SEQ,a.OPER_DATE,a.PARTY_ID,a.PARTY_ROLE_ID,a.OPER_REGION_ID,a.ORD_ACTION_TYPE," +
		"a.ORD_NO,b.attr_id,b.attr_desc ATTR_NAME,b.SEND_BILL,b.input_method, b.value_type" +
		" from product_attr a, ATTRIBUTE b " +
		" where a.product_id = ? and a.attr_seq = b.attr_id and b.state <> '00X' and a.state <> '00X' " +
		"order by a.ORDER_ID asc";
		//String countSQL ="select count(a.attr_seq) as col_counts from product_attr a, ATTRIBUTE b " +
		//		" where a.product_id = ? and a.attr_seq = b.attr_id and b.state <> '00X' and a.state <> '00X' ";
		String countSQL ="select count(*) as col_counts from product_attr a where a.product_id = ? and a.state <> '00X' ";
				
		
		DynamicDict AttrDict = new DynamicDict();
		AttrDict = Base.queryPage(JNDINames.PM_DATASOURCE, querySQL, countSQL, para, pageIndex, pageSize, 
				AttrDict,Const.ACTION_RESULT, ecode, etype, edesc) ;
		
		return DataTranslate._PageModel(AttrDict.m_Values.get(Const.ACTION_RESULT)) ;*/
	}
	
	/**
	 * 根据库表主键查询对象
	 */
	public Map getProductAttrById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto) ;
		ProductAttrDAO dao = new ProductAttrDAO();
		Map result = dao.findByPrimaryKey( keyCondMap ) ;
		
		return result ;
	}
	

	public List findProductAttrByCond(DynamicDict dto ) throws Exception {
		//条件处理
		String filterStr = "" ;
		Map changeName = null ; 
		SQLWhereClause where = new SQLWhereClause(dto , filterStr , changeName,SQLWhereClause.NO_PAGING) ;
		
		//组织调用DAO代码参数、
		String whereCond = where.getWhereCond() ;
		List para = where.getPara() ;
		//调用DAO代码
		ProductAttrDAO dao = new ProductAttrDAO();
		List result = dao.findByCond( whereCond, para) ;
		
		return result ;
	}
	
	/**
	 * 删除产品属性
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public boolean deleteProductAttrById(DynamicDict dto ) throws Exception {
		//调用DAO代码
		Map keyCondMap = Const.getParam(dto)  ;
		ProductAttrDAO dao = new ProductAttrDAO();
		String product_id = (String)keyCondMap.get("product_id");
		String attr_seq = (String)keyCondMap.get("attr_seq");
		
		Base.update(JNDINames.PM_DATASOURCE, "update product_attr set party_id = ? , party_role_id = ? , oper_region_id = ? where product_id = ? and attr_seq = ?", 
				new String[]{this.party_id,this.party_role_id,this.oper_region_id,product_id,attr_seq}, ecode);			
		
		
		saveOldData(product_id,attr_seq);
		
		boolean result = dao.deleteByKey( keyCondMap ) > 0 ;
		
		return result ;
	}
}
