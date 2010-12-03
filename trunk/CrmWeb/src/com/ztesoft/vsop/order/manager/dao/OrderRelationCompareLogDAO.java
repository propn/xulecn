package com.ztesoft.vsop.order.manager.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
/**
 * <pre>
 * Title:
 * Description: 程序功能的描述  订购关系比对数据持久化操作
 * </pre>
 * @author 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：caozj  修改日期:20100920     修改内容: SQL语法增加informix数据库语法
 * </pre>
 */
public class OrderRelationCompareLogDAO extends AbstractDAO {
	
	//查询SQL ORACLE
		private String SQL_SELECT = "select order_compare_id,streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,suspend_reason,spid,product_id,(select product_name from product p where p.product_id = c.product_id and rownum =1) product_name," +
			" package_id,(select p.prod_offer_name from prod_offer p where p.prod_offer_id =package_id  and rownum = 1 ) package_name,pproduct_offer_id,(select p.prod_offer_name from prod_offer p where p.prod_offer_id =c.pproduct_offer_id  and rownum = 1 ) pprod_offer_name," +
			" old_package_id,(select p.prod_offer_name from prod_offer p where p.prod_offer_id =c.old_package_id  and rownum = 1 ) old_package_name,old_pproduct_offer_id,(select p.prod_offer_name from prod_offer p where p.prod_offer_id =c.old_pproduct_offer_id  and rownum = 1 ) old_pprod_offer_name," +
			" status,subscribe_time,effective_time,expire_time,suspend_time,resume_time,last_use_time,silence,channel_player_id,flag,old_state,file_name,create_date from ORDER_RELATION_COMPARE_LOG c where 1=1 ";
	
	//	查询SQL informix
	private String SQL_SELECT_INFORMIX = "select order_compare_id,streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,suspend_reason,spid,c.product_id,"
			+ " (select min(product_name) from product p where p.product_id = c.product_id )  product_name,"
			+ " package_id,"
			+ " ( select min(p.prod_offer_name) from prod_offer p where p.prod_offer_id =package_id)  package_name,pproduct_offer_id,"
			+ " ( select min(p.prod_offer_name) from prod_offer p where p.prod_offer_id =c.pproduct_offer_id)  pprod_offer_name, old_package_id,"
			+ " ( select min(p.prod_offer_name) from prod_offer p where p.prod_offer_id =c.old_package_id)  old_package_name,old_pproduct_offer_id,"
			+ " ( select min(p.prod_offer_name) from prod_offer p where p.prod_offer_id =c.old_pproduct_offer_id)  old_pprod_offer_name,"
			+ " status,subscribe_time,effective_time,expire_time,suspend_time,resume_time,last_use_time,silence,channel_player_id,flag,old_state,file_name,c.create_date "
			+ " from ORDER_RELATION_COMPARE_LOG c  "
			+ " where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from order_relation_compare_log c where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ORDER_RELATION_COMPARE_LOG (order_compare_id, streaming_no, subscription_id, oa, oa_type, da, da_type, fa, fa_type, withdrawer, withdraw_reason, suspend_reason, spid, product_id, package_id, pproduct_offer_id, status, subscribe_time, effective_time, expire_time, suspend_time, resume_time, last_use_time, silence, channel_player_id, flag, old_state) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ORDER_RELATION_COMPARE_LOG set order_compare_id=?, streaming_no=?, subscription_id=?, oa=?, oa_type=?, da=?, da_type=?, fa=?, fa_type=?, withdrawer=?, withdraw_reason=?, suspend_reason=?, spid=?, product_id=?, package_id=?, pproduct_offer_id=?, status=?, subscribe_time=?, effective_time=?, expire_time=?, suspend_time=?, resume_time=?, last_use_time=?, silence=?, channel_player_id=?, flag=?, old_state=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ORDER_RELATION_COMPARE_LOG where 1=1 ";
	
		
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public OrderRelationCompareLogDAO() {

	}
	

	protected List translateKeyCondMap(Map keyCondMap) throws FrameException {
		// TODO Auto-generated method stub
		List keyParams = new ArrayList() ;
		keyParams.add(keyCondMap.get("order_compare_id"));
		return keyParams;
	}


	/**
	 * Insert参数设置
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
							
		params.add(map.get("order_compare_id")) ;
									
		params.add(map.get("streaming_no")) ;
									
		params.add(map.get("subscription_id")) ;
									
		params.add(map.get("oa")) ;
									
		params.add(map.get("oa_type")) ;
									
		params.add(map.get("da")) ;
									
		params.add(map.get("da_type")) ;
									
		params.add(map.get("fa")) ;
									
		params.add(map.get("fa_type")) ;
									
		params.add(map.get("withdrawer")) ;
									
		params.add(map.get("withdraw_reason")) ;
									
		params.add(map.get("suspend_reason")) ;
									
		params.add(map.get("spid")) ;
									
		params.add(map.get("product_id")) ;
									
		params.add(map.get("package_id")) ;
									
		params.add(map.get("pproduct_offer_id")) ;
									
		params.add(map.get("status")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("subscribe_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("effective_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("expire_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("suspend_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("resume_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("last_use_time" ))) ;
									
		params.add(map.get("silence")) ;
									
		params.add(map.get("channel_player_id")) ;
									
		params.add(map.get("flag")) ;
									
		params.add(map.get("old_state")) ;
						
		return params ;
	}
	

	/**
	 * update 参数设置
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
					
		params.add(vo.get("order_compare_id")) ;
						
					
		params.add(vo.get("streaming_no")) ;
						
					
		params.add(vo.get("subscription_id")) ;
						
					
		params.add(vo.get("oa")) ;
						
					
		params.add(vo.get("oa_type")) ;
						
					
		params.add(vo.get("da")) ;
						
					
		params.add(vo.get("da_type")) ;
						
					
		params.add(vo.get("fa")) ;
						
					
		params.add(vo.get("fa_type")) ;
						
					
		params.add(vo.get("withdrawer")) ;
						
					
		params.add(vo.get("withdraw_reason")) ;
						
					
		params.add(vo.get("suspend_reason")) ;
						
					
		params.add(vo.get("spid")) ;
						
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("package_id")) ;
						
					
		params.add(vo.get("pproduct_offer_id")) ;
						
					
		params.add(vo.get("status")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("subscribe_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("effective_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("expire_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("suspend_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("resume_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("last_use_time" ))) ;
						
					
		params.add(vo.get("silence")) ;
						
					
		params.add(vo.get("channel_player_id")) ;
						
					
		params.add(vo.get("flag")) ;
						
					
		params.add(vo.get("old_state")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE+" and order_compare_id=?";		
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		return this.SQL_UPDATE+" and order_compare_id=?";	
				
	}
	
	public String getSelectSQL(){
		//根据数据库类型返回 默认oracle数据库
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_INFORMIX;
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT ;
		}
		return this.SQL_SELECT ;
	}
	
	public String getSelectCountSQL(){
		return this.SQL_SELECT_COUNT ;
	}
	
	public String getInsertSQL(){
		return this.SQL_INSERT ;
	}
	
	public String getUpdateSQL(){
		return this.SQL_UPDATE ;
	}
	
	public String getDeleteSQL(){
		return this.SQL_DELETE ;
	}
	
	public String getSQLSQLByKey() throws FrameException {
								
		//根据数据库类型返回 默认oracle数据库
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_INFORMIX+" and order_compare_id=?";
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT +" and order_compare_id=?";
		}
		return this.SQL_SELECT+" and order_compare_id=?";	
				
	}
	
}
