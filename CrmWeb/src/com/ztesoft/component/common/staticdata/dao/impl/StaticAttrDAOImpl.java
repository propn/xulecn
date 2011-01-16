package com.ztesoft.component.common.staticdata.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.powerise.ibss.framework.DynamicDict;
import com.ztesoft.common.dao.ConnectionContext;
import com.ztesoft.common.dao.DAOSQLUtils;
import com.ztesoft.common.dao.DAOSystemException;
import com.ztesoft.common.dao.DAOUtils;
import com.ztesoft.common.util.JNDINames;
import com.ztesoft.component.common.staticdata.dao.StaticAttrDAO;
import com.ztesoft.component.common.staticdata.vo.StaticAttrListVO;
import com.ztesoft.component.common.staticdata.vo.StaticAttrVO;

public class StaticAttrDAOImpl implements StaticAttrDAO {
	/**
	 * Logger for this class
	 */
	private DynamicDict dto = null;

	public DynamicDict getDto() {
		// TODO Auto-generated method stub
		return this.dto;
	}

	public void setDto(DynamicDict dto) {
		// TODO Auto-generated method stub
		this.dto = dto;
	}

	private static final Logger logger = Logger
			.getLogger(StaticAttrDAOImpl.class);

	private String SQL_SELECT_PARENT = " SELECT attrValue.attr_id, attrValue.attr_value, attrValue.attr_value_id, attr_value_desc, attr.attr_code, rela.a_attr_value_id FROM ATTRIBUTE attr,ATTRIBUTE_VALUE attrValue,ATTRIBUTE_VALUE_RELA rela "
			+ " where attr.attr_code = ? and attrValue.attr_id = attr.attr_id and "
			+ " rela.z_attr_id = attr.attr_id and rela.z_attr_value_id = attrValue.attr_value_id  "
			+ " order by attrValue.sortby , attrValue.attr_value_id ";

	private String SQL_SELECT_SINGLE = " SELECT attrValue.attr_id, attrValue.attr_value, attrValue.attr_value_id, attr_value_desc, attr.attr_code FROM ATTRIBUTE attr,ATTRIBUTE_VALUE attrValue "
			+ " where attr.attr_code = ? and attrValue.attr_id = attr.attr_id  "
			+ " order by attrValue.sortby, attrValue.attr_value_id ";

	private String ALL_SQL_SELECT_PARENT = "SELECT  distinct attrValue.attr_id,   attrValue.attr_value,   attrValue.attr_value_id,   attr_value_desc,   attr.attr_code,   rela.a_attr_value_id, attrValue.sortby  from  "
			+ "attribute  attr, attribute_value  attrValue, attribute_value_rela  rela    where  attrValue.attr_id  =  attr.attr_id  and    rela.z_attr_id  =  attr.attr_id  and  rela.z_attr_value_id  =  attrValue.attr_value_id  order  by attrValue.attr_id, attrValue.sortby, attrValue.attr_value_id";

	private String ALL_SQL_SELECT_SINGLE = "SELECT distinct attrValue.attr_id,   attrValue.attr_value,   attrValue.attr_value_id,   attrValue.attr_value_desc,   attr.attr_code ,attrValue.sortby from "
			+ " attribute  attr, attribute_value  attrValue    where   attrValue.attr_id  =  attr.attr_id   order  by  attrValue.attr_id ,attrValue.sortby, attrValue.attr_value_id asc ";

	public StaticAttrDAOImpl() {

	}

	public ArrayList findByCode(String attrCode) throws DAOSystemException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList resultList = new ArrayList();

		try {

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			// load from SQL_SELECT_PARENT
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(SQL_SELECT_PARENT));
			stmt.setString(1, attrCode);
			rs = stmt.executeQuery();

			boolean hasParent = false;

			if (rs.next()) {
				hasParent = true;
				do {
					StaticAttrVO vo = new StaticAttrVO();
					populateDto(vo, rs, hasParent);
					resultList.add(vo);
				} while (rs.next());

			} else {
				rs.close();
				stmt.close();
				// load from SQL_SELECT_SINGLE
				stmt = conn.prepareStatement(DAOSQLUtils
						.getFilterSQL(SQL_SELECT_SINGLE));
				stmt.setString(1, attrCode);
				rs = stmt.executeQuery();

				while (rs.next()) {
					StaticAttrVO vo = new StaticAttrVO();
					populateDto(vo, rs, hasParent);
					resultList.add(vo);
				}
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ this.SQL_SELECT_PARENT, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			// DAOUtils.closeConnection(conn, this);
		}

		return resultList;
	}

	public ArrayList findSingleByCode(String attrCode)
			throws DAOSystemException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList resultList = new ArrayList();

		try {

			// conn = DAOUtils.getDBConnection(JNDINames.CRM_DATASOURCE, this);

			conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);
			// load from SQL_SELECT_SINGLE
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(SQL_SELECT_SINGLE));
			stmt.setString(1, attrCode);
			rs = stmt.executeQuery();
			while (rs.next()) {
				StaticAttrVO vo = new StaticAttrVO();
				populateDto(vo, rs, false);
				resultList.add(vo);
			}

		} catch (SQLException se) {
			throw new DAOSystemException("SQLException while getting sql:\n"
					+ this.SQL_SELECT_PARENT, se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			// DAOUtils.closeConnection(conn, this);
		}

		return resultList;
	}

	protected void populateDto(StaticAttrVO vo, ResultSet rs, boolean hasParent)
			throws SQLException {

		vo.setAttrValue(DAOUtils.trimStr(rs.getString("attr_value")));
		vo.setAttrId(rs.getString("attr_id"));
		vo.setAttrCode(DAOUtils.trimStr(rs.getString("attr_code")));
		vo.setAttrValueDesc(DAOUtils.trimStr(rs.getString("attr_value_desc")));
		vo.setAttrValueId(rs.getString("attr_value_id"));

		if (hasParent)
			vo.setParentValueId(rs.getString("a_attr_value_id"));
		else
			vo.setParentValueId("");

	}

	public ArrayList findAllStaticAttr() throws DAOSystemException {

		ArrayList dataList = new ArrayList();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList resultList = new ArrayList();
		String logSQL = null;
		try {
			conn = ConnectionContext.getContext().getConnection(
					JNDINames.CRM_DATASOURCE);// load from ALL_SQL_SELECT_SINGLE
			logSQL = DAOSQLUtils.getFilterSQL(ALL_SQL_SELECT_SINGLE);
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(ALL_SQL_SELECT_SINGLE));
			rs = stmt.executeQuery();

			boolean hasParent = false;
			String lastCode = "##_##";
			String attrCode = "";
			StaticAttrVO vo = null;

			hasParent = false;
			while (rs.next()) {
				vo = new StaticAttrVO();
				populateDto(vo, rs, hasParent);
				attrCode = vo.getAttrCode();

				if (lastCode.equals("##_##")) {
					lastCode = attrCode;
					resultList.add(vo);
				} else if (lastCode.equals(attrCode)) {
					resultList.add(vo);
				} else {
					StaticAttrListVO staticAttrListVO = new StaticAttrListVO();
					staticAttrListVO.setAttrCode(lastCode);
					staticAttrListVO.setDataList(resultList);
					dataList.add(staticAttrListVO);

					logger.debug("[ attrCode = " + lastCode + " ][ size = "
							+ resultList.size() + " ]");

					lastCode = attrCode;
					resultList = new ArrayList();
					resultList.add(vo);
				}

			}
			// last attrCode
			if (resultList.size() != 0) {
				StaticAttrListVO staticAttrListVO = new StaticAttrListVO();
				staticAttrListVO.setAttrCode(lastCode);
				staticAttrListVO.setDataList(resultList);
				dataList.add(staticAttrListVO);

				lastCode = attrCode;
				resultList = new ArrayList();

				logger.debug("[ attrCode = " + lastCode + " ][ size = "
						+ resultList.size() + " ]");
			}
			rs.close();
			stmt.close();

			// load from ALL_SQL_SELECT_PARENT
			logSQL = DAOSQLUtils.getFilterSQL(ALL_SQL_SELECT_PARENT);
			stmt = conn.prepareStatement(DAOSQLUtils
					.getFilterSQL(ALL_SQL_SELECT_PARENT));
			rs = stmt.executeQuery();
			hasParent = true;
			lastCode = "##_##";
			attrCode = "";
			resultList = new ArrayList();
			vo = null;
			int count = 0;

			while (rs.next()) {
				count++;
				vo = new StaticAttrVO();
				populateDto(vo, rs, hasParent);
				attrCode = vo.getAttrCode();

				if (lastCode.equals("##_##")) {
					lastCode = attrCode;
					resultList.add(vo);
				} else if (lastCode.equals(attrCode)) {
					resultList.add(vo);
				} else {
					StaticAttrListVO staticAttrListVO = new StaticAttrListVO();
					staticAttrListVO.setAttrCode(lastCode);
					staticAttrListVO.setDataList(resultList);
					dataList.add(staticAttrListVO);

					logger.debug("[ attrCode = " + lastCode + " ][ size = "
							+ resultList.size() + " ]");

					lastCode = attrCode;
					resultList = new ArrayList();
					resultList.add(vo);
				}
			}
			// last attrCode
			if (resultList.size() != 0) {
				StaticAttrListVO staticAttrListVO = new StaticAttrListVO();
				staticAttrListVO.setAttrCode(lastCode);
				staticAttrListVO.setDataList(resultList);
				dataList.add(staticAttrListVO);

				logger.debug("[ attrCode = " + lastCode + " ][ size = "
						+ resultList.size() + " ]");
			}

			rs.close();
			stmt.close();

		} catch (SQLException se) {
			System.out.println("LOGSQL================" + logSQL);
			se.printStackTrace();
			throw new DAOSystemException(
					"SQLException while findAllStaticAttr():\n", se);
		} finally {
			DAOUtils.closeResultSet(rs, this);
			DAOUtils.closeStatement(stmt, this);
			// DAOUtils.closeConnection(conn, this);
		}

		return dataList;

	}

}
