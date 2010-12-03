package com.ztesoft.oaas.dao.mmmenu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import com.ztesoft.oaas.dao.staff.StaffDAO;
import com.ztesoft.oaas.dao.staff.StaffDAOFactory;
import com.ztesoft.oaas.vo.MmMenuVO;

public class MmMenuDAOImpl   implements MmMenuDAO {

	private String SQL_SELECT = "SELECT menu_id,system_id,menu_code,menu_name,order_id,target_name,parameter,open_flag,privilege_flag,valid_flag,menu_type,menu_grade,super_id,image_path,comments,path_code FROM MM_MENU";

	private String SQL_SELECT_COUNT = "SELECT COUNT(*) AS COL_COUNTS FROM MM_MENU";

	private int maxRows;

	private String SQL_INSERT = "INSERT INTO MM_MENU ( menu_id,system_id,menu_code,menu_name,order_id,target_name,parameter,open_flag,privilege_flag,valid_flag,menu_type,menu_grade,super_id,image_path,comments,path_code ) VALUES ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? )";

	private String SQL_UPDATE = "UPDATE MM_MENU SET  system_id = ?, menu_code = ?, menu_name = ?, order_id = ?, target_name = ?, parameter = ?, open_flag = ?, privilege_flag = ?, valid_flag = ?, menu_type = ?, menu_grade = ?, super_id = ?, image_path = ?, comments = ?, path_code = ? WHERE menu_id = ? ";

	private String SQL_DELETE = "DELETE FROM MM_MENU WHERE menu_id = ? ";

	private String SQL_DELETE_BY_WHERE = "DELETE FROM MM_MENU ";

	public MmMenuDAOImpl() {

	}

	public MmMenuVO findByPrimaryKey(String pmenu_id) throws DAOSystemException {
		ArrayList arrayList = findBySql(SQL_SELECT + " WHERE menu_id = ? ",
				new String[] { pmenu_id });
		if (arrayList.size() > 0)
			return (MmMenuVO) arrayList.get(0);
		else
			return null;
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
			MmMenuVO vo = new MmMenuVO();
			populateDto(vo, rs);
			resultList.add(vo);
		}
		return resultList;
	}

	protected void populateDto(MmMenuVO vo, ResultSet rs) throws SQLException {
		vo.setSuperId(rs.getString("super_id"));
		vo.setMenuId(rs.getString("menu_id"));
		vo.setSystemId(rs.getString("system_id"));
		vo.setMenuCode(rs.getString("menu_code"));
		vo.setMenuName(rs.getString("menu_name"));
		vo.setOrderId(rs.getString("order_id"));
		vo.setTargetName(rs.getString("target_name"));
		vo.setPara(rs.getString("parameter"));
		vo.setPrivFlag(rs.getString("privilege_flag"));
		vo.setValidFlag(rs.getString("valid_flag"));
		vo.setMenuType(rs.getString("menu_type"));
		vo.setMenuGrade(rs.getString("menu_grade"));
		vo.setImagePath(rs.getString("image_path"));
		vo.setComments(rs.getString("comments"));
		vo.setPathCode(rs.getString("path_code"));
		vo.setOpenFlag(rs.getString("open_flag"));
	}

	//ר����ҳ�˵�����ʹ��
	protected void populateDto2(MmMenuVO vo, ResultSet rs) throws SQLException {
		vo.setSuperId(rs.getString("super_id"));
		vo.setMenuId(rs.getString("menu_id"));
		vo.setSystemId(rs.getString("system_id"));
		vo.setMenuCode(rs.getString("menu_code"));
		vo.setMenuName(rs.getString("menu_name"));
		vo.setOrderId(rs.getString("order_id"));
		vo.setTargetName(rs.getString("target_name"));
		vo.setPara(rs.getString("parameter"));
		vo.setPrivFlag(rs.getString("privilege_flag"));
		vo.setValidFlag(rs.getString("valid_flag"));
		vo.setMenuType(rs.getString("menu_type"));
		vo.setMenuGrade(rs.getString("menu_grade"));
		vo.setImagePath(rs.getString("image_path"));
		vo.setComments(rs.getString("comments"));
		vo.setPathCode(rs.getString("path_code"));
		vo.setOpenFlag( rs.getString("open_flag"));
	}

	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException {
		MmMenuVO vo = new MmMenuVO();
		try {
			populateDto(vo, rs);
		} catch (SQLException se) {
			Debug.print("populateCurrRecord����", this);
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
					.append("UPDATE MM_MENU SET menu_id = ?,system_id = ?,menu_code = ?,menu_name = ?,order_id = ?,target_name = ?,parameter = ?,open_flag = ?,privilege_flag = ?,valid_flag = ?,menu_type = ?,menu_grade = ?,super_id = ?,image_path = ?,comments = ?,path_code = ?");
			sql.append(" WHERE " + whereCond);
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((MmMenuVO) vo).getMenuId())) {
				((MmMenuVO) vo).setMenuId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getMenuId());
			if ("".equals(((MmMenuVO) vo).getSystemId())) {
				((MmMenuVO) vo).setSystemId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getSystemId());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuCode());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuName());
			if ("".equals(((MmMenuVO) vo).getOrderId())) {
				((MmMenuVO) vo).setOrderId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getOrderId());
			stmt.setString(index++, ((MmMenuVO) vo).getTargetName());
			stmt.setString(index++, ((MmMenuVO) vo).getPara());
			stmt.setString(index++, ((MmMenuVO) vo).getOpenFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getPrivFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getValidFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuType());
			if ("".equals(((MmMenuVO) vo).getMenuGrade())) {
				((MmMenuVO) vo).setMenuGrade(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getMenuGrade());
			if ("".equals(((MmMenuVO) vo).getSuperId())) {
				((MmMenuVO) vo).setSuperId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getSuperId());
			stmt.setString(index++, ((MmMenuVO) vo).getImagePath());
			stmt.setString(index++, ((MmMenuVO) vo).getComments());
			stmt.setString(index++, ((MmMenuVO) vo).getPathCode());
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
		return new MmMenuVO();
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
	 * ��ȡ���в˵��б���path_code����
	 * 
	 * @return ���в˵�(MmMenuVO)���ɵ�ArrayList
	 * @throws DAOSystemException
	 */
	public ArrayList getAllMmMenus() {
		 return findBySql(SQL_SELECT + " ORDER BY path_code", null);
	}

	
	/**
	 * ��ȡȨ���б��Ӧ�����в˵�����path_code����
	 * ���˵�Ӫ�������ĸ��ڵ�:99999
	 * ���˵�Ӫ����Դ�ĸ��ڵ�:31700
	 * ���˵������̵ĸ��ڵ�:94000
	 * @param privset
	 *            Ȩ���б���ʽΪ"privId1, privId2, ..."
	 * @return ָ��Ȩ�޼��϶�Ӧ�����в˵�(MmMenuVO)���ɵ�ArrayList
	 * @throws DAOSystemException
	 */
	public ArrayList getMmMenusByPrivSet(String privset,String parentMenuId,String staffCode)
			throws DAOSystemException {
		String sql = "SELECT distinct menu.menu_id, menu.system_id, menu.menu_code, menu.menu_name, " +
							" menu.order_id, menu.target_name, menu.parameter, menu.open_flag, menu.privilege_flag, " +
							" menu.valid_flag, menu.menu_type, menu.menu_grade, menu.super_id, menu.image_path, " +
							" menu.comments, menu.path_code " +
							" FROM MM_MENU menu, MM_DATA_PRIVILEGE mr , PRIVILEGE PRIV" +
							" WHERE cast( menu.menu_id as varchar(20) ) =mr.DATA_PKEY_1  AND mr.PRIVILEGE_ID = PRIV.PRIVILEGE_ID" +
							" AND PRIV.PRIVILEGE_TYPE = '99B' AND valid_flag = '0' and menu.menu_id not in(99999,31700,94000) and super_id = " + parentMenuId ;

		StaffDAO staffDAO = StaffDAOFactory.getStaffDAO() ;
		boolean isSuperStaff = false ;
		try{
			isSuperStaff = staffDAO.isSuperStaff(staffCode);
		}catch( Exception e ){
			throw new DAOSystemException( e ) ;
		}
		//superStaffCodeΪϵͳ��������Ա,����ʹ�����в˵�
		//String superStaffCode = CrmParamsConfig.getInstance().getParamValue("SUPER_STAFF_CODE");
		//if( !superStaffCode.equals(staffCode)){
		if( !isSuperStaff){
			sql = sql + " AND mr.privilege_id IN ("	+ privset + ") ";
		}
		sql = sql + " ORDER BY menu.path_code";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql));
			stmt.setMaxRows(maxRows);
			rs = stmt.executeQuery();

			ArrayList resultList = new ArrayList();
			while (rs.next()) {
				MmMenuVO vo = new MmMenuVO();
				populateDto2(vo, rs);
				if( vo.getTargetName() != null ){
					vo.setTargetName( vo.getTargetName().trim());
        		}else{
        			vo.setTargetName("");
        		}
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
	 * ��ȡĳ�ڵ��µ�����ֱ���ӽڵ�
	 * 
	 * @param idParent
	 *            ���ڵ��ʶ������Ϊ��
	 * @return
	 * @throws DAOSystemException
	 */
	public ArrayList getDirectSubNodes(String idParent)
			throws DAOSystemException {
		return findBySql(SQL_SELECT + " WHERE super_id=?",
				new String[] { idParent });
	}

	/**
	 * ����˵�ʵ�壬���ɲ˵���ʶ��path_code
	 */
	public void insert(VO vo) throws DAOSystemException {
		// ��ȡ���ڵ��Եõ��ϲ�path_code
		String parent = ((MmMenuVO) vo).getSuperId();
		if (parent != null && !"-1".equals(parent) && !"".equals(parent)) {//�����ϼ��˵�
			MmMenuVO voParent = findByPrimaryKey(parent);
			if (voParent == null) {
				throw new DAOSystemException("INVALID SUPER_ID [" + parent
						+ "]");
			}
			parent = voParent.getPathCode();
		} else {
			parent = null;
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		try {
//			 ���ɱ�ʶ

			SequenceManageDAO smDAO = SeqDAOFactory.getInstance().getSequenceManageDAO();
			        	
			((MmMenuVO) vo).setMenuId(smDAO.getNextSequence(vo.getTableName(),
							"MENU_ID"));
			
			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_INSERT));
			
			// ����path_code,����Ǹ��ڵ�,��ʹ��MENU_ID��ΪPATH_CODE,������Ǹ��ڵ�,��ʹ���ϼ��˵���PATH_CODE�����Լ���ID��ΪPATH_CODE
			((MmMenuVO) vo).setPathCode(parent == null ? ((MmMenuVO) vo).getMenuCode() : parent + "." + ((MmMenuVO) vo).getMenuCode());
			
			
			int index = 1;
			if ("".equals(((MmMenuVO) vo).getMenuId())) {
				((MmMenuVO) vo).setMenuId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getMenuId());
			if ("".equals(((MmMenuVO) vo).getSystemId())) {
				((MmMenuVO) vo).setSystemId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getSystemId());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuCode());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuName());
			if ("".equals(((MmMenuVO) vo).getOrderId())) {
				((MmMenuVO) vo).setOrderId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getOrderId());
			stmt.setString(index++, ((MmMenuVO) vo).getTargetName());
			stmt.setString(index++, ((MmMenuVO) vo).getPara());
			stmt.setString(index++, ((MmMenuVO) vo).getOpenFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getPrivFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getValidFlag());
			stmt.setString(index++, ((MmMenuVO) vo).getMenuType());
			if ("".equals(((MmMenuVO) vo).getMenuGrade())) {
				((MmMenuVO) vo).setMenuGrade(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getMenuGrade());
			if ("".equals(((MmMenuVO) vo).getSuperId())) {
				((MmMenuVO) vo).setSuperId(null);
			}
			stmt.setString(index++, ((MmMenuVO) vo).getSuperId());
			stmt.setString(index++, ((MmMenuVO) vo).getImagePath());
			stmt.setString(index++, ((MmMenuVO) vo).getComments());
			stmt.setString(index++, ((MmMenuVO) vo).getPathCode());
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
	 * ���²˵�ʵ�壬������ʶ�򸸽ڵ������ع���path_code�����ع�����������ڵ�
	 * ��path_code
	 */
	public boolean update(String pmenu_id, MmMenuVO vo)throws DAOSystemException {
		boolean rebuildSubNodes = true;
		if (pmenu_id.equals(vo.getMenuId())) {
			MmMenuVO voOld = findByPrimaryKey(pmenu_id);//����ǰ���ҵ�Ҫ�����µļ�¼
			
			//������޸ĵ��Ǹ��ڵ�
			if (vo.getSuperId() == null || "-1".equals(vo.getSuperId()) || "".equals(vo.getSuperId()) ) {
				//���ԭ���ļ�¼Ҳ�Ǹ��ڵ�
				if (voOld.getSuperId() == null || "-1".equals(vo.getSuperId()) || "".equals(voOld.getSuperId())) {
					if( vo.getMenuCode().equals(voOld.getMenuCode()) ){
						vo.setPathCode(voOld.getPathCode());//��ԭ����¼�е�Path code ��ֵ���µļ�¼
						rebuildSubNodes = false;
					}
				}
			} else {
				//������Ǹ��ڵ㲢���ϼ��ڵ�û�иı�,����MENU_CODEҲû���޸�,�������޸�PATH_CODE
				if (vo.getSuperId().equals(voOld.getSuperId()) && vo.getMenuCode().equals(voOld.getMenuCode())) {
					vo.setPathCode(voOld.getPathCode());
					rebuildSubNodes = false; 
				}
			}
		}
		if (rebuildSubNodes) {
			String pathCode = null;
			if (vo.getSuperId() == null || "".equals(vo.getSuperId())|| "-1".equals(vo.getSuperId())) {//����޸ĵ��Ǹ��ڵ�˵�
				pathCode = vo.getMenuCode();
			} else {
				MmMenuVO voParent = findByPrimaryKey(vo.getSuperId());
				if (voParent == null) {
					throw new DAOSystemException("INVALID SUPER_ID ["
							+ vo.getSuperId() + "]");
				}
				pathCode = voParent.getPathCode() + "." + vo.getMenuCode();
			}
			vo.setPathCode(pathCode);
			return updateTreeFromNode(pmenu_id, vo) > 0;
		} else {
			return updateNodeSelf(pmenu_id, vo);
		}
	}

	/**
	 * ɾ���˵����������ӽڵ�������ɾ��
	 */
	public long delete(String pmenu_id) throws DAOSystemException {
		if (getDirectSubNodes(pmenu_id).size() > 0) {
			throw new DAOSystemException("UNABLE TO DELETE WITH SUBNODES");
		}

		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(SQL_DELETE));
			int index = 1;
			stmt.setString(index++, pmenu_id);
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
	 * ����ĳ���˵��ڵ�����
	 * 
	 * @param pmenu_id
	 *            �˵���ʶ
	 * @param vo
	 *            ��ֵ����
	 * @return
	 * @throws DAOSystemException
	 */
	private boolean updateNodeSelf(String pmenu_id, MmMenuVO vo)
			throws DAOSystemException {
		Connection conn = null;
		PreparedStatement stmt = null;
		boolean bResult = false;
		StringBuffer sql = new StringBuffer();
		try {

			conn = ConnectionContext.getContext().getConnection(JNDINames.CRM_DATASOURCE) ;
			sql
					.append("UPDATE MM_MENU SET menu_id = ?,system_id = ?,menu_code = ?,menu_name = ?,order_id = ?,target_name = ?,parameter = ?,open_flag = ?,privilege_flag = ?,valid_flag = ?,menu_type = ?,menu_grade = ?,super_id = ?,image_path = ?,comments = ?,path_code = ?");
			sql.append(" WHERE  menu_id = ? ");
			stmt = conn.prepareStatement(DAOSQLUtils.getFilterSQL(sql
					.toString()));
			int index = 1;
			if ("".equals(((MmMenuVO) vo).getMenuId())) {
				((MmMenuVO) vo).setMenuId(null);
			}
			stmt.setString(index++, vo.getMenuId());
			if ("".equals(((MmMenuVO) vo).getSystemId())) {
				((MmMenuVO) vo).setSystemId(null);
			}
			stmt.setString(index++, vo.getSystemId());
			stmt.setString(index++, vo.getMenuCode());
			stmt.setString(index++, vo.getMenuName());
			if ("".equals(((MmMenuVO) vo).getOrderId())) {
				((MmMenuVO) vo).setOrderId(null);
			}
			stmt.setString(index++, vo.getOrderId());
			stmt.setString(index++, vo.getTargetName());
			stmt.setString(index++, vo.getPara());
			stmt.setString(index++, vo.getOpenFlag());
			stmt.setString(index++, vo.getPrivFlag());
			stmt.setString(index++, vo.getValidFlag());
			stmt.setString(index++, vo.getMenuType());
			if ("".equals(((MmMenuVO) vo).getMenuGrade())) {
				((MmMenuVO) vo).setMenuGrade(null);
			}
			stmt.setString(index++, vo.getMenuGrade());
			if ("".equals(((MmMenuVO) vo).getSuperId())) {
				((MmMenuVO) vo).setSuperId(null);
			}
			stmt.setString(index++, vo.getSuperId());
			stmt.setString(index++, vo.getImagePath());
			stmt.setString(index++, vo.getComments());
			stmt.setString(index++, vo.getPathCode());
			stmt.setString(index++, pmenu_id);
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
	 * ��ĳ���ڵ㿪ʼ�����䱾�����������ڵ��path_code
	 * 
	 * @param pmenu_id
	 *            ��ʼ�ڵ��ʶ
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	private int updateTreeFromNode(String pmenu_id, MmMenuVO vo) 	throws DAOSystemException {
		int recsUpdated = 0;
		if (updateNodeSelf(pmenu_id, vo)) {//��������Լ��ɹ�,������¼��ڵ� 
			recsUpdated += 1;
			Iterator iterSubNodes = getDirectSubNodes(pmenu_id).iterator();//��ȡ��ǰ�ڵ�������ӽڵ�
			
			MmMenuVO voNode;
			
			int recsUpdSub;
			
			while (iterSubNodes.hasNext()) {
				voNode = (MmMenuVO) iterSubNodes.next();
				
				voNode.setSuperId(vo.getMenuId());//�޸Ĳ˵����ϼ�
				
				voNode.setPathCode(vo.getPathCode() + "." + voNode.getMenuCode());//�޸Ĳ˵���PATH_CODE
				
				recsUpdSub = updateTreeFromNode(voNode.getMenuId(), voNode);//�ݹ����,�޸�����һ���Ĳ˵�
				
				recsUpdated = recsUpdated + recsUpdSub;
				
				if (recsUpdSub < 1) {
					throw new DAOSystemException("UPDATE FAIL WITH SUBNODE["
							+ voNode.getMenuId() + "]");
				}
			}
		}

		return recsUpdated;
	}

}
