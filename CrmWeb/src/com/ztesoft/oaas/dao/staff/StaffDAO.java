package com.ztesoft.oaas.dao.staff;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.StaffVO;

public interface StaffDAO extends DAO {
	boolean isSuperStaff(String StaffCode) throws DAOSystemException ;
	
	StaffVO getStaffByStaffCode( String staffCode ) throws DAOSystemException;
	
	StaffVO findByPrimaryKey(String pparty_role_id) throws DAOSystemException;
	
	long countBySql( String sql ) throws DAOSystemException;
	
	//PageModel findBySqlPaginate( StaffVO vo, int pageIndex, int pageSize ) throws DAOSystemException;
	PageModel findByStaffVO( StaffVO vo, int pageIndex, int pageSize ) throws DAOSystemException;
	
	PageModel findByStaffPromotionVO( StaffVO vo, int pageIndex, int pageSize ) throws DAOSystemException;
	
	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pparty_role_id,StaffVO vo) throws DAOSystemException;

	long delete( String pparty_role_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getPrivs(String pparty_role_id) throws DAOSystemException;
    
    //ArrayList getStaffsByOrg(String pparty_id) throws DAOSystemException;
    
    //ArrayList getStaffsByStaffCond(StaffVO voStaff) throws DAOSystemException;
    
    public HashMap getSrSysParams()throws DAOSystemException;
}
