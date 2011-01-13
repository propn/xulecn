package com.ztesoft.oaas.dao.resczone;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RescZoneVO;

public interface RescZoneDAO extends DAO {

	RescZoneVO findByPrimaryKey(String presource_zone_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String presource_zone_id,RescZoneVO vo) throws DAOSystemException;

	long delete( String presource_zone_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
