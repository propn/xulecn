package com.ztesoft.oaas.dao.cpromotion;

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
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.CPromotionVO;

public class CPromotionDAOImpl   implements CPromotionDAO {

	private String SQL_SELECT = "SELECT promotion_id,promotion_code,promotion_name,org_id,oper_id,create_date,party_role_id,promotion_type,IF_SYS_OPER, state FROM C_PROMOTION";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM C_PROMOTION";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO C_PROMOTION ( promotion_id,promotion_code,promotion_name,org_id,oper_id,create_date,party_role_id,promotion_type,IF_SYS_OPER, state,lan_id,contact_name,contact_phone,short_name,comments,team_name,post_name ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE C_PROMOTION SET  promotion_code = ?, promotion_name = ?, org_id = ?, oper_id = ?, create_date = ?, party_role_id = ?, promotion_type = ?, IF_SYS_OPER = ?, state = ? WHERE promotion_id = ? ";

	private String SQL_DELETE = "DELETE FROM C_PROMOTION WHERE promotion_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM C_PROMOTION ";

	public CPromotionDAOImpl() {

	}

	public CPromotionVO findByPrimaryKey(String ppromotion_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE promotion_id = ? ", new String[] { ppromotion_id } );
		if (arrayList.size()>0)
			return (CPromotionVO)arrayList.get(0);
		else
			return (CPromotionVO) getEmptyVO();
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
			CPromotionVO vo = new CPromotionVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(CPromotionVO vo, ResultSet rs) throws SQLException {
		vo.setPromotionId( DAOUtils.trimStr( rs.getString( "promotion_id" ) ) );
		vo.setPromotionCode( DAOUtils.trimStr( rs.getString( "promotion_code" ) ) );
		vo.setPromotionName( DAOUtils.trimStr( rs.getString( "promotion_name" ) ) );
		vo.setOrgId( DAOUtils.trimStr( rs.getString( "org_id" ) ) );
		vo.setOperId( DAOUtils.trimStr( rs.getString( "oper_id" ) ) );
		vo.setCreateDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "create_date" ) ) );
		vo.setPartyRoleId( DAOUtils.trimStr( rs.getString( "party_role_id" ) ) );
		vo.setPromotionType( DAOUtils.trimStr( rs.getString( "promotion_type" ) ) );
		vo.setIfSysOper(DAOUtils.trimStr(rs.getString("IF_SYS_OPER")));
		vo.setState(DAOUtils.trimStr(rs.getString("state")));
		vo.setLanId(DAOUtils.trimStr(rs.getString("lan_id")));
		vo.setContactName(DAOUtils.trimStr(rs.getString("contact_name")));
		vo.setContactPhone(DAOUtils.trimStr(rs.getString("contact_phone")));
		vo.setShortName(DAOUtils.trimStr(rs.getString("short_name")));
		vo.setComments(DAOUtils.trimStr(rs.getString("comments")));
		vo.setTeamName(DAOUtils.trimStr(rs.getString("team_name")));
		vo.setPostName(DAOUtils.trimStr(rs.getString("post_name")));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		CPromotionVO vo = new CPromotionVO();
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			        	String seqId = smDAO.getNextSequence(vo.getTableName(),
			"PROMOTION_ID");
			int index = 1;
			if ("".equals(((CPromotionVO)vo).getPromotionId())) {
				((CPromotionVO)vo).setPromotionId(seqId);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionId() );
			((CPromotionVO)vo).setPromotionCode("023" + seqId);
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionCode() );
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionName() );
			if ("".equals(((CPromotionVO)vo).getOrgId())) {
				((CPromotionVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getOrgId() );
			if ("".equals(((CPromotionVO)vo).getOperId())) {
				((CPromotionVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(DateFormatUtils.getFormatedDateTime() ));
			if ("".equals(((CPromotionVO)vo).getPartyRoleId())) {
				((CPromotionVO)vo).setPartyRoleId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getPartyRoleId() );
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionType() );
			stmt.setString( index++, ((CPromotionVO)vo).getIfSysOper());
			stmt.setString(index++, ((CPromotionVO)vo).getState());
			
			if ("".equals(((CPromotionVO)vo).getLanId())) {
				((CPromotionVO)vo).setLanId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getLanId() );
			
			stmt.setString( index++, ((CPromotionVO)vo).getContactName() );
			stmt.setString( index++, ((CPromotionVO)vo).getContactPhone() );
			stmt.setString( index++, ((CPromotionVO)vo).getShortName() );
			stmt.setString( index++, ((CPromotionVO)vo).getComments() );
			stmt.setString( index++, ((CPromotionVO)vo).getTeamName() );
			stmt.setString( index++, ((CPromotionVO)vo).getPostName() );
			
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

	public boolean update( String ppromotion_id,CPromotionVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE C_PROMOTION SET promotion_id = ?,promotion_code = ?,promotion_name = ?,org_id = ?,party_role_id = ?,promotion_type = ?,IF_SYS_OPER = ?,state = ?,lan_id = ?,contact_name = ?,contact_phone = ?, short_name = ?, comments = ?, team_name = ?, post_name = ? " );
			sql.append( " WHERE  promotion_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((CPromotionVO)vo).getPromotionId())) {
				((CPromotionVO)vo).setPromotionId(null);
			}
			stmt.setString( index++, vo.getPromotionId() );
			stmt.setString( index++, vo.getPromotionCode() );
			stmt.setString( index++, vo.getPromotionName() );
			if ("".equals(((CPromotionVO)vo).getOrgId())) {
				((CPromotionVO)vo).setOrgId(null);
			}
			stmt.setString( index++, vo.getOrgId() );
			if ("".equals(((CPromotionVO)vo).getOperId())) {
				((CPromotionVO)vo).setOperId(null);
			}
			stmt.setString( index++, vo.getPartyRoleId() );
			stmt.setString( index++, vo.getPromotionType() );
			stmt.setString( index++, vo.getIfSysOper());
			stmt.setString(index++, vo.getState());
			
			if ("".equals(((CPromotionVO)vo).getLanId())) {
				((CPromotionVO)vo).setLanId(null);
			}
			stmt.setString( index++, vo.getLanId() );
			stmt.setString( index++, vo.getContactName() );
			stmt.setString( index++, vo.getContactPhone() );
			stmt.setString( index++, vo.getShortName() );
			stmt.setString( index++, vo.getComments() );
			stmt.setString( index++, vo.getTeamName() );
			stmt.setString( index++, vo.getPostName() );
			
			stmt.setString( index++, ppromotion_id );
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
			sql.append( "UPDATE C_PROMOTION SET promotion_id = ?,promotion_code = ?,promotion_name = ?,org_id = ?,party_role_id = ?,promotion_type = ?,IF_SYS_OPER=?,state=?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((CPromotionVO)vo).getPromotionId())) {
				((CPromotionVO)vo).setPromotionId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionId() );
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionCode() );
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionName() );
			if ("".equals(((CPromotionVO)vo).getOrgId())) {
				((CPromotionVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getOrgId() );
			if ("".equals(((CPromotionVO)vo).getOperId())) {
				((CPromotionVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((CPromotionVO)vo).getPartyRoleId() );
			stmt.setString( index++, ((CPromotionVO)vo).getPromotionType() );
			stmt.setString( index++, ((CPromotionVO)vo).getIfSysOper());
			stmt.setString( index++, ((CPromotionVO)vo).getState());
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
			SQL = SQL_SELECT_COUNT + " WHERE " + whereCond;
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

	public long delete( String ppromotion_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ppromotion_id );
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
		return new CPromotionVO();
	}

	public List findByCond(String whereCond,QueryFilter queryFilter) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT + " WHERE " + whereCond;
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = filterSQL;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
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
	
	public int countBySQL( String countSQL ) {
		Connection conn = null;
		int count = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(countSQL) );
			rs = stmt.executeQuery();

			while (rs.next()) {
				count = rs.getInt( 1 );
			}
		}
		catch (SQLException se) {
			Debug.print(SQL,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+countSQL, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return count;
	}

	/**
	 * 根据条件查询促销人信息
	 * @author suns ，modify 070818
	 */
	public PageModel queryCPromotionList(CPromotionVO vo, int pageIndex, int pageSize ) 
	throws DAOSystemException {
		String selectSQL = 
			"select a.PROMOTION_ID, a.PROMOTION_CODE, a.PROMOTION_NAME,"
			+ " a.org_id,a.OPER_ID, a.CREATE_DATE,a.party_role_id,"
			+ " a.PROMOTION_TYPE,a.IF_SYS_OPER,a.state, " 
			+ " a.lan_id,a.contact_name,a.contact_phone,a.short_name,a.comments,a.team_name,a.post_name,"
			+ " d.org_name, c.region_name  from C_PROMOTION a, ORGANIZATION d, REGION c " 
			+ " WHERE a.org_id = d.party_id and a.lan_id = c.region_id ";
			

		String countSQL =  
			"select DISTINCT count(*) from C_PROMOTION a, ORGANIZATION d,REGION c" 
			+ " WHERE a.org_id = d.party_id and a.lan_id = c.region_id ";
		
		if (!"".equals(vo.getPromotionItemStr()) && null != vo.getPromotionItemStr()) {
			/*selectSQL = selectSQL + " AND a.promotion_id IN(SELECT DISTINCT promotion_id"
			+ " FROM promotion_direc WHERE promotion_item IN (" 
			+ vo.getPromotionItemStr() + ") AND state = '00A')";
			
			/*countSQL = countSQL + " AND a.promotion_id IN(SELECT DISTINCT promotion_id"
			+ " FROM promotion_direc WHERE promotion_item IN (" 
			+ vo.getPromotionItemStr() + ") AND state = '00A')";
			*/
		
		}
		
		String cond = "" ;
		if( !"".equals(vo.getPromotionType()) && !"-1".equals(vo.getPromotionType())){//促销人类型
			cond = cond + " AND a.PROMOTION_TYPE = '" + vo.getPromotionType() + "'";
		}
		if( !"".equals(vo.getPartyRoleId())){//促销人关联参与人标识
			cond = cond + " AND a.party_role_id = " + vo.getPartyRoleId();
		}
		if( !"".equals(vo.getOrgId())){//所属部门
			cond = cond + " AND a.org_id = " + vo.getOrgId();
		}
		if( !"".equals(vo.getLanId())){  //本地网
			cond = cond + " AND a.lan_id = '" + vo.getLanId() + "'";
		}		
		if( !"".equals(vo.getPromotionCode())){//促销人代码
			cond = cond + " AND a.PROMOTION_CODE = '" + vo.getPromotionCode() + "'";
		}
		if( !"".equals(vo.getPromotionName())){//促销人姓名
			cond = cond + " AND a.PROMOTION_NAME like '%" + vo.getPromotionName() + "%'";
		}
		if( !"".equals(vo.getIfSysOper())){
			cond = cond + " AND a.IF_SYS_OPER = '" + vo.getIfSysOper() + "'";
		}
		if( !"".equals(vo.getState())){
			cond = cond + " AND a.state = '" + vo.getState() + "'";
		}
		if( !"".equals(vo.getTeamName())){
			cond = cond + " AND a.team_name like '%" + vo.getTeamName() + "%'";
		}
		if( !"".equals(vo.getPostName())){
			cond = cond + " AND a.post_name like '%" + vo.getPostName() + "%'";
		}

		if( !"".equals(cond)){
			selectSQL = selectSQL + cond ;
			countSQL = countSQL + cond ;
		}
		
		PageModel pageModel = new PageModel();
		//计算 totalCount
		int totalCount = countBySQL( countSQL ) ;
		pageModel.setTotalCount( totalCount );

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
		} else if(pageIndex>pageModel.getPageCount()){
			pageModel.setPageIndex(pageModel.getPageCount());
		}else {
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
			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			selectSQL = DAOSQLUtils.getFilterSQL(selectSQL) ;
			if("ORACLE".equalsIgnoreCase(databaseType)){
				selectSQL = DAOSQLUtils.doPreFilter(false,selectSQL,pageIndex,pageSize);
			}

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(selectSQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			
			ArrayList resultList = new ArrayList();
			if("ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					CPromotionVO cpromotionVO = new CPromotionVO();
					populateDto(cpromotionVO, rs);
					cpromotionVO.setOrgName(rs.getString("org_name"));
					cpromotionVO.setLanName(rs.getString("region_name"));
					resultList.add(cpromotionVO);
				}
			}else{
				int currentSize = 0;
		         if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
		             int startIndex = pageModel.getPageCount();
		             currentSize = pageModel.getTotalCount() - (startIndex - 1) * pageModel.getPageSize();
		         }
		         else {
		             currentSize = pageModel.getPageSize();
		         }
		         
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
			        
				int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize()+1;
				rs.absolute(locationInt);
				int count = 0;
				
				do{
					CPromotionVO cpromotionVO = new CPromotionVO();
					populateDto(cpromotionVO, rs);
					cpromotionVO.setOrgName(rs.getString("org_name"));
					resultList.add(cpromotionVO);
					count ++;
				}while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;
			
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ selectSQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public boolean ifPromotionInUsed( String promotionId ) throws DAOSystemException {
		return false ;
	}
}

