package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.SalesRescIdRelaVO;

public interface SalesRescIdRelaDAO extends DAO {

	SalesRescIdRelaVO findByPrimaryKey(String pDC_DEVICE_SCODE,String pNC_SALES_RESOURCE_ID,String pSALES_RESOURCE_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pDC_DEVICE_SCODE, String pNC_SALES_RESOURCE_ID, String pSALES_RESOURCE_ID,SalesRescIdRelaVO vo) throws DAOSystemException;

	long delete( String pDC_DEVICE_SCODE, String pNC_SALES_RESOURCE_ID, String pSALES_RESOURCE_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
