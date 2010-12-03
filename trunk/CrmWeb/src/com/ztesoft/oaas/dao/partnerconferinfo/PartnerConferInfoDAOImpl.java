package com.ztesoft.oaas.dao.partnerconferinfo;

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
import com.ztesoft.common.util.DateFormatUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.PartnerConferInfoVO;

public class PartnerConferInfoDAOImpl   implements PartnerConferInfoDAO {

	private String SQL_SELECT = "SELECT partner_conf_id,partner_id,confer_code,partner_confer_type,sign_date,exp_date,cooperate_type,confer_contence,balance_rule,partner_droit_duty,droit_duty,abort_condiction,break_duty,state,oper_id,oper_time FROM PARTNER_CONFER_INFO";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTNER_CONFER_INFO";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTNER_CONFER_INFO ( partner_conf_id,partner_id,confer_code,partner_confer_type,sign_date,exp_date,cooperate_type,confer_contence,balance_rule,partner_droit_duty,droit_duty,abort_condiction,break_duty,state,oper_id,oper_time ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PARTNER_CONFER_INFO SET  partner_id = ?, confer_code = ?, partner_confer_type = ?, sign_date = ?, exp_date = ?, cooperate_type = ?, confer_contence = ?, balance_rule = ?, partner_droit_duty = ?, droit_duty = ?, abort_condiction = ?, break_duty = ?, state = ?, oper_id = ?, oper_time = ? WHERE partner_conf_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTNER_CONFER_INFO WHERE partner_conf_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTNER_CONFER_INFO ";

	public PartnerConferInfoDAOImpl() {

	}

	public PartnerConferInfoVO findByPrimaryKey(String ppartner_conf_id) throws DAOSystemException {
		ArrayList arrayList = findBySql( SQL_SELECT + " WHERE partner_conf_id = ? ", new String[] { ppartner_conf_id } );
		if (arrayList.size()>0)
			return (PartnerConferInfoVO)arrayList.get(0);
		else
			return (PartnerConferInfoVO) getEmptyVO();
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
			PartnerConferInfoVO vo = new PartnerConferInfoVO();
			populateDto( vo, rs);
			resultList.add( vo );
		}
		return resultList;
	}

	protected void populateDto(PartnerConferInfoVO vo, ResultSet rs) throws SQLException {
		vo.setPartnerConfId( DAOUtils.trimStr( rs.getString( "partner_conf_id" ) ) );
		vo.setPartnerId( DAOUtils.trimStr( rs.getString( "partner_id" ) ) );
		vo.setConferCode( DAOUtils.trimStr( rs.getString( "confer_code" ) ) );
		vo.setPartnerConferType( DAOUtils.trimStr( rs.getString( "partner_confer_type" ) ) );
		vo.setSignDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "sign_date" ) ) );
		vo.setExpDate( DAOUtils.getFormatedDateTime( rs.getTimestamp( "exp_date" ) ) );
		vo.setCooperateType( DAOUtils.trimStr( rs.getString( "cooperate_type" ) ) );
		vo.setConferContence( DAOUtils.trimStr( rs.getString( "confer_contence" ) ) );
		vo.setBalnsRule( DAOUtils.trimStr( rs.getString( "balance_rule" ) ) );
		vo.setPartnerDroitDuty( DAOUtils.trimStr( rs.getString( "partner_droit_duty" ) ) );
		vo.setDroitDuty( DAOUtils.trimStr( rs.getString( "droit_duty" ) ) );
		vo.setAbortCondiction( DAOUtils.trimStr( rs.getString( "abort_condiction" ) ) );
		vo.setBreakDuty( DAOUtils.trimStr( rs.getString( "break_duty" ) ) );
		vo.setState( DAOUtils.trimStr( rs.getString( "state" ) ) );
		vo.setOperId( DAOUtils.trimStr(rs.getString("oper_id")));
		vo.setOperTime(DAOUtils.trimStr(rs.getString("oper_time")));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException{
		PartnerConferInfoVO vo = new PartnerConferInfoVO();
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
			String seq = smDAO.getNextSequence(vo.getTableName(), "PARTNER_CONF_ID");
			((PartnerConferInfoVO)vo).setPartnerConfId(seq);
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_INSERT) );
			int index = 1;
			
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerConfId() );
			if ("".equals(((PartnerConferInfoVO)vo).getPartnerId())) {
				((PartnerConferInfoVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerId() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getConferCode() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerConferType() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerConferInfoVO)vo).getSignDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerConferInfoVO)vo).getExpDate()) );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getConferContence() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getBalnsRule() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerDroitDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getDroitDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getAbortCondiction() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getBreakDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getState() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getOperId());
			stmt.setString( index++, DateFormatUtils.getFormatedDateTime());
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

	public boolean update( String ppartner_conf_id,PartnerConferInfoVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( "UPDATE PARTNER_CONFER_INFO SET partner_conf_id = ?,partner_id = ?,confer_code = ?,partner_confer_type = ?,sign_date = ?,exp_date = ?,cooperate_type = ?,confer_contence = ?,balance_rule = ?,partner_droit_duty = ?,droit_duty = ?,abort_condiction = ?,break_duty = ?,state = ?, oper_id = ?, oper_time = ?" );
			sql.append( " WHERE  partner_conf_id = ? " );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerConferInfoVO)vo).getPartnerConfId())) {
				((PartnerConferInfoVO)vo).setPartnerConfId(null);
			}
			stmt.setString( index++, vo.getPartnerConfId() );
			if ("".equals(((PartnerConferInfoVO)vo).getPartnerId())) {
				((PartnerConferInfoVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, vo.getPartnerId() );
			stmt.setString( index++, vo.getConferCode() );
			stmt.setString( index++, vo.getPartnerConferType() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getSignDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()) );
			stmt.setString( index++, vo.getCooperateType() );
			stmt.setString( index++, vo.getConferContence() );
			stmt.setString( index++, vo.getBalnsRule() );
			stmt.setString( index++, vo.getPartnerDroitDuty() );
			stmt.setString( index++, vo.getDroitDuty() );
			stmt.setString( index++, vo.getAbortCondiction() );
			stmt.setString( index++, vo.getBreakDuty() );
			stmt.setString( index++, vo.getState() );
			stmt.setString( index++, vo.getOperId());
			stmt.setString( index++, DateFormatUtils.getFormatedDateTime());
			stmt.setString( index++, ppartner_conf_id );
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
			sql.append( "UPDATE PARTNER_CONFER_INFO SET partner_conf_id = ?,partner_id = ?,confer_code = ?,partner_confer_type = ?,sign_date = ?,exp_date = ?,cooperate_type = ?,confer_contence = ?,balance_rule = ?,partner_droit_duty = ?,droit_duty = ?,abort_condiction = ?,break_duty = ?,state = ?, oper_id = ?, oper_time = ?" );
			sql.append( " WHERE "+whereCond );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if ("".equals(((PartnerConferInfoVO)vo).getPartnerConfId())) {
				((PartnerConferInfoVO)vo).setPartnerConfId(null);
			}
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerConfId() );
			if ("".equals(((PartnerConferInfoVO)vo).getPartnerId())) {
				((PartnerConferInfoVO)vo).setPartnerId(null);
			}
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerId() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getConferCode() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerConferType() );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerConferInfoVO)vo).getSignDate()) );
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartnerConferInfoVO)vo).getExpDate()) );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getCooperateType() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getConferContence() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getBalnsRule() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getPartnerDroitDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getDroitDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getAbortCondiction() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getBreakDuty() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getState() );
			stmt.setString( index++, ((PartnerConferInfoVO)vo).getOperId());
			stmt.setString( index++, DateFormatUtils.getFormatedDateTime());
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

	public long delete( String ppartner_conf_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(SQL_DELETE) );
			int index = 1;
			stmt.setString( index++, ppartner_conf_id );
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
		return new PartnerConferInfoVO();
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
