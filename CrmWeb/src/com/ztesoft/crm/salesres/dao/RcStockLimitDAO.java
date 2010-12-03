package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcStockLimitVO;

public interface RcStockLimitDAO extends DAO {

	RcStockLimitVO findByPrimaryKey(String pFAMILY_ID,String pSTORAGE_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pFAMILY_ID, String pSTORAGE_ID,RcStockLimitVO vo) throws DAOSystemException;

	long delete( String pFAMILY_ID, String pSTORAGE_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
