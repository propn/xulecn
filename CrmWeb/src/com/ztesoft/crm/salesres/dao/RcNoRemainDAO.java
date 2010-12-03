package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;
import com.ztesoft.crm.salesres.vo.RcNoRemainVO;


public interface RcNoRemainDAO extends DAO {
    RcNoRemainVO findByPrimaryKey(String premain_id,
        String presource_instance_id) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String premain_id, String presource_instance_id,
        RcNoRemainVO vo) throws DAOSystemException;

    long delete(String premain_id, String presource_instance_id)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    Map handleNoRemain(RcNoRemainInfoVO vo, String[] nos, String type);
}
