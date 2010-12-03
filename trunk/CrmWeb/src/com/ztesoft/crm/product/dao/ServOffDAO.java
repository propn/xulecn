package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames;

public class ServOffDAO extends AbstractDAO {

	//查询SQL
	private String SQL_SELECT = "select service_offer_id,action_id,product_id,service_offer_name,service_offer_desc,service_offer_type,state,state_date,eff_date,exp_date,prod_cat_type,action_type,install_flag,ask_flow,serv_state,owe_business_type_id,seq,party_id,party_role_id,oper_region_id,oper_date,service_offer_adsc from SERVICE_OFFER where 1=1 ";

	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from service_offer where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into SERVICE_OFFER (service_offer_id, action_id, product_id, service_offer_name, service_offer_desc, service_offer_type, state, state_date, eff_date, exp_date, prod_cat_type, action_type, install_flag, ask_flow, serv_state, owe_business_type_id, seq, party_id, party_role_id, oper_region_id, oper_date,service_offer_adsc,page_path) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update SERVICE_OFFER set service_offer_id=?, action_id=?, product_id=?, service_offer_name=?, service_offer_desc=?, service_offer_type=?, state=?, state_date=?, eff_date=?, exp_date=?, prod_cat_type=?, action_type=?, install_flag=?, ask_flow=?, serv_state=?, owe_business_type_id=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, service_offer_adsc=? where 1=1 ";

	//	普通delete SQL
	private String SQL_DELETE = "delete from SERVICE_OFFER where 1=1 ";

		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from SERVICE_OFFER where service_offer_id=?";

		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update SERVICE_OFFER set action_id=?, product_id=?, service_offer_name=?, service_offer_desc=?, service_offer_type=?, state_date=?, eff_date=?, exp_date=?, prod_cat_type=?, action_type=?, install_flag=?, ask_flow=?, serv_state=?, owe_business_type_id=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, service_offer_adsc=?,page_path=? where service_offer_id=?";

		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select service_offer_id,action_id,product_id,service_offer_name,service_offer_desc,service_offer_type,state,state_date,eff_date,exp_date,prod_cat_type,action_type,install_flag,ask_flow,serv_state,owe_business_type_id,seq,party_id,party_role_id,oper_region_id,oper_date,page_path,service_offer_adsc from SERVICE_OFFER where service_offer_id=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public ServOffDAO() {

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

		params.add(map.get("service_offer_id")) ;

		params.add(map.get("action_id")) ;

		params.add(map.get("product_id")) ;

		params.add(map.get("service_offer_name")) ;

		params.add(map.get("service_offer_desc")) ;

		params.add(map.get("service_offer_type")) ;

		params.add(map.get("state")) ;

		params.add(DAOUtils.parseDateTime(map.get("state_date" ))) ;

		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;

		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;

		params.add(map.get("prod_cat_type")) ;

		params.add(map.get("action_type")) ;

		params.add(map.get("install_flag")) ;

		params.add(map.get("ask_flow")) ;

		params.add(map.get("serv_state")) ;

		params.add(map.get("owe_business_type_id")) ;

		params.add(map.get("seq")) ;

		params.add(map.get("party_id")) ;

		params.add(map.get("party_role_id")) ;

		params.add(map.get("oper_region_id")) ;

		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;

		params.add(map.get("service_offer_adsc")) ;
		params.add(map.get("page_path")) ;
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


		params.add(vo.get("service_offer_id")) ;


		params.add(vo.get("action_id")) ;


		params.add(vo.get("product_id")) ;


		params.add(vo.get("service_offer_name")) ;


		params.add(vo.get("service_offer_desc")) ;


		params.add(vo.get("service_offer_type")) ;


		params.add(vo.get("state")) ;


		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;


		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;


		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;


		params.add(vo.get("prod_cat_type")) ;


		params.add(vo.get("action_type")) ;


		params.add(vo.get("install_flag")) ;


		params.add(vo.get("ask_flow")) ;


		params.add(vo.get("serv_state")) ;


		params.add(vo.get("owe_business_type_id")) ;


		params.add(vo.get("seq")) ;


		params.add(vo.get("party_id")) ;


		params.add(vo.get("party_role_id")) ;


		params.add(vo.get("oper_region_id")) ;


		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;


		params.add(vo.get("service_offer_adsc")) ;

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

		//params.add(vo.get("service_offer_id")) ;

		params.add(vo.get("action_id")) ;

		params.add(vo.get("product_id")) ;

		params.add(vo.get("service_offer_name")) ;

		params.add(vo.get("service_offer_desc")) ;

		params.add(vo.get("service_offer_type")) ;

		//params.add(vo.get("state")) ;

		params.add(DAOUtils.parseDateTime(vo.get("state_date" ))) ;

		params.add(DAOUtils.parseDateTime(vo.get("eff_date" ))) ;

		params.add(DAOUtils.parseDateTime(vo.get("exp_date" ))) ;

		params.add(vo.get("prod_cat_type")) ;

		params.add(vo.get("action_type")) ;

		params.add(vo.get("install_flag")) ;

		params.add(vo.get("ask_flow")) ;

		params.add(vo.get("serv_state")) ;

		params.add(vo.get("owe_business_type_id")) ;

		params.add(vo.get("seq")) ;

		params.add(vo.get("party_id")) ;

		params.add(vo.get("party_role_id")) ;

		params.add(vo.get("oper_region_id")) ;

		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;

		params.add(vo.get("service_offer_adsc")) ;
		params.add(vo.get("page_path")) ;


		params.add(keyCondMap.get("service_offer_id")) ;

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
