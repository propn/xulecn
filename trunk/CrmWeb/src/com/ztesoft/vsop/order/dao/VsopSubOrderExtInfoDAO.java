package com.ztesoft.vsop.order.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class VsopSubOrderExtInfoDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select ext_info_id,sub_order_id,incr_biz_inst_id,attr_code,attr_name,attr_value,lan_id from VSOP_SUB_ORDER_EXT_INFO where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from vsop_sub_order_ext_info where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into VSOP_SUB_ORDER_EXT_INFO (ext_info_id, sub_order_id, incr_biz_inst_id, attr_code, attr_name, attr_value, lan_id) values (?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update VSOP_SUB_ORDER_EXT_INFO set ext_info_id=?, sub_order_id=?, incr_biz_inst_id=?, attr_code=?, attr_name=?, attr_value=?, lan_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from VSOP_SUB_ORDER_EXT_INFO where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from VSOP_SUB_ORDER_EXT_INFO where ext_info_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update VSOP_SUB_ORDER_EXT_INFO set ext_info_id=?, sub_order_id=?, incr_biz_inst_id=?, attr_code=?, attr_name=?, attr_value=?, lan_id=? where ext_info_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select ext_info_id,sub_order_id,incr_biz_inst_id,attr_code,attr_name,attr_value,lan_id from VSOP_SUB_ORDER_EXT_INFO where ext_info_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;
	public static final String SEQUENCE = "SEQ_SUB_ORDER_EXT_INFO_ID";

	public VsopSubOrderExtInfoDAO() {

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
							
		params.add(map.get("ext_info_id")) ;
									
		params.add(map.get("sub_order_id")) ;
									
		params.add(map.get("incr_biz_inst_id")) ;
									
		params.add(map.get("attr_code")) ;
									
		params.add(map.get("attr_name")) ;
									
		params.add(map.get("attr_value")) ;
									
		params.add(map.get("lan_id")) ;
						
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
				
					
		params.add(vo.get("ext_info_id")) ;
						
					
		params.add(vo.get("sub_order_id")) ;
						
					
		params.add(vo.get("incr_biz_inst_id")) ;
						
					
		params.add(vo.get("attr_code")) ;
						
					
		params.add(vo.get("attr_name")) ;
						
					
		params.add(vo.get("attr_value")) ;
						
					
		params.add(vo.get("lan_id")) ;
						
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
							
		params.add(vo.get("ext_info_id")) ;
									
		params.add(vo.get("sub_order_id")) ;
									
		params.add(vo.get("incr_biz_inst_id")) ;
									
		params.add(vo.get("attr_code")) ;
									
		params.add(vo.get("attr_name")) ;
									
		params.add(vo.get("attr_value")) ;
									
		params.add(vo.get("lan_id")) ;
						
							
		params.add(keyCondMap.get("ext_info_id")) ;
						
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
							
		params.add(keyCondMap.get("ext_info_id")) ;
						
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
