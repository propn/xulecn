package com.ztesoft.vsop.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames ;
/**
 * <pre>
 * Title:
 * Description: 程序功能的描述 
 * </pre>
 * @author 
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：caozj  修改日期:20101020     修改内容: SQL语法修改informix数据库语法
 * </pre>
 */
public class VsopCompareDealMidDAO extends AbstractDAO {
	
	//查询SQL ORACLE
	private String SQL_SELECT_ORACLE = "select streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,suspend_reason,"
			+ "spid,product_id,package_id,pproduct_offer_id,status,subscribe_time,effective_time,expire_time,suspend_time,"
			+ "resume_time,last_use_time,silence,channel_player_id,flag,old_status,old_package_id,old_pproduct_offer_id,IS_ADJUST,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = package_id and rownum=1) prod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = pproduct_offer_id and rownum=1) pprod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = old_package_id and rownum=1) old_prod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = old_pproduct_offer_id and rownum=1) old_pprod_offer_name,"
			+ "(select product_name from product a where a.product_id = product_id and rownum=1) product_name,"
			+ "file_name from VSOP_ADJUST_COMPARE where 1=1 ";

	private String SQL_SELECT_KEY_ORACLE = "select streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,"
			+ "suspend_reason,spid,product_id,package_id,pproduct_offer_id,status,subscribe_time,effective_time,"
			+ "expire_time,suspend_time,resume_time,last_use_time,silence,channel_player_id,flag,old_status,old_package_id,"
			+ "old_pproduct_offer_id,file_name,IS_ADJUST,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = package_id and rownum=1) prod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = pproduct_offer_id and rownum=1) pprod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = old_package_id and rownum=1) old_prod_offer_name,"
			+ "(select prod_offer_name from prod_offer a where a.prod_offer_id = old_pproduct_offer_id and rownum=1) old_pprod_offer_name,"
			+ "(select product_name from product a where a.product_id = product_id and rownum=1) product_name"
			+ " from VSOP_ADJUST_COMPARE where streaming_no=? ";
	// 查询SQL INFORMIX
	private String SQL_SELECT_INFORMIX = "select streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,suspend_reason,"
			+ "spid,product_id,package_id,pproduct_offer_id,status,subscribe_time,effective_time,expire_time,suspend_time,"
			+ "resume_time,last_use_time,silence,channel_player_id,flag,old_status,old_package_id,old_pproduct_offer_id,IS_ADJUST,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = package_id ) prod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = pproduct_offer_id ) pprod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = old_package_id ) old_prod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = old_pproduct_offer_id ) old_pprod_offer_name,"
			+ "(select min(product_name) from product a where a.product_id = product_id ) product_name,"
			+ "file_name from VSOP_ADJUST_COMPARE where 1=1 ";

	private String SQL_SELECT_KEY_INFORMIX = "select streaming_no,subscription_id,oa,oa_type,da,da_type,fa,fa_type,withdrawer,withdraw_reason,"
			+ "suspend_reason,spid,product_id,package_id,pproduct_offer_id,status,subscribe_time,effective_time,"
			+ "expire_time,suspend_time,resume_time,last_use_time,silence,channel_player_id,flag,old_status,old_package_id,"
			+ "old_pproduct_offer_id,file_name,IS_ADJUST,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = package_id ) prod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = pproduct_offer_id ) pprod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = old_package_id ) old_prod_offer_name,"
			+ "(select min(prod_offer_name) from prod_offer a where a.prod_offer_id = old_pproduct_offer_id ) old_pprod_offer_name,"
			+ "(select min(product_name) from product a where a.product_id = product_id ) product_name"
			+ " from VSOP_ADJUST_COMPARE where streaming_no=? ";

	
	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from VSOP_ADJUST_COMPARE where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into VSOP_ADJUST_COMPARE (streaming_no, subscription_id, oa, oa_type, da, da_type, fa, fa_type, withdrawer, withdraw_reason, suspend_reason, spid, product_id, package_id, pproduct_offer_id, status, subscribe_time, effective_time, expire_time, suspend_time, resume_time, last_use_time, silence, channel_player_id, flag, old_status, old_package_id, old_pproduct_offer_id, file_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update VSOP_ADJUST_COMPARE set streaming_no=?, subscription_id=?, oa=?, oa_type=?, da=?, da_type=?, fa=?, fa_type=?, withdrawer=?, withdraw_reason=?, suspend_reason=?, spid=?, product_id=?, package_id=?, pproduct_offer_id=?, status=?, subscribe_time=?, effective_time=?, expire_time=?, suspend_time=?, resume_time=?, last_use_time=?, silence=?, channel_player_id=?, flag=?, old_status=?, old_package_id=?, old_pproduct_offer_id=?, file_name=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from VSOP_ADJUST_COMPARE where 1=1 ";
	
		
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public VsopCompareDealMidDAO() {

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
									
		params.add(map.get("old_status")) ;
									
		params.add(map.get("old_package_id")) ;
									
		params.add(map.get("old_pproduct_offer_id")) ;
									
		params.add(map.get("file_name")) ;
						
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
						
					
		params.add(vo.get("old_status")) ;
						
					
		params.add(vo.get("old_package_id")) ;
						
					
		params.add(vo.get("old_pproduct_offer_id")) ;
						
					
		params.add(vo.get("file_name")) ;
						
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
					
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
						
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
	public String getSelectSQL(){
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_INFORMIX;
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_ORACLE;
		}
		return this.SQL_SELECT_ORACLE ;
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
		if(CrmConstants.DB_TYPE_INFORMIX.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_KEY_INFORMIX;
		}
		if(CrmConstants.DB_TYPE_ORACLE.equals(CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE"))){
			return this.SQL_SELECT_KEY_ORACLE;
		}
	
		return this.SQL_SELECT_KEY_ORACLE;	
				
	}
	protected List translateKeyCondMap(Map keyCondMap) throws FrameException {
		// TODO Auto-generated method stub
		List keyParams = new ArrayList() ;
		keyParams.add(keyCondMap.get("streaming_no"));
		return keyParams;
	}
	public boolean delOrderComMid(String prodType,
									String accNbr,
									String productId) throws FrameException{
		String querySQL = "delete from VSOP_ADJUST_COMPARE where oa_type="+prodType+" and oa='"+accNbr+"' and product_id="+productId;
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
	public boolean updateOrderComMid(String prodType,
									 String accNbr,
									 String productId) throws FrameException{
		String querySQL = "update VSOP_ADJUST_COMPARE set is_adjust='2' where oa_type="+prodType+" and oa='"+accNbr+"' and product_id="+productId;
		int num = Base.update(this.getDbName(), querySQL, new String[]{}, 1);
		if(num > 0)return true;
		else return false;
	}
}
