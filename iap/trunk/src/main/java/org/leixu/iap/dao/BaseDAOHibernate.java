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
	 * �����������ʵ��
	 * 
	 * @param id
	 *            ʵ������
	 * @return ʵ�����
	 */
	@SuppressWarnings("unchecked")
	public final E getEntity(final Long id) {
		return (E) getHibernateTemplate().get(clazz, id);
	}

	/**
	 * �������ʵ��
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
	 * ����ʵ��
	 * 
	 * @param entity
	 *            ʵ�����
	 */
	public final void saveEntity(final E entity) {
		getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * ��������ɾ��ʵ��
	 * 
	 * @param id
	 *            ʵ������
	 */
	public final void removeEntity(final Long id) {
		Object o = getEntity(id);
		if (null != o) {
			getHibernateTemplate().delete(o);
		}
	}

	/**
	 * ִ���������º�ɾ��������HQL
	 * 
	 * @param sql
	 *            hql���
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
	 * ִ�з�ҳ��ѯ
	 * 
	 * @param selectField
	 *            HQL����У�SELECT ������(��������˴˲���ֵ,��sql�����в��ɴ�SELCT��䲿��)
	 * @param countField
	 *            HQL����У�count ������
	 * @param sql
	 *            HQL���
	 * @param page
	 *            �ڼ�ҳ
	 * @param rowsPerPage
	 *            ÿҳ��¼��
	 * @return PageList
	 */
	// @SuppressWarnings("unchecked")
	// protected final List<E> pageListQuery(final String selectField,
	// String countField, String sql, int page, int rowsPerPage) {
	// PageList result = new PageList();
	// Session session = null;
	// try {
	// session = this.getSession();
	// // Ԥ��count��sql���
	// String countSql = sql.substring(sql.toUpperCase().indexOf("FROM"));
	// // ���÷��ص��У����в�ѯ
	// if (null != selectField) {
	// sql = "SELECT " + selectField + sql;
	// }
	// if (page <= 0) {
	// page = 1; // page��СΪ1
	// }
	// log.debug("query sql:" + sql);
	// Query q = session.createQuery(sql);
	// if (rowsPerPage > 0) { // rowsPerPage��ֵ��0��-1ʱ,������ȫ�������
	// q.setFirstResult(rowsPerPage * (page - 1));
	// q.setMaxResults(rowsPerPage);
	// }
	//
	// result.addAll(q.list());
	//
	// // ���÷�ҳ��ѯ����
	// if (null == countField) {
	// countField = "*";
	// }
	//
	// int rowsCount = result.size();
	// if (rowsPerPage > 1 && rowsCount > 0) {
	// // ÿҳ��¼������1�ҽ��������0�ż����ҳ��Ϣ,����ǰҳ��¼������Ϊ�ܵļ�¼��
	// // TODO ���pageֵ����,���ܵ���rowsCountΪ0������
	// countSql = "select count(" + countField + ") " + countSql;
	// // �����ܼ�¼��ʱ,���� Order by��䣬�������
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
	 * ִ�з�ҳ��ѯ
	 * 
	 * @param selectField
	 *            HQL����У�SELECT ������(��������˴˲���ֵ,��sql�����в��ɴ�SELCT��䲿��)
	 * @param sql
	 *            HQL���
	 * @param page
	 *            �ڼ�ҳ
	 * @param rowsPerPage
	 *            ÿҳ��¼��
	 * @param totalRowCount
	 *            HQL����õ��ܼ�¼��
	 * @return PageList
	 */
	// @SuppressWarnings("unchecked")
	// protected final PageList pageListQuery(final String selectField,
	// String sql, int page, int rowsPerPage, final int totalRowCount) {
	// PageList result = new PageList();
	// Session session = null;
	// try {
	// session = this.getSession();
	// // ���÷��ص��У����в�ѯ
	// if (null != selectField) {
	// sql = "SELECT " + selectField + sql;
	// }
	// if (page <= 0) {
	// page = 1; // page��СΪ1
	// }
	//
	// Query q = session.createQuery(sql);
	// if (rowsPerPage > 0) { // rowsPerPage��ֵ��0��-1ʱ,������ȫ�������
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
	// * ִ�з�ҳ��ѯ
	// *
	// * @param sql HQL���
	// * @param page �ڼ�ҳ
	// * @param rowsPerPage ÿҳ��¼��
	// * @param totalRowCount HQL����õ��ܼ�¼��
	// * @return PageList
	// */
	// protected final PageList pageListQuery(final String sql, final int page,
	// final int rowsPerPage, final int totalRowCount) {
	// return pageListQuery(null, sql, page, rowsPerPage, totalRowCount);
	// }

	/**
	 * ִ�з�ҳ��ѯ
	 * 
	 * @param sql
	 *            HQL���
	 * @param rowsPerPage
	 *            ÿҳ��¼��
	 * @param page
	 *            �ڼ�ҳ
	 * @return PageList
	 * @throws HibernateException
	 *             hibernate �쳣
	 */
	// protected List<E> pageListQuery(final String sql, final int page,
	// final int rowsPerPage) throws HibernateException {
	// return pageListQuery(null, null, sql, page, rowsPerPage);
	// }

	/**
	 * ִ�з�ҳ��ѯ
	 * 
	 * @param countField
	 *            HQL����У�count ������
	 * @param sql
	 *            HQL���
	 * @param rowsPerPage
	 *            ÿҳ��¼��
	 * @param page
	 *            �ڼ�ҳ
	 * @return PageList
	 * @throws HibernateException
	 *             hibernate �쳣
	 */
	// protected List pageListQuery(final String countField, final String sql,
	// final int page, final int rowsPerPage) throws HibernateException {
	// return pageListQuery(null, countField, sql, page, rowsPerPage);
	// }

	/**
	 * ����HQL��ѯ�ķ��ؼ�¼��
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @param countField
	 *            count���������ֶ�
	 * @return ��¼��
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
	 * ����HQL��ѯ�ķ��ؼ�¼��
	 * 
	 * @param sql
	 *            ��ѯ���
	 * @return ��¼��
	 */
	protected int countHQL(String sql) {
		return countHQL(sql, null);
	}
}
