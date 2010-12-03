package com.ztesoft.oaas.dao.chnseg;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ChnSegVO;

public interface ChnSegDAO extends DAO {

	ChnSegVO findByPrimaryKey(String pchannel_segment_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pchannel_segment_id,ChnSegVO vo) throws DAOSystemException;

	long delete( String pchannel_segment_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getChnSegsByPartyRole(String pparty_role_id) throws DAOSystemException;

    public long deleteByPartyRole(String pparty_role_id) throws DAOSystemException;
}
