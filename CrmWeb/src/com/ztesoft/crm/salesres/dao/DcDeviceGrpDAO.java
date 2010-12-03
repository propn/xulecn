package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.DcDeviceGrpVO;

public interface DcDeviceGrpDAO extends DAO {

	DcDeviceGrpVO findByPrimaryKey(String pDC_DEVICE_SCODE,String pGROUP_CODE,String pNC_RES_CODE) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pDC_DEVICE_SCODE, String pGROUP_CODE, String pNC_RES_CODE,DcDeviceGrpVO vo) throws DAOSystemException;

	long delete( String pDC_DEVICE_SCODE, String pGROUP_CODE, String pNC_RES_CODE) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
