package org.leixu.iap.dao;

import java.util.HashMap;
import java.util.List;

/**
 * TODO ҵ���߼�����ӿ�
 */
public interface BaseManager<E> {
	/**
	 * �����������ʵ��
	 * 
	 * @param id
	 *            ����id
	 * @return BaseEntity
	 */
	E get(Long id);

	/**
	 * �������ʵ��
	 * 
	 * @return List
	 */
	List<E> getAll();

	/**
	 * ����ʵ��
	 * 
	 * @param entity
	 *            pojo instance
	 */
	void save(E entity);

	/**
	 * ��������ɾ��ʵ��
	 * 
	 * @param id
	 *            ʵ������
	 */
	void remove(Long id);

	/**
	 * ��ҳ����
	 * 
	 * @param con
	 * @param page
	 * @param rowsPerPage
	 * @return
	 */
	public List<E> search(HashMap con, int page, int rowsPerPage);

	/**
	 * ����
	 * 
	 * @param con
	 * @return
	 */
	public List<E> search(HashMap con);
}
