package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;


public interface RcNoSegDAO extends DAO {
    RcNoSegVO findByPrimaryKey(String pNO_SEG_ID) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pNO_SEG_ID, RcNoSegVO vo) throws DAOSystemException;

    long delete(String pNO_SEG_ID) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * 查询号码类营销资源的id
     * @return String
     */
    public String findNoFamilyId();

    /**
     * 根据静态数据查找该静态数据配置的家族id
     * @return String
     */
    public String findFamilyId(String attrCode);

    //查询将要写入的号段是否已存在
    long countRsByCond(String whereCond) throws DAOSystemException;

    //查询将要写入的号段是否已存在
    public String findStateByCond(String whereCond) throws DAOSystemException;

    //查询输入号段是否重叠
    public long findNoSegByCond(String whereCond) throws DAOSystemException;
}
