package com.ztesoft.oaas.dao.position;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PositionVO;

public interface PositionDAO extends DAO {

	PositionVO findByPrimaryKey(String pposition_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pposition_id,PositionVO vo) throws DAOSystemException;

	long delete( String pposition_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getAllPositions() throws DAOSystemException;
}
