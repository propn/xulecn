package com.ztesoft.crm.salesres.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;

public class SqlExcuteByStr {
  public SqlExcuteByStr() {
  }

  Connection conn = null;

  PreparedStatement stmt = null;

  ResultSet rs = null;

  // 取历史表序号
  public int getSeqNbrSqlStr(String sqlstr) throws DAOSystemException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    int seqr = 0;
    try {

      conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB);
      stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sqlstr));
      rs = stmt.executeQuery();
      while (rs.next()) {
        seqr = rs.getInt("seq");
      }

    }
    catch (SQLException se) {
      Debug.print(sqlstr, this);
      throw new DAOSystemException("SQLException while getting sql:\n",
                                   se);
    }
    finally {
      DAOUtils.closeResultSet(rs, this);
      DAOUtils.closeStatement(stmt, this);
      DAOUtils.closeConnection(conn, this);
    }

    return seqr;
  }

  // 执行SQL 语句返回受影响行数
  public int sqlExcu(String sqlstr) throws DAOSystemException {

    int n = 0;
    try {

      conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
      stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sqlstr));

      rs = stmt.executeQuery();
      while (rs.next()) {

        n = n + 1;

      }
    }
    catch (SQLException se) {
      Debug.print(sqlstr, this);
      throw new DAOSystemException("SQLException while getting sql:\n",
                                   se);
    }
    finally {
      DAOUtils.closeResultSet(rs, this);
      DAOUtils.closeStatement(stmt, this);
      DAOUtils.closeConnection(conn, this);
    }
    return n;

  }

  // 更新历史表语句
  public void LUpdate(String sqlstr) throws DAOSystemException {

    try {

      conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
      stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sqlstr));
      stmt.executeUpdate();
    }
    catch (SQLException se) {
      Debug.print(sqlstr, this);
      throw new DAOSystemException("SQLException while update sql:\n", se);
    }
    finally {
      DAOUtils.closeResultSet(rs, this);
      DAOUtils.closeStatement(stmt, this);
      DAOUtils.closeConnection(conn, this);
    }

  }

  // 执行SQL语句 返回一条记录的一个字段的值 提供给查询用
  public String getString(String sqlStr) throws DAOSystemException {
    String retV = "";
    try {
      conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
      stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sqlStr));
      rs = stmt.executeQuery();
      while (rs.next()) {
        retV = rs.getString("result");
        break;
      }
    }
    catch (SQLException se) {
      Debug.print(sqlStr, this);
      throw new DAOSystemException("SQLException while update sql:\n", se);
    }
    finally {
      DAOUtils.closeResultSet(rs, this);
      DAOUtils.closeStatement(stmt, this);
      DAOUtils.closeConnection(conn, this);
    }
    return retV;
  }

  public String getCNStr(String fieldnameCN, String fieldname, String tablename,
                         String whereCond) throws DAOSystemException {
    Connection conn = null;
    PreparedStatement stmt = null;
    ResultSet rs = null;
    String CNStr = "";
    String SQL = "";
    try {
      if ( (whereCond == null) || ("".equalsIgnoreCase(whereCond))) {
        return CNStr;
      }
      if ("-1".equalsIgnoreCase(whereCond)) {
        return CNStr = "全省";
      }

      conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
      SQL = "SELECT " + fieldnameCN + "  FROM " + tablename + "   WHERE " +
          fieldname + " in(" + whereCond + ")";
      stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
      rs = stmt.executeQuery();
      if (rs.next()) {
        CNStr = rs.getString(1);
        while (rs.next()) {
          CNStr = CNStr + "," + rs.getString(1);
        }
      }

      return CNStr;

    }
    catch (SQLException se) {
      Debug.print(SQL, this);
      throw new DAOSystemException("SQLException while getting sql:\n" + SQL,
                                   se);
    }
    finally {
      DAOUtils.closeResultSet(rs, this);
      DAOUtils.closeStatement(stmt, this);
      DAOUtils.closeConnection(conn, this);
    }

  }

  // 通过infoId取得其对应的名称;
  public String getInfoNameByInfoId(String infoId) throws DAOSystemException {
    String retV = "";
    if ( (infoId != null) && (!"".equals(infoId)) ) {
      String strSql = "select * from dc_data_info where info_id=" + infoId.trim();
      try {
        conn = DAOUtils.getDBConnection(JNDINames.CRM_RCDB, this);
        stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(strSql));
        rs = stmt.executeQuery();
        while (rs.next()) {
          retV = rs.getString("info_name");
          break;
        }
      }
      catch (SQLException se) {
        Debug.print(strSql, this);
        throw new DAOSystemException("SQLException while update sql:\n", se);
      }
      finally {
        DAOUtils.closeResultSet(rs, this);
        DAOUtils.closeStatement(stmt, this);
        DAOUtils.closeConnection(conn, this);
      }
    }
    return retV;
  }

}
