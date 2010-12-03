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

public class SiVproductDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select id,app_id,vproduct_id,package_id,ppordoffer_id,act_type,eff_time,exp_time,order_state from SI_VPRODUCT where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from si_vproduct where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SI_VPRODUCT (id, app_id, vproduct_id, package_id, ppordoffer_id, act_type, eff_time, exp_time, order_state) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update SI_VPRODUCT set id=?, app_id=?, vproduct_id=?, package_id=?, ppordoffer_id=?, act_type=?, eff_time=?, exp_time=?, order_state=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from SI_VPRODUCT where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from SI_VPRODUCT where id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update SI_VPRODUCT set id=?, app_id=?, vproduct_id=?, package_id=?, ppordoffer_id=?, act_type=?, eff_time=?, exp_time=?, order_state=? where id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select id,app_id,vproduct_id,package_id,ppordoffer_id,act_type,eff_time,exp_time,order_state from SI_VPRODUCT where id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;


	public SiVproductDAO() {

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
		SequenceManageDAO dao = new SequenceManageDAOImpl();
		String id = dao.getNextSequence("Si_vproduct_Seq");
							
							
		params.add(id) ;
									
		params.add(map.get("app_id")) ;
									
		params.add(map.get("vproduct_id")) ;
									
		params.add(map.get("package_id")) ;
									
		params.add(map.get("ppordoffer_id")) ;
									
		params.add(map.get("act_type")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_time" ))) ;
									
		params.add(map.get("order_state")) ;
						
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
						
					
		params.add(vo.get("app_id")) ;
						
					
		params.add(vo.get("vproduct_id")) ;
						
					
		params.add(vo.get("package_id")) ;
						
					
		params.add(vo.get("ppordoffer_id")) ;
						
					
		params.add(vo.get("act_type")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
						
					
		params.add(vo.get("order_state")) ;
						
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
									
		params.add(vo.get("app_id")) ;
									
		params.add(vo.get("vproduct_id")) ;
									
		params.add(vo.get("package_id")) ;
									
		params.add(vo.get("ppordoffer_id")) ;
									
		params.add(vo.get("act_type")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_time" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_time" ))) ;
									
		params.add(vo.get("order_state")) ;
						
							
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
