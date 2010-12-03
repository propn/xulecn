package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames;

public class ProductOffServDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select offer_id,service_offer_id,flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date,order_id from PRODUCT_OFFER_SERVICE where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product_offer_service where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT_OFFER_SERVICE (offer_id, service_offer_id, flag, eff_date, exp_date, state, seq, party_id, party_role_id, oper_region_id, oper_date, order_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PRODUCT_OFFER_SERVICE set offer_id=?, service_offer_id=?, flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, order_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PRODUCT_OFFER_SERVICE where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT_OFFER_SERVICE where offer_id=? and service_offer_id=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT_OFFER_SERVICE set offer_id=?, service_offer_id=?, flag=?, eff_date=?, exp_date=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, order_id=? where offer_id=? and service_offer_id=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select offer_id,service_offer_id,flag,eff_date,exp_date,state,seq,party_id,party_role_id,oper_region_id,oper_date,order_id from PRODUCT_OFFER_SERVICE where offer_id=? and service_offer_id=? ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName =JNDINames.PM_DATASOURCE;


	public ProductOffServDAO() {

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
							
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("service_offer_id")) ;
									
		params.add(map.get("flag")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("state")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("order_id")) ;
						
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
				
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("service_offer_id")) ;
						
					
		params.add(vo.get("flag")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("state")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("order_id")) ;
						
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
							
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("service_offer_id")) ;
									
		params.add(vo.get("flag")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("state")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("order_id")) ;
						
							
		params.add(keyCondMap.get("offer_id")) ;
									
		params.add(keyCondMap.get("service_offer_id")) ;
						
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
							
		params.add(keyCondMap.get("offer_id")) ;
									
		params.add(keyCondMap.get("service_offer_id")) ;
						
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
