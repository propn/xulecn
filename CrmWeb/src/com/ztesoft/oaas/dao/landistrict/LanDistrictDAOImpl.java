package com.ztesoft.oaas.dao.landistrict;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.dao.SeqDAOFactory;
import com.ztesoft.common.dao.SequenceManageDAO;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.LanDistrictVO;
import com.ztesoft.oaas.vo.RegionVO;

public class LanDistrictDAOImpl   implements LanDistrictDAO {

	private String SQL_SELECT = "SELECT district_id,district_code,district_name,district_type,district_addr,deal_exch,sub_exch FROM LAN_DISTRICT";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM LAN_DISTRICT";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO LAN_DISTRICT ( district_id,district_code,district_name,district_type,district_addr,deal_exch,sub_exch ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE LAN_DISTRICT SET  district_code = ?, district_name = ?, district_type = ?, district_addr = ?, deal_exch = ?, sub_exch = ? WHERE district_id = ? ";

	private String SQL_DELETE = "DELETE FROM LAN_DISTRICT WHERE district_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM LAN_DISTRICT ";

	public LanDistrictDAOImpl() {

	}

	public LanDistrictVO findByPrimaryKey(String pdistrict_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE district_id = ? ", new String[] { pdistrict_id } );
		if (arrayList.size()>0)
			return (LanDistrictVO)arrayList.get(0);
		else
			return (LanDistrictVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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
			LanDistrictVO vo = new LanDistrictVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(LanDistrictVO vo, ResultSet rs) throws SQLException {
		vo.setDistrictId( DAOUtils.trimStr( rs.getString( "district_id" ) ) );
		vo.setDistrictCode( DAOUtils.trimStr( rs.getString( "district_code" ) ) );
		vo.setDistrictName( DAOUtils.trimStr( rs.getString( "district_name" ) ) );
		vo.setDistrictType( DAOUtils.trimStr( rs.getString( "district_type" ) ) );
		vo.setDistrictAddr( DAOUtils.trimStr( rs.getString( "district_addr" ) ) );
		vo.setDealExch( DAOUtils.trimStr( rs.getString( "deal_exch" ) ) );
		vo.setSubExch( DAOUtils.trimStr( rs.getString( "sub_exch" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		LanDistrictVO vo = new LanDistrictVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord出错",this);
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
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

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			        	
			String districtId = smDAO.getNextSequence(vo.getTableName(), "DISTRICT_ID");
			((LanDistrictVO)vo).setDistrictId(districtId);
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictId() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictCode() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictName() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictType() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictAddr() );
			if ("".equals(((LanDistrictVO)vo).getDealExch())) {
				((LanDistrictVO)vo).setDealExch(null);
			}
			stmt.setString( index++, ((LanDistrictVO)vo).getDealExch() );
			if ("".equals(((LanDistrictVO)vo).getSubExch())) {
				((LanDistrictVO)vo).setSubExch(null);
			}
			stmt.setString( index++, ((LanDistrictVO)vo).getSubExch() );
			int rows = stmt.executeUpdate();
		}
		catch (SQLException se) {
			Debug.print(SQL_INSERT,this);
			throw new DAOSystemException("SQLException while insert sql:\n"+SQL_INSERT, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update( String pdistrict_id,LanDistrictVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE LAN_DISTRICT SET district_id = ?,district_code = ?,district_name = ?,district_type = ?,district_addr = ?,deal_exch = ?,sub_exch = ?" );
			sql.append( " WHERE  district_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((LanDistrictVO)vo).getDistrictId())) {
				((LanDistrictVO)vo).setDistrictId(null);
			}
			stmt.setString( index++, vo.getDistrictId() );
			stmt.setString( index++, vo.getDistrictCode() );
			stmt.setString( index++, vo.getDistrictName() );
			stmt.setString( index++, vo.getDistrictType() );
			stmt.setString( index++, vo.getDistrictAddr() );
			if ("".equals(((LanDistrictVO)vo).getDealExch())) {
				((LanDistrictVO)vo).setDealExch(null);
			}
			stmt.setString( index++, vo.getDealExch() );
			if ("".equals(((LanDistrictVO)vo).getSubExch())) {
				((LanDistrictVO)vo).setSubExch(null);
			}
			stmt.setString( index++, vo.getSubExch() );
			stmt.setString( index++, pdistrict_id );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond,VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE LAN_DISTRICT SET district_id = ?,district_code = ?,district_name = ?,district_type = ?,district_addr = ?,deal_exch = ?,sub_exch = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((LanDistrictVO)vo).getDistrictId())) {
				((LanDistrictVO)vo).setDistrictId(null);
			}
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictId() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictCode() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictName() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictType() );
			stmt.setString( index++, ((LanDistrictVO)vo).getDistrictAddr() );
			if ("".equals(((LanDistrictVO)vo).getDealExch())) {
				((LanDistrictVO)vo).setDealExch(null);
			}
			stmt.setString( index++, ((LanDistrictVO)vo).getDealExch() );
			if ("".equals(((LanDistrictVO)vo).getSubExch())) {
				((LanDistrictVO)vo).setSubExch(null);
			}
			stmt.setString( index++, ((LanDistrictVO)vo).getSubExch() );
			int rows = stmt.executeUpdate();
			if (rows>0) {
				bResult = true;
			}
		}
		catch (SQLException se) {
			Debug.print(sql.toString(),this);
			throw new DAOSystemException("SQLException while update sql:\n"+sql.toString(), se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public long countByCond(String whereCond) throws DAOSystemException {
		String countSQL = "select count(*) COL_COUNTS from LAN_DISTRICT a, region b, region c where a.DEAL_EXCH = b.region_id and a.sub_exch = c.region_id " + whereCond ;
		
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
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			//SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
			SQL = countSQL ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong( "COL_COUNTS" );
			}
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
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL) );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete( String pdistrict_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, pdistrict_id );
			rows = stmt.executeUpdate();

		}
		catch (SQLException se) {
			Debug.print(SQL_DELETE,this);
			throw new DAOSystemException("SQLException while deleting sql:\n"+SQL_DELETE, se);
		}
		finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
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
		return new LanDistrictVO();
	}

	public List findByCond(String whereCond,QueryFilter queryFilter) throws DAOSystemException {
		String selectSQL = "select a.* , b.region_name as deal_exch_name , b.path_code, c.region_name as sub_exch_name from LAN_DISTRICT a, region b, region c " +
		" where a.DEAL_EXCH = b.region_id and a.sub_exch = c.region_id ";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		//String SQL = SQL_SELECT + " WHERE " + whereCond;
		String SQL = selectSQL + " " + whereCond;
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			//stmt = conn.prepareStatement( SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rs = stmt.executeQuery();

			List retList = null;
			if (queryFilter != null) {
				retList = queryFilter.doPostFilter(rs,this);
			}
			else {
				retList=fetchMultiResults(rs);
			}
			return retList;
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
	private long getRecordCount(String sql) {
		Connection conn = null;
		long lCount = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong(1);
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}
	public PageModel getLanDistrictPaginate(String districtCode, String districtName, String dealExch, ArrayList regionVOs, int pageIndex, int pageSize ) throws DAOSystemException {
		String countSQL = "select count(*) from LAN_DISTRICT a, region b, region c where a.DEAL_EXCH = b.region_id and a.sub_exch = c.region_id ";
		String selectSQL = "select a.* , b.region_name as deal_exch_name , b.path_code, c.region_name as sub_exch_name from LAN_DISTRICT a, region b, region c " +
										" where a.DEAL_EXCH = b.region_id and a.sub_exch = c.region_id ";
		String cond = "";
		if( !"".equals(districtCode)){
			cond = cond + " and a.district_code like '%" + districtCode + "%'";
		}
		if( !"".equals(districtName)){
			cond = cond + " and a.district_name like '%" + districtName + "%'";
		}
		if( !"".equals(dealExch)){
			cond = cond + " and a.deal_exch = " + dealExch ;
		}
		String pathCodeCond = "";
		for( int i = 0 ; i < regionVOs.size() ; i ++ ){
			RegionVO vo = (RegionVO)regionVOs.get(i);
			if( i == 0 ){
				pathCodeCond += " b.path_code like '" + vo.getPathCode() + "%' " ;
			}else{
				pathCodeCond += " or b.path_code like '" + vo.getPathCode() + "%' " ;
			}
		}
		if( !"".equals(pathCodeCond)){
			pathCodeCond = "( " + pathCodeCond + " ) " ;
			cond = cond + " and " + pathCodeCond ;
		}
		
		countSQL += cond ;
		selectSQL += cond ;
		
		PageModel pageModel = new PageModel();
		// 计算 totalCount
		int totalCount = (new Long(getRecordCount(countSQL))).intValue();
		pageModel.setTotalCount(totalCount);

		// 空记录的处理
		if (totalCount == 0) {
			return new PageModel();
		}
		// pageCount
		if (totalCount % pageSize > 0) {
			pageModel.setPageCount(totalCount / pageSize + 1);
		} else {
			pageModel.setPageCount(totalCount / pageSize);
		}

		// 边界的处理
		if (pageIndex < 0) {
			pageModel.setPageIndex(1);
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(selectSQL),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			stmt.setMaxRows(maxRows);

			rs = stmt.executeQuery();

			int currentSize = 0;
			if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
				int startIndex = pageModel.getPageCount();
				currentSize = pageModel.getTotalCount() - (startIndex - 1)
						* pageModel.getPageSize();
			} else {
				currentSize = pageModel.getPageSize();
			}

			if (!rs.next()) {
				pageModel.setList(new ArrayList());
				return pageModel;
			}

			int locationInt = (pageModel.getPageIndex() - 1)
					* pageModel.getPageSize() + 1;
			rs.absolute(locationInt);
			int count = 0;

			ArrayList resultList = new ArrayList();
			do {
				LanDistrictVO vo = new LanDistrictVO();
				this.populateDto(vo, rs);
				vo.setDealExchName(rs.getString("deal_exch_name"));
				vo.setSubExchName(rs.getString("sub_exch_name"));
				resultList.add(vo);
				count++;
			} while (rs.next() && count < currentSize);

			pageModel.setList(resultList);
			return pageModel;

		} catch (SQLException se) {
			Debug.print(selectSQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ selectSQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
}

