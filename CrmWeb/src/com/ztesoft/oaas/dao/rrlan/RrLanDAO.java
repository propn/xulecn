package com.ztesoft.oaas.dao.rrlan;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RrLanVO;

public interface RrLanDAO extends DAO {

	RrLanVO findByPrimaryKey(String plan_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plan_id,RrLanVO vo) throws DAOSystemException;

	long delete( String plan_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
