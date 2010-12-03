package com.ztesoft.oaas.dao.chsalecomm;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ChSaleCommVO;

public interface ChSaleCommDAO extends DAO {

	ChSaleCommVO findByPrimaryKey(String pcomm_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pcomm_id,ChSaleCommVO vo) throws DAOSystemException;

	long delete( String pcomm_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
