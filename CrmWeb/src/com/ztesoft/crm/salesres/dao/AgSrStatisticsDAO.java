package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;

public interface AgSrStatisticsDAO extends DAO {

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    void setAcctCode(String acctCode) throws DAOSystemException;

    void setSql(String qrySql,String countSql) throws DAOSystemException;

}
