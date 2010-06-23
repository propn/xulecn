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
 * 业务逻辑基类
 * 
 * Revision History
 * 
 * 
 */
public class BaseManagerImpl<E, D extends BaseDAO<E>> implements BaseManager<E> {
	protected final Log log = LogFactory.getLog(getClass().getName());

	/**
	 * 数据访问接口
	 */
	protected D dao;

	/**
	 * @return List 所有实体
	 */
	public final List<E> getAll() {
		return dao.getAllEntity();
	}

	/**
	 * @param id
	 *            实体主键
	 * @return 实体
	 */
	public final E get(final Long id) {

		return dao.getEntity(id);
	}

	/**
	 * @param id
	 *            实体主键
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
	 *            实体
	 */
	public final void save(final E entity) {
		dao.saveEntity(entity);
	}

	/**
	 * 
	 * @return 获取泛形
	 */
	public final D getDao() {
		return dao;
	}

	/**
	 * 
	 * @param dao
	 *            泛形
	 */
	public final void setDao(final D dao) {
		this.dao = dao;
	}

}
