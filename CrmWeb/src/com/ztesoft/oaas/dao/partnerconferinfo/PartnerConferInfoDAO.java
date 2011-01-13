package com.ztesoft.oaas.dao.partnerconferinfo;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerConferInfoVO;

public interface PartnerConferInfoDAO extends DAO {

	PartnerConferInfoVO findByPrimaryKey(String ppartner_conf_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ppartner_conf_id,PartnerConferInfoVO vo) throws DAOSystemException;

	long delete( String ppartner_conf_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
