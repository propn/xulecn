package com.ztesoft.component.common.staticdata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;


public class DcSqlDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select dc_name , dc_desc , dc_sql from ( "+
								"	select d.dc_name,d.dc_desc,d.dc_sql  from dc_sql d  "+
								"		union all "+
								"	select a.attr_code ,a.attr_name , a.attr_desc   from attribute a where attr_code is not null ) where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ( "+
								"	select d.dc_name,d.dc_desc,d.dc_sql  from dc_sql d  "+
								"		union all "+
								"	select a.attr_code ,a.attr_name , a.attr_desc   from attribute a where attr_code is not null ) where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into DC_SQL (dc_name, dc_sql,dc_desc) values (?,?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update DC_SQL set dc_name=?, dc_sql=?,dc_desc=?  where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from DC_SQL where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from DC_SQL where dc_name=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update DC_SQL set dc_name=?, dc_sql=?,dc_desc=?  where dc_name=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select dc_name,dc_sql,dc_desc from DC_SQL where dc_name=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public DcSqlDAO() {

	}
	

	/**
	 * Insert��������
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;
				
		params.add((String)map.get("dc_name" ));
				
		params.add((String)map.get("dc_sql" ));
			
		params.add((String)map.get("dc_desc" ));
		
		return params ;
	}
	

	/**
	 * update ��������
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;
		
		List params = new ArrayList() ;
				
		params.add((String)vo.get("dc_name" ));
				
		params.add((String)vo.get("dc_sql" ));
			
		params.add((String)vo.get("dc_desc" ));
		
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add((String)condParas.get(i));
			}
		}
		return params ;
		
	}
	
		/**	
	 * �����������²�������
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
				
		params.add((String)vo.get("dc_name" ));
				
		params.add((String)vo.get("dc_sql" ));
				
		params.add((String)vo.get("dc_desc" ));
		
		params.add((String)keyCondMap.get("dc_name")) ;
				
		return params ;
	}
		
		/**
	 * ����������������
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */	
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;
		
		List params = new ArrayList() ;
				
		params.add((String)keyCondMap.get("dc_name")) ;
				
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
