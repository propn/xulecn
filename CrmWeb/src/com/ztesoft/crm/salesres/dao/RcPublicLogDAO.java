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
	 * 记录日志
	 * @param vo：必须包括reworkTime(更改时间)、reworkWen(修改人)、
	 * reworkIp(修改人ip)、reworkTable(修改表)、act(动作)
	 * @param oldValue：旧的值vo
	 * @param newValue：新的值vo
	 * @return
	 */
	public boolean logVO(RcPublicLogVO vo,Object oldValue,Object newValue) throws DAOSystemException;

}
