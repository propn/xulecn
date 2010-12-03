package com.ztesoft.component.job.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.job.vo.CrmJobVO;

public interface CrmJobDAO extends DAO {

	CrmJobVO findByPrimaryKey(String pjob_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pjob_id,CrmJobVO vo) throws DAOSystemException;

	long delete( String pjob_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	public List findRuningByCond() throws DAOSystemException;

}
