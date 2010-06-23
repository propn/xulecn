/**
 *
 */
package org.leixu.iap.dao;

import java.util.HashMap;
import java.util.List;

public interface BaseDAO<E> {

	/**
	 * 根据主键获得实体
	 * 
	 * @param id
	 *            实体主键
	 * @return BaseEntity
	 */
	E getEntity(Long id);

	/**
	 * 获得所有实体
	 * 
	 * @return List
	 */
	List<E> getAllEntity();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            pojo instance
	 */
	void saveEntity(E entity);

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 *            实体主键
	 */
	void removeEntity(Long id);

	public List<E> search(HashMap con, int page, int rowsPerPage);

	public List<E> search(HashMap con);
}
