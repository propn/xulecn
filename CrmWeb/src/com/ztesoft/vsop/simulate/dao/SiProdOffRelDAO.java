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

public class SiProdOffRelDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select prod_offer_rel_id,offer_a_id,offer_z_id,relation_type_id,eff_date,exp_date,(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID=offer_a_id) offer_name_a,(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID=offer_z_id) offer_name_z from PROD_OFFER_REL where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from prod_offer_rel where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PROD_OFFER_REL (prod_offer_rel_id, offer_a_id, offer_z_id, relation_type_id, eff_date, exp_date) values (?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update PROD_OFFER_REL set prod_offer_rel_id=?, offer_a_id=?, offer_z_id=?, relation_type_id=?, eff_date=?, exp_date=? where 1=1 ";
	
	//	普通delete SQL 
	//删除的时候根据OFFER_A_ID, OFFER_Z_ID, RELATION_TYPE_ID进行删除. {offer_a_id=1004, relation_type_id=2, offer_z_id=1005}
	//private String SQL_DELETE = "delete from PROD_OFFER_REL where 1=1 ";
	private String SQL_DELETE = "delete from PROD_OFFER_REL where offer_a_id=? and relation_type_id=? and offer_z_id=?";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from PROD_OFFER_REL where prod_offer_rel_id=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update PROD_OFFER_REL set prod_offer_rel_id=?, offer_a_id=?, offer_z_id=?, relation_type_id=?, eff_date=?, exp_date=? where prod_offer_rel_id=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select prod_offer_rel_id,offer_a_id,offer_z_id,relation_type_id,eff_date,exp_date,(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID=offer_a_id) offer_name_a,(select PROD_OFFER_NAME from PROD_OFFER where PROD_OFFER_ID=offer_z_id) offer_name_z from PROD_OFFER_REL where prod_offer_rel_id=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE ;

	private String SQL_PROD_OFF_NAME = "select PROD_OFFER_NAME from prod_offer where PROD_OFFER_ID=?";

	private String SQL_SELECT_BY_OFFER_ID = "select prod_offer_rel_id,offer_a_id,offer_z_id,relation_type_id,eff_date,exp_date from PROD_OFFER_REL t where t.offer_a_id=? or t.offer_z_id=?";
	
	public SiProdOffRelDAO() {

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
							
		//params.add(map.get("prod_offer_rel_id")) ;
		
		SequenceManageDAO seqDao = new SequenceManageDAOImpl();
		String prodOfferRelId = seqDao.getNextSequence("SEQ_PROD_OFFER_REL_ID");
		params.add(prodOfferRelId);
									
		params.add(map.get("offer_a_id")) ;
									
		params.add(map.get("offer_z_id")) ;
									
		params.add(map.get("relation_type_id")) ;
						
		//params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());		//暂把生效时间和实效时间都设置为系统当前时间，尚不知逻辑如何	
		//params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
		params.add(DAOUtils.getCurrentTimestamp());
		
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
				
					
		params.add(vo.get("prod_offer_rel_id")) ;
						
					
		params.add(vo.get("offer_a_id")) ;
						
					
		params.add(vo.get("offer_z_id")) ;
						
					
		params.add(vo.get("relation_type_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
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
							
		params.add(vo.get("prod_offer_rel_id")) ;
									
		params.add(vo.get("offer_a_id")) ;
									
		params.add(vo.get("offer_z_id")) ;
									
		params.add(vo.get("relation_type_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
							
		params.add(keyCondMap.get("prod_offer_rel_id")) ;
						
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
							
		params.add(keyCondMap.get("prod_offer_rel_id")) ;
						
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

	public String getSQLForProdOffName() {
		return this.SQL_PROD_OFF_NAME;
	}
	
	public String getSQLByOfferId() {
		return this.SQL_SELECT_BY_OFFER_ID;
	}
	
}
