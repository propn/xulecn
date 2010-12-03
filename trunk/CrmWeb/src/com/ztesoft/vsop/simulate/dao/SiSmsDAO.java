package com.ztesoft.vsop.simulate.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class SiSmsDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select radom_code,prod_type,product_no,receive_time,sms_content,sms_type from SI_SMS where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_sms where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SI_SMS (radom_code, product_no, receive_time, sms_content, sms_type,prod_type) values (?, ?, ?, ?, ?,?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SI_SMS set radom_code=?, product_no=?, receive_time=?, sms_content=?, sms_type=?,prod_type=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from SI_SMS where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SI_SMS where radom_code=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SI_SMS set radom_code=?, product_no=?, receive_time=?, sms_content=?, sms_type=?,prod_type=? where radom_code=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select radom_code,product_no,receive_time,sms_content,sms_type,prod_type from SI_SMS where radom_code=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SiSmsDAO() {

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
		SequenceManageDAO dao = new SequenceManageDAOImpl();
		String id = dao.getNextSequence("Si_sms_SEQ");
					
		params.add(id) ;
									
		params.add(map.get("product_no")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("receive_time" ))) ;
									
		params.add(map.get("sms_content")) ;
									
		params.add(map.get("sms_type")) ;
		
		params.add(map.get("prod_type")) ;
						
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
				
					
		params.add(vo.get("radom_code")) ;
						
					
		params.add(vo.get("product_no")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("receive_time" ))) ;
						
					
		params.add(vo.get("sms_content")) ;
						
					
		params.add(vo.get("sms_type")) ;
		
		params.add(vo.get("prod_type")) ;
						
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
							
		params.add(vo.get("radom_code")) ;
									
		params.add(vo.get("product_no")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("receive_time" ))) ;
									
		params.add(vo.get("sms_content")) ;
									
		params.add(vo.get("sms_type")) ;
		
		params.add(vo.get("prod_type")) ;
						
							
		params.add(keyCondMap.get("radom_code")) ;
						
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
							
		params.add(keyCondMap.get("radom_code")) ;
						
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
