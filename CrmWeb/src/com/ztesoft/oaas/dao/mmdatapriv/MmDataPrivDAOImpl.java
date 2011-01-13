package com.ztesoft.oaas.dao.mmdatapriv;

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
import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.util.tracer.Debug;
import com.ztesoft.common.valueobject.VO;
import com.ztesoft.oaas.vo.CustCtrlInfoVo;
import com.ztesoft.oaas.vo.MmDataPrivVO;
import com.ztesoft.oaas.vo.MmMenuVO;
import com.ztesoft.oaas.vo.OffWarrRequementVO;
import com.ztesoft.oaas.vo.PageFieldVO;
import com.ztesoft.oaas.vo.SimpleDataPrivilegeVO;
import com.ztesoft.oaas.vo.SimpleOfferServVO;
import com.ztesoft.oaas.vo.SimplePricCataContentVO;
import com.ztesoft.oaas.vo.SimpleProductVO;
import com.ztesoft.oaas.vo.SimpleServiceOfferVO;

public class MmDataPrivDAOImpl   implements MmDataPrivDAO {

	private String SQL_SELECT = "SELECT privilege_id,data_pkey_1,data_pkey_2,data_pkey_3 FROM MM_DATA_PRIVILEGE";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM MM_DATA_PRIVILEGE";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO MM_DATA_PRIVILEGE ( privilege_id,data_pkey_1,data_pkey_2,data_pkey_3 ) VALUES ( ?,?,?,? )";

	private String SQL_UPDATE = "UPDATE MM_DATA_PRIVILEGE SET  WHERE data_pkey_1 = ? AND data_pkey_2 = ? AND data_pkey_3 = ? AND privilege_id = ? ";

	private String SQL_DELETE = "DELETE FROM MM_DATA_PRIVILEGE WHERE data_pkey_1 = ? AND data_pkey_2 = ? AND data_pkey_3 = ? AND privilege_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM MM_DATA_PRIVILEGE ";

	public MmDataPrivDAOImpl() {

	}

	public MmDataPrivVO findByPrimaryKey(String pdata_pkey_1,
			String pdata_pkey_2, String pdata_pkey_3, String pprivilege_id)
			throws DAOSystemException {
		ArrayList arrayList = findBySql(
				SQL_SELECT
						+ " WHERE data_pkey_1 = ? AND data_pkey_2 = ? AND data_pkey_3 = ? AND privilege_id = ? ",
				new String[] { pdata_pkey_1, pdata_pkey_2, pdata_pkey_3,
						pprivilege_id });
		if (arrayList.size() > 0)
			return (MmDataPrivVO) arrayList.get(0);
		else
			return null;// (MmDataPrivVO) getEmptyVO();
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
			MmDataPrivVO vo = new MmDataPrivVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(MmDataPrivVO vo, ResultSet rs)
			throws SQLException {
		vo.setPrivId(DAOUtils.trimStr(rs.getString("privilege_id")));
		vo.setDataPkey1(DAOUtils.trimStr(rs.getString("data_pkey_1")));
		vo.setDataPkey2(DAOUtils.trimStr(rs.getString("data_pkey_2")));
		vo.setDataPkey3(DAOUtils.trimStr(rs.getString("data_pkey_3")));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		MmDataPrivVO vo = new MmDataPrivVO();
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

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			int index = 1;
			if ("".equals(((MmDataPrivVO) vo).getPrivId())) {
				((MmDataPrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, ((MmDataPrivVO) vo).getPrivId());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey1());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey2());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey3());
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

	public boolean update(String pdata_pkey_1, String pdata_pkey_2,
			String pdata_pkey_3, String pprivilege_id, MmDataPrivVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql.append("UPDATE MM_DATA_PRIVILEGE SET privilege_id = ?,data_pkey_1 = ?,data_pkey_2 = ?,data_pkey_3 = ?");
			sql.append(" WHERE  data_pkey_1 = ? AND data_pkey_2 = ? AND data_pkey_3 = ? AND privilege_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((MmDataPrivVO) vo).getPrivId())) {
				((MmDataPrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, vo.getPrivId());
			stmt.setString(index++, vo.getDataPkey1());
			stmt.setString(index++, vo.getDataPkey2());
			stmt.setString(index++, vo.getDataPkey3());
			stmt.setString(index++, pdata_pkey_1);
			stmt.setString(index++, pdata_pkey_2);
			stmt.setString(index++, pdata_pkey_3);
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

	public boolean update(String whereCond, VO vo) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE MM_DATA_PRIVILEGE SET privilege_id = ?,data_pkey_1 = ?,data_pkey_2 = ?,data_pkey_3 = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((MmDataPrivVO) vo).getPrivId())) {
				((MmDataPrivVO) vo).setPrivId(null);
			}
			stmt.setString(index++, ((MmDataPrivVO) vo).getPrivId());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey1());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey2());
			stmt.setString(index++, ((MmDataPrivVO) vo).getDataPkey3());
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

	public long delete( MmDataPrivVO[] voLs ) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;

		String SQL = SQL_DELETE_BY_WHERE + " WHERE ";
		for(int i=0;i<voLs.length;i++){
			String whereCond = " (data_pkey_1 = ? AND data_pkey_2 = ? AND data_pkey_3 = ? AND privilege_id = ?) ";
			if(i>0) whereCond = " OR " + whereCond;
			SQL += whereCond;
		}
		
		try {
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			int index = 1;
			for(int i=0;i<voLs.length;i++){
				stmt.setString(index++, voLs[i].getDataPkey1());
				stmt.setString(index++, voLs[i].getDataPkey2());
				stmt.setString(index++, voLs[i].getDataPkey3());
				stmt.setString(index++, voLs[i].getPrivId());
			}
			rows = stmt.executeUpdate();

		} catch (SQLException se) {
			Debug.print(SQL, this);
			throw new DAOSystemException("SQLException while deleting sql:\n" + SQL, se);
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
		return new MmDataPrivVO();
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

	public ArrayList findDataByPrivilegeId(String sql, String privilegeId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, privilegeId);
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();

			while (rs.next()) {
				MmDataPrivVO vo = new MmDataPrivVO();
				vo.setDataPkey1(rs.getString("data_pkey_1"));
				vo.setDataPkey2(rs.getString("data_pkey_2"));
				vo.setDataPkey3(rs.getString("data_pkey_3"));
				vo.setName(rs.getString("name"));
				vo.setPrivId(rs.getString("privilege_id"));
				resultList.add(vo);
			}
			return resultList;
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

	public ArrayList findPrivilegeData(String sql, String privId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, privId);
			stmt.setString(2, privId);

			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				SimpleDataPrivilegeVO vo = new SimpleDataPrivilegeVO();
				vo.setIsChecked(rs.getString("IsCheck"));
				vo.setDataId(rs.getString("id"));
				vo.setDataName(rs.getString("name"));
				resultList.add(vo);
			}
			return resultList;
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

	/**
	 * 查询分配到权限ID对应的权限的商品服务提供数据
	 */
	public PageModel getProdOfferDataPrivilegeByPrivilegeId(String privilegeId, int pageIndex, int pageSize )
			throws DAOSystemException {
		PageModel pageModel = new PageModel();
		
		String countSQL = "select count(*) from mm_data_privilege a "
				+ " left join service_offer b on b.service_offer_id = cast(a.data_pkey_2 as decimal(9,0)) "
				+ " left join product_offer c on c.offer_id = a.data_pkey_1"
				+ " where   a.privilege_id = " + privilegeId + " and b.service_offer_type in (0,2)";
		
		String sql = "select a.privilege_id, a.data_pkey_1, a.data_pkey_2, a.data_pkey_3, "
				+ " nvl(b.service_offer_name,'所有服务提供') as service_offer_name, nvl(c.offer_name, '所有商品') as offer_name "
				+ " from mm_data_privilege a "
				+ " left join service_offer b on b.service_offer_id = cast(a.data_pkey_2 as decimal(9,0)) "
				+ " left join product_offer c on c.offer_id = a.data_pkey_1"
				+ " where   a.privilege_id = ? and b.service_offer_type in (0,2)";

		String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
//			 计算 totalCount
		int totalCount = countBySQL(countSQL);
		pageModel.setTotalCount(totalCount);

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
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
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
			sql = DAOSQLUtils.getFilterSQL(sql);
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				//如果是Oracle数据库，按照物理分页的方式组装SQL
				sql = DAOSQLUtils.doPreFilter(false, sql, pageIndex,pageIndex) ;
			}
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;
			stmt.setString(index++, privilegeId);
			rs = stmt.executeQuery();
			
	        ArrayList resultList = new ArrayList();
	        if( "ORACLE".equalsIgnoreCase(databaseType)){
	        	while( rs.next() ){
	        		MmDataPrivVO vo = new MmDataPrivVO();
					vo.setPrivId(DAOUtils.trimStr(rs.getString("privilege_id")));
					vo.setDataPkey1(DAOUtils.trimStr(rs.getString("data_pkey_1")));
					vo.setDataPkey2(DAOUtils.trimStr(rs.getString("data_pkey_2")));
					vo.setDataPkey3(DAOUtils.trimStr(rs.getString("data_pkey_3")));
					vo.setOfferName(DAOUtils.trimStr(rs.getString("offer_name")));
					vo.setServiceOfferName(DAOUtils.trimStr(rs
							.getString("service_offer_name")));
					resultList.add(vo);
	        	}
	        }else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
				
				do {
					MmDataPrivVO vo = new MmDataPrivVO();
					vo.setPrivId(DAOUtils.trimStr(rs.getString("privilege_id")));
					vo.setDataPkey1(DAOUtils.trimStr(rs.getString("data_pkey_1")));
					vo.setDataPkey2(DAOUtils.trimStr(rs.getString("data_pkey_2")));
					vo.setDataPkey3(DAOUtils.trimStr(rs.getString("data_pkey_3")));
					vo.setOfferName(DAOUtils.trimStr(rs.getString("offer_name")));
					vo.setServiceOfferName(DAOUtils.trimStr(rs
							.getString("service_offer_name")));
					resultList.add(vo);
					count ++ ;
				}while (rs.next() && count < currentSize);
	        }
	        pageModel.setList(resultList);
			return pageModel ;
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

	/**
	 * 查询权限ID对应的客户数据权限
	 */
	public ArrayList getCustDataPrivilegeByPrivilegeId(String privilegeId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select a.privilege_id, a.data_pkey_1, a.data_pkey_2, a.data_pkey_3, "
				+ " b.dataset_name, nvl(c.field_name,'所有数据项') as field_name "
				//+ " from page_dataset b ,mm_data_privilege a  left join page_field c on c.field_id = cast(a.data_pkey_3 as decimal(9,0)) "
				+ " from page_dataset b ,mm_data_privilege a  left join page_field c on c.field_id = a.data_pkey_1 "
				+ " where b.dataset_id = cast(a.data_pkey_2 as decimal(9,0)) and a.privilege_id = ?";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;
			stmt.setString(index++, privilegeId);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmDataPrivVO vo = new MmDataPrivVO();
				vo.setPrivId(DAOUtils.trimStr(rs.getString("privilege_id")));
				vo.setDataPkey1(DAOUtils.trimStr(rs.getString("data_pkey_1")));
				vo.setDataPkey2(DAOUtils.trimStr(rs.getString("data_pkey_2")));
				vo.setDataPkey3(DAOUtils.trimStr(rs.getString("data_pkey_3")));
				vo.setDatasetName(DAOUtils
						.trimStr(rs.getString("dataset_name")));
				vo.setFieldName(DAOUtils.trimStr(rs.getString("field_name")));
				resultList.add(vo);
			}
			return resultList;
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

	public ArrayList getCustDataPrivilege(String privilegeId,
			String custTypeId, String datasetId) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		String sql = "Select 1 as IsCheck, a.field_id, a.field_name from page_field a "
			+ " where exists (select 1 from mm_data_privilege b where b.data_pkey_1= a.field_id and b.privilege_id = ? and b.data_pkey_3 = ? and b.data_pkey_2 =  ?)  and a.dataset_id = ? "
				+ " union "
				+ " select 0 as IsCheck, a.field_id, a.field_name from page_field a "
				+ " where not exists (select 1 from mm_data_privilege b where b.data_pkey_1 = a.field_id and b.privilege_id = ? and b.data_pkey_3 = ? and b.data_pkey_2 =  ?) and a.dataset_id = ? order by ischeck desc";
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;
			stmt.setString(index++, privilegeId);
			stmt.setString(index++, custTypeId);
			stmt.setString(index++, datasetId);
			stmt.setString(index++, datasetId);
			stmt.setString(index++, privilegeId);
			stmt.setString(index++, custTypeId);
			stmt.setString(index++, datasetId);
			stmt.setString(index++, datasetId);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				PageFieldVO vo = new PageFieldVO();
				vo.setIsChecked(DAOUtils.trimStr(rs.getString("IsCheck")));
				vo.setFieldId(DAOUtils.trimStr(rs.getString("field_id")));
				vo.setFieldName(DAOUtils.trimStr(rs.getString("field_name")));
				resultList.add(vo);
			}
			return resultList;
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
	
	public PageModel getProductOfferList(String serviceOfferId, String privilegeId, boolean selected, int pageIndex, int pageSize ) throws DAOSystemException{
		PageModel pageModel = new PageModel();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String countSQL = "";
		int totalCount ;
		
		if( selected )
			countSQL = "select count(*) from product_offer a "+ 
				"where exists ( select 1 from mm_data_privilege b "+  
				"where cast(b.data_pkey_1 as decimal(9,0)) = a.offer_id and b.privilege_id = "+ privilegeId +  
				" and b.data_pkey_2 = '" + serviceOfferId + "' ) and a.offer_id in ( select offer_id from offer_service_offer "+  
				"where service_offer_id = " + serviceOfferId + " ) ";
		else
			countSQL = "select count(*) "+  
				"from product_offer where offer_id in (select offer_id from offer_service_offer "+  
				"where service_offer_id= " + serviceOfferId + " ) ";
		
		totalCount = countBySQL( countSQL ) ;
		if( selected ){
			countSQL = "select count(*) from mm_data_privilege where privilege_id=" +privilegeId +
								" and data_pkey_1 = -1";
			totalCount = totalCount + countBySQL( countSQL ) ;
		}
		pageModel.setTotalCount(totalCount);
		
//		 空记录的处理
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
		} else if (pageIndex > pageModel.getPageCount()) {
			pageModel.setPageIndex(pageModel.getPageCount());
		} else {
			pageModel.setPageIndex(pageIndex);
		}

		if (pageSize < 0) {
			pageModel.setPageSize(totalCount);
		} else {
			pageModel.setPageSize(pageSize);
		}
		
		String sql = "";
			if( selected )
			sql = "select a.offer_id,a.offer_name from product_offer a "+ 
				"where exists ( select 1 from mm_data_privilege b "+  
				"where cast(b.data_pkey_1 as decimal(9,0)) = a.offer_id and b.privilege_id = ? "+  
				"and b.data_pkey_2 = ? ) and a.offer_id in ( select offer_id from offer_service_offer "+  
				"where service_offer_id =? ) " + 
				"union select data_pkey_1 offer_id, '所有商品' from mm_data_privilege where privilege_id = ? and data_pkey_1 = -1 ";
			else
				sql = "select offer_id,offer_name "+  
				"from product_offer where offer_id in (select offer_id from offer_service_offer "+  
				"where service_offer_id=? ) ";
		try {
			sql = DAOSQLUtils.getFilterSQL(sql);
			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				sql = DAOSQLUtils.doPreFilter(false,sql,pageIndex,pageSize);
			}
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;

			if( selected ){
				stmt.setString(index++, privilegeId);
				stmt.setString(index++, serviceOfferId);
				stmt.setString(index++, serviceOfferId);
				stmt.setString(index++, privilegeId);
			}else{
				stmt.setString(index++, serviceOfferId);
			}

			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					SimpleOfferServVO vo = new SimpleOfferServVO();
					vo.setServOffId(DAOUtils.trimStr(rs
							.getString("offer_id")));
					vo.setServOffName(DAOUtils.trimStr(rs
							.getString("offer_name")));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
				
				do {
					SimpleOfferServVO vo = new SimpleOfferServVO();
					//vo.setIsChecked(DAOUtils.trimStr(rs.getString("IsCheck")));
					vo.setServOffId(DAOUtils.trimStr(rs
							.getString("offer_id")));
					vo.setServOffName(DAOUtils.trimStr(rs
							.getString("offer_name")));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;
		} catch (SQLException se) {
			se.printStackTrace();
			Debug.print(sql, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	/**
	 * 查询商品对应的服务提供
	 * 
	 * @param isAllOffer
	 *            如果为1,表示所有的商品;如果为2,表示某一个指定的商品
	 * @param privilegeId
	 *            权限ID,
	 * @param offerId
	 *            商品ID
	 */
	public ArrayList getOfferServ(String isAllOffer, String privilegeId,
			String offerId) throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "";
		if ("1".equals(isAllOffer)) {// 全部商品
			sql = "Select 1 as IsCheck, a.service_offer_id, a.service_offer_name from service_offer a "
					+ "where exists (select 1 from mm_data_privilege b where cast(b.data_pkey_2 as decimal(9,0)) = a.service_offer_id and b.privilege_id = ? and b.data_pkey_1 = '-1') "
					+ " union "
					+ " Select 0 as IsCheck, a.service_offer_id, a.service_offer_name from service_offer a "
					+ " where not exists (select 1 from mm_data_privilege b where cast(b.data_pkey_2 as decimal(9,0)) = a.service_offer_id and b.privilege_id = ? and b.data_pkey_1 = '-1') order by IsCheck desc ";
		} else if ("2".equals(isAllOffer)) {// 某一个指定的商品
			sql = "Select 1 as IsCheck, a.service_offer_id, a.service_offer_name from service_offer a "
					+ " where exists (select 1 from mm_data_privilege b where cast(b.data_pkey_2 as decimal(9,0)) = a.service_offer_id and b.privilege_id = ? and b.data_pkey_1 = ?) "
					+ " and a.service_offer_id in (select service_offer_id from offer_service_offer where offer_id = ?) "
					+ " union "
					+ " Select 0 as IsCheck, a.service_offer_id, a.service_offer_name from service_offer a "
					+ " where not exists (select 1 from mm_data_privilege b where cast(b.data_pkey_2 as decimal(9,0)) = a.service_offer_id and b.privilege_id = ? and b.data_pkey_1 = ?) "
					+ " and a.service_offer_id in (select service_offer_id from offer_service_offer where offer_id = ?) order by IsCheck desc ";
		}
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;

			if ("1".equals(isAllOffer)) {
				stmt.setString(index++, privilegeId);
				stmt.setString(index++, privilegeId);
			} else if ("2".equals(isAllOffer)) {
				stmt.setString(index++, privilegeId);
				stmt.setString(index++, offerId);
				stmt.setString(index++, offerId);
				stmt.setString(index++, privilegeId);
				stmt.setString(index++, offerId);
				stmt.setString(index++, offerId);
			}
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				SimpleOfferServVO vo = new SimpleOfferServVO();
				vo.setIsChecked(DAOUtils.trimStr(rs.getString("IsCheck")));
				vo.setServOffId(DAOUtils.trimStr(rs
						.getString("service_offer_id")));
				vo.setServOffName(DAOUtils.trimStr(rs
						.getString("service_offer_name")));
				resultList.add(vo);
			}
			return resultList;
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

	/**
	 * 根据权限ID查询关联的定价参数目录
	 * 
	 * @param privilegeIds
	 */
	public ArrayList getPricingParamCatalogByPrivilege(ArrayList privilegeIds)
			throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);

		String sql = "select DATA_PKEY_1 from mm_data_privilege where privilege_id in ("
				+ privilegeIdCond + ")";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				resultList.add(rs.getString("DATA_PKEY_1"));
			}
			return resultList;
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

	/**
	 * 根据权限ID查询关联的号码等级ID
	 * 
	 * @param privilegeIds
	 */
	public ArrayList getRnLevelByPrivilege(ArrayList privilegeIds)
			throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);

		String sql = "select DATA_PKEY_1 from mm_data_privilege where privilege_id in ("
				+ privilegeIdCond + ")";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				resultList.add(rs.getString("DATA_PKEY_1"));
			}
			return resultList;
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
	
	/**
	 * 根据权限ID查询关联的组织ID
	 * 
	 * @param privilegeIds
	 */
	public ArrayList getOrganizationByPrivilege(ArrayList privilegeIds,String cProductId)
			throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);

		String sql = "select a.privilege_id,c.privilege_name,a.DATA_PKEY_1,a.DATA_PKEY_2,a.DATA_PKEY_3,a.DATA_PKEY_4 from mm_data_privilege a,product_number_requirement b,privilege c where a.privilege_id in("+privilegeIdCond+") and a.privilege_id = c.privilege_id and a.data_pkey_1 = b.no_family_id and b.product_id='"+cProductId+"'";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmDataPrivVO vo = new MmDataPrivVO();
				vo.setPrivId(rs.getString("privilege_id"));
				vo.setDataPkey1(rs.getString("DATA_PKEY_1"));
				vo.setDataPkey2(rs.getString("DATA_PKEY_2"));
				vo.setDataPkey3(rs.getString("DATA_PKEY_3"));
				vo.setDataPkey4(rs.getString("DATA_PKEY_4"));
				vo.setName(rs.getString("privilege_name"));
				resultList.add(vo);
			}
			return resultList;
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
	
	
	/**
	 * 根据权限ID查询关联的组织ID
	 * 
	 * @param privilegeIds
	 */
	public ArrayList getOrgForNoHeadByPrivilege(ArrayList privilegeIds,String noFamilyId)
			throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);

		String sql = "select a.privilege_id,c.privilege_name,a.DATA_PKEY_1,a.DATA_PKEY_2,a.DATA_PKEY_3,a.DATA_PKEY_4 from mm_data_privilege a,rn_family b,privilege c where a.privilege_id in("+privilegeIdCond+") and a.privilege_id = c.privilege_id and a.data_pkey_1 = b.no_family_id and b.no_family_id='"+noFamilyId+"'";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmDataPrivVO vo = new MmDataPrivVO();
				vo.setPrivId(rs.getString("privilege_id"));
				vo.setDataPkey1(rs.getString("DATA_PKEY_1"));
				vo.setDataPkey2(rs.getString("DATA_PKEY_2"));
				vo.setDataPkey3(rs.getString("DATA_PKEY_3"));
				vo.setDataPkey4(rs.getString("DATA_PKEY_4"));
				vo.setName(rs.getString("privilege_name"));
				resultList.add(vo);
			}
			return resultList;
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
	
	/**
	 * 根据权限ID查询关联的组织ID
	 * 
	 * @param privilegeIds
	 */
	public ArrayList getOrgForDeliverNoByPrivilege(ArrayList privilegeIds,String noFamilyId)
			throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);
		String sql = "";
		if(noFamilyId!=null&&!"".equalsIgnoreCase(noFamilyId)){
			sql = "select a.privilege_id,c.privilege_name,a.DATA_PKEY_1,a.DATA_PKEY_2,a.DATA_PKEY_3,a.DATA_PKEY_4 from mm_data_privilege a,rn_family b,privilege c where a.privilege_id in("+privilegeIdCond+") and a.privilege_id = c.privilege_id and a.data_pkey_1 = b.no_family_id and b.no_family_id="+noFamilyId;
		}else{
			sql = "select a.privilege_id,c.privilege_name,a.DATA_PKEY_1,a.DATA_PKEY_2,a.DATA_PKEY_3,a.DATA_PKEY_4 from mm_data_privilege a,rn_family b,privilege c where a.privilege_id in("+privilegeIdCond+") and a.privilege_id = c.privilege_id and a.data_pkey_1 = b.no_family_id ";
		}
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmDataPrivVO vo = new MmDataPrivVO();
				vo.setPrivId(rs.getString("privilege_id"));
				vo.setDataPkey1(rs.getString("DATA_PKEY_1"));
				vo.setDataPkey2(rs.getString("DATA_PKEY_2"));
				vo.setDataPkey3(rs.getString("DATA_PKEY_3"));
				vo.setDataPkey4(rs.getString("DATA_PKEY_4"));
				vo.setName(rs.getString("privilege_name"));
				resultList.add(vo);
			}
			return resultList;
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

	public ArrayList getOfferServiceByPrivilege(String offerId,
			ArrayList privilegeIds) throws DAOSystemException {
		String privilegeIdCond = "";
		for (int i = 0; i < privilegeIds.size(); i++) {
			privilegeIdCond = privilegeIdCond + (String) privilegeIds.get(i)
					+ ",";
		}
		privilegeIdCond = privilegeIdCond.substring(0,
				privilegeIdCond.length() - 1);

		String sql = "select a.DATA_PKEY_2, b.service_offer_name from mm_data_privilege a, service_offer b"
				+ " where a.DATA_PKEY_2 = b.service_offer_id AND ( a.DATA_PKEY_1 = '"
				+ offerId
				+ "'"
				+ " OR a.DATA_PKEY_1 = '-1' )"
				+ " AND a.privilege_id in (" + privilegeIdCond + ")";

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			int index = 1;

			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				SimpleOfferServVO vo = new SimpleOfferServVO();
				vo.setServOffId(DAOUtils.trimStr(rs.getString("DATA_PKEY_2")));
				vo.setServOffName(DAOUtils.trimStr(rs
						.getString("service_offer_name")));
				resultList.add(vo);
			}
			return resultList;
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

	public int countBySQL(String SQL) {
		Connection conn = null;
		int lCount = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL));
			rs = stmt.executeQuery();

			while (rs.next()) {
				lCount = rs.getInt(1);
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

	public PageModel queryOfferWarr(OffWarrRequementVO vo, int pageIndex,
			int pageSize) throws DAOSystemException {
		String countSQL = " select count(*) from OFFER_WARR_REQUEMENT a, PRODUCT_OFFER b where a.offer_id = b.offer_id ";

		String selectSQL = "select a.OFFER_WARR_REQUEMENT_ID, a.OFFER_ID,a.WARR_CONTENT,a.WARR_MODE,a.WARR_VALUE,a.RESTRICT_SERVICES,a.WARR_LEN,a.UNITE_ACCT_REQUEST,a.MAX_WARR_NUM,a.MAX_UNITE_ACCT_NUM, " +
										" b.OFFER_NAME from OFFER_WARR_REQUEMENT a, PRODUCT_OFFER b where a.offer_id = b.offer_id ";

		String cond = "";
		if( !"".equals(vo.getOffName())){
			cond = cond + " AND b.OFFER_NAME like '%" + vo.getOffName() + "%'";
		}
		if( !"".equals(vo.getWarrContent())){
			cond = cond + " AND a.WARR_CONTENT = '" + vo.getWarrContent() + "'";
		}
		if( !"".equals(vo.getWarrMode())){
			cond = cond + " AND a.WARR_MODE = '" + vo.getWarrMode() + "'";
		}
		if( !"".equals(vo.getWarrValue())){
			cond = cond + " AND a.WARR_VALUE = '" + vo.getWarrValue() + "'";
		}
		if( !"".equals(vo.getWarrLen())){
			cond = cond + " AND a.WARR_LEN = " + vo.getWarrLen() ;
		}
		if( !"".equals(vo.getUniteAcctRequest())){
			cond = cond + " AND a.UNITE_ACCT_REQUEST = '" + vo.getUniteAcctRequest() + "'";
		}
		if( !"".equals(vo.getMaxWarrNum())){
			cond = cond + " AND a.MAX_WARR_NUM = " + vo.getMaxWarrNum() ;
		}
		if( !"".equals(vo.getMaxUniteAcctNum())){
			cond = cond + " AND a.MAX_UNITE_ACCT_NUM = " + vo.getMaxUniteAcctNum() ;
		}
		
		countSQL = countSQL + cond ;
		selectSQL = selectSQL + cond ;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}

			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			selectSQL = DAOSQLUtils.getFilterSQL(selectSQL);
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				selectSQL = DAOSQLUtils.doPreFilter(false,selectSQL,pageIndex,pageSize);
			}

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(selectSQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			ArrayList resultList = new ArrayList();
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next()){
					OffWarrRequementVO offWarrRequementVO = new OffWarrRequementVO();
					//vo.setPrivId(privilegeId);
					offWarrRequementVO.setOffId(rs.getString("OFFER_ID"));
					offWarrRequementVO.setOffName(rs.getString("OFFER_NAME"));
					offWarrRequementVO.setOffWarrRequementId(rs
							.getString("OFFER_WARR_REQUEMENT_ID"));
					offWarrRequementVO.setWarrContent(rs.getString("WARR_CONTENT"));
					offWarrRequementVO.setWarrMode(rs.getString("WARR_MODE"));
					offWarrRequementVO.setWarrValue(rs.getString("WARR_VALUE"));
					offWarrRequementVO.setRestrictServices(rs.getString("RESTRICT_SERVICES"));
					offWarrRequementVO.setWarrLen(rs.getString("WARR_LEN"));
					offWarrRequementVO.setUniteAcctRequest(rs.getString("UNITE_ACCT_REQUEST"));
					offWarrRequementVO.setMaxUniteAcctNum(rs.getString("MAX_UNITE_ACCT_NUM"));
					offWarrRequementVO.setMaxWarrNum(rs.getString("MAX_WARR_NUM"));
					resultList.add(offWarrRequementVO);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
	
				do {
					OffWarrRequementVO offWarrRequementVO = new OffWarrRequementVO();
					//vo.setPrivId(privilegeId);
					offWarrRequementVO.setOffId(rs.getString("OFFER_ID"));
					offWarrRequementVO.setOffName(rs.getString("OFFER_NAME"));
					offWarrRequementVO.setOffWarrRequementId(rs
							.getString("OFFER_WARR_REQUEMENT_ID"));
					offWarrRequementVO.setWarrContent(rs.getString("WARR_CONTENT"));
					offWarrRequementVO.setWarrMode(rs.getString("WARR_MODE"));
					offWarrRequementVO.setWarrValue(rs.getString("WARR_VALUE"));
					offWarrRequementVO.setRestrictServices(rs.getString("RESTRICT_SERVICES"));
					offWarrRequementVO.setWarrLen(rs.getString("WARR_LEN"));
					offWarrRequementVO.setUniteAcctRequest(rs.getString("UNITE_ACCT_REQUEST"));
					offWarrRequementVO.setMaxUniteAcctNum(rs.getString("MAX_UNITE_ACCT_NUM"));
					offWarrRequementVO.setMaxWarrNum(rs.getString("MAX_WARR_NUM"));
					resultList.add(offWarrRequementVO);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

		} catch (SQLException se) {
			Debug.print(selectSQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ selectSQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public PageModel getOfferWarrByPrivilegeId(String privilegeId,
			int pageIndex, int pageSize) throws DAOSystemException {
		String countSQL = "select count(*) from OFFER_WARR_REQUEMENT a, PRODUCT_OFFER b, MM_DATA_PRIVILEGE c where a.offer_id = b.offer_id and c.data_pkey_1 = a.OFFER_WARR_REQUEMENT_ID "
				+ " and c.privilege_id = " + privilegeId;
		String selectSQL = "select a.OFFER_WARR_REQUEMENT_ID,a.OFFER_ID,a.WARR_CONTENT,a.WARR_MODE,a.WARR_VALUE,a.RESTRICT_SERVICES,a.WARR_LEN,a.UNITE_ACCT_REQUEST,a.MAX_WARR_NUM,a.MAX_UNITE_ACCT_NUM, "
				+ " b.OFFER_NAME from OFFER_WARR_REQUEMENT a, PRODUCT_OFFER b, MM_DATA_PRIVILEGE c where a.offer_id = b.offer_id and c.data_pkey_1 = a.OFFER_WARR_REQUEMENT_ID "
				+ " and c.privilege_id = " + privilegeId;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}
			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			selectSQL = DAOSQLUtils.getFilterSQL(selectSQL);
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
				while(rs.next()){
					OffWarrRequementVO vo = new OffWarrRequementVO();
					vo.setPrivId(privilegeId);
					vo.setOffId(rs.getString("OFFER_ID"));
					vo.setOffName(rs.getString("OFFER_NAME"));
					vo.setOffWarrRequementId(rs
							.getString("OFFER_WARR_REQUEMENT_ID"));
					vo.setWarrContent(rs.getString("WARR_CONTENT"));
					vo.setWarrMode(rs.getString("WARR_MODE"));
					vo.setWarrValue(rs.getString("WARR_VALUE"));
					vo.setRestrictServices(rs.getString("RESTRICT_SERVICES"));
					vo.setWarrLen(rs.getString("WARR_LEN"));
					vo.setUniteAcctRequest(rs.getString("UNITE_ACCT_REQUEST"));
					vo.setMaxUniteAcctNum(rs.getString("MAX_UNITE_ACCT_NUM"));
					vo.setMaxWarrNum(rs.getString("MAX_WARR_NUM"));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
	
				do {
					OffWarrRequementVO vo = new OffWarrRequementVO();
					vo.setPrivId(privilegeId);
					vo.setOffId(rs.getString("OFFER_ID"));
					vo.setOffName(rs.getString("OFFER_NAME"));
					vo.setOffWarrRequementId(rs
							.getString("OFFER_WARR_REQUEMENT_ID"));
					vo.setWarrContent(rs.getString("WARR_CONTENT"));
					vo.setWarrMode(rs.getString("WARR_MODE"));
					vo.setWarrValue(rs.getString("WARR_VALUE"));
					vo.setRestrictServices(rs.getString("RESTRICT_SERVICES"));
					vo.setWarrLen(rs.getString("WARR_LEN"));
					vo.setUniteAcctRequest(rs.getString("UNITE_ACCT_REQUEST"));
					vo.setMaxUniteAcctNum(rs.getString("MAX_UNITE_ACCT_NUM"));
					vo.setMaxWarrNum(rs.getString("MAX_WARR_NUM"));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

		} catch (SQLException se) {
			Debug.print(selectSQL, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ selectSQL, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	public PageModel getPriceCataContentByPrivilegeId(String privilegeId,
			int pageIndex, int pageSize) throws DAOSystemException {
		String sql1 = "select DATA_PKEY_1 from mm_data_privilege where privilege_id = ?";
		String sql2 = "select b.offer_name,c.pricing_plan_name,a.pricing_param_catalog_id, "
				+ " a.CATALOG_TYPE,a.pricing_param_catalog_name, e.product_name "
				+ " from PRICING_PARAM_CATALOG a,product_offer b, PRICING_PLAN c, PRICING_OBJECT d, product e, mm_data_privilege f  "
				+ " where a.PRICING_PLAN_ID=b.PRICING_PLAN_ID "
				+ " and a.PRICING_PLAN_ID=c. PRICING_PLAN_ID "
				+ " and a.PRICING_OBJECT_ID=d.PRICING_OBJECT_ID "
				+ " and d.OBJECT_ID=e.product_id"
				+ " and f.DATA_PKEY_1 = a.pricing_param_catalog_id "
				+ " and f.privilege_id = " + privilegeId;

		String countSQL = "select count(*) "
				+ " from PRICING_PARAM_CATALOG a,product_offer b, PRICING_PLAN c, PRICING_OBJECT d, product e, mm_data_privilege f  "
				+ " where a.PRICING_PLAN_ID=b.PRICING_PLAN_ID "
				+ " and a.PRICING_PLAN_ID=c. PRICING_PLAN_ID "
				+ " and a.PRICING_OBJECT_ID=d.PRICING_OBJECT_ID "
				+ " and d.OBJECT_ID=e.product_id"
				+ " and f.DATA_PKEY_1 = a.pricing_param_catalog_id "
				+ " and f.privilege_id = " + privilegeId;

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql1),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			stmt.setString(1, privilegeId);
			rs = stmt.executeQuery();

			String pricingParamCatalogIdCond = "";
			ArrayList resultList = new ArrayList();
			String id = "";
			if (rs.next()) {
				id = rs.getString("DATA_PKEY_1");
				pricingParamCatalogIdCond = pricingParamCatalogIdCond + id
						+ ",";
			}
			if (!"".equals(pricingParamCatalogIdCond)) {
				if ("-1".equals(id.trim())) {// 全部商品
					SimplePricCataContentVO vo = new SimplePricCataContentVO();
					vo.setOfferName("全部商品");
					vo.setPricingPlanName("全部定价计划");
					vo.setPricingParamCatalogName("全部目录");
					vo.setCatalogType("");// 全部类型
					vo.setPricingParamCatalogId("-1");
					vo.setProductName("");
					resultList.add(vo);
					pageModel.setList(resultList);
					pageModel.setPageCount(1);
					pageModel.setPageIndex(1);
					pageModel.setPageSize(pageSize);
					pageModel.setTotalCount(1);
					return pageModel;
				}
				pricingParamCatalogIdCond = pricingParamCatalogIdCond
						.substring(0, pricingParamCatalogIdCond.length() - 1);
			} else {
				return new PageModel();
			}

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}

			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			sql2 = DAOSQLUtils.getFilterSQL(sql2);
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				sql2 = DAOSQLUtils.doPreFilter(false,sql2,pageIndex,pageSize);
			}
			
			stmt = conn
					.prepareStatement(sql2,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					SimplePricCataContentVO vo = new SimplePricCataContentVO();
					vo.setOfferName(rs.getString("offer_name"));
					vo.setPricingPlanName(rs.getString("pricing_plan_name"));
					vo.setPricingParamCatalogId(rs
							.getString("pricing_param_catalog_id"));
					vo.setCatalogType(rs.getString("CATALOG_TYPE"));
					vo.setPricingParamCatalogName(rs
							.getString("pricing_param_catalog_name"));
					vo.setProductName(rs.getString("product_name"));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
	
				do {
					SimplePricCataContentVO vo = new SimplePricCataContentVO();
					vo.setOfferName(rs.getString("offer_name"));
					vo.setPricingPlanName(rs.getString("pricing_plan_name"));
					vo.setPricingParamCatalogId(rs
							.getString("pricing_param_catalog_id"));
					vo.setCatalogType(rs.getString("CATALOG_TYPE"));
					vo.setPricingParamCatalogName(rs
							.getString("pricing_param_catalog_name"));
					vo.setProductName(rs.getString("product_name"));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

		} catch (SQLException se) {
			Debug.print(sql2, this);
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ sql2, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			//DAOUtils.closeConnection(conn, this);
		}
	}

	/**
	 * 取当前页面当前数据集当前客户类型下的可读字段列表．
	 */
	public ArrayList getCustCtrlDataInfoFromDatabase(String pageCode,
			String datasetCode, String custTypeId, ArrayList privilegeIds)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// 通过dataset_code和page_code查询出对应的dataset_id
		String SQL = "select a.dataset_id from page_dataset a, data_page b where a.page_id = b.page_id and "
				+ " a.dataset_code = ? and b.page_code = ?";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			stmt.setString(1, datasetCode);
			stmt.setString(2, pageCode);

			rs = stmt.executeQuery();
			String datasetId = "";
			if (rs.next()) {
				datasetId = rs.getString("dataset_id");
			}
			if ("".equals(datasetId)) {// 如果dataset_id为空,则 直接返回空列表
				return new ArrayList();
			}

			StringBuffer privCond = new StringBuffer();
			for (int i = 0; i < privilegeIds.size(); i++) {
				privCond.append(",").append((String) privilegeIds.get(i));
			}
			privCond.deleteCharAt(0);

			// 查找数据项列表
			//SQL = "select cast(a.data_pkey_3 as decimal(9,0)) as field_id, b.field_code from mm_data_privilege a "
			SQL = "select a.data_pkey_1 as field_id, b.field_code from mm_data_privilege a "
					//+ " left join page_field b on b.field_id= cast(a.data_pkey_3 as decimal(9,0)) "
					+ " left join page_field b on b.field_id= a.data_pkey_1 "
					+ " where privilege_id in ( "
					+ privCond.toString()
					//+ " ) and a.data_pkey_1 = '"
					+ " ) and a.data_pkey_3 = '"
					+ custTypeId
					+ "' and a.data_pkey_2 = '" + datasetId + "'";
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);

			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			boolean queryAllField = false;

			while (rs.next()) {
				if ("-1".equals(rs.getString("field_id"))) {// 如果查询出来的Field_ID为-1,则需要查询数据集的所有FIELD_ID
					queryAllField = true;
					break;
				}
				CustCtrlInfoVo vo = new CustCtrlInfoVo();
				vo.setCustTypeId(custTypeId);
				vo.setDatasetCode(datasetCode);
				vo.setPageCode(pageCode);
				vo.setFieldCode(rs.getString("field_code"));
				resultList.add(vo);
			}
			if (queryAllField) {// 如果field_id=-1,则取数据集的所有数据项
				SQL = "select field_id, field_code from page_field where dataset_id = ?";
				stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL),
						ResultSet.TYPE_SCROLL_SENSITIVE,
						ResultSet.CONCUR_UPDATABLE);
				stmt.setString(1, datasetId);
				rs = stmt.executeQuery();
				while (rs.next()) {
					CustCtrlInfoVo vo = new CustCtrlInfoVo();
					vo.setCustTypeId(custTypeId);
					vo.setDatasetCode(datasetCode);
					vo.setPageCode(pageCode);
					vo.setFieldCode(rs.getString("field_code"));
					resultList.add(vo);
				}
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

	public ArrayList getMenuDataPrivilege(String privilegeId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select a.menu_id, a.menu_code, a.menu_name, a.path_code from mm_menu a, mm_data_privilege b "
				+ " where a.menu_id = b.data_pkey_1 and b.privilege_id = "
				+ privilegeId + " order by a.path_code ";
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();

			while (rs.next()) {
				MmMenuVO vo = new MmMenuVO();
				vo.setMenuCode(rs.getString("menu_code"));
				vo.setMenuId(rs.getString("menu_id"));
				vo.setMenuName(rs.getString("menu_name"));
				vo.setPathCode(rs.getString("path_code"));
				resultList.add(vo);
			}
			return resultList;
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

	public ArrayList getMenuIdByPrivilege(String privId)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "select data_pkey_1 from mm_data_privilege where privilege_id = "
				+ privId;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn
					.prepareStatement(DAOSQLUtils.getFilterSQL(sql),
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();

			while (rs.next()) {
				resultList.add(rs.getString(1));
			}
			return resultList;
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

	public PageModel queryAttachProductList( String privilegeId , String productCode, String productName, int pageIndex, int pageSize ) throws DAOSystemException {
		String SQL = "";
		String countSQL = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {
			countSQL = "select count(*) " +
						" FROM PRODUCT A " +
						" WHERE A.PRODUCT_ID NOT IN (" + 
						" SELECT DATA_PKEY_1 FROM MM_DATA_PRIVILEGE B" +
						" WHERE B.PRIVILEGE_ID = " + privilegeId + " )" ;
			SQL = "select A.PRODUCT_ID, A.PRODUCT_PROVIDER_ID, A.PRODUCT_NAME, "+
						" A.product_comments,A.PRODUCT_CODE " +
						" FROM PRODUCT A " +
						" WHERE A.PRODUCT_ID NOT IN (" + 
						" SELECT DATA_PKEY_1 FROM MM_DATA_PRIVILEGE B" +
						" WHERE B.PRIVILEGE_ID = " + privilegeId + " )" ;
			if( !"".equals(productCode)){
				SQL = SQL + " AND A.PRODUCT_CODE LIKE '%" + productCode + "%'" ;
				countSQL = countSQL + " AND A.PRODUCT_CODE LIKE '%" + productCode + "%'" ;
			}
			if( !"".equals(productName)){
				SQL = SQL + " AND A.PRODUCT_NAME LIKE '%" + productName + "%'";
				countSQL = countSQL + " AND A.PRODUCT_NAME LIKE '%" + productName + "%'";
			}
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}

			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			SQL = DAOSQLUtils.getFilterSQL(SQL) ;
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				SQL = DAOSQLUtils.doPreFilter(false,SQL,pageIndex,pageSize);
			}
			
			stmt = conn
					.prepareStatement(SQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					SimpleProductVO vo = new SimpleProductVO();
					vo.setProductId(rs.getString("PRODUCT_ID"));
					vo.setProductPrividerId(rs.getString("PRODUCT_PROVIDER_ID"));
					vo.setProductCode(rs.getString("PRODUCT_CODE"));
					vo.setProductName(rs.getString("PRODUCT_NAME"));
					vo.setProductComments(rs.getString("product_comments"));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
				
				do {
					SimpleProductVO vo = new SimpleProductVO();
					vo.setProductId(rs.getString("PRODUCT_ID"));
					vo.setProductPrividerId(rs.getString("PRODUCT_PROVIDER_ID"));
					vo.setProductCode(rs.getString("PRODUCT_CODE"));
					vo.setProductName(rs.getString("PRODUCT_NAME"));
					vo.setProductComments(rs.getString("product_comments"));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

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
	
	public PageModel getAttachProductByPrivilegeId( String privId , int pageIndex , int pageSize ) throws DAOSystemException {
		String SQL = "";
		String countSQL = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {
			countSQL = "select count(*) " +
						" FROM PRODUCT A, MM_DATA_PRIVILEGE B " +
						" WHERE B.DATA_PKEY_1 = A.PRODUCT_ID "+
						" AND B.PRIVILEGE_ID = " + privId ;
			SQL = "select A.PRODUCT_ID, A.PRODUCT_PROVIDER_ID, A.PRODUCT_NAME, "+
						" A.product_comments,A.PRODUCT_CODE " +
						" FROM PRODUCT A, MM_DATA_PRIVILEGE B " +
						" WHERE B.DATA_PKEY_1 = A.PRODUCT_ID "+
						" AND B.PRIVILEGE_ID = " + privId ;
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}

			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			SQL = DAOSQLUtils.getFilterSQL(SQL) ;
			if("ORACLE".equalsIgnoreCase(databaseType)){
				SQL = DAOSQLUtils.doPreFilter(false,SQL,pageIndex,pageSize);
			}
			
			stmt = conn
					.prepareStatement(SQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if( "ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					SimpleProductVO vo = new SimpleProductVO();
					vo.setProductId(rs.getString("PRODUCT_ID"));
					vo.setProductPrividerId(rs.getString("PRODUCT_PROVIDER_ID"));
					vo.setProductCode(rs.getString("PRODUCT_CODE"));
					vo.setProductName(rs.getString("PRODUCT_NAME"));
					vo.setProductComments(rs.getString("product_comments"));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
				
				do {
					SimpleProductVO vo = new SimpleProductVO();
					vo.setProductId(rs.getString("PRODUCT_ID"));
					vo.setProductPrividerId(rs.getString("PRODUCT_PROVIDER_ID"));
					vo.setProductCode(rs.getString("PRODUCT_CODE"));
					vo.setProductName(rs.getString("PRODUCT_NAME"));
					vo.setProductComments(rs.getString("product_comments"));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

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
	public PageModel queryServiceOffer(String serviceOfferName , int pageIndex , int pageSize) throws DAOSystemException{
		String SQL = "";
		String countSQL = "";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		PageModel pageModel = new PageModel();
		try {
			String cond = "";
			if( serviceOfferName != null && !"".equals(serviceOfferName)){
				cond = "where service_offer_name like '%" + serviceOfferName + "%'";
			}
			countSQL = "select count(*) " +
						" FROM service_offer " + cond ;
			
			SQL = "select service_offer_id, service_offer_name from service_offer " + cond;
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;

			// 计算 totalCount
			int totalCount = countBySQL(countSQL);
			pageModel.setTotalCount(totalCount);

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
			} else if (pageIndex > pageModel.getPageCount()) {
				pageModel.setPageIndex(pageModel.getPageCount());
			} else {
				pageModel.setPageIndex(pageIndex);
			}

			if (pageSize < 0) {
				pageModel.setPageSize(totalCount);
			} else {
				pageModel.setPageSize(pageSize);
			}

			SQL = DAOSQLUtils.getFilterSQL(SQL) ;
			String databaseType = CrmParamsConfig.getInstance().getParamValue("DATABASE_TYPE");
			if("ORACLE".equalsIgnoreCase(databaseType)){
				SQL = DAOSQLUtils.doPreFilter(false,SQL,pageIndex,pageSize);
			}
			
			stmt = conn
					.prepareStatement(SQL,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
			rs = stmt.executeQuery();
			List resultList = new ArrayList();
			if("ORACLE".equalsIgnoreCase(databaseType)){
				while( rs.next() ){
					SimpleServiceOfferVO vo = new SimpleServiceOfferVO();
					vo.setServiceOfferId(rs.getString("service_offer_id"));
					vo.setServiceOfferName(rs.getString("service_offer_name"));
					resultList.add(vo);
				}
			}else{
				int currentSize = 0;
				if (pageModel.getPageIndex() >= pageModel.getPageCount()) {
					int startIndex = pageModel.getPageCount();
					currentSize = pageModel.getTotalCount() - (startIndex - 1)
							* pageModel.getPageSize();
				} else {
					currentSize = pageModel.getPageSize();
				}
	
		        if( !rs.next() ){
		        	pageModel.setList( new ArrayList());
		        	return pageModel ;
		        }
		        
				int locationInt = (pageModel.getPageIndex() - 1)
						* pageModel.getPageSize() + 1;
				rs.absolute(locationInt);
				int count = 0;
				
				do {
					SimpleServiceOfferVO vo = new SimpleServiceOfferVO();
					vo.setServiceOfferId(rs.getString("service_offer_id"));
					vo.setServiceOfferName(rs.getString("service_offer_name"));
					resultList.add(vo);
					count++;
				} while (rs.next() && count < currentSize);
			}
			pageModel.setList(resultList);
			return pageModel;

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
