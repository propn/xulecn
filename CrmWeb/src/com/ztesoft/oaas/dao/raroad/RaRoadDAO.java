package com.ztesoft.oaas.dao.raroad;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RaRoadVO;

public interface RaRoadDAO extends DAO {

	RaRoadVO findByPrimaryKey(String proad_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String proad_id,RaRoadVO vo) throws DAOSystemException;

	long delete( String proad_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
