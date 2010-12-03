package com.ztesoft.oaas.dao.partnerdeptrelat;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerDeptRelatVO;

public interface PartnerDeptRelatDAO extends DAO {

	PartnerDeptRelatVO findByPrimaryKey(String pparty_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_id,PartnerDeptRelatVO vo) throws DAOSystemException;

	long delete( String pparty_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	List queryDepartRelatByPartnerId( String partnerId ) throws DAOSystemException ;
}
