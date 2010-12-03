/**
 * 
 */
package com.ztesoft.oaas.dao.agentprop;

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
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.AgentPropertyVO;

/**
 * @author ÐíÈñºÀ
 *
 */
public class AgentPropertyDAOImpl   implements AgentPropertyDAO {

		private static final String SQL_SELECT = "SELECT PARTY_ID,DEPOSIT_AMOUNT,CEIL_FLAG,UPPER_AMOUNT,SCOPE_FLAG,SERV_GROUP_ID,SETTLED_CHARGE,CHARGE,LAST_SETTLE_DATE,STATE,STATE_DATE,ALARM_AMOUNT FROM AGENT_PROPERTY";
		private static final Object SQL_UPDATE = "UPDATE AGENT_PROPERTY SET DEPOSIT_AMOUNT=?,CEIL_FLAG=?,UPPER_AMOUNT=?,SCOPE_FLAG=?,SERV_GROUP_ID=?,SETTLED_CHARGE=?,CHARGE=?,LAST_SETTLE_DATE=?,STATE=?,STATE_DATE=?,ALARM_AMOUNT WHERE PARTY_ID = ? ";
		private static final Object SQL_UPDATE_COND = "UPDATE AGENT_PROPERTY SET DEPOSIT_AMOUNT=?,CEIL_FLAG=?,UPPER_AMOUNT=?,SCOPE_FLAG=?,SERV_GROUP_ID=?,SETTLED_CHARGE=?,CHARGE=?,LAST_SETTLE_DATE=?,STATE=?,STATE_DATE=?,ALARM_AMOUNT ";
		private static final String SQL_DELETE = "DELETE FROM AGENT_PROPERTY WHERE PARTY_ID = ?";
		private static final String SQL_INSERT = "INSERT INTO AGENT_PROPERTY ( PARTY_ID,DEPOSIT_AMOUNT,CEIL_FLAG,UPPER_AMOUNT,SCOPE_FLAG,SERV_GROUP_ID,SETTLED_CHARGE,CHARGE,LAST_SETTLE_DATE,STATE,STATE_DATE,ALARM_AMOUNT ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?)";
		private static final String SQL_DELETE_BY_WHERE = "DELETE FROM AGENT_PROPERTY ";
		private static final String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM AGENT_PROPERTY";
		private int maxRows;
		
		public AgentPropertyVO findByPrimaryKey(String partyId) throws DAOSystemException {
			ArrayList arrayList = findBySql(SQL_SELECT + " WHERE PARTY_ID = ? ",
					new String[] { partyId });
			if (arrayList.size() > 0)
				return (AgentPropertyVO) arrayList.get(0);
			else
				return null;
		}

	public ArrayList findBySql(String sql, String[] sqlParams) throws DAOSystemException {
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

	public boolean update(String partyId, AgentPropertyVO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append( this.SQL_UPDATE );
			stmt = conn.prepareStatement( DAOSQLUtils.getFilterSQL(sql.toString()) );
			int index = 1;
			if("".equals(vo.getPartyId())){
				vo.setPartyId(null);
			}
			stmt.setString( index++, vo.getDepositAmount() );
			stmt.setString(index++, vo.getCeilFlag());
			stmt.setString(index++,vo.getUpperAmount());
			stmt.setString(index++,vo.getScopeFlag());
			stmt.setString(index++,vo.getServGroupId());
			stmt.setString(index++,vo.getSettledCharge());
			stmt.setString(index++,vo.getCharge());
			stmt.setString(index++,vo.getLastSettleDate());
			stmt.setString(index++,vo.getState());
			stmt.setDate(index++,DAOUtils.parseDateTime(vo.getStateDate()));
			stmt.setString(index++,vo.getAlarmAmount());
			stmt.setString(index++,vo.getPartyId());
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

	public long delete(String partyId) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, partyId);
			rows = stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(DAOSQLUtils.getFilterSQL(SQL_DELETE), this);
			se.printStackTrace();
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL_DELETE, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		AgentPropertyVO vo = new AgentPropertyVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord³ö´í", this);
			throw new DAOSystemException("SQLException while populateDto:\n",
					se);
		}
		return vo;
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

			int index = 1;
			if ("".equals(((AgentPropertyVO)vo).getPartyId())) {
				((AgentPropertyVO)vo).setPartyId(null);
			}

			stmt.setString(index++, ((AgentPropertyVO) vo).getPartyId());
			stmt.setString(index++, ((AgentPropertyVO) vo).getDepositAmount());
			stmt.setString(index++, ((AgentPropertyVO) vo).getCeilFlag());
			stmt.setString(index++, ((AgentPropertyVO) vo).getUpperAmount());
			stmt.setString(index++, ((AgentPropertyVO) vo).getScopeFlag());
			stmt.setString(index++, ((AgentPropertyVO) vo).getServGroupId());
			stmt.setString(index++, ((AgentPropertyVO) vo).getSettledCharge());
			stmt.setString(index++, ((AgentPropertyVO) vo).getCharge());
			stmt.setString(index++, ((AgentPropertyVO) vo).getLastSettleDate());
			stmt.setString(index++, ((AgentPropertyVO) vo).getState());
			stmt.setDate(index++, DAOUtils.parseDateTime(((AgentPropertyVO) vo).getStateDate()));
			stmt.setString(index++, ((AgentPropertyVO) vo).getAlarmAmount());
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

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
        PreparedStatement stmt = null;
        boolean bResult = false;
        StringBuffer sql = new StringBuffer();
        try
        {

            conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
            sql.append(this.SQL_UPDATE_COND);
            sql.append(" where " ).append(whereCond);
            stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
                    .toString()));
			int index = 1;
			if ("".equals(((AgentPropertyVO)vo).getPartyId())) {
				((AgentPropertyVO)vo).setPartyId(null);
			}
			stmt.setString(index++, ((AgentPropertyVO) vo).getDepositAmount());
			stmt.setString(index++, ((AgentPropertyVO) vo).getCeilFlag());
			stmt.setString(index++, ((AgentPropertyVO) vo).getUpperAmount());
			stmt.setString(index++, ((AgentPropertyVO) vo).getScopeFlag());
			stmt.setString(index++, ((AgentPropertyVO) vo).getServGroupId());
			stmt.setString(index++, ((AgentPropertyVO) vo).getSettledCharge());
			stmt.setString(index++, ((AgentPropertyVO) vo).getCharge());
			stmt.setString(index++, ((AgentPropertyVO) vo).getLastSettleDate());
			stmt.setString(index++, ((AgentPropertyVO) vo).getState());
			stmt.setDate(index++, DAOUtils.parseDateTime(((AgentPropertyVO) vo).getStateDate()));
			stmt.setString(index++, ((AgentPropertyVO) vo).getAlarmAmount());
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
			SQL = DAOSQLUtils.getFilterSQL(SQL);
			stmt = conn.prepareStatement(SQL);
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getLong("COL_COUNTS");
			}
		} catch (SQLException se) {
			Debug.print(DAOSQLUtils.getFilterSQL(SQL), this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}

	public VO getEmptyVO() {
		return null;
	}

	public List findByCond(String whereCond, QueryFilter queryFilter) throws DAOSystemException {
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

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			AgentPropertyVO vo = new AgentPropertyVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}
	
	protected void populateDto(AgentPropertyVO vo, ResultSet rs) throws SQLException {
		vo.setPartyId(rs.getString("PARTY_ID"));
		vo.setDepositAmount("DEPOSIT_AMOUNT");
		vo.setCeilFlag(rs.getString("CEIL_FLAG"));
		vo.setUpperAmount(rs.getString("UPPER_AMOUNT"));
		vo.setScopeFlag(rs.getString("SCOPE_FLAG"));
		vo.setServGroupId(rs.getString("SERV_GROUP_ID"));
		vo.setSettledCharge(rs.getString("SETTLED_CHARGE"));
		vo.setCharge(rs.getString("CHARGE"));
		vo.setLastSettleDate(rs.getString("LAST_SETTLE_DATE"));
		vo.setState(rs.getString("STATE"));
		vo.setStateDate(rs.getString("STATE_DATE"));
		vo.setAlarmAmount(rs.getString("ALARM_AMOUNT"));
	}
}
