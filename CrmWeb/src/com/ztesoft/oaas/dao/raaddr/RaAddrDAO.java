package com.ztesoft.oaas.dao.raaddr;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.RaAddrVO;

public interface RaAddrDAO extends DAO {

	RaAddrVO findByPrimaryKey(String paddress_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String paddress_id,RaAddrVO vo) throws DAOSystemException;

	long delete( String paddress_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
