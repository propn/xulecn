package com.ztesoft.vsop.paras.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames;

public class NeJavaEngineDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select id,name,rule_desc,rule_content,state,class_name,ceate_date from NE_JAVA_ENGINE where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from ne_java_engine where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into NE_JAVA_ENGINE (id, name, rule_desc, rule_content, state, class_name, ceate_date) values (?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update NE_JAVA_ENGINE set id=?, name=?, rule_desc=?, rule_content=?, state=?, class_name=?, ceate_date=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from NE_JAVA_ENGINE where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from NE_JAVA_ENGINE where id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update NE_JAVA_ENGINE set id=?, name=?, rule_desc=?, rule_content=?, state=?, class_name=?, ceate_date=? where id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select id,name,rule_desc,rule_content,state,class_name,ceate_date from NE_JAVA_ENGINE where id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public NeJavaEngineDAO() {

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
							
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		params.add(seqDao.getNextSequence("Ne_Java_Engine_Seq")) ;
				
		params.add(map.get("name")) ;
									
		params.add(map.get("rule_desc")) ;
									
		params.add(map.get("rule_content")) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("class_name")) ;
						
		params.add(DAOUtils.getCurrentDate()) ;
						
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
				
					
		params.add(vo.get("id")) ;
						
					
		params.add(vo.get("name")) ;
						
					
		params.add(vo.get("rule_desc")) ;
						
					
		params.add(vo.get("rule_content")) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("class_name")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("ceate_date" ))) ;
						
		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add(condParas.get(i));
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
							
		params.add(vo.get("id")) ;
									
		params.add(vo.get("name")) ;
									
		params.add(vo.get("rule_desc")) ;
									
		params.add(vo.get("rule_content")) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("class_name")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("ceate_date" ))) ;
						
							
		params.add(keyCondMap.get("id")) ;
						
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
							
		params.add(keyCondMap.get("id")) ;
						
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
