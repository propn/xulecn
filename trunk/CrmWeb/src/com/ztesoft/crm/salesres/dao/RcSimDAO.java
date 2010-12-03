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
     * 根据sim卡号选择该sim卡实体
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
     * 查询iccid号是否已经是双芯卡
     * @param iccid
     * @return String:-1该iccid在sim卡中不存在或操作员没有权限操作该iccid号；
     * -2该iccid在双芯表中已存在;大于0代表iccid在双芯表中还不存在，返回的是该iccid号对应的imsi_id；
     */
    public String isAlreadyExist(String iccid, String operId, String departId);

    /**
     * 根据sim卡resource_instance_code更新sim_chip_type字段的值
     * @param resource_instance_code,sim_chip_type
     * @return
     */
    public void updateSimChipType(String simCodeCorr, String simChipType);

    public int updateBatchSimChipType(List list, String simChipType);
}
