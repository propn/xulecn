/**
 * 
 */
package com.ztesoft.oaas.dao.regionorgrelat;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RegionOrgRelaVO;

public interface RegionOrgRelaDAO extends DAO {

	RegionOrgRelaVO findByPrimaryKey(String pregion_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pregion_id,RegionOrgRelaVO vo) throws DAOSystemException;

	long delete( String pregion_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
