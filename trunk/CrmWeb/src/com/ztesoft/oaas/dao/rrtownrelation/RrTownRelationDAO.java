package com.ztesoft.oaas.dao.rrtownrelation;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RrTownRelationVO;

public interface RrTownRelationDAO extends DAO {

	RrTownRelationVO findByPrimaryKey(String pexch_id,String ptown_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pexch_id, String ptown_id,RrTownRelationVO vo) throws DAOSystemException;

	long delete( String pexch_id, String ptown_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
