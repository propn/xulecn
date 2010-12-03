package com.ztesoft.component.common.staticdata.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.common.staticdata.vo.BillFormatVO;

public interface BillFormatDAO extends DAO {

	BillFormatVO findByPrimaryKey(String pbill_format_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pbill_format_id,BillFormatVO vo) throws DAOSystemException;

	long delete( String pbill_format_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
