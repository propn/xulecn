package com.ztesoft.vsop.module.dao;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.dao.SequenceManageDAOImpl;
import com.ztesoft.common.util.JNDINames ;

public class PbBusinessObjDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select business_obj_id,obj_type_code,business_obj_name,class_name,method_name,create_date,state,state_date from PB_BUSINESS_OBJ where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from pb_business_obj where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PB_BUSINESS_OBJ (business_obj_id, obj_type_code, business_obj_name, class_name, method_name, create_date, state, state_date) values (?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PB_BUSINESS_OBJ set business_obj_id=?, obj_type_code=?, business_obj_name=?, class_name=?, method_name=?, create_date=?, state=?, state_date=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PB_BUSINESS_OBJ where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PB_BUSINESS_OBJ where business_obj_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PB_BUSINESS_OBJ set business_obj_id=?, obj_type_code=?, business_obj_name=?, class_name=?, method_name=?, create_date=?, state=?, state_date=? where business_obj_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select business_obj_id,obj_type_code,business_obj_name,class_name,method_name,create_date,state,state_date from PB_BUSINESS_OBJ where business_obj_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public PbBusinessObjDAO() {

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
		params.add(seqDao.getNextSequence("pb_business_obj_seq")) ;
									
		params.add(map.get("obj_type_code")) ;
									
		params.add(map.get("business_obj_name")) ;
									
		params.add(map.get("class_name")) ;
									
		params.add(map.get("method_name")) ;
						
		params.add(DAOUtils.getCurrentDate()) ;
									
		params.add("10A") ;
						
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
				
					
		params.add(vo.get("business_obj_id")) ;
						
					
		params.add(vo.get("obj_type_code")) ;
						
					
		params.add(vo.get("business_obj_name")) ;
						
					
		params.add(vo.get("class_name")) ;
						
					
		params.add(vo.get("method_name")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
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
							
		params.add(vo.get("business_obj_id")) ;
									
		params.add(vo.get("obj_type_code")) ;
									
		params.add(vo.get("business_obj_name")) ;
									
		params.add(vo.get("class_name")) ;
									
		params.add(vo.get("method_name")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;
						
							
		params.add(keyCondMap.get("business_obj_id")) ;
						
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
							
		params.add(keyCondMap.get("business_obj_id")) ;
						
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
