package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.bo.RcNoBo;
import com.ztesoft.crm.salesres.common.ParamsConsConfig;
import com.ztesoft.crm.salesres.dao.RcNoDAO;
import com.ztesoft.crm.salesres.dao.SalesRescDAO;
import com.ztesoft.crm.salesres.vo.RcNoSegVO;
import com.ztesoft.crm.salesres.vo.RcNoVO;
import com.ztesoft.crm.salesres.vo.SalesRescVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RcNoDAOImpl implements RcNoDAO {
    private String noFamilyId = "";
    private String SQL_SELECT = "SELECT RESOURCE_INSTANCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,SALES_RESOURCE_ID,STORAGE_ID,RESOURCE_STATE,STATE,EFF_DATE,EXP_DATE,IMSI_ID,NO_SEG_ID,REC_TIME,BALA_MODE,INIT_TIME,NO_SEG_NAME,SALES_RESOURCE_NAME,STORAGE_NAME,LAN_ID,REGION_ID,EXCH_ID,self_help_flag FROM RC_NO";
    private String SQL_SELECT_4 = "select RC_NO.RESOURCE_INSTANCE_ID,RC_NO.RESOURCE_INSTANCE_CODE,RC_NO.RESOURCE_LEVEL,RC_NO.SALES_RESOURCE_ID," +
        "RC_NO.STORAGE_ID,RC_NO.RESOURCE_STATE,RC_NO.STATE,RC_NO.EFF_DATE,RC_NO.EXP_DATE,RC_NO.IMSI_ID,RC_NO.NO_SEG_ID,RC_NO.REC_TIME,RC_NO.BALA_MODE,RC_NO.INIT_TIME,RC_NO.NO_SEG_NAME,RC_NO.SALES_RESOURCE_NAME,RC_NO.STORAGE_NAME,LAN_ID,RC_NO.REGION_ID,EXCH_ID,RC_NO.self_help_flag,rc_no_seg.c_double_flag" +
        " FROM RC_NO ,rc_no_seg  where RC_NO.no_seg_id=rc_no_seg.no_seg_id ";
    private String SQL_SELECT_2 = "SELECT a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_LEVEL,a.SALES_RESOURCE_ID,a.STORAGE_ID,a.RESOURCE_STATE,a.STATE,a.EFF_DATE,a.EXP_DATE,a.IMSI_ID,a.NO_SEG_ID,a.REC_TIME,a.BALA_MODE,a.INIT_TIME,b.NO_SEG_NAME,c.SALES_RESOURCE_NAME,d.STORAGE_NAME " +
        ",a.LAN_ID,a.REGION_ID,a.EXCH_ID,a.self_help_flag,b.C_DOUBLE_FLAG" +
        " FROM RC_NO a,rc_no_seg b,SALES_RESOURCE c,RC_STORAGE d where a.no_seg_id=b.no_seg_id and b.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and a.STORAGE_ID=d.STORAGE_ID ";
    private String SQL_SELECT_3 = "";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO a,rc_no_seg b where a.no_seg_id=b.no_seg_id  ";
    private String SQL_SELECT_COUNT_2 = "SELECT  COUNT(*) AS COL_COUNTS FROM RC_NO a,rc_no_seg b,SALES_RESOURCE c,RC_STORAGE d " +
        " where a.no_seg_id=b.no_seg_id and b.SALES_RESOURCE_ID=c.SALES_RESOURCE_ID and a.STORAGE_ID=d.STORAGE_ID ";
    private String SQL_SELECT_COUNT_3 = "";
    private String SQL_SELECT_COUNT_4 = "SELECT COUNT(*) AS COL_COUNTS FROM RC_NO ,rc_no_seg  where RC_NO.no_seg_id=rc_no_seg.no_seg_id  ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_NO ( RESOURCE_INSTANCE_ID,RESOURCE_INSTANCE_CODE,RESOURCE_LEVEL,SALES_RESOURCE_ID,STORAGE_ID,RESOURCE_STATE,STATE,EFF_DATE,EXP_DATE,IMSI_ID,NO_SEG_ID,REC_TIME,BALA_MODE,INIT_TIME,LAN_ID,REGION_ID,EXCH_ID,NO_SEG_NAME,SALES_RESOURCE_NAME,STORAGE_NAME,self_help_flag ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,?,?,?)";
    private String SQL_UPDATE = "UPDATE RC_NO SET  RESOURCE_INSTANCE_CODE = ?, RESOURCE_LEVEL = ?, SALES_RESOURCE_ID = ?, STORAGE_ID = ?, RESOURCE_STATE = ?, STATE = ?, EFF_DATE = ?, EXP_DATE = ?, IMSI_ID = ?, NO_SEG_ID = ?, REC_TIME = ?, BALA_MODE = ?, INIT_TIME = ?,self_help_flag=?  WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_NO WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_NO ";
    private int flag = 1;

    public RcNoDAOImpl() {
    }

    public RcNoVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException {
        List arrayList = findByCond(" RESOURCE_INSTANCE_ID = '" +
                DAOUtils.filterQureyCond(pRESOURCE_INSTANCE_ID) + "'");

        if (arrayList.size() > 0) {
            return (RcNoVO) arrayList.get(0);
        } else {
            return (RcNoVO) getEmptyVO();
        }
    }

    /**
     * 根据号码选择该号码实体
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode(String code) throws DAOSystemException {
        List arrayList = findByCond(" RESOURCE_INSTANCE_CODE = '" +
                DAOUtils.filterQureyCond(code) + "'");

        if (arrayList.size() > 0) {
            return (RcNoVO) arrayList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据号码选择该号码实体，同时加上权限判断
     * @param code String
     * @throws DAOSystemException
     * @return RcNoVO
     */
    public RcNoVO findByCode2(String code, String operId, String departId)
        throws DAOSystemException {
        String cond = " RESOURCE_INSTANCE_CODE = '" +
            DAOUtils.filterQureyCond(code) + "'";

        cond += ("  and exists (select 1 from mp_storage b where RC_NO.storage_id=b.storage_id and " +
        " b.oper_id=" + operId + " union all " +
        "  select 1 from STORAGE_DEPART_RELA c where c.storage_Id=RC_NO.storage_id " +
        "  and c.depart_id=" + departId + ") ");

        List arrayList = findByCond(cond);

        if (arrayList.size() > 0) {
            return (RcNoVO) arrayList.get(0);
        } else {
            return null;
        }
    }

    /**
     * 根据号码查找该号码所属的号段信息
     * @param code String
     * @throws DAOSystemException
     * @return RcNoSegVO:号段信息,如果查不到号段信息则返回null
     */
    public RcNoSegVO findNoSegByNoCode(String code) throws DAOSystemException {
        if ((code == null) || (code.trim().length() < 0)) {
            return null;
        }

        code = DAOUtils.filterQureyCond(code);

        if (!code.matches("\\d*")) {
            return null;
        }

        RcNoSegVO segVO = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String noSegId = null;
        String sql = "SELECT * FROM RC_NO_SEG where BEGINN<='" + code +
            "' and " + "ENDNO>='" + code + "'";
        sql = DAOSQLUtils.getFilterSQL(sql);

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                if (segVO == null) {
                    segVO = new RcNoSegVO();
                }

                segVO.setNoSegId(DAOUtils.trimStr(rs.getString("NO_SEG_ID")));
                segVO.setNoSegName(DAOUtils.trimStr(rs.getString("NO_SEG_NAME")));
                segVO.setStarttime(DAOUtils.getFormatedDateTime(rs.getDate(
                            "STARTTIME")));
                segVO.setIntime(DAOUtils.getFormatedDateTime(rs.getDate(
                            "INTIME")));
                segVO.setBeginn(DAOUtils.trimStr(rs.getString("BEGINN")));
                segVO.setEndno(DAOUtils.trimStr(rs.getString("ENDNO")));
                segVO.setState(DAOUtils.trimStr(rs.getString("STATE")));
                segVO.setImsiSegId(DAOUtils.trimStr(rs.getString("IMSI_SEG_ID")));
                segVO.setNoGrpId(DAOUtils.trimStr(rs.getString("NO_GROUP_ID")));
                segVO.setSalesRescId(DAOUtils.trimStr(rs.getString(
                            "SALES_RESOURCE_ID")));

                segVO.setBalaMode(DAOUtils.trimStr(rs.getString("BALA_MODE")));

                // 查询所属的营销资源名称
                SalesRescDAO rescDao = new SalesRescDAOImpl();
                SalesRescVO rescVO = rescDao.findByPrimaryKey(segVO.getSalesRescId());

                if (rescVO != null) {
                    segVO.setSalesRescName(rescVO.getSalesRescName());
                }
            }
        } catch (SQLException se) {
            Debug.print(sql, this);
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return segVO;
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
            RcNoVO vo = new RcNoVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcNoVO vo, ResultSet rs)
        throws SQLException {
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setRescState(DAOUtils.trimStr(rs.getString("RESOURCE_STATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("EFF_DATE")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("EXP_DATE")));
        vo.setImsiId(DAOUtils.trimStr(rs.getString("IMSI_ID")));
        vo.setNoSegId(DAOUtils.trimStr(rs.getString("NO_SEG_ID")));
        vo.setRecTime(DAOUtils.getFormatedDateTime(rs.getDate("REC_TIME")));
        vo.setBalaMode(DAOUtils.trimStr(rs.getString("BALA_MODE")));
        vo.setInitTime(DAOUtils.getFormatedDateTime(rs.getDate("INIT_TIME")));
        vo.setNoSegName(DAOUtils.trimStr(rs.getString("NO_SEG_NAME")));
        vo.setSalesRescName(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_NAME")));
        vo.setStorageName(DAOUtils.trimStr(rs.getString("STORAGE_NAME")));
        vo.setSelfhelpflag(DAOUtils.trimStr(rs.getString("SELF_HELP_FLAG")));

        if (flag == 4) {
            vo.setCDoubleFlag(DAOUtils.trimStr(rs.getString("C_DOUBLE_FLAG")));
            vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
            vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
            vo.setExchId(DAOUtils.trimStr(rs.getString("EXCH_ID")));
        } else if (flag == 3) {
            vo.setOldRescLevle(DAOUtils.trimStr(rs.getString(
                        "OLD_RESOURCE_LEVEL")));
            vo.setOldRescLevleName(DAOUtils.trimStr(rs.getString(
                        "OLD_RESOURCE_LEVEL_NAME")));
            vo.setRescLevelName(DAOUtils.trimStr(rs.getString(
                        "RESOURCE_LEVEL_NAME")));
        } else {
            vo.setLanId(DAOUtils.trimStr(rs.getString("LAN_ID")));
            vo.setRegionId(DAOUtils.trimStr(rs.getString("REGION_ID")));
            vo.setExchId(DAOUtils.trimStr(rs.getString("EXCH_ID")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcNoVO vo = new RcNoVO();

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

            if (flag == 1) {
                SQL = SQL_SELECT + " WHERE " + whereCond;
            } else if (flag == 2) {
                SQL = SQL_SELECT_2 + whereCond;
            } else if (flag == 3) {
                SQL = SQL_SELECT_3 + whereCond;
            } else if (flag == 4) {
                SQL = SQL_SELECT_4 + whereCond;
            }

            System.out.println(">>" + SQL);
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
        if (vo == null) {
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            int index = 1;

            if ("".equals(((RcNoVO) vo).getRescInstanceId())) {
                ((RcNoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNoVO) vo).getRescInstanceCode());

            if ("".equals(((RcNoVO) vo).getRescLevel())) {
                ((RcNoVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getRescLevel());

            if ("".equals(((RcNoVO) vo).getSalesRescId())) {
                ((RcNoVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getSalesRescId());

            if ("".equals(((RcNoVO) vo).getStorageId())) {
                ((RcNoVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getStorageId());
            stmt.setString(index++, ((RcNoVO) vo).getRescState());
            stmt.setString(index++, ((RcNoVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getExpDate()));

            if ("".equals(((RcNoVO) vo).getImsiId())) {
                ((RcNoVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getImsiId());

            if ("".equals(((RcNoVO) vo).getNoSegId())) {
                ((RcNoVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getNoSegId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getRecTime()));
            stmt.setString(index++, ((RcNoVO) vo).getBalaMode());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcNoVO) vo).getInitTime()));
            stmt.setString(index++, ((RcNoVO) vo).getLanId());
            stmt.setString(index++, ((RcNoVO) vo).getRegionId());
            stmt.setString(index++, ((RcNoVO) vo).getExchId());
            stmt.setString(index++, ((RcNoVO) vo).getNoSegName());
            stmt.setString(index++, ((RcNoVO) vo).getSalesRescName());
            stmt.setString(index++, ((RcNoVO) vo).getStorageName());
            stmt.setString(index++, ((RcNoVO) vo).getSelfhelpflag());

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

    public boolean update(String pRESOURCE_INSTANCE_ID, RcNoVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_NO SET RESOURCE_INSTANCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,SALES_RESOURCE_ID = ?,STORAGE_ID = ?,RESOURCE_STATE = ?,STATE = ?,EFF_DATE = ?,EXP_DATE = ?,IMSI_ID = ?,NO_SEG_ID = ?,REC_TIME = ?,BALA_MODE = ?,INIT_TIME = ?,self_help_flag=?");
            sql.append(" WHERE  RESOURCE_INSTANCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoVO) vo).getRescInstanceId())) {
                ((RcNoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcNoVO) vo).getRescLevel())) {
                ((RcNoVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());

            if ("".equals(((RcNoVO) vo).getSalesRescId())) {
                ((RcNoVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RcNoVO) vo).getStorageId())) {
                ((RcNoVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());
            stmt.setString(index++, vo.getRescState());
            stmt.setString(index++, vo.getState());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));

            if ("".equals(((RcNoVO) vo).getImsiId())) {
                ((RcNoVO) vo).setImsiId(null);
            }

            stmt.setString(index++, vo.getImsiId());

            if ("".equals(((RcNoVO) vo).getNoSegId())) {
                ((RcNoVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, vo.getNoSegId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getRecTime()));
            stmt.setString(index++, ((RcNoVO) vo).getBalaMode());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getInitTime()));
            stmt.setString(index++, ((RcNoVO) vo).getSelfhelpflag());
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);

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
     * 根据条件更新号池
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateStorage(String storageId, String storageName,
        String whereCond) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        long rows = 0L;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_NO SET STORAGE_ID = ?,STORAGE_NAME=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index, storageId);
            stmt.setString(++index, storageName);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
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
                "UPDATE RC_NO SET RESOURCE_INSTANCE_ID = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_LEVEL = ?,SALES_RESOURCE_ID = ?,STORAGE_ID = ?,RESOURCE_STATE = ?,STATE = ?,EFF_DATE = ?,EXP_DATE = ?,IMSI_ID = ?,NO_SEG_ID = ?,REC_TIME = ?,BALA_MODE = ?,INIT_TIME = ?,self_help_flag=?");
            sql.append(" WHERE " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcNoVO) vo).getRescInstanceId())) {
                ((RcNoVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcNoVO) vo).getRescInstanceCode());

            if ("".equals(((RcNoVO) vo).getRescLevel())) {
                ((RcNoVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getRescLevel());

            if ("".equals(((RcNoVO) vo).getSalesRescId())) {
                ((RcNoVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getSalesRescId());

            if ("".equals(((RcNoVO) vo).getStorageId())) {
                ((RcNoVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getStorageId());
            stmt.setString(index++, ((RcNoVO) vo).getRescState());
            stmt.setString(index++, ((RcNoVO) vo).getState());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getEffDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getExpDate()));

            if ("".equals(((RcNoVO) vo).getImsiId())) {
                ((RcNoVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getImsiId());

            if ("".equals(((RcNoVO) vo).getNoSegId())) {
                ((RcNoVO) vo).setNoSegId(null);
            }

            stmt.setString(index++, ((RcNoVO) vo).getNoSegId());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getRecTime()));
            stmt.setString(index++, ((RcNoVO) vo).getBalaMode());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcNoVO) vo).getInitTime()));
            stmt.setString(index++, ((RcNoVO) vo).getSelfhelpflag());

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

            if (flag == 1) {
                if (whereCond.trim().startsWith("and")) {
                    SQL = SQL_SELECT_COUNT + whereCond;
                } else {
                    SQL = SQL_SELECT_COUNT + " and " + whereCond;
                }
            } else if (flag == 2) {
                SQL = SQL_SELECT_COUNT_2 + whereCond;
            } else if (flag == 3) {
                SQL = SQL_SELECT_COUNT_3 + whereCond;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            } else if (flag == 4) {
                SQL = SQL_SELECT_COUNT_4 + " and " + whereCond;
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
            }

            if (flag != 4) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
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

    public long delete(String pRESOURCE_INSTANCE_ID) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        int rows = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));

            int index = 1;
            stmt.setString(index++, pRESOURCE_INSTANCE_ID);
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

    public VO getEmptyVO() {
        return new RcNoVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = "";

        if (flag == 1) {
            SQL = SQL_SELECT + " WHERE " + whereCond;
        } else if (flag == 2) {
            SQL = SQL_SELECT_2 + whereCond;
        } else if (flag == 3) {
            SQL = SQL_SELECT_3 + whereCond;
        } else if (flag == 4) {
            SQL = SQL_SELECT_4 + " and " + whereCond;
        }

        String filterSQL = SQL;

        if (queryFilter != null) {
            filterSQL = queryFilter.doPreFilter(SQL);
        }

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            SQL = filterSQL;

            if (flag != 3) {
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
     * 根据条件更改号码的状态
     * @param storageId String
     * @param whereCond String
     * @throws DAOSystemException
     * @return int
     */
    public long updateRescState(String rescState, String whereCond)
        throws DAOSystemException {
        if ((rescState == null) || (rescState.trim().length() < 1) ||
                (whereCond == null) || (whereCond.trim().length() < 1)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        long rows = 0L;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_NO SET RESOURCE_STATE = ?");
            sql.append(" WHERE " + whereCond);
            System.out.println("号码冻结" +
                DAOSQLUtils.getFilterSQL(sql.toString()));
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;
            stmt.setString(index, rescState);
            rows = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return rows;
    }

    /**
     *  批量插入号码
     * @param list List
     * @return long
     */
    public long batchInsert(RcNoVO vo, String beginnoStr, String endnoStr,
        List levelList) throws DAOSystemException {
        if (vo == null) {
            return 0L;
        }

        long result = 0L;
        RcNoBo bo = new RcNoBo();
        String noPk = null;
        String levelId = null;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            SequenceManageDAO seqDao = SeqDAOFactory.getInstance()
                                                    .getSequenceManageDAO();
            long beginno = Long.parseLong(beginnoStr);
            long endno = Long.parseLong(endnoStr);

            while (beginno <= endno) {
                noPk = seqDao.getNextSequence("RC_ENTITY", "RcEntity_ID");

                int index = 1;

                if ((noPk != null) && "".equals(noPk)) {
                    noPk = null;
                }

                stmt.setString(index++, noPk);
                stmt.setString(index++, String.valueOf(beginno));
                levelId = bo.setEntityLevel(String.valueOf(beginno), levelList);

                if ((levelId != null) && "".equals(levelId)) {
                    levelId = null;
                }

                stmt.setString(index++, levelId);

                if ("".equals(vo.getSalesRescId())) {
                    vo.setSalesRescId(null);
                }

                stmt.setString(index++, (vo.getSalesRescId()));

                if ("".equals(vo.getStorageId())) {
                    vo.setStorageId(null);
                }

                stmt.setString(index++, vo.getStorageId());
                stmt.setString(index++, ParamsConsConfig.RcEntityFreeState);
                stmt.setString(index++, ParamsConsConfig.RcEntityStateValide);
                stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
                stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));

                if ("".equals(vo.getImsiId())) {
                    vo.setImsiId(null);
                }

                stmt.setString(index++, vo.getImsiId());

                if ("".equals(vo.getNoSegId())) {
                    vo.setNoSegId(null);
                }

                stmt.setString(index++, vo.getNoSegId());
                stmt.setDate(index++, DAOUtils.parseDateTime(vo.getRecTime()));
                stmt.setString(index++, vo.getBalaMode());
                stmt.setDate(index++, DAOUtils.parseDateTime(vo.getInitTime()));
                stmt.setString(index++, vo.getLanId());
                stmt.setString(index++, vo.getRegionId());
                stmt.setString(index++, vo.getExchId());
                stmt.setString(index++, vo.getNoSegName());
                stmt.setString(index++, vo.getSalesRescName());
                stmt.setString(index++, vo.getStorageName());
                stmt.setString(index++, vo.getSelfhelpflag());
                stmt.addBatch();
                beginno++;
            }

            int[] resultArr = stmt.executeBatch();

            if (resultArr != null) {
                result = (long) resultArr.length;
            }

            return result;
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     *  批量插入号码
     * @param list List
     * @return long
     */
    public long batchAdd(List noList) throws DAOSystemException {
        if ((noList == null) || (noList.size() < 1)) {
            return 0L;
        }

        long result = 0L;
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

            ////////
            int index = 1;
            RcNoVO voTemp = null;

            for (int i = 0; i < noList.size(); i++) {
                voTemp = (RcNoVO) noList.get(i);

                if ("".equals(voTemp.getRescInstanceId())) {
                    voTemp.setRescInstanceId(null);
                }

                stmt.setString(index++, voTemp.getRescInstanceId());
                stmt.setString(index++, voTemp.getRescInstanceCode());

                if ("".equals(voTemp.getRescLevel())) {
                    voTemp.setRescLevel(null);
                }

                stmt.setString(index++, voTemp.getRescLevel());

                if ("".equals(voTemp.getSalesRescId())) {
                    voTemp.setSalesRescId(null);
                }

                stmt.setString(index++, voTemp.getSalesRescId());

                if ("".equals(voTemp.getStorageId())) {
                    voTemp.setStorageId(null);
                }

                stmt.setString(index++, voTemp.getStorageId());
                stmt.setString(index++, voTemp.getRescState());
                stmt.setString(index++, voTemp.getState());
                stmt.setDate(index++,
                    DAOUtils.parseDateTime(voTemp.getEffDate()));
                stmt.setDate(index++,
                    DAOUtils.parseDateTime(voTemp.getExpDate()));

                if ("".equals(voTemp.getImsiId())) {
                    voTemp.setImsiId(null);
                }

                stmt.setString(index++, voTemp.getImsiId());

                if ("".equals(voTemp.getNoSegId())) {
                    voTemp.setNoSegId(null);
                }

                stmt.setString(index++, voTemp.getNoSegId());
                stmt.setDate(index++,
                    DAOUtils.parseDateTime(voTemp.getRecTime()));
                stmt.setString(index++, voTemp.getBalaMode());
                stmt.setTimestamp(index++,
                    DAOUtils.parseTimestamp(voTemp.getInitTime()));
                stmt.setString(index++, voTemp.getLanId());
                stmt.setString(index++, voTemp.getRegionId());
                stmt.setString(index++, voTemp.getExchId());
                stmt.setString(index++, voTemp.getNoSegName());
                stmt.setString(index++, voTemp.getSalesRescName());
                stmt.setString(index++, voTemp.getStorageName());
                stmt.setString(index++, voTemp.getSelfhelpflag());

                stmt.addBatch();
                index = 1;
            }

            ////////
            int[] resultArr = stmt.executeBatch();

            if (resultArr != null) {
                result = (long) resultArr.length;
            }

            return result;
        } catch (SQLException se) {
            Debug.print(SQL_INSERT, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_INSERT, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    public int updateRescStateByBatch(List list) throws DAOSystemException {
        if ((list == null) || (list.size() == 0)) {
            return 0;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if (conn != null) {
                stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                            "UPDATE RC_NO SET  RESOURCE_LEVEL = ? WHERE RESOURCE_INSTANCE_ID = ? "));

                for (int i = 0; i < list.size(); i++) {
                    int index = 1;
                    Object vo = list.get(i);

                    if ("".equals(((RcNoVO) vo).getRescLevel())) {
                        ((RcNoVO) vo).setRescLevel(null);
                    }

                    stmt.setString(index++, ((RcNoVO) vo).getRescLevel());

                    if ("".equals(((RcNoVO) vo).getRescInstanceId())) {
                        ((RcNoVO) vo).setRescInstanceId(null);
                    }

                    stmt.setString(index++, ((RcNoVO) vo).getRescInstanceId());
                    stmt.addBatch();
                    stmt.clearParameters();
                }

                stmt.executeBatch();
            }

            return list.size();
        } catch (SQLException se) {
            Debug.print(SQL_UPDATE, this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                SQL_UPDATE, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 按照仓库,号段,或者起始号码和终止号码修改号码等级
     * 状态,资源状态,付费方式.
     * @see com.ztesoft.crm.sr.nosim.dao.RcNoDAO#updateBatch(com.ztesoft.crm.sr.nosim.vo.RcNoVO)
     */
    public int updateBatch(RcNoVO vo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        PreparedStatement stmt2 = null;

        // 通用日志序列
        SequenceManageDAO seqDao = SeqDAOFactory.getInstance()
                                                .getSequenceManageDAO();
        String logCode = seqDao.getNextSequence("EntityChangeLog", "logCode");

        // 是否插入表EntityChangeLog及其语句
        boolean needInsEntityChg = false;
        StringBuffer insertlogSql = new StringBuffer(
                "insert into EntityChangeLog(RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,family_id,entityType,resource_state_from," +
                "state_from,resource_state_to,state_to,storage_id_from,storage_id_to,changeDate,operCode,operId,logCode,oper_Type,notes) " +
                " select RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,null,1");

        // 是否插入表NoChangeLog及其语句
        boolean needInsNoChg = false;
        StringBuffer insNoChgSql = new StringBuffer("insert into ").append(DAOSQLUtils.getTableName(
                    "NoChangeLog"))
                                                                   .append("(LOGCODE,RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID," +
                "SELF_HELP_FLAG_NEW,SELF_HELP_FLAG_OLD,OPER_CODE,CHANGEDATE)" +
                " select '" + logCode +
                "',RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,");

        StringBuffer upSql = new StringBuffer();
        String table = DAOSQLUtils.getTableName("RC_NO");
        upSql.append("update ").append(table).append(" set ");

        if (null != (vo.getState())) {
            upSql.append(" state ='").append(vo.getState()).append("', ");
        }

        if (!"".equals(vo.getRescState()) && !"A".equals(vo.getRescState())) {
            upSql.append(" RESOURCE_STATE ='").append(vo.getRescState())
                 .append("', ");
        }

        if (null != (vo.getRescLevel())) {
            if (!"delete".equals(vo.getRescLevel())) {
                upSql.append(" RESOURCE_LEVEL ='").append(vo.getRescLevel())
                     .append("', ");
            } else {
                upSql.append(" RESOURCE_LEVEL ='',");
            }
        }

        if (null != (vo.getBalaMode())) {
            upSql.append(" BALA_MODE ='").append(vo.getBalaMode()).append("', ");
        }

        if (null != (vo.getLanId())) {
            upSql.append(" LAN_ID=")
                 .append(vo.getLanId().equals("") ? null : vo.getLanId())
                 .append(",");
        }

        if (null != (vo.getRegionId())) {
            upSql.append(" REGION_ID=")
                 .append(vo.getRegionId().equals("") ? null : vo.getRegionId())
                 .append(",");
        }

        if (null != (vo.getExchId())) {
            upSql.append(" EXCH_ID=")
                 .append(vo.getExchId().equals("") ? null : vo.getExchId())
                 .append(",");
        }

        if (null != (vo.getEffDate())) {
            upSql.append("EFF_DATE = to_date('").append(vo.getEffDate())
                 .append("','yyyy-mm-dd'),");
        }

        if (null != (vo.getExpDate())) {
            upSql.append("EXP_DATE = to_date('").append(vo.getExpDate())
                 .append("','yyyy-mm-dd'),");
        }

        if ((null != (vo.getSelfhelpflag())) &&
                (vo.getSelfhelpflag().trim().length() > 0)) {
            upSql.append("self_help_flag = '").append(vo.getSelfhelpflag())
                 .append("'");
            // 
            needInsNoChg = true;
            insNoChgSql.append("'" + vo.getSelfhelpflag() + "'")
                       .append(",SELF_HELP_FLAG,");
        }

        if (upSql.toString().trim().endsWith(",")) {
            String temp = upSql.toString().trim();
            temp = temp.substring(0, temp.length() - 1);
            upSql = new StringBuffer(temp);
        }

        upSql.append(" where STORAGE_ID in(").append(vo.getStorageId())
             .append(") ");

        // 日志
        if (needInsNoChg) {
            insNoChgSql.append("'" + vo.getOperCode() + "',")
                       .append("sysdate from ").append(table)
                       .append(" where STORAGE_ID in(").append(vo.getStorageId())
                       .append(") ");
        }

        if (!"".equals(vo.getNoSegId())) {
            upSql.append(" and NO_SEG_ID =").append(vo.getNoSegId());

            if (needInsNoChg) {
                insNoChgSql.append(" and NO_SEG_ID =").append(vo.getNoSegId());
            }
        } else if (!"".equals(vo.getStartCode()) &&
                !"".equals(vo.getEndCode())) {
            upSql.append(" and RESOURCE_INSTANCE_CODE>='")
                 .append(vo.getStartCode()).append("'")
                 .append(" and RESOURCE_INSTANCE_CODE<='")
                 .append(vo.getEndCode()).append("'");

            if (needInsNoChg) {
                insNoChgSql.append(" and RESOURCE_INSTANCE_CODE>='")
                           .append(vo.getStartCode()).append("'")
                           .append(" and RESOURCE_INSTANCE_CODE<='")
                           .append(vo.getEndCode()).append("'");
            }
        }

        int r = 0;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);

            if (needInsNoChg) { // 记录号码自助选号日志
                Debug.print("号码自助选号日志：" + insNoChgSql.toString());
                stmt2 = conn.prepareStatement(insNoChgSql.toString());
                stmt2.executeUpdate();
            }

            Debug.print("号码修改：" + upSql.toString());
            stmt = conn.prepareStatement(upSql.toString());
            r = stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(upSql.toString(), this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                upSql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn, this);
        }

        return r;
    }

    /**
     * 批量更新号码的状态
     * @param vo
     * @param noArr:需要更新的号码的数组
     * @param checkType:检查类型，0代表不检查状态，直接更新；1代表校验状态：不能是已用B、预占E
     * @return Map：result:-1缺少参数,1成功执行，但可能有失败记录。errorList:失败号码的集合，这些号码是逻辑错误，即不符合要求的号码
     */
    public Map updateBatchNoState(RcNoVO vo, String[] noArr, int checkType,
        String states) {
        Map map = new HashMap();
        List failList = new ArrayList();
        String ss = vo.getOperId();
        String[] s = null;
        String departId = "";

        if ((ss != null) && (ss.length() > 0)) {
            s = ss.split("@");

            if (s.length > 1) {
                vo.setOperId(s[0]);
                departId = s[1];
            }
        }

        if ((noArr == null) || (noArr.length < 1) || (vo == null)) {
            map.put("result", "-1");
            map.put("failList", failList);

            return map;
        }

        Connection conn1 = null;
        Connection conn2 = null;

        // 此处用Statement，是因为oracle如果用preparedstatement批量执行后返回的数组没有成功条数信息，因此无法知道哪些号码不合要求。
        // 而且考虑数据量应该不会太大，所以用statement
        Statement stmt1 = null;
        PreparedStatement stmt2 = null;
        String operType = "01"; // 修改号码状态
        int[] insertArr = null;
        int[] updateArr = null;

        StringBuffer upSql = new StringBuffer();

        String insertSql = "insert into nochangelog(RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,resource_state_from," +
            "state_from,resource_state_to,state_to,storage_id_from,storage_id_to,changeDate,oper_code,logCode) " +
            " select RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID,RESOURCE_STATE,STATE,";
        String checkCondTemp = " and RESOURCE_STATE not in (" + states + ")"; //不可修改的状态
        String resourceStateToTemp = "RESOURCE_STATE";
        String stateToTemp = "STATE";
        String logCode = null;
        String table = DAOSQLUtils.getTableName("RC_NO");

        if (!"".equals(vo.getRescState()) && (vo.getRescState() != null) &&
                !"".equals(vo.getState()) && (vo.getState() != null)) {
            upSql.append("update ").append(table).append(" set ");
            upSql.append(
                " RESOURCE_STATE = ?,STATE=? where RESOURCE_INSTANCE_CODE = ? ");
            resourceStateToTemp = "'" + vo.getRescState() + "'";
            stateToTemp = "'" + vo.getState() + "'";
        } else if (!"".equals(vo.getRescState()) &&
                (vo.getRescState() != null)) {
            upSql.append("update ").append(table).append(" set ");
            upSql.append(
                " RESOURCE_STATE = ? where RESOURCE_INSTANCE_CODE = ? ");
            resourceStateToTemp = "'" + vo.getRescState() + "'";
        } else if (!"".equals(vo.getState()) && (vo.getState() != null)) {
            upSql.append("update ").append(table).append(" set ");
            upSql.append(" STATE=? where RESOURCE_INSTANCE_CODE = ? ");
            stateToTemp = "'" + vo.getState() + "'";
        } else {
            map.put("result", "-1");
            map.put("failList", failList);

            return map;
        }

        SequenceManageDAO seqDao = SeqDAOFactory.getInstance()
                                                .getSequenceManageDAO();
        logCode = seqDao.getNextSequence("NoChangeLog", "logCode");
        insertSql += (resourceStateToTemp + "," + stateToTemp +
        ",null,null,sysdate,'" + vo.getOperCode() + "'," + logCode +
        " from rc_no where 1=1 ");

        if (checkType == 1) {
            insertSql += checkCondTemp;
        }

        insertSql += (" and not exists (select 1 from pdn_pdinfo" +
        " where dn_no = rc_no.resource_instance_code union all " +
        " select 1 from pd_pdinfo" +
        " where dn_no = rc_no.resource_instance_code)");

        if (departId.length() > 0) {
            insertSql += (" and exists(select x.STORAGE_ID from mp_storage x where x.STORAGE_ID=rc_no.storage_id and x.oper_id=" +
            vo.getOperId() +
            " union all select y.STORAGE_ID from STORAGE_DEPART_RELA y where y.STORAGE_ID=rc_no.storage_id and y.depart_id=" +
            departId + ")");
        }

        insertSql += " and RESOURCE_INSTANCE_CODE=";

        try {
            conn1 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt1 = conn1.createStatement();

            for (int i = 0; i < noArr.length; i++) {
                stmt1.addBatch(insertSql + "'" +
                    DAOUtils.filterQureyCond(noArr[i]) + "'");
            }

            insertArr = stmt1.executeBatch();
            // 先执行查询并插入修改日志的表，得到成功的号码，再更新这些号码的状态
            conn2 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt2 = conn2.prepareStatement(upSql.toString());

            for (int j = 0; j < noArr.length; j++) {
                if (insertArr[j] > 0) {
                    if (!"".equals(vo.getRescState()) &&
                            (vo.getRescState() != null) &&
                            !"".equals(vo.getState()) &&
                            (vo.getState() != null)) {
                        stmt2.setString(1, vo.getRescState());
                        stmt2.setString(2, vo.getState());
                        stmt2.setString(3, noArr[j]);
                    } else if (!"".equals(vo.getRescState()) &&
                            (vo.getRescState() != null)) {
                        stmt2.setString(1, vo.getRescState());
                        stmt2.setString(2, noArr[j]);
                    } else if (!"".equals(vo.getState()) &&
                            (vo.getState() != null)) {
                        stmt2.setString(1, vo.getState());
                        stmt2.setString(2, noArr[j]);
                    }

                    stmt2.addBatch();
                } else {
                    failList.add(noArr[j]);
                }
            }

            updateArr = stmt2.executeBatch();
        } catch (SQLException se) {
            Debug.print(upSql.toString(), this);
            throw new DAOSystemException("SQLException ,insert sql:\n" +
                insertSql.toString() + "||update sql:" + upSql, se);
        } finally {
            DAOUtils.closeStatement(stmt1);
            DAOUtils.closeConnection(conn1, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn2, this);
        }

        map.put("result", "1");
        map.put("failList", failList);

        return map;
    }

    public void setNoFamilyId(String string) {
        this.noFamilyId = string;
        SQL_SELECT_3 = "SELECT  a.RESOURCE_INSTANCE_ID,a.RESOURCE_INSTANCE_CODE,a.RESOURCE_LEVEL,a.SALES_RESOURCE_ID,a.STORAGE_ID,a.RESOURCE_STATE,a.STATE,a.EFF_DATE,a.EXP_DATE,a.IMSI_ID,a.NO_SEG_ID,a.REC_TIME,a.BALA_MODE,a.INIT_TIME," +
            "a.NO_SEG_NAME,a.SALES_RESOURCE_NAME,a.STORAGE_NAME,'' as self_help_flag,'' as OLD_RESOURCE_LEVEL,h.rc_level_name AS RESOURCE_LEVEL_NAME ,'' AS OLD_RESOURCE_LEVEL_NAME" +
            " FROM rcdb.RC_NO a " //+ "left  join (select distinct F.RESOURCE_INSTANCE_ID, F.OLD_RESOURCE_LEVEL from rcdb.RC_LEVEL_LOG f order by log_id desc ) E "
                                  //+ " on a.resource_instance_id = e.resource_instance_id "
             +"left  join rcdb.rc_level_def h on h.rc_level_id  = a.resource_level and h.family_id=" +
            getNoFamilyId() //+ " left  join rcdb.rc_level_def j on j.rc_level_id  = E.OLD_RESOURCE_LEVEL and j.family_id="
                            //+ getNoFamilyId() 
             +" where a.resource_state = 'A' and";
        SQL_SELECT_COUNT_3 = "SELECT  COUNT(*) AS COL_COUNTS " +
            " FROM rcdb.RC_NO a " + " where a.resource_state = 'A' and";
    }

    public String getNoFamilyId() {
        return noFamilyId;
    }

    /**
     * 按照仓库,号段,或者起始号码和终止号码修改号码等级
     * 状态,资源状态,付费方式.
     * @see com.ztesoft.crm.sr.nosim.dao.RcNoDAO#updateBatch(com.ztesoft.crm.sr.nosim.vo.RcNoVO)
     */
    public Map updateTxtBatch(RcNoVO vo, String flag, List list) {
        Connection conn1 = null;
        Connection conn2 = null;

        // 此处用Statement，是因为oracle如果用preparedstatement批量执行后返回的数组没有成功条数信息，因此无法知道哪些号码不合要求。
        // 而且考虑数据量应该不会太大，所以用statement
        Statement stmt1 = null;
        PreparedStatement stmt2 = null;
        int r = 0;

        // 通用日志序列	  
        SequenceManageDAO seqDao = SeqDAOFactory.getInstance()
                                                .getSequenceManageDAO();
        String logCode = seqDao.getNextSequence("NoChangeLog", "logCode");

        //	  String logCode = "139";
        int[] insertArr = null;
        int[] updateArr = null;
        StringBuffer upSql = new StringBuffer();
        String table = DAOSQLUtils.getTableName("RC_NO");
        upSql.append("update ").append(table).append(" set ");

        StringBuffer insNoChgSql = new StringBuffer("insert into ").append(DAOSQLUtils.getTableName(
                    "NoChangeLog"))
                                                                   .append("(LOGCODE,RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID," +
                "resource_state_from,resource_state_to,state_from,state_to,storage_id_from,storage_id_to,resc_level_from," +
                "resc_level_to ,SELF_HELP_FLAG_OLD,SELF_HELP_FLAG_NEW,OPER_CODE,CHANGEDATE)" +
                " select " + logCode +
                ",RESOURCE_INSTANCE_CODE,SALES_RESOURCE_ID,RESOURCE_INSTANCE_ID," +
                "null,null,null,null,null,null");

        if ((null != vo.getRescLevel()) &&
                (vo.getRescLevel().trim().length() > 0)) {
            if (vo.getRescLevel().equals("delete")) {
                insNoChgSql.append(",resource_level," + "null");
            } else {
                insNoChgSql.append(",resource_level," + vo.getRescLevel());
            }
        } else {
            insNoChgSql.append(",null,null");
        }

        if ((null != vo.getSelfhelpflag()) &&
                (vo.getSelfhelpflag().trim().length() > 0)) {
            insNoChgSql.append(",SELF_HELP_FLAG," + vo.getSelfhelpflag());
        } else {
            insNoChgSql.append(",null,null");
        }

        if ((null != vo.getOperCode()) &&
                (vo.getOperCode().trim().length() > 0)) {
            insNoChgSql.append(",'" + vo.getOperCode() + "',sysdate" +
                " from " + table);
        } else {
            insNoChgSql.append(",'',sysdate" + " from " + table);
        }

        if ((null != (vo.getRescLevel())) &&
                (vo.getRescLevel().trim().length() > 0) &&
                !"null".equals(vo.getRescLevel())) {
            if (vo.getRescLevel().equals("delete")) {
                upSql.append(" RESOURCE_LEVEL ='',");
            } else {
                upSql.append("RESOURCE_LEVEL ='").append(vo.getRescLevel())
                     .append("', ");
            }
        }

        if ((null != (vo.getBalaMode())) &&
                (vo.getBalaMode().trim().length() > 0) &&
                !"null".equals(vo.getBalaMode())) {
            upSql.append(" BALA_MODE ='").append(vo.getBalaMode()).append("', ");
        }

        if ((null != (vo.getLanId())) && (vo.getLanId().trim().length() > 0) &&
                !"null".equals(vo.getLanId())) {
            upSql.append(" LAN_ID=")
                 .append(vo.getLanId().equals("") ? null : vo.getLanId())
                 .append(",");
        }

        if ((null != (vo.getRegionId())) &&
                (vo.getRegionId().trim().length() > 0) &&
                !"null".equals(vo.getRegionId())) {
            upSql.append(" REGION_ID=")
                 .append(vo.getRegionId().equals("") ? null : vo.getRegionId())
                 .append(",");
        }

        if ((null != (vo.getExchId())) && (vo.getExchId().trim().length() > 0) &&
                !"null".equals(vo.getExchId())) {
            upSql.append(" EXCH_ID=")
                 .append(vo.getExchId().equals("") ? null : vo.getExchId())
                 .append(",");
        }

        if ((null != (vo.getEffDate())) &&
                (vo.getEffDate().trim().length() > 0) &&
                !"null".equals(vo.getEffDate())) {
            upSql.append("EFF_DATE = to_date('").append(vo.getEffDate())
                 .append("','yyyy-mm-dd'),");
        }

        if ((null != (vo.getExpDate())) &&
                (vo.getExpDate().trim().length() > 0) &&
                !"null".equals(vo.getExpDate())) {
            upSql.append("EXP_DATE = to_date('").append(vo.getExpDate())
                 .append("','yyyy-mm-dd'),");
        }

        if ((null != (vo.getSelfhelpflag())) &&
                (vo.getSelfhelpflag().trim().length() > 0) &&
                !"null".equals(vo.getSelfhelpflag())) {
            upSql.append("self_help_flag = '").append(vo.getSelfhelpflag())
                 .append("'");
        }

        if (upSql.toString().trim().endsWith(",")) {
            String temp = upSql.toString().trim();
            temp = temp.substring(0, temp.length() - 1);
            upSql = new StringBuffer(temp);
        }

        upSql.append(" where STORAGE_ID in(").append(vo.getStorageId())
             .append(") ");

        //	  upSql.append(" where STORAGE_ID in(10140) ");
        int[] result = null;
        List failList = new ArrayList();
        Map map = new HashMap();
        insNoChgSql.append(" where STORAGE_ID in(").append(vo.getStorageId())
                   .append(") ");
        insNoChgSql.append("  and resource_instance_code = ");
        upSql.append(" and resource_instance_code = ?");

        try {
            conn1 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt1 = conn1.createStatement();

            for (int i = 0; i < list.size(); i++) {
                stmt1.addBatch(insNoChgSql.toString() + "'" + list.get(i) +
                    "'");
            }

            insertArr = stmt1.executeBatch();
            //先执行查询并插入修改日志的表，得到成功的号码，再更新这些号码的状态  	
            conn2 = ConnectionContext.getContext()
                                     .getConnection(JNDINames.CRM_RCDB);
            stmt2 = conn2.prepareStatement(upSql.toString());

            for (int j = 0; j < list.size(); j++) {
                if (insertArr[j] > 0) {
                    stmt2.setString(1, (String) list.get(j));
                    stmt2.addBatch();
                } else {
                    failList.add(list.get(j));
                }
            }

            updateArr = stmt2.executeBatch();
        } catch (SQLException se) {
            Debug.print("插入日志报错：");
            Debug.print(insNoChgSql.toString(), this);
            Debug.print("更新表报错：");
            Debug.print(upSql.toString(), this);
            throw new DAOSystemException("SQLException while insert sql:\n" +
                upSql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt1);
            DAOUtils.closeConnection(conn1, this);
            DAOUtils.closeStatement(stmt2, this);
            DAOUtils.closeConnection(conn2, this);
        }

        map.put("result", "1");
        map.put("failList", failList);

        return map;
    }
}
