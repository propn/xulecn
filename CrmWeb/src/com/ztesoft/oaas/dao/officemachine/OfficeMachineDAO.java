package com.ztesoft.oaas.dao.officemachine;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.OfficeMachineVO;

public interface OfficeMachineDAO extends DAO {

	OfficeMachineVO findByPrimaryKey(String pmachine_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pmachine_id,OfficeMachineVO vo) throws DAOSystemException;

	long delete( String pmachine_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getOfficeMachinesByOffice(String poffice_id) throws DAOSystemException;

    long deleteByOffice(String poffice_id) throws DAOSystemException;
    
}
