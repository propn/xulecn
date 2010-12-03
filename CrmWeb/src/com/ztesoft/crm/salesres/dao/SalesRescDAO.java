package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

public interface SalesRescDAO extends DAO {

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

        SalesRescVO findByPrimaryKey(String pSALES_RESOURCE_ID) throws DAOSystemException;

        ArrayList qrySrAttrInfo(String salesRescId) throws DAOSystemException;

        boolean insertAttrInfo(String salesRescId, String attrId, String attrValue) throws DAOSystemException;

        long countAttrInfo(String salesRescId, String attrId) throws DAOSystemException;

        int deleteAttrInfo(String salesRescId, String attrValue) throws DAOSystemException;

		String deleteAttrInfos(String salesRescId, List attrObjs)throws DAOSystemException;

		String insertAttrInfos(String salesRescId, List attrObjs)throws DAOSystemException;

}
