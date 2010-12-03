package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcFamilyEntityRelaVO;

public interface RcFamilyEntityRelaDAO extends DAO {

	RcFamilyEntityRelaVO findByPrimaryKey(String pENTITY_TAB_NAME,String pFAMILY_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pENTITY_TAB_NAME, String pFAMILY_ID,RcFamilyEntityRelaVO vo) throws DAOSystemException;

	long delete( String pENTITY_TAB_NAME, String pFAMILY_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
