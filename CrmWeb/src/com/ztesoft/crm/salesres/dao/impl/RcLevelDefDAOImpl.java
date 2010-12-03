package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcLevelDefDAO;
import com.ztesoft.crm.salesres.vo.RcLevelDefVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcLevelDefDAOImpl implements RcLevelDefDAO {
    private String SQL_SELECT = "SELECT FAMILY_ID,RC_LEVEL_ID,RC_LEVEL_NAME,LEVEL_COMMENTS,PRI_ID,RULE_STRING,pre_fee,limit_lfee,lan_id,is_lucky FROM RC_LEVEL_DEF";
    private String SQL_SELECT_EXT = "SELECT D.FAMILY_ID,D.RC_LEVEL_ID,D.RC_LEVEL_NAME,D.LEVEL_COMMENTS,D.PRI_ID,D.RULE_STRING,D.PRE_FEE,D.LIMIT_LFEE,D.LAN_ID,D.is_lucky from RC_LEVEL_DEF D,RC_NO E,SALES_RESOURCE R  " +
        "WHERE 1=1 AND e.resource_level=d.rc_level_id  and d.family_id=r.family_id  and r.sales_resource_id=e.sales_resource_id ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_LEVEL_DEF";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_LEVEL_DEF ( LAN_ID,FAMILY_ID,RC_LEVEL_ID,RC_LEVEL_NAME,LEVEL_COMMENTS,PRI_ID,RULE_STRING,PRE_FEE,LIMIT_LFEE,IS_LUCKY ) VALUES ( ?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_LEVEL_DEF SET  RC_LEVEL_NAME = ?, LEVEL_COMMENTS = ?, PRI_ID = ?, RULE_STRING = ?,PRE_FEE=?,LIMIT_LFEE = ? WHERE FAMILY_ID = ? AND RC_LEVEL_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_LEVEL_DEF WHERE FAMILY_ID = ? AND RC_LEVEL_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_LEVEL_DEF  d ";
    private String SQL_NEXT = "SELECT RC_LEVLE_DEF_SEQ.NEXTVAL AS NEXT FROM DUAL";

    public RcLevelDefDAOImpl() {
    }

    public RcLevelDefVO findByPrimaryKey(String pFAMILY_ID, String pRC_LEVEL_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE FAMILY_ID = ? AND RC_LEVEL_ID = ? ",
                new String[] { pFAMILY_ID, pRC_LEVEL_ID });

        if (arrayList.size() > 0) {
            return (RcLevelDefVO) arrayList.get(0);
        } else {
            return (RcLevelDefVO) getEmptyVO();
        }
    }

    /**
     *
     * @param rcLevelId
     * @param lanId
     * @return
     * @throws DAOSystemException
     */
    public RcLevelDefVO findByLogicPK(String rcLevelId, String lanId)
        throws DAOSystemException {
        if ((rcLevelId == null) || (rcLevelId.trim().length() < 1) ||
                (lanId == null) || (lanId.trim().length() < 1)) {
            return null;
        }

        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE RC_LEVEL_ID = ? AND LAN_ID = ? ",
                new String[] { rcLevelId, lanId });

        if (arrayList.size() > 0) {
            return (RcLevelDefVO) arrayList.get(0);
        } else {
            return null;
        }
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
            RcLevelDefVO vo = new RcLevelDefVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcLevelDefVO vo, ResultSet rs)
        throws SQLException {
        vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("FAMILY_ID")));
        vo.setRcLevelId(DAOUtils.trimStr(rs.getString("RC_LEVEL_ID")));
        vo.setRcLevelName(DAOUtils.trimStr(rs.getString("RC_LEVEL_NAME")));
        vo.setLevelComments(DAOUtils.trimStr(rs.getString("LEVEL_COMMENTS")));
        vo.setPriId(DAOUtils.trimStr(rs.getString("PRI_ID")));
        vo.setRuleString(DAOUtils.trimStr(rs.getString("RULE_STRING")));
        vo.setPreFee(DAOUtils.trimStr(rs.getString("pre_fee")));
        vo.setLimitFee(DAOUtils.trimStr(rs.getString("limit_lfee")));
        vo.setIsLucky(DAOUtils.trimStr(rs.getString("is_lucky")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcLevelDefVO vo = new RcLevelDefVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord出错", this);
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
            SQL = SQL_SELECT_EXT + whereCond;

            if (SQL.toLowerCase().indexOf("order by") == -1) {
                SQL += " order by D.PRI_ID ";
            }

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

    /**
     * 根据号码查询需要计算等级用的规则list，用了distinct防止重复
     * @param familyId
     * @return
     * @throws DAOSystemException
     */
    public List findByFimilyForCal(String familyId) throws DAOSystemException {
        if ((familyId == null) || (familyId.trim().length() < 1)) {
            return new ArrayList();
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "SELECT distinct FAMILY_ID,RC_LEVEL_ID,null as RC_LEVEL_NAME,null as LEVEL_COMMENTS,PRI_ID,RULE_STRING,null as pre_fee,null as limit_lfee,null as lan_id,null as is_lucky " +
            " FROM RC_LEVEL_DEF where FAMILY_ID=? order by PRI_ID ";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            stmt.setString(1, familyId);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException(
                "class:RcLevelDefDAOImpl||method:findByFimilyForCal: while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public List findByFimilyAndLanId(String familyId, String lanId)
        throws DAOSystemException {
        if ((familyId == null) || (familyId.trim().length() < 1)) {
            return new ArrayList();
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "SELECT * " +
            " FROM RC_LEVEL_DEF where FAMILY_ID=? and lan_id=? order by PRI_ID ";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setMaxRows(maxRows);
            stmt.setString(1, familyId);
            stmt.setString(2, lanId);
            rs = stmt.executeQuery();

            return fetchMultiResults(rs);
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException(
                "class:RcLevelDefDAOImpl||method:findByFimilyForCal: while getting sql:\n" +
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
            stmt.setString(index++, ((RcLevelDefVO) vo).getLanId());

            if ("".equals(((RcLevelDefVO) vo).getFamilyId())) {
                ((RcLevelDefVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getFamilyId());

            if ("".equals(((RcLevelDefVO) vo).getRcLevelId())) {
                ((RcLevelDefVO) vo).setRcLevelId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getRcLevelId());
            stmt.setString(index++, ((RcLevelDefVO) vo).getRcLevelName());
            stmt.setString(index++, ((RcLevelDefVO) vo).getLevelComments());

            if ("".equals(((RcLevelDefVO) vo).getPriId())) {
                ((RcLevelDefVO) vo).setPriId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getPriId());
            stmt.setString(index++, ((RcLevelDefVO) vo).getRuleString());
            stmt.setString(index++, ((RcLevelDefVO) vo).getPreFee());
            stmt.setString(index++, ((RcLevelDefVO) vo).getLimitFee());
            stmt.setString(index++, ((RcLevelDefVO) vo).getIsLucky());

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

    public boolean update(String pFAMILY_ID, String pRC_LEVEL_ID, String lanId,
        RcLevelDefVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_LEVEL_DEF SET FAMILY_ID = ?,RC_LEVEL_ID = ?,RC_LEVEL_NAME = ?,LEVEL_COMMENTS = ?,PRI_ID = ?,RULE_STRING = ?,is_lucky=?");

            if (!"".equals(vo.getPreFee())) {
                sql.append(" ,pre_fee =? ");
            }

            if (!"".equals(vo.getLimitFee())) {
                sql.append(" ,limit_lfee =? ");
            }

            sql.append(
                " WHERE  FAMILY_ID = ? AND RC_LEVEL_ID = ? and LAN_ID =?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcLevelDefVO) vo).getFamilyId())) {
                ((RcLevelDefVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, vo.getFamilyId());

            if ("".equals(((RcLevelDefVO) vo).getRcLevelId())) {
                ((RcLevelDefVO) vo).setRcLevelId(null);
            }

            stmt.setString(index++, vo.getRcLevelId());
            stmt.setString(index++, vo.getRcLevelName());
            stmt.setString(index++, vo.getLevelComments());

            if ("".equals(((RcLevelDefVO) vo).getPriId())) {
                ((RcLevelDefVO) vo).setPriId(null);
            }

            stmt.setString(index++, vo.getPriId());
            stmt.setString(index++, vo.getRuleString());
            stmt.setString(index++, vo.getIsLucky());

            if (!"".equals(vo.getPreFee())) {
                stmt.setString(index++, vo.getPreFee());
            }

            if (!"".equals(vo.getLimitFee())) {
                stmt.setString(index++, vo.getLimitFee());
            }

            stmt.setString(index++, pFAMILY_ID);
            stmt.setString(index++, pRC_LEVEL_ID);
            stmt.setString(index++, lanId);

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

    /**
     * 根据levelId修改字段,修改的字段包括：RC_LEVEL_NAME，LEVEL_COMMENTS，PRI_ID，RULE_STRING，is_lucky
     * @param pRC_LEVEL_ID
     * @param vo
     * @return
     * @throws DAOSystemException
     */
    public boolean updateByLevelId(String pRC_LEVEL_ID, RcLevelDefVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_LEVEL_DEF SET RC_LEVEL_NAME = ?,LEVEL_COMMENTS = ?,PRI_ID = ?,RULE_STRING = ?,is_lucky=?");
            sql.append(" WHERE  RC_LEVEL_ID = ?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index++, vo.getRcLevelName());
            stmt.setString(index++, vo.getLevelComments());

            if ("".equals(((RcLevelDefVO) vo).getPriId())) {
                ((RcLevelDefVO) vo).setPriId(null);
            }

            stmt.setString(index++, vo.getPriId());
            stmt.setString(index++, vo.getRuleString());
            stmt.setString(index++, vo.getIsLucky());

            stmt.setString(index++, pRC_LEVEL_ID);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                bResult = true;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException(
                "SQLException while updateByLevelId sql:\n" + sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return bResult;
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
                "UPDATE RC_LEVEL_DEF SET FAMILY_ID = ?,RC_LEVEL_ID = ?,RC_LEVEL_NAME = ?,LEVEL_COMMENTS = ?,PRI_ID = ?,RULE_STRING = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcLevelDefVO) vo).getFamilyId())) {
                ((RcLevelDefVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getFamilyId());

            if ("".equals(((RcLevelDefVO) vo).getRcLevelId())) {
                ((RcLevelDefVO) vo).setRcLevelId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getRcLevelId());
            stmt.setString(index++, ((RcLevelDefVO) vo).getRcLevelName());
            stmt.setString(index++, ((RcLevelDefVO) vo).getLevelComments());

            if ("".equals(((RcLevelDefVO) vo).getPriId())) {
                ((RcLevelDefVO) vo).setPriId(null);
            }

            stmt.setString(index++, ((RcLevelDefVO) vo).getPriId());
            stmt.setString(index++, ((RcLevelDefVO) vo).getRuleString());

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
            System.out.println(SQL);
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

    public long delete(String pFAMILY_ID, String pRC_LEVEL_ID)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pFAMILY_ID);
            stmt.setString(index++, pRC_LEVEL_ID);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL_DELETE, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL_DELETE, se);
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
        return new RcLevelDefVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;

        if (SQL.toLowerCase().indexOf("order by") == -1) {
            SQL += " order by PRI_ID ";
        }

        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
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

    /**
     * 检查某个资源等级有没有被引用
     * @param familyId
     * @param levelId
     * @return
     * @throws DAOSystemException
     */
    public boolean isLevelRef(String familyId, String levelId)
        throws DAOSystemException {
        if ((familyId == null) || (familyId.trim().length() < 1) ||
                (levelId == null) || (levelId.trim().length() < 1)) {
            throw new DAOSystemException(
                "----class:RcLevelDefDAOImpl:lackOfParamter");
        }

        Connection conn = null;
        long lCount = 0;
        boolean ref = false;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        String SQL = "";
        String tablename = "rc_entity";
        String sql_table = "select ENTITY_TAB_NAME from RC_FAMILY_ENTITY_RELA where FAMILY_ID=?";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql_table));
            stmt.setMaxRows(maxRows);
            stmt.setString(1, familyId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tablename = rs.getString("ENTITY_TAB_NAME");
            }

            tablename = DAOSQLUtils.getFilterSQL(tablename);
            SQL = "select count(*) as COL_COUNTS from dual where exists(" +
                "select 1 from " + tablename + " where resource_level=?)";
            Debug.print("----class:RcLevelDefDAOImpl:SQL:" + SQL, this);
            stmt2 = conn.prepareStatement(SQL);
            stmt2.setString(1, levelId);
            rs2 = stmt2.executeQuery();

            while (rs2.next()) {
                lCount = rs2.getLong("COL_COUNTS");
            }

            if (lCount > 0) {
                ref = true;
            }
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException(
                "----class:RcLevelDefDAOImpl:method isLevelRef:\n" + SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeResultSet(rs2, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn, this);
        }

        return ref;
    }

    public long getNextId() throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_NEXT));
            rs = stmt.executeQuery();
            rs.next();

            return rs.getLong("NEXT");
        } catch (SQLException se) {
            Debug.print(SQL_NEXT, this);
            throw new DAOSystemException("SQLException while  sql:\n" +
                SQL_NEXT, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }
}
