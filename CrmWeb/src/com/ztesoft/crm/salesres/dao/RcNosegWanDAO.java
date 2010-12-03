package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNosegWanVO;


public interface RcNosegWanDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    RcNosegWanVO findByPrimaryKey(String pSEG_ID) throws DAOSystemException;

    //查询输入号段是否重叠
    public long findNoSegWanByCond(String whereCond) throws DAOSystemException;

    //查询将要写入的号段是否已存在
    long countRsByCond(String whereCond) throws DAOSystemException;

    String checkWan(String segId, String isPass) throws DAOSystemException;
}
