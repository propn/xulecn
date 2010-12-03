package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TpmBusiPriceParaDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select price_id,owner_object_type,owner_object_id,para_name,seq,para_cname,para_type,data_type,para_value,para_value2,compute_rela_type,compute_method,compute_value,order_id,state,party_id,party_role_id,oper_date,para_value_cname,para_value2_cname from TPM_BUSI_PRICE_PARA where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tpm_busi_price_para where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into TPM_BUSI_PRICE_PARA (price_id, owner_object_type, owner_object_id, para_name, seq, para_cname, para_type, data_type, para_value, para_value2, compute_rela_type, compute_method, compute_value, order_id, state, party_id, party_role_id, oper_date, para_value_cname, para_value2_cname) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TPM_BUSI_PRICE_PARA set price_id=?, owner_object_type=?, owner_object_id=?, para_name=?, seq=?, para_cname=?, para_type=?, data_type=?, para_value=?, para_value2=?, compute_rela_type=?, compute_method=?, compute_value=?, order_id=?, state=?, party_id=?, party_role_id=?, oper_date=?, para_value_cname=?, para_value2_cname=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from TPM_BUSI_PRICE_PARA where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TPM_BUSI_PRICE_PARA where price_id=? and owner_object_type=? and owner_object_id=? and para_name=? and seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update TPM_BUSI_PRICE_PARA set price_id=?, owner_object_type=?, owner_object_id=?, para_name=?, seq=?, para_cname=?, para_type=?, data_type=?, para_value=?, para_value2=?, compute_rela_type=?, compute_method=?, compute_value=?, order_id=?, state=?, party_id=?, party_role_id=?, oper_date=?, para_value_cname=?, para_value2_cname=? where price_id=? and owner_object_type=? and owner_object_id=? and para_name=? and seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select price_id,owner_object_type,owner_object_id,para_name,seq,para_cname,para_type,data_type,para_value,para_value2,compute_rela_type,compute_method,compute_value,order_id,state,party_id,party_role_id,oper_date,para_value_cname,para_value2_cname from TPM_BUSI_PRICE_PARA where price_id=? and owner_object_type=? and owner_object_id=? and para_name=? and seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public TpmBusiPriceParaDAO() {

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
							
		params.add(map.get("price_id")) ;
									
		params.add(map.get("owner_object_type")) ;
									
		params.add(map.get("owner_object_id")) ;
									
		params.add(map.get("para_name")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("para_cname")) ;
									
		params.add(map.get("para_type")) ;
									
		params.add(map.get("data_type")) ;
									
		params.add(map.get("para_value")) ;
									
		params.add(map.get("para_value2")) ;
									
		params.add(map.get("compute_rela_type")) ;
									
		params.add(map.get("compute_method")) ;
									
		params.add(map.get("compute_value")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("para_value_cname")) ;
									
		params.add(map.get("para_value2_cname")) ;
						
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
				
					
		params.add(vo.get("price_id")) ;
						
					
		params.add(vo.get("owner_object_type")) ;
						
					
		params.add(vo.get("owner_object_id")) ;
						
					
		params.add(vo.get("para_name")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("para_cname")) ;
						
					
		params.add(vo.get("para_type")) ;
						
					
		params.add(vo.get("data_type")) ;
						
					
		params.add(vo.get("para_value")) ;
						
					
		params.add(vo.get("para_value2")) ;
						
					
		params.add(vo.get("compute_rela_type")) ;
						
					
		params.add(vo.get("compute_method")) ;
						
					
		params.add(vo.get("compute_value")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("para_value_cname")) ;
						
					
		params.add(vo.get("para_value2_cname")) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * 根据主键更新参数设置
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo , Map keyCondMap) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(vo.get("price_id")) ;
									
		params.add(vo.get("owner_object_type")) ;
									
		params.add(vo.get("owner_object_id")) ;
									
		params.add(vo.get("para_name")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("para_cname")) ;
									
		params.add(vo.get("para_type")) ;
									
		params.add(vo.get("data_type")) ;
									
		params.add(vo.get("para_value")) ;
									
		params.add(vo.get("para_value2")) ;
									
		params.add(vo.get("compute_rela_type")) ;
									
		params.add(vo.get("compute_method")) ;
									
		params.add(vo.get("compute_value")) ;
									
		params.add(vo.get("order_id")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("para_value_cname")) ;
									
		params.add(vo.get("para_value2_cname")) ;
						
							
		params.add(keyCondMap.get("price_id")) ;
									
		params.add(keyCondMap.get("owner_object_type")) ;
									
		params.add(keyCondMap.get("owner_object_id")) ;
									
		params.add(keyCondMap.get("para_name")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
		return params ;
	}
		
		/**
	 * 主键条件参数设置
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
							
		params.add(keyCondMap.get("price_id")) ;
									
		params.add(keyCondMap.get("owner_object_type")) ;
									
		params.add(keyCondMap.get("owner_object_id")) ;
									
		params.add(keyCondMap.get("para_name")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
		return params  ;
	}
	
	
	public String getDbName(){
		return this.dbName ;
	}
	
	public String getDeleteSQLByKey() throws FrameException {
					
		return this.SQL_DELETE_KEY ;
				
	}
	
	public String getUpdateSQLByKey() throws FrameException {
					
		return this.SQL_UPDATE_KEY ;
				
	}
	
	public String getSelectSQL(){
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
					
		return this.SQL_SELECT_KEY ;
				
	}
	
}
