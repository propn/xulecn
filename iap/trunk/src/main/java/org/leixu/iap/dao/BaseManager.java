package org.leixu.iap.dao;

import java.util.HashMap;
import java.util.List;

/**
 * TODO 业务逻辑基类接口
 */
public interface BaseManager<E> {
	/**
	 * 根据主键获得实体
	 * 
	 * @param id
	 *            主键id
	 * @return BaseEntity
	 */
	E get(Long id);

	/**
	 * 获得所有实体
	 * 
	 * @return List
	 */
	List<E> getAll();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            pojo instance
	 */
	void save(E entity);

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 *            实体主键
	 */
	void remove(Long id);

	/**
	 * 分页搜索
	 * 
	 * @param con
	 * @param page
	 * @param rowsPerPage
	 * @return
	 */
	public List<E> search(HashMap con, int page, int rowsPerPage);

	/**
	 * 搜索
	 * 
	 * @param con
	 * @return
	 */
	public List<E> search(HashMap con);
}
