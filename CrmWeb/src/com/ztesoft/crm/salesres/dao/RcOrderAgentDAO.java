package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcOrderAgentVO;

public interface RcOrderAgentDAO extends DAO {

	RcOrderAgentVO findByPrimaryKey(String pAPP_ID,String pORDER_ID) throws DAOSystemException;

	ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

	boolean update( String pAPP_ID, String pORDER_ID,RcOrderAgentVO vo) throws DAOSystemException;

	long delete( String pAPP_ID, String pORDER_ID) throws DAOSystemException;

	VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

        public void setSQL_SELECT(String SQL_SELECT) ;

        public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT) ;

        /**
         * 更新押金的押金编号
         * @param whereCond String
         * @param acceptId String
         * @throws DAOSystemException
         * @return boolean
         */
        public boolean updateAcceptId(String whereCond,String acceptId) throws DAOSystemException ;


}
