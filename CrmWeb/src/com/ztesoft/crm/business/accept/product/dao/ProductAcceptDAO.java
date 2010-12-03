package com.ztesoft.crm.business.accept.product.dao;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;

public class ProductAcceptDAO extends AbstractDAO 
{
	//SQL
	//获得产品属性数据
	public static final String SELECT_PRODUCT_ATTR = " select a.*,b.attr_name,b.attr_code,a.attr_value_type_id input_method,b.attr_id,'' attr_value  "+
    								  " from  attribute b,product_attr a "+
								      " where b.state = '00A' "+
								      " and b.attr_id = a.attr_seq "+
								      " and a.state = '00A' "+
								      " and a.product_id  = ? ";
	
	//获得产品属性实例数据
	public static final String SELECT_PRODUCT_ATTR_INST = " select a.*, b.attr_name,b.attr_code,a.attr_value_type_id input_method,b.attr_id, "+
                                      " ( select attr_val from serv_product_attr c "+
                                      " where c.product_id = a.product_id "+
                                      " and c.attr_id=a.attr_seq  "+
                                      " and c.serv_product_id = ? ) attr_value "+
                                      " from  attribute b,product_attr a "+
                                      " where b.state = '00A' "+
                                      " and b.attr_id = a.attr_seq "+
                                      " and a.state = '00A' "+
                                      " and a.product_id  = ? ";
	
	//获得产品属性取值
	public static final String SELECT_PRODUCT_ATTR_VALUE = " select *  from  attribute_value where state = '00A' and attr_id  = ? ";
	
	//获得主产品的附属产品
	public static final String SELECT_OTHER_PRODUCT = " select b.*,a.max_count,d.prod_cat_name "+
								      " from  product_cat d, product b , product_bureau c , product_relation a "+
								      " where d.prod_cat_type = b.prod_cat_type "+
								      " and b.state = '00A' "+
								      " and b.product_id = a.prod_z_id "+
								      " and c.state = '00A' "+
								      " and c.product_id = a.prod_z_id "+
								      " and c.region_id = ? "+
								      " and a.state = '00A' "+
								      " and a.relation_type_id = '1' "+
								      " and a.prod_a_id = ? "+
								      " order by b.prod_cat_type ";
	
	//根据serv_id获得附属产品实例
	public static final String SELECT_SERV_PRODUCT = " select a.product_id, a.serv_product_id, a.serv_id, 'k' oper_type, "+
     								  " b.product_name, b.prod_cat_type "+
     								  " from product b, serv_product a "+
     								  " where b.state = '00A' "+
     								  " and b.product_id = a.product_id "+
     								  " and a.state = '00A' "+
     								  " and a.action_type <> '2' "+
     								  " and a.serv_id = ? ";
	
	//查询产品属性实例数据
	public static final String SELECT_SERV_ATTR = " select field_name,attr_val,serv_id,product_id "+
	 								  " from serv_attr "+
	 								  " where serv_id = ? ";
    
	//根据本地网、号码查询客户ID
	public static final String SELECT_CUST_ID = "select cust_id from serv where lan_id = ? and acc_nbr = ? and state = '00A'";
	
	//获得帐目类型
	public static final String SELECT_ACCT_TYPE = "select acct_item_type_id,name from acct_item_type";
	
	//获取主产品所有属性存放的表名
	public static final String SELECT_TABLE = "select distinct table_name from product_attr where state = '00A' and product_id = ?";
	
	//获得帐目类型
	public static final String SELECT_SERV_ACCT_ORACLE = " select a.serv_id,a.invoice_require_id,a.payment_rule_id,a.self_flag, "+
       							" a.acct_item_group_id, (select name from acct_item_type b where b.acct_item_type_id = a.acct_item_group_id and rownum < 2) name, "+
       							" a.acct_id,(select acct_name from acct c where c.acct_id = a.acct_id and c.state = '00A' and rownum < 2) acct_name "+
       							" from serv_acct a "+
       							" where a.serv_id = ? ";
	public static final String SELECT_SERV_ACCT_INFORMIX = " select a.serv_id,a.invoice_require_id,a.payment_rule_id,a.self_flag, "
			+ " a.acct_item_group_id, (select min(name) from acct_item_type b where b.acct_item_type_id = a.acct_item_group_id ) name, "
			+ " a.acct_id,(select min(acct_name) from acct c where c.acct_id = a.acct_id and c.state = '00A' ) acct_name "
			+ " from serv_acct a " + " where a.serv_id = ? ";
	
	
	public String getDbName(){
		return "" ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return "" ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return "" ;
				
	}
	
	public String getSelectSQL(){
		return "" ;
	}
	
	public String getSelectCountSQL(){
		return "" ;
	}
	
	public String getInsertSQL(){
		return "" ;
	}
	
	public String getUpdateSQL(){
		return "" ;
	}
	
	public String getDeleteSQL(){
		return "" ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
					
		return "" ;
				
	}
}
