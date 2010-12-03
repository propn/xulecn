package com.ztesoft.oaas.dao.mmrightmodule;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.MmRightModuleVO;

public interface MmRightModuleDAO extends DAO {

	MmRightModuleVO findByPrimaryKey(String pmenu_id,String pprivilege_id) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pmenu_id, String pprivilege_id,MmRightModuleVO vo) throws DAOSystemException;

	long delete( String pmenu_id, String pprivilege_id) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    ArrayList getPrivsByMenu(String pmenu_id) throws DAOSystemException;
    
    long deleteByMenu(String pmenu_id) throws DAOSystemException;

}
