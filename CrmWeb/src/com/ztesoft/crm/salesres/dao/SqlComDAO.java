package com.ztesoft.crm.salesres.dao;

import java.util.List;

import com.ztesoft.common.dao.DAOSystemException;

public interface SqlComDAO {

	/**
	 * ���Բ��롢���¡�ɾ��sql���
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
	 * ��ѯ�����sql��䣬���ݴ�����ֶ�����װ��ѯ�Ľ����map�У�����һ��list����
	 * 
	 * @param sql
	 *            String
	 * @param arrs
	 *            String[]��Ҫ��ѯ���ֶ�������
	 * @throws DAOSystemException
	 * @return List
	 */
	public List qryComSql(String sql, String[] arrs) throws DAOSystemException;

	/**
	 * ����ִ�����
	 * 
	 * @param sqlArr
	 *            String[]
	 * @return long
	 */
	public long batchExecute(String[] sqlArr) throws DAOSystemException;

	/**
	 * ��sql������statement,�Ժ��������ִ����Щ���
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
