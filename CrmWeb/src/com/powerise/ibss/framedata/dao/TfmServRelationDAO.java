package com.powerise.ibss.framedata.dao;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;

public class TfmServRelationDAO extends AbstractDAO {

	// ��ѯSQL
	private String SQL_SELECT = "select service_name,seq,co_service_name,cond_service_name,condition_flag,cond_arg_name from TFM_SERV_RELATION where 1=1 ";

	// ͳ������SQL
	private String SQL_SELECT_COUNT = "select count(*) as col_counts from tfm_serv_relation where 1=1 ";

	// insert SQl
	private String SQL_INSERT = "insert into TFM_SERV_RELATION (service_name, seq, co_service_name, cond_service_name, condition_flag, cond_arg_name) values (?, ?, ?, ?, ?, ?)";

	// ��ͨupdate SQL
	private String SQL_UPDATE = "update TFM_SERV_RELATION set service_name=?, seq=?, co_service_name=?, cond_service_name=?, condition_flag=?, cond_arg_name=? where 1=1 ";

	// ��ͨdelete SQL
	private String SQL_DELETE = "delete from TFM_SERV_RELATION where 1=1 ";

	// ��������delete SQL
	private String SQL_DELETE_KEY = "delete from TFM_SERV_RELATION where service_name=? and seq=?";

	// ��������update SQL
	private String SQL_UPDATE_KEY = "update TFM_SERV_RELATION set service_name=?, seq=?, co_service_name=?, cond_service_name=?, condition_flag=?, cond_arg_name=? where service_name=? and seq=?";

	// ����������ѯSQL
	private String SQL_SELECT_KEY = "select service_name,seq,co_service_name,cond_service_name,condition_flag,cond_arg_name from TFM_SERV_RELATION where service_name=? and seq=? ";

	// ��ǰDAO �������ݿ�name
	private String dbName = JNDINames.DEFAULT_DATASOURCE;

	public TfmServRelationDAO() {

	}

	/**
	 * Insert��������
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

		params.add(map.get("seq"));

		params.add(map.get("co_service_name"));

		params.add(map.get("cond_service_name"));

		params.add(map.get("condition_flag"));

		params.add(map.get("cond_arg_name"));

		return params;
	}

	/**
	 * update ��������
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

		params.add(vo.get("seq"));

		params.add(vo.get("co_service_name"));

		params.add(vo.get("cond_service_name"));

		params.add(vo.get("condition_flag"));

		params.add(vo.get("cond_arg_name"));

		if (condParas != null && !condParas.isEmpty()) {
			for (int i = 0, j = condParas.size(); i < j; i++) {
				params.add(condParas.get(i));
			}
		}
		return params;

	}

	/**
	 * �����������²�������
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

		params.add(vo.get("seq"));

		params.add(vo.get("co_service_name"));

		params.add(vo.get("cond_service_name"));

		params.add(vo.get("condition_flag"));

		params.add(vo.get("cond_arg_name"));

		params.add(keyCondMap.get("service_name"));

		params.add(keyCondMap.get("seq"));

		return params;
	}

	/**
	 * ����������������
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

		params.add(keyCondMap.get("seq"));

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
