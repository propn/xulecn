package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.SaleRescPricVO;

public interface SaleRescPricDAO extends DAO {

	SaleRescPricVO findByPrimaryKey(String pbusiness_id,String presource_level,String psales_resource_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pbusiness_id, String presource_level, String psales_resource_id,SaleRescPricVO vo) throws DAOSystemException;

	long delete( String pbusiness_id, String presource_level, String psales_resource_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
	
	public void setSQL_SELECT(String sql_select);

	public void setSQL_DELETE(String sql_delete);

	public void setSQL_DELETE_BY_WHERE(String sql_delete_by_where);

	public void setSQL_INSERT(String sql_insert);

	public void setSQL_SELECT_COUNT(String sql_select_count);

	public void setSQL_UPDATE(String sql_update);
}
