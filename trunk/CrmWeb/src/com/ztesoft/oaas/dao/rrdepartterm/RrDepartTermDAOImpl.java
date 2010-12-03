package com.ztesoft.oaas.dao.rrdepartterm;

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
import com.ztesoft.oaas.vo.RrDepartTermVO;

public class RrDepartTermDAOImpl  implements RrDepartTermDAO {

	private String SQL_SELECT = "SELECT ipaddr,end_ipaddr,party_id,ip_name,valid_date,lan_id,static_state,valid_flag,comments,region_id FROM RR_DEPARTMENT_TERMINAL";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM RR_DEPARTMENT_TERMINAL";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO RR_DEPARTMENT_TERMINAL ( ipaddr,end_ipaddr,party_id,ip_name,valid_date,lan_id,static_state,valid_flag,comments,region_id ) VALUES ( ?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE RR_DEPARTMENT_TERMINAL SET  party_id = ?, ip_name = ?, valid_date = ?, lan_id = ?, static_state = ?, valid_flag = ?, comments = ?, region_id = ? WHERE end_ipaddr = ? AND ipaddr = ? ";

	private String SQL_DELETE = "DELETE FROM RR_DEPARTMENT_TERMINAL WHERE end_ipaddr = ? AND ipaddr = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM RR_DEPARTMENT_TERMINAL ";

	public RrDepartTermDAOImpl() {

	}

	public RrDepartTermVO findByPrimaryKey(String pend_ipaddr, String pipaddr)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT
				+ " WHERE end_ipaddr = ? AND ipaddr = ? ", new String[] {
				pend_ipaddr, pipaddr });
		if (arrayList.size() > 0)
			return (RrDepartTermVO) arrayList.get(0);
		else
			return (RrDepartTermVO) getEmptyVO();
	}

	public ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
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
//			DAOUtils.closeConnection(conn, this);
		}
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			RrDepartTermVO vo = new RrDepartTermVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(RrDepartTermVO vo, ResultSet rs)
			throws SQLException {
		vo.setIpaddr(rs.getString("ipaddr"));
		vo.setEndIpaddr(rs.getString("end_ipaddr"));
		vo.setPartyId(rs.getString("party_id"));
		vo.setIpName(rs.getString("ip_name"));
		vo.setValidDate(rs.getString("valid_date"));
		vo.setLanId(rs.getString("lan_id"));
		vo.setStaticState(rs.getString("static_state"));
		vo.setValidFlag(rs.getString("valid_flag"));
		vo.setComments(rs.getString("comments"));
		vo.setRegionId(rs.getString("region_id"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		RrDepartTermVO vo = new RrDepartTermVO();
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

			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
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
//			DAOUtils.closeConnection(conn, this);
		}
	}

	public void insert(VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			int index = 1;
			stmt.setString(index++, ((RrDepartTermVO) vo).getIpaddr());
			stmt.setString(index++, ((RrDepartTermVO) vo).getEndIpaddr());
			if ("".equals(((RrDepartTermVO) vo).getPartyId())) {
				((RrDepartTermVO) vo).setPartyId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getPartyId());
			stmt.setString(index++, ((RrDepartTermVO) vo).getIpName());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((RrDepartTermVO) vo)
					.getValidDate()));
			if ("".equals(((RrDepartTermVO) vo).getLanId())) {
				((RrDepartTermVO) vo).setLanId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getLanId());
			stmt.setString(index++, ((RrDepartTermVO) vo).getStaticState());
			stmt.setString(index++, ((RrDepartTermVO) vo).getValidFlag());
			stmt.setString(index++, ((RrDepartTermVO) vo).getComments());
			if ("".equals(((RrDepartTermVO) vo).getRegionId())) {
				((RrDepartTermVO) vo).setRegionId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getRegionId());
			int rows = stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(SQL_INSERT, this);
			throw new DAOSystemException("SQLException while insert sql:\n"
					+ SQL_INSERT, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}

	public boolean update(String pend_ipaddr, String pipaddr, RrDepartTermVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			sql
					.append("UPDATE RR_DEPARTMENT_TERMINAL SET ipaddr = ?,end_ipaddr = ?,party_id = ?,ip_name = ?,valid_date = ?,lan_id = ?,static_state = ?,valid_flag = ?,comments = ?,region_id = ?");
			sql.append(" WHERE  end_ipaddr = ? AND ipaddr = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			stmt.setString(index++, vo.getIpaddr());
			stmt.setString(index++, vo.getEndIpaddr());
			if ("".equals(((RrDepartTermVO) vo).getPartyId())) {
				((RrDepartTermVO) vo).setPartyId(null);
			}
			stmt.setString(index++, vo.getPartyId());
			stmt.setString(index++, vo.getIpName());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(vo.getValidDate()));
			if ("".equals(((RrDepartTermVO) vo).getLanId())) {
				((RrDepartTermVO) vo).setLanId(null);
			}
			stmt.setString(index++, vo.getLanId());
			stmt.setString(index++, vo.getStaticState());
			stmt.setString(index++, vo.getValidFlag());
			stmt.setString(index++, vo.getComments());
			if ("".equals(((RrDepartTermVO) vo).getRegionId())) {
				((RrDepartTermVO) vo).setRegionId(null);
			}
			stmt.setString(index++, vo.getRegionId());
			stmt.setString(index++, pend_ipaddr);
			stmt.setString(index++, pipaddr);
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
//			DAOUtils.closeConnection(conn, this);
		}
		return bResult;
	}

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			sql
					.append("UPDATE RR_DEPARTMENT_TERMINAL SET ipaddr = ?,end_ipaddr = ?,party_id = ?,ip_name = ?,valid_date = ?,lan_id = ?,static_state = ?,valid_flag = ?,comments = ?,region_id = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			stmt.setString(index++, ((RrDepartTermVO) vo).getIpaddr());
			stmt.setString(index++, ((RrDepartTermVO) vo).getEndIpaddr());
			if ("".equals(((RrDepartTermVO) vo).getPartyId())) {
				((RrDepartTermVO) vo).setPartyId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getPartyId());
			stmt.setString(index++, ((RrDepartTermVO) vo).getIpName());
			stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((RrDepartTermVO) vo)
					.getValidDate()));
			if ("".equals(((RrDepartTermVO) vo).getLanId())) {
				((RrDepartTermVO) vo).setLanId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getLanId());
			stmt.setString(index++, ((RrDepartTermVO) vo).getStaticState());
			stmt.setString(index++, ((RrDepartTermVO) vo).getValidFlag());
			stmt.setString(index++, ((RrDepartTermVO) vo).getComments());
			if ("".equals(((RrDepartTermVO) vo).getRegionId())) {
				((RrDepartTermVO) vo).setRegionId(null);
			}
			stmt.setString(index++, ((RrDepartTermVO) vo).getRegionId());
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
//			DAOUtils.closeConnection(conn, this);
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
//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
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
//			DAOUtils.closeConnection(conn, this);
		}
		return lCount;
	}

	public long deleteByCond(String whereCond) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		String SQL = "";
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			SQL = SQL_DELETE_BY_WHERE + " WHERE " + whereCond;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
		return rows;
	}

	public long delete(String pend_ipaddr, String pipaddr)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pend_ipaddr);
			stmt.setString(index++, pipaddr);
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL_DELETE, this);
			throw new DAOSystemException("SQLException while deleting sql:\n"
					+ SQL_DELETE, se);
		} finally {
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
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
		return new RrDepartTermVO();
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

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
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
//			DAOUtils.closeConnection(conn, this);
		}
	}

	/**
	 * 根据组织标识查询所有部门终端范围实体
	 * 
	 * @param pparty_id
	 * @return 指定组织标识对应的所有部门终端范围实体列表（RrDepartTermVO构成的ArrayList）
	 * @throws DAOSystemException
	 */
	public ArrayList getRrDepartTermsByParty(String pparty_id)
			throws DAOSystemException {
		return findBySql(SQL_SELECT + " WHERE party_id = ? ",
				new String[] { pparty_id });
	}

	public long deleteByParty(String pparty_id) throws DAOSystemException {
		return deleteByCond(" party_id = " + pparty_id);
	}

	public ArrayList getRrDepartTermsByIP(String ip) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = SQL_SELECT;
		try {

//			//			conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE);
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			ArrayList alRrDepartTerms = new ArrayList();
			
			String inputIpAddr = rebuildIpAddr(ip);//将输入的IP地址转换成为完整格式的IP
			
			String ipCur;
			int recordNumbers = 0;
			
			while (rs.next()) {
				recordNumbers++;

				// 不小于起始IP
				ipCur = rs.getString("ipaddr");
				String ipAddrInDatabase = rebuildIpAddr( ipCur );
				if( ipAddrInDatabase.compareTo( inputIpAddr ) > 0 ){
					continue ;
				}
				
				// 不大于结束IP
				ipCur = rs.getString("end_ipaddr");
				ipAddrInDatabase = rebuildIpAddr( ipCur );
				if( ipAddrInDatabase.compareTo( inputIpAddr) < 0 ){
					continue ;
				}

				RrDepartTermVO voRrDepartTerm = new RrDepartTermVO();
				populateDto(voRrDepartTerm, rs);
				alRrDepartTerms.add(voRrDepartTerm);
				break; // 只获取一个符合条件的记录
			}
			return alRrDepartTerms;
		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ SQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
//			DAOUtils.closeConnection(conn, this);
		}
	}
	
	private String rebuildIpAddr(String ip){
		String[] arrIPSeg = ip.split("\\.");
		for( int i = 0 ; i < arrIPSeg.length ; i ++ ){
			if( arrIPSeg[i].length() == 1 ){
				arrIPSeg[i] = "00" + arrIPSeg[i];
			}
			if( arrIPSeg[i].length() == 2 ){
				arrIPSeg[i] = "0" + arrIPSeg[i] ;
			}
		}
		String newIPAddr = "" ;
		for( int i = 0; i < arrIPSeg.length ; i ++ ){
			newIPAddr += arrIPSeg[i] ;
		}
		return newIPAddr ;
	}

}
