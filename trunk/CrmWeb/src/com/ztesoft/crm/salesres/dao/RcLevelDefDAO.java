package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;

public interface RcLevelDefDAO
    extends DAO {

  RcLevelDefVO findByPrimaryKey(String pFAMILY_ID, String pRC_LEVEL_ID) throws
      DAOSystemException;

  ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

  public RcLevelDefVO findByLogicPK(String rcLevelId, String lanId) throws
      DAOSystemException ;
  
  boolean update(String pFAMILY_ID, String pRC_LEVEL_ID,String lanId, RcLevelDefVO vo) throws
      DAOSystemException;

  /**
   * ����levelId�޸��ֶ�,�޸ĵ��ֶΰ�����RC_LEVEL_NAME��LEVEL_COMMENTS��PRI_ID��RULE_STRING��is_lucky
   * @param pRC_LEVEL_ID
   * @param vo
   * @return
   * @throws DAOSystemException
   */
  public boolean updateByLevelId(String pRC_LEVEL_ID,RcLevelDefVO vo) throws
	  DAOSystemException;
  
  long delete(String pFAMILY_ID, String pRC_LEVEL_ID) throws DAOSystemException;

  VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

  public long getNextId() throws DAOSystemException;
  
  /**
   * ���ݺ����ѯ��Ҫ����ȼ��õĹ���list������distinct��ֹ�ظ�
   * @param familyId
   * @return
   * @throws DAOSystemException
   */
  public List findByFimilyForCal(String familyId) throws DAOSystemException ;
  /**
   * ���ݺ����ѯ��Ҫ����ȼ��õĹ���list������distinct��ֹ�ظ�
   * @param familyId
   * @return
   * @throws DAOSystemException
   */
  public List findByFimilyAndLanId(String familyId,String lanId) throws DAOSystemException ;
  
  /**
   * ���ĳ����Դ�ȼ���û�б�����
   * @param familyId
   * @param levelId
   * @return
   * @throws DAOSystemException
   */
  public boolean isLevelRef(String familyId,String levelId) throws DAOSystemException ;
  
}
