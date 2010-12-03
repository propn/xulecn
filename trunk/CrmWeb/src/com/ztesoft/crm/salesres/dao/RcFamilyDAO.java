package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;

public interface RcFamilyDAO
    extends DAO {

  ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

  VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

  void insert(VO vo) throws DAOSystemException;

  boolean update(String whereCond, VO vo) throws DAOSystemException;

  long deleteByCond(String whereCond) throws DAOSystemException;

  boolean checkFamilyState(String familyId) throws DAOSystemException;

  ArrayList searchAttrInfo(String familyId) throws DAOSystemException;

  boolean insertAttrInfo(String attrValue, String attrOwnerType,
                         String familyId) throws DAOSystemException;

  long countAttrInfo(String attrValue, String familyId) throws
      DAOSystemException;

  int deleteAttrInfo(String rcFamilyId, String attrId, String attrOwnerType) throws
      DAOSystemException;

  ArrayList findAttrInfo(String familyId, String attrOwnerType) throws
      DAOSystemException;
  ArrayList findAttrInfo(String familyId, String attrOwnerType,String salesRescId) throws
  DAOSystemException;
  ArrayList findAttrValue(String attrId) throws DAOSystemException;
  public void setAtt_flag(int att_flag) ;
  ArrayList findAttrInfo2(String familyId, String attrOwnerType) throws
  DAOSystemException;
}
