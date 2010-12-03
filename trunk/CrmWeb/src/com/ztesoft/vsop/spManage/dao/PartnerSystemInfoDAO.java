package com.ztesoft.vsop.spManage.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.Const;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class PartnerSystemInfoDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select partner_system_info_id,p.system_code,(select system_name from system_info s where s.system_code=p.system_code) as system_name,partner_id,create_date,state,state_date from PARTNER_SYSTEM_INFO p where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from partner_system_info where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PARTNER_SYSTEM_INFO (partner_system_info_id, system_code, partner_id, create_date, state, state_date) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PARTNER_SYSTEM_INFO set partner_system_info_id=?, system_code=?, partner_id=?, create_date=?, state=?, state_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from PARTNER_SYSTEM_INFO where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PARTNER_SYSTEM_INFO where partner_system_info_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PARTNER_SYSTEM_INFO set partner_system_info_id=?, system_code=?, partner_id=?, create_date=?, state=?, state_date=? where partner_system_info_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select partner_system_info_id,p.system_code,(select system_name from system_info s where s.system_code=p.system_code) as system_name,partner_id,create_date,state,state_date from PARTNER_SYSTEM_INFO p where p.partner_system_info_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE ;


	public PartnerSystemInfoDAO() {

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
							
		params.add(map.get("partner_system_info_id")) ;
									
		params.add(map.get("system_code")) ;
									
		params.add(map.get("partner_id")) ;
						
		params.add(DAOUtils.getCurrentTimestamp()) ;
									
		params.add("0") ;//params.add(map.get("state")) ;
						
		params.add(DAOUtils.getCurrentTimestamp()) ;
						
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
				
					
		params.add(vo.get("partner_system_info_id")) ;
						
					
		params.add(vo.get("system_code")) ;
						
					
		params.add(vo.get("partner_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.getCurrentTimestamp()) ;
						
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
							
		params.add(vo.get("partner_system_info_id")) ;
									
		params.add(vo.get("system_code")) ;
									
		params.add(vo.get("partner_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
							
		params.add(keyCondMap.get("partner_system_info_id")) ;
						
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
							
		params.add(keyCondMap.get("partner_system_info_id")) ;
						
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
	
	public boolean batchDelete(String partnerId ) throws Exception {
		String SQL_BatchDelete="delete from PARTNER_SYSTEM_INFO where partner_id=?";
		List para = new ArrayList() ;
		para.add(partnerId) ;
		return Base.update( getDbName() , SQL_BatchDelete , para, Const.UN_JUDGE_ERROR, Const.UN_JUDGE_ERROR, null)>1 ;
	}
	
	public int[] batchInsertPlatForm(List para ) throws Exception{
		return Base.batchUpdate(getDbName() , this.SQL_INSERT, para,  Const.UN_JUDGE_ERROR,  Const.UN_JUDGE_ERROR, null) ;
	}
}
