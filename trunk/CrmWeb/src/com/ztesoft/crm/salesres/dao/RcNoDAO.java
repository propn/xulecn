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
     * 根据号码选择该号码实体
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode(String code) throws DAOSystemException;

    /**
     * 根据号码选择该号码实体，加上权限判断
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode2(String code, String operId, String departId)
        throws DAOSystemException;

    /**
     * 根据号码查找该号码所属的号段信息
     * @param code String
     * @throws DAOSystemException
     * @return RcNoSegVO:号段信息,如果查不到号段信息则返回null
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
     * 根据条件更新号池
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateStorage(String storageId, String storageName,
        String whereCond) throws DAOSystemException;

    /**
     * 根据条件更改号码的状态
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateRescState(String rescState, String whereCond)
        throws DAOSystemException;

    /**
     *  批量插入号码
     * @param list List
     * @return long
     */
    public long batchInsert(RcNoVO vo, String beginnoStr, String endnoStr,
        List levelList) throws DAOSystemException;

    /**
     * 批量更新号码等级
     * @param list
     * @return
     * @throws SQLException
     */
    public int updateRescStateByBatch(List list) throws DAOSystemException;

    /**
     * 批量更新
     * @param list
     * @return
     * @throws SQLException
     */
    public int updateBatch(RcNoVO vo);

    public Map updateTxtBatch(RcNoVO vo, String flag, List list);

    /**
     * 批量更新号码的状态
     * @param vo
     * @param noArr:需要更新的号码的数组
     * @param checkType:检查类型，0代表不检查状态，直接更新；1代表校验状态：不能是已用B、预占E
     * @return Map：result:-1缺少参数,1成功执行，但可能有失败记录。errorList:失败号码的集合，这些号码是逻辑错误，即不符合要求的号码
     */
    Map updateBatchNoState(RcNoVO vo, String[] noArr, int checkType,
        String states);

    void setNoFamilyId(String string);

    /**
     *  批量插入号码
     * @param list List
     * @return long
     */
    public long batchAdd(List noList) throws DAOSystemException;
}
