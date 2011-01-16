package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;

public class TfmActArgsDAO extends AbstractDAO {

	// 查询SQL
	private String SQL_SELECT = "select action_id,arg_seq,arg_data_type,arg_length,arg_name,in_out_flag from TFM_ACTION_ARGS where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_action_args where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into TFM_ACTION_ARGS (action_id, arg_seq, arg_data_type, arg_length, arg_name, in_out_flag) values (?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update TFM_ACTION_ARGS set action_id=?, arg_seq=?, arg_data_type=?, arg_length=?, arg_name=?, in_out_flag=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from TFM_ACTION_ARGS where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TFM_ACTION_ARGS where action_id=? and arg_seq=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update TFM_ACTION_ARGS set action_id=?, arg_seq=?, arg_data_type=?, arg_length=?, arg_name=?, in_out_flag=? where action_id=? and arg_seq=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select action_id,arg_seq,arg_data_type,arg_length,arg_name,in_out_flag from TFM_ACTION_ARGS where action_id=? and arg_seq=? ";

	// 当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE;

	public TfmActArgsDAO() {

	}

	/**
	 * Insert参数设置
	 * 
	 * @param map
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateInsertParams(Map map) throws FrameException {
		if (map == null || map.isEmpty())
			return null;
		List params = new ArrayList();

		params.add(map.get("action_id"));

		params.add(map.get("arg_seq"));

		params.add(map.get("arg_data_type"));

		params.add(map.get("arg_length"));

		params.add(map.get("arg_name"));

		params.add(map.get("in_out_flag"));

		return params;
	}

	/**
	 * update 参数设置
	 * 
	 * @param vo
	 * @param condParas
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParams(Map vo, List condParas)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("action_id"));

		params.add(vo.get("arg_seq"));

		params.add(vo.get("arg_data_type"));

		params.add(vo.get("arg_length"));

		params.add(vo.get("arg_name"));

		params.add(vo.get("in_out_flag"));

		if (condParas != null && !condParas.isEmpty()) {
			for (int i = 0, j = condParas.size(); i < j; i++) {
				params.add(condParas.get(i));
			}
		}
		return params;

	}

	/**
	 * 根据主键更新参数设置
	 * 
	 * @param vo
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 */
	public List translateUpdateParamsByKey(Map vo, Map keyCondMap)
			throws FrameException {
		if (vo == null || vo.isEmpty())
			return null;
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(vo.get("action_id"));

		params.add(vo.get("arg_seq"));

		params.add(vo.get("arg_data_type"));

		params.add(vo.get("arg_length"));

		params.add(vo.get("arg_name"));

		params.add(vo.get("in_out_flag"));

		params.add(keyCondMap.get("action_id"));

		params.add(keyCondMap.get("arg_seq"));

		return params;
	}

	/**
	 * 主键条件参数设置
	 * 
	 * @param keyCondMap
	 * @return
	 * @throws FrameException
	 * 
	 */
	public List translateKeyCondMap(Map keyCondMap) throws FrameException {
		if (keyCondMap == null || keyCondMap.isEmpty())
			return null;

		List params = new ArrayList();

		params.add(keyCondMap.get("action_id"));

		params.add(keyCondMap.get("arg_seq"));

		return params;
	}

	public String getDbName() {
		return this.dbName;
	}

	public String getDeleteSQLByKey() throws FrameException {

		return this.SQL_DELETE_KEY;

	}

	public String getUpdateSQLByKey() throws FrameException {

		return this.SQL_UPDATE_KEY;

	}

	public String getSelectSQL() {
		return this.SQL_SELECT;
	}

	public String getSelectCountSQL() {
		return this.SQL_SELECT_COUNT;
	}

	public String getInsertSQL() {
		return this.SQL_INSERT;
	}

	public String getUpdateSQL() {
		return this.SQL_UPDATE;
	}

	public String getDeleteSQL() {
		return this.SQL_DELETE;
	}

	public String getSQLSQLByKey() throws FrameException {

		return this.SQL_SELECT_KEY;

	}

}
