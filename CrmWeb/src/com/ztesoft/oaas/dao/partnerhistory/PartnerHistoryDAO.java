package com.ztesoft.oaas.dao.partnerhistory;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerHistoryVO;

public interface PartnerHistoryDAO extends DAO {

	PartnerHistoryVO findByPrimaryKey(String ppartner_his_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String ppartner_his_id,PartnerHistoryVO vo) throws DAOSystemException;

	long delete( String ppartner_his_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
