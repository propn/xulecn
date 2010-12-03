package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcServAcceptVO;

public interface RcServAcceptDAO extends DAO {

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	public RcServAcceptVO findByLogId(String logId);

	public void setSQL_SELECT(String sql_select);

	public void setSQL_SELECT_COUNT(String sql_select_count);
	
	public boolean updateState(String state,String sAcceptId) throws DAOSystemException;
	
	public void setFlag(int flag);
	
}
