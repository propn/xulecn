package com.ztesoft.common.dao;

import java.sql.ResultSet;
import java.util.List;

import com.ztesoft.common.valueobject.VO;

/**
 * 
 * @Classname : DAO
 * @Description : ���ݴ�ȡ����ӿ�
 * @Copyright 2006 ZTEsoft.
 * @Author :  fjy
 * @Create Date : 2006-3-22
 *
 * @Last Modified :
 * @Modified by :
 * @Version : 1.0
 */
public interface DAO {
	
	/**
	 * ��������
	 * @param vo
	 * @throws DAOSystemException
	 */
	public void insert(VO vo) throws DAOSystemException;

	/**
	 * �޸�����
	 * @param cond
	 * @param vo
	 * @return
	 * @throws DAOSystemException
	 */
	public boolean update(String cond, VO vo) throws DAOSystemException;

	/**
	 * ɾ������
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public long deleteByCond(String whereCond) throws DAOSystemException;

	/**
	 * �������ݡ����û�����ݷ��ؿ��б�[]�� ���ܷ���NULL;
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public List findByCond(String whereCond) throws DAOSystemException;
	
	/**
	 * 
	 * @param whereCond
	 * @return
	 * @throws DAOSystemException
	 */
	public long countByCond(String whereCond) throws DAOSystemException;

	/**
	 * ��ȡһ���յ�ֵ����
	 * @return
	 */
	public VO getEmptyVO();
	
	
	/**
	 * ͨ����ѯ�����͹������õ��������ݡ�
	 * @param where
	 * @param queryFilter
	 * @return
	 * @throws DAOSystemException
	 */
	public List findByCond(String where, QueryFilter queryFilter) throws DAOSystemException;
	
	/**
	 * ȡ�ý�����ĵ�ǰ��¼��
	 * @param rs
	 * @return
	 * @throws DAOSystemException
	 */
	public VO populateCurrRecord(ResultSet rs) throws DAOSystemException;
}
