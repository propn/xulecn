package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.CrmConstants;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.vo.RcAttrDefVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class SalesRescDAOImpl implements SalesRescDAO {
    private String SQL_SELECT = "SELECT sales_resource_id,family_id,resource_area_id,manage_mode,sales_resource_code,sales_resource_name,sales_resource_worth,state,state_date,create_date,eff_date,exp_date,MOST_PRICE,LOW_PRICE,fee_item_id,B.feeitem_name,is_coop,u_kind FROM SALES_RESOURCE left join  DF_FEE_ITEM B on fee_item_id = B.feeitem_code ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM SALES_RESOURCE";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO SALES_RESOURCE ( sales_resource_id,family_id,resource_area_id,manage_mode,sales_resource_code,sales_resource_name,sales_resource_worth,state,state_date,create_date,eff_date,exp_date,LOW_PRICE, fee_item_id) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE SALES_RESOURCE SET  sales_resource_id = ?, family_id = ?, resource_area_id = ?, manage_mode = ?, sales_resource_code = ?, sales_resource_name = ?, sales_resource_worth = ?, state = ?, state_date = ?, create_date = ?, eff_date = ?, exp_date = ?,LOW_PRICE = ?,fee_item_id = ? WHERE";
    private String SQL_DELETE = "DELETE FROM SALES_RESOURCE WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM SALES_RESOURCE ";

    public SalesRescDAOImpl() {
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
            SalesRescVO vo = new SalesRescVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(SalesRescVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
        vo.setRescAreaId(DAOUtils.trimStr(rs.getString("resource_area_id")));
        vo.setManageMode(DAOUtils.trimStr(rs.getString("manage_mode")));
        vo.setSalesRescCode(DAOUtils.trimStr(rs.getString("sales_resource_code")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
        vo.setSalesRescWorth(DAOUtils.trimStr(rs.getString(
                    "sales_resource_worth")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setStateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "state_date")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "create_date")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("exp_date")));
        vo.setLowPrice(DAOUtils.trimStr(rs.getString("LOW_PRICE")));
        vo.setMostPrice(DAOUtils.trimStr(rs.getString("MOST_PRICE")));
        vo.setFee_item_id(DAOUtils.trimStr(rs.getString("fee_item_id")));
        vo.setIsCoop(DAOUtils.trimStr(rs.getString("IS_COOP")));
        vo.setUKind(DAOUtils.trimStr(rs.getString("U_KIND")));

        ResultSetMetaData rsmd = rs.getMetaData();
        int rsFlag = 0;

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            String ColumnName = rsmd.getColumnName(i);

            if (ColumnName == "feeitem_name") {
                rsFlag = 1;
            }

            if (rsFlag == 1) {
                vo.setFeeitem_name(DAOUtils.trimStr(rs.getString("feeitem_name")));
            }
        }
    }

    protected void populateDtoUtil(SalesRescVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
        vo.setRescAreaId(DAOUtils.trimStr(rs.getString("resource_area_id")));
        vo.setManageMode(DAOUtils.trimStr(rs.getString("manage_mode")));
        vo.setSalesRescCode(DAOUtils.trimStr(rs.getString("sales_resource_code")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
        vo.setSalesRescWorth(DAOUtils.trimStr(rs.getString(
                    "sales_resource_worth")));
        vo.setState(DAOUtils.trimStr(rs.getString("state")));
        vo.setStateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "state_date")));
        vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                    "create_date")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("eff_date")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("exp_date")));
        vo.setFamilyName(DAOUtils.trimStr(rs.getString("family_name")));
        vo.setLowPrice(DAOUtils.trimStr(rs.getString("LOW_PRICE")));
        vo.setMostPrice(DAOUtils.trimStr(rs.getString("MOST_PRICE")));
        vo.setFee_item_id(DAOUtils.trimStr(rs.getString("fee_item_id")));
        vo.setFeeitem_name(DAOUtils.trimStr(rs.getString("feeitem_name")));
        vo.setIsCoop(DAOUtils.trimStr(rs.getString("IS_COOP")));
        vo.setUKind(DAOUtils.trimStr(rs.getString("U_KIND")));

        //    try {
        //      vo.setNcSalesRescId(DAOUtils.trimStr(rs.getString("nc_sales_resource_id")));
        //      vo.setDcDeviceScode(DAOUtils.trimStr(rs.getString("dc_device_scode")));
        //    }
        //    catch (SQLException e) {}
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        SalesRescVO vo = new SalesRescVO();

        try {
            populateDtoUtil(vo, rs);
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
            SQL = SQL_SELECT + " WHERE  " + whereCond;
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

    public SalesRescVO findByPrimaryKey(String pSALES_RESOURCE_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " WHERE SALES_RESOURCE_ID = ? ",
                new String[] { pSALES_RESOURCE_ID });

        if (arrayList.size() > 0) {
            return (SalesRescVO) arrayList.get(0);
        } else {
            return (SalesRescVO) getEmptyVO();
        }
    }

    public void insert(VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL_INSERT = "";

        try {
            String databaseType = DAOUtils.getDatabaseType();

            if (databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)) {
                SQL_INSERT = "INSERT INTO SALES_RESOURCE ( sales_resource_id,family_id,resource_area_id,manage_mode,sales_resource_code,sales_resource_name,sales_resource_worth,state,state_date,create_date,eff_date,exp_date,LOW_PRICE,fee_item_id ,IS_COOP,U_KIND,most_price) VALUES ( ?,?,?,?,?,?,?,?,current,current,?,?,?,?,?,?,? )";
            } else if (databaseType.equals(CrmConstants.DB_TYPE_ORACLE)) {
                SQL_INSERT = "INSERT INTO SALES_RESOURCE ( sales_resource_id,family_id,resource_area_id,manage_mode,sales_resource_code,sales_resource_name,sales_resource_worth,state,state_date,create_date,eff_date,exp_date,LOW_PRICE,fee_item_id ,IS_COOP,U_KIND,most_price) VALUES ( ?,?,?,?,?,?,?,?,sysdate,sysdate,?,?,?,?,?,?,? )";
            }

            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            int index = 1;

            if ("".equals(((SalesRescVO) vo).getSalesRescId())) {
                ((SalesRescVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescId());

            if ("".equals(((SalesRescVO) vo).getFamilyId())) {
                ((SalesRescVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getFamilyId());

            if ("".equals(((SalesRescVO) vo).getRescAreaId())) {
                ((SalesRescVO) vo).setRescAreaId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getRescAreaId());
            stmt.setString(index++, ((SalesRescVO) vo).getManageMode());
            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescCode());
            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescName());

            if ("".equals(((SalesRescVO) vo).getSalesRescWorth())) {
                ((SalesRescVO) vo).setSalesRescWorth(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescWorth());
            stmt.setString(index++, ((SalesRescVO) vo).getState());

            //stmt.setTimestamp(index++, DAOUtils.parseTimestamp(((SalesRescVO) vo)
            //.getStateDate()));
            //stmt.setTimestamp(index++, DAOUtils.parseTimestamp(((SalesRescVO) vo)
            //.getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((SalesRescVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((SalesRescVO) vo).getExpDate()));
            stmt.setString(index++, ((SalesRescVO) vo).getLowPrice());
            stmt.setString(index++, ((SalesRescVO) vo).getFee_item_id());
            stmt.setString(index++, ((SalesRescVO) vo).getIsCoop());
            stmt.setString(index++, ((SalesRescVO) vo).getUKind());
            stmt.setString(index++, ((SalesRescVO) vo).getMostPrice());

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

            String databaseType = DAOUtils.getDatabaseType();

            if (databaseType.equals(CrmConstants.DB_TYPE_INFORMIX)) {
                sql.append(
                    "UPDATE SALES_RESOURCE SET sales_resource_id = ?,family_id = ?,resource_area_id = ?,manage_mode = ?,sales_resource_code = ?,sales_resource_name = ?,sales_resource_worth = ?,state = ?,state_date = current,eff_date = ?,exp_date = ?,low_price = ?,fee_item_id = ?,IS_COOP=?,U_KIND=?,most_price=?");
            } else if (databaseType.equals(CrmConstants.DB_TYPE_ORACLE)) {
                sql.append(
                    "UPDATE SALES_RESOURCE SET sales_resource_id = ?,family_id = ?,resource_area_id = ?,manage_mode = ?,sales_resource_code = ?,sales_resource_name = ?,sales_resource_worth = ?,state = ?,state_date = sysdate,eff_date = ?,exp_date = ?,low_price = ?,fee_item_id = ?,IS_COOP=?,U_KIND=?,most_price=?");
            }

            sql.append(" WHERE  " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((SalesRescVO) vo).getSalesRescId())) {
                ((SalesRescVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescId());

            if ("".equals(((SalesRescVO) vo).getFamilyId())) {
                ((SalesRescVO) vo).setFamilyId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getFamilyId());

            if ("".equals(((SalesRescVO) vo).getRescAreaId())) {
                ((SalesRescVO) vo).setRescAreaId(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getRescAreaId());
            stmt.setString(index++, ((SalesRescVO) vo).getManageMode());
            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescCode());
            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescName());

            if ("".equals(((SalesRescVO) vo).getSalesRescWorth())) {
                ((SalesRescVO) vo).setSalesRescWorth(null);
            }

            stmt.setString(index++, ((SalesRescVO) vo).getSalesRescWorth());
            stmt.setString(index++, ((SalesRescVO) vo).getState());
            //stmt.setTimestamp(index++, DAOUtils.parseTimestamp(((SalesRescVO) vo)
            //		.getStateDate()));
            //stmt.setTimestamp(index++, DAOUtils.parseTimestamp(((SalesRescVO) vo)
            //		.getCreateDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((SalesRescVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDate(((SalesRescVO) vo).getExpDate()));
            stmt.setString(index++, ((SalesRescVO) vo).getLowPrice());
            stmt.setString(index++, ((SalesRescVO) vo).getFee_item_id());
            stmt.setString(index++, ((SalesRescVO) vo).getIsCoop());
            stmt.setString(index++, ((SalesRescVO) vo).getUKind());
            stmt.setString(index++, ((SalesRescVO) vo).getMostPrice());

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

            String SALES_RESOURCE = DAOSQLUtils.getTableName("SALES_RESOURCE");
            String RC_FAMILY = DAOSQLUtils.getTableName("RC_FAMILY");
            SQL = " SELECT COUNT(*) AS COL_COUNTS FROM " + SALES_RESOURCE +
                " A , " + RC_FAMILY + " B" +
                " WHERE A.family_id = B.family_id " + whereCond;
            stmt = conn.prepareStatement(SQL);
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
            // 删除营销资源属性
            deleteAttrByCond(whereCond);
            // 删除营销资源
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

    public void deleteAttrByCond(String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "delete from SALES_RESOURCE_ATTR WHERE " + whereCond;
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public int getMaxRows() {
        return maxRows;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new SalesRescVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SALES_RESOURCE = DAOSQLUtils.getTableName("SALES_RESOURCE");
        String RC_FAMILY = DAOSQLUtils.getTableName("RC_FAMILY");
        String SALES_RESOURCE_ID_RELA = DAOSQLUtils.getTableName(
                "SALES_RESOURCE_ID_RELA");
        String DF_FEE_ITEM = DAOSQLUtils.getTableName("DF_FEE_ITEM");

        //    String SQL = " SELECT A.sales_resource_id,A.family_id,B.family_name,A.resource_area_id,A.manage_mode,A.sales_resource_code,"
        //        + " A.sales_resource_name,A.sales_resource_worth,A.state,A.state_date,A.create_date,A.eff_date,A.exp_date,A.low_price,C.nc_sales_resource_id,C.dc_device_scode"
        //        + " FROM " + SALES_RESOURCE + " A inner join " + RC_FAMILY + " B"
        //        + " on A.family_id = B.family_id " + whereCond
        //        + " left outer join " + SALES_RESOURCE_ID_RELA +
        //        " C on A.sales_resource_id=C.sales_resource_id "
        //        + " ORDER BY B.FAMILY_NAME, A.sales_resource_name, A.create_date";
        String SQL = " SELECT A.sales_resource_id,A.family_id,B.family_name,A.resource_area_id,A.manage_mode,A.sales_resource_code," +
            " A.sales_resource_name,A.sales_resource_worth,A.state,A.state_date,A.create_date,A.eff_date,A.exp_date,A.low_price,A.most_price, " +
            "A.fee_item_id,C.feeitem_name,A.IS_COOP,A.u_kind FROM " +
            SALES_RESOURCE + " A inner join " + RC_FAMILY + " B" +
            " on A.family_id = B.family_id " + whereCond + " left join " +
            DF_FEE_ITEM + " C on A.fee_item_id = C.feeitem_code " +
            " ORDER BY B.FAMILY_NAME, A.sales_resource_name, A.create_date";
        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;
            stmt = conn.prepareStatement(SQL, ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE);
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

    public ArrayList qrySrAttrInfo(String salesRescId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";
        ArrayList retList = new ArrayList();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = "select a.attr_id,a.attr_value,b.attr_name from SALES_RESOURCE_ATTR a, attribute b where  a.sales_resource_id = ? and a.attr_id = b.attr_id";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
                    ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            stmt.setString(1, salesRescId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                RcAttrDefVO vo = new RcAttrDefVO();
                vo.setAttrId(DAOUtils.trimStr(rs.getString("attr_id")));
                vo.setAttrName(DAOUtils.trimStr(rs.getString("attr_name")));
                vo.setAttrValue(DAOUtils.trimStr(rs.getString("attr_value")));
                retList.add(vo);
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

    //保存实体资源的属性信息
    public boolean insertAttrInfo(String salesRescId, String attrId,
        String attrValue) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        boolean flag = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " insert into SALES_RESOURCE_ATTR values(?,?,?,'F')";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            if ("".equals(salesRescId)) {
                salesRescId = null;
            }

            stmt.setString(1, salesRescId);

            if ("".equals(attrId)) {
                attrId = null;
            }

            stmt.setString(2, attrId);

            if ("".equals(attrValue)) {
                attrValue = null;
            }

            stmt.setString(3, attrValue);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                flag = true;
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return flag;
    }

    //查看当前要添加的属性是否已经添加
    public long countAttrInfo(String salesRescId, String attrId)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;
        ResultSet rs = null;
        long lCount = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " SELECT COUNT(*) AS COL_COUNTS from SALES_RESOURCE_ATTR where sales_resource_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
            stmt.setString(1, salesRescId);
            stmt.setString(2, attrId);
            rs = stmt.executeQuery();

            while (rs.next()) {
                lCount = rs.getLong("COL_COUNTS");
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return lCount;
    }

    public int deleteAttrInfo(String salesRescId, String attrValue)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;
        String SQL = "";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from SALES_RESOURCE_ATTR where sales_resource_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            stmt.setString(1, salesRescId);
            stmt.setString(2, attrValue);
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

    public String deleteAttrInfos(String salesRescId, List attrObjs)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String SQL = "";
        HashMap hp = new HashMap();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = " delete from SALES_RESOURCE_ATTR where sales_resource_id = ? and attr_id = ? ";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));

            for (Iterator it = attrObjs.iterator(); it.hasNext();) {
                int i = 0;
                hp = (HashMap) it.next();
                stmt.setObject(++i, salesRescId);
                stmt.setObject(++i, hp.get("attrId"));

                try {
                    stmt.executeUpdate();
                } catch (SQLException se) {
                    Debug.print(salesRescId + "===" +
                        String.valueOf(hp.get("attrId")), this);
                    it.remove();
                }

                stmt.clearParameters();
            }
        } catch (SQLException se) {
            Debug.print(SQL, this);
            throw new DAOSystemException("SQLException while deleting sql:\n" +
                SQL, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return "";
    }

    public String insertAttrInfos(String salesRescId, List attrObjs)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql = " insert into SALES_RESOURCE_ATTR values(?,?,?,'F')";
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));

            for (Iterator it = attrObjs.iterator(); it.hasNext();) {
                int i = 0;
                HashMap hp = (HashMap) it.next();
                stmt.setObject(++i, salesRescId);
                stmt.setObject(++i, hp.get("attrId"));
                stmt.setObject(++i, hp.get("attrValue"));

                try {
                    stmt.executeUpdate();
                } catch (SQLException se) {
                    Debug.print(salesRescId + "===" +
                        String.valueOf(hp.get("attrId")) + "===" +
                        String.valueOf(hp.get("attrValue")), this);
                    it.remove();
                }

                stmt.clearParameters();
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return "";
    }
}
