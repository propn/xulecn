package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.ztesoft.common.util.CrmParamsConfig;
import com.ztesoft.common.util.PageModel;
import com.ztesoft.common.valueobject.VO;

public class PageFilterOracleImpl implements QueryFilter{
	
	private PageModel pageModel ; //��ǰ��ҳ��Ϣģ��
	
	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterOracleImpl() {
		this.pageModel = new PageModel();	
	}
	
	/**
	 * 
	 * PageFilterInformixImpl.java������
	 */
	public PageFilterOracleImpl(PageModel pageModel) {
		
		this.pageModel = pageModel;
		
	}
	
	public String doPreFilterWithoutFilterSQL(String sql) {
		int startIndex = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
		int endIndex = startIndex + this.pageModel.getPageSize() ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql +
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;
		return sql;
	}

	/**  
	 * Ԥ���ˣ���Ҫ��Ϊoracleһ������ݿ�ʹ��rownum����װsql��ҳԤ����
	 */
	public String doPreFilter(String sql) {
		//crmdb,proddb,custdb,acctdb...
		String[] dbusers = CrmParamsConfig.getInstance().getParamValue("SYSTEM_DATABASE_USER").split(",");
		boolean needFilter = true ;
		for( int i = 0 ; i < dbusers.length ; i ++ ){
			if( sql.indexOf(dbusers[i]+".") != -1 ){
				needFilter = false ;
				break ;
			}
		}
		if( needFilter ){
			sql = DAOSQLUtils.getFilterSQL(sql) ;
		}
		int startIndex = (this.pageModel.getPageIndex() - 1) * this.pageModel.getPageSize()+1;
		int endIndex = startIndex + this.pageModel.getPageSize() ;
		sql = "select * from (select my_table.*,rownum as my_rownum from( "+
		sql + 
				" ) my_table where rownum< " + endIndex + 
				") where my_rownum>= " + startIndex ;

		//sql = "PAGEMODEL" + sql ;
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
			/*if (!resultSet.next())
				return list;
			 
			int count = 0;
			while (resultSet.next()){
				VO vo = dao.populateCurrRecord(resultSet);
				list.add(vo);
				count++;
			}*/
				while( resultSet.next() ){
					VO vo = dao.populateCurrRecord(resultSet);
					list.add(vo);
				}
		} catch (SQLException e) {
			throw e;
		}
		//���������б�
		this.pageModel.setList(list);
		
		return list;
	}
	
	
/*	public ArrayList doPostFilter(ResultSet resultSet ) throws SQLException {
		
		

		
		ArrayList list = new ArrayList();

			
		if (this.pageModel.getPageSize() == -1)
			return list;
	

		//���������б�
		this.pageModel.setList(SQLUtils.getInst().convert(resultSet));
		
		return list;
	}*/
	

	public PageModel getPageModel() {
		return pageModel;
	}

	public void setPageModel(PageModel pageModel) {
		this.pageModel = pageModel;
	}

	public ArrayList doPostFilter(ResultSet resultSet) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
