package com.powerise.ibss.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.powerise.ibss.framework.FrameException;

public class SqlExec {
  //�������
  private Connection conn = null; //���ݿ����Ӷ���
  private ResultSet rs = null;
  private boolean prepared = false;
  private PreparedStatement prepst = null;
  private String strSql; //ִ�е�SQL���

  public SqlExec(Connection connIn) {
    conn = connIn;
  }

  /**
   * �൱����������
   */
  public void destroy() {
    try {
      reset();
    }
    catch (FrameException e) {
      ;
    }
  }

  /**
   * @throws FrameException
   */
  public void reset() throws FrameException {
    try {
      if (prepst != null) {
        prepst.clearParameters();
        prepst.close();
        prepst = null;
      }
      if (prepared == true) {
        prepared = false;
      }
      if (rs != null)
        rs.close();
    }
    catch (Exception e) {
      throw new FrameException( -22079901, "Close ResultSet error.", e.getMessage());
    }
  }

  private void setSql(String asSQL) {
    strSql = asSQL;
  }

  private ResultSet fetch() throws FrameException {
    ResultSet rs;
    try {
      rs = prepst.getResultSet();
    }
    catch (SQLException e) {
      throw new FrameException( -22079904, "Get ResultSet error.", e.getMessage());
    }
    return rs;
  }

  public void run(String errMsg) throws FrameException {
    try {
      if (!prepared)
        prepare();
      prepst.execute();
      rs = fetch();
    }
    catch (SQLException e) {
      throw new FrameException( -22079903,
                               errMsg + "\nexecute sql error:" + e.getMessage() + "\nSQL statement:" +
                               strSql);
    }
  }

  public void run(String asSql, String errMsg) throws FrameException {
    prepare(asSql);
    run(errMsg);
  }

  public void run() throws FrameException {
    run("");
  }

  public int getRows() throws FrameException {
    int iRows = 0;
    try {
      iRows = prepst.getUpdateCount();
    }
    catch (SQLException e) {
      throw new FrameException( -22079903, "get rows error:" + strSql, e.getMessage());
    }
    return iRows;
  }

  private void prepare() throws FrameException {
    reset();
    try {
      prepst = conn.prepareStatement(strSql);
      prepared = true;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new FrameException( -22079906, "Prepare error:" + strSql,
                               e.getMessage());
    }
  }

  public void prepare(String asSql) throws FrameException {
    setSql(asSql);
    prepare();
  }

  public void bind(int aiPos, String asValue) throws FrameException {
    try {
      prepst.setString(aiPos, asValue);
    }
    catch (SQLException e) {
      throw new FrameException( -22079902, "Bind argument error��" + aiPos + asValue);
    }
  }

  public void bind(int aiPos, long alValue) throws FrameException {
    try {
      prepst.setLong(aiPos, alValue);
    }
    catch (SQLException e) {
      throw new FrameException( -22079902,
                               "Bind argument error��" + aiPos + " " + alValue);
    }
  }

  public void bind(int aiPos, int aiValue) throws FrameException {
    try {
      prepst.setInt(aiPos, aiValue);
    }
    catch (SQLException e) {
      throw new FrameException( -22079902,
                               "Bind argument error��" + aiPos + " " + aiValue);
    }
  }

  public void bind(int aiPos, float afValue) throws FrameException {
    try {
      prepst.setFloat(aiPos, afValue);
    }
    catch (Exception e) {
      throw new FrameException( -22079902,
                               "Bind argument error��" + aiPos + " " + afValue);
    }
  }

  public void bind(int aiPos, Object afValue) throws FrameException {
    try {
      prepst.setObject(aiPos, afValue);
    }
    catch (Exception e) {
      throw new FrameException( -22079902,
                               "Bind argument error��" + aiPos + " " + afValue);
    }
  }

  public String getString(String asName) throws FrameException {
    try {
      String value = rs.getString(asName);
      if (value == null)
        value = "";
      return value;
    }
    catch (Exception e) {
      throw new FrameException( -22079902,
                               "GetString error��Column name��" + asName + e.getMessage());
    }
  }

  public int getInt(String asName) throws FrameException {
    try {
      return rs.getInt(asName);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "GetInt error." + e.getMessage());
    }
  }

  public long getLong(String asName) throws FrameException {
    try {
      return rs.getLong(asName);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getLongʧ�ܡ�" + e.getMessage());
    }
  }

  public Object getObject(String asName) throws FrameException {
    try {
      return rs.getObject(asName);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getObjectʧ�ܡ�" + e.getMessage());
    }
  }

  public float getFloat(String asName) throws FrameException {
    try {
      return rs.getFloat(asName);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getFloatʧ�ܡ�" + e.getMessage());
    }
  }

  public String getString(int aiPos) throws FrameException {
    try {
      String strValue = rs.getString(aiPos);
      if (strValue == null)
        strValue = "";
      return strValue;
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getStringʧ��," + e.getMessage());
    }
  }

  public int getInt(int aiPos) throws FrameException {
    try {
      return rs.getInt(aiPos);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getIntʧ�ܡ�" + aiPos);
    }
  }

  public float getFloat(int aiPos) throws FrameException {
    try {
      return rs.getFloat(aiPos);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getFloatʧ�ܡ�" + aiPos);
    }
  }

  public long getLong(int aiPos) throws FrameException {
    try {
      return rs.getLong(aiPos);
    }
    catch (Exception e) {
      throw new FrameException( -22079902, "ִ���α�getLongʧ�ܡ�" + aiPos);
    }
  }

  public boolean next() throws FrameException {
    try {
      return rs.next();
    }
    catch (Exception e) {
      throw new FrameException( -22079908, "ִ���α�Nextʧ�ܡ�", e.getMessage());
    }
  }

  public ResultSet getResultSet() {
    return rs;
  }

}
