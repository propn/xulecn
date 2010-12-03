package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNobagRuleVO;


public interface RcNobagRuleDAO extends DAO {
    public RcNobagRuleVO findByPk(String bagruleId);

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public boolean updateByPk(RcNobagRuleVO vo) throws DAOSystemException;

    /**
     * ���º������������
     * @param bagruleId
     * @return
     * @throws DAOSystemException
     */
    public boolean updateNoNum(String bagruleId) throws DAOSystemException;
}
