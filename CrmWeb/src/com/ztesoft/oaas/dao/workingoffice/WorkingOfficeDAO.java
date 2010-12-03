package com.ztesoft.oaas.dao.workingoffice;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.WorkingOfficeVO;

public interface WorkingOfficeDAO extends DAO {

	WorkingOfficeVO findByPrimaryKey(String poffice_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String poffice_id,WorkingOfficeVO vo) throws DAOSystemException;

	long delete( String poffice_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
