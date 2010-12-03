package com.ztesoft.component.common.staticdata.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.vo.BankVO;

public interface BankDAO extends DAO {

	BankVO findByPrimaryKey(String pbank_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pbank_id,BankVO vo) throws DAOSystemException;

	long delete( String pbank_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
