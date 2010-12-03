package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;


public interface RcNoRemainInfoDAO extends DAO {
    RcNoRemainInfoVO findByPrimaryKey(String premain_id)
        throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String premain_id, RcNoRemainInfoVO vo)
        throws DAOSystemException;

    long delete(String premain_id) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    void batchInsert(VO vo, List nolist, List reidlist, String type)
        throws DAOSystemException;
}
