package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TpmCardAttrControlDAO extends AbstractDAO {

	//查询SQL
	private String SQL_SELECT = "select service_offer_id,action_no,object_type,object_id,field_name,is_visible,is_readonly from TPM_CARD_ATTR_CONTROL where 1=1 ";

	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tpm_card_attr_control where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into TPM_CARD_ATTR_CONTROL (service_offer_id, action_no, object_type, object_id, field_name, is_visible, is_readonly) values (?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TPM_CARD_ATTR_CONTROL set service_offer_id=?, action_no=?, object_type=?, object_id=?, field_name=?, is_visible=?, is_readonly=? where 1=1 ";

	//	普通delete SQL
	private String SQL_DELETE = "delete from TPM_CARD_ATTR_CONTROL where 1=1 ";

		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TPM_CARD_ATTR_CONTROL where service_offer_id=? and object_type=? and object_id=? and field_name=?";

		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update TPM_CARD_ATTR_CONTROL set service_offer_id=?, action_no=?, object_type=?, object_id=?, field_name=?, is_visible=?, is_readonly=? where service_offer_id=? and action_no=? and object_type=? and object_id=? and field_name=?";

		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select service_offer_id,action_no,object_type,object_id,field_name,is_visible,is_readonly from TPM_CARD_ATTR_CONTROL where service_offer_id=? and action_no=? and object_type=? and object_id=? and field_name=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public TpmCardAttrControlDAO() {

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

		params.add(map.get("action_no")) ;

		params.add(map.get("object_type")) ;

		params.add(map.get("object_id")) ;

		params.add(map.get("field_name")) ;

		params.add(map.get("is_visible")) ;

		params.add(map.get("is_readonly")) ;

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


		params.add(vo.get("action_no")) ;


		params.add(vo.get("object_type")) ;


		params.add(vo.get("object_id")) ;


		params.add(vo.get("field_name")) ;


		params.add(vo.get("is_visible")) ;


		params.add(vo.get("is_readonly")) ;

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

		params.add(vo.get("service_offer_id")) ;

		params.add(vo.get("action_no")) ;

		params.add(vo.get("object_type")) ;

		params.add(vo.get("object_id")) ;

		params.add(vo.get("field_name")) ;

		params.add(vo.get("is_visible")) ;

		params.add(vo.get("is_readonly")) ;


		params.add(keyCondMap.get("service_offer_id")) ;

		params.add(keyCondMap.get("action_no")) ;

		params.add(keyCondMap.get("object_type")) ;

		params.add(keyCondMap.get("object_id")) ;

		params.add(keyCondMap.get("field_name")) ;

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

		//params.add(keyCondMap.get("action_no")) ;

		params.add(keyCondMap.get("object_type")) ;

		params.add(keyCondMap.get("object_id")) ;

		params.add(keyCondMap.get("field_name")) ;

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
