package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.InflRcToCardDAO;
import com.ztesoft.crm.salesres.vo.InflRcToCardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class InflRcToCardDAOImpl implements InflRcToCardDAO {
    private String SQL_SELECT = "SELECT SEQ_ID,SALES_RESOURCE_ID,RESOURCE_TYPE,SALES_NUM,NMNY,INFFLAG,OPER_ID,OPER_NAME,ATIME,CTIME,ITIME,STATE,STATE_MSG FROM INFL_RC_TO_CARD";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM INFL_RC_TO_CARD";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO INFL_RC_TO_CARD ( SEQ_ID,SALES_RESOURCE_ID,RESOURCE_TYPE,SALES_NUM,NMNY,INFFLAG,OPER_ID,OPER_NAME,ATIME,CTIME,ITIME,STATE,STATE_MSG ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE INFL_RC_TO_CARD SET  SEQ_ID = ?, SALES_RESOURCE_ID = ?, RESOURCE_TYPE = ?, SALES_NUM = ?, NMNY = ?, INFFLAG = ?, OPER_ID = ?, OPER_NAME = ?, ATIME = ?, CTIME = ?, ITIME = ?, STATE = ?, STATE_MSG = ? WHERE";
    private String SQL_DELETE = "DELETE FROM INFL_RC_TO_CARD WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM INFL_RC_TO_CARD ";

    public InflRcToCardDAOImpl() {
    }

    public ArrayList findBySql(String sql, String[] sqlParams)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setMaxRows(maxRows);

            for (int i = 0; (sqlParams != null) && (i < sqlParams.length);
                    i++) {
                stmt.setString(i + 1, sqlParams[i]);
            }

            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    protected ArrayList fetchMultiResults(ResultSet rs)
        throws SQLException {
        ArrayList resultList = new ArrayList();

        while (rs.next()) {
            InflRcToCardVO vo = new InflRcToCardVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(InflRcToCardVO vo, ResultSet rs)
        throws SQLException {
        vo.setSeqId(DAOUtils.trimStr(rs.getString("SEQ_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescType(DAOUtils.trimStr(rs.getString("RESOURCE_TYPE")));
        vo.setSalesNum(DAOUtils.trimStr(rs.getString("SALES_NUM")));
        vo.setNmny(DAOUtils.trimStr(rs.getString("NMNY")));
        vo.setInfflag(DAOUtils.trimStr(rs.getString("INFFLAG")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setAtime(DAOUtils.getFormatedDateTime(rs.getDate("ATIME")));
        vo.setCtime(DAOUtils.getFormatedDateTime(rs.getDate("CTIME")));
        vo.setItime(DAOUtils.getFormatedDateTime(rs.getDate("ITIME")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setStateMsg(DAOUtils.trimStr(rs.getString("STATE_MSG")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        InflRcToCardVO vo = new InflRcToCardVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord³ö´í", this);
            throw new DAOSystemException("SQLException while populateDto:\n", se);
        }

        return vo;
    }

    public List findByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_SELECT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
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

    public void insert(VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            int index = 1;

            if ("".equals(((InflRcToCardVO) vo).getSeqId())) {
                ((InflRcToCardVO) vo).setSeqId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSeqId());

            if ("".equals(((InflRcToCardVO) vo).getSalesRescId())) {
                ((InflRcToCardVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSalesRescId());

            if ("".equals(((InflRcToCardVO) vo).getRescType())) {
                ((InflRcToCardVO) vo).setRescType(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getRescType());

            if ("".equals(((InflRcToCardVO) vo).getSalesNum())) {
                ((InflRcToCardVO) vo).setSalesNum(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSalesNum());

            if ("".equals(((InflRcToCardVO) vo).getNmny())) {
                ((InflRcToCardVO) vo).setNmny(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getNmny());
            stmt.setString(index++, ((InflRcToCardVO) vo).getInfflag());

            if ("".equals(((InflRcToCardVO) vo).getOperId())) {
                ((InflRcToCardVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getOperId());
            stmt.setString(index++, ((InflRcToCardVO) vo).getOperName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getAtime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getCtime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getItime()));
            stmt.setString(index++, ((InflRcToCardVO) vo).getState());
            stmt.setString(index++, ((InflRcToCardVO) vo).getStateMsg());

            int rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE INFL_RC_TO_CARD SET SEQ_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_TYPE = ?,SALES_NUM = ?,NMNY = ?,INFFLAG = ?,OPER_ID = ?,OPER_NAME = ?,ATIME = ?,CTIME = ?,ITIME = ?,STATE = ?,STATE_MSG = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((InflRcToCardVO) vo).getSeqId())) {
                ((InflRcToCardVO) vo).setSeqId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSeqId());

            if ("".equals(((InflRcToCardVO) vo).getSalesRescId())) {
                ((InflRcToCardVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSalesRescId());

            if ("".equals(((InflRcToCardVO) vo).getRescType())) {
                ((InflRcToCardVO) vo).setRescType(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getRescType());

            if ("".equals(((InflRcToCardVO) vo).getSalesNum())) {
                ((InflRcToCardVO) vo).setSalesNum(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getSalesNum());

            if ("".equals(((InflRcToCardVO) vo).getNmny())) {
                ((InflRcToCardVO) vo).setNmny(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getNmny());
            stmt.setString(index++, ((InflRcToCardVO) vo).getInfflag());

            if ("".equals(((InflRcToCardVO) vo).getOperId())) {
                ((InflRcToCardVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((InflRcToCardVO) vo).getOperId());
            stmt.setString(index++, ((InflRcToCardVO) vo).getOperName());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getAtime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getCtime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InflRcToCardVO) vo).getItime()));
            stmt.setString(index++, ((InflRcToCardVO) vo).getState());
            stmt.setString(index++, ((InflRcToCardVO) vo).getStateMsg());

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return bResult;
    }

    public long countByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        long lCount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public long deleteByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new InflRcToCardVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;
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

            List retList = null;

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

    public int insertByBatch(List list) throws DAOSystemException {
        if ((list == null) || (list.size() == 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            Iterator it = list.iterator();
            int counter = 0;

            while (it.hasNext()) {
                HashMap map = (HashMap) it.next();

                if ((map.get("STATE") == null) || map.get("STATE").equals("") ||
                        map.get("STATE").equals("4")) {
                    continue;
                }

                int index = 0;
                stmt.setString(++index,
                    (map.get("SEQ_ID") == null) ? ""
                                                : String.valueOf(map.get(
                            "SEQ_ID")));
                stmt.setString(++index,
                    (map.get("SALES_RESOURCE_ID") == null) ? ""
                                                           : String.valueOf(
                        map.get("SALES_RESOURCE_ID")));
                stmt.setString(++index,
                    (map.get("RESOURCE_TYPE") == null) ? ""
                                                       : String.valueOf(map.get(
                            "RESOURCE_TYPE")));
                stmt.setString(++index,
                    (map.get("SALES_NUM") == null) ? ""
                                                   : String.valueOf(map.get(
                            "SALES_NUM")));
                stmt.setString(++index,
                    (map.get("NMNY") == null) ? ""
                                              : String.valueOf(map.get("NMNY")));
                stmt.setString(++index,
                    (map.get("INFLAG") == null) ? ""
                                                : String.valueOf(map.get(
                            "INFLAG")));
                stmt.setString(++index,
                    (map.get("OPER_ID") == null) ? ""
                                                 : String.valueOf(map.get(
                            "OPER_ID")));
                stmt.setString(++index,
                    (map.get("OPER_NAME") == null) ? ""
                                                   : String.valueOf(map.get(
                            "OPER_NAME")));
                stmt.setString(++index,
                    (map.get("ACCEPT_DATE") == null) ? ""
                                                     : String.valueOf(map.get(
                            "ACCEPT_DATE")));
                stmt.setString(++index,
                    (map.get("TS") == null) ? "" : String.valueOf(map.get("TS")));
                stmt.setString(++index,
                    (map.get("INVOKE_TIME") == null) ? ""
                                                     : String.valueOf(map.get(
                            "INVOKE_TIME")));
                stmt.setString(++index,
                    (map.get("STATE") == null) ? ""
                                               : String.valueOf(map.get("STATE")));
                stmt.setString(++index,
                    (map.get("STATE_MSG") == null) ? ""
                                                   : String.valueOf(map.get(
                            "STATE_MSG")));
                stmt.execute();
                stmt.clearParameters();
                counter++;
            }

            return counter;
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    private String genePrimaryKey() throws DAOSystemException {
        return SeqDAOFactory.getInstance().getSequenceManageDAO()
                            .getNextSequence("SEQ_INFL_RC_TO_CARD");
    }
}
