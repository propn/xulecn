package com.ztesoft.oaas.dao.partyrole;

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
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAO;
import com.ztesoft.oaas.dao.mpdepart.MpDepartDAOFactory;
import com.ztesoft.oaas.dao.organization.OrganizationDAO;
import com.ztesoft.oaas.dao.organization.OrganizationDAOFactory;
import com.ztesoft.oaas.vo.MpDepartVO;
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.PartyRoleVO;

public class PartyRoleDAOImpl   implements PartyRoleDAO {

	private String SQL_SELECT = "SELECT party_role_id,s_party_id,party_id,org_party_id,party_role_name,party_role_type,org_manager,create_date,memo,state,eff_date,exp_date,office_id,password,PWDVALIDTYPE,update_time,login_status,login_count,limit_count,incor_starttime,lan_id,dev_org_id FROM PARTY_ROLE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PARTY_ROLE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PARTY_ROLE ( party_role_id,party_id,org_party_id,party_role_name,party_role_type,org_manager,create_date,memo,state,eff_date,exp_date,office_id,password,s_party_id,PWDVALIDTYPE,update_time,login_status,login_count,limit_count,incor_starttime, org_maneger, lan_id, dev_org_id ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

	private String SQL_UPDATE = "UPDATE PARTY_ROLE SET  party_id = ?, org_party_id = ?, party_role_name = ?, party_role_type = ?, org_manager = ?, create_date = ?, memo = ?, state = ?, eff_date = ?, exp_date = ?, office_id = ?, password = ?,S_PARTY_ID=?,PWDVALIDTYPE=?,update_time=?,login_status=?,login_count=?,limit_count=?,incor_starttime=?,org_maneger=? WHERE party_role_id = ? ";

	private String SQL_DELETE = "DELETE FROM PARTY_ROLE WHERE party_role_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PARTY_ROLE ";

	public PartyRoleDAOImpl() {

	}

	public PartyRoleVO findByPrimaryKey(String pparty_role_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT
				+ " WHERE party_role_id = ? ", new String[] { pparty_role_id });
		if (arrayList.size() > 0)
			return (PartyRoleVO) arrayList.get(0);
		else
			//return (PartyRoleVO) getEmptyVO();
			return null ;
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
			Debug.print(sql, this);
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
			PartyRoleVO vo = new PartyRoleVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(PartyRoleVO vo, ResultSet rs)
			throws SQLException {
		vo.setDevOrgId(rs.getString("dev_org_id"));
		vo.setPartyRoleId(rs.getString("party_role_id"));
		vo.setPartyId(rs.getString("party_id"));
		vo.setSPartyId(rs.getString("s_party_id"));
		vo.setOrgPartyId(rs.getString("org_party_id"));
		vo.setPartyRoleName(rs.getString("party_role_name"));
		vo.setPartyRoleType(rs.getString("party_role_type"));
		vo.setOrgManager(rs.getString("org_manager"));
		vo.setCreateDate(DAOUtils.getFormatedDate(rs.getDate("create_date")));
		vo.setMemo(rs.getString("memo"));
		vo.setState(rs.getString("state"));
		vo.setEffDate(DAOUtils.getFormatedDate(rs.getDate("eff_date")));
		vo.setExpDate(DAOUtils.getFormatedDate(rs.getDate("exp_date")));
		vo.setOfficeId(rs.getString("office_id"));
		vo.setPassword(rs.getString("password"));
		vo.setSPartyId(rs.getString("s_party_id"));
		
		vo.setPwdValidType( rs.getString("PWDVALIDTYPE"));
		vo.setUpdateTime(rs.getString("UPDATE_TIME"));
		vo.setLoginStatus(rs.getString("LOGIN_STATUS"));
		vo.setLoginCount( rs.getString("LOGIN_COUNT"));
		vo.setLimitCount(rs.getString("LIMIT_COUNT"));
		vo.setIncorStarttime(rs.getString("INCOR_STARTTIME"));
		vo.setLanId(rs.getString("LAN_ID"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		PartyRoleVO vo = new PartyRoleVO();
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
			SQL = SQL_SELECT + " WHERE " + whereCond;
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
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			//add 2008-11-6
			//如果班组是代理商班组，那么更新party_role表dev_org_id为他对应的班组id。如果员工不是代理商班组，则更新为本地网id。
			OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
			MpDepartDAO departmentDao = MpDepartDAOFactory.getMpDepartDAO();
			OrganizationVO orgVo = orgDao.findByPrimaryKey(((PartyRoleVO)vo).getOrgPartyId());
			if( orgVo.getPartyId() != null && !"".equals(orgVo.getPartyId())){
				if( "6".equals(orgVo.getOrgTypeId())){//如果所在组织为班组
					MpDepartVO mpVo = departmentDao.findByPrimaryKey(orgVo.getPartyId());
					if( mpVo != null && mpVo.getPartyId()!=null && !"".equals(mpVo.getPartyId())){
						String departType = mpVo.getDepartType();
						if( "05".equals(departType)){
							((PartyRoleVO)vo).setDevOrgId(mpVo.getPartyId());
						}else{
							((PartyRoleVO)vo).setDevOrgId(((PartyRoleVO)vo).getLanId());
						}
					}
				}
			}//add end here

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			String seqId = smDAO
			.getNextSequence(vo.getTableName(), "PARTY_ROLE_ID");
	
			((PartyRoleVO) vo).setPartyRoleId(seqId);//主键
	
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			
			((PartyRoleVO) vo).setCreateDate(DAOUtils.getFormatedDate());

			int index = 1;

			if ("".equals(((PartyRoleVO) vo).getPartyRoleId())) {
				((PartyRoleVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleId());// 参与人角色ID

			if ("".equals(((PartyRoleVO) vo).getOrgPartyId())) {
				((PartyRoleVO) vo).setOrgPartyId(null);
			}
			((PartyRoleVO) vo).setPartyId(((PartyRoleVO) vo).getOrgPartyId());

			stmt.setString(index++, ((PartyRoleVO) vo).getOrgPartyId());// Party_ID 相当于ORG_PARTY_ID
			stmt.setString(index++, ((PartyRoleVO) vo).getOrgPartyId());// 所属组织

			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleName());// 参与人名称
			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleType());// 参与人类型
			stmt.setString(index++, ((PartyRoleVO) vo).getOrgManager());// 是否管理者
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO) vo)
					.getCreateDate()));// 创建日期
			stmt.setString(index++, ((PartyRoleVO) vo).getMemo());// 备注
			stmt.setString(index++, ((PartyRoleVO) vo).getState());// 状态
			
			if( "".equals(((PartyRoleVO)vo).getEffDate() )){
				((PartyRoleVO)vo).setEffDate(DateFormatUtils.getFormatedDateTime());
			}
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO) vo)
					.getEffDate()));// 生效日期
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO) vo)
					.getExpDate()));// 失效日期
			stmt.setString(index++, ((PartyRoleVO) vo).getOfficeId());// 办公地点
			
			stmt.setString(index++, ((PartyRoleVO) vo).getPassword());// 密码
			
			stmt.setString(index++, ((PartyRoleVO) vo).getSPartyId());// 参与人ID
			
			stmt.setString(index++, ((PartyRoleVO)vo).getPwdValidType());//密码有效期类型
			
			if( "".equals(((PartyRoleVO)vo).getUpdateTime())){//密码上次修改时间,如果为空,则使用当前时间
				((PartyRoleVO)vo).setUpdateTime(DateFormatUtils.getFormatedDateTime());
			}
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO)vo).getUpdateTime()));//密码上次修改时间
			
			stmt.setString(index++,((PartyRoleVO)vo).getLoginStatus());//登录状态
			stmt.setString(index++,((PartyRoleVO)vo).getLoginCount());//登录次数
			stmt.setString(index++,((PartyRoleVO)vo).getLimitCount());//登录次数限制
			stmt.setDate(index++,null);//输入错误密码时间
			stmt.setString(index++, ((PartyRoleVO)vo).getOrgManager());

			stmt.setString(index++, ((PartyRoleVO)vo).getLanId());
			stmt.setString(index++, ((PartyRoleVO)vo).getDevorgid());
			
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

	public boolean update(String pparty_role_id, PartyRoleVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			//add 2008-11-6
			//如果班组是代理商班组，那么更新party_role表dev_org_id为他对应的班组id。如果员工不是代理商班组，则更新为本地网id。
			OrganizationDAO orgDao = OrganizationDAOFactory.getOrganizationDAO();
			MpDepartDAO departmentDao = MpDepartDAOFactory.getMpDepartDAO();
			
			OrganizationVO orgVo = orgDao.findByPrimaryKey(vo.getOrgPartyId());
			if( orgVo.getPartyId() != null && !"".equals(orgVo.getPartyId())){
				if( "6".equals(orgVo.getOrgTypeId())){//如果所在组织为班组
					MpDepartVO mpVo = departmentDao.findByPrimaryKey(orgVo.getPartyId());
					if( mpVo != null && mpVo.getPartyId()!=null && !"".equals(mpVo.getPartyId())){
						String departType = mpVo.getDepartType();
						if( "05".equals(departType)){
							vo.setDevOrgId(mpVo.getPartyId());
						}else{
							vo.setDevOrgId(vo.getLanId());
						}
					}
				}
			}//add end here
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE PARTY_ROLE SET party_role_id = ?,party_id = ?,org_party_id = ?,party_role_name = ?,party_role_type = ?,org_manager = ?,memo = ?,state = ?,eff_date = ?,exp_date = ?,office_id=?,password=?,s_party_id=?,PWDVALIDTYPE=?,update_time=?,login_status=?,login_count=?,limit_count=?,incor_starttime=?,org_maneger=?,lan_id=?,dev_org_id=? ");
			sql.append(" WHERE  party_role_id = ? ");
			
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			
			if ("".equals(((PartyRoleVO) vo).getPartyRoleId())) {
				((PartyRoleVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, vo.getPartyRoleId());

			stmt.setString(index++, vo.getPartyId());

			if ("".equals(((PartyRoleVO) vo).getOrgPartyId())) {
				((PartyRoleVO) vo).setOrgPartyId(null);
			}
			stmt.setString(index++, vo.getOrgPartyId());
			stmt.setString(index++, vo.getPartyRoleName());
			stmt.setString(index++, vo.getPartyRoleType());
			stmt.setString(index++, vo.getOrgManager());
			stmt.setString(index++, vo.getMemo());
			stmt.setString(index++, vo.getState());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getEffDate()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getExpDate()));
			
			if( "".equals(vo.getOfficeId() )){
				vo.setOfficeId(null);
			}

			stmt.setString(index++, vo.getOfficeId());
			stmt.setString(index++, vo.getPassword());
			
			if ("".equals(((PartyRoleVO) vo).getSPartyId())) {
				((PartyRoleVO) vo).setSPartyId(null);
			}
			stmt.setString(index++, vo.getSPartyId());
			
			stmt.setString(index++,vo.getPwdValidType());
			//stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getUpdateTime()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getUpdateTime()));
			stmt.setString(index++,vo.getLoginStatus());
			stmt.setString(index++,vo.getLoginCount());
			stmt.setString(index++,vo.getLimitCount());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getIncorStarttime()));
			stmt.setString(index++, vo.getOrgManager());
			stmt.setString(index++, vo.getLanId());
			
			stmt.setString(index++,vo.getDevorgid());
			
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
					.append("UPDATE PARTY_ROLE SET party_role_id = ?,party_id = ?,org_party_id = ?,party_role_name = ?,party_role_type = ?,org_manager = ?,memo = ?,state = ?,eff_date = ?,exp_date = ?, office_id = ?, password = ?,PWDVALIDTYPE=?,update_time=?,login_status=?,login_count=?,limit_count=?,incor_starttime=?,org_maneger=?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((PartyRoleVO) vo).getPartyRoleId())) {
				((PartyRoleVO) vo).setPartyRoleId(null);
			}
			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleId());

			if ("".equals(((PartyRoleVO) vo).getSPartyId())) {
				((PartyRoleVO) vo).setSPartyId(null);
			}
			stmt.setString(index++, ((PartyRoleVO) vo).getSPartyId());

			if ("".equals(((PartyRoleVO) vo).getOrgPartyId())) {
				((PartyRoleVO) vo).setOrgPartyId(null);
			}
			stmt.setString(index++, ((PartyRoleVO) vo).getOrgPartyId());
			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleName());
			stmt.setString(index++, ((PartyRoleVO) vo).getPartyRoleType());
			stmt.setString(index++, ((PartyRoleVO) vo).getOrgManager());
			stmt.setString(index++, ((PartyRoleVO) vo).getMemo());
			stmt.setString(index++, ((PartyRoleVO) vo).getState());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO) vo)
					.getEffDate()));
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO) vo)
					.getExpDate()));
			stmt.setString(index++, ((PartyRoleVO) vo).getOfficeId());
			stmt.setString(index++, ((PartyRoleVO) vo).getPassword());
			
			stmt.setString(index++, ((PartyRoleVO)vo).getPwdValidType());
			stmt.setString(index++, ((PartyRoleVO)vo).getUpdateTime());
			stmt.setString(index++,((PartyRoleVO)vo).getLoginStatus());
			stmt.setString(index++,((PartyRoleVO)vo).getLoginCount());
			stmt.setString(index++,((PartyRoleVO)vo).getLimitCount());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((PartyRoleVO)vo).getIncorStarttime()));
			stmt.setString(index++,((PartyRoleVO)vo).getOrgManager());
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
			Debug.print(SQL, this);
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
		return new PartyRoleVO();
	}

	public List findByCond(String whereCond, QueryFilter queryFilter)
			throws DAOSystemException {
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

	/**
	 * 获取指定参与人的所有参与人角色
	 * 
	 * @param pparty_id
	 * @return 指定参与人的所有参与人角色列表（PartyRoleVO构成的ArrayList）
	 * @throws DAOSystemException
	 */
	public ArrayList getPartyRolesByParty(String pparty_id)
			throws DAOSystemException {
		return findBySql(SQL_SELECT + " WHERE party_id = ? ",
				new String[] { pparty_id });
	}

	public long deleteByParty(String pparty_id) throws DAOSystemException {
		return deleteByCond(" party_id = " + pparty_id);
	}
	
	public void insertStaffPassLog(String partyRoleId, String password ) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		String SQL = "INSERT INTO STAFF_PASSLOG VALUES( ?, ? , ?) ";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setString(1, partyRoleId);
			stmt.setTimestamp( 2, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
			stmt.setString(3, password );
			stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
}
