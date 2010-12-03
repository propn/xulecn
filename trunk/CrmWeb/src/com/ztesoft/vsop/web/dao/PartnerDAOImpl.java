package com.ztesoft.vsop.web.dao;

import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.dao.*;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.util.tracer.Debug;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.web.vo.*;
import com.ztesoft.vsop.web.dao.*;

public class PartnerDAOImpl  {

	private String SQL_SELECT = "SELECT PARTNER_ID,PARTNER_CODE,PARTNER_TYPE,PARTNER_DESC,STATE,STATE_DATE,PARTNER_LEVEL,PARTNER_NAME,PARTNER_PASSWORD,PARTNER_AREA_CODE,PARTNER_URL,PARTNER_IP,CREATE_DATE,PARTNER_ENG_NAME,PARTNER_ENG_DESC,SETTLE_CYCLE,SETTLE_PAY_METHOD,SETTLE_RATE,CUST_SERVICE_PHONE,IF_ROAM,COMPANY_ADDRESS,ARTIFICIAL_PERSON,PRIMARY_PERSON_NAME,PRIMARY_PERSON_PHONE,PRIMARY_PERSON_EMAIL,BUSINESS_LICENSE,CONTRACT_EXP_DATE,COMPANY_CODE,PARTNER_NUMBER,CUST_SERVICE_URL,SYSTEM_CODE FROM PARTNER";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER ( PARTNER_ID,PARTNER_CODE,PARTNER_TYPE,PARTNER_DESC,STATE,STATE_DATE,PARTNER_LEVEL,PARTNER_NAME,PARTNER_PASSWORD,PARTNER_AREA_CODE,PARTNER_URL,PARTNER_IP,CREATE_DATE,PARTNER_ENG_NAME,PARTNER_ENG_DESC,SETTLE_CYCLE,SETTLE_PAY_METHOD,SETTLE_RATE,CUST_SERVICE_PHONE,IF_ROAM,COMPANY_ADDRESS,ARTIFICIAL_PERSON,PRIMARY_PERSON_NAME,PRIMARY_PERSON_PHONE,PRIMARY_PERSON_EMAIL,BUSINESS_LICENSE,CONTRACT_EXP_DATE,COMPANY_CODE,PARTNER_NUMBER,CUST_SERVICE_URL,SYSTEM_CODE ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER SET  PARTNER_CODE = ?, PARTNER_TYPE = ?, PARTNER_DESC = ?, STATE = ?, STATE_DATE = ?, PARTNER_LEVEL = ?, PARTNER_NAME = ?, PARTNER_PASSWORD = ?, PARTNER_AREA_CODE = ?, PARTNER_URL = ?, PARTNER_IP = ?, CREATE_DATE = ?, PARTNER_ENG_NAME = ?, PARTNER_ENG_DESC = ?, SETTLE_CYCLE = ?, SETTLE_PAY_METHOD = ?, SETTLE_RATE = ?, CUST_SERVICE_PHONE = ?, IF_ROAM = ?, COMPANY_ADDRESS = ?, ARTIFICIAL_PERSON = ?, PRIMARY_PERSON_NAME = ?, PRIMARY_PERSON_PHONE = ?, PRIMARY_PERSON_EMAIL = ?, BUSINESS_LICENSE = ?, CONTRACT_EXP_DATE = ?, COMPANY_CODE = ?, PARTNER_NUMBER = ?, CUST_SERVICE_URL = ?, SYSTEM_CODE = ? WHERE PARTNER_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER WHERE PARTNER_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER ";

	public PartnerDAOImpl() {

	}

	public PartnerVO findByPrimaryKey(String pPARTNER_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PARTNER_ID = ? ", new String[] { pPARTNER_ID } );
		if (arrayList.size()>0)
			return (PartnerVO)arrayList.get(0);
		else
			return (PartnerVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = LegacyDAOUtil.getConnection();
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql) );
			stmt.setMaxRows( maxRows );
			for (int i=0; sqlParams!=null && i<sqlParams.length; i++ ) {
				stmt.setString( i+1, sqlParams[i] );
			}

			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		}
		catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			PartnerVO vo = new PartnerVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerVO vo, ResultSet rs) throws SQLException {
		vo.setPartnerId( DAOUtils.trimStr( rs.getString( "PARTNER_ID" ) ) );
		vo.setPartnerCode( DAOUtils.trimStr( rs.getString( "PARTNER_CODE" ) ) );
		vo.setPartnerType( DAOUtils.trimStr( rs.getString( "PARTNER_TYPE" ) ) );
		vo.setPartnerDesc( DAOUtils.trimStr( rs.getString( "PARTNER_DESC" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "STATE" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getDate( "STATE_DATE" ) ) );
		vo.setPartnerLevel( DAOUtils.trimStr( rs.getString( "PARTNER_LEVEL" ) ) );
		vo.setPartnerName( DAOUtils.trimStr( rs.getString( "PARTNER_NAME" ) ) );
		vo.setPartnerPassword( DAOUtils.trimStr( rs.getString( "PARTNER_PASSWORD" ) ) );
		vo.setPartnerAreaCode( DAOUtils.trimStr( rs.getString( "PARTNER_AREA_CODE" ) ) );
		vo.setPartnerUrl( DAOUtils.trimStr( rs.getString( "PARTNER_URL" ) ) );
		vo.setPartnerIp( DAOUtils.trimStr( rs.getString( "PARTNER_IP" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getDate( "CREATE_DATE" ) ) );
		vo.setPartnerEngName( DAOUtils.trimStr( rs.getString( "PARTNER_ENG_NAME" ) ) );
		vo.setPartnerEngDesc( DAOUtils.trimStr( rs.getString( "PARTNER_ENG_DESC" ) ) );
		vo.setSettleCycle( DAOUtils.trimStr( rs.getString( "SETTLE_CYCLE" ) ) );
		vo.setSettlePayMethod( DAOUtils.trimStr( rs.getString( "SETTLE_PAY_METHOD" ) ) );
		vo.setSettleRate( DAOUtils.trimStr( rs.getString( "SETTLE_RATE" ) ) );
		vo.setCustServPhone( DAOUtils.trimStr( rs.getString( "CUST_SERVICE_PHONE" ) ) );
		vo.setIfRoam( DAOUtils.trimStr( rs.getString( "IF_ROAM" ) ) );
		vo.setCompanyAddr( DAOUtils.trimStr( rs.getString( "COMPANY_ADDRESS" ) ) );
		vo.setArtificialPerson( DAOUtils.trimStr( rs.getString( "ARTIFICIAL_PERSON" ) ) );
		vo.setPrimaryPersonName( DAOUtils.trimStr( rs.getString( "PRIMARY_PERSON_NAME" ) ) );
		vo.setPrimaryPersonPhone( DAOUtils.trimStr( rs.getString( "PRIMARY_PERSON_PHONE" ) ) );
		vo.setPrimaryPersonEmail( DAOUtils.trimStr( rs.getString( "PRIMARY_PERSON_EMAIL" ) ) );
		vo.setBusinessLicense( DAOUtils.trimStr( rs.getString( "BUSINESS_LICENSE" ) ) );
		vo.setContractExpDate( DAOUtils.getFormatedDateTime( rs.getDate( "CONTRACT_EXP_DATE" ) ) );
		vo.setCompanyCode( DAOUtils.trimStr( rs.getString( "COMPANY_CODE" ) ) );
		vo.setPartnerNumber( DAOUtils.trimStr( rs.getString( "PARTNER_NUMBER" ) ) );
		vo.setCustServUrl( DAOUtils.trimStr( rs.getString( "CUST_SERVICE_URL" ) ) );
		vo.setSystemCode( DAOUtils.trimStr( rs.getString( "SYSTEM_CODE" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerVO vo = new PartnerVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í",this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}
		return vo;
	}

	public Map findByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = LegacyDAOUtil.getConnection();
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			stmt.setMaxRows( maxRows );
			Map partnerMaps = new HashMap();
			rs = stmt.executeQuery();
			while (rs.next()) {
				PartnerVO vo = new PartnerVO();
				populateDto( vo, rs);
				partnerMaps.put(vo.getPartnerId(), vo);
			}

			return partnerMaps;
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public VO getEmptyVO() {
		return new PartnerVO();
	}

}
