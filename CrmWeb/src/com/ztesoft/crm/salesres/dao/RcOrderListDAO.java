package com.ztesoft.crm.salesres.dao;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.ztesoft.common.dao.DAO;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.crm.salesres.vo.RcOrderListVO;

public interface RcOrderListDAO
    extends DAO {

  RcOrderListVO findByPrimaryKey(String pORDER_ID, String pSALES_RESOURCE_ID) throws
      DAOSystemException;

  ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException;

  boolean update(String pORDER_ID, String pSALES_RESOURCE_ID, RcOrderListVO vo) throws
      DAOSystemException;

  long delete(String pORDER_ID, String pSALES_RESOURCE_ID) throws
      DAOSystemException;

  VO populateCurrRecord(ResultSet rs) throws DAOSystemException;

  public String[] checkRcEntity(String salesRescId,String rc) throws DAOSystemException;
  public String[] checkRcEntity(String salesRescId,String rc,String storgeId) throws DAOSystemException;
  public String getTableMap(String SalesRescId) throws DAOSystemException;
}
