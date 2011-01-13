package com.ztesoft.oaas.dao.region;

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
import com.ztesoft.oaas.vo.OrganizationVO;
import com.ztesoft.oaas.vo.RegionVO;

public class RegionDAOImpl   implements RegionDAO {

	private String SQL_SELECT = "SELECT region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code,path_name,ngn_flag,virtual_deal_flag,is_actual_region,province_code, country_type FROM REGION";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM REGION";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO REGION ( region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code,path_name,ngn_flag,virtual_deal_flag,state_date,is_actual_region,province_code, country_type ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE REGION SET  parent_region_id = ?, region_level = ?, region_name = ?, region_desc = ?, region_code = ?, path_code = ?, path_name = ?, ngn_flag = ?, virtual_deal_flag = ?, state_date = ?,  is_actual_region = ?, province_code = ?, country_type = ? WHERE region_id = ? ";

	private String SQL_DELETE = "DELETE FROM REGION WHERE region_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM REGION ";

	public RegionDAOImpl() {

	}

	public void insertChnBranch(String regionId) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String insertChnBranchSQL = "insert into chn_branch ( SUB_EXCH_ID,BRANCH_ID,BUSINESS_ID,OPER_FLAG ) values ( ?, ?, ?, ? ) ";
		try {
			RegionVO vo = this.findByPrimaryKey(regionId);
			String businessId = "";
			if( vo !=null ){
				vo = this.findByPrimaryKey(vo.getParentRegionId());
				if( vo != null ){
					//vo = this.findByPrimaryKey(vo.getParentRegionId() ) ;
					//System.out.println("Region Level : " + vo.getRegionLevel());
					businessId = vo.getParentRegionId();
				}
			}
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(insertChnBranchSQL));
			stmt.setString(1, regionId);
			stmt.setString(2, regionId);
			stmt.setString(3, businessId) ;
			stmt.setString(4, "0");
			stmt.executeUpdate();
		} catch (SQLException se) {
			Debug.print(insertChnBranchSQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ insertChnBranchSQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}
	
	public RegionVO findByPrimaryKey(String pregion_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT + " WHERE region_id = ? ",
				new String[] { pregion_id });
		if (arrayList.size() > 0)
			return (RegionVO) arrayList.get(0);
		else
			return (RegionVO) getEmptyVO();
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
			RegionVO vo = new RegionVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(RegionVO vo, ResultSet rs) throws SQLException {
		vo.setRegionId(rs.getString("region_id"));
		vo.setParentRegionId(rs.getString("parent_region_id"));
		vo.setRegionLevel(rs.getString("region_level"));
		vo.setRegionName(rs.getString("region_name"));
		vo.setRegionDesc(rs.getString("region_desc"));
		vo.setRegionCode(rs.getString("region_code"));
		vo.setPathCode(rs.getString("path_code"));
		vo.setPathName(rs.getString("path_name"));
		vo.setNgnFlag(rs.getString("ngn_flag"));
		vo.setVirtualDealFlag(rs.getString("virtual_deal_flag"));
		vo.setIsActualRegion(rs.getString("is_actual_region"));
		vo.setProvinceCode(rs.getString("province_code"));
		vo.setCountryType(rs.getString("country_type"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		RegionVO vo = new RegionVO();
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

	public boolean update(VO vo) throws DAOSystemException {
		update("region_id = '" + ((RegionVO) vo).getRegionId() + "'", vo);
		return true;
	}

	public boolean update(String whereCond, VO vo) throws DAOSystemException {

		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();

		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append("UPDATE REGION SET region_id = ?,region_level = ?,region_name = ?,region_desc = ?,region_code = ?, ngn_flag = ?, virtual_deal_flag = ?, state_date = ? , is_actual_region = ?, province_code = ?, country_type = ? ");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));

			int index = 1;
			if ("".equals(((RegionVO) vo).getRegionId())) {
				((RegionVO) vo).setRegionId(null);
			}
			stmt.setString(index++, ((RegionVO) vo).getRegionId());
			stmt.setString(index++, ((RegionVO) vo).getRegionLevel());
			stmt.setString(index++, ((RegionVO) vo).getRegionName());
			stmt.setString(index++, ((RegionVO) vo).getRegionDesc());
			stmt.setString(index++, ((RegionVO) vo).getRegionCode());
			stmt.setString(index++, ((RegionVO) vo).getNgnFlag());
			stmt.setString(index++,((RegionVO)vo).getVirtualDealFlag());
			stmt.setTimestamp(index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
			stmt.setString(index++, ((RegionVO) vo).getIsActualRegion());
			stmt.setString(index++,((RegionVO)vo).getProvinceCode());
			stmt.setString(index++, ((RegionVO)vo).getCountryType());

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

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public VO getEmptyVO() {
		return new RegionVO();
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

	public ArrayList getAllRegions() {
		return findBySql(SQL_SELECT + " ORDER BY path_code", null);
	}

	public ArrayList getRegionsByCond(String regionIds, String regionLevel,
			String regionType) {
		String sql = SQL_SELECT; //+ " WHERE REGION_ID IN (" + regionIds + ")";
		if ("000".equals(regionType)) {// 计费区域
			if ("97A".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL ='97A' ";
			} else if ("97B".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97B' )";
			} else if ("97C".endsWith(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97C','97B' )";
			} else if ("97D".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ('97A','97C','97B','97D' )";
			} else if ("97D".equals(regionLevel)) {
				sql = sql
						+ " AND REGION_LEVEL in ('97A','97C','97B','97D','97D' )";
			}
		} else if ("001".endsWith(regionType)) {// 资源区域
			if ("97A".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL ='97A' ";
			} else if ("97B".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97B' )";
			} else if ("97C".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97C','97B' )";
			} else if ("97D".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97C','97B','97D' )";
			} else if ("98D".equals(regionLevel)) {
				sql = sql
						+ " AND REGION_LEVEL in ('97A','97C','97B','97D','98D' )";
			} else if ("98E".equals(regionLevel)) {
				sql = sql
						+ " AND REGION_LEVEL in ('97A','97C','97B','97D','98D','98E' )";
			}
		} else if ("002".equals(regionType)) {// 营销区域
			if ("97A".equals(regionLevel)) {
				sql = sql + " where REGION_LEVEL ='97A' ";
			} else if ("97B".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97B' )";
			} else if ("97C".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ( '97A','97C','97B' )";
			} else if ("99D".equals(regionLevel)) {
				sql = sql + " AND REGION_LEVEL in ('97A','97C','97B','99D' )";
			} else if ("99E".equals(regionLevel)) {
				sql = sql
						+ " AND REGION_LEVEL in ('97A','97C','97B','99D','99E' )";
			}
		}
		sql = sql + " ORDER BY  path_code";

		return findBySql(sql, null);
	}

	/**
	 * 查询regionIdSet中指定的ID的Region记录
	 */
	public List getBillRegionByRegionIds(Set regionIdSet) throws DAOSystemException{
		StringBuffer regionIdCond = new StringBuffer();
		Iterator it = regionIdSet.iterator();
		while( it.hasNext()){
			regionIdCond.append(",").append((String)it.next());
		}
		regionIdCond.deleteCharAt(0);
		return this.findByCond("REGION_ID IN (" + regionIdCond.toString() + ") ORDER BY path_code");
	}
	
	public ArrayList getAllBillRegions(String parentRegionId) {
		return findBySql(
				SQL_SELECT
						+ " WHERE parent_region_id = "
						+ parentRegionId
						//+ " AND region_level IN ('97A', '97E', '97B', '97D', '97D', '97F') ORDER BY path_code",
						+ " AND region_level IN ('97A','97B', '97C', '97D', '97E') ORDER BY path_code",
				null);
	}


	/*
		97A        集团
		97B        省
		97C        本地网
		97D        营业区
		98D        处理局
		98E        母局
		98F        局站
	 */
	public ArrayList getAllRescRegions(String parentRegionId) {
		return findBySql(
				SQL_SELECT
						+ " WHERE parent_region_id = "
						+ parentRegionId
						+ " AND region_level IN ('97A', '97B', '97C','97D','98D', '98E', '98F') ORDER BY path_code",
				null);
	}

	/**
	 * 获取所有区域列表，按path_code排序
	 * 
	 * @return 所有区域(MmMenuVO)构成的ArrayList
	 * @throws DAOSystemException
	 */
	public ArrayList getAllSaleRegions(String parentRegionId) {
		return findBySql(
				SQL_SELECT
						+ " WHERE parent_region_id = "
						+ parentRegionId
						+ " AND region_level IN ('97A', '97B', '97C', '99D', '99E', '99F') ORDER BY path_code",
				null);
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
		return findBySql(SQL_SELECT + " WHERE parent_region_id=?",
				new String[] { idParent });
	}

	/**
	 * 插入区域实体，生成区域标识和path_code
	 */
	public void insert(VO vo) throws DAOSystemException {
		String parent = ((RegionVO) vo).getParentRegionId();
		if (parent != null && !"-1".equals(parent) && !"".equals(parent)) {
			RegionVO voParent = findByPrimaryKey(parent);
			if (voParent == null) {
				throw new DAOSystemException("INVALID PARENT_ID [" + parent
						+ "]");
			}
			parent = voParent.getPathCode();
		} else {
			parent = null;
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		try {

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			((RegionVO) vo).setRegionId(smDAO.getNextSequence(vo.getTableName(),
							"REGION_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			
			((RegionVO) vo).setPathCode(parent == null ? ((RegionVO) vo)
					.getRegionId() : parent + "."
					+ ((RegionVO) vo).getRegionId());
			int index = 1;
			if ("".equals(((RegionVO) vo).getRegionId())) {
				((RegionVO) vo).setRegionId(null);
			}
			stmt.setString(index++, ((RegionVO) vo).getRegionId());
			if ("".equals(((RegionVO) vo).getParentRegionId())) {
				((RegionVO) vo).setParentRegionId(null);
			}
			stmt.setString(index++, ((RegionVO) vo).getParentRegionId());
			stmt.setString(index++, ((RegionVO) vo).getRegionLevel());
			stmt.setString(index++, ((RegionVO) vo).getRegionName());
			stmt.setString(index++, ((RegionVO) vo).getRegionDesc());
			stmt.setString(index++, ((RegionVO) vo).getRegionCode());
			stmt.setString(index++, ((RegionVO) vo).getPathCode());
			stmt.setString(index++, ((RegionVO) vo).getPathName());
			stmt.setString(index++, ((RegionVO) vo).getNgnFlag());
			stmt.setString(index++, ((RegionVO) vo).getVirtualDealFlag());
			stmt.setTimestamp(index++, DAOUtils.parseTimestamp(DAOUtils.getFormatedDate()));
			stmt.setString(index++, ((RegionVO) vo).getIsActualRegion());
			stmt.setString(index++,((RegionVO)vo).getProvinceCode());
			stmt.setString(index++, ((RegionVO)vo).getCountryType());

			//stmt.setTimestamp( index++, DAOUtils.parseTimestamp(((StaffPrivVO)vo).getEffDate()) );
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
	 * 更新区域实体，如果其标识或父节点变更，重构其path_code，并重构其所有子孙节点的path_code
	 */
	public boolean update(String pregion_id, RegionVO vo)
			throws DAOSystemException {
		boolean rebuildSubNodes = true;
		if (pregion_id.equals(vo.getRegionId())) {
			RegionVO voOld = findByPrimaryKey(pregion_id);
			if (vo.getParentRegionId() == null
					|| "-1".equals(vo.getParentRegionId())
					|| "".equals(vo.getParentRegionId())) {
				if (voOld.getParentRegionId() == null
						|| "-1".equals(voOld.getParentRegionId())
						|| "".equals(voOld.getParentRegionId())) {
					vo.setPathCode(voOld.getPathCode());
					rebuildSubNodes = false;
				}
			} else {
				if (vo.getParentRegionId().equals(voOld.getParentRegionId())) {
					vo.setPathCode(voOld.getPathCode());
					rebuildSubNodes = false;
				}
			}
		}
		if (rebuildSubNodes) {
			String pathCode = null;
			if (vo.getParentRegionId() == null
					|| "".equals(vo.getParentRegionId())) {
				pathCode = vo.getRegionId();
			} else {
				RegionVO voParent = findByPrimaryKey(vo.getParentRegionId());
				if (voParent == null) {
					throw new DAOSystemException("INVALID PARENT_ID ["
							+ vo.getParentRegionId() + "]");
				}
				pathCode = voParent.getPathCode() + "." + vo.getRegionId();
			}
			vo.setPathCode(pathCode);
			return updateTreeFromNode(pregion_id, vo) > 0;
		} else {
			return updateNodeSelf(pregion_id, vo);
		}
	}

	/**
	 * 删除区域，若存在子节点则不允许删除
	 */
	public long delete(String pregion_id) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pregion_id);
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

	/**
	 * 更新某个区域节点自身
	 * 
	 * @param pregion_id
	 *            区域标识
	 * @param vo
	 *            新值对象
	 * @return
	 * @throws DAOSystemException
	 */
	private boolean updateNodeSelf(String pregion_id, RegionVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append("UPDATE REGION SET region_id = ?,parent_region_id = ?,region_level = ?,region_name = ?,region_desc = ?,region_code = ?,path_code = ?,path_name = ?, ngn_flag = ?, virtual_deal_flag = ?,  is_actual_region = ?, province_code = ?, country_type = ? ");
			sql.append(" WHERE  region_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql.toString()));
			int index = 1;
			if ("".equals(((RegionVO) vo).getRegionId())) {
				((RegionVO) vo).setRegionId(null);
			}
			stmt.setString(index++, vo.getRegionId());
			if ("".equals(((RegionVO) vo).getParentRegionId())) {
				((RegionVO) vo).setParentRegionId(null);
			}
			stmt.setString(index++, vo.getParentRegionId());
			stmt.setString(index++, vo.getRegionLevel());
			stmt.setString(index++, vo.getRegionName());
			stmt.setString(index++, vo.getRegionDesc());
			stmt.setString(index++, vo.getRegionCode());
			stmt.setString(index++, vo.getPathCode());
			stmt.setString(index++, vo.getPathName());
			stmt.setString(index++, vo.getNgnFlag());
			stmt.setString(index++, vo.getVirtualDealFlag());
			stmt.setString(index++, vo.getIsActualRegion());
			stmt.setString(index++, vo.getProvinceCode());
			stmt.setString(index++, vo.getCountryType());
			stmt.setString(index++, pregion_id);

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
	 * @param pregion_id
	 *            起始节点标识
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	private int updateTreeFromNode(String pregion_id, RegionVO vo)
			throws DAOSystemException {
		int recsUpdated = 0;
		if (updateNodeSelf(pregion_id, vo)) {
			recsUpdated += 1;
			Iterator iterSubNodes = getDirectSubNodes(pregion_id).iterator();
			RegionVO voNode;
			int recsUpdSub;
			while (iterSubNodes.hasNext()) {
				voNode = (RegionVO) iterSubNodes.next();
				voNode.setParentRegionId(vo.getRegionId());
				voNode.setPathCode(vo.getPathCode() + "."
						+ voNode.getRegionId());
				recsUpdSub = updateTreeFromNode(voNode.getRegionId(), voNode);
				recsUpdated = recsUpdated + recsUpdSub;
				if (recsUpdSub < 1) {
					throw new DAOSystemException("UPDATE FAIL WITH SUBNODE["
							+ voNode.getRegionId() + "]");
				}
			}
		}

		return recsUpdated;
	}

	/**
	 * 判断一个处理局的下级(母局和局站)是否存在NGNFlag为T的.
	 * 
	 * @param regionId
	 *            处理局ID
	 * @return 如果存在则返回true,否则返回false
	 */
	public boolean checkSubRegionNGNFlag(String regionId)
			throws DAOSystemException {
		List list = this.findByCond(" PARENT_REGION_ID = " + regionId
				+ " AND NGN_FLAG = 'F' ");
		if (list.size() > 0) {
			return false;
		} else {
			list = this
					.findByCond(" NGN_FLAG = 'F' AND PARENT_REGION_ID IN ( SELECT REGION_ID FROM REGION WHERE PARENT_REGION_ID = "
							+ regionId + ")");
			if (list.size() > 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 根据组织线地域选择组件中当前被选中的区域节点作为上级节点来查询,当前节点是
	 * 用户权限范围内的地域,所以它的下级节点也都是权限范围内的地域,所以查询出来以后
	 * 要给VO设置上权限标志,表示该节点是用户的权限范围内的区域.
	 * @param parentRegionId
	 * @return
	 * @throws DAOSystemException
	 */
	public List getOrganizationRegionListWithPrivFlag( String parentRegionID ) throws DAOSystemException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "select decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'市公司',3,'分公司',4,'营业区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name, party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class from organization where PARENT_PARTY_ID = " + parentRegionID;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OrganizationVO vo = new OrganizationVO();
				populateOrganizationVO(vo, rs);
				vo.setOrgTypeName(rs.getString("org_type_name"));
				vo.setPrivilegeFlag( "T" );//设置权限标志
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
	/**
	 * 根据资源线地域选择组件中当前被选中的区域节点作为上级节点来查询,当前节点是
	 * 用户权限范围内的地域,所以它的下级节点也都是权限范围内的地域,所以查询出来以后
	 * 要给VO设置上权限标志,表示该节点是用户的权限范围内的区域.
	 * @param parentRegionId
	 * @return
	 * @throws DAOSystemException
	 */
	public List getResourceRegionListWithPrivFlag(String parentRegionId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String SQL = "SELECT decode ( region_level , '97A','集团公司','97B','省公司','97C','本地网','97D','营业区','98D','处理局','98E','母局','98F','局站', region_level ) as region_level_name, " +
								" region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code, " +
								" path_name,ngn_flag,virtual_deal_flag,is_actual_region,province_code, country_type FROM REGION  WHERE PARENT_REGION_ID = " + parentRegionId;
		/*String SQL = "SELECT decode ( region_level , '97A','集团公司','97E','省公司','97B','本地网','97D','营业区','98D','处理局','98E','母局','98F','局站', region_level ) as region_level_name, " +
								" region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code, " +
								" path_name,ngn_flag,virtual_deal_flag FROM REGION  WHERE REGION_LEVEL != '97D' AND PARENT_REGION_ID = " + parentRegionId;*/
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				RegionVO vo = new RegionVO();
				populateDto(vo, rs);
				vo.setRegionLevelName(rs.getString("region_level_name"));
				vo.setPrivilegeFlag( "T" );//设置权限标志
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

	/**
	 * 根据组织线地域选择组件中当前被选中的区域节点作为上级节点来查询,当前节点不是
	 * 用户权限范围内的地域,但是它的下级节点有可能是权限范围内的地域,所以查询出子节点以后
	 * 要给判断是否是权限范围内的区域,如果是,则VO设置上权限标志,表示该节点是用户的权限范围内的区域.
	 */
	public List getOrganizationRegionListByFilter(String parentRegionId,ArrayList regionIds, String orgTypeId) throws DAOSystemException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String regionIdCond = "";
		//*******************************************************************************
		if( "-1".equals(parentRegionId) && regionIds.size() == 0){
			String SQL = "select decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'市公司',3,'分公司',4,'营业区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name, party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class from organization where PARENT_PARTY_ID = -1 ";
			try{
				conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
				stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
				rs = stmt.executeQuery();
				
				ArrayList resultList = new ArrayList();
				while (rs.next()) {
					OrganizationVO vo = new OrganizationVO();
					populateOrganizationVO(vo, rs);
					vo.setOrgTypeName(rs.getString("org_type_name"));
					for( int i = 0 ; i < regionIds.size() ; i ++ ){
						if( ((String)regionIds.get(i)).equals(vo.getPartyId())){
							vo.setPrivilegeFlag( "T" );//设置权限标志
							break ;
						}
					}
					resultList.add(vo);
				}
				return resultList;
			}catch (SQLException se) {
				Debug.print(SQL, this);
				throw new DAOSystemException("SQLException while getting sql:\n"
						+ SQL, se);
			} finally {
				DAOUtils.closeResultSet(rs, this);
				DAOUtils.closeStatement(stmt, this);
				//DAOUtils.closeConnection(conn, this);
			}
		}
		//*******************************************************************************
		
		for( int i = 0 ; i < regionIds.size() ; i ++ ){
			regionIdCond = regionIdCond + (String)regionIds.get(i) + ",";
		}
		regionIdCond = regionIdCond.substring(0,regionIdCond.length() - 1 );

		String SQL = "select path_code from organization " +
								//" where org_type_id <= " + orgTypeId + " and party_id in ( " + regionIdCond + ") " ;
								" where party_id in ( " + regionIdCond + ") " ;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));

			rs = stmt.executeQuery();
			String pathCodeCond = "";
			while( rs.next() ){
				pathCodeCond = pathCodeCond + "." + rs.getString("path_code");
			}

			String str = "";
			for( int i = 0 ; i < pathCodeCond.length() ; i ++ ){
				if( pathCodeCond.charAt(i) == '.' ){
					if( i == 0 )
						continue ;
					str = str + "," ;
				}else{
					str = str + pathCodeCond.charAt(i) ;
				}
			}
			pathCodeCond = str ;
			if( "".equals(pathCodeCond)){
				return new ArrayList();
			}
			SQL = "select decode (cast(org_type_id as varchar(20)),0,'集团公司',1,'省公司',2,'市公司',3,'分公司',4,'营业区',5,'部门',6,'班组',9,'其他组织',cast( org_type_id as varchar(20))) as org_type_name, party_id,parent_party_id,org_code,org_name,org_level,org_type_id,org_content,address_id,state,state_date,path_code,path_name,org_class from organization where PARENT_PARTY_ID = " + parentRegionId + " and PARTY_ID in (" + pathCodeCond + ")";
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();
			
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				OrganizationVO vo = new OrganizationVO();
				populateOrganizationVO(vo, rs);
				vo.setOrgTypeName(rs.getString("org_type_name"));
				for( int i = 0 ; i < regionIds.size() ; i ++ ){
					if( ((String)regionIds.get(i)).equals(vo.getPartyId())){
						vo.setPrivilegeFlag( "T" );//设置权限标志
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
	
	/**
	 * 根据资源线地域选择组件中当前被选中的区域节点作为上级节点来查询,当前节点不是
	 * 用户权限范围内的地域,但是它的下级节点有可能是权限范围内的地域,所以查询出子节点以后
	 * 要给判断是否是权限范围内的区域,如果是,则VO设置上权限标志,表示该节点是用户的权限范围内的区域.
	 */
	public List getResourceRegionListByFilter(String parentRegionId ,String regionLevel, ArrayList regionIds ) throws DAOSystemException {
		System.out.println("getResourceRegionListByFilter");
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String regionIdCond = "";
		
		//*******************************************************************************
		if( "-1".equals(parentRegionId)){
			String SQL = "SELECT decode ( region_level , '97A','集团公司','97B','省公司','97C','本地网','97D','营业区','98D','处理局','98E','母局','98F','局站', region_level ) as region_level_name, " +
			" region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code, " +
			" path_name,ngn_flag,virtual_deal_flag,is_actual_region,province_code, country_type FROM REGION  WHERE parent_region_id = -1";
			/*String SQL = "SELECT decode ( region_level , '97A','集团公司','97E','省公司','97B','本地网','97D','营业区','98D','处理局','98E','母局','98F','局站', region_level ) as region_level_name, " +
			" region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code, " +
			" path_name,ngn_flag,virtual_deal_flag FROM REGION  WHERE parent_region_id = -1";*/
			try{
				conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
				stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
				rs = stmt.executeQuery();
				
				ArrayList resultList = new ArrayList();
				while (rs.next()) {
					RegionVO vo = new RegionVO();
					populateDto(vo, rs);
					vo.setRegionLevelName(rs.getString("region_level_name"));
					for( int i = 0 ; i < regionIds.size() ; i ++ ){
						if( ((String)regionIds.get(i)).equals(vo.getRegionId())){
							vo.setPrivilegeFlag( "T" );//设置权限标志
							break ;
						}
					}
					resultList.add(vo);
				}
				return resultList;
			}catch (SQLException se) {
				Debug.print(SQL, this);
				throw new DAOSystemException("SQLException while getting sql:\n"
						+ SQL, se);
			} finally {
				DAOUtils.closeResultSet(rs, this);
				DAOUtils.closeStatement(stmt, this);
				//DAOUtils.closeConnection(conn, this);
			}
		}
		//*******************************************************************************
		for( int i = 0 ; i < regionIds.size() ; i ++ ){
			regionIdCond = regionIdCond + (String)regionIds.get(i) + ",";
		}
		regionIdCond = regionIdCond.substring(0,regionIdCond.length() - 1 );

		String SQL = "select path_code from region " +
								" where region_id in ( " + regionIdCond + ") " ;

		if ("97A".equals(regionLevel)) {//集团公司
			SQL = SQL + " AND REGION_LEVEL ='97A' ";
		} else if ("97B".equals(regionLevel)) {//省公司
			SQL = SQL + " AND REGION_LEVEL in ( '97A','97B' )";
		} else if ("97C".equals(regionLevel)) {//本地网
			SQL = SQL + " AND REGION_LEVEL in ( '97A','97C','97B' )";
		} else if ("97D".equals(regionLevel)) {//营业区
			SQL = SQL + " AND REGION_LEVEL in ( '97A','97C','97B','97D' )";
		} else if ("98D".equals(regionLevel)) {//处理局
			SQL = SQL + " AND REGION_LEVEL in ('97A','97C','97B','97D','98D' )";
		} else if ("98E".equals(regionLevel)) {//母局
			SQL = SQL	+ " AND REGION_LEVEL in ('97A','97C','97B','97D','98D','98E' )";
		} else if ("98F".equals(regionLevel)) {//局站
			SQL = SQL	+ " AND REGION_LEVEL in ('97A','97C','97B','97D','98D','98E','98F' )";
		}
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));

			rs = stmt.executeQuery();
			String pathCodeCond = "";
			while( rs.next() ){
				pathCodeCond = pathCodeCond + "." + rs.getString("path_code");
			}

			String str = "";
			for( int i = 0 ; i < pathCodeCond.length() ; i ++ ){
				if( pathCodeCond.charAt(i) == '.' ){
					if( i == 0 )
						continue ;
					str = str + "," ;
				}else{
					str = str + pathCodeCond.charAt(i) ;
				}
			}
			pathCodeCond = str ;
			if( "".equals(pathCodeCond)){
				return new ArrayList();
			}
			SQL = "SELECT decode ( region_level , '97A','集团公司','97B','省公司','97C','本地网','97D','营业区','98D','处理局','98E','母局','98F','局站', region_level ) as region_level_name, " +
			" region_id,parent_region_id,region_level,region_name,region_desc,region_code,path_code, " +
			" path_name,ngn_flag,virtual_deal_flag,is_actual_region,province_code, country_type FROM REGION  WHERE parent_region_id = " + parentRegionId + " and region_id in (" + pathCodeCond + ")";
			
			stmt = conn	.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();
			
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				RegionVO vo = new RegionVO();
				populateDto(vo, rs);
				vo.setRegionLevelName(rs.getString("region_level_name"));
				for( int i = 0 ; i < regionIds.size() ; i ++ ){
					if( ((String)regionIds.get(i)).equals(vo.getRegionId())){
						vo.setPrivilegeFlag( "T" );//设置权限标志
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
	
	private void populateOrganizationVO(OrganizationVO vo , ResultSet rs ) throws SQLException{
		vo.setPartyId(rs.getString("party_id"));
        vo.setParentPartyId(rs.getString("parent_party_id"));
        vo.setOrgCode(rs.getString("org_code"));
        vo.setOrgName(rs.getString("org_name"));
        vo.setOrgLevel(rs.getString("org_level"));
        vo.setOrgTypeId(rs.getString("org_type_id"));
        vo.setOrgContent(rs.getString("org_content"));
        vo.setAddrId(rs.getString("address_id"));
        vo.setState(rs.getString("state"));
        vo.setStateDate(DAOUtils.getFormatedDate(rs.getDate("state_date")));
        vo.setPathCode(rs.getString("path_code"));
        vo.setPathName(rs.getString("path_name"));
        vo.setOrgClass(rs.getString("org_class"));
	}
}
