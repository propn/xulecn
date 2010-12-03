package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcEntityVO;

public interface RcEntityDAO extends DAO {

	RcEntityVO findByPrimaryKey(String presource_instance_id)
			throws DAOSystemException;

	/**
	 * 根据编码查找营销实例
	 * 
	 * @param rescInstanceCode
	 * @return
	 */
	public RcEntityVO findByEntityCode(String rescInstanceCode)
			throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException;

	boolean update(String presource_instance_id, RcEntityVO vo)
			throws DAOSystemException;

	long delete(String presource_instance_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	public void setFlag(int flag);

	public void setUpdateFlag(int updateFlag);

	boolean checkEntity(String salesRescId);

	public void setTableType(int tableType);

}
