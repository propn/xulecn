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

public class ProdSystemInfoDAOImpl   {

	private String SQL_SELECT = "SELECT PARTNER_SYSTEM_INFO_ID,PRODUCT_ID,SYSTEM_CODE,CREATE_DATE,STATE,STATE_DATE FROM PRODUCT_SYSTEM_INFO";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PRODUCT_SYSTEM_INFO";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PRODUCT_SYSTEM_INFO ( PARTNER_SYSTEM_INFO_ID,PRODUCT_ID,SYSTEM_CODE,CREATE_DATE,STATE,STATE_DATE ) VALUES ( ?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PRODUCT_SYSTEM_INFO SET  PRODUCT_ID = ?, SYSTEM_CODE = ?, CREATE_DATE = ?, STATE = ?, STATE_DATE = ? WHERE PARTNER_SYSTEM_INFO_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PRODUCT_SYSTEM_INFO WHERE PARTNER_SYSTEM_INFO_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PRODUCT_SYSTEM_INFO ";

	public ProdSystemInfoDAOImpl() {

	}

	public ProdSystemInfoVO findByPrimaryKey(String pPARTNER_SYSTEM_INFO_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PARTNER_SYSTEM_INFO_ID = ? ", new String[] { pPARTNER_SYSTEM_INFO_ID } );
		if (arrayList.size()>0)
			return (ProdSystemInfoVO)arrayList.get(0);
		else
			return (ProdSystemInfoVO) getEmptyVO();
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
			ProdSystemInfoVO vo = new ProdSystemInfoVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ProdSystemInfoVO vo, ResultSet rs) throws SQLException {
		vo.setPartnerSystemInfoId( DAOUtils.trimStr( rs.getString( "PARTNER_SYSTEM_INFO_ID" ) ) );
		vo.setProdId( DAOUtils.trimStr( rs.getString( "PRODUCT_ID" ) ) );
		vo.setSystemCode( DAOUtils.trimStr( rs.getString( "SYSTEM_CODE" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getDate( "CREATE_DATE" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "STATE" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getDate( "STATE_DATE" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ProdSystemInfoVO vo = new ProdSystemInfoVO();
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
			rs = stmt.executeQuery();
			Map prodMapPlatform = new HashMap();
			//Map platformMapProd = new HashMap();
			
			//ArrayList resultList = new ArrayList();
			while (rs.next()) {
				//ProdSystemInfoVO vo = new ProdSystemInfoVO();
				//populateDto( vo, rs);
				String productId = rs.getString("PRODUCT_ID");
				String platformId = rs.getString("SYSTEM_CODE");
				List prodPlatform = (List)prodMapPlatform.get(productId);
				if(prodPlatform == null){
					prodPlatform = new ArrayList();
					prodPlatform.add(platformId);
					prodMapPlatform.put(productId, prodPlatform);
				}else{
					prodPlatform.add(platformId);
				}
				//resultList.add( vo );
			}

			return prodMapPlatform;
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
		return new ProdSystemInfoVO();
	}


}
