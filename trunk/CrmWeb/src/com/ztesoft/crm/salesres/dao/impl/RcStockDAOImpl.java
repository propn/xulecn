package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcStockDAO;
import com.ztesoft.crm.salesres.vo.RcStockVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcStockDAOImpl implements RcStockDAO {
    /** �в������ƵĲ�ѯ **/
    private String SQL_SELECT = "SELECT distinct a.storage_id,c.storage_name,a.sales_resource_id,d.sales_resource_name,a.stock_amount FROM RC_STOCK a,STORAGE_DEPART_RELA b,RC_STORAGE c,SALES_RESOURCE d" +
        " where a.storage_id=b.storage_id and a.storage_id=c.storage_id and a.sales_resource_id=d.sales_resource_id ";

    /** ԭʼ�Ĳ�ѯ�����Բ��������ʵ����� **/
    private String SQL_SELECT_2 = "SELECT a.storage_id,c.storage_name,a.sales_resource_id,d.sales_resource_name,a.stock_amount,'' as UP_LIMIT,'' as DOWN_LIMIT,'' as DEPART_ID FROM RC_STOCK a,RC_STORAGE c,SALES_RESOURCE d" +
        " where a.storage_id=c.storage_id and a.sales_resource_id=d.sales_resource_id ";
    private String SQL_SELECT_2_COUNT = "SELECT count(a.sales_resource_id) as COL_COUNTS FROM RC_STOCK a,RC_STORAGE c,SALES_RESOURCE d" +
        " where a.storage_id=c.storage_id and a.sales_resource_id=d.sales_resource_id ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_STOCK a,STORAGE_DEPART_RELA b,RC_STORAGE c,SALES_RESOURCE d" +
        " where a.storage_id=b.storage_id and a.storage_id=c.storage_id and a.sales_resource_id=d.sales_resource_id ";
    private int maxRows;

    /** ���ӷ���1��Ϊͳ�������޷��� **/
    private int flag = 0;

    //��ʶ�Ƿ��ǹ���������
    private int flag2 = 0;
    private String SQL_INSERT = "INSERT INTO RC_STOCK ( storage_id,sales_resource_id,stock_amount ) VALUES ( ?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_STOCK SET  stock_amount = ? WHERE sales_resource_id = ? AND storage_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_STOCK WHERE sales_resource_id = ? AND storage_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_STOCK ";
    private boolean filtered = false; //�����жϸ�sql����Ƿ��Ѿ�������
    private String updown = "up"; //���������Ƿ��ѯ�¼��ֿ���Ϣ�ı�ǣ�Ĭ������Ҫ��ѯ

    public RcStockDAOImpl() {
    }

    public RcStockVO findByPrimaryKey(String psales_resource_id,
        String pstorage_id) throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT_2 +
                " and a.sales_resource_id = ? AND a.storage_id = ? ",
                new String[] { psales_resource_id, pstorage_id });

        if (arrayList.size() > 0) {
            return (RcStockVO) arrayList.get(0);
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
            RcStockVO vo = new RcStockVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcStockVO vo, ResultSet rs)
        throws SQLException {
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
        vo.setStockAmount(DAOUtils.trimStr(rs.getString("stock_amount")));
        vo.setUpLimit(DAOUtils.trimStr(rs.getString("UP_LIMIT")));
        vo.setDownLimit(DAOUtils.trimStr(rs.getString("DOWN_LIMIT")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("DEPART_ID")));

        if (flag == 1) {
            //                  vo.setUpLimit( DAOUtils.trimStr( rs.getString( "UP_LIMIT" ) ) );
            //                  vo.setDownLimit( DAOUtils.trimStr( rs.getString( "DOWN_LIMIT" ) ) );
            //                  
            //                  if(updown.equals("up")){
            //                	  vo.setDepartId( DAOUtils.trimStr( rs.getString( "DEPART_ID" ) ) );
            //                	  getDownStorageInfo(vo);
            //                  }
        } else if (flag == 2) {
            vo.setRescState(DAOUtils.trimStr(rs.getString("resource_state")));

            //                  vo.setUpLimit( DAOUtils.trimStr( rs.getString( "UP_LIMIT" ) ) );
            //                  vo.setDownLimit( DAOUtils.trimStr( rs.getString( "DOWN_LIMIT" ) ) );
            //                  if(updown.equals("up")){
            //                	  vo.setDepartId( DAOUtils.trimStr( rs.getString( "DEPART_ID" ) ) );
            //                	  getDownStorageInfo(vo);
            //                  }
        }

        if (flag2 == 1) {
            vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
            vo.setResourceLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcStockVO vo = new RcStockVO();

        try {
            populateDto(vo, rs);
        } catch (SQLException se) {
            Debug.print("populateCurrRecord����", this);
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
            SQL = SQL_SELECT + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            System.out.println(DAOSQLUtils.getFilterSQL(SQL));
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

            if ("".equals(((RcStockVO) vo).getStorageId())) {
                ((RcStockVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getStorageId());

            if ("".equals(((RcStockVO) vo).getSalesRescId())) {
                ((RcStockVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getSalesRescId());

            if ("".equals(((RcStockVO) vo).getStockAmount())) {
                ((RcStockVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getStockAmount());

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

    public boolean update(String psales_resource_id, String pstorage_id,
        RcStockVO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_STOCK SET storage_id = ?,sales_resource_id = ?,stock_amount = ?");
            sql.append(" WHERE  sales_resource_id = ? AND storage_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcStockVO) vo).getStorageId())) {
                ((RcStockVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());

            if ("".equals(((RcStockVO) vo).getSalesRescId())) {
                ((RcStockVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RcStockVO) vo).getStockAmount())) {
                ((RcStockVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, vo.getStockAmount());
            stmt.setString(index++, psales_resource_id);
            stmt.setString(index++, pstorage_id);

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
                "UPDATE RC_STOCK SET storage_id = ?,sales_resource_id = ?,stock_amount = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcStockVO) vo).getStorageId())) {
                ((RcStockVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getStorageId());

            if ("".equals(((RcStockVO) vo).getSalesRescId())) {
                ((RcStockVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getSalesRescId());

            if ("".equals(((RcStockVO) vo).getStockAmount())) {
                ((RcStockVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, ((RcStockVO) vo).getStockAmount());

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
        String SQL = SQL_SELECT_COUNT;

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if (flag == 5) {
                SQL = SQL_SELECT_2_COUNT;
            }

            SQL += whereCond;

            //			�ж�sql�Ƿ��Ѿ�������
            if (!filtered) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            } else {
                stmt = conn.prepareStatement((SQL));
            }

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

    public long delete(String psales_resource_id, String pstorage_id)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, psales_resource_id);
            stmt.setString(index++, pstorage_id);
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

    public int getFlag() {
        return flag;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public void setSQL_SELECT(String SQL_SELECT) {
        this.SQL_SELECT = SQL_SELECT;
    }

    public void setSQL_SELECT_COUNT(String SQL_SELECT_COUNT) {
        this.SQL_SELECT_COUNT = SQL_SELECT_COUNT;
    }

    public VO getEmptyVO() {
        return new RcStockVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        String SQL = SQL_SELECT;

        if (flag == 5) {
            SQL = SQL_SELECT_2;
        }

        SQL = SQL + whereCond;

        String filterSQL = "";

        //�ж���sql���Ѿ�������
        if (queryFilter != null) {
            if (!filtered) {
                filterSQL = queryFilter.doPreFilter(SQL);
            } else {
                filterSQL = queryFilter.doPreFilterWithoutFilterSQL(SQL);
            }
        }

        System.out.println(SQL + "\n>>>" + filterSQL);

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;

            //�ж�sql�Ƿ��Ѿ�������
            if (!filtered) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            } else {
                stmt = conn.prepareStatement((SQL),
                        ResultSet.TYPE_SCROLL_SENSITIVE,
                        ResultSet.CONCUR_UPDATABLE);
            }

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
     * ���ݲֿ�id�޸ĸòֿ�Ŀ������
     * @param storageId
     * @param stockAmount
     * @return
     * @throws DAOSystemException
     */
    public boolean updateAmount(String storageId, String salesRescId,
        String stockAmount) throws DAOSystemException {
        if ((storageId == null) || (storageId.trim().length() < 1) ||
                (stockAmount == null) || (stockAmount.trim().length() < 1)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_STOCK SET stock_amount = ? WHERE storage_id=? and sales_resource_id=?");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));
            stmt.setString(1, stockAmount);
            stmt.setString(2, storageId);
            stmt.setString(3, salesRescId);

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

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

    private void getDownStorageInfo(RcStockVO vo) throws DAOSystemException {
        if ((vo == null) || (vo.getStorageId() == null) ||
                vo.getStorageId().equals("") || (vo.getDepartId() == null) ||
                vo.getDepartId().equals("")) {
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = " select storage_id ,storage_name from rc_storage where storage_state = '0' and up_storage_id=" +
            vo.getStorageId() +
            " and exists(select 1 from storage_depart_rela a where a.storage_id = storage_id and a.depart_id in(" +
            vo.getDepartId() + "))";
        String storageIds = "";
        String storageNames = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            rs = stmt.executeQuery();

            while (rs.next()) {
                storageIds += (rs.getString(1) + ",");
                storageNames += (rs.getString(2) + ",");
            }

            vo.setDownStorageId(storageIds);
            vo.setDownStorageName(storageNames);
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public void setUpDown(String string) {
        this.updown = string;
    }

    public boolean getRealCfg(String lanId, String tableName) {
        if ("".equals(lanId) || "".equals(tableName)) {
            return false;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql = " select rp_switch from rc_rp_real_cfg where lan_id = ? and rp_table =?";
        String tmp = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, lanId);
            stmt.setString(2, tableName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                tmp = rs.getString(1);
            }

            if ("0".equals(tmp)) { //���عر�,����δ���ÿ���

                return true;
            } else {
                return false;
            }
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * ����������ѯ����¼����������һ���ֶε�ͳ������
     * sql�б�����COL_COUNTS �� totalAmount�����ֶ�
     * @param whereCond
     * @return
     * @throws DAOSystemException
     */
    public long[] countSumByCond(String whereCond) throws DAOSystemException {
        long[] rtnArr = new long[2];
        Connection conn = null;
        long lCount = 0;
        long totalAmount = 0;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT_COUNT;

        try {
            int orderbyIndex = whereCond.toUpperCase().lastIndexOf("ORDER BY");

            if (orderbyIndex > 0) {
                whereCond = whereCond.substring(0, orderbyIndex);
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            SQL += whereCond;

            //		�ж�sql�Ƿ��Ѿ�������
            if (!filtered) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            } else {
                stmt = conn.prepareStatement((SQL));
            }

            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
                totalAmount = rs.getLong("totalAmount");
            }

            rtnArr[0] = lCount;
            rtnArr[1] = totalAmount;
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rtnArr;
    }

    public int getFlag2() {
        return flag2;
    }

    public void setFlag2(int flag2) {
        this.flag2 = flag2;
    }
}
