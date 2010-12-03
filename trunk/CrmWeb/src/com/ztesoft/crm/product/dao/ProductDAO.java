package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames;

public class ProductDAO extends AbstractDAO {
	
	//��ѯSQL
	private String SQL_SELECT = "select product_id,seq,product_provider_id,pricing_plan_id,product_name,product_comments,product_type,product_classification,product_code,product_family_id,state,eff_date,exp_date,limit_num,prod_cat_type,prod_adsc,manage_grade,region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,party_id,party_role_id,oper_region_id from PRODUCT where 1=1 ";
	
	//	ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from product where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into PRODUCT (product_id, seq, product_provider_id, pricing_plan_id, product_name, product_comments, product_type, product_classification, product_code, product_family_id, state, eff_date, exp_date, limit_num, prod_cat_type, prod_adsc, manage_grade, region_id,oper_date, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, party_id, party_role_id, oper_region_id) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

	//	��ͨupdate SQL
	private String SQL_UPDATE = "update PRODUCT set product_id=?, seq=?, product_provider_id=?, pricing_plan_id=?, product_name=?, product_comments=?, product_type=?, product_classification=?, product_code=?, product_family_id=?, state=?, eff_date=?, exp_date=?, limit_num=?, prod_cat_type=?, prod_adsc=?, manage_grade=?, region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=? where 1=1 ";
	
	//	��ͨdelete SQL
	private String SQL_DELETE = "delete from PRODUCT where 1=1 ";
	
		//	��������delete SQL
	private String SQL_DELETE_KEY = "delete from PRODUCT where product_id=? and seq=?";
		
		//	��������update SQL
	private String SQL_UPDATE_KEY = "update PRODUCT set product_id=?, seq=?, product_provider_id=?, pricing_plan_id=?, product_name=?, product_comments=?, product_type=?, product_classification=?, product_code=?, product_family_id=?, state=?, eff_date=?, exp_date=?, limit_num=?, prod_cat_type=?, prod_adsc=?, manage_grade=?, region_id=?, oper_date=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, party_id=?, party_role_id=?, oper_region_id=? where product_id=? and seq=?";
		
		//	����������ѯSQL
	private String SQL_SELECT_KEY = "select product_id,seq,product_provider_id,pricing_plan_id,product_name,product_comments,product_type,product_classification,product_code,product_family_id,state,eff_date,exp_date,limit_num,prod_cat_type,prod_adsc,manage_grade,region_id,oper_date,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,party_id,party_role_id,oper_region_id from PRODUCT where product_id=? and seq = ? and state <> '00X' ";
	
	//	��ǰDAO �������ݿ�name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ProductDAO() {

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
							
		params.add(map.get("product_id")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("product_provider_id")) ;
									
		params.add(map.get("pricing_plan_id")) ;
									
		params.add(map.get("product_name")) ;
									
		params.add(map.get("product_comments")) ;
									
		params.add(map.get("product_type")) ;
									
		params.add(map.get("product_classification")) ;
									
		params.add(map.get("product_code")) ;
									
		params.add(map.get("product_family_id")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("limit_num")) ;
									
		params.add(map.get("prod_cat_type")) ;
									
		params.add(map.get("prod_adsc")) ;
									
		params.add(map.get("manage_grade")) ;
									
		params.add(map.get("region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
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
				
					
		params.add(vo.get("product_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("product_provider_id")) ;
						
					
		params.add(vo.get("pricing_plan_id")) ;
						
					
		params.add(vo.get("product_name")) ;
						
					
		params.add(vo.get("product_comments")) ;
						
					
		params.add(vo.get("product_type")) ;
						
					
		params.add(vo.get("product_classification")) ;
						
					
		params.add(vo.get("product_code")) ;
						
					
		params.add(vo.get("product_family_id")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("limit_num")) ;
						
					
		params.add(vo.get("prod_cat_type")) ;
						
					
		params.add(vo.get("prod_adsc")) ;
						
					
		params.add(vo.get("manage_grade")) ;
						
					
		params.add(vo.get("region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
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
							
		params.add(vo.get("product_id")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("product_provider_id")) ;
									
		params.add(vo.get("pricing_plan_id")) ;
									
		params.add(vo.get("product_name")) ;
									
		params.add(vo.get("product_comments")) ;
									
		params.add(vo.get("product_type")) ;
									
		params.add(vo.get("product_classification")) ;
									
		params.add(vo.get("product_code")) ;
									
		params.add(vo.get("product_family_id")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("limit_num")) ;
									
		params.add(vo.get("prod_cat_type")) ;
									
		params.add(vo.get("prod_adsc")) ;
									
		params.add(vo.get("manage_grade")) ;
									
		params.add(vo.get("region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
							
		params.add(keyCondMap.get("product_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
							
		params.add(keyCondMap.get("product_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
