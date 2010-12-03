package com.ztesoft.oaas.dao.addr;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.AddrVO;

public interface AddrDAO extends DAO {

	AddrVO findByPrimaryKey(String paddress_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String paddress_id,AddrVO vo) throws DAOSystemException;

	long delete( String paddress_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
