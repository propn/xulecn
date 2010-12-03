package com.ztesoft.crm.salesres.dao.impl;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;

import com.ztesoft.crm.salesres.dao.RcSimDAO;
import com.ztesoft.crm.salesres.vo.RcSimVO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;


public class RcSimDAOImpl implements RcSimDAO {
    private String SQL_SELECT = "SELECT CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE,RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID,WLAN_pwd,WLAN_acct,MSISDN,KI,pin2,pin1,puk2_no,ESN,IMSI_ID,DN_NO,IMSI_ID2,DN_NO2,UMID,SERV_CODE,SERV_NAME,CARD_TYPE,C_SERV_TYPE ,C_SERV_TYPE_NAME,ACC,SMSP,SIM_CHIP_TYPE FROM RC_SIM  where 1=1 ";
    private String SQL_SELECT_EX = "SELECT R.CARDMILL,R.INIT_TIME,R.PUK_NO,R.SIM_TYPE,R.CAPACITY,R.TEL_CAPACITY,R.SMS_CAPACITY,R.BACKUP,R.EXP_DATE,R.EFF_DATE,R.STATE,R.RESOURCE_STATE,R.STORAGE_ID,R.SALES_RESOURCE_ID,R.RESOURCE_LEVEL,R.RESOURCE_INSTANCE_CODE,R.RESOURCE_INSTANCE_ID, R.IMSI_ID,S.STORAGE_NAME,R.WLAN_pwd,R.WLAN_acct,R.MSISDN,R.KI,R.pin2,R.pin1,R.puk2_no,R.ESN,R.DN_NO,R.IMSI_ID2,R.DN_NO2,R.UMID,R.SERV_CODE,R.SERV_NAME,R.CARD_TYPE,R.C_SERV_TYPE,R.C_SERV_TYPE_NAME,R.ACC,R.SMSP,R.SIM_CHIP_TYPE " +
        " FROM RC_SIM R inner join RC_STORAGE S on R.STORAGE_ID = S.STORAGE_ID WHERE  1=1  ";
    private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RC_SIM  where 1=1 ";
    private String SQL_SELECT_COUNT_EX = "SELECT COUNT(*) AS COL_COUNTS FROM RC_SIM R inner join RC_STORAGE S on R.STORAGE_ID = S.STORAGE_ID  WHERE  1=1  ";
    private int maxRows;
    private String SQL_INSERT = "INSERT INTO RC_SIM ( ACCEPT_ID,CARDMILL,INIT_TIME,PUK_NO,SIM_TYPE,CAPACITY,TEL_CAPACITY,SMS_CAPACITY,BACKUP,EXP_DATE,EFF_DATE,STATE,RESOURCE_STATE,STORAGE_ID,SALES_RESOURCE_ID,RESOURCE_LEVEL,RESOURCE_INSTANCE_CODE,RESOURCE_INSTANCE_ID,WLAN_pwd,WLAN_acct,MSISDN,KI,pin2,pin1,puk2_no,ESN,IMSI_ID,DN_NO,IMSI_ID2,DN_NO2,C_SERV_TYPE_NAME,ACC,SMSP,SIM_CHIP_TYPE ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";
    private String SQL_UPDATE = "UPDATE RC_SIM SET ACCEPT_ID = ?, CARDMILL = ?, INIT_TIME = ?, PUK_NO = ?, SIM_TYPE = ?, CAPACITY = ?, TEL_CAPACITY = ?, SMS_CAPACITY = ?, BACKUP = ?, EXP_DATE = ?, EFF_DATE = ?, STATE = ?, RESOURCE_STATE = ?, STORAGE_ID = ?, SALES_RESOURCE_ID = ?, RESOURCE_LEVEL = ?, RESOURCE_INSTANCE_CODE = ?, ESN = ?, IMSI_ID = ?, DN_NO = ?, IMSI_ID2 = ?, DN_NO2 = ? ,C_SERV_TYPE_NAME =?,ACC=?,SMSP=?,SIM_CHIP_TYPE = ? WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE = "DELETE FROM RC_SIM WHERE RESOURCE_INSTANCE_ID = ? ";
    private String SQL_DELETE_BY_WHERE = "DELETE FROM RC_SIM ";

    public RcSimDAOImpl() {
    }

    public RcSimVO findByPrimaryKey(String pRESOURCE_INSTANCE_ID)
        throws DAOSystemException {
        ArrayList arrayList = findBySql(SQL_SELECT +
                " and RESOURCE_INSTANCE_ID = ? ",
                new String[] { pRESOURCE_INSTANCE_ID });

        if (arrayList.size() > 0) {
            return (RcSimVO) arrayList.get(0);
        } else {
            return (RcSimVO) getEmptyVO();
        }
    }

    /**
     * 根据sim卡号选择该sim卡实体
     * @param code String
     * @throws DAOSystemException
     * @return RcSimVO
     */
    public RcSimVO findByCode(String code) throws DAOSystemException {
        List arrayList = findByCond(" and RESOURCE_INSTANCE_CODE = '" +
                DAOUtils.filterQureyCond(code) + "'");

        if (arrayList.size() > 0) {
            return (RcSimVO) arrayList.get(0);
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
            RcSimVO vo = new RcSimVO();
            populateDto(vo, rs);
            resultList.add(vo);
        }

        return resultList;
    }

    protected void populateDto(RcSimVO vo, ResultSet rs)
        throws SQLException {
        int count = rs.getMetaData().getColumnCount();
        vo.setCardmill(DAOUtils.trimStr(rs.getString("CARDMILL")));
        vo.setInitTime(DAOUtils.getFormatedDateTime(rs.getDate("INIT_TIME")));
        vo.setPukNo(DAOUtils.trimStr(rs.getString("PUK_NO")));
        vo.setSimType(DAOUtils.trimStr(rs.getString("SIM_TYPE")));
        vo.setCapacity(DAOUtils.trimStr(rs.getString("CAPACITY")));
        vo.setTelCapacity(DAOUtils.trimStr(rs.getString("TEL_CAPACITY")));
        vo.setSmsCapacity(DAOUtils.trimStr(rs.getString("SMS_CAPACITY")));
        vo.setBackup(DAOUtils.trimStr(rs.getString("BACKUP")));
        vo.setExpDate(DAOUtils.getFormatedDateTime(rs.getDate("EXP_DATE")));
        vo.setEffDate(DAOUtils.getFormatedDateTime(rs.getDate("EFF_DATE")));
        vo.setState(DAOUtils.trimStr(rs.getString("STATE")));
        vo.setRescState(DAOUtils.trimStr(rs.getString("RESOURCE_STATE")));
        vo.setStorageId(DAOUtils.trimStr(rs.getString("STORAGE_ID")));
        vo.setSalesRescId(DAOUtils.trimStr(rs.getString("SALES_RESOURCE_ID")));
        vo.setRescLevel(DAOUtils.trimStr(rs.getString("RESOURCE_LEVEL")));
        vo.setRescInstanceCode(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_CODE")));
        vo.setRescInstanceId(DAOUtils.trimStr(rs.getString(
                    "RESOURCE_INSTANCE_ID")));
        vo.setWlanPwd(DAOUtils.trimStr(rs.getString("WLAN_pwd")));
        vo.setWlanAcct(DAOUtils.trimStr(rs.getString("WLAN_acct")));
        vo.setMsisdn(DAOUtils.trimStr(rs.getString("MSISDN")));
        vo.setKi(DAOUtils.trimStr(rs.getString("KI")));
        vo.setPin2(DAOUtils.trimStr(rs.getString("pin2")));
        vo.setPin1(DAOUtils.trimStr(rs.getString("pin1")));
        vo.setPuk2No(DAOUtils.trimStr(rs.getString("puk2_no")));
        vo.setEsn(DAOUtils.trimStr(rs.getString("ESN")));
        vo.setImsiId(DAOUtils.trimStr(rs.getString("IMSI_ID")));
        vo.setDnNo(DAOUtils.trimStr(rs.getString("DN_NO")));
        vo.setImsiId2(DAOUtils.trimStr(rs.getString("IMSI_ID2")));
        vo.setDnNo2(DAOUtils.trimStr(rs.getString("DN_NO2")));
        vo.setUmid(DAOUtils.trimStr(rs.getString("UMID")));
        vo.setServCode(DAOUtils.trimStr(rs.getString("SERV_CODE")));
        vo.setServName(DAOUtils.trimStr(rs.getString("SERV_NAME")));
        vo.setCardType(DAOUtils.trimStr(rs.getString("CARD_TYPE")));
        vo.setCServType(DAOUtils.trimStr(rs.getString("C_SERV_TYPE")));
        vo.setCServTypeName(DAOUtils.trimStr(rs.getString("C_SERV_TYPE_NAME")));
        vo.setAcc(DAOUtils.trimStr(rs.getString("ACC")));
        vo.setSmsp(DAOUtils.trimStr(rs.getString("SMSP")));
        vo.setSimChipType(DAOUtils.trimStr(rs.getString("SIM_CHIP_TYPE")));

        if (count > 38) {
            vo.setStorageName(DAOUtils.trimStr(rs.getString("STORAGE_NAME")));
        }
    }

    public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
        RcSimVO vo = new RcSimVO();

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
            SQL = SQL_SELECT + whereCond;
            //System.out.println(">>>"+SQL);
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

            if ("".equals(((RcSimVO) vo).getAccept_id())) {
                ((RcSimVO) vo).setAccept_id(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getAccept_id());

            if ("".equals(((RcSimVO) vo).getCardmill())) {
                ((RcSimVO) vo).setCardmill(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCardmill());
            stmt.setTimestamp(index++,
                DAOUtils.parseTimestamp(((RcSimVO) vo).getInitTime()));
            stmt.setString(index++, ((RcSimVO) vo).getPukNo());
            stmt.setString(index++, ((RcSimVO) vo).getSimType());

            if ("".equals(((RcSimVO) vo).getCapacity())) {
                ((RcSimVO) vo).setCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCapacity());

            if ("".equals(((RcSimVO) vo).getTelCapacity())) {
                ((RcSimVO) vo).setTelCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getTelCapacity());

            if ("".equals(((RcSimVO) vo).getSmsCapacity())) {
                ((RcSimVO) vo).setSmsCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSmsCapacity());
            stmt.setString(index++, ((RcSimVO) vo).getBackup());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getExpDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getEffDate()));
            stmt.setString(index++, ((RcSimVO) vo).getState());
            stmt.setString(index++, ((RcSimVO) vo).getRescState());

            if ("".equals(((RcSimVO) vo).getStorageId())) {
                ((RcSimVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getStorageId());

            if ("".equals(((RcSimVO) vo).getSalesRescId())) {
                ((RcSimVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSalesRescId());

            if ("".equals(((RcSimVO) vo).getRescLevel())) {
                ((RcSimVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescLevel());
            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceCode());

            if ("".equals(((RcSimVO) vo).getRescInstanceId())) {
                ((RcSimVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceId());

            if ("".equals(((RcSimVO) vo).getWlanPwd())) {
                ((RcSimVO) vo).setWlanPwd(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getWlanPwd());

            if ("".equals(((RcSimVO) vo).getWlanAcct())) {
                ((RcSimVO) vo).setWlanAcct(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getWlanAcct());

            if ("".equals(((RcSimVO) vo).getMsisdn())) {
                ((RcSimVO) vo).setMsisdn(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getMsisdn());

            if ("".equals(((RcSimVO) vo).getKi())) {
                ((RcSimVO) vo).setKi(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getKi());

            if ("".equals(((RcSimVO) vo).getPin2())) {
                ((RcSimVO) vo).setPin2(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getPin2());

            if ("".equals(((RcSimVO) vo).getPin1())) {
                ((RcSimVO) vo).setPin1(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getPin1());

            if ("".equals(((RcSimVO) vo).getPuk2No())) {
                ((RcSimVO) vo).setPuk2No(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getPuk2No());
            stmt.setString(index++, ((RcSimVO) vo).getEsn());

            if ("".equals(((RcSimVO) vo).getImsiId())) {
                ((RcSimVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getImsiId());
            stmt.setString(index++, ((RcSimVO) vo).getDnNo());

            if ("".equals(((RcSimVO) vo).getImsiId2())) {
                ((RcSimVO) vo).setImsiId2(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getImsiId2());
            stmt.setString(index++, ((RcSimVO) vo).getDnNo2());

            if ("".equals(((RcSimVO) vo).getCServTypeName())) {
                ((RcSimVO) vo).setCServTypeName(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCServTypeName());
            stmt.setString(index++, ((RcSimVO) vo).getAcc());
            stmt.setString(index++, ((RcSimVO) vo).getSmsp());

            if ((((RcSimVO) vo).getSimChipType() == null) ||
                    (((RcSimVO) vo).getSimChipType().length() <= 0)) {
                ((RcSimVO) vo).setSimChipType("0");
            }

            stmt.setString(index++, ((RcSimVO) vo).getSimChipType());

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

    public boolean updateStorage(String storageId, String conditon)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append("UPDATE RC_SIM SET storage_id=? ");
            sql.append(" WHERE  ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));
            stmt.setString(1, storageId);
            stmt.executeUpdate();
        } catch (SQLException se) {
            Debug.print(sql.toString(), this);
            throw new DAOSystemException("SQLException while update sql:\n" +
                sql.toString(), se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        return true;
    }

    public boolean update(String pRESOURCE_INSTANCE_ID, RcSimVO vo)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_SIM SET ACCEPT_ID = ?,CARDMILL = ?,INIT_TIME = ?,PUK_NO = ?,SIM_TYPE = ?,CAPACITY = ?,TEL_CAPACITY = ?,SMS_CAPACITY = ?,BACKUP = ?,EXP_DATE = ?,EFF_DATE = ?,STATE = ?,RESOURCE_STATE = ?,STORAGE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_LEVEL = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_INSTANCE_ID = ?,WLAN_pwd = ?,WLAN_acct = ?,MSISDN = ?,KI=?,pin2=?,pin1=?,puk2_no=?,ESN = ?,IMSI_ID = ?,DN_NO = ?,IMSI_ID2 = ?,DN_NO2 = ?,C_SERV_TYPE_NAME=?,ACC=?,SMSP=?,SIM_CHIP_TYPE=? ");
            sql.append(" WHERE  RESOURCE_INSTANCE_ID = ? ");
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcSimVO) vo).getAccept_id())) {
                ((RcSimVO) vo).setAccept_id(null);
            }

            stmt.setString(index++, vo.getAccept_id());

            if ("".equals(((RcSimVO) vo).getCardmill())) {
                ((RcSimVO) vo).setCardmill(null);
            }

            stmt.setString(index++, vo.getCardmill());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getInitTime()));
            stmt.setString(index++, vo.getPukNo());
            stmt.setString(index++, vo.getSimType());

            if ("".equals(((RcSimVO) vo).getCapacity())) {
                ((RcSimVO) vo).setCapacity(null);
            }

            stmt.setString(index++, vo.getCapacity());

            if ("".equals(((RcSimVO) vo).getTelCapacity())) {
                ((RcSimVO) vo).setTelCapacity(null);
            }

            stmt.setString(index++, vo.getTelCapacity());

            if ("".equals(((RcSimVO) vo).getSmsCapacity())) {
                ((RcSimVO) vo).setSmsCapacity(null);
            }

            stmt.setString(index++, vo.getSmsCapacity());
            stmt.setString(index++, vo.getBackup());
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getExpDate()));
            stmt.setDate(index++, DAOUtils.parseDateTime(vo.getEffDate()));
            stmt.setString(index++, vo.getState());
            stmt.setString(index++, vo.getRescState());

            if ("".equals(((RcSimVO) vo).getStorageId())) {
                ((RcSimVO) vo).setStorageId(null);
            }

            stmt.setString(index++, vo.getStorageId());

            if ("".equals(((RcSimVO) vo).getSalesRescId())) {
                ((RcSimVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, vo.getSalesRescId());

            if ("".equals(((RcSimVO) vo).getRescLevel())) {
                ((RcSimVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, vo.getRescLevel());
            stmt.setString(index++, vo.getRescInstanceCode());

            if ("".equals(((RcSimVO) vo).getRescInstanceId())) {
                ((RcSimVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, vo.getRescInstanceId());

            if ("".equals(((RcSimVO) vo).getWlanPwd())) {
                ((RcSimVO) vo).setWlanPwd(null);
            }

            stmt.setString(index++, vo.getWlanPwd());

            if ("".equals(((RcSimVO) vo).getWlanAcct())) {
                ((RcSimVO) vo).setWlanAcct(null);
            }

            stmt.setString(index++, vo.getWlanAcct());

            if ("".equals(((RcSimVO) vo).getMsisdn())) {
                ((RcSimVO) vo).setMsisdn(null);
            }

            stmt.setString(index++, vo.getMsisdn());

            if ("".equals(((RcSimVO) vo).getKi())) {
                ((RcSimVO) vo).setKi(null);
            }

            stmt.setString(index++, vo.getKi());

            if ("".equals(((RcSimVO) vo).getPin2())) {
                ((RcSimVO) vo).setPin2(null);
            }

            stmt.setString(index++, vo.getPin2());

            if ("".equals(((RcSimVO) vo).getPin1())) {
                ((RcSimVO) vo).setPin1(null);
            }

            stmt.setString(index++, vo.getPin1());

            if ("".equals(((RcSimVO) vo).getPuk2No())) {
                ((RcSimVO) vo).setPuk2No(null);
            }

            stmt.setString(index++, vo.getPuk2No());
            stmt.setString(index++, vo.getEsn());

            if ("".equals(((RcSimVO) vo).getImsiId())) {
                ((RcSimVO) vo).setImsiId(null);
            }

            stmt.setString(index++, vo.getImsiId());
            stmt.setString(index++, vo.getDnNo());

            if ("".equals(((RcSimVO) vo).getImsiId2())) {
                ((RcSimVO) vo).setImsiId2(null);
            }

            stmt.setString(index++, vo.getImsiId2());
            stmt.setString(index++, vo.getDnNo2());

            if ("".equals(((RcSimVO) vo).getCServTypeName())) {
                ((RcSimVO) vo).setCServTypeName(null);
            }

            stmt.setString(index++, vo.getCServTypeName());
            stmt.setString(index++, vo.getAcc());
            stmt.setString(index++, vo.getSmsp());
            stmt.setString(index++, vo.getSimChipType());
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

    public boolean update(String whereCond, VO vo) throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            sql.append(
                "UPDATE RC_SIM SET CARDMILL = ?,INIT_TIME = ?,PUK_NO = ?,SIM_TYPE = ?,CAPACITY = ?,TEL_CAPACITY = ?,SMS_CAPACITY = ?,BACKUP = ?,EXP_DATE = ?,EFF_DATE = ?,STATE = ?,RESOURCE_STATE = ?,STORAGE_ID = ?,SALES_RESOURCE_ID = ?,RESOURCE_LEVEL = ?,RESOURCE_INSTANCE_CODE = ?,RESOURCE_INSTANCE_ID = ?,WLAN_PWD = ?,WLAN_ACCT = ?,MSISDN = ?,KI = ?,PIN2 = ?,PIN1 = ?,PUK2_NO = ?,ESN = ?,IMSI_ID = ?,DN_NO = ?,IMSI_ID2 = ?,DN_NO2 = ?,ACC=?,SMSP=?");
            sql.append(" WHERE  1=1 " + whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(
                        sql.toString()));

            int index = 1;

            if ("".equals(((RcSimVO) vo).getCardmill())) {
                ((RcSimVO) vo).setCardmill(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCardmill());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getInitTime()));
            stmt.setString(index++, ((RcSimVO) vo).getPukNo());
            stmt.setString(index++, ((RcSimVO) vo).getSimType());

            if ("".equals(((RcSimVO) vo).getCapacity())) {
                ((RcSimVO) vo).setCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getCapacity());

            if ("".equals(((RcSimVO) vo).getTelCapacity())) {
                ((RcSimVO) vo).setTelCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getTelCapacity());

            if ("".equals(((RcSimVO) vo).getSmsCapacity())) {
                ((RcSimVO) vo).setSmsCapacity(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSmsCapacity());
            stmt.setString(index++, ((RcSimVO) vo).getBackup());
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getExpDate()));
            stmt.setDate(index++,
                DAOUtils.parseDateTime(((RcSimVO) vo).getEffDate()));
            stmt.setString(index++, ((RcSimVO) vo).getState());
            stmt.setString(index++, ((RcSimVO) vo).getRescState());

            if ("".equals(((RcSimVO) vo).getStorageId())) {
                ((RcSimVO) vo).setStorageId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getStorageId());

            if ("".equals(((RcSimVO) vo).getSalesRescId())) {
                ((RcSimVO) vo).setSalesRescId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getSalesRescId());

            if ("".equals(((RcSimVO) vo).getRescLevel())) {
                ((RcSimVO) vo).setRescLevel(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescLevel());
            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceCode());

            if ("".equals(((RcSimVO) vo).getRescInstanceId())) {
                ((RcSimVO) vo).setRescInstanceId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getRescInstanceId());
            stmt.setString(index++, ((RcSimVO) vo).getWlanPwd());
            stmt.setString(index++, ((RcSimVO) vo).getWlanAcct());
            stmt.setString(index++, ((RcSimVO) vo).getMsisdn());
            stmt.setString(index++, ((RcSimVO) vo).getKi());
            stmt.setString(index++, ((RcSimVO) vo).getPin2());
            stmt.setString(index++, ((RcSimVO) vo).getPin1());
            stmt.setString(index++, ((RcSimVO) vo).getPuk2No());
            stmt.setString(index++, ((RcSimVO) vo).getEsn());

            if ("".equals(((RcSimVO) vo).getImsiId())) {
                ((RcSimVO) vo).setImsiId(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getImsiId());
            stmt.setString(index++, ((RcSimVO) vo).getDnNo());

            if ("".equals(((RcSimVO) vo).getImsiId2())) {
                ((RcSimVO) vo).setImsiId2(null);
            }

            stmt.setString(index++, ((RcSimVO) vo).getImsiId2());
            stmt.setString(index++, ((RcSimVO) vo).getDnNo2());
            stmt.setString(index++, ((RcSimVO) vo).getAcc());
            stmt.setString(index++, ((RcSimVO) vo).getSmsp());

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
            SQL = SQL_SELECT_COUNT_EX + whereCond;
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

    public void setMaxRows(int maxRows) {
        this.maxRows = maxRows;
    }

    public VO getEmptyVO() {
        return new RcSimVO();
    }

    public List findByCond(String whereCond, QueryFilter queryFilter)
        throws DAOSystemException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String SQL = SQL_SELECT_EX + whereCond;
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

    //        public List getIMSIId(String rescInstanceId) throws DAOSystemException{
    //
    //          String sql = "SELECT IMSI_ID,IMSI_SEG_ID,RESOURCE_INSTANCE_ID,STATE,MASTER_FLAG FROM RC_IMSI WHERE RESOURCE_INSTANCE_ID="+rescInstanceId;
    //
    //        }

    /**
     * 查询iccid号是否已经是双芯卡
     * @param iccid
     * @return String:-1该iccid在sim卡中不存在或操作员没有权限操作该iccid号；
     * -2该iccid在双芯表中已存在;大于0代表iccid在双芯表中还不存在，返回的是该iccid号对应的imsi_id；
     */
    public String isAlreadyExist(String iccid, String operId, String departId) {
        if ((iccid == null) || (iccid.trim().length() < 1) || (operId == null) ||
                (operId.trim().length() < 1) || (departId == null) ||
                (departId.trim().length() < 1)) {
            return "-1";
        }

        String sql1 = "select a.imsi_id,a.sim_chip_type from rc_sim a where a.RESOURCE_INSTANCE_CODE=?" +
            " and exists(select b.storage_id from mp_storage b where a.storage_id=b.storage_id and b.oper_id=? " +
            " union all select c.storage_Id from STORAGE_DEPART_RELA c where c.storage_id=a.storage_id and c.depart_id=?) ";
        String ret = "-1";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql1));
            stmt.setString(1, iccid);
            stmt.setString(2, operId);
            stmt.setString(3, departId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String type = rs.getString("sim_chip_type");
                String imsi = rs.getString("imsi_id");

                if ((type != null) && (type.equals("3") || type.equals("4"))) {
                    ret = "-2";
                } else {
                    ret = imsi;
                }
            }

            return ret; //-1:不存在或者没有权限；-2：已经是双芯卡；否则：存在、有权限而且不是双芯卡
        } catch (SQLException se) {
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql1, se);
        } finally {
            DAOUtils.closeResultSet(rs, this);
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 根据sim卡resource_instance_code更新sim_chip_type字段的值
     * @param resource_instance_code,sim_chip_type
     * @return
     */
    public void updateSimChipType(String simCodeCorr, String simChipType) {
        String sql1 = "update rc_sim set sim_chip_type = ? where  resource_instance_code = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql1));
            stmt.setString(1, simChipType);
            stmt.setString(2, simCodeCorr);
            stmt.executeUpdate();
        } catch (SQLException se) {
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql1, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }
    }

    /**
     * 批量插入双芯卡
     * @param list:数组的集合：
     * @return
     */
    public int updateBatchSimChipType(List list, String simChipType) {
        if ((list == null) || (list.size() < 1)) {
            return 0;
        }

        int rows = 0;
        int[] resultArr = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql1 = "update rc_sim set sim_chip_type = ? where  resource_instance_code = ?";

        try {
            conn = ConnectionContext.getContext()
                                    .getConnection(JNDINames.CRM_RCDB);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql1));

            for (int i = 0; i < list.size(); i++) {
                int index = 1;
                String param = (String) list.get(i);
                stmt.setString(index++, simChipType);
                stmt.setString(index++, param);
                stmt.addBatch();
            }

            resultArr = stmt.executeBatch();
        } catch (SQLException se) {
            throw new DAOSystemException("SQLException while getting sql:\n" +
                sql1, se);
        } finally {
            DAOUtils.closeStatement(stmt, this);
            DAOUtils.closeConnection(conn, this);
        }

        if (resultArr != null) {
            rows = resultArr.length;
        }

        return rows;
    }
}
