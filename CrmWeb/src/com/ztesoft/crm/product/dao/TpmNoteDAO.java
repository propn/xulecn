package com.ztesoft.crm.product.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils ;
import com.ztesoft.common.util.JNDINames ;

public class TpmNoteDAO extends AbstractDAO {

	//查询SQL
	private String SQL_SELECT = "select note_id,note_name,owner_type,owner_id,note,state,seq,party_id,party_role_id,oper_region_id,oper_date,order_id,attr_value from TPM_NOTE where 1=1 ";

	//	统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tpm_note where 1=1 ";

	//	insert SQl
	private String SQL_INSERT = "insert into TPM_NOTE (note_id, note_name, owner_type, owner_id,owner_name, note, state, seq, party_id, party_role_id, oper_region_id, oper_date, order_id, attr_value) values (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	//	普通update SQL
	private String SQL_UPDATE = "update TPM_NOTE set note_id=?, note_name=?, owner_type=?, owner_id=?, note=?, state=?, seq=?, party_id=?, party_role_id=?, oper_region_id=?, oper_date=?, order_id=?, attr_value=? where 1=1 ";

	//	普通delete SQL
	private String SQL_DELETE = "delete from TPM_NOTE where 1=1 ";

		//	根据主键delete SQL
	private String SQL_DELETE_KEY = "update  TPM_NOTE set state='00X' where note_id=? ";

		//	根据主键update SQL
	private String SQL_UPDATE_KEY = "update TPM_NOTE set note_name=?, owner_type=?, note=?,party_id=?, party_role_id=?, oper_region_id=?, oper_date=sysdate, order_id=? where note_id=? ";

		//	根据主键查询SQL
	private String SQL_SELECT_KEY = "select note_id,note_name,owner_type,owner_id,note,state,seq,party_id,party_role_id,oper_region_id,oper_date,order_id,attr_value from TPM_NOTE where note_id=? and seq=? ";

	//	当前DAO 所属数据库name
	private String dbName = JNDINames.PM_DATASOURCE ;


	public TpmNoteDAO() {

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

		params.add(map.get("note_id")) ;

		params.add(map.get("note_name")) ;

		params.add(map.get("owner_type")) ;

		params.add(map.get("owner_id")) ;

		params.add(map.get("owner_name")) ;

		params.add(map.get("note")) ;

		params.add(map.get("state")) ;

		params.add(map.get("seq")) ;

		params.add(map.get("party_id")) ;

		params.add(map.get("party_role_id")) ;

		params.add(map.get("oper_region_id")) ;

		params.add(DAOUtils.parseDateTime(map.get("oper_date" ))) ;

		params.add(map.get("order_id")) ;

		params.add(map.get("attr_value")) ;

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


		params.add(vo.get("note_id")) ;


		params.add(vo.get("note_name")) ;


		params.add(vo.get("owner_type")) ;


		params.add(vo.get("owner_id")) ;


		params.add(vo.get("note")) ;


		params.add(vo.get("state")) ;


		params.add(vo.get("seq")) ;


		params.add(vo.get("party_id")) ;


		params.add(vo.get("party_role_id")) ;


		params.add(vo.get("oper_region_id")) ;


		params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;


		params.add(vo.get("order_id")) ;


		params.add(vo.get("attr_value")) ;

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

		//params.add(vo.get("note_id")) ;

		params.add(vo.get("note_name")) ;

		params.add(vo.get("owner_type")) ;

		//params.add(vo.get("owner_id")) ;

		params.add(vo.get("note")) ;

		//params.add(vo.get("state")) ;

		//params.add(vo.get("seq")) ;

		params.add(vo.get("party_id")) ;

		params.add(vo.get("party_role_id")) ;

		params.add(vo.get("oper_region_id")) ;

		//params.add(DAOUtils.parseDateTime(vo.get("oper_date" ))) ;

		params.add(vo.get("order_id")) ;

		//params.add(vo.get("attr_value")) ;


		params.add(keyCondMap.get("note_id")) ;

		//params.add(keyCondMap.get("seq")) ;

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

		params.add(keyCondMap.get("note_id")) ;

		//params.add(keyCondMap.get("seq")) ;

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
