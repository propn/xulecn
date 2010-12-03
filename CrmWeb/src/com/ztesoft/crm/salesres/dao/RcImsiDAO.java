package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcImsiVO;


public interface RcImsiDAO extends DAO {
    RcImsiVO findByPrimaryKey(String pIMSI_ID) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pIMSI_ID, RcImsiVO vo) throws DAOSystemException;

    long delete(String pIMSI_ID) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public VO getResultForValidate(VO vo) throws DAOSystemException;

    /**
     * 根据imsi码查询其对应的imsiSegId的信息
     * @param vo RcImsiVO
     * @throws DAOSystemException
     * @return String 如果查不到则返回null
     */
    public String qryImsiSeg(String imsiId) throws DAOSystemException;

    void setActCode(String actCode) throws DAOSystemException;

    boolean updateState(String whereCond) throws DAOSystemException;
}
