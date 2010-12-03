package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcPublicLogVO;

public interface RcPublicLogDAO extends DAO {

	RcPublicLogVO findByPrimaryKey(String plog_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String plog_id,RcPublicLogVO vo) throws DAOSystemException;

	long delete( String plog_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	/**
	 * ��¼��־
	 * @param vo���������reworkTime(����ʱ��)��reworkWen(�޸���)��
	 * reworkIp(�޸���ip)��reworkTable(�޸ı�)��act(����)
	 * @param oldValue���ɵ�ֵvo
	 * @param newValue���µ�ֵvo
	 * @return
	 */
	public boolean logVO(RcPublicLogVO vo,Object oldValue,Object newValue) throws DAOSystemException;

}
