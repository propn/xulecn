package com.ztesoft.crm.product.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.powerise.ibss.framework.FrameException;
import com.ztesoft.common.dao.AbstractDAO;
import com.ztesoft.common.util.JNDINames;

/**
 * 角色产品成员关系
 * @author qin.guoquan
 *
 */
public class RoleProdRelaDao extends AbstractDAO {

	// insert SQl
	private String SQL_INSERT = "insert into ROLE_PROD_RELA (PROD_OFFER_ROLE_CD, PRODUCT_ID, MAX_COUNT, MIN_COUNT, RULE_TYPE) values (?, ?, ?, ?, ?)";
	
	// 当前DAO 所属数据库name
	private String dbName = JNDINames.VSOP_DATASOURCE;

	public RoleProdRelaDao() {

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
		
		if (map == null || map.isEmpty()) return null;
		List params = new ArrayList();

		params.add(map.get("prod_offer_role_cd"));
		params.add(map.get("product_id"));
		params.add(map.get("max_count"));
		params.add(map.get("min_count"));
		params.add(map.get("rule_type"));

		return params;
	}

	public String getDbName() {
		return this.dbName;
	}
	
	public String getInsertSQL() {
		return this.SQL_INSERT;
	}

	public String getDeleteSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDeleteSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSQLSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSelectCountSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getSelectSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUpdateSQL() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getUpdateSQLByKey() throws FrameException {
		// TODO Auto-generated method stub
		return null;
	}

}
