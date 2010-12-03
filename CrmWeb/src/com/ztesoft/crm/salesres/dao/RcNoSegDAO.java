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
     * ��ѯ������Ӫ����Դ��id
     * @return String
     */
    public String findNoFamilyId();

    /**
     * ���ݾ�̬���ݲ��Ҹþ�̬�������õļ���id
     * @return String
     */
    public String findFamilyId(String attrCode);

    //��ѯ��Ҫд��ĺŶ��Ƿ��Ѵ���
    long countRsByCond(String whereCond) throws DAOSystemException;

    //��ѯ��Ҫд��ĺŶ��Ƿ��Ѵ���
    public String findStateByCond(String whereCond) throws DAOSystemException;

    //��ѯ����Ŷ��Ƿ��ص�
    public long findNoSegByCond(String whereCond) throws DAOSystemException;
}
