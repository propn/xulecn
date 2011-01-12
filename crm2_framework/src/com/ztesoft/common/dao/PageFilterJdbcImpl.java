package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;

/**
 * 
 * @Classname : PageFilterInformixImpl
 * @Description : ��ѯ������QueryFilter informix�µ�ʵ�֡�
 * @Copyright ? 2006 ZTEsoft.
 * @Author : Mr. fjy
 * @Create Date : 2006-3-28
 * 
 * @Last Modified :
 * @Modified by :5
 * @Version : 1.0
 */
public class PageFilterJdbcImpl implements QueryFilter {
	
	private PageModel pageModel ; //��ǰ��ҳ��Ϣģ��
	
	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterJdbcImpl() {
				
	}
	
	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterJdbcImpl(PageModel pageModel) {
		
		this.pageModel = pageModel;
		
	}

	/**
	 * Ԥ���ˣ���Ҫ��Ϊoracleһ������ݿ�ʹ��rownum����װsql��ҳԤ����
	 */
	public String doPreFilter(String sql) {
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
	public ArrayList doPostFilter(ResultSet resultSet, DAO dao) throws SQLException {
		ArrayList list = new ArrayList();
		try {
			if (this.pageModel.getPageSize() == -1)
				return list;
			if (!resultSet.next())
				return list;
			
			 int currentSize = 0;
	         if (this.pageModel.getPageIndex() >= this.pageModel.getPageCount()) {
	             int startIndex = this.pageModel.getPageCount();
	             currentSize = this.pageModel.getTotalCount() - (startIndex - 1) * this.pageModel.getPageSize();
	         }
	         else {
	             currentSize = this.pageModel.getPageSize();
	         }
	         
			int locationInt = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
			resultSet.absolute(locationInt);
			int count = 0;
			do {
				VO vo = dao.populateCurrRecord(resultSet);
				list.add(vo);
				count++;
			}while (resultSet.next() && count < currentSize);

		} catch (SQLException e) {
			throw e;
		}
		//���������б�
		this.pageModel.setList(list);
		
		return list;
	}

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	/* (non-Javadoc)
	 * @see com.ztesoft.common.dao.QueryFilter#doPreFilterWithoutFilterSQL(java.lang.String)
	 */
	public String doPreFilterWithoutFilterSQL(String sql) {
		return sql;
	}
	
	public ArrayList doPostFilter(ResultSet resultSet ) throws SQLException {
		return new ArrayList();
		}

}
