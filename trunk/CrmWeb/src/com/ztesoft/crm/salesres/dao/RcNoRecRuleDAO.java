package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNoRecRuleVO;


public interface RcNoRecRuleDAO extends DAO {
    RcNoRecRuleVO findByPrimaryKey(String pLAN_ID) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pLAN_ID, RcNoRecRuleVO vo) throws DAOSystemException;

    /**
     * ��������
     * @param rules
     * @return
     * @throws DAOSystemException
     */
    public long updateBatch(RcNoRecRuleVO[] rules) throws DAOSystemException;

    long delete(String pLAN_ID) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ��ѯ�����еı���������
     * @return
     */
    public List findAllLanConf();
}
