package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcSimVO;


public interface RcSimDAO extends DAO {
    RcSimVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException;

    /**
     * ����sim����ѡ���sim��ʵ��
     * @param code String
     * @throws DAOSystemException
     * @return RcSimVO
     */
    public RcSimVO findByCode(String code) throws DAOSystemException;

    ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException;

    boolean update(String pRESOURCE_INSTANCE_ID, RcSimVO vo)
        throws DAOSystemException;

    long delete(String pRESOURCE_INSTANCE_ID) throws DAOSystemException;

    VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

    public boolean updateStorage(String storageId, String conditon)
        throws DAOSystemException;

    /**
     * ��ѯiccid���Ƿ��Ѿ���˫о��
     * @param iccid
     * @return String:-1��iccid��sim���в����ڻ����Աû��Ȩ�޲�����iccid�ţ�
     * -2��iccid��˫о�����Ѵ���;����0����iccid��˫о���л������ڣ����ص��Ǹ�iccid�Ŷ�Ӧ��imsi_id��
     */
    public String isAlreadyExist(String iccid, String operId, String departId);

    /**
     * ����sim��resource_instance_code����sim_chip_type�ֶε�ֵ
     * @param resource_instance_code,sim_chip_type
     * @return
     */
    public void updateSimChipType(String simCodeCorr, String simChipType);

    public int updateBatchSimChipType(List list, String simChipType);
}
