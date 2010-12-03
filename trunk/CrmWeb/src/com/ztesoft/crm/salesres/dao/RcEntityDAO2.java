package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcEntityVO2;

public interface RcEntityDAO2
    extends DAO {

  ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

  VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

  boolean checkStorageId(String storageId);

  boolean checkSalesRescId(String salesRescId);

  boolean checkRescInstanceCode(String rescInstanceCode);

  public boolean checkRescInstanceCode(RcEntityVO2 rvo);

  ArrayList searchAttrInfo(String rescInstanceId,String flag) throws DAOSystemException;

  boolean insertRcEnAttrInfo(String rescInstanceId, String attrId,
                             String attrValue) throws DAOSystemException;

  long countAttrInfo(String rescInstanceId, String attrId) throws
      DAOSystemException;

  int deleteAttrInfo(String rescInstanceId, String attrValue) throws
      DAOSystemException;

  /**
   * 删除起始和结束编码之间的实例的属性
   * @param resBCode String
   * @param resECode String
   * @throws DAOSystemException
   * @return int
   */
  public int deleteAttrByCode(String salesRescId,String resBCode,String resECode,String outStorageId) throws DAOSystemException;

  /**
   * 更新起始和结束编码之间的实例的仓库信息，改为inStorageId
   * @param resBCode String
   * @param resECode String
   * @throws DAOSystemException
   * @return int
   */
  public int updateStorageByCode(String tableName,String salesRescId,String resBCode,String resECode,String outStorageId,String inStorageId) throws
    DAOSystemException ;

   /**
    * 根据实例id删除该实例的所有属性
    * @param rescInstanceId String
    * @throws DAOSystemException
    * @return int
    */
    public int deleteAttrInfoAll(String rescInstanceId) throws
       DAOSystemException ;

public void setUsageFlag(String usageFlag);
    public void setSQL_SELECT(String SQL_SELECT) ;
    public void setSQL_SELECT_COUNT(String SQL_SELECT) ;
    
    public boolean checkManageMode(String whereCond) throws DAOSystemException;

	void insert2(VO vo);
	void setTableName(String tableName);
	boolean update2(String whereCond, VO vo);

	long deleteByCond2(String whereCond);
}
