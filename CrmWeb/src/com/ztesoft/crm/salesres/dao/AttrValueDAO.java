package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.AttrValueVO;

public interface AttrValueDAO extends DAO {

	AttrValueVO findByPrimaryKey(String pattr_value_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pattr_value_id,AttrValueVO vo) throws DAOSystemException;

	long delete( String pattr_value_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	ArrayList multiTabQry(String whereCond) throws DAOSystemException;

}