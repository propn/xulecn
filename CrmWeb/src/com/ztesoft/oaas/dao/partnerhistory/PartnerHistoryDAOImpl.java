package com.ztesoft.oaas.dao.partnerhistory;

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
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerHistoryVO;

public class PartnerHistoryDAOImpl   implements PartnerHistoryDAO {

	private String SQL_SELECT = "SELECT partner_his_id,partner_id,party_id,partner_code,partner_type,partner_desc,corporation_agent,address,apply_date,linkman,corporate_licence_no,spcp_licence_no,bank_permit,company_size,company_balance,cooperate_type,dev_plan,partner_grade,state,org_id,staff_id,oper_id,oper_time,super_partner_id,path_code FROM PARTNER_HISTORY";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER_HISTORY";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER_HISTORY ( partner_his_id,partner_id,party_id,partner_code,partner_type,partner_desc,corporation_agent,address,apply_date,linkman,corporate_licence_no,spcp_licence_no,bank_permit,company_size,company_balance,cooperate_type,dev_plan,partner_grade,state,org_id,staff_id,oper_id,oper_time,super_partner_id,path_code ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER_HISTORY SET  partner_id = ?, party_id = ?, partner_code = ?, partner_type = ?, partner_desc = ?, corporation_agent = ?, address = ?, apply_date = ?, linkman = ?, corporate_licence_no = ?, spcp_licence_no = ?, bank_permit = ?, company_size = ?, company_balance = ?, cooperate_type = ?, dev_plan = ?, partner_grade = ?, state = ?, org_id = ?, staff_id = ?, oper_id = ?, oper_time = ?, super_partner_id = ?, path_code = ? WHERE partner_his_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER_HISTORY WHERE partner_his_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER_HISTORY ";

	public PartnerHistoryDAOImpl() {

	}

	public PartnerHistoryVO findByPrimaryKey(String ppartner_his_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE partner_his_id = ? ", new String[] { ppartner_his_id } );
		if (arrayList.size()>0)
			return (PartnerHistoryVO)arrayList.get(0);
		else
			return (PartnerHistoryVO) getEmptyVO();
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
			PartnerHistoryVO vo = new PartnerHistoryVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerHistoryVO vo, ResultSet rs) throws SQLException {
		vo.setPartnerHisId( DAOUtils.trimStr( rs.getString( "partner_his_id" ) ) );
		vo.setPartnerId( DAOUtils.trimStr( rs.getString( "partner_id" ) ) );
		vo.setPartyId( DAOUtils.trimStr( rs.getString( "party_id" ) ) );
		vo.setPartnerCode( DAOUtils.trimStr( rs.getString( "partner_code" ) ) );
		vo.setPartnerType( DAOUtils.trimStr( rs.getString( "partner_type" ) ) );
		vo.setPartnerDesc( DAOUtils.trimStr( rs.getString( "partner_desc" ) ) );
		vo.setCorpAgent( DAOUtils.trimStr( rs.getString( "corporation_agent" ) ) );
		vo.setAddr( DAOUtils.trimStr( rs.getString( "address" ) ) );
		vo.setApplyDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "apply_date" ) ) );
		vo.setLinkman( DAOUtils.trimStr( rs.getString( "linkman" ) ) );
		vo.setCorporateLicenceNo( DAOUtils.trimStr( rs.getString( "corporate_licence_no" ) ) );
		vo.setSpcpLicenceNo( DAOUtils.trimStr( rs.getString( "spcp_licence_no" ) ) );
		vo.setBankPermit( DAOUtils.trimStr( rs.getString( "bank_permit" ) ) );
		vo.setCompanySize( DAOUtils.trimStr( rs.getString( "company_size" ) ) );
		vo.setCompanyBalns( DAOUtils.trimStr( rs.getString( "company_balance" ) ) );
		vo.setCooperateType( DAOUtils.trimStr( rs.getString( "cooperate_type" ) ) );
		vo.setDevPlan( DAOUtils.trimStr( rs.getString( "dev_plan" ) ) );
		vo.setPartnerGrade( DAOUtils.trimStr( rs.getString( "partner_grade" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "state" ) ) );
		vo.setOrgId( DAOUtils.trimStr( rs.getString( "org_id" ) ) );
		vo.setStaffId( DAOUtils.trimStr( rs.getString( "staff_id" ) ) );
		vo.setOperId( DAOUtils.trimStr( rs.getString( "oper_id" ) ) );
		vo.setOperTime( DAOUtils.getFormatedDateTime( rs.getTimestamp( "oper_time" ) ) );
		vo.setSuperPartnerId( DAOUtils.trimStr( rs.getString( "super_partner_id" ) ) );
		vo.setPathCode( DAOUtils.trimStr( rs.getString( "path_code" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerHistoryVO vo = new PartnerHistoryVO();
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
			String seqId = smDAO.getNextSequence(vo.getTableName(), "PARTNER_HIS_ID");
			((PartnerHistoryVO)vo).setPartnerHisId(seqId);
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			
			int index = 1;
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerHisId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartnerId())) {
				((PartnerHistoryVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartyId())) {
				((PartnerHistoryVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartyId() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerCode() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerType() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerDesc() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCorpAgent() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getAddr() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerHistoryVO)vo).getApplyDate()) );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getLinkman() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCorporateLicenceNo() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getSpcpLicenceNo() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getBankPermit() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCompanySize() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCompanyBalns() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getDevPlan() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerGrade() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getState() );
			if ("".equals(((PartnerHistoryVO)vo).getOrgId())) {
				((PartnerHistoryVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getOrgId() );
			if ("".equals(((PartnerHistoryVO)vo).getStaffId())) {
				((PartnerHistoryVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getStaffId() );
			if ("".equals(((PartnerHistoryVO)vo).getOperId())) {
				((PartnerHistoryVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerHistoryVO)vo).getOperTime()) );
			if ("".equals(((PartnerHistoryVO)vo).getSuperPartnerId())) {
				((PartnerHistoryVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getSuperPartnerId() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPathCode() );
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

	public boolean update( String ppartner_his_id,PartnerHistoryVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTNER_HISTORY SET partner_his_id = ?,partner_id = ?,party_id = ?,partner_code = ?,partner_type = ?,partner_desc = ?,corporation_agent = ?,address = ?,apply_date = ?,linkman = ?,corporate_licence_no = ?,spcp_licence_no = ?,bank_permit = ?,company_size = ?,company_balance = ?,cooperate_type = ?,dev_plan = ?,partner_grade = ?,state = ?,org_id = ?,staff_id = ?,oper_id = ?,oper_time = ?,super_partner_id = ?,path_code = ?" );
			sql.append( " WHERE  partner_his_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerHistoryVO)vo).getPartnerHisId())) {
				((PartnerHistoryVO)vo).setPartnerHisId(null);
			}
			stmt.setString( index++, vo.getPartnerHisId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartnerId())) {
				((PartnerHistoryVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, vo.getPartnerId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartyId())) {
				((PartnerHistoryVO)vo).setPartyId(null);
			}
			stmt.setString( index++, vo.getPartyId() );
			stmt.setString( index++, vo.getPartnerCode() );
			stmt.setString( index++, vo.getPartnerType() );
			stmt.setString( index++, vo.getPartnerDesc() );
			stmt.setString( index++, vo.getCorpAgent() );
			stmt.setString( index++, vo.getAddr() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getApplyDate()) );
			stmt.setString( index++, vo.getLinkman() );
			stmt.setString( index++, vo.getCorporateLicenceNo() );
			stmt.setString( index++, vo.getSpcpLicenceNo() );
			stmt.setString( index++, vo.getBankPermit() );
			stmt.setString( index++, vo.getCompanySize() );
			stmt.setString( index++, vo.getCompanyBalns() );
			stmt.setString( index++, vo.getCooperateType() );
			stmt.setString( index++, vo.getDevPlan() );
			stmt.setString( index++, vo.getPartnerGrade() );
			stmt.setString( index++, vo.getState() );
			if ("".equals(((PartnerHistoryVO)vo).getOrgId())) {
				((PartnerHistoryVO)vo).setOrgId(null);
			}
			stmt.setString( index++, vo.getOrgId() );
			if ("".equals(((PartnerHistoryVO)vo).getStaffId())) {
				((PartnerHistoryVO)vo).setStaffId(null);
			}
			stmt.setString( index++, vo.getStaffId() );
			if ("".equals(((PartnerHistoryVO)vo).getOperId())) {
				((PartnerHistoryVO)vo).setOperId(null);
			}
			stmt.setString( index++, vo.getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getOperTime()) );
			if ("".equals(((PartnerHistoryVO)vo).getSuperPartnerId())) {
				((PartnerHistoryVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, vo.getSuperPartnerId() );
			stmt.setString( index++, vo.getPathCode() );
			stmt.setString( index++, ppartner_his_id );
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
			sql.append( "UPDATE PARTNER_HISTORY SET partner_his_id = ?,partner_id = ?,party_id = ?,partner_code = ?,partner_type = ?,partner_desc = ?,corporation_agent = ?,address = ?,apply_date = ?,linkman = ?,corporate_licence_no = ?,spcp_licence_no = ?,bank_permit = ?,company_size = ?,company_balance = ?,cooperate_type = ?,dev_plan = ?,partner_grade = ?,state = ?,org_id = ?,staff_id = ?,oper_id = ?,oper_time = ?,super_partner_id = ?,path_code = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerHistoryVO)vo).getPartnerHisId())) {
				((PartnerHistoryVO)vo).setPartnerHisId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerHisId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartnerId())) {
				((PartnerHistoryVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerId() );
			if ("".equals(((PartnerHistoryVO)vo).getPartyId())) {
				((PartnerHistoryVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartyId() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerCode() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerType() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerDesc() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCorpAgent() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getAddr() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerHistoryVO)vo).getApplyDate()) );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getLinkman() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCorporateLicenceNo() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getSpcpLicenceNo() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getBankPermit() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCompanySize() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCompanyBalns() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getDevPlan() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPartnerGrade() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getState() );
			if ("".equals(((PartnerHistoryVO)vo).getOrgId())) {
				((PartnerHistoryVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getOrgId() );
			if ("".equals(((PartnerHistoryVO)vo).getStaffId())) {
				((PartnerHistoryVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getStaffId() );
			if ("".equals(((PartnerHistoryVO)vo).getOperId())) {
				((PartnerHistoryVO)vo).setOperId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getOperId() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerHistoryVO)vo).getOperTime()) );
			if ("".equals(((PartnerHistoryVO)vo).getSuperPartnerId())) {
				((PartnerHistoryVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, ((PartnerHistoryVO)vo).getSuperPartnerId() );
			stmt.setString( index++, ((PartnerHistoryVO)vo).getPathCode() );
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

	public long delete( String ppartner_his_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ppartner_his_id );
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
		return new PartnerHistoryVO();
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

}
