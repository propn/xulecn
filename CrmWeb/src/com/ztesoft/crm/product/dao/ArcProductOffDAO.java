package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class ArcProductOffDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select offer_id,seq,pricing_plan_id,offer_name,offer_comments,can_be_buy_alone,offer_code,state,eff_date,exp_date,priority_value,manage_grade,brand_id,sub_brand_id,serv_brand_id,offer_kind,comp_product_id,region_id,offer_provider_id,effective_type,effective_period_type,effective_period,relet_type,relet_period_type,relet_period,limit_check,send_bill,packet_type,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,price_comments,party_id,party_role_id,oper_region_id,oper_date from ARC_PRODUCT_OFFER where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from arc_product_offer where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into ARC_PRODUCT_OFFER (offer_id, seq, pricing_plan_id, offer_name, offer_comments, can_be_buy_alone, offer_code, state, eff_date, exp_date, priority_value, manage_grade, brand_id, sub_brand_id, serv_brand_id, offer_kind, comp_product_id, region_id, offer_provider_id, effective_type, effective_period_type, effective_period, relet_type, relet_period_type, relet_period, limit_check, send_bill, packet_type, ord_action_type, ord_no, cancel_ord_no, event_seq, cancel_event_seq, price_comments, party_id, party_role_id, oper_region_id, oper_date) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update ARC_PRODUCT_OFFER set offer_id=?, seq=?, pricing_plan_id=?, offer_name=?, offer_comments=?, can_be_buy_alone=?, offer_code=?, state=?, eff_date=?, exp_date=?, priority_value=?, manage_grade=?, brand_id=?, sub_brand_id=?, serv_brand_id=?, offer_kind=?, comp_product_id=?, region_id=?, offer_provider_id=?, effective_type=?, effective_period_type=?, effective_period=?, relet_type=?, relet_period_type=?, relet_period=?, limit_check=?, send_bill=?, packet_type=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, price_comments=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where 1=1 ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from ARC_PRODUCT_OFFER where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from ARC_PRODUCT_OFFER where offer_id=? and seq=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update ARC_PRODUCT_OFFER set offer_id=?, seq=?, pricing_plan_id=?, offer_name=?, offer_comments=?, can_be_buy_alone=?, offer_code=?, state=?, eff_date=?, exp_date=?, priority_value=?, manage_grade=?, brand_id=?, sub_brand_id=?, serv_brand_id=?, offer_kind=?, comp_product_id=?, region_id=?, offer_provider_id=?, effective_type=?, effective_period_type=?, effective_period=?, relet_type=?, relet_period_type=?, relet_period=?, limit_check=?, send_bill=?, packet_type=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, event_seq=?, cancel_event_seq=?, price_comments=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=? where offer_id=? and seq=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select offer_id,seq,pricing_plan_id,offer_name,offer_comments,can_be_buy_alone,offer_code,state,eff_date,exp_date,priority_value,manage_grade,brand_id,sub_brand_id,serv_brand_id,offer_kind,comp_product_id,region_id,offer_provider_id,effective_type,effective_period_type,effective_period,relet_type,relet_period_type,relet_period,limit_check,send_bill,packet_type,ord_action_type,ord_no,cancel_ord_no,event_seq,cancel_event_seq,price_comments,party_id,party_role_id,oper_region_id,oper_date from ARC_PRODUCT_OFFER where offer_id=? and seq=? ";
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ArcProductOffDAO() {

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
							
		params.add(map.get("offer_id")) ;
									
		params.add(map.get("seq")) ;
									
		params.add(map.get("pricing_plan_id")) ;
									
		params.add(map.get("offer_name")) ;
									
		params.add(map.get("offer_comments")) ;
									
		params.add(map.get("can_be_buy_alone")) ;
									
		params.add(map.get("offer_code")) ;
									
		params.add(map.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;
									
		params.add(map.get("priority_value")) ;
									
		params.add(map.get("manage_grade")) ;
									
		params.add(map.get("brand_id")) ;
									
		params.add(map.get("sub_brand_id")) ;
									
		params.add(map.get("serv_brand_id")) ;
									
		params.add(map.get("offer_kind")) ;
									
		params.add(map.get("comp_product_id")) ;
									
		params.add(map.get("region_id")) ;
									
		params.add(map.get("offer_provider_id")) ;
									
		params.add(map.get("effective_type")) ;
									
		params.add(map.get("effective_period_type")) ;
									
		params.add(map.get("effective_period")) ;
									
		params.add(map.get("relet_type")) ;
									
		params.add(map.get("relet_period_type")) ;
									
		params.add(map.get("relet_period")) ;
									
		params.add(map.get("limit_check")) ;
									
		params.add(map.get("send_bill")) ;
									
		params.add(map.get("packet_type")) ;
									
		params.add(map.get("ord_action_type")) ;
									
		params.add(map.get("ord_no")) ;
									
		params.add(map.get("cancel_ord_no")) ;
									
		params.add(map.get("event_seq")) ;
									
		params.add(map.get("cancel_event_seq")) ;
									
		params.add(map.get("price_comments")) ;
									
		params.add(map.get("party_id")) ;
									
		params.add(map.get("party_role_id")) ;
									
		params.add(map.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;
						
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
				
					
		params.add(vo.get("offer_id")) ;
						
					
		params.add(vo.get("seq")) ;
						
					
		params.add(vo.get("pricing_plan_id")) ;
						
					
		params.add(vo.get("offer_name")) ;
						
					
		params.add(vo.get("offer_comments")) ;
						
					
		params.add(vo.get("can_be_buy_alone")) ;
						
					
		params.add(vo.get("offer_code")) ;
						
					
		params.add(vo.get("state")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
						
					
		params.add(vo.get("priority_value")) ;
						
					
		params.add(vo.get("manage_grade")) ;
						
					
		params.add(vo.get("brand_id")) ;
						
					
		params.add(vo.get("sub_brand_id")) ;
						
					
		params.add(vo.get("serv_brand_id")) ;
						
					
		params.add(vo.get("offer_kind")) ;
						
					
		params.add(vo.get("comp_product_id")) ;
						
					
		params.add(vo.get("region_id")) ;
						
					
		params.add(vo.get("offer_provider_id")) ;
						
					
		params.add(vo.get("effective_type")) ;
						
					
		params.add(vo.get("effective_period_type")) ;
						
					
		params.add(vo.get("effective_period")) ;
						
					
		params.add(vo.get("relet_type")) ;
						
					
		params.add(vo.get("relet_period_type")) ;
						
					
		params.add(vo.get("relet_period")) ;
						
					
		params.add(vo.get("limit_check")) ;
						
					
		params.add(vo.get("send_bill")) ;
						
					
		params.add(vo.get("packet_type")) ;
						
					
		params.add(vo.get("ord_action_type")) ;
						
					
		params.add(vo.get("ord_no")) ;
						
					
		params.add(vo.get("cancel_ord_no")) ;
						
					
		params.add(vo.get("event_seq")) ;
						
					
		params.add(vo.get("cancel_event_seq")) ;
						
					
		params.add(vo.get("price_comments")) ;
						
					
		params.add(vo.get("party_id")) ;
						
					
		params.add(vo.get("party_role_id")) ;
						
					
		params.add(vo.get("oper_region_id")) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
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
							
		params.add(vo.get("offer_id")) ;
									
		params.add(vo.get("seq")) ;
									
		params.add(vo.get("pricing_plan_id")) ;
									
		params.add(vo.get("offer_name")) ;
									
		params.add(vo.get("offer_comments")) ;
									
		params.add(vo.get("can_be_buy_alone")) ;
									
		params.add(vo.get("offer_code")) ;
									
		params.add(vo.get("state")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;
									
		params.add(vo.get("priority_value")) ;
									
		params.add(vo.get("manage_grade")) ;
									
		params.add(vo.get("brand_id")) ;
									
		params.add(vo.get("sub_brand_id")) ;
									
		params.add(vo.get("serv_brand_id")) ;
									
		params.add(vo.get("offer_kind")) ;
									
		params.add(vo.get("comp_product_id")) ;
									
		params.add(vo.get("region_id")) ;
									
		params.add(vo.get("offer_provider_id")) ;
									
		params.add(vo.get("effective_type")) ;
									
		params.add(vo.get("effective_period_type")) ;
									
		params.add(vo.get("effective_period")) ;
									
		params.add(vo.get("relet_type")) ;
									
		params.add(vo.get("relet_period_type")) ;
									
		params.add(vo.get("relet_period")) ;
									
		params.add(vo.get("limit_check")) ;
									
		params.add(vo.get("send_bill")) ;
									
		params.add(vo.get("packet_type")) ;
									
		params.add(vo.get("ord_action_type")) ;
									
		params.add(vo.get("ord_no")) ;
									
		params.add(vo.get("cancel_ord_no")) ;
									
		params.add(vo.get("event_seq")) ;
									
		params.add(vo.get("cancel_event_seq")) ;
									
		params.add(vo.get("price_comments")) ;
									
		params.add(vo.get("party_id")) ;
									
		params.add(vo.get("party_role_id")) ;
									
		params.add(vo.get("oper_region_id")) ;
						
		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;
						
							
		params.add(keyCondMap.get("offer_id")) ;
									
		params.add(keyCondMap.get("seq")) ;
						
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
							
		params.add(keyCondMap.get("offer_id")) ;
									
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
