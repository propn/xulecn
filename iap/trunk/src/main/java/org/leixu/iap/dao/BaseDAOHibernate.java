/**
 *
 */
package org.leixu.iap.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public abstract class BaseDAOHibernate<E> extends HibernateDaoSupport implements
		BaseDAO<E> {
	protected final Log log = LogFactory.getLog(this.getClass().getName());
	protected Class<E> clazz;

	@SuppressWarnings("unchecked")
	public BaseDAOHibernate() {
		this.clazz = (Class<E>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 根据主键获得实体
	 * 
	 * @param id
	 *            实体主键
	 * @return 实体对象
	 */
	@SuppressWarnings("unchecked")
	public final E getEntity(final Long id) {
		return (E) getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 获得所有实体
	 * 
	 * @return List
	 */
	// @SuppressWarnings("unchecked")
	// public final List<E> getAllEntity() {
	// PageList result = new PageList();
	// List l = getHibernateTemplate().loadAll(clazz);
	// result.addAll(l);
	// result.setCurrentPage(1);
	// result.setPageCount(1);
	// result.setTotalRowCount(l.size());
	// return result;
	// }

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            实体对象
	 */
	public final void saveEntity(final E entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 *            实体主键
	 */
	public final void removeEntity(final Long id) {
		Object o = getEntity(id);
		if (null != o) {
			getHibernateTemplate().delete(o);
		}
	}

	/**
	 * 执行批量更新和删除操作的HQL
	 * 
	 * @param sql
	 *            hql语句
	 * @return PageList
	 */
	@SuppressWarnings("unchecked")
	protected final List<E> executeHQL(final String sql) {
		Session session = null;
		List<E> ret = null;
		try {
			log.info(sql);
			session = this.getSession();
			if (sql.toUpperCase().startsWith("DELETE")
					|| sql.toUpperCase().startsWith("UPDATE")) {
				session.createQuery(sql).executeUpdate();
			} else {
				ret = session.createQuery(sql).list();
			}
		} catch (HibernateException e) {
			log.error("executeHQL() error:" + sql, e);
			throw convertHibernateAccessException(e);
		} finally {
			this.releaseSession(session);
		}
		return ret;
	}

	/**
	 * 执行分页查询
	 * 
	 * @param selectField
	 *            HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
	 * @param countField
	 *            HQL语句中，count 的内容
	 * @param sql
	 *            HQL语句
	 * @param page
	 *            第几页
	 * @param rowsPerPage
	 *            每页记录数
	 * @return PageList
	 */
	// @SuppressWarnings("unchecked")
	// protected final List<E> pageListQuery(final String selectField,
	// String countField, String sql, int page, int rowsPerPage) {
	// PageList result = new PageList();
	// Session session = null;
	// try {
	// session = this.getSession();
	// // 预留count的sql语句
	// String countSql = sql.substring(sql.toUpperCase().indexOf("FROM"));
	// // 设置返回的列，进行查询
	// if (null != selectField) {
	// sql = "SELECT " + selectField + sql;
	// }
	// if (page <= 0) {
	// page = 1; // page最小为1
	// }
	// log.debug("query sql:" + sql);
	// Query q = session.createQuery(sql);
	// if (rowsPerPage > 0) { // rowsPerPage的值是0或-1时,都返回全部结果集
	// q.setFirstResult(rowsPerPage * (page - 1));
	// q.setMaxResults(rowsPerPage);
	// }
	//
	// result.addAll(q.list());
	//
	// // 设置分页查询的列
	// if (null == countField) {
	// countField = "*";
	// }
	//
	// int rowsCount = result.size();
	// if (rowsPerPage > 1 && rowsCount > 0) {
	// // 每页记录数大于1且结果集大于0才计算分页信息,否则当前页记录数就作为总的记录数
	// // TODO 解决page值过大,可能导致rowsCount为0的问题
	// countSql = "select count(" + countField + ") " + countSql;
	// // 计算总记录数时,消除 Order by语句，提高性能
	// int oPos = countSql.toUpperCase().indexOf("ORDER BY");
	// if (oPos > 0) {
	// countSql = countSql.substring(0, oPos);
	// }
	// rowsCount = ((Integer)
	// session.createQuery(countSql).iterate().next()).intValue();
	// }
	//
	// if (0 == rowsCount) {
	// page = 0;
	// }
	// if (rowsPerPage < 0) {
	// page = 1;
	// rowsPerPage = rowsCount;
	// }
	//
	// result.setCurrentPage(page);
	// result.setTotalRowCount(rowsCount);
	// result.calcPageCount(rowsPerPage);
	//
	// } catch (HibernateException e) {
	// log.error("pageListQuery() error:" + sql, e);
	// throw convertHibernateAccessException(e);
	// } finally {
	// this.releaseSession(session);
	// }
	// return result;
	// }

	/**
	 * 执行分页查询
	 * 
	 * @param selectField
	 *            HQL语句中，SELECT 的内容(如果设置了此参数值,则sql参数中不可带SELCT语句部分)
	 * @param sql
	 *            HQL语句
	 * @param page
	 *            第几页
	 * @param rowsPerPage
	 *            每页记录数
	 * @param totalRowCount
	 *            HQL语句获得的总记录数
	 * @return PageList
	 */
	// @SuppressWarnings("unchecked")
	// protected final PageList pageListQuery(final String selectField,
	// String sql, int page, int rowsPerPage, final int totalRowCount) {
	// PageList result = new PageList();
	// Session session = null;
	// try {
	// session = this.getSession();
	// // 设置返回的列，进行查询
	// if (null != selectField) {
	// sql = "SELECT " + selectField + sql;
	// }
	// if (page <= 0) {
	// page = 1; // page最小为1
	// }
	//
	// Query q = session.createQuery(sql);
	// if (rowsPerPage > 0) { // rowsPerPage的值是0或-1时,都返回全部结果集
	// q.setFirstResult(rowsPerPage * (page - 1));
	// q.setMaxResults(rowsPerPage);
	// }
	//
	// result.addAll(q.list());
	//
	// if (0 == totalRowCount) {
	// page = 0;
	// }
	// if (rowsPerPage < 0) {
	// page = 1;
	// rowsPerPage = totalRowCount;
	// }
	//
	// result.setCurrentPage(page);
	// result.setTotalRowCount(totalRowCount);
	// result.calcPageCount(rowsPerPage);
	//
	// } catch (HibernateException e) {
	// log.error("pageListQuery() error:" + sql, e);
	// throw convertHibernateAccessException(e);
	// } finally {
	// this.releaseSession(session);
	// }
	// return result;
	// }

	// /**
	// * 执行分页查询
	// *
	// * @param sql HQL语句
	// * @param page 第几页
	// * @param rowsPerPage 每页记录数
	// * @param totalRowCount HQL语句获得的总记录数
	// * @return PageList
	// */
	// protected final PageList pageListQuery(final String sql, final int page,
	// final int rowsPerPage, final int totalRowCount) {
	// return pageListQuery(null, sql, page, rowsPerPage, totalRowCount);
	// }

	/**
	 * 执行分页查询
	 * 
	 * @param sql
	 *            HQL语句
	 * @param rowsPerPage
	 *            每页记录数
	 * @param page
	 *            第几页
	 * @return PageList
	 * @throws HibernateException
	 *             hibernate 异常
	 */
	// protected List<E> pageListQuery(final String sql, final int page,
	// final int rowsPerPage) throws HibernateException {
	// return pageListQuery(null, null, sql, page, rowsPerPage);
	// }

	/**
	 * 执行分页查询
	 * 
	 * @param countField
	 *            HQL语句中，count 的内容
	 * @param sql
	 *            HQL语句
	 * @param rowsPerPage
	 *            每页记录数
	 * @param page
	 *            第几页
	 * @return PageList
	 * @throws HibernateException
	 *             hibernate 异常
	 */
	// protected List pageListQuery(final String countField, final String sql,
	// final int page, final int rowsPerPage) throws HibernateException {
	// return pageListQuery(null, countField, sql, page, rowsPerPage);
	// }

	/**
	 * 计算HQL查询的返回记录数
	 * 
	 * @param sql
	 *            查询语句
	 * @param countField
	 *            count语句操作的字段
	 * @return 记录数
	 */
	protected int countHQL(String sql, String countField) {

		int rowsCount = 0;
		Session session = null;
		try {
			session = this.getSession();
			if (null == countField) {
				countField = "*";
			}
			sql = "select count(" + countField + ") " + sql;
			rowsCount = ((Integer) session.createQuery(sql).iterate().next())
					.intValue();
		} catch (HibernateException e) {
			log.error("countHQL() error:" + sql, e);
			throw convertHibernateAccessException(e);
		} finally {
			this.releaseSession(session);
		}
		return rowsCount;
	}

	/**
	 * 计算HQL查询的返回记录数
	 * 
	 * @param sql
	 *            查询语句
	 * @return 记录数
	 */
	protected int countHQL(String sql) {
		return countHQL(sql, null);
	}
}
