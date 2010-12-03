package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.SrDepositoryDAO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SrDepositoryDAOImpl implements SrDepositoryDAO {
    public SrDepositoryDAOImpl() {
    }

    /**
     * countByCond
     *
     * @param whereCond String
     * @return long
     */
    public long countByCond(String whereCond) {
        return 0L;
    }

    /**
     * deleteByCond
     *
     * @param whereCond String
     * @return long
     */
    public long deleteByCond(String whereCond) {
        return 0L;
    }

    /**
     * findByCond
     *
     * @param where String
     * @param queryFilter QueryFilter
     * @return List
     */
    public List findByCond(String where, QueryFilter queryFilter) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = where;
        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmt.executeQuery();

            List retList = new ArrayList();

            if (queryFilter != null) {
                retList = queryFilter.doPostFilter(rs, this);
            } else {
                retList = fetchMultiResults(rs);
            }

            return retList;
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * fetchMultiResults
     *
     * @param rs ResultSet
     * @return List
     */
    private List fetchMultiResults(ResultSet rs) throws SQLException {
        List list = new ArrayList();

        if (rs != null) {
            ResultSetMetaData md = rs.getMetaData();

            while (rs.next()) {
                HashMap map = new HashMap();

                for (int i = 0; i < md.getColumnCount(); i++) {
                    map.put(md.getColumnName(i + 1), rs.getObject((i + 1)));
                }

                list.add(map);
            }
        }

        return list;
    }

    /**
     * findByCond
     *
     * @param whereCond String
     * @return List
     */
    public List findByCond(String whereCond) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(whereCond));
            rs = stmt.executeQuery();

            RcStorageVO vo = null;
            List list = new ArrayList();
            ResultSetMetaData rsmd = rs.getMetaData();
            HashMap map = null;

            while (rs.next()) {
                vo = new RcStorageVO();
                map = new HashMap();
                System.out.println("---------- " + rsmd.getColumnCount());

                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    String cn = rsmd.getColumnName(i).toUpperCase();
                    System.out.println(cn + "---" + rs.getString(i));
                    map.put(cn, rs.getString(i));
                }

                vo.loadFromHashMap(map);
                list.add(vo);
            }

            return list;
        } catch (SQLException se) {
            Debug.print(se, whereCond, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                whereCond, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * getEmptyVO
     *
     * @return VO
     */
    public VO getEmptyVO() {
        return null;
    }

    /**
     * insert
     *
     * @param vo VO
     */
    public void insert(VO vo) {
    }

    /**
     * populateCurrRecord
     *
     * @param rs ResultSet
     * @return VO
     */
    public VO populateCurrRecord(ResultSet rs) {
        return null;
    }

    /**
     * update
     *
     * @param cond String
     * @param vo VO
     * @return boolean
     */
    public boolean update(String cond, VO vo) {
        return false;
    }

    /**
     * update
     *
     * @param sql String
     * @return String
     */
    public int update(String sql, List args) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            if (args != null) {
                for (int i = 0; i < args.size(); i++) {
                    stmt.setObject(i + 1, args.get(i));
                }
            }

            return stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(se, sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
