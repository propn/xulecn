package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

public class TfmServicesDAO extends AbstractDAO {

	// 查询SQL
	private String SQL_SELECT = "select service_name,env_id,version,service_desc,service_type,(select class_name from tfm_serv_class_def d where d.service_name=s.service_name ) class_name,state,cache_flag,module_name,if_log from TFM_SERVICES s where 1=1 ";

	// 统计总数SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_services where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into TFM_SERVICES (service_name, env_id, version, service_desc, service_type, class_name, state, cache_flag, module_name, if_log) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	// 普通update SQL
	private String SQL_UPDATE = "update TFM_SERVICES set service_name=?, env_id=?, version=?, service_desc=?, service_type=?, class_name=?, state=?, cache_flag=?, module_name=?, if_log=? where 1=1 ";

	// 普通delete SQL
	private String SQL_DELETE = "delete from TFM_SERVICES where 1=1 ";

	// 根据主键delete SQL
	private String SQL_DELETE_KEY = "delete from TFM_SERVICES where service_name=?";

	// 根据主键update SQL
	private String SQL_UPDATE_KEY = "update TFM_SERVICES set service_name=?, env_id=?, version=?, service_desc=?, service_type=?, class_name=?, state=?, cache_flag=?, module_name=?, if_log=? where service_name=?";

	// 根据主键查询SQL
	private String SQL_SELECT_KEY = "select service_name,env_id,version,service_desc,service_type,class_name,state,cache_flag,module_name,if_log from TFM_SERVICES where service_name=? ";

	// 当前DAO 所属数据库name
	private String dbName = JNDINames.DEFAULT_DATASOURCE;

	public TfmServicesDAO() {

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

		params.add(map.get("env_id"));

		params.add(map.get("version"));

		params.add(map.get("service_desc"));

		params.add(map.get("service_type"));

		params.add(map.get("class_name"));

		params.add(map.get("state"));

		params.add(map.get("cache_flag"));

		params.add(map.get("module_name"));

		params.add(map.get("if_log"));

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

		params.add(vo.get("env_id"));

		params.add(vo.get("version"));

		params.add(vo.get("service_desc"));

		params.add(vo.get("service_type"));

		params.add(vo.get("class_name"));

		params.add(vo.get("state"));

		params.add(vo.get("cache_flag"));

		params.add(vo.get("module_name"));

		params.add(vo.get("if_log"));

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

		params.add(vo.get("env_id"));

		params.add(vo.get("version"));

		params.add(vo.get("service_desc"));

		params.add(vo.get("service_type"));

		params.add(vo.get("class_name"));

		params.add(vo.get("state"));

		params.add(vo.get("cache_flag"));

		params.add(vo.get("module_name"));

		params.add(vo.get("if_log"));

		params.add(keyCondMap.get("service_name"));

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
