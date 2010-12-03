package com.ztesoft.crm.salesres.dao;

import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;

public interface SqlComDAO {

	/**
	 * 可以插入、更新、删除sql语句
	 * 
	 * @param sql
	 *            String
	 * @throws DAOSystemException
	 * @return int
	 */
	public int excute(String sql) throws DAOSystemException;
	

	public int excute(String sql,String []params) throws DAOSystemException;

	public long count(String sql) throws DAOSystemException;
	public boolean checkExist(String sql);
	public boolean checkExist(String sql,String []params);
	/**
	 * 查询传入的sql语句，根据传入的字段名封装查询的结果到map中，返回一个list集合
	 * 
	 * @param sql
	 *            String
	 * @param arrs
	 *            String[]：要查询的字段名数组
	 * @throws DAOSystemException
	 * @return List
	 */
	public List qryComSql(String sql, String[] arrs) throws DAOSystemException;

	/**
	 * 批量执行语句
	 * 
	 * @param sqlArr
	 *            String[]
	 * @return long
	 */
	public long batchExecute(String[] sqlArr) throws DAOSystemException;

	/**
	 * 把sql语句加入statement,以后可以批量执行这些语句
	 * 
	 * @param sql
	 *            String
	 */
	public void addBatchSql(String sql) throws DAOSystemException;

	public void closeConnection();

	public boolean checkStorageRelation(String storageId, String operId,
			String departId);

	public int batchExecuteExtends(List params, String sql);
	

	public List executeQueryForMapList(String sql,String []params) throws Exception ;

}
