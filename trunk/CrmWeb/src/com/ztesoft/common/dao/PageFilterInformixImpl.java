package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.jfree.util.Log;

import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;

/**
 * informix���ݿ��ҳ����
 * @author xulei
 * 
 *
 */
public class PageFilterInformixImpl implements QueryFilter {
	private static final Logger logger = Logger.getLogger(PageFilterInformixImpl.class);
	private PageModel pageModel; // ��ǰ��ҳ��Ϣģ��

	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterInformixImpl() {
		this.pageModel = new PageModel();
	}

	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterInformixImpl(PageModel pageModel) {

		this.pageModel = pageModel;

	}

	public String doPreFilterWithoutFilterSQL(String sql) {
		int startIndex = (this.pageModel.getPageIndex() - 1)
				* this.pageModel.getPageSize() + 1;
		int endIndex = startIndex + this.pageModel.getPageSize();
		sql = "select skip " + startIndex + " first " + String.valueOf(endIndex-startIndex) + " * from ( " + sql
				+ " ) ";
		
		return sql;
	}

	/**
	 * Ԥ���ˣ���Ҫ��ΪINformixһ������ݿ�ʹ��skip first����װsql��ҳԤ����
	 */
	public String doPreFilter(String sql) {
		String[] dbusers = CrmParamsConfig.getInstance().getParamValue(
				"SYSTEM_DATABASE_USER").split(",");
		boolean needFilter = true;
		for (int i = 0; i < dbusers.length; i++) {
			if (sql.indexOf(dbusers[i] + ".") != -1) {
				needFilter = false;
				break;
			}
		}
		if (needFilter) {
			sql = DAOSQLUtils.getFilterSQL(sql);
		}
		int startIndex = (this.pageModel.getPageIndex() - 1)
				* this.pageModel.getPageSize();
		int endIndex = startIndex + this.pageModel.getPageSize();

		sql = "select skip " + startIndex + " first " + String.valueOf(endIndex-startIndex) + " * from( " + sql
				+ " ) ";
		
		logger.debug(sql);
		return sql;
	}

	/**
	 * ����ˣ��ڽ�����У����˳���ǰ��ҳ���õ�ҳ�����ݡ�
	 * 
	 * @param resultSet
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	public ArrayList doPostFilter(ResultSet resultSet, DAO dao)
			throws SQLException {
		ArrayList list = new ArrayList();
		try {

			if (this.pageModel.getPageSize() == -1)
				return list;
			while (resultSet.next()) {
				VO vo = dao.populateCurrRecord(resultSet);
				list.add(vo);
			}
		} catch (SQLException e) {
			throw e;
		}
		// ���������б�
		this.pageModel.setList(list);

		return list;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public ArrayList doPostFilter(ResultSet resultSet) throws SQLException {
		return null;
	}

}
