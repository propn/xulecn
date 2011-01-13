package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
/**
 * 
 * @Classname     : QueryFilter
 * @Description   : ��ѯ���ع�����QueryFilter
 * @Copyright ? 2006 ZTEsoft.
 * @Author        : fjy
 * @Create Date   : 2006-3-22
 *
 * @Last Modified : 
 * @Modified by   : 
 * @Version       : 1.0
 */
public interface QueryFilter {
	
	/**
	 * Ԥ���ˣ���sql�й�������,ͳһ���зֿ⴦��
	 */
	public String doPreFilter(String where);
	
	/**
	 * Ԥ���ˣ���sql�й�������,�����зֿ⴦��
	 */
	public String doPreFilterWithoutFilterSQL(String sql);

	/**
	 * ����ˣ��ڽ�����й������ݡ�
	 * 
	 * @param resultSet
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	public ArrayList doPostFilter(ResultSet resultSet, DAO dao)throws SQLException;
	
	
	/**
	 * ����ˣ��ڽ�����й������ݡ�
	 * 
	 * @param resultSet
	 * @param dao
	 * @return
	 * @throws SQLException
	 */
	public ArrayList doPostFilter(ResultSet resultSet)throws SQLException;
	
}
