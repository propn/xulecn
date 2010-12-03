package com.ztesoft.oaas.dao.party;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartyVO;

public interface PartyDAO extends DAO {

	PartyVO findByPrimaryKey(String pparty_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_id,PartyVO vo) throws DAOSystemException;

	long delete( String pparty_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
