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

public class ProdServAbilityRelDAOImpl{

	private String SQL_SELECT = "SELECT PRODUCT_SERVICE_ABILITY_REL_ID,PRODUCT_ID,SERVICE_CODE,REL_TYPE FROM PRODUCT_SERVICE_ABILITY_REL";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PRODUCT_SERVICE_ABILITY_REL";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PRODUCT_SERVICE_ABILITY_REL ( PRODUCT_SERVICE_ABILITY_REL_ID,PRODUCT_ID,SERVICE_CODE,REL_TYPE ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PRODUCT_SERVICE_ABILITY_REL SET  PRODUCT_ID = ?, SERVICE_CODE = ?, REL_TYPE = ? WHERE PRODUCT_SERVICE_ABILITY_REL_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PRODUCT_SERVICE_ABILITY_REL WHERE PRODUCT_SERVICE_ABILITY_REL_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PRODUCT_SERVICE_ABILITY_REL ";

	public ProdServAbilityRelDAOImpl() {

	}

	public ProdServAbilityRelVO findByPrimaryKey(String pPRODUCT_SERVICE_ABILITY_REL_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PRODUCT_SERVICE_ABILITY_REL_ID = ? ", new String[] { pPRODUCT_SERVICE_ABILITY_REL_ID } );
		if (arrayList.size()>0)
			return (ProdServAbilityRelVO)arrayList.get(0);
		else
			return (ProdServAbilityRelVO) getEmptyVO();
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
			ProdServAbilityRelVO vo = new ProdServAbilityRelVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ProdServAbilityRelVO vo, ResultSet rs) throws SQLException {
		vo.setProdServAbilityRelId( DAOUtils.trimStr( rs.getString( "PRODUCT_SERVICE_ABILITY_REL_ID" ) ) );
		vo.setProdId( DAOUtils.trimStr( rs.getString( "PRODUCT_ID" ) ) );
		vo.setServCode( DAOUtils.trimStr( rs.getString( "SERVICE_CODE" ) ) );
		vo.setRelType( DAOUtils.trimStr( rs.getString( "REL_TYPE" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ProdServAbilityRelVO vo = new ProdServAbilityRelVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í",this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}
		return vo;
	}
	
	public Map findAllServAbility()throws DAOSystemException {
		Connection conn = null ;
		PreparedStatement stmt = null ;
		ResultSet rs = null ;
		Map map=new HashMap();
		String sql = "select SERVICE_CODE,SERVICE_NAME  from SERVICE_ABILITY ";
		//String sql="select  param_code, param_value from ua_config"; 
		try {
			conn = LegacyDAOUtil.getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			while( rs.next() ){
				String key=rs.getString("SERVICE_CODE");
				String value=rs.getString("SERVICE_NAME");
				map.put(key, value);
			}
		} catch (SQLException se) {
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
		}
		return map;
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
			
			ArrayList resultList = new ArrayList();
			Map prodMapServiceAbility = new HashMap();
			rs = stmt.executeQuery();
			while (rs.next()) {
				String productId = rs.getString("PRODUCT_ID");
				String serviceCode = rs.getString("SERVICE_CODE");
				List prodServiceAbilitys = (List)prodMapServiceAbility.get(productId);
				if(prodServiceAbilitys == null){
					prodServiceAbilitys = new ArrayList();
					prodServiceAbilitys.add(serviceCode);
					prodMapServiceAbility.put(productId, prodServiceAbilitys);
				}else{
					prodServiceAbilitys.add(serviceCode);
				}
			}
			return prodMapServiceAbility;
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
		return new ProdServAbilityRelVO();
	}

}
