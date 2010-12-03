package com.ztesoft.component.job.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.job.vo.CrmJobClustorVO;

public interface CrmJobClustorDAO extends DAO {

	CrmJobClustorVO findByPrimaryKey(String pclustor_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pclustor_id,CrmJobClustorVO vo) throws DAOSystemException;

	long delete( String pclustor_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
