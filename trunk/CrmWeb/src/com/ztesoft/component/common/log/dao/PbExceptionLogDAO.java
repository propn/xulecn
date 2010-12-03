package com.ztesoft.component.common.log.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.vo.PbExceptionLogVO;

public interface PbExceptionLogDAO extends DAO {

	PbExceptionLogVO findByPrimaryKey(String plog_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plog_id,PbExceptionLogVO vo) throws DAOSystemException;

	long delete( String plog_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
