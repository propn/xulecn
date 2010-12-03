package com.ztesoft.oaas.dao.partnerinfo;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerInfoVO;
import com.ztesoft.oaas.vo.PartnerQueryVO;

public interface PartnerInfoDAO extends DAO {

	PartnerInfoVO findByPrimaryKey(String ppartner_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ppartner_id,PartnerInfoVO vo) throws DAOSystemException;

	long delete( String ppartner_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	List queryPartner( PartnerQueryVO vo ) throws DAOSystemException;
}
