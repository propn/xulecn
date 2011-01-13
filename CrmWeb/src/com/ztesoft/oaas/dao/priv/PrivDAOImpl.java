package com.ztesoft.oaas.dao.priv;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import com.ztesoft.component.common.staticdata.StaticAttrCache;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;
import com.ztesoft.oaas.vo.PrivVO;

public class PrivDAOImpl   implements PrivDAO {

	private String SQL_SELECT = "SELECT privilege_id,parent_prg_id,privilege_name,privilege_type,privilege_desc,privilege_code,path_code FROM PRIVILEGE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM PRIVILEGE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO PRIVILEGE ( privilege_id,parent_prg_id,privilege_name,privilege_type,privilege_desc,privilege_code,path_code ) VALUES ( ?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE PRIVILEGE SET  parent_prg_id = ?, privilege_name = ?, privilege_type = ?, privilege_desc = ?, privilege_code = ?, path_code = ? WHERE privilege_id = ? ";

	private String SQL_DELETE = "DELETE FROM PRIVILEGE WHERE privilege_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM PRIVILEGE ";

	public PrivDAOImpl() {

	}

	public PrivVO findByPrimaryKey(String pprivilege_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(
				SQL_SELECT + " WHERE privilege_id = ? ",
				new String[] { pprivilege_id });
		if (arrayList.size() > 0)
			return (PrivVO) arrayList.get(0);
		else
			return null;
	}

	public ArrayList findBySqlNoFilter(String sql, String[] sqlParams)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(sql);
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

	public ArrayList findBySql(String sql, String[] sqlParams)
			throws DAOSystemException {
		return this.findBySqlNoFilter(DAOSQLUtils.getFilterSQL(sql), sqlParams);
	}

	protected ArrayList fetchMultiResults(ResultSet rs) throws SQLException {
		ArrayList resultList = new ArrayList();
		while (rs.next()) {
			PrivVO vo = new PrivVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(PrivVO vo, ResultSet rs) throws SQLException {
		vo.setPrivId(rs.getString("privilege_id"));
		vo.setParentPrgId(rs.getString("parent_prg_id"));
		vo.setPrivName(rs.getString("privilege_name"));
		vo.setPrivType(rs.getString("privilege_type"));
		vo.setPrivDesc(rs.getString("privilege_desc"));
		vo.setPrivCode(rs.getString("privilege_code"));
		vo.setPathCode(rs.getString("path_code"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		PrivVO vo = new PrivVO();
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

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE PRIVILEGE SET privilege_id = ?,parent_prg_id = ?,privilege_name = ?,privilege_type = ?,privilege_desc = ?,privilege_code = ?,path_code = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((PrivVO) vo).getPrivId())) {
				((PrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, ((PrivVO) vo).getPrivId());
			if ("".equals(((PrivVO) vo).getParentPrgId())) {
				((PrivVO) vo).setParentPrgId(null);
			}
			stmt.setString(index++, ((PrivVO) vo).getParentPrgId());
			stmt.setString(index++, ((PrivVO) vo).getPrivName());
			stmt.setString(index++, ((PrivVO) vo).getPrivType());
			stmt.setString(index++, ((PrivVO) vo).getPrivDesc());
			stmt.setString(index++, ((PrivVO) vo).getPrivCode());
			stmt.setString(index++, ((PrivVO) vo).getPathCode());
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

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public VO getEmptyVO() {
		return new PrivVO();
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
	 * 获取所有权限列表，按path_code排序
	 * 
	 * @return 所有权限(PrivVO)构成的ArrayList
	 * @throws DAOSystemException
	 */
	public ArrayList getAllPrivs() {
		return findBySql(SQL_SELECT + " ORDER BY path_code", null);
	}

	/**
	 * 获取某节点下的所有直接子节点
	 * 
	 * @param idParent
	 *            父节点标识，不能为空
	 * @return
	 * @throws DAOSystemException
	 */
	public ArrayList getDirectSubNodes(String idParent)
			throws DAOSystemException {
		return findBySql(SQL_SELECT + " WHERE parent_prg_id=?",
				new String[] { idParent });
	}

	/**
	 * 插入权限实体，生成权限标识和path_code
	 */
	public void insert(VO vo) throws DAOSystemException {
		String parent = ((PrivVO) vo).getParentPrgId();
		if (parent != null && !"-1".equals(parent) && !"".equals(parent)) {
			PrivVO voParent = findByPrimaryKey(parent);
			if (voParent == null) {
				throw new DAOSystemException("INVALID PARENT_PRG_ID [" + parent
						+ "]");
			}
			parent = voParent.getPathCode();
		} else {
			parent = null;
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			SequenceManageDAO seqDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			((PrivVO) vo).setPrivId(seqDAO.getNextSequence(vo.getTableName(),
							"PRIVILEGE_ID"));

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));

			((PrivVO) vo).setPathCode(parent == null ? ((PrivVO) vo)
					.getPrivId() : parent + "." + ((PrivVO) vo).getPrivId());
			int index = 1;
			if ("".equals(((PrivVO) vo).getPrivId())) {
				((PrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, ((PrivVO) vo).getPrivId());
			if ("".equals(((PrivVO) vo).getParentPrgId())) {
				((PrivVO) vo).setParentPrgId(null);
			}
			stmt.setString(index++, ((PrivVO) vo).getParentPrgId());
			stmt.setString(index++, ((PrivVO) vo).getPrivName());
			stmt.setString(index++, ((PrivVO) vo).getPrivType());
			stmt.setString(index++, ((PrivVO) vo).getPrivDesc());
			stmt.setString(index++, ((PrivVO) vo).getPrivCode());
			stmt.setString(index++, ((PrivVO) vo).getPathCode());
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

	/**
	 * 更新权限实体，如果其标识或父节点变更，重构其path_code，并重构其所有子孙节点的path_code
	 */
	public boolean update(String pprivilege_id, PrivVO vo)
			throws DAOSystemException {
		boolean rebuildSubNodes = true;
		if (pprivilege_id.equals(vo.getPrivId())) {
			PrivVO voOld = findByPrimaryKey(pprivilege_id);
			if (vo.getParentPrgId() == null || "-1".equals(vo.getParentPrgId())
					|| "".equals(vo.getParentPrgId())) {
				if (voOld.getParentPrgId() == null
						|| "-1".equals(voOld.getParentPrgId())
						|| "".equals(voOld.getParentPrgId())) {
					vo.setPathCode(voOld.getPathCode());
					rebuildSubNodes = false;
				}
			} else {
				if (vo.getParentPrgId().equals(voOld.getParentPrgId())) {
					vo.setPathCode(voOld.getPathCode());
					rebuildSubNodes = false;
				}
			}
		}
		if (rebuildSubNodes) {
			String pathCode = null;
			if (vo.getParentPrgId() == null || "".equals(vo.getParentPrgId())) {
				pathCode = vo.getPrivId();
			} else {
				PrivVO voParent = findByPrimaryKey(vo.getParentPrgId());
				if (voParent == null) {
					throw new DAOSystemException("INVALID PARENT_PRG_ID ["
							+ vo.getParentPrgId() + "]");
				}
				pathCode = voParent.getPathCode() + "." + vo.getPrivId();
			}
			vo.setPathCode(pathCode);
			return updateTreeFromNode(pprivilege_id, vo) > 0;
		} else {
			return updateNodeSelf(pprivilege_id, vo);
		}
	}

	/**
	 * 删除权限，若存在子节点则不允许删除
	 */
	public long delete(String pprivilege_id) throws DAOSystemException {
		if (getDirectSubNodes(pprivilege_id).size() > 0) {
			throw new DAOSystemException("UNABLE TO DELETE WITH SUBNODES");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pprivilege_id);
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

	/**
	 * 更新某个权限节点自身
	 * 
	 * @param pprivilege_id
	 *            区域标识
	 * @param vo
	 *            新值对象
	 * @return
	 * @throws DAOSystemException
	 */
	private boolean updateNodeSelf(String pprivilege_id, PrivVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE PRIVILEGE SET privilege_id = ?,parent_prg_id = ?,privilege_name = ?,privilege_type = ?,privilege_desc = ?,privilege_code = ?,path_code = ?");
			sql.append(" WHERE  privilege_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((PrivVO) vo).getPrivId())) {
				((PrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, vo.getPrivId());
			if ("".equals(((PrivVO) vo).getParentPrgId())) {
				((PrivVO) vo).setParentPrgId(null);
			}
			stmt.setString(index++, vo.getParentPrgId());
			stmt.setString(index++, vo.getPrivName());
			stmt.setString(index++, vo.getPrivType());
			stmt.setString(index++, vo.getPrivDesc());
			stmt.setString(index++, vo.getPrivCode());
			stmt.setString(index++, vo.getPathCode());
			stmt.setString(index++, pprivilege_id);
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

	/**
	 * 从某个节点开始更新其本身和所有子孙节点的path_code
	 * 
	 * @param pprivilege_id
	 *            起始节点标识
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	private int updateTreeFromNode(String pprivilege_id, PrivVO vo)
			throws DAOSystemException {
		int recsUpdated = 0;
		if (updateNodeSelf(pprivilege_id, vo)) {
			recsUpdated += 1;
			Iterator iterSubNodes = getDirectSubNodes(pprivilege_id).iterator();
			PrivVO voNode;
			int recsUpdSub;
			while (iterSubNodes.hasNext()) {
				voNode = (PrivVO) iterSubNodes.next();
				voNode.setParentPrgId(vo.getPrivId());
				voNode.setPathCode(vo.getPathCode() + "." + voNode.getPrivId());
				recsUpdSub = updateTreeFromNode(voNode.getPrivId(), voNode);
				recsUpdated = recsUpdated + recsUpdSub;
				if (recsUpdSub < 1) {
					throw new DAOSystemException("UPDATE FAIL WITH SUBNODE["
							+ voNode.getPrivId() + "]");
				}
			}
		}

		return recsUpdated;
	}

	public ArrayList getPrivilegeListWithRegionRelatByParentId(String parentId, Set privilegeSet)
			throws DAOSystemException {
		ArrayList privilegeTypeList ;
    	try{
    		privilegeTypeList = StaticAttrCache.getInstance().getStaticAttr("PRIVILEGE_TYPE");
    	}catch( Exception e ){
    		throw new DAOSystemException() ;
    	}
    	StringBuffer privilegeIdCond = new StringBuffer();
    	Iterator it = privilegeSet.iterator();
    	while( it.hasNext() ){
    		privilegeIdCond.append(",").append((String)it.next());
    	}
    	if( privilegeIdCond.length() > 0 ){
    		privilegeIdCond.deleteCharAt(0);
    	}
    	
		String SQL = "SELECT a.privilege_id,a.parent_prg_id,a.privilege_name,a.privilege_type,a.privilege_desc,a.privilege_code,a.path_code,b.IF_REGION_RELA FROM PRIVILEGE a,mm_data_privilege_rule b where a.privilege_type = b.privilege_type";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = SQL + " AND a.PARENT_PRG_ID = " + parentId  ;
//			if( privilegeIdCond.length() > 0 ){
//				SQL = SQL + " AND a.PRIVILEGE_ID IN( " + privilegeIdCond.toString() + ")";
//			}
			
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				PrivVO vo = new PrivVO();
				populateDto(vo, rs);
				vo.setIfRegionRela(rs.getString("if_region_rela"));
				if( "T".equals(vo.getIfRegionRela())){
					vo.setIfRegionRelaName("区域相关");
				}else{
					vo.setIfRegionRelaName("区域无关");
				}
				
				for( int i = 0 ; i < privilegeTypeList.size() ; i ++ ){
                	StaticAttrVO staticAttrVo = (StaticAttrVO)privilegeTypeList.get(i);
                	if( vo.getPrivType().equals(staticAttrVo.getAttrValue())){
                		vo.setPrivTypeName(staticAttrVo.getAttrValueDesc());
                		break ;
                	}
                }
				resultList.add(vo);
			}
			return resultList;
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


	public ArrayList getPrivilegeListWithRegionRelatByName(String privilegeName, Set privilegeSet)
			throws DAOSystemException {
		ArrayList privilegeTypeList ;
    	try{
    		privilegeTypeList = StaticAttrCache.getInstance().getStaticAttr("PRIVILEGE_TYPE");
    	}catch( Exception e ){
    		throw new DAOSystemException() ;
    	}
    	StringBuffer privilegeIdCond = new StringBuffer();
    	Iterator it = privilegeSet.iterator();
    	while( it.hasNext() ){
    		privilegeIdCond.append(",").append((String)it.next());
    	}
    	if( privilegeIdCond.length() > 0 ){
    		privilegeIdCond.deleteCharAt(0);
    	}
    	
		String SQL = "SELECT a.privilege_id,a.parent_prg_id,a.privilege_name,a.privilege_type,a.privilege_desc,a.privilege_code,a.path_code,b.IF_REGION_RELA FROM PRIVILEGE a,mm_data_privilege_rule b where a.privilege_type = b.privilege_type";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			SQL = SQL + " AND a.privilege_name like '%" + privilegeName  + "%'";
//			if( privilegeIdCond.length() > 0 ){
//				SQL = SQL + " AND a.PRIVILEGE_ID IN( " + privilegeIdCond.toString() + ")";
//			}
//			SQL = SQL + " ORDER BY path_code ";

			
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				PrivVO vo = new PrivVO();
				populateDto(vo, rs);
				vo.setIfRegionRela(rs.getString("if_region_rela"));
				if( "T".equals(vo.getIfRegionRela())){
					vo.setIfRegionRelaName("区域相关");
				}else{
					vo.setIfRegionRelaName("区域无关");
				}
				
			for( int i = 0 ; i < privilegeTypeList.size() ; i ++ ){
	                	StaticAttrVO staticAttrVo = (StaticAttrVO)privilegeTypeList.get(i);
	                	if( vo.getPrivType().equals(staticAttrVo.getAttrValue())){
	                		vo.setPrivTypeName(staticAttrVo.getAttrValueDesc());
	                		break ;
	                	}
                	}
				resultList.add(vo);
			}
			return resultList;
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

	
	public ArrayList getStaffPrivs( String partyRoleId ) throws DAOSystemException{
		String SQL = "SELECT a.privilege_id,a.parent_prg_id,a.privilege_name,a.privilege_type,a.privilege_desc,a.privilege_code,a.path_code,b.party_role_id FROM PRIVILEGE a, staff_privilege b " +
								" where a.privilege_id  = b.privilege_id and b.party_role_id = ?";
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			System.out.println("Check CONN============="+conn) ;
//			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			stmt = conn.prepareStatement(SQL);
			stmt.setString(1,partyRoleId);
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				PrivVO vo = new PrivVO();
				populateDto(vo, rs);
				resultList.add(vo);
			}
			return resultList;
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
}
