package com.ztesoft.oaas.dao.partnerinfo;

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
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerInfoVO;
import com.ztesoft.oaas.vo.PartnerQueryVO;

public class PartnerInfoDAOImpl   implements PartnerInfoDAO {

	private String SQL_SELECT = "SELECT partner_id,party_id,partner_code,partner_type,partner_desc,corporation_agent,address,apply_date,linkman,corporate_licence_no,spcp_licence_no,bank_permit,company_size,company_balance,cooperate_type,dev_plan,partner_grade,state,org_id,staff_id,super_partner_id,path_code FROM PARTNER_INFO";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER_INFO";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER_INFO ( partner_id,party_id,partner_code,partner_type,partner_desc,corporation_agent,address,apply_date,linkman,corporate_licence_no,spcp_licence_no,bank_permit,company_size,company_balance,cooperate_type,dev_plan,partner_grade,state,org_id,staff_id,super_partner_id,path_code ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER_INFO SET  party_id = ?, partner_code = ?, partner_type = ?, partner_desc = ?, corporation_agent = ?, address = ?, apply_date = ?, linkman = ?, corporate_licence_no = ?, spcp_licence_no = ?, bank_permit = ?, company_size = ?, company_balance = ?, cooperate_type = ?, dev_plan = ?, partner_grade = ?, state = ?, org_id = ?, staff_id = ?, super_partner_id = ?, path_code = ? WHERE partner_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER_INFO WHERE partner_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER_INFO ";

	public PartnerInfoDAOImpl() {

	}

	public PartnerInfoVO findByPrimaryKey(String ppartner_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE partner_id = ? ", new String[] { ppartner_id } );
		if (arrayList.size()>0)
			return (PartnerInfoVO)arrayList.get(0);
		else
			return (PartnerInfoVO) getEmptyVO();
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
			PartnerInfoVO vo = new PartnerInfoVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerInfoVO vo, ResultSet rs) throws SQLException {
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
		vo.setSuperPartnerId( DAOUtils.trimStr( rs.getString( "super_partner_id" ) ) );
		vo.setPathCode( DAOUtils.trimStr( rs.getString( "path_code" ) ) );
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerInfoVO vo = new PartnerInfoVO();
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
			SQL = SQL_SELECT + " WHERE " + whereCond + " ORDER BY PATH_CODE " ;
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
		String parent = ((PartnerInfoVO)vo).getSuperPartnerId();
        if(parent != null && !"-1".equals(parent) && !"".equals(parent)){
        	PartnerInfoVO voParent = findByPrimaryKey(parent);
            if(voParent == null){
                throw new DAOSystemException("INVALID PARENT_ID [" + parent  + "]");
            }
            parent = voParent.getPathCode();
        } else {
            parent = null;
        }
        
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			String seqId = SeqDAOFactory.getInstance().getSequenceManageDAO().getNextSequence(vo.getTableName(), "PARTNER_ID");
			((PartnerInfoVO)vo).setPartnerId( seqId ) ;
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			
			int index = 1;

			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerId() );
			if ("".equals(((PartnerInfoVO)vo).getPartyId())) {
				((PartnerInfoVO)vo).setPartyId(null);
			}
			
			((PartnerInfoVO)vo).setPathCode(parent == null ? ((PartnerInfoVO)vo)
                    .getPartnerId() : parent + "."
                    + ((PartnerInfoVO)vo).getPartnerId());
			
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartyId() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerCode() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerType() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerDesc() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCorpAgent() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getAddr() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerInfoVO)vo).getApplyDate()) );
			stmt.setString( index++, ((PartnerInfoVO)vo).getLinkman() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCorporateLicenceNo() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getSpcpLicenceNo() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getBankPermit() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCompanySize() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCompanyBalns() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getDevPlan() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerGrade() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getState() );
			if ("".equals(((PartnerInfoVO)vo).getOrgId())) {
				((PartnerInfoVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getOrgId() );
			if ("".equals(((PartnerInfoVO)vo).getStaffId())) {
				((PartnerInfoVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getStaffId() );
			if ("".equals(((PartnerInfoVO)vo).getSuperPartnerId())) {
				((PartnerInfoVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getSuperPartnerId() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPathCode() );
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

	public boolean update( String ppartner_id,PartnerInfoVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTNER_INFO SET partner_id = ?,party_id = ?,partner_code = ?,partner_type = ?,partner_desc = ?,corporation_agent = ?,address = ?,apply_date = ?,linkman = ?,corporate_licence_no = ?,spcp_licence_no = ?,bank_permit = ?,company_size = ?,company_balance = ?,cooperate_type = ?,dev_plan = ?,partner_grade = ?,state = ?,org_id = ?,staff_id = ?,super_partner_id = ?" );
			sql.append( " WHERE  partner_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerInfoVO)vo).getPartnerId())) {
				((PartnerInfoVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, vo.getPartnerId() );
			if ("".equals(((PartnerInfoVO)vo).getPartyId())) {
				((PartnerInfoVO)vo).setPartyId(null);
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
			if ("".equals(((PartnerInfoVO)vo).getOrgId())) {
				((PartnerInfoVO)vo).setOrgId(null);
			}
			stmt.setString( index++, vo.getOrgId() );
			if ("".equals(((PartnerInfoVO)vo).getStaffId())) {
				((PartnerInfoVO)vo).setStaffId(null);
			}
			stmt.setString( index++, vo.getStaffId() );
			if ("".equals(((PartnerInfoVO)vo).getSuperPartnerId())) {
				((PartnerInfoVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, vo.getSuperPartnerId() );
			//stmt.setString( index++, vo.getPathCode() );
			stmt.setString( index++, ppartner_id );
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
			sql.append( "UPDATE PARTNER_INFO SET partner_id = ?,party_id = ?,partner_code = ?,partner_type = ?,partner_desc = ?,corporation_agent = ?,address = ?,apply_date = ?,linkman = ?,corporate_licence_no = ?,spcp_licence_no = ?,bank_permit = ?,company_size = ?,company_balance = ?,cooperate_type = ?,dev_plan = ?,partner_grade = ?,state = ?,org_id = ?,staff_id = ?,super_partner_id = ?,path_code = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerInfoVO)vo).getPartnerId())) {
				((PartnerInfoVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerId() );
			if ("".equals(((PartnerInfoVO)vo).getPartyId())) {
				((PartnerInfoVO)vo).setPartyId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartyId() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerCode() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerType() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerDesc() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCorpAgent() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getAddr() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerInfoVO)vo).getApplyDate()) );
			stmt.setString( index++, ((PartnerInfoVO)vo).getLinkman() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCorporateLicenceNo() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getSpcpLicenceNo() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getBankPermit() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCompanySize() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCompanyBalns() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getDevPlan() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPartnerGrade() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getState() );
			if ("".equals(((PartnerInfoVO)vo).getOrgId())) {
				((PartnerInfoVO)vo).setOrgId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getOrgId() );
			if ("".equals(((PartnerInfoVO)vo).getStaffId())) {
				((PartnerInfoVO)vo).setStaffId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getStaffId() );
			if ("".equals(((PartnerInfoVO)vo).getSuperPartnerId())) {
				((PartnerInfoVO)vo).setSuperPartnerId(null);
			}
			stmt.setString( index++, ((PartnerInfoVO)vo).getSuperPartnerId() );
			stmt.setString( index++, ((PartnerInfoVO)vo).getPathCode() );
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

	public long delete( String ppartner_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ppartner_id );
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
		return new PartnerInfoVO();
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

	public List queryPartner( PartnerQueryVO vo ) throws DAOSystemException{
		
		String sql = "SELECT ptn.partner_id, ptn.party_id, ptn.partner_code, ptn.partner_type, ptn.partner_desc, ptn.corporation_agent, ptn.address, ptn.apply_date, ptn.linkman, ptn.corporate_licence_no, ptn.spcp_licence_no, ptn.bank_permit, ptn.company_size, ptn.company_balance, ptn.cooperate_type, ptn.dev_plan, ptn.partner_grade, ptn.state, ptn.org_id, ptn.staff_id, ptn.super_partner_id, ptn.path_code, pr.party_role_name,org.org_name FROM PARTNER_INFO ptn, PARTY_ROLE pr, ORGANIZATION org WHERE ptn.staff_id = pr.party_role_id AND ptn.org_id = org.party_id ";
		String cond = "" ;
		if( !"".equals(vo.getCooperateType())){
			cond += " AND ptn.COOPERATE_TYPE = '" + vo.getCooperateType() + "'" ;
		}
		if( !"".equals(vo.getOrgId())){
			cond += " AND ptn.ORG_ID = " + vo.getOrgId() ;
		}
		if( !"".equals(vo.getPartnerCode())){
			cond += " AND ptn.PARTNER_CODE = '" + vo.getPartnerCode() + "'";
		}
		if( !"".equals( vo.getPartnerGrade())){
			cond += " AND ptn.PARTNER_GRADE = '" + vo.getPartnerGrade() + "'";
		}
		if( !"".equals( vo.getPartnerType())){
			cond += " AND ptn.PARTNER_TYPE = '" + vo.getPartnerType() + "'";
		}
		if( !"".equals( vo.getStaffId())){
			cond += " AND ptn.STAFF_ID = " + vo.getStaffId() ;
		}
		if(!"".equals(vo.getSuperPartnerId())){
			cond += " AND ptn.SUPER_PARTNER_ID = " + vo.getSuperPartnerId() ;
		}
		
		sql += cond ;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql), ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE );
			rs = stmt.executeQuery();
			
			List resultList = new ArrayList() ;
			while (rs.next()) {
				PartnerInfoVO partnerInfoVO = new PartnerInfoVO();
				populateDto( partnerInfoVO, rs);
				partnerInfoVO.setStaffName( rs.getString("party_role_name"));
				partnerInfoVO.setOrgName( rs.getString( "org_name"));
				resultList.add( partnerInfoVO );
			}
			return resultList ;
		}catch (SQLException se) {
			Debug.print(sql,this);
			throw new DAOSystemException("SQLException while getting sql:\n"+sql, se);
		}
		finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
}
