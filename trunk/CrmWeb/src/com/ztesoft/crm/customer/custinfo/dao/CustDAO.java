package com.ztesoft.crm.customer.custinfo.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.crm.customer.custinfo.common.CustHelper;

public class CustDAO extends AbstractDAO {
	
	//查询SQL
	private String SQL_SELECT = "select a.cust_id,a.seq_nbr,a.cust_order_id,a.order_id,a.action_type,a.record_eff_date,a.record_exp_date,a.state_date,a.cust_name,a.cust_code,a.certi_type,a.certi_number,a.certi_address,a.certi_org,a.service_region,a.market_region,a.type_flag,a.cust_type,a.cust_type_attr,a.sub_cust_type_attr,a.cust_brand,a.sub_cust_brand,a.is_vip,a.importance_level,a.service_level,a.industry_code,a.manage_type,a.town_flag,a.lieu_id,a.telecom_id,a.cust_state,a.secr_grade,a.canton_no,a.city_no,a.cust_addr,a.addr_id,a.cust_mgr,a.become_date,a.create_date,a.ini_password,a.def_acct_id,a.grp_cust_code,a.charge_province_code,a.charge_latn_id,a.real_flag,a.not_real_reason,a.accept_channel,a.notes from CUST a where 1=1 " ;
								//" left join CUST_CONTACT_INFO b on a.cust_id = b.cust_id " +
								//" left join ACCT c on a.cust_id = c.cust_id where 1=1 ";
	
	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from CUST a where 1=1 " ;
								//" left join CUST_CONTACT_INFO b on a.cust_id = b.cust_id " +
								//" left join ACCT c on a.cust_id = c.cust_id where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into CUST (cust_id, seq_nbr, cust_order_id, order_id, action_type, record_eff_date, record_exp_date, state_date, cust_name, cust_code, certi_type, certi_number, certi_address, certi_org, service_region, market_region, type_flag, cust_type, cust_type_attr, sub_cust_type_attr, cust_brand, sub_cust_brand, is_vip, importance_level, service_level, industry_code, manage_type, town_flag, lieu_id, telecom_id, cust_state, secr_grade, canton_no, city_no, cust_addr, addr_id, cust_mgr, become_date, create_date, ini_password, def_acct_id, grp_cust_code, charge_province_code, charge_latn_id, real_flag, not_real_reason, accept_channel, notes) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE_KEY = "update CUST set record_exp_date=?, state_date=?  where  cust_id=? and seq_nbr=? ";
	
	//	普通delete SQL
	private String SQL_DELETE = "delete from CUST where 1=1 ";
	
		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from CUST where cust_id=? and seq_nbr=?";
		
		//	根据主键update SQL
	private String SQL_UPDATE = "update CUST set cust_id=?, seq_nbr=?, cust_order_id=?, order_id=?, action_type=?, record_eff_date=?, record_exp_date=?, state_date=?, cust_name=?, cust_code=?, certi_type=?, certi_number=?, certi_address=?, certi_org=?, service_region=?, market_region=?, type_flag=?, cust_type=?, cust_type_attr=?, sub_cust_type_attr=?, cust_brand=?, sub_cust_brand=?, is_vip=?, importance_level=?, service_level=?, industry_code=?, manage_type=?, town_flag=?, lieu_id=?, telecom_id=?, cust_state=?, secr_grade=?, canton_no=?, city_no=?, cust_addr=?, addr_id=?, cust_mgr=?, become_date=?, create_date=?, ini_password=?, def_acct_id=?, grp_cust_code=?, charge_province_code=?, charge_latn_id=?, real_flag=?, not_real_reason=?, accept_channel=?, notes=? where cust_id=? and seq_nbr=?";
		
		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select cust_id,seq_nbr,cust_order_id,order_id,action_type,record_eff_date,record_exp_date,state_date,cust_name,cust_code,certi_type,certi_number,certi_address,certi_org,service_region,market_region,type_flag,cust_type,cust_type_attr,sub_cust_type_attr,cust_brand,sub_cust_brand,is_vip,importance_level,service_level,industry_code,manage_type,town_flag,lieu_id,telecom_id,cust_state,secr_grade,canton_no,city_no,cust_addr,addr_id,cust_mgr,become_date,create_date,ini_password,def_acct_id,grp_cust_code,charge_province_code,charge_latn_id,real_flag,not_real_reason,accept_channel,notes from CUST where cust_id=? and record_exp_date = "+CustHelper.getDefaultExpDate();
	
	//	当前DAO 所属数据库name
	private String dbName = JNDINames.CM_DATASOURCE ;


	public CustDAO() {

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
							
		params.add(map.get("cust_id")) ;
									
		params.add(map.get("seq_nbr")) ;
									
		params.add(map.get("cust_order_id")) ;
									
		params.add(map.get("order_id")) ;
									
		params.add(map.get("action_type")) ;
						
		params.add(DAOUtils.parseTimestamp((String)map.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseDateTime(map.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseTimestamp((String)map.get("state_date" ))) ;
									
		params.add(map.get("cust_name")) ;
									
		params.add(map.get("cust_code")) ;
									
		params.add(map.get("certi_type")) ;
									
		params.add(map.get("certi_number")) ;
									
		params.add(map.get("certi_address")) ;
									
		params.add(map.get("certi_org")) ;
									
		params.add(map.get("service_region")) ;
									
		params.add(map.get("market_region")) ;
									
		params.add(map.get("type_flag")) ;
									
		params.add(map.get("cust_type")) ;
									
		params.add(map.get("cust_type_attr")) ;
									
		params.add(map.get("sub_cust_type_attr")) ;
									
		params.add(map.get("cust_brand")) ;
									
		params.add(map.get("sub_cust_brand")) ;
									
		params.add(map.get("is_vip")) ;
									
		params.add(map.get("importance_level")) ;
									
		params.add(map.get("service_level")) ;
									
		params.add(map.get("industry_code")) ;
									
		params.add(map.get("manage_type")) ;
									
		params.add(map.get("town_flag")) ;
									
		params.add(map.get("lieu_id")) ;
									
		params.add(map.get("telecom_id")) ;
									
		params.add(map.get("cust_state")) ;
									
		params.add(map.get("secr_grade")) ;
									
		params.add(map.get("canton_no")) ;
									
		params.add(map.get("city_no")) ;
									
		params.add(map.get("cust_addr")) ;
									
		params.add(map.get("addr_id")) ;
									
		params.add(map.get("cust_mgr")) ;
						
		params.add(DAOUtils.parseTimestamp((String)map.get("become_date" ))) ;
						
		params.add(DAOUtils.parseTimestamp((String)map.get("create_date" ))) ;
									
		params.add(map.get("ini_password")) ;
									
		params.add(map.get("def_acct_id")) ;
									
		params.add(map.get("grp_cust_code")) ;
									
		params.add(map.get("charge_province_code")) ;
									
		params.add(map.get("charge_latn_id")) ;
									
		params.add(map.get("real_flag")) ;
									
		params.add(map.get("not_real_reason")) ;
									
		params.add(map.get("accept_channel")) ;
									
		params.add(map.get("notes")) ;
						
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
				
					
		params.add(vo.get("cust_id")) ;
						
					
		params.add(vo.get("seq_nbr")) ;
						
					
		params.add(vo.get("cust_order_id")) ;
						
					
		params.add(vo.get("order_id")) ;
						
					
		params.add(vo.get("action_type")) ;
						
		
		params.add(DAOUtils.parseTimestamp((String)vo.get("record_eff_date" ))) ;
						
		
		params.add(DAOUtils.parseDateTime(vo.get("record_exp_date" ))) ;
						
		
		params.add(DAOUtils.parseTimestamp((String)vo.get("state_date" ))) ;
						
					
		params.add(vo.get("cust_name")) ;
						
					
		params.add(vo.get("cust_code")) ;
						
					
		params.add(vo.get("certi_type")) ;
						
					
		params.add(vo.get("certi_number")) ;
						
					
		params.add(vo.get("certi_address")) ;
						
					
		params.add(vo.get("certi_org")) ;
						
					
		params.add(vo.get("service_region")) ;
						
					
		params.add(vo.get("market_region")) ;
						
					
		params.add(vo.get("type_flag")) ;
						
					
		params.add(vo.get("cust_type")) ;
						
					
		params.add(vo.get("cust_type_attr")) ;
						
					
		params.add(vo.get("sub_cust_type_attr")) ;
						
					
		params.add(vo.get("cust_brand")) ;
						
					
		params.add(vo.get("sub_cust_brand")) ;
						
					
		params.add(vo.get("is_vip")) ;
						
					
		params.add(vo.get("importance_level")) ;
						
					
		params.add(vo.get("service_level")) ;
						
					
		params.add(vo.get("industry_code")) ;
						
					
		params.add(vo.get("manage_type")) ;
						
					
		params.add(vo.get("town_flag")) ;
						
					
		params.add(vo.get("lieu_id")) ;
						
					
		params.add(vo.get("telecom_id")) ;
						
					
		params.add(vo.get("cust_state")) ;
						
					
		params.add(vo.get("secr_grade")) ;
						
					
		params.add(vo.get("canton_no")) ;
						
					
		params.add(vo.get("city_no")) ;
						
					
		params.add(vo.get("cust_addr")) ;
						
					
		params.add(vo.get("addr_id")) ;
						
					
		params.add(vo.get("cust_mgr")) ;
						
		
		params.add(DAOUtils.parseTimestamp((String)vo.get("become_date" ))) ;
						
		
		params.add(DAOUtils.parseTimestamp((String)vo.get("create_date" ))) ;
						
					
		params.add(vo.get("ini_password")) ;
						
					
		params.add(vo.get("def_acct_id")) ;
						
					
		params.add(vo.get("grp_cust_code")) ;
						
					
		params.add(vo.get("charge_province_code")) ;
						
					
		params.add(vo.get("charge_latn_id")) ;
						
					
		params.add(vo.get("real_flag")) ;
						
					
		params.add(vo.get("not_real_reason")) ;
						
					
		params.add(vo.get("accept_channel")) ;
						
					
		params.add(vo.get("notes")) ;
						
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
							
//		params.add(vo.get("cust_id")) ;
//									
//		params.add(vo.get("seq_nbr")) ;
									
//		params.add(vo.get("cust_order_id")) ;
//									
//		params.add(vo.get("order_id")) ;
//									
//		params.add(vo.get("action_type")) ;
						
//		params.add(DAOUtils.parseTimestamp((String)vo.get("record_eff_date" ))) ;
						
		params.add(DAOUtils.parseTimestamp((String)vo.get("record_exp_date" ))) ;
						
		params.add(DAOUtils.parseTimestamp((String)vo.get("state_date" ))) ;
									
//		params.add(vo.get("cust_name")) ;
//									
//		params.add(vo.get("cust_code")) ;
//									
//		params.add(vo.get("certi_type")) ;
//									
//		params.add(vo.get("certi_number")) ;
//									
//		params.add(vo.get("certi_address")) ;
//									
//		params.add(vo.get("certi_org")) ;
//									
//		params.add(vo.get("service_region")) ;
//									
//		params.add(vo.get("market_region")) ;
//									
//		params.add(vo.get("type_flag")) ;
//									
//		params.add(vo.get("cust_type")) ;
//									
//		params.add(vo.get("cust_type_attr")) ;
//									
//		params.add(vo.get("sub_cust_type_attr")) ;
//									
//		params.add(vo.get("cust_brand")) ;
//									
//		params.add(vo.get("sub_cust_brand")) ;
//									
//		params.add(vo.get("is_vip")) ;
//									
//		params.add(vo.get("importance_level")) ;
//									
//		params.add(vo.get("service_level")) ;
//									
//		params.add(vo.get("industry_code")) ;
//									
//		params.add(vo.get("manage_type")) ;
//									
//		params.add(vo.get("town_flag")) ;
//									
//		params.add(vo.get("lieu_id")) ;
//									
//		params.add(vo.get("telecom_id")) ;
//									
//		params.add(vo.get("cust_state")) ;
//									
//		params.add(vo.get("secr_grade")) ;
//									
//		params.add(vo.get("canton_no")) ;
//									
//		params.add(vo.get("city_no")) ;
//									
//		params.add(vo.get("cust_addr")) ;
//									
//		params.add(vo.get("addr_id")) ;
//									
//		params.add(vo.get("cust_mgr")) ;
//						
//		params.add(DAOUtils.parseDateTime(vo.get("become_date" ))) ;
//						
//		params.add(DAOUtils.parseDateTime(vo.get("create_date" ))) ;
//									
//		params.add(vo.get("ini_password")) ;
//									
//		params.add(vo.get("def_acct_id")) ;
//									
//		params.add(vo.get("grp_cust_code")) ;
//									
//		params.add(vo.get("charge_province_code")) ;
//									
//		params.add(vo.get("charge_latn_id")) ;
//									
//		params.add(vo.get("real_flag")) ;
//									
//		params.add(vo.get("not_real_reason")) ;
//									
//		params.add(vo.get("accept_channel")) ;
//									
//		params.add(vo.get("notes")) ;
//						
							
		params.add(keyCondMap.get("cust_id")) ;
									
		params.add(keyCondMap.get("seq_nbr")) ;
						
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
							
		params.add(keyCondMap.get("cust_id")) ;
									
		//params.add(keyCondMap.get("seq_nbr")) ;
						
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
	
	public void setSelectSQL(String SQL_SELECT){
		this.SQL_SELECT =SQL_SELECT;
	}
	
	public void setSelectCountSQL(String SQL_SELECT_COUNT){
		this.SQL_SELECT_COUNT= SQL_SELECT_COUNT ;
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
