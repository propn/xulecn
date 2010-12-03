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
   * ɾ����ʼ�ͽ�������֮���ʵ��������
   * @param resBCode String
   * @param resECode String
   * @throws DAOSystemException
   * @return int
   */
  public int deleteAttrByCode(String salesRescId,String resBCode,String resECode,String outStorageId) throws DAOSystemException;

  /**
   * ������ʼ�ͽ�������֮���ʵ���Ĳֿ���Ϣ����ΪinStorageId
   * @param resBCode String
   * @param resECode String
   * @throws DAOSystemException
   * @return int
   */
  public int updateStorageByCode(String tableName,String salesRescId,String resBCode,String resECode,String outStorageId,String inStorageId) throws
    DAOSystemException ;

   /**
    * ����ʵ��idɾ����ʵ������������
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
