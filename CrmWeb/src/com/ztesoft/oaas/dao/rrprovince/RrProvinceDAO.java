package com.ztesoft.oaas.dao.rrprovince;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RrProvinceVO;

public interface RrProvinceDAO extends DAO {

	RrProvinceVO findByPrimaryKey(String pprov_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pprov_id,RrProvinceVO vo) throws DAOSystemException;

	long delete( String pprov_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
