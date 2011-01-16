package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

public class TfmServArgsDAO extends AbstractDAO {

	// 查询SQL
	private String SQL_SELECT = "select service_name,name,data_type,data_length,io_flag,map_name,map_flag,default_value from TFM_SERV_ARGS where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_serv_args where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into TFM_SERV_ARGS (service_name, name, data_type, data_length, io_flag, map_name, map_flag, default_value) values (?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update TFM_SERV_ARGS set service_name=?, name=?, data_type=?, data_length=?, io_flag=?, map_name=?, map_flag=?, default_value=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from TFM_SERV_ARGS where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TFM_SERV_ARGS where service_name=? and name=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update TFM_SERV_ARGS set service_name=?, name=?, data_type=?, data_length=?, io_flag=?, map_name=?, map_flag=?, default_value=? where service_name=? and name=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select service_name,name,data_type,data_length,io_flag,map_name,map_flag,default_value from TFM_SERV_ARGS where service_name=? and name=? ";

	// 当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE;

	public TfmServArgsDAO() {

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

		params.add(map.get("service_name"));

		params.add(map.get("name"));

		params.add(map.get("data_type"));

		params.add(map.get("data_length"));

		params.add(map.get("io_flag"));

		params.add(map.get("map_name"));

		params.add(map.get("map_flag"));

		params.add(map.get("default_value"));

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

		params.add(vo.get("service_name"));

		params.add(vo.get("name"));

		params.add(vo.get("data_type"));

		params.add(vo.get("data_length"));

		params.add(vo.get("io_flag"));

		params.add(vo.get("map_name"));

		params.add(vo.get("map_flag"));

		params.add(vo.get("default_value"));

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

		params.add(vo.get("service_name"));

		params.add(vo.get("name"));

		params.add(vo.get("data_type"));

		params.add(vo.get("data_length"));

		params.add(vo.get("io_flag"));

		params.add(vo.get("map_name"));

		params.add(vo.get("map_flag"));

		params.add(vo.get("default_value"));

		params.add(keyCondMap.get("service_name"));

		params.add(keyCondMap.get("name"));

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

		params.add(keyCondMap.get("service_name"));

		params.add(keyCondMap.get("name"));

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
