package com.ztesoft.oaas.dao.exch;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.ExchVO;

public interface ExchDAO extends DAO {

	ExchVO findByPrimaryKey(String pexchange_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pexchange_id,ExchVO vo) throws DAOSystemException;

	long delete( String pexchange_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
    
    ArrayList getAccNbrsByExchRegion(String exchregion_id) throws DAOSystemException;

    long deleteByExchRegion(String region_id) throws DAOSystemException;
}
