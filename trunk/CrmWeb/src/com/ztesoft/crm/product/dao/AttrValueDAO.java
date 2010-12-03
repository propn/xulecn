package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;


public class AttrValueDAO extends AbstractDAO {


	private String SQL_SELECT = "select attr_value_id,attr_id,attr_value,attr_value_desc,order_id,ord_action_type,ord_no,cancel_ord_no,state,seq,oper_date from ATTRIBUTE_VALUE where state ='00A' ";


	private String SQL_SELECT_COUNT = "select count(*) as col_counts from attribute_value where 1=1 ";


	private String SQL_INSERT = "insert into ATTRIBUTE_VALUE (attr_value_id, attr_id, attr_value, attr_value_desc, order_id, oper_date ,state) values (?, ?, ?, ?, ?, sysdate,'00A')";


	private String SQL_UPDATE = "update ATTRIBUTE_VALUE set attr_value_id=?, attr_id=?, attr_value=?, attr_value_desc=?, order_id=?, ord_action_type=?, ord_no=?, cancel_ord_no=?, state=?, seq=?, oper_date=? where 1=1 ";


	private String SQL_DELETE = "delete from ATTRIBUTE_VALUE where 1=1 ";


	private String SQL_DELETE_KEY = "delete ATTRIBUTE_VALUE  where attr_value_id=?";


	private String SQL_UPDATE_KEY = "update ATTRIBUTE_VALUE set attr_id=?, attr_value=?, attr_value_desc=?, order_id=?, oper_date=sysdate where attr_value_id=? ";


	private String SQL_SELECT_KEY = "select attr_value_id,attr_id,attr_value,attr_value_desc,order_id,ord_action_type,ord_no,cancel_ord_no,state,seq,oper_date from ATTRIBUTE_VALUE where state ='00A' and attr_value_id=? ";


	private String dbName = JNDINames.PM_DATASOURCE ;


	public AttrValueDAO() {

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

		params.add((String)map.get("attr_value_id" ));

		params.add((String)map.get("attr_id" ));

		params.add((String)map.get("attr_value" ));

		params.add((String)map.get("attr_value_desc" ));

		params.add((String)map.get("order_id" ));

		//params.add((String)map.get("oper_date" ));

		return params ;
	}


	/**
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo , List condParas) throws FrameException{
		if(vo == null || vo.isEmpty() )
			return null ;

		List params = new ArrayList() ;

		params.add((String)vo.get("attr_value_id" ));

		params.add((String)vo.get("attr_id" ));

		params.add((String)vo.get("attr_value" ));

		params.add((String)vo.get("attr_value_desc" ));

		params.add((String)vo.get("order_id" ));

		params.add((String)vo.get("ord_action_type" ));

		params.add((String)vo.get("ord_no" ));

		params.add((String)vo.get("cancel_ord_no" ));

		params.add((String)vo.get("state" ));

		params.add((String)vo.get("seq" ));

		params.add((String)vo.get("oper_date" ));


		if(condParas!= null && !condParas.isEmpty() ){
			for(int i = 0 ,j=condParas.size() ; i < j ; i++ ){
				params.add((String)condParas.get(i));
			}
		}
		return params ;

	}

		/**
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

		//params.add((String)vo.get("attr_value_id" ));

		params.add((String)vo.get("attr_id" ));

		params.add((String)vo.get("attr_value" ));

		params.add((String)vo.get("attr_value_desc" ));

		params.add((String)vo.get("order_id" ));

		//params.add((String)vo.get("ord_action_type" ));

		//params.add((String)vo.get("ord_no" ));

		params.add((String)keyCondMap.get("attr_value_id")) ;

		return params ;
	}

		/**
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 *
	 */
	public List translateKeyCondMap(Map keyCondMap) throws FrameException{
		if(keyCondMap == null || keyCondMap.isEmpty())
			return null ;

		List params = new ArrayList() ;

		params.add((String)keyCondMap.get("attr_value_id")) ;

		//params.add((String)keyCondMap.get("seq")) ;

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
