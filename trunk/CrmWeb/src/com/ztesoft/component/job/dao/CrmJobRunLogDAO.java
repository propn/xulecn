package com.ztesoft.component.job.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.job.vo.CrmJobRunLogVO;

public interface CrmJobRunLogDAO extends DAO {

	CrmJobRunLogVO findByPrimaryKey(String pjob_log_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pjob_log_id,CrmJobRunLogVO vo) throws DAOSystemException;

	long delete( String pjob_log_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
