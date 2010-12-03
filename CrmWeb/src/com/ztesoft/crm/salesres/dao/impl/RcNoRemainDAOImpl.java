package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcNoRemainDAO;
import com.ztesoft.crm.salesres.dao.SrNSDAOFactory;
import com.ztesoft.crm.salesres.vo.RcNoRemainInfoVO;
import com.ztesoft.crm.salesres.vo.RcNoRemainVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RcNoRemainDAOImpl implements RcNoRemainDAO {
    private String SQL_SELECT = "SELECT resource_instance_id,resource_instance_code,remain_id FROM RC_NO_REMAIN";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO_REMAIN";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NO_REMAIN ( resource_instance_id,resource_instance_code,remain_id ) VALUES ( ?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_NO_REMAIN SET  resource_instance_code = ? WHERE remain_id = ? AND resource_instance_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_NO_REMAIN WHERE remain_id = ? AND resource_instance_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NO_REMAIN ";

    public RcNoRemainDAOImpl() {
    }

    public RcNoRemainVO findByPrimaryKey(String premain_id,
        String presource_instance_id) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE remain_id = ? AND resource_instance_id = ? ",
                new String[] { premain_id, presource_instance_id });

        if (arrayList.size() > 0) {
            return (RcNoRemainVO) arrayList.get(0);
        } else {
            return (RcNoRemainVO) getEmptyVO();
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
            RcNoRemainVO vo = new RcNoRemainVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNoRemainVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "resource_instance_id")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "resource_instance_code")));
        vo.setRemainId(DAOUtils.trimStr(rs.getString("remain_id")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNoRemainVO vo = new RcNoRemainVO();

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

            if ("".equals(((RcNoRemainVO) vo).getRescInstanceId())) {
                ((RcNoRemainVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNoRemainVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNoRemainVO) vo).getRescInstanceCode());

            if ("".equals(((RcNoRemainVO) vo).getRemainId())) {
                ((RcNoRemainVO) vo).setRemainId(null);
            }

            stmt.setString(index++, ((RcNoRemainVO) vo).getRemainId());

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

    public boolean update(String premain_id, String presource_instance_id,
        RcNoRemainVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NO_REMAIN SET resource_instance_id = ?,resource_instance_code = ?,remain_id = ?");
            sql.append(" WHERE  remain_id = ? AND resource_instance_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRemainVO) vo).getRescInstanceId())) {
                ((RcNoRemainVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcNoRemainVO) vo).getRemainId())) {
                ((RcNoRemainVO) vo).setRemainId(null);
            }

            stmt.setString(index++, vo.getRemainId());
            stmt.setString(index++, premain_id);
            stmt.setString(index++, presource_instance_id);

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

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NO_REMAIN SET resource_instance_id = ?,resource_instance_code = ?,remain_id = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoRemainVO) vo).getRescInstanceId())) {
                ((RcNoRemainVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNoRemainVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNoRemainVO) vo).getRescInstanceCode());

            if ("".equals(((RcNoRemainVO) vo).getRemainId())) {
                ((RcNoRemainVO) vo).setRemainId(null);
            }

            stmt.setString(index++, ((RcNoRemainVO) vo).getRemainId());

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

    public long delete(String premain_id, String presource_instance_id)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, premain_id);
            stmt.setString(index++, presource_instance_id);
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
        return new RcNoRemainVO();
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

    public Map handleNoRemain(RcNoRemainInfoVO vo, String[] nos, String type) {
        String failList = "";
        Map map = new HashMap();

        if (vo == null) {
            return null;
        }

        String storageId = vo.getStorageId();
        String remainNum = vo.getRemainNum();
        String startNo = vo.getStartNo();
        String endNo = vo.getEndNo();
        String remainFlag = vo.getRemainFlag();
        String endRemainTime = vo.getEndRemainTime();
        String rescState = vo.getRescState();

        //	   	if (remainFlag.equals("0")){
        if ((vo.getOperId() == null) || vo.getOperId().equals("") ||
                (vo.getDepartId() == null) || vo.getDepartId().equals("") ||
                (storageId == null) || storageId.equals("") ||
                (remainFlag == null) || remainFlag.equals("") ||
                (endRemainTime == null) || endRemainTime.equals("") ||
                (rescState == null) || "".equals(rescState)) {
            return null;
        }

        String[] str = vo.getOperId().split("@");

        if ((str == null) || (str.length != 2)) {
            return null;
        }

        vo.setOperId(str[0]);

        String provId = str[1];
        vo.setRemainTime(DateFormatUtils.getFormatedDateTime());

        //插入rc_no_remain表，插入成功，即执行更新以及插入rc_no_remain_info
        String remainId = "";
        //SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence("seq_rc_no_remain_inf");
        vo.setRemainId(remainId);

        String insNoRemain1 = "insert into RC_NO_REMAIN ( resource_instance_id,resource_instance_code,remain_id )" +
            " SELECT a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,";

        //+ remainId;
        String insNoRemain = "";
        insNoRemain += " FROM RC_NO a,rc_no_seg b,SALES_RESOURCE c,RC_STORAGE d where a.no_seg_id=b.no_seg_id and b.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and a.STORAGE_ID=d.STORAGE_ID ";
        insNoRemain += (" and a.storage_id in (" + storageId +
        ") and d.storage_state='0' and a.state='" +
        ParamsConsConfig.RcEntityStateValide + "' and a.resource_state='" +
        ParamsConsConfig.RcEntityFreeState + "'");

        if ("20".equals(provId)) { //广西的加上工号权限判断
            insNoRemain += (" and exists (select 1 from mp_storage b where a.storage_id=b.storage_id and " +
            " b.oper_id=" + vo.getOperId() + " union all " +
            "  select 1 from STORAGE_DEPART_RELA c where c.storage_Id=a.storage_id " +
            "  and c.depart_id=" + vo.getDepartId() + ") ");
        }

        insNoRemain += " and a.resource_instance_code = ";

        Connection conn1 = null;
        Connection conn2 = null;

        // 此处用Statement，是因为oracle如果用preparedstatement批量执行后返回的数组没有成功条数信息，因此无法知道哪些号码不合要求。
        // 而且考虑数据量应该不会太大，所以用statement
        Statement stmt1 = null;
        PreparedStatement stmt2 = null;

        int[] insertArr = null;
        String upSql = "update RC_NO set resource_state = ? where resource_instance_code = ?";
        String[] reids = new String[nos.length];

        try {
            conn1 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt1 = conn1.createStatement();

            if (type.equals("1")) { //文件上传方式

                for (int i = 0; i < nos.length; i++) {
                    remainId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                            .getNextSequence("seq_rc_no_remain_inf");
                    reids[i] = remainId;
                    stmt1.addBatch(insNoRemain1.toString() + remainId +
                        insNoRemain.toString() + "'" + nos[i] + "'");
                }
            } else {
                remainId = SeqDAOFactory.getInstance().getSequenceManageDAO()
                                        .getNextSequence("seq_rc_no_remain_inf");
                vo.setRemainId(remainId);

                //reids[i]=remainId;
                for (int i = 0; i < nos.length; i++) {
                    stmt1.addBatch(insNoRemain1.toString() + remainId +
                        insNoRemain.toString() + "'" + nos[i] + "'");
                }
            }

            insertArr = stmt1.executeBatch();
            // 先执行查询并插入修改日志的表，得到成功的号码，再更新这些号码的状态
            conn2 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt2 = conn2.prepareStatement(upSql);

            List toInslist = new ArrayList();
            List reidlist = new ArrayList();

            for (int j = 0; j < nos.length; j++) {
                if (insertArr[j] > 0) {
                    toInslist.add(nos[j]); //待插入rc_no_remain_info
                    reidlist.add(reids[j]);
                    stmt2.setString(1, "Q");
                    stmt2.setString(2, nos[j]);
                    stmt2.addBatch();
                } else {
                    failList += (nos[j] + ",");
                }
            }

            stmt2.executeBatch();

            if (type.equals("1")) {
                vo.setRemainNum("1");
            } else {
                vo.setRemainNum(String.valueOf(toInslist.size()));
            }

            SrNSDAOFactory.getInstance().getRcNoRemainInfoDAO()
                          .batchInsert(vo, toInslist, reidlist, type);
        } catch (SQLException se) {
            Debug.print("插入封存表报错：");
            Debug.print(insNoRemain + "?", this);
            Debug.print("更新表报错：");
            Debug.print(upSql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                upSql, se);
        } finally {
            DAOUtils.closeStatement(stmt1);
            DAOUtils.closeConnection(conn1, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn2, this);
        }

        map.put("result", "1");

        if ((failList != null) && (failList.length() > 0)) {
            failList = failList.substring(0, failList.length() - 1);
        }

        map.put("failList", failList);

        return map;
    }
}
