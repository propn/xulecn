package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.InfRcToCardDAO;
import com.ztesoft.crm.salesres.vo.InfRcToCardVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class InfRcToCardDAOImpl implements InfRcToCardDAO {
    private String SQL_SELECT = "SELECT SEQ_ID,SALES_RESOURCE_ID,RESOURCE_TYPE,SALES_NUM,PRODUCE_ID,PRODUCE_NO,ACCEPT_DATE,STATE,STATE_MSG,TS,INVOKE_TIME,BACK_TIME,INFLAG,NMNY,OPER_ID,OPER_NAME,STORAGEID FROM INF_RC_TO_CARD";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM INF_RC_TO_CARD";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO INF_RC_TO_CARD ( SEQ_ID,SALES_RESOURCE_ID,RESOURCE_TYPE,SALES_NUM,PRODUCE_ID,PRODUCE_NO,ACCEPT_DATE,STATE,STATE_MSG,TS,INVOKE_TIME,BACK_TIME,INFLAG,NMNY,OPER_ID,OPER_NAME,STORAGEID ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE INF_RC_TO_CARD SET  SEQ_ID = ?, SALES_RESOURCE_ID = ?, RESOURCE_TYPE = ?, SALES_NUM = ?, PRODUCE_ID = ?, PRODUCE_NO = ?, ACCEPT_DATE = ?, STATE = ?, STATE_MSG = ?, TS = ?, INVOKE_TIME = ?, BACK_TIME = ?, INFLAG = ?, NMNY = ?, OPER_ID = ?, OPER_NAME = ?, STORAGEID = ? WHERE";
    private String SQL_DELETE = "DELETE FROM INF_RC_TO_CARD WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM INF_RC_TO_CARD ";

    public InfRcToCardDAOImpl() {
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
            InfRcToCardVO vo = new InfRcToCardVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(InfRcToCardVO vo, ResultSet rs)
        throws SQLException {
        vo.setSeqId(DAOUtils.trimStr(rs.getString("SEQ_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescType(DAOUtils.trimStr(rs.getString("RESOURCE_TYPE")));
        vo.setSalesNum(DAOUtils.trimStr(rs.getString("SALES_NUM")));
        vo.setProduceId(DAOUtils.trimStr(rs.getString("PRODUCE_ID")));
        vo.setProduceNo(DAOUtils.trimStr(rs.getString("PRODUCE_NO")));
        vo.setAcceptDate(DAOUtils.trimStr(rs.getString("ACCEPT_DATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setStateMsg(DAOUtils.trimStr(rs.getString("STATE_MSG")));
        vo.setTs(DAOUtils.trimStr(rs.getString("TS")));
        vo.setInvokeTime(DAOUtils.getFormatedDateTime(rs.getDate("INVOKE_TIME")));
        vo.setBackTime(DAOUtils.getFormatedDateTime(rs.getDate("BACK_TIME")));
        vo.setInflag(DAOUtils.trimStr(rs.getString("INFLAG")));
        vo.setNmny(DAOUtils.trimStr(rs.getString("NMNY")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("OPER_ID")));
        vo.setOperName(DAOUtils.trimStr(rs.getString("OPER_NAME")));
        vo.setStorageid(DAOUtils.trimStr(rs.getString("STORAGEID")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        InfRcToCardVO vo = new InfRcToCardVO();

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

            if ("".equals(((InfRcToCardVO) vo).getSeqId())) {
                ((InfRcToCardVO) vo).setSeqId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSeqId());

            if ("".equals(((InfRcToCardVO) vo).getSalesRescId())) {
                ((InfRcToCardVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSalesRescId());

            if ("".equals(((InfRcToCardVO) vo).getRescType())) {
                ((InfRcToCardVO) vo).setRescType(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getRescType());

            if ("".equals(((InfRcToCardVO) vo).getSalesNum())) {
                ((InfRcToCardVO) vo).setSalesNum(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSalesNum());

            if ("".equals(((InfRcToCardVO) vo).getProduceId())) {
                ((InfRcToCardVO) vo).setProduceId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getProduceId());

            if ("".equals(((InfRcToCardVO) vo).getProduceNo())) {
                ((InfRcToCardVO) vo).setProduceNo(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getProduceNo());
            stmt.setString(index++, ((InfRcToCardVO) vo).getAcceptDate());
            stmt.setString(index++, ((InfRcToCardVO) vo).getState());
            stmt.setString(index++, ((InfRcToCardVO) vo).getStateMsg());
            stmt.setString(index++, ((InfRcToCardVO) vo).getTs());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InfRcToCardVO) vo).getInvokeTime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InfRcToCardVO) vo).getBackTime()));
            stmt.setString(index++, ((InfRcToCardVO) vo).getInflag());

            if ("".equals(((InfRcToCardVO) vo).getNmny())) {
                ((InfRcToCardVO) vo).setNmny(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getNmny());

            if ("".equals(((InfRcToCardVO) vo).getOperId())) {
                ((InfRcToCardVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getOperId());
            stmt.setString(index++, ((InfRcToCardVO) vo).getOperName());
            stmt.setString(index++, ((InfRcToCardVO) vo).getStorageid());

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
                "UPDATE INF_RC_TO_CARD SET SEQ_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_TYPE = ?,SALES_NUM = ?,PRODUCE_ID = ?,PRODUCE_NO = ?,ACCEPT_DATE = ?,STATE = ?,STATE_MSG = ?,TS = ?,INVOKE_TIME = ?,BACK_TIME = ?,INFLAG = ?,NMNY = ?,OPER_ID = ?,OPER_NAME = ?,STORAGEID = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((InfRcToCardVO) vo).getSeqId())) {
                ((InfRcToCardVO) vo).setSeqId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSeqId());

            if ("".equals(((InfRcToCardVO) vo).getSalesRescId())) {
                ((InfRcToCardVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSalesRescId());

            if ("".equals(((InfRcToCardVO) vo).getRescType())) {
                ((InfRcToCardVO) vo).setRescType(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getRescType());

            if ("".equals(((InfRcToCardVO) vo).getSalesNum())) {
                ((InfRcToCardVO) vo).setSalesNum(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getSalesNum());

            if ("".equals(((InfRcToCardVO) vo).getProduceId())) {
                ((InfRcToCardVO) vo).setProduceId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getProduceId());

            if ("".equals(((InfRcToCardVO) vo).getProduceNo())) {
                ((InfRcToCardVO) vo).setProduceNo(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getProduceNo());
            stmt.setString(index++, ((InfRcToCardVO) vo).getAcceptDate());
            stmt.setString(index++, ((InfRcToCardVO) vo).getState());
            stmt.setString(index++, ((InfRcToCardVO) vo).getStateMsg());
            stmt.setString(index++, ((InfRcToCardVO) vo).getTs());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InfRcToCardVO) vo).getInvokeTime()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((InfRcToCardVO) vo).getBackTime()));
            stmt.setString(index++, ((InfRcToCardVO) vo).getInflag());

            if ("".equals(((InfRcToCardVO) vo).getNmny())) {
                ((InfRcToCardVO) vo).setNmny(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getNmny());

            if ("".equals(((InfRcToCardVO) vo).getOperId())) {
                ((InfRcToCardVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((InfRcToCardVO) vo).getOperId());
            stmt.setString(index++, ((InfRcToCardVO) vo).getOperName());
            stmt.setString(index++, ((InfRcToCardVO) vo).getStorageid());

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
        return new InfRcToCardVO();
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

    public int updateStateByBatch(List list, String[] params)
        throws DAOSystemException {
        if ((list == null) || (list.size() == 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL = " UPDATE INF_RC_TO_CARD SET STATE=? WHERE LOG_ID=?";
        int counter = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(SQL);

            Iterator iter = list.iterator();

            while (iter.hasNext()) {
                HashMap map = (HashMap) iter.next();

                if (map != null) {
                    Set entityset = map.keySet();
                    int index = 0;

                    if (entityset.contains(params[index++])) {
                        stmt.setString(index,
                            String.valueOf(map.get(params[0])).equals("0")
                            ? "2" : "4");
                    }

                    if (entityset.contains(params[index++])) {
                        stmt.setString(index, String.valueOf(map.get(params[1])));
                    }

                    //stmt.setObject(Integer.parseInt(String.valueOf(entityset.toArray()[0])), map.get(entityset.toArray()[0]));
                    counter += stmt.executeUpdate();
                    stmt.clearParameters();
                }
            }

            return counter;
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
