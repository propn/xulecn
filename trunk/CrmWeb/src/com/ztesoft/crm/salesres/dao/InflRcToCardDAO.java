package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;

public interface InflRcToCardDAO extends DAO {

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	public int insertByBatch(List list) throws DAOSystemException;

}
