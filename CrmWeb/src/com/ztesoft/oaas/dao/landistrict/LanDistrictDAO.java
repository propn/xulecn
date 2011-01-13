package com.ztesoft.oaas.dao.landistrict;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.LanDistrictVO;

public interface LanDistrictDAO extends DAO {

	LanDistrictVO findByPrimaryKey(String pdistrict_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pdistrict_id,LanDistrictVO vo) throws DAOSystemException;

	long delete( String pdistrict_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

	PageModel getLanDistrictPaginate(String districtCode, String districtName, String dealExch, ArrayList regionVOs, int pageIndex, int pageSize ) throws DAOSystemException;
}