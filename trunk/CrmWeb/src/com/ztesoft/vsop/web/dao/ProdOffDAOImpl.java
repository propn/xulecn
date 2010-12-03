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

public class ProdOffDAOImpl   {

	private String SQL_SELECT = "SELECT PROD_OFFER_ID,FEE_SET_FLAG,PROD_OFFER_SUB_TYPE,PROD_OFFER_NAME,STATE_DATE,PROD_OFFER_PUBLISHER,STATE,EFF_DATE,EXP_DATE,MANAGE_GRADE,OFFER_NBR,OFFER_PROVIDER_ID,BRAND_ID,SERV_BRAND_ID,TEMPLET_ID,DEFAULT_TIME_PERIOD,OFFER_DESC,PRICING_DESC,PNAME_CN,PNAME_EN,PDES_CN,PDES_EN,CHARGINGPOLICY_CN,CHARGINGPOLICY_ID,SUB_OPTION,PRESENT_OPTION,CORP_ONLY,SCOPE,PACKAGE_HOST,OFFER_CODE FROM PROD_OFFER";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PROD_OFFER";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PROD_OFFER ( PROD_OFFER_ID,FEE_SET_FLAG,PROD_OFFER_SUB_TYPE,PROD_OFFER_NAME,STATE_DATE,PROD_OFFER_PUBLISHER,STATE,EFF_DATE,EXP_DATE,MANAGE_GRADE,OFFER_NBR,OFFER_PROVIDER_ID,BRAND_ID,SERV_BRAND_ID,TEMPLET_ID,DEFAULT_TIME_PERIOD,OFFER_DESC,PRICING_DESC,PNAME_CN,PNAME_EN,PDES_CN,PDES_EN,CHARGINGPOLICY_CN,CHARGINGPOLICY_ID,SUB_OPTION,PRESENT_OPTION,CORP_ONLY,SCOPE,PACKAGE_HOST,OFFER_CODE ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PROD_OFFER SET  FEE_SET_FLAG = ?, PROD_OFFER_SUB_TYPE = ?, PROD_OFFER_NAME = ?, STATE_DATE = ?, PROD_OFFER_PUBLISHER = ?, STATE = ?, EFF_DATE = ?, EXP_DATE = ?, MANAGE_GRADE = ?, OFFER_NBR = ?, OFFER_PROVIDER_ID = ?, BRAND_ID = ?, SERV_BRAND_ID = ?, TEMPLET_ID = ?, DEFAULT_TIME_PERIOD = ?, OFFER_DESC = ?, PRICING_DESC = ?, PNAME_CN = ?, PNAME_EN = ?, PDES_CN = ?, PDES_EN = ?, CHARGINGPOLICY_CN = ?, CHARGINGPOLICY_ID = ?, SUB_OPTION = ?, PRESENT_OPTION = ?, CORP_ONLY = ?, SCOPE = ?, PACKAGE_HOST = ?, OFFER_CODE = ? WHERE PROD_OFFER_ID = ? ";

	private String SQL_DELETE = "DELETE FROM PROD_OFFER WHERE PROD_OFFER_ID = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PROD_OFFER ";

	public ProdOffDAOImpl() {

	}

	public ProdOffVO findByPrimaryKey(String pPROD_OFFER_ID) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE PROD_OFFER_ID = ? ", new String[] { pPROD_OFFER_ID } );
		if (arrayList.size()>0)
			return (ProdOffVO)arrayList.get(0);
		else
			return (ProdOffVO) getEmptyVO();
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
			ProdOffVO vo = new ProdOffVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(ProdOffVO vo, ResultSet rs) throws SQLException {
		vo.setProdOffId( DAOUtils.trimStr( rs.getString( "PROD_OFFER_ID" ) ) );
		vo.setFeeSetFlag( DAOUtils.trimStr( rs.getString( "FEE_SET_FLAG" ) ) );
		vo.setProdOffSubType( DAOUtils.trimStr( rs.getString( "PROD_OFFER_SUB_TYPE" ) ) );
		vo.setProdOffName( DAOUtils.trimStr( rs.getString( "PROD_OFFER_NAME" ) ) );
		vo.setStateDate( DAOUtils.getFormatedDateTime( rs.getDate( "STATE_DATE" ) ) );
		vo.setProdOffPublisher( DAOUtils.trimStr( rs.getString( "PROD_OFFER_PUBLISHER" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "STATE" ) ) );
		vo.setEffDate( DAOUtils.getFormatedDateTime( rs.getDate( "EFF_DATE" ) ) );
		vo.setExpDate( DAOUtils.getFormatedDateTime( rs.getDate( "EXP_DATE" ) ) );
		vo.setManageGrade( DAOUtils.trimStr( rs.getString( "MANAGE_GRADE" ) ) );
		vo.setOffNbr( DAOUtils.trimStr( rs.getString( "OFFER_NBR" ) ) );
		vo.setOffProviderId( DAOUtils.trimStr( rs.getString( "OFFER_PROVIDER_ID" ) ) );
		vo.setBrandId( DAOUtils.trimStr( rs.getString( "BRAND_ID" ) ) );
		vo.setServBrandId( DAOUtils.trimStr( rs.getString( "SERV_BRAND_ID" ) ) );
		vo.setTempletId( DAOUtils.trimStr( rs.getString( "TEMPLET_ID" ) ) );
		vo.setDefaultTimePeriod( DAOUtils.trimStr( rs.getString( "DEFAULT_TIME_PERIOD" ) ) );
		vo.setOffDesc( DAOUtils.trimStr( rs.getString( "OFFER_DESC" ) ) );
		vo.setPricingDesc( DAOUtils.trimStr( rs.getString( "PRICING_DESC" ) ) );
		vo.setPnameCn( DAOUtils.trimStr( rs.getString( "PNAME_CN" ) ) );
		vo.setPnameEn( DAOUtils.trimStr( rs.getString( "PNAME_EN" ) ) );
		vo.setPdesCn( DAOUtils.trimStr( rs.getString( "PDES_CN" ) ) );
		vo.setPdesEn( DAOUtils.trimStr( rs.getString( "PDES_EN" ) ) );
		vo.setChargingpolicyCn( DAOUtils.trimStr( rs.getString( "CHARGINGPOLICY_CN" ) ) );
		vo.setChargingpolicyId( DAOUtils.trimStr( rs.getString( "CHARGINGPOLICY_ID" ) ) );
		vo.setSubOption( DAOUtils.trimStr( rs.getString( "SUB_OPTION" ) ) );
		vo.setPresentOption( DAOUtils.trimStr( rs.getString( "PRESENT_OPTION" ) ) );
		vo.setCorpOnly( DAOUtils.trimStr( rs.getString( "CORP_ONLY" ) ) );
		vo.setScope( DAOUtils.trimStr( rs.getString( "SCOPE" ) ) );
		vo.setPackageHost( DAOUtils.trimStr( rs.getString( "PACKAGE_HOST" ) ) );
		vo.setOffCode( DAOUtils.trimStr( rs.getString( "OFFER_CODE" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		ProdOffVO vo = new ProdOffVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord出错",this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}
		return vo;
	}
	
	
	public Map findAllProduct2Offer(String whereCond) throws DAOSystemException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn=null;
		String selectSql = "select a.prod_offer_id,b.PRODUCT_ID  "+
					  " from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,PROD_OFFER c "+
					 " where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id=c.prod_offer_id "+
					   "  and c.prod_offer_sub_type='0' ";
		String sql=selectSql + whereCond;
		Map map=new HashMap();
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			rs = ps.executeQuery();
			while(rs.next()){
				map.put(rs.getString("PRODUCT_ID"), rs.getString("prod_offer_id"));
			}
		} catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return map;
	}
	/**
	 * 
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public Map findAllProductOffer2VproductList(String whereCond) throws DAOSystemException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn=null;
		String selectSql = "select a.prod_offer_id,b.PRODUCT_ID  "+
					  " from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,PROD_OFFER c "+
					 " where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id=c.prod_offer_id ";
//		+"  and c.state='0' ";
		String sql=selectSql + whereCond;
		Map offer2VporuductList = new HashMap();
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			rs = ps.executeQuery();
			
			while(rs.next()){
				String productOfferId=rs.getString("prod_offer_id");
				String productId=rs.getString("PRODUCT_ID");
				List vproductList = (List)offer2VporuductList.get(productOfferId);
				if(vproductList == null){
					vproductList = new ArrayList();
					vproductList.add(productId);
					offer2VporuductList.put(productOfferId, vproductList);
				}else{
					vproductList.add(productId);
				}
			}
		} catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return offer2VporuductList;
	}
	
	/**
	 * 找出所有关系，包括销售品各种状态下的
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public Map findAllProductOfferSVproductList(String whereCond) throws DAOSystemException {
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn=null;
		String selectSql = "select a.prod_offer_id,b.PRODUCT_ID  "+
					  " from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,PROD_OFFER c "+
					 " where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id=c.prod_offer_id ";
		String sql=selectSql + whereCond;
		Map offer2VporuductList = new HashMap();
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			rs = ps.executeQuery();
			
			while(rs.next()){
				String productOfferId=rs.getString("prod_offer_id");
				String productId=rs.getString("PRODUCT_ID");
				List vproductList = (List)offer2VporuductList.get(productOfferId);
				if(vproductList == null){
					vproductList = new ArrayList();
					vproductList.add(productId);
					offer2VporuductList.put(productOfferId, vproductList);
				}else{
					vproductList.add(productId);
				}
			}
		} catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while populateDto:\n", se);
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return offer2VporuductList;
	}
	
	/**
	 * 根据产品找基础销售品
	 * @param order
	 * @param conn
	 * @throws SQLException 
	 */
	public String findOfferByProductDb(String productId) throws SQLException {
		String prodOfferId = "";
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection conn=null;
		String sql = "select a.prod_offer_id "+
					  "from PROD_OFFER_DETAIL_ROLE a, ROLE_PROD_RELA b,PROD_OFFER c "+
					 "where a.prod_offer_role_cd = b.prod_offer_role_cd and a.prod_offer_id=c.prod_offer_id "+
					   "and b.product_id =? and c.prod_offer_sub_type='0'";
		try {
			conn = LegacyDAOUtil.getConnection();
			ps = conn.prepareStatement(sql);
			ps.setString(1, productId);
			rs = ps.executeQuery();
			if (rs.next()) {
				prodOfferId = rs.getString("prod_offer_id");
			}
		} catch (SQLException e) {
			throw e;
		}finally{
			DAOUtils.closeResultSet(rs);
			DAOUtils.closeStatement(ps);
		}
		return prodOfferId;
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
		return new ProdOffVO();
	}

}
