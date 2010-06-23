/**
 * BaseManagerImpl.java
 *
 * Copyright 2007 easou, Inc. All Rights Reserved.
 */
package org.leixu.iap.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * ҵ���߼�����
 * 
 * Revision History
 * 
 * 
 */
public class BaseManagerImpl<E, D extends BaseDAO<E>> implements BaseManager<E> {
	protected final Log log = LogFactory.getLog(getClass().getName());

	/**
	 * ���ݷ��ʽӿ�
	 */
	protected D dao;

	/**
	 * @return List ����ʵ��
	 */
	public final List<E> getAll() {
		return dao.getAllEntity();
	}

	/**
	 * @param id
	 *            ʵ������
	 * @return ʵ��
	 */
	public final E get(final Long id) {

		return dao.getEntity(id);
	}

	/**
	 * @param id
	 *            ʵ������
	 */
	public final void remove(final Long id) {
		dao.removeEntity(id);
	}

	public List<E> search(HashMap con, int page, int rowsPerPage) {
		return ((D) dao).search(con, page, rowsPerPage);
	}

	public List<E> search(HashMap con) {
		return ((D) dao).search(con);
	}

	/**
	 * @param entity
	 *            ʵ��
	 */
	public final void save(final E entity) {
		dao.saveEntity(entity);
	}

	/**
	 * 
	 * @return ��ȡ����
	 */
	public final D getDao() {
		return dao;
	}

	/**
	 * 
	 * @param dao
	 *            ����
	 */
	public final void setDao(final D dao) {
		this.dao = dao;
	}

}
