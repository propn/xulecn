package com.ztesoft.oaas.dao.partner;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerVO;

public interface PartnerDAO extends DAO {

	PartnerVO findByPrimaryKey(String pparty_role_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_role_id,PartnerVO vo) throws DAOSystemException;

	long delete( String pparty_role_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
