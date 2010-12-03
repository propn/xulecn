package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcSimEvdoVO;


public interface RcSimEvdoDAO extends DAO {
    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    /**
     * ����sim��������ѯ��ص�evdo������Ϣ���˴�������evdo��������
     * @param rescInstanceId
     * @return
     */
    public RcSimEvdoVO findEvdoBySimId(String rescInstanceId);

    boolean updateByPk(RcSimEvdoVO vo) throws DAOSystemException;

    boolean update(String whereCond, VO vo) throws DAOSystemException;

    /**
     * ����sim��������ɾ��evdo��������
     * @param rescInstanceId
     * @return
     */
    public long deleteBySimId(String rescInstanceId);

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    /**
     * ��������evdo������
     * @param list
     * @return
     */
    public long insertBatch(List list);
}
