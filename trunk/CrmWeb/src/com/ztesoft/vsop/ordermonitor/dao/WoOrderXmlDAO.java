package com.ztesoft.vsop.ordermonitor.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class WoOrderXmlDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select order_id,xml_seq,xml_info from WO_ORDER_XML where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from wo_order_xml where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into WO_ORDER_XML (order_id, xml_seq, xml_info) values (?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update WO_ORDER_XML set order_id=?, xml_seq=?, xml_info=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from WO_ORDER_XML where 1=1 ";
	
		
		
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public WoOrderXmlDAO() {

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
							
		params.add(map.get("order_id")) ;
									
		params.add(map.get("xml_seq")) ;
									
		params.add(map.get("xml_info")) ;
						
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
				
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("xml_seq")) ;
						
					
		params.add(vo.get("xml_info")) ;
						
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
								
		throw new FrameException("当前SQL没有主键，请确认库表结构 ！") ;	
				
	}
	
}
