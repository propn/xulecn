package com.ztesoft.vsop.web.dao;

import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.dao.*;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.common.util.tracer.Debug;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.vsop.LegacyDAOUtil;
import com.ztesoft.vsop.web.vo.*;
import com.ztesoft.vsop.web.dao.*;

public class ProdDAOImpl  {

	private String SQL_SELECT = "SELECT PRODUCT_ID,PRODUCT_NBR,PRODUCT_NAME,PRODUCT_DESC,MANAGE_GRADE,PROD_BUNDLE_TYPE,PRODUCT_PROVIDER_ID,PRODUCT_STATE_CD,STATE_DATE,EFF_DATE,EXP_DATE,CREATE_DATE,PROD_FUNC_TYPE,ORDER_HOST,CHARGING_POLICY_CN,CHARGING_POLICY_ID,SUB_OPTION,PRESENT,CORP_ONLY,PACKAGE_ONLY,SETTLEMENT_CYCLE,SETTLEMENT_PAYTYPE,SETTLEMENT_PERCENT,SCOPE,SYSTEM_CODE,PROD_OFFER_ID,PROD_TYPE,PRODUCT_CODE,PROD_CLASS FROM PRODUCT";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PRODUCT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PRODUCT ( PRODUCT_ID,PRODUCT_NBR,PRODUCT_NAME,PRODUCT_DESC,MANAGE_GRADE,PROD_BUNDLE_TYPE,PRODUCT_PROVIDER_ID,PRODUCT_STATE_CD,STATE_DATE,EFF_DATE,EXP_DATE,CREATE_DATE,PROD_FUNC_TYPE,ORDER_HOST,CHARGING_POLICY_CN,CHARGING_POLICY_ID,SUB_OPTION,PRESENT,CORP_ONLY,PACKAGE_ONLY,SETTLEMENT_CYCLE,SETTLEMENT_PAYTYPE,SETTLEMENT_PERCENT,SCOPE,SYSTEM_CODE,PROD_OFFER_ID,PROD_TYPE,PRODUCT_CODE,PROD_CLASS ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PRODUCT SET  PRODUCT_NBR = ?, PRODUCT_NAME = ?, PRODUCT_DESC = ?, MANAGE_GRADE = ?, PROD_BUNDLE_TYPE = ?, PRODUCT_PROVIDER_ID = ?, PRODUCT_STATE_CD = ?, STATE_DATE = ?, EFF_DATE = ?, EXP_DATE = ?, CREATE_DATE = ?, PROD_FUNC_TYPE = ?, ORDER_HOST = ?, CHARGING_POLICY_CN = ?, CHARGING_POLICY_ID = ?, SUB_OPTION = ?, PRESENT = ?, CORP_ONLY = ?, PACKAGE_ONLY = ?, SETTLEMENT_CYCLE = ?, SETTLEMENT_PAYTYPE = ?, SETTLEMENT_PERCENT = ?, SCOPE = ?, SYSTEM_CODE = ?, PROD_OFFER_ID = ?, PROD_TYPE = ?, PRODUCT_CODE = ?, PROD_CLASS = ? WHERE PRODUCT_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PRODUCT WHERE PRODUCT_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PRODUCT ";

	public ProdDAOImpl() {

	}

	public ProdVO findByPrimaryKey(String pPRODUCT_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PRODUCT_ID = ? ", new String[] { pPRODUCT_ID } );
		if (arrayList.size()>0)
			return (ProdVO)arrayList.get(0);
		else
			return (ProdVO) getEmptyVO();
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
			//DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			ProdVO vo = new ProdVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ProdVO vo, ResultSet rs) throws SQLException {
		vo.setProdId( DAOUtils.trimStr( rs.getString( "PRODUCT_ID" ) ) );
		vo.setProdNbr( DAOUtils.trimStr( rs.getString( "PRODUCT_NBR" ) ) );
		vo.setProdName( DAOUtils.trimStr( rs.getString( "PRODUCT_NAME" ) ) );
		vo.setProdDesc( DAOUtils.trimStr( rs.getString( "PRODUCT_DESC" ) ) );
		vo.setManageGrade( DAOUtils.trimStr( rs.getString( "MANAGE_GRADE" ) ) );
		vo.setProdBundleType( DAOUtils.trimStr( rs.getString( "PROD_BUNDLE_TYPE" ) ) );
		vo.setProdProviderId( DAOUtils.trimStr( rs.getString( "PRODUCT_PROVIDER_ID" ) ) );
		vo.setProdStateCd( DAOUtils.trimStr( rs.getString( "PRODUCT_STATE_CD" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getDate( "STATE_DATE" ) ) );
		vo.setEffDate( DAOUtils.getFormatedDateTime( rs.getDate( "EFF_DATE" ) ) );
		vo.setExpDate( DAOUtils.getFormatedDateTime( rs.getDate( "EXP_DATE" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getDate( "CREATE_DATE" ) ) );
		vo.setProdFuncType( DAOUtils.trimStr( rs.getString( "PROD_FUNC_TYPE" ) ) );
		vo.setOrderHost( DAOUtils.trimStr( rs.getString( "ORDER_HOST" ) ) );
		vo.setChargingPolicyCn( DAOUtils.trimStr( rs.getString( "CHARGING_POLICY_CN" ) ) );
		vo.setChargingPolicyId( DAOUtils.trimStr( rs.getString( "CHARGING_POLICY_ID" ) ) );
		vo.setSubOption( DAOUtils.trimStr( rs.getString( "SUB_OPTION" ) ) );
		vo.setPresent( DAOUtils.trimStr( rs.getString( "PRESENT" ) ) );
		vo.setCorpOnly( DAOUtils.trimStr( rs.getString( "CORP_ONLY" ) ) );
		vo.setPackageOnly( DAOUtils.trimStr( rs.getString( "PACKAGE_ONLY" ) ) );
		vo.setSettlementCycle( DAOUtils.trimStr( rs.getString( "SETTLEMENT_CYCLE" ) ) );
		vo.setSettlementPaytype( DAOUtils.trimStr( rs.getString( "SETTLEMENT_PAYTYPE" ) ) );
		vo.setSettlementPercent( DAOUtils.trimStr( rs.getString( "SETTLEMENT_PERCENT" ) ) );
		vo.setScope( DAOUtils.trimStr( rs.getString( "SCOPE" ) ) );
		vo.setSystemCode( DAOUtils.trimStr( rs.getString( "SYSTEM_CODE" ) ) );
		vo.setProdOffId( DAOUtils.trimStr( rs.getString( "PROD_OFFER_ID" ) ) );
		vo.setProdType( DAOUtils.trimStr( rs.getString( "PROD_TYPE" ) ) );
		vo.setProdCode( DAOUtils.trimStr( rs.getString( "PRODUCT_CODE" ) ) );
		vo.setProdClass( DAOUtils.trimStr( rs.getString( "PROD_CLASS" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ProdVO vo = new ProdVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í",this);
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

			conn = LegacyDAOUtil.getConnection();
			SQL = SQL_SELECT + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			stmt.setMaxRows( maxRows );
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
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
		return new ProdVO();
	}

}
