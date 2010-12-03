package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcStorageDAO;
import com.ztesoft.crm.salesres.vo.RcStorageVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcStorageDAOImpl implements RcStorageDAO {
//    private String SQL_SELECT = "SELECT a.storage_id,a.storage_name,a.owner_id,a.storage_state,a.storage_desc,a.address,a.storage_code,a.up_storage_id,a.R_STORAGE_CODE1,a.R_STORAGE_CODE2,a.rc_type,a.lan_id,c.lan_name,d.oper_name,a.c_time from rc_storage a  " +
//        "  left join rr_lan c on a.lan_id = c.lan_id " +
//        "left join mp_operator d on a.c_oper_id = d.oper_id ";
    
    private String SQL_SELECT = "SELECT a.storage_id,a.storage_name,a.owner_id,a.storage_state,a.storage_desc,a.address, "
        +" a.storage_code,a.up_storage_id,b.storage_name as up_storage_name,a.R_STORAGE_CODE1,a.R_STORAGE_CODE2,a.rc_type,a.lan_id,d.oper_name,a.c_time "
        +" from rc_storage a left outer join rc_storage b "
        +" on a.up_storage_id=b.storage_id left join mp_operator d on a.c_oper_id = d.oper_id ";

    //private String SQL_SELECT_2 = "SELECT a.*,(select count(*) from rc_storage b where b.up_storage_id=a.storage_id) as childrenNum from rc_storage a ";
    private String SQL_SELECT_2 = "SELECT a.*,0 as childrenNum from rc_storage a ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS from rc_storage a";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_STORAGE ( storage_id,storage_name,owner_id,storage_state,storage_desc,address,storage_code,up_storage_id,R_STORAGE_CODE1,R_STORAGE_CODE2,rc_type,c_oper_id,c_time,lan_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String SQL_UPDATE = "UPDATE RC_STORAGE SET  storage_id = ?, storage_name = ?, owner_id = ?, storage_state = ?, storage_desc = ?, address = ?, storage_code = ?,up_storage_id=?,R_STORAGE_CODE1=?,R_STORAGE_CODE2=? WHERE";
    private String SQL_DELETE = "DELETE FROM RC_STORAGE WHERE";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_STORAGE ";
    public int flag = 0;

    public RcStorageDAOImpl() {
    }

    public RcStorageVO findByPrimaryKey(String storageId) {
        RcStorageVO vo = null;
        String sql = "SELECT a.storage_id,a.storage_name,a.owner_id,a.storage_state,a.storage_desc,a.address, " +
            " a.storage_code,a.up_storage_id,b.storage_name as up_storage_name,a.R_STORAGE_CODE1,a.R_STORAGE_CODE2,a.rc_type,a.lan_id,d.oper_name,a.c_time  " +
            " from rc_storage a left outer join rc_storage b " +
            " on a.up_storage_id=b.storage_id left join mp_operator d on a.c_oper_id = d.oper_id where a.storage_id=? ";
        this.setFlag(1);

        ArrayList list = this.findBySql(sql, new String[] { storageId });

        if ((list != null) && (list.size() > 0)) {
            vo = (RcStorageVO) list.get(0);
        }

        return vo;
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
        RcStorageVO vo = null;

        while (rs.next()) {
            vo = new RcStorageVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcStorageVO vo, ResultSet rs)
        throws SQLException {
        vo.setStorageId(DAOUtils.trimStr(rs.getString("storage_id")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("storage_name")));
        vo.setOwnerId(DAOUtils.trimStr(rs.getString("owner_id")));
        vo.setStorageState(DAOUtils.trimStr(rs.getString("storage_state")));
        vo.setStorageDesc(DAOUtils.trimStr(rs.getString("storage_desc")));
        vo.setAddr(DAOUtils.trimStr(rs.getString("address")));
        vo.setStorageCode(DAOUtils.trimStr(rs.getString("storage_code")));
        vo.setRStorageCode1(DAOUtils.trimStr(rs.getString("R_STORAGE_CODE1")));
        vo.setRStorageCode2(DAOUtils.trimStr(rs.getString("R_STORAGE_CODE2")));
        vo.setRcType(DAOUtils.trimStr(rs.getString("rc_type")));
        vo.setLan_id(DAOUtils.trimStr(rs.getString("lan_id")));
        //		vo.setStorageType( DAOUtils.trimStr( rs.getString( "storage_type" ) ) );
        vo.setUpStorageId(DAOUtils.trimStr(rs.getString("up_storage_id")));

        if (flag == 1) {
            vo.setUpStorageName(DAOUtils.trimStr(rs.getString("up_storage_name")));
            vo.setLan_id(DAOUtils.trimStr(rs.getString("lan_id")));
            vo.setOper_name(DAOUtils.trimStr(rs.getString("oper_name")));
            vo.setC_time(DAOUtils.getFormatedDateTime(rs.getTimestamp("c_time")));
        }

        if (flag == 2) {
            vo.setChildrenNum(rs.getInt("childrenNum"));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcStorageVO vo = new RcStorageVO();

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
        String man_sql = SQL_SELECT;

        if (flag == 2) {
            man_sql = SQL_SELECT_2;
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = man_sql + " WHERE " + whereCond;
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

            if ("".equals(((RcStorageVO) vo).getStorageId())) {
                ((RcStorageVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getStorageId());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageName());

            if ("".equals(((RcStorageVO) vo).getOwnerId())) {
                ((RcStorageVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getOwnerId());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageState());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageDesc());
            stmt.setString(index++, ((RcStorageVO) vo).getAddr());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageCode());

            if ("".equals(((RcStorageVO) vo).getUpStorageId())) {
                ((RcStorageVO) vo).setUpStorageId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getUpStorageId());
            stmt.setString(index++, ((RcStorageVO) vo).getRStorageCode1());
            stmt.setString(index++, ((RcStorageVO) vo).getRStorageCode2());
            stmt.setString(index++, ((RcStorageVO) vo).getRcType());
            stmt.setString(index++, ((RcStorageVO) vo).getC_oper_id());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcStorageVO) vo).getC_time()));
            stmt.setString(index++, ((RcStorageVO) vo).getLan_id());

            //			stmt.setString(index++, ((RcStorageVO) vo).getStorageType());
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
                "UPDATE RC_STORAGE SET storage_id = ?,storage_name = ?,owner_id = ?,storage_state = ?,storage_desc = ?,address = ?,storage_code = ?,up_storage_id= ?,R_STORAGE_CODE1=?,R_STORAGE_CODE2=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcStorageVO) vo).getStorageId())) {
                ((RcStorageVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getStorageId());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageName());

            if ("".equals(((RcStorageVO) vo).getOwnerId())) {
                ((RcStorageVO) vo).setOwnerId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getOwnerId());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageState());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageDesc());
            stmt.setString(index++, ((RcStorageVO) vo).getAddr());
            stmt.setString(index++, ((RcStorageVO) vo).getStorageCode());

            if ("".equals(((RcStorageVO) vo).getUpStorageId())) {
                ((RcStorageVO) vo).setUpStorageId(null);
            }

            stmt.setString(index++, ((RcStorageVO) vo).getUpStorageId());
            stmt.setString(index++, ((RcStorageVO) vo).getRStorageCode1());
            stmt.setString(index++, ((RcStorageVO) vo).getRStorageCode2());

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

    public int getFlag() {
        return flag;
    }

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public VO getEmptyVO() {
        return new RcStorageVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT + " WHERE " + whereCond;
        String filterSQL = SQL;
        
        this.setFlag(1);

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

    /**
     * 检查该仓库是否被别的表或资源引用
     * @param StorageId String
     * @throws DAOSystemException
     * @return boolean
     */
    public boolean checkStorageState(String StorageId)
        throws DAOSystemException {
        if ((StorageId == null) || (StorageId.trim().length() < 1)) {
            return false;
        }

        StorageId = DAOUtils.filterQureyCond(StorageId);

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String sql_entity = " select * from rc_entity where storage_id =  " +
            StorageId;
        String sql_stock = " select * from rc_stock where storage_id =  " +
            StorageId;
        String sql_sim = " select * from rc_sim where storage_id =  " +
            StorageId;
        String sql_no = " select * from rc_no where storage_id =  " +
            StorageId;
        String sql_mp_storage = " select * from mp_storage where storage_id =  " +
            StorageId;
        String sql_rela = " select * from storage_depart_rela where storage_id =  " +
            StorageId;
        String sql_limit = " select * from rc_stock_limit where storage_id =  " +
            StorageId;
        String[] arrs = new String[] {
                sql_entity, sql_stock, sql_sim, sql_no, sql_mp_storage, sql_rela,
                sql_limit
            };
        String sql = sql_entity;
        boolean result = false;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if ((arrs != null) && (arrs.length > 0)) {
                for (int i = 0; i < arrs.length; i++) {
                    sql = arrs[i];
                    stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
                            ResultSet.TYPE_SCROLL_SENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);
                    rs = stmt.executeQuery();

                    if (rs.next()) {
                        result = true;

                        break;
                    }
                }
            }

            return result;
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
}
