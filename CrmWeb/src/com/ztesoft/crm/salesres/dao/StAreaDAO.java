package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.StAreaVO;

public interface StAreaDAO extends DAO {

	StAreaVO findByPrimaryKey(String pAREA_ID,String pLAN_ID,String pREGION_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pAREA_ID, String pLAN_ID, String pREGION_ID,StAreaVO vo) throws DAOSystemException;

	long delete( String pAREA_ID, String pLAN_ID, String pREGION_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
