package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.crm.salesres.vo.RcNoVO;


public interface RcNoDAO extends DAO {
    RcNoVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException;

    /**
     * ���ݺ���ѡ��ú���ʵ��
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode(String code) throws DAOSystemException;

    /**
     * ���ݺ���ѡ��ú���ʵ�壬����Ȩ���ж�
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode2(String code, String operId, String departId)
        throws DAOSystemException;

    /**
     * ���ݺ�����Ҹú��������ĺŶ���Ϣ
     * @param code String
     * @throws DAOSystemException
     * @return RcNoSegVO:�Ŷ���Ϣ,����鲻���Ŷ���Ϣ�򷵻�null
     */
    public RcNoSegVO findNoSegByNoCode(String code) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pRESOURCE_INSTANCE_ID, RcNoVO vo)
        throws DAOSystemException;

    long delete(String pRESOURCE_INSTANCE_ID) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public void setFlag(int flag);

    /**
     * �����������ºų�
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateStorage(String storageId, String storageName,
        String whereCond) throws DAOSystemException;

    /**
     * �����������ĺ����״̬
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateRescState(String rescState, String whereCond)
        throws DAOSystemException;

    /**
     *  �����������
     * @param list List
     * @return long
     */
    public long batchInsert(RcNoVO vo, String beginnoStr, String endnoStr,
        List levelList) throws DAOSystemException;

    /**
     * �������º���ȼ�
     * @param list
     * @return
     * @throws SQLException
     */
    public int updateRescStateByBatch(List list) throws DAOSystemException;

    /**
     * ��������
     * @param list
     * @return
     * @throws SQLException
     */
    public int updateBatch(RcNoVO vo);

    public Map updateTxtBatch(RcNoVO vo, String flag, List list);

    /**
     * �������º����״̬
     * @param vo
     * @param noArr:��Ҫ���µĺ��������
     * @param checkType:������ͣ�0�������״̬��ֱ�Ӹ��£�1����У��״̬������������B��ԤռE
     * @return Map��result:-1ȱ�ٲ���,1�ɹ�ִ�У���������ʧ�ܼ�¼��errorList:ʧ�ܺ���ļ��ϣ���Щ�������߼����󣬼�������Ҫ��ĺ���
     */
    Map updateBatchNoState(RcNoVO vo, String[] noArr, int checkType,
        String states);

    void setNoFamilyId(String string);

    /**
     *  �����������
     * @param list List
     * @return long
     */
    public long batchAdd(List noList) throws DAOSystemException;
}
