package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import com.powerise.ibss.framework.Base;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;


public class AttrDAO extends AbstractDAO {

	private String SQL_SELECT = "select attr_id,attr_name,eff_date,exp_date,attr_code from ATTRIBUTE where state='00A' ";

	private String SQL_SELECT_COUNT = "select count(*) as col_counts from attribute where 1=1 and state='00A' ";

	private String SQL_INSERT = "insert into ATTRIBUTE (attr_id, attr_desc, value_type, input_method, nullable, default_value, min_value, max_value, display_flag, state, eff_date, exp_date, if_default_value, attr_code, seq, oper_date, party_id, party_role_id, send_bill, attr_name,field_name) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private String SQL_UPDATE = "update ATTRIBUTE set attr_desc=?, value_type=?, input_method=?, nullable=?, default_value=?, min_value=?, max_value=?, display_flag=?, state=?, eff_date=?, exp_date=?, if_default_value=?, attr_code=?, seq=?, oper_date=?,  send_bill=? where 1=1 ";

	private String SQL_DELETE = "delete from ATTRIBUTE where 1=1 ";

	private String SQL_DELETE_KEY = "update ATTRIBUTE set state ='00X' where attr_id=?";

	private String SQL_UPDATE_KEY = "update ATTRIBUTE set attr_desc=?, value_type=?, input_method=?, nullable=?, default_value=?, min_value=?, max_value=?, display_flag=?, state=?, if_default_value=?, attr_code=?,  seq=?, oper_date=sysdate,  send_bill=?,attr_name=?,field_name=? where attr_id=?";

	private String SQL_SELECT_KEY = "select attr_id,attr_desc,value_type,input_method,nullable,default_value,min_value,max_value,display_flag,state,eff_date,exp_date,if_default_value,attr_code,seq,oper_date,party_id,party_role_id,send_bill,attr_name,field_name from ATTRIBUTE where attr_id=? ";

	private String dbName = JNDINames.PM_DATASOURCE ;


	public AttrDAO() {

	}


	/**
	 *
	 * @param map
	 * @return
	 * @throws FrameException
	 *
	 */
	public List translateInsertParams(Map map) throws FrameException{
		if(map == null || map.isEmpty())
			return null ;
		List params = new ArrayList() ;

		params.add(map.get("attr_id")) ;

		params.add(map.get("attr_desc")) ;

		params.add(map.get("value_type")) ;

		params.add(map.get("input_method")) ;

		params.add(map.get("nullable")) ;

		params.add(map.get("default_value")) ;

		params.add(map.get("min_value")) ;

		params.add(map.get("max_value")) ;

		params.add(map.get("display_flag")) ;

		params.add(map.get("state")) ;

		params.add(DAOUtils.parseDateTime(map.get("eff_date" ))) ;

		params.add(DAOUtils.parseDateTime(map.get("exp_date" ))) ;

		params.add(map.get("if_default_value")) ;

		params.add(map.get("attr_code")) ;

		//params.add(map.get("change_agreement_rate")) ;

		params.add(map.get("seq")) ;

		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;

		//params.add(map.get("field_length")) ;

		//params.add(map.get("object_type")) ;

		//params.add(map.get("object_id")) ;

		//params.add(map.get("ord_action_type")) ;

		//params.add(map.get("ord_no")) ;

		//params.add(map.get("cancel_ord_no")) ;

		params.add(map.get("party_id")) ;

		params.add(map.get("party_role_id")) ;

		//params.add(map.get("oper_region_id")) ;

		params.add(map.get("send_bill")) ;

		params.add(map.get("attr_name")) ;

		params.add(map.get("field_name")) ;

		return params ;
	}


	/**
	 *
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;

		List params = new ArrayList() ;

		params.add((String)vo.get("attr_id" ));

		params.add((String)vo.get("attr_desc" ));

		params.add((String)vo.get("value_type" ));

		params.add((String)vo.get("input_method" ));

		params.add((String)vo.get("nullable" ));

		params.add((String)vo.get("default_value" ));

		params.add((String)vo.get("min_value" ));

		params.add((String)vo.get("max_value" ));

		params.add((String)vo.get("display_flag" ));

		params.add((String)vo.get("state" ));

		params.add((String)vo.get("eff_date" ));

		params.add((String)vo.get("exp_date" ));

		params.add((String)vo.get("if_default_value" ));

		params.add((String)vo.get("attr_code" ));

		//params.add((String)vo.get("change_agreement_rate" ));

		params.add((String)vo.get("seq" ));

		params.add((String)vo.get("oper_date" ));

		//params.add((String)vo.get("field_name" ));

		//params.add((String)vo.get("field_length" ));

		//params.add((String)vo.get("object_type" ));

		//params.add((String)vo.get("object_id" ));

		//params.add((String)vo.get("ord_action_type" ));

		//params.add((String)vo.get("ord_no" ));

		//params.add((String)vo.get("cancel_ord_no" ));

		params.add((String)vo.get("site_no" ));

		params.add((String)vo.get("staff_no" ));

		params.add((String)vo.get("oper_bureau_no" ));

		params.add((String)vo.get("send_bill" ));

		//params.add((String)vo.get("pricing_param_id" ));


		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add((String)condParas.get(i));
			}
		}
		return params ;

	}

		/**
	 *
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

		params.add((String)vo.get("attr_desc" ));

		params.add((String)vo.get("value_type" ));

		params.add((String)vo.get("input_method" ));

		params.add((String)vo.get("nullable" ));

		params.add((String)vo.get("default_value" ));

		params.add((String)vo.get("min_value" ));

		params.add((String)vo.get("max_value" ));

		params.add((String)vo.get("display_flag" ));

		params.add((String)vo.get("state" ));

		params.add((String)vo.get("if_default_value" ));

		params.add((String)vo.get("attr_code" ));

		//params.add((String)vo.get("change_agreement_rate" ));

		params.add((String)vo.get("seq" ));

		//

		//params.add((String)vo.get("field_length" ));

		params.add((String)vo.get("send_bill" ));
		params.add((String)vo.get("attr_name" ));
		params.add((String)vo.get("field_name" ));
		params.add((String)keyCondMap.get("attr_id")) ;

		return params ;
	}

		/**
	 *
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 *
	 */
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;

		List params = new ArrayList() ;

		params.add((String)keyCondMap.get("attr_id")) ;

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
