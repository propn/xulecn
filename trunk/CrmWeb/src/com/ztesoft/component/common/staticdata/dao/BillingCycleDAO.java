package com.ztesoft.component.common.staticdata.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.vo.BillingCycleVO;

public interface BillingCycleDAO extends DAO {

	BillingCycleVO findByPrimaryKey(String pbilling_cycle_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pbilling_cycle_id,BillingCycleVO vo) throws DAOSystemException;

	long delete( String pbilling_cycle_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
