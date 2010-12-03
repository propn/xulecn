package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcSaleLogDAO;
import com.ztesoft.crm.salesres.vo.RcSaleLogVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcSaleLogDAOImpl implements RcSaleLogDAO {
    private String SQL_SELECT = "SELECT a.log_id,a.resource_instance_id,a.sales_resource_id,a.lan_id,a.sale_time,a.resource_instance2,a.depart_id,a.oper_id,a.price,a.style,a.is_out,a.before_state,a.after_state,a.storage_id,a.stock_amount,a.product_no,a.cust_id,a.imei,a.cust_name,a.deal_type,a.deal_info,a.produce_no,a.biz_type,b.org_name,c.family_id,c.manage_mode,c.sales_resource_name,d.storage_name " +
        " FROM RC_SALE_LOG a,organization b,SALES_RESOURCE c,rc_storage d " +
        " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS " +
        " FROM RC_SALE_LOG a,organization b,SALES_RESOURCE c,rc_storage d " +
        " where a.depart_id=b.party_id and a.sales_resource_id=c.sales_resource_id and a.storage_id=d.storage_id ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_SALE_LOG ( log_id,resource_instance_id,sales_resource_id,lan_id,sale_time,resource_instance2,depart_id,oper_id,price,style,is_out,before_state,after_state,storage_id,stock_amount,product_no,cust_id,imei,cust_name,deal_type,deal_info,produce_no ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_SALE_LOG SET  resource_instance_id = ?, sales_resource_id = ?, lan_id = ?, sale_time = ?, resource_instance2 = ?, depart_id = ?, oper_id = ?, price = ?, style = ?, is_out = ?, before_state = ?, after_state = ?, storage_id = ?, stock_amount = ?, product_no = ?, cust_id = ?,imei=?, DEAL_TYPE = ?, DEAL_INFO = ?  WHERE log_id = ? ";
    private String SQL_DELETE = "DELETE FROM RC_SALE_LOG WHERE log_id = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_SALE_LOG ";

    //如果flag为1，则用另一个populateDto2方法,如果为3则调用populateDto3
    int flag = 0;

    /** sql语句中的表明是否经过了过滤，默认没有经过过滤 **/
    boolean filtered = false;

    public RcSaleLogDAOImpl() {
    }

    public RcSaleLogVO findByPrimaryKey(String plog_id)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT + " and a.log_id = ? ",
                new String[] { plog_id });

        if (arrayList.size() > 0) {
            return (RcSaleLogVO) arrayList.get(0);
        } else {
            return (RcSaleLogVO) getEmptyVO();
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
            RcSaleLogVO vo = new RcSaleLogVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcSaleLogVO vo, ResultSet rs)
        throws SQLException {
        if (flag == 1) {
            this.populateDto2(vo, rs);

            return;
        } else if (flag == 3) {
            this.populateDto3(vo, rs);

            return;
        } else if (flag == 5) {
            this.populateDto5(vo, rs);

            return;
        }

        vo.setLogId(DAOUtils.trimStr(rs.getString("log_id")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "resource_instance_id")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setLanId(DAOUtils.trimStr(rs.getString("lan_id")));
        vo.setSaleTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("sale_time")));
        vo.setRescInstance2(DAOUtils.trimStr(rs.getString("resource_instance2")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setOperId(DAOUtils.trimStr(rs.getString("oper_id")));
        vo.setPrice(DAOUtils.trimStr(rs.getString("price")));
        vo.setStyle(DAOUtils.trimStr(rs.getString("style")));
        vo.setIsOut(DAOUtils.trimStr(rs.getString("is_out")));
        vo.setBeforeState(DAOUtils.trimStr(rs.getString("before_state")));
        vo.setAfterState(DAOUtils.trimStr(rs.getString("after_state")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setStockAmount(DAOUtils.trimStr(rs.getString("stock_amount")));
        vo.setProdNo(DAOUtils.trimStr(rs.getString("product_no")));
        vo.setCustId(DAOUtils.trimStr(rs.getString("cust_id")));
        vo.setImei(DAOUtils.trimStr(rs.getString("imei")));
        vo.setCustName(DAOUtils.trimStr(rs.getString("cust_name")));
        vo.setDealType(DAOUtils.trimStr(rs.getString("deal_type")));
        vo.setDealInfo(DAOUtils.trimStr(rs.getString("deal_info")));
        vo.setProduceNo(DAOUtils.trimStr(rs.getString("produce_no")));
        vo.setBiztype(DAOUtils.trimStr(rs.getString("biz_type")));

        vo.setOrgName(DAOUtils.trimStr(rs.getString("org_name")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));

        vo.setFamilyId(DAOUtils.trimStr(rs.getString("family_id")));
        vo.setManageMode(DAOUtils.trimStr(rs.getString("manage_mode")));

        if (flag == 2) {
            vo.setOperName(DAOUtils.trimStr(rs.getString("party_role_name")));
            vo.setCreateDate(DAOUtils.getFormatedDateTime(rs.getTimestamp(
                        "create_date")));
            vo.setRescState(DAOUtils.trimStr(rs.getString("RESOURCE_STATE")));
            vo.setState(DAOUtils.trimStr(rs.getString("state")));
            vo.setProduceNo(DAOUtils.trimStr(rs.getString("produce_no")));
        }
    }

    protected void populateDto2(RcSaleLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setDepartId(DAOUtils.trimStr(rs.getString("depart_id")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setStockAmount(DAOUtils.trimStr(rs.getString("stock_amount")));
        vo.setOrgName(DAOUtils.trimStr(rs.getString("org_name")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
    }

    protected void populateDto3(RcSaleLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("sales_resource_id")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setStockAmount(DAOUtils.trimStr(rs.getString("stock_amount")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
        vo.setBiztype(DAOUtils.trimStr(rs.getString("biz_type")));
    }

    protected void populateDto5(RcSaleLogVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstance2(DAOUtils.trimStr(rs.getString("resource_instance2")));
        vo.setSaleTime(DAOUtils.getFormatedDateTime(rs.getTimestamp("sale_time")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("depart_name")));
        vo.setManufacturer(DAOUtils.trimStr(rs.getString("manufacturer")));
        vo.setProvider(DAOUtils.trimStr(rs.getString("provider")));
        vo.setShopkeeper(DAOUtils.trimStr(rs.getString("shopkeeper")));
        vo.setCurrState(DAOUtils.trimStr(rs.getString("curr_state")));
        vo.setLanName(DAOUtils.trimStr(rs.getString("lan_name")));
        vo.setDepartName(DAOUtils.trimStr(rs.getString("depart_name")));
        vo.setProduceNo(DAOUtils.trimStr(rs.getString("product_no")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("sales_resource_name")));
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcSaleLogVO vo = new RcSaleLogVO();

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
            SQL = SQL_SELECT + " " + whereCond;
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

            if ("".equals(((RcSaleLogVO) vo).getLogId())) {
                ((RcSaleLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getLogId());

            if ("".equals(((RcSaleLogVO) vo).getRescInstanceId())) {
                ((RcSaleLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getRescInstanceId());

            if ("".equals(((RcSaleLogVO) vo).getSalesRescId())) {
                ((RcSaleLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getSalesRescId());

            if ("".equals(((RcSaleLogVO) vo).getLanId())) {
                ((RcSaleLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getLanId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSaleLogVO) vo).getSaleTime()));
            stmt.setString(index++, ((RcSaleLogVO) vo).getRescInstance2());

            if ("".equals(((RcSaleLogVO) vo).getDepartId())) {
                ((RcSaleLogVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getDepartId());

            if ("".equals(((RcSaleLogVO) vo).getOperId())) {
                ((RcSaleLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getOperId());

            if ("".equals(((RcSaleLogVO) vo).getPrice())) {
                ((RcSaleLogVO) vo).setPrice(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getPrice());
            stmt.setString(index++, ((RcSaleLogVO) vo).getStyle());
            stmt.setString(index++, ((RcSaleLogVO) vo).getIsOut());
            stmt.setString(index++, ((RcSaleLogVO) vo).getBeforeState());
            stmt.setString(index++, ((RcSaleLogVO) vo).getAfterState());

            if ("".equals(((RcSaleLogVO) vo).getStorageId())) {
                ((RcSaleLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getStorageId());

            if ("".equals(((RcSaleLogVO) vo).getStockAmount())) {
                ((RcSaleLogVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getStockAmount());
            stmt.setString(index++, ((RcSaleLogVO) vo).getProdNo());

            if ("".equals(((RcSaleLogVO) vo).getCustId())) {
                ((RcSaleLogVO) vo).setCustId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getCustId());
            stmt.setString(index++, ((RcSaleLogVO) vo).getImei());
            stmt.setString(index++, ((RcSaleLogVO) vo).getCustName());
            stmt.setString(index++, ((RcSaleLogVO) vo).getDealType());
            stmt.setString(index++, ((RcSaleLogVO) vo).getDealInfo());

            if ("".equals(((RcSaleLogVO) vo).getProduceNo())) {
                ((RcSaleLogVO) vo).setProduceNo(null);
            }

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

    public boolean update(String plog_id, RcSaleLogVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_SALE_LOG SET log_id = ?,resource_instance_id = ?,sales_resource_id = ?,lan_id = ?,sale_time = ?,resource_instance2 = ?,depart_id = ?,oper_id = ?,price = ?,style = ?,is_out = ?,before_state = ?,after_state = ?,storage_id = ?,stock_amount = ?,product_no = ?,cust_id = ?,imei=?,deal_type = ?,deal_info = ?");
            sql.append(" WHERE  log_id = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcSaleLogVO) vo).getLogId())) {
                ((RcSaleLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, vo.getLogId());

            if ("".equals(((RcSaleLogVO) vo).getRescInstanceId())) {
                ((RcSaleLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());

            if ("".equals(((RcSaleLogVO) vo).getSalesRescId())) {
                ((RcSaleLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RcSaleLogVO) vo).getLanId())) {
                ((RcSaleLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, vo.getLanId());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getSaleTime()));
            stmt.setString(index++, vo.getRescInstance2());

            if ("".equals(((RcSaleLogVO) vo).getDepartId())) {
                ((RcSaleLogVO) vo).setDepartId(null);
            }

            stmt.setString(index++, vo.getDepartId());

            if ("".equals(((RcSaleLogVO) vo).getOperId())) {
                ((RcSaleLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, vo.getOperId());

            if ("".equals(((RcSaleLogVO) vo).getPrice())) {
                ((RcSaleLogVO) vo).setPrice(null);
            }

            stmt.setString(index++, vo.getPrice());
            stmt.setString(index++, vo.getStyle());
            stmt.setString(index++, vo.getIsOut());
            stmt.setString(index++, vo.getBeforeState());
            stmt.setString(index++, vo.getAfterState());

            if ("".equals(((RcSaleLogVO) vo).getStorageId())) {
                ((RcSaleLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());

            if ("".equals(((RcSaleLogVO) vo).getStockAmount())) {
                ((RcSaleLogVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, vo.getStockAmount());
            stmt.setString(index++, vo.getProdNo());

            if ("".equals(((RcSaleLogVO) vo).getCustId())) {
                ((RcSaleLogVO) vo).setCustId(null);
            }

            stmt.setString(index++, vo.getCustId());
            stmt.setString(index++, vo.getImei());
            stmt.setString(index++, vo.getDealType());
            stmt.setString(index++, vo.getDealInfo());

            stmt.setString(index++, plog_id);

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
                "UPDATE RC_SALE_LOG SET log_id = ?,resource_instance_id = ?,sales_resource_id = ?,lan_id = ?,sale_time = ?,resource_instance2 = ?,depart_id = ?,oper_id = ?,price = ?,style = ?,is_out = ?,before_state = ?,after_state = ?,storage_id = ?,stock_amount = ?,product_no = ?,cust_id = ?,imei=?,deal_type = ?,deal_info = ?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcSaleLogVO) vo).getLogId())) {
                ((RcSaleLogVO) vo).setLogId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getLogId());

            if ("".equals(((RcSaleLogVO) vo).getRescInstanceId())) {
                ((RcSaleLogVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getRescInstanceId());

            if ("".equals(((RcSaleLogVO) vo).getSalesRescId())) {
                ((RcSaleLogVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getSalesRescId());

            if ("".equals(((RcSaleLogVO) vo).getLanId())) {
                ((RcSaleLogVO) vo).setLanId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getLanId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSaleLogVO) vo).getSaleTime()));
            stmt.setString(index++, ((RcSaleLogVO) vo).getRescInstance2());

            if ("".equals(((RcSaleLogVO) vo).getDepartId())) {
                ((RcSaleLogVO) vo).setDepartId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getDepartId());

            if ("".equals(((RcSaleLogVO) vo).getOperId())) {
                ((RcSaleLogVO) vo).setOperId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getOperId());

            if ("".equals(((RcSaleLogVO) vo).getPrice())) {
                ((RcSaleLogVO) vo).setPrice(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getPrice());
            stmt.setString(index++, ((RcSaleLogVO) vo).getStyle());
            stmt.setString(index++, ((RcSaleLogVO) vo).getIsOut());
            stmt.setString(index++, ((RcSaleLogVO) vo).getBeforeState());
            stmt.setString(index++, ((RcSaleLogVO) vo).getAfterState());

            if ("".equals(((RcSaleLogVO) vo).getStorageId())) {
                ((RcSaleLogVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getStorageId());

            if ("".equals(((RcSaleLogVO) vo).getStockAmount())) {
                ((RcSaleLogVO) vo).setStockAmount(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getStockAmount());
            stmt.setString(index++, ((RcSaleLogVO) vo).getProdNo());

            if ("".equals(((RcSaleLogVO) vo).getCustId())) {
                ((RcSaleLogVO) vo).setCustId(null);
            }

            stmt.setString(index++, ((RcSaleLogVO) vo).getCustId());
            stmt.setString(index++, ((RcSaleLogVO) vo).getImei());
            stmt.setString(index++, ((RcSaleLogVO) vo).getDealType());
            stmt.setString(index++, ((RcSaleLogVO) vo).getDealInfo());

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
            SQL = SQL_SELECT_COUNT + " " + whereCond;

            if (flag != 1) {
                SQL = DAOSQLUtils.getFilterSQL(SQL);
            }

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

    public long delete(String plog_id) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, plog_id);
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
        return new RcSaleLogVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " " + whereCond;
        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;

            if (flag != 1) {
                SQL = DAOSQLUtils.getFilterSQL(SQL);
            }

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

    /**
     * 根据条件查询出记录总数和其中一个字段的统计总数
     * sql中必须有COL_COUNTS 和 totalAmount两个字段
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

            //		判断sql是否已经过滤了
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

    public void setSQL_SELECT(String sql_select) {
        SQL_SELECT = sql_select;

        //flag = 1;
    }

    public void setSQL_SELECT_COUNT(String sql_select_count) {
        SQL_SELECT_COUNT = sql_select_count;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }
}
