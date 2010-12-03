package com.ztesoft.component.common.log.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.log.vo.StaffOnlineVO;

public interface StaffOnlineDAO extends DAO {

	StaffOnlineVO findByPrimaryKey(String plog_info_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plog_info_id,StaffOnlineVO vo) throws DAOSystemException;

	long delete( String plog_info_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
