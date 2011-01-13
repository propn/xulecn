package com.ztesoft.oaas.dao.staff;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.dao.QueryFilter;
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.dao.priv.PrivDAO;
import com.ztesoft.oaas.dao.priv.PrivDAOFactory;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.StaffVO;

public class StaffDAOImpl   implements StaffDAO {

	private String SQL_SELECT = "SELECT s.party_role_id,s.staff_code, s.staff_desc, s.menu_code,s.channel_segment_id, s.dev_user_belong,s.business_id,s.county_type,i.party_id, i.gender, i.birth_place, i.birth_date, i.marital_status, i.skill, p.party_name, p.eff_date, p.state, p.state_date, pr.org_party_id, pr.party_role_type, pr.org_manager, pr.exp_date FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o WHERE s.party_role_id=pr.party_role_id AND pr.s_party_id=i.party_id AND pr.s_party_id=p.party_id AND pr.org_party_id=o.party_id ";
//	private String SQL_SELECT = "SELECT s.party_role_id,s.staff_code, s.staff_desc, s.menu_code,s.channel_segment_id, s.dev_user_belong, i.party_id, i.gender, i.birth_place, i.birth_date, i.marital_status, i.skill, p.party_name, p.eff_date, p.state, p.state_date, pr.org_party_id, pr.party_role_type, pr.org_manager, pr.exp_date FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o WHERE s.party_role_id=pr.party_role_id AND pr.s_party_id=i.party_id AND pr.s_party_id=p.party_id AND pr.org_party_id=o.party_id ";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM STAFF";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO STAFF ( party_role_id,staff_code,staff_desc,menu_code=?,channel_segment_id, dev_user_belong) VALUES ( ?,?,?,?,?,?)";

	private String SQL_UPDATE = "UPDATE STAFF SET  staff_code = ?, staff_desc = ?, menu_code =?, channel_segment_id = ?, dev_user_belong WHERE party_role_id = ? ";

	private String SQL_DELETE = "DELETE FROM STAFF WHERE party_role_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM STAFF ";

	public StaffDAOImpl() {

	}
	
	public boolean isSuperStaff(String staffCode) throws DAOSystemException {
		boolean returnValue = false ;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT param_code,param_desc,param_val FROM DC_SYSTEM_PARAM WHERE param_code = ? ";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			stmt.setMaxRows(maxRows);
			stmt.setString( 1, "SUPER_STAFF_CODE" );
	
			rs = stmt.executeQuery();
			
			if( rs.next() ){
				String paramValue = rs.getString("param_val") ;
				String staffCodes[] = paramValue.split(",");
				for( int i = 0 ; i < staffCodes.length ; i ++ ){
					if( staffCode.equalsIgnoreCase(staffCodes[i])){
						returnValue = true ;
						break ;
					}
				}
			}
			return returnValue;
		} catch (SQLException se) {
			Debug.print(DAOSQLUtils.getFilterSQL(sql), this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public StaffVO getStaffByStaffCode(String staffCode)
			throws DAOSystemException {
		String SQL = "SELECT PARTY_ROLE_ID,STAFF_CODE,STAFF_DESC,menu_code,CHANNEL_SEGMENT_ID, dev_user_belong FROM STAFF where STAFF_CODE = ?";
		ArrayList arrayList = this.findBySql(SQL, new String[] { staffCode });
		if (arrayList.size() > 0) {
			return (StaffVO) arrayList.get(0);
		} else {
			return null;
		}
	}

	public StaffVO findByPrimaryKey(String pparty_role_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT
				+ " AND s.party_role_id = ? ", new String[] { pparty_role_id });
		if (arrayList.size() > 0)
			return (StaffVO) arrayList.get(0);
		else
			return (StaffVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			stmt.setMaxRows(maxRows);
			for (int i = 0; sqlParams != null && i < sqlParams.length; i++) {
				stmt.setString(i + 1, sqlParams[i]);
			}

			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(DAOSQLUtils.getFilterSQL(sql), this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			StaffVO vo = new StaffVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(StaffVO vo, ResultSet rs) throws SQLException {
		vo.setPartyRoleId(rs.getString("party_role_id"));
		vo.setStaffCode(rs.getString("staff_code"));
		vo.setStaffDesc(rs.getString("staff_desc"));
		vo.setMenuCode(rs.getString("menu_code"));
		vo.setBusinessId(rs.getString("business_id"));//严俊波 2010/4/19
		vo.setCountyType(rs.getString("county_type"));
		vo.setOrgManager(rs.getString("org_manager"));
		vo.setGender(rs.getString("gender"));
		
		vo.setChannelSegmentId(rs.getString("channel_segment_id"));
		vo.setDevUserBelong(rs.getString("dev_user_belong"));
	}

	protected void populateDetailDto(StaffVO vo, ResultSet rs)
			throws SQLException {
		populateDto(vo, rs);
		vo.setDevOrgId(rs.getString("dev_org_id"));
		if( vo.getDevOrgId() != null && !"".equals(vo.getDevOrgId())){
			OrganizationDAO orgdao = OrganizationDAOFactory.getOrganizationDAO();
			OrganizationVO orgvo = orgdao.findByPrimaryKey(vo.getDevOrgId());
			if( orgvo != null && !"".equals(orgvo.getOrgName())){
				vo.setDevOrgName(orgvo.getOrgName());
			}
		}
		
		vo.setOrgPartyId(rs.getString("party_id"));
		vo.setOrgPartyName(rs.getString("org_name"));
		vo.setPartyName(rs.getString("party_name"));
		vo.setPassword(rs.getString("password"));
		vo.setPartyId(rs.getString("party_id"));
		vo.setOrgPartyId(rs.getString("org_party_id"));
		vo.setEffDate(DAOUtils.getFormatedDate(rs.getDate("eff_date")));
		vo.setExpDate(DAOUtils.getFormatedDate(rs.getDate("exp_date")));
		vo.setGender(rs.getString("gender"));
		vo.setBirthPlace(rs.getString("birth_place"));
		vo.setBirthDate(DAOUtils.getFormatedDate(rs.getDate("birth_date")));
		vo.setMaritalStatus(rs.getString("marital_status"));
		vo.setSkill(rs.getString("skill"));
		vo.setState(rs.getString("state"));
		vo.setStateDate(DAOUtils.getFormatedDate(rs.getDate("state_date")));
		vo.setPartyRoleType(rs.getString("party_role_type"));
		vo.setOrgManager(rs.getString("org_manager"));
		vo.setOfficeId(rs.getString("office_id"));
		vo.setCreateDate(DAOUtils.getFormatedDate(rs.getDate("create_date")));
		vo.setOrgPartyId(rs.getString("org_party_id"));

		vo.setPwdvalidtype(rs.getString("pwdvalidtype"));
		vo.setUpdateTime(DAOUtils.getFormatedDate(rs.getDate("update_time")));
		vo.setLoginStatus(rs.getString("login_status"));
		vo.setLoginCount(rs.getString("login_count"));
		vo.setLimitCount(rs.getString("limit_count"));

		vo.setOrgPartyName(rs.getString("org_name"));
		vo.setLanId(rs.getString("lan_id"));
		
		// add [bob] 2010/4/16
		vo.setBusinessId(rs.getString("business_id"));
		vo.setCountyType(rs.getString("county_type"));
		vo.setOrgManager(rs.getString("org_manager"));
		vo.setGender(rs.getString("gender"));

	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		StaffVO vo = new StaffVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord出错", this);
			throw new DAOSystemException("SQLException while populateDto:\n",
					se);
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
			SQL = SQL_SELECT + " AND " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			return fetchMultiResults(rs);
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		((StaffVO)vo).setStaffCode(((StaffVO)vo).getStaffCode().toUpperCase());
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			String sql = "INSERT INTO "
					+ DAOSQLUtils.getTableName("STAFF")
					//+ "  ( party_role_id,staff_code,staff_desc,menu_code,channel_segment_id, dev_user_belong ) VALUES ( ?,?,?,?,?,? )";
					+ "  ( party_role_id,staff_code,staff_desc,menu_code,channel_segment_id, dev_user_belong,business_id,county_type ) VALUES ( ?,?,?,?,?,?,?,? )";
			stmt = conn.prepareStatement(sql);
			
			int index = 1;
			if ("".equals(((StaffVO) vo).getPartyRoleId())) {
				((StaffVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, ((StaffVO) vo).getPartyRoleId());
			stmt.setString(index++, ((StaffVO) vo).getStaffCode());
			stmt.setString(index++, ((StaffVO) vo).getStaffDesc());
			stmt.setString(index++, ((StaffVO) vo).getMenuCode());
			if("".equals(((StaffVO) vo).getChannelSegmentId())){
				((StaffVO)vo).setChannelSegmentId("-1");
			}
			stmt.setString(index++, ((StaffVO) vo).getChannelSegmentId());
			if("".equals(((StaffVO)vo).getDevUserBelong())){
				((StaffVO)vo).setDevUserBelong(null);
			}
			stmt.setString(index++,((StaffVO)vo).getDevUserBelong());
			stmt.setString(index++,((StaffVO)vo).getBusinessId());
			stmt.setString(index++,((StaffVO)vo).getCountyType());
			
//			System.out.println("sssssss => " + ((StaffVO)vo).getCountyType());
//			System.out.println("sssssss2 => " + ((StaffVO)vo).getCountyType());
			
			int rows = stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(SQL_INSERT, this);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ SQL_INSERT, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update(String pparty_role_id, StaffVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE STAFF SET party_role_id = ?,staff_code = ?,staff_desc = ?,menu_code = ?, channel_segment_id = ?, dev_user_belong = ?,business_id = ?, county_type = ? ");
			sql.append(" WHERE  party_role_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((StaffVO) vo).getPartyRoleId())) {
				((StaffVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, vo.getPartyRoleId());

			stmt.setString(index++, vo.getStaffCode());
			stmt.setString(index++, vo.getStaffDesc());
			stmt.setString(index++, vo.getMenuCode());
			stmt.setString(index++, vo.getChannelSegmentId());
			if( "".equals(vo.getDevUserBelong())){
				vo.setDevUserBelong(null);
			}
			stmt.setString(index++,vo.getDevUserBelong());
			stmt.setString(index++, vo.getBusinessId());	//严俊波 2010/4/16
			stmt.setString(index++, vo.getCountyType());
			stmt.setString(index++, pparty_role_id);
			
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE STAFF SET party_role_id = ?,staff_code = ?,staff_desc = ?,menu_code = ?, channel_segment_id = ?, dev_user_belong = ?,business_id = ?, county_type = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((StaffVO) vo).getPartyRoleId())) {
				((StaffVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, ((StaffVO) vo).getPartyRoleId());
			stmt.setString(index++, ((StaffVO) vo).getStaffCode());
			stmt.setString(index++, ((StaffVO) vo).getStaffDesc());
			stmt.setString(index++, ((StaffVO) vo).getMenuCode());
			stmt.setString(index++, ((StaffVO) vo).getChannelSegmentId());
			if( "".equals(((StaffVO)vo).getDevUserBelong())){
				((StaffVO)vo).setDevUserBelong(null);
			}
			stmt.setString(index++, ((StaffVO)vo).getDevUserBelong());
			stmt.setString(index++, ((StaffVO)vo).getBusinessId());	//严俊波 2010/4/16
			stmt.setString(index++, ((StaffVO)vo).getCountyType());
			int rows = stmt.executeUpdate();
			if (rows > 0) {
				bResult = true;
			}
		} catch (SQLException se) {
			Debug.print(sql.toString(), this);
			throw new DAOSystemException("SQLException while update sql:\n"
					+ sql.toString(), se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public long countBySql(String sql) throws DAOSystemException {
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

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong("COL_COUNTS");
			}
		} catch (SQLException se) {
			se.printStackTrace();
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
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
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete(String pparty_role_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pparty_role_id);
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL_DELETE, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL_DELETE, se);
		} finally {
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
		return new StaffVO();
	}

	private String buildCountSQLByVO(StaffVO voStaff) {
		StringBuffer sbSql = new StringBuffer(
				"SELECT count(*) FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o WHERE s.party_role_id=pr.party_role_id AND pr.s_party_id=i.party_id AND pr.s_party_id=p.party_id AND pr.org_party_id=o.party_id ");
		if (voStaff.getPartyRoleId() != null
				&& !voStaff.getPartyRoleId().equals("")) {
			sbSql.append(" AND s.party_role_id = " + voStaff.getPartyRoleId());
		}
		if (voStaff.getStaffCode() != null
				&& !voStaff.getStaffCode().equals("")) {
			sbSql.append(" AND s.staff_code  like '%" + voStaff.getStaffCode()
					+ "%'");
		}
		if (voStaff.getOrgPartyId() != null
				&& !voStaff.getOrgPartyId().equals("")) {
			if (voStaff.getScope() != null && voStaff.getScope().equals("1")) // 搜索子组织
			{
				OrganizationDAO organizationDao = OrganizationDAOFactory
						.getOrganizationDAO();
				OrganizationVO organizationVO = organizationDao
						.findByPrimaryKey(voStaff.getOrgPartyId());
				sbSql.append(" AND (pr.org_party_id = "
						+ voStaff.getOrgPartyId() + " OR (o.path_code like '%"
						+ organizationVO.getPathCode() + "%'))");
			} else {
				sbSql.append(" AND pr.org_party_id =  "
						+ voStaff.getOrgPartyId());
			}
		}
		return sbSql.toString();
	}

	private long getRecordCount(StaffVO voStaff) {
		String str = "select count(*)"
				+ " FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o,CHANNEL_SEGMENT seg "
				+ " WHERE s.party_role_id=pr.party_role_id "
				+ " AND pr.s_party_id=i.party_id "
				+ " AND pr.s_party_id=p.party_id  "
				+ " AND pr.org_party_id=o.party_id "
				+ " AND seg.CHANNEL_SEGMENT_ID = s.channel_segment_id ";
		StringBuffer sbSql = new StringBuffer(str);
		if( voStaff.getPartyRoleId() != null && !"".equals(voStaff.getPartyRoleId())){
			sbSql.append( " AND s.party_role_id in ( " + voStaff.getPartyRoleId() + " ) ") ;
		}
		if (voStaff.getState() != null && !"".equals(voStaff.getState())) {
			sbSql.append("AND p.state = '" + voStaff.getState() + "'");
		}
		if (voStaff.getStaffCode() != null
				&& !voStaff.getStaffCode().equals("")) {
			sbSql.append(" AND s.staff_code like '%" + voStaff.getStaffCode()
					+ "%'");
		}
		if (voStaff.getPartyName() != null
				&& !voStaff.getPartyName().equals("")) {
			sbSql.append(" AND p.party_name like '%" + voStaff.getPartyName()
					+ "%'");
		}
		if (voStaff.getOrgPartyId() != null
				&& !voStaff.getOrgPartyId().equals("")) {
			if (voStaff.getScope() != null && voStaff.getScope().equals("1")) { // 搜索子组织
				OrganizationDAO organizationDao = OrganizationDAOFactory
						.getOrganizationDAO();
				OrganizationVO organizationVO = organizationDao
						.findByPrimaryKey(voStaff.getOrgPartyId());
				if (!"".equals(organizationVO.getPathCode())) {
					sbSql.append(" AND (pr.org_party_id = "
							+ voStaff.getOrgPartyId()
							+ " OR (o.path_code like '%"
							+ organizationVO.getPathCode() + "%'))");
				} else {
					sbSql.append(" AND pr.org_party_id =  "
							+ voStaff.getOrgPartyId());
				}
			} else {
				sbSql.append(" AND pr.org_party_id =  "
						+ voStaff.getOrgPartyId());
			}
		}
		return this.countBySql(sbSql.toString());
	}
	//add by lpy 071009
	private long getStaffPromRecordCount(StaffVO voStaff) {
		String str = "select count(*)"
				+ " FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o,CHANNEL_SEGMENT seg, cust_manager cu"
				+ " WHERE s.party_role_id=pr.party_role_id "
				+ " AND pr.s_party_id=i.party_id "
				+ " AND pr.s_party_id=p.party_id  "
				+ " AND pr.org_party_id=o.party_id "
				+ " AND seg.CHANNEL_SEGMENT_ID = s.channel_segment_id "
				+ " AND s.party_role_id=cu.party_role_id ";
		StringBuffer sbSql = new StringBuffer(str);
		if( voStaff.getPartyRoleId() != null && !"".equals(voStaff.getPartyRoleId())){
			sbSql.append( " AND s.party_role_id in ( " + voStaff.getPartyRoleId() + " ) ") ;
		}
		if (voStaff.getState() != null && !"".equals(voStaff.getState())) {
			sbSql.append("AND p.state = '" + voStaff.getState() + "'");
		}
		if (voStaff.getStaffCode() != null
				&& !voStaff.getStaffCode().equals("")) {
			sbSql.append(" AND s.staff_code like '%" + voStaff.getStaffCode()
					+ "%'");
		}
		if (voStaff.getPartyName() != null
				&& !voStaff.getPartyName().equals("")) {
			sbSql.append(" AND p.party_name like '%" + voStaff.getPartyName()
					+ "%'");
		}
		
		if (voStaff.getPartyName() != null
				&& !voStaff.getPartyName().equals("")) {
			sbSql.append(" AND p.party_name like '%" + voStaff.getPartyName()
					+ "%'");
		}
		
		if (voStaff.getPromotionType() != null
				&& !voStaff.getPromotionType().equals("")) {
			sbSql.append(" AND cu.manager_type='" + voStaff.getPromotionType()
					+ "'");
		}
		
		if (voStaff.getOrgPartyId() != null
				&& !voStaff.getOrgPartyId().equals("")) {
			if (voStaff.getScope() != null && voStaff.getScope().equals("1")) { // 搜索子组织
				OrganizationDAO organizationDao = OrganizationDAOFactory
						.getOrganizationDAO();
				OrganizationVO organizationVO = organizationDao
						.findByPrimaryKey(voStaff.getOrgPartyId());
				if (!"".equals(organizationVO.getPathCode())) {
					sbSql.append(" AND (pr.org_party_id = "
							+ voStaff.getOrgPartyId()
							+ " OR (o.path_code like '%"
							+ organizationVO.getPathCode() + "%'))");
				} else {
					sbSql.append(" AND pr.org_party_id =  "
							+ voStaff.getOrgPartyId());
				}
			} else {
				sbSql.append(" AND pr.org_party_id =  "
						+ voStaff.getOrgPartyId());
			}
		}
		return this.countBySql(sbSql.toString());
	}
	private String buildSelectSQLByVO(StaffVO voStaff ) {
		String str = "SELECT pr.dev_org_id, s.party_role_id, s.staff_code, s.staff_desc,s.menu_code,s.channel_segment_id,s.dev_user_belong, s.business_id,s.county_type,"//add s.business_id,s.county_type [bob]2010/4/16
				+ " i.party_id, i.gender, i.birth_place, i.birth_date, i.marital_status, i.skill, "
				+ " pr.party_role_name as party_name, p.eff_date, p.state, p.state_date, "
				+ " o.org_name,  "
				+ " pr.org_party_id, pr.party_role_type, pr.org_manager,pr.create_date, pr.pwdvalidtype, pr.update_time, pr.login_status, pr.login_count, pr.limit_count, pr.exp_date,pr.office_id,pr.password, "
				+ " seg.CHANNEL_SEGMENT_NAME,pr.lan_id "
				+ " FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o,CHANNEL_SEGMENT seg "
				+ " WHERE s.party_role_id=pr.party_role_id "
				+ " AND pr.s_party_id=i.party_id "
				+ " AND pr.s_party_id=p.party_id  "
				+ " AND pr.org_party_id=o.party_id "
				//+ " and pr.dev_org_id = o2.party_id "
				+ " AND seg.CHANNEL_SEGMENT_ID = s.channel_segment_id ";
		StringBuffer sbSql = new StringBuffer(str);
		if( voStaff.getPartyRoleId() != null && !"".equals(voStaff.getPartyRoleId().trim())){
			sbSql.append( " AND s.party_role_id in ( " + voStaff.getPartyRoleId() + " ) ") ;
		}
		if (voStaff.getState() != null && !"".equals(voStaff.getState())) {
			sbSql.append("AND p.state = '" + voStaff.getState() + "'");
		}
		if (voStaff.getStaffCode() != null
				&& !voStaff.getStaffCode().equals("")) {
			sbSql.append(" AND s.staff_code like '%" + voStaff.getStaffCode()
					+ "%'");
		}
		if (voStaff.getPartyName() != null
				&& !voStaff.getPartyName().equals("")) {
			sbSql.append(" AND p.party_name like '%" + voStaff.getPartyName()
					+ "%'");
		}
		if (voStaff.getOrgPartyId() != null
				&& !voStaff.getOrgPartyId().equals("")) {
			if (voStaff.getScope() != null && voStaff.getScope().equals("1")) // 搜索子组织
			{
				OrganizationDAO organizationDao = OrganizationDAOFactory
						.getOrganizationDAO();
				OrganizationVO organizationVO = organizationDao
						.findByPrimaryKey(voStaff.getOrgPartyId());
				if (!"".equals(organizationVO.getPathCode())) {
					sbSql.append(" AND (pr.org_party_id = "
							+ voStaff.getOrgPartyId()
							+ " OR (o.path_code like '%"
							+ organizationVO.getPathCode() + "%'))");
				} else {
					sbSql.append(" AND pr.org_party_id =  "
							+ voStaff.getOrgPartyId());
				}
			} else {
				sbSql.append(" AND pr.org_party_id =  "
						+ voStaff.getOrgPartyId());
			}
		}
		return sbSql.toString() + " order by s.staff_code ";
	}
	//add by lpy 071009
	private String buildPromSelectSQLByVO(StaffVO voStaff ) {
		String str = "SELECT s.party_role_id, s.staff_code, s.staff_desc,s.menu_code,s.channel_segment_id,s.dev_user_belong, "
				+ " i.party_id, i.gender, i.birth_place, i.birth_date, i.marital_status, i.skill, "
				+ " pr.party_role_name as party_name, p.eff_date, p.state, p.state_date, "
				+ " o.org_name, "
				+ " pr.org_party_id, pr.party_role_type, pr.org_manager,pr.create_date, pr.pwdvalidtype, pr.update_time, pr.login_status, pr.login_count, pr.limit_count, pr.exp_date,pr.office_id,pr.password, "
				+ " seg.CHANNEL_SEGMENT_NAME,pr.lan_id "
				+ " FROM STAFF s, INDIVIDUAL i, PARTY_ROLE pr, PARTY p, ORGANIZATION o,CHANNEL_SEGMENT seg,CUST_MANAGER CU "
				+ " WHERE s.party_role_id=pr.party_role_id "
				+ " AND pr.s_party_id=i.party_id "
				+ " AND pr.s_party_id=p.party_id  "
				+ " AND pr.org_party_id=o.party_id "
				+ " AND seg.CHANNEL_SEGMENT_ID = s.channel_segment_id "
				+ " AND s.party_role_id=cu.party_role_id ";
		StringBuffer sbSql = new StringBuffer(str);
		if( voStaff.getPartyRoleId() != null && !"".equals(voStaff.getPartyRoleId().trim())){
			sbSql.append( " AND s.party_role_id in ( " + voStaff.getPartyRoleId() + " ) ") ;
		}
		if (voStaff.getState() != null && !"".equals(voStaff.getState())) {
			sbSql.append("AND p.state = '" + voStaff.getState() + "'");
		}
		if (voStaff.getStaffCode() != null
				&& !voStaff.getStaffCode().equals("")) {
			sbSql.append(" AND s.staff_code like '%" + voStaff.getStaffCode()
					+ "%'");
		}
		if (voStaff.getPartyName() != null
				&& !voStaff.getPartyName().equals("")) {
			sbSql.append(" AND p.party_name like '%" + voStaff.getPartyName()
					+ "%'");
		}
		if (voStaff.getPromotionType() != null
				&& !voStaff.getPromotionType().equals("")) {
			sbSql.append(" AND cu.manager_type='" + voStaff.getPromotionType()
					+ "'");
		}
		if (voStaff.getOrgPartyId() != null
				&& !voStaff.getOrgPartyId().equals("")) {
			if (voStaff.getScope() != null && voStaff.getScope().equals("1")) // 搜索子组织
			{
				OrganizationDAO organizationDao = OrganizationDAOFactory
						.getOrganizationDAO();
				OrganizationVO organizationVO = organizationDao
						.findByPrimaryKey(voStaff.getOrgPartyId());
				if (!"".equals(organizationVO.getPathCode())) {
					sbSql.append(" AND (pr.org_party_id = "
							+ voStaff.getOrgPartyId()
							+ " OR (o.path_code like '%"
							+ organizationVO.getPathCode() + "%'))");
				} else {
					sbSql.append(" AND pr.org_party_id =  "
							+ voStaff.getOrgPartyId());
				}
			} else {
				sbSql.append(" AND pr.org_party_id =  "
						+ voStaff.getOrgPartyId());
			}
		}
		return sbSql.toString() + " order by s.staff_code ";
	}
	public PageModel findByStaffVO(StaffVO voStaff,int pageIndex, int pageSize ) throws DAOSystemException {

		PageModel pageModel = new PageModel();
//		计算 totalCount
		int totalCount = (new Long (getRecordCount(voStaff))).intValue() ;
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
		String SQL = buildSelectSQLByVO(voStaff);
		
		SQL = DAOSQLUtils.getFilterSQL(SQL) ;
		String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
		if("ORACLE".equalsIgnoreCase(databaseType)){
			SQL = DAOSQLUtils.doPreFilter(false,SQL,pageIndex,pageSize);
		}
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			stmt = conn
					.prepareStatement(SQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			if("ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					StaffVO vo = new StaffVO();
					populateDetailDto(vo, rs);
					resultList.add(vo);
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
		         
				int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize()+1;
				rs.absolute(locationInt);
				int count = 0;
				
				do{
					StaffVO vo = new StaffVO();
					populateDetailDto(vo, rs);
					resultList.add(vo);
					count ++;
				}while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;
			
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public PageModel findByStaffPromotionVO(StaffVO voStaff,int pageIndex, int pageSize ) throws DAOSystemException {

		PageModel pageModel = new PageModel();
//		计算 totalCount
		int totalCount = (new Long (getStaffPromRecordCount(voStaff))).intValue() ;
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
		String SQL = buildPromSelectSQLByVO(voStaff);
		
		SQL = DAOSQLUtils.getFilterSQL(SQL) ;
		String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
		if("ORACLE".equalsIgnoreCase(databaseType)){
			SQL = DAOSQLUtils.doPreFilter(false,SQL,pageIndex,pageSize);
		}
		
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(SQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			if("ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					StaffVO vo = new StaffVO();
					populateDetailDto(vo, rs);
					resultList.add(vo);
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
		         
				int locationInt = (pageModel.getPageIndex() - 1) * pageModel.getPageSize()+1;
				rs.absolute(locationInt);
				int count = 0;
				
				do{
					StaffVO vo = new StaffVO();
					populateDetailDto(vo, rs);
					resultList.add(vo);
					count ++;
				}while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;
			
		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public List findByCond(String whereCond, QueryFilter queryFilter)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT + " AND " + whereCond;
		String filterSQL = SQL;
		if (queryFilter != null) {
			filterSQL = queryFilter.doPreFilter(SQL);
		}
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = filterSQL;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			List retList = null;
			if (queryFilter != null) {
				retList = queryFilter.doPostFilter(rs, this);
			} else {
				retList = fetchMultiResults(rs);
			}
			return retList;
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public ArrayList getPrivs(String pparty_role_id) throws DAOSystemException {
		PrivDAO daoPriv = PrivDAOFactory.getPrivDAO();
		String sql = "SELECT priv.privilege_id, priv.parent_prg_id, priv.privilege_name, priv.privilege_type, priv.privilege_desc, priv.privilege_code, priv.path_code FROM "
				+ DAOSQLUtils.getTableName("PRIVILEGE")
				+ " priv, "
				+ DAOSQLUtils.getTableName("STAFF_PRIVILEGE")
				+ " sp WHERE priv.privilege_id=sp.privilege_id AND sp.party_role_id=? UNION SELECT priv.privilege_id, priv.parent_prg_id, priv.privilege_name, priv.privilege_type, priv.privilege_desc, priv.privilege_code, priv.path_code FROM "
				+ DAOSQLUtils.getTableName("PRIVILEGE")
				+ " priv, "
				+ DAOSQLUtils.getTableName("ROLE_PRIVILEGE")
				+ " rp, "
				+ DAOSQLUtils.getTableName("STAFF_ROLE")
				+ " sr WHERE priv.privilege_id=rp.privilege_id AND rp.role_id=sr.role_id AND sr.party_role_id=? ORDER BY priv.path_code";
		return daoPriv.findBySqlNoFilter(sql, new String[] { pparty_role_id,
				pparty_role_id });
	}
	
	public HashMap getSrSysParams() throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = " select IP,PORT,SYS_NAME from COMMON_IPPORT_CFG order by sys_key ";
		HashMap map = new HashMap();
		try{
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			if(conn!=null){
				stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
				rs = stmt.executeQuery();
				if(rs!=null && rs.next()){
					ResultSetMetaData rsm = rs.getMetaData();
					for(int i=0;i<rsm.getColumnCount();i++){
						map.put(rsm.getColumnName(i+1).toUpperCase(),rs.getString(i+1));
					}
				}
			}
			return map;
		}catch(SQLException se){
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
}
