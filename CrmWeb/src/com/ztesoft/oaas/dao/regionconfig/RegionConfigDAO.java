package com.ztesoft.oaas.dao.regionconfig;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RegionConfigVO;

public interface RegionConfigDAO extends DAO {

	RegionConfigVO findByPrimaryKey(String pconfig_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pconfig_id,RegionConfigVO vo) throws DAOSystemException;

	long delete( String pconfig_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
