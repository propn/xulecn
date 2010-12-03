package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RescNumVO;

public interface RescNumDAO extends DAO {

	RescNumVO findByPrimaryKey(String pCINVENTORYID,String pLAN_ID,String pPK_CALBODY,String pSALES_RESOURCE_ID,String pSTORAGE_ID,String pVBATCHCODE) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pCINVENTORYID, String pLAN_ID, String pPK_CALBODY, String pSALES_RESOURCE_ID, String pSTORAGE_ID, String pVBATCHCODE,RescNumVO vo) throws DAOSystemException;

	long delete( String pCINVENTORYID, String pLAN_ID, String pPK_CALBODY, String pSALES_RESOURCE_ID, String pSTORAGE_ID, String pVBATCHCODE) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

}
