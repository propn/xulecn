package com.ztesoft.component.common.log.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.vo.PbLogTypeVO;

public interface PbLogTypeDAO extends DAO {

	PbLogTypeVO findByPrimaryKey(String plog_type) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plog_type,PbLogTypeVO vo) throws DAOSystemException;

	long delete( String plog_type) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
