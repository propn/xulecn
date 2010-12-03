package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.AttrVO;

public interface AttrDAO extends DAO {

	AttrVO findByPrimaryKey(String pattr_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pattr_id,AttrVO vo) throws DAOSystemException;

	long delete( String pattr_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

        long findAttrByCond(String cond) throws DAOSystemException;

}
