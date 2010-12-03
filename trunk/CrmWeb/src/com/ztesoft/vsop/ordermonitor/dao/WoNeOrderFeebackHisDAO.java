package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoNeOrderFeebackHisDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select id,ne_order_id,feeback_info,create_date,deal_date,state,deal_desc from WO_NE_ORDER_FEEBACK_HIS where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_ne_order_feeback_his where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_NE_ORDER_FEEBACK_HIS (id, ne_order_id, feeback_info, create_date, deal_date, state, deal_desc) values (?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update WO_NE_ORDER_FEEBACK_HIS set id=?, ne_order_id=?, feeback_info=?, create_date=?, deal_date=?, state=?, deal_desc=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from WO_NE_ORDER_FEEBACK_HIS where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from WO_NE_ORDER_FEEBACK_HIS where id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update WO_NE_ORDER_FEEBACK_HIS set id=?, ne_order_id=?, feeback_info=?, create_date=?, deal_date=?, state=?, deal_desc=? where id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select id,ne_order_id,feeback_info,create_date,deal_date,state,deal_desc from WO_NE_ORDER_FEEBACK_HIS where ne_order_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoNeOrderFeebackHisDAO() {

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
							
		params.add(map.get("id")) ;
									
		params.add(map.get("ne_order_id")) ;
									
		params.add(map.get("feeback_info")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("deal_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("deal_desc")) ;
						
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
						
					
		params.add(vo.get("ne_order_id")) ;
						
					
		params.add(vo.get("feeback_info")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("deal_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("deal_desc")) ;
						
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
									
		params.add(vo.get("ne_order_id")) ;
									
		params.add(vo.get("feeback_info")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("deal_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("deal_desc")) ;
						
							
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
