package com.ztesoft.oaas.dao.individual;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.IndividualVO;

public interface IndividualDAO extends DAO {

	IndividualVO findByPrimaryKey(String pparty_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_id,IndividualVO vo) throws DAOSystemException;

	long delete( String pparty_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
