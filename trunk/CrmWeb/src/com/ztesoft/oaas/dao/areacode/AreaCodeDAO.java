package com.ztesoft.oaas.dao.areacode;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.AreaCodeVO;

public interface AreaCodeDAO extends DAO {

	AreaCodeVO findByPrimaryKey(String pregion_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pregion_id,AreaCodeVO vo) throws DAOSystemException;

	long delete( String pregion_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
