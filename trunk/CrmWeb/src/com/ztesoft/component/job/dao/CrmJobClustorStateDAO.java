package com.ztesoft.component.job.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.component.job.vo.CrmJobClustorStateVO;

public interface CrmJobClustorStateDAO extends DAO {

	CrmJobClustorStateVO findByPrimaryKey(String pclustor_id, String pjob_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update(String pclustor_id, String pjob_id, CrmJobClustorStateVO vo) throws DAOSystemException;

	long delete(String pclustor_id, String pjob_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	/**
	 * ����ִ��ʱ��
	 * 
	 * @param pclustor_id
	 * @param pjob_id
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean updateCheckTime(String pclustor_id, String pjob_id, CrmJobClustorStateVO vo) throws DAOSystemException;

	/**
	 * ����������Ϣ
	 * 
	 * @param pclustor_id
	 * @param pjob_id
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean updateRuningMessage(String pclustor_id, String pjob_id, CrmJobClustorStateVO vo) throws DAOSystemException;

}
