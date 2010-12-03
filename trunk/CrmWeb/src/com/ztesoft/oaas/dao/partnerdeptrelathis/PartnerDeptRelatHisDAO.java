package com.ztesoft.oaas.dao.partnerdeptrelathis;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerDeptRelatHisVO;

public interface PartnerDeptRelatHisDAO extends DAO {

	PartnerDeptRelatHisVO findByPrimaryKey(String ppartner_id,String pparty_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ppartner_id, String pparty_id,PartnerDeptRelatHisVO vo) throws DAOSystemException;

	long delete( String ppartner_id, String pparty_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
