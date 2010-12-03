package com.ztesoft.component.common.log.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.vo.PbLogVO;

public interface PbLogDAO extends DAO {

	PbLogVO findByPrimaryKey(String pid) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pid,PbLogVO vo) throws DAOSystemException;

	long delete( String pid) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
