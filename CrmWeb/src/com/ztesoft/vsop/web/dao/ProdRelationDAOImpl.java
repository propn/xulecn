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

public class ProdRelationDAOImpl   {

	private String SQL_SELECT = "SELECT PRODUCT_REL_ID,RELATION_TYPE_CD,PRODUCT_ID,PRO_PRODUCT_ID,STATE_CD,STATE_DATE,CREATE_DATE FROM PRODUCT_RELATION";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PRODUCT_RELATION";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PRODUCT_RELATION ( PRODUCT_REL_ID,RELATION_TYPE_CD,PRODUCT_ID,PRO_PRODUCT_ID,STATE_CD,STATE_DATE,CREATE_DATE ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PRODUCT_RELATION SET  RELATION_TYPE_CD = ?, PRODUCT_ID = ?, PRO_PRODUCT_ID = ?, STATE_CD = ?, STATE_DATE = ?, CREATE_DATE = ? WHERE PRODUCT_REL_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PRODUCT_RELATION WHERE PRODUCT_REL_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PRODUCT_RELATION ";

	public ProdRelationDAOImpl() {

	}

	public ProdRelationVO findByPrimaryKey(String pPRODUCT_REL_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PRODUCT_REL_ID = ? ", new String[] { pPRODUCT_REL_ID } );
		if (arrayList.size()>0)
			return (ProdRelationVO)arrayList.get(0);
		else
			return (ProdRelationVO) getEmptyVO();
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
			ProdRelationVO vo = new ProdRelationVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ProdRelationVO vo, ResultSet rs) throws SQLException {
		vo.setProdRelId( DAOUtils.trimStr( rs.getString( "PRODUCT_REL_ID" ) ) );
		vo.setRelationTypeCd( DAOUtils.trimStr( rs.getString( "RELATION_TYPE_CD" ) ) );
		vo.setProdId( DAOUtils.trimStr( rs.getString( "PRODUCT_ID" ) ) );
		vo.setProProdId( DAOUtils.trimStr( rs.getString( "PRO_PRODUCT_ID" ) ) );
		vo.setStateCd( DAOUtils.trimStr( rs.getString( "STATE_CD" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getDate( "STATE_DATE" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getDate( "CREATE_DATE" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ProdRelationVO vo = new ProdRelationVO();
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
			Map prodRelationMap = new HashMap();
			rs = stmt.executeQuery();
			while (rs.next()) {
				ProdRelationVO vo = new ProdRelationVO();
				populateDto( vo, rs);
				//A¶Ë
				List prodRelations = (List)prodRelationMap.get(vo.getProdId());
				if(prodRelations == null){
					prodRelations = new ArrayList();
					prodRelations.add(vo);
					prodRelationMap.put(vo.getProdId(), prodRelations);
				}else{
					prodRelations.add(vo);
				}
				//Z¶Ë
				List prodRelations2 = (List)prodRelationMap.get(vo.getProProdId());
				if(prodRelations2 == null){
					prodRelations2 = new ArrayList();
					prodRelations2.add(vo);
					prodRelationMap.put(vo.getProProdId(), prodRelations2);
				}else{
					prodRelations2.add(vo);
				}
			}
			return prodRelationMap;
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
		return new ProdRelationVO();
	}

}
