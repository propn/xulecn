/**
 *
 */
package org.leixu.iap.dao;

import java.util.HashMap;
import java.util.List;

public interface BaseDAO<E> {

	/**
	 * �����������ʵ��
	 * 
	 * @param id
	 *            ʵ������
	 * @return BaseEntity
	 */
	E getEntity(Long id);

	/**
	 * �������ʵ��
	 * 
	 * @return List
	 */
	List<E> getAllEntity();

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 *            pojo instance
	 */
	void saveEntity(E entity);

	/**
	 * ��������ɾ��ʵ��
	 * 
	 * @param id
	 *            ʵ������
	 */
	void removeEntity(Long id);

	public List<E> search(HashMap con, int page, int rowsPerPage);

	public List<E> search(HashMap con);
}
