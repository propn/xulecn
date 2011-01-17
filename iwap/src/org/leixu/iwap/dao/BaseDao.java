/**
 * 
 */
package org.leixu.iwap.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.List;

import org.leixu.iwap.db.DbCtx;
import org.leixu.iwap.dbutils.QueryRunner;
import org.leixu.iwap.dbutils.handlers.BeanListHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 * @param <T>
 * 
 */
public class BaseDao<T> {

	private static final Logger log = LoggerFactory.getLogger(DbCtx.class);

	// 在构造函数中反射出泛型类对象
	private Class<T> _class;
	private String dateSource = null;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		_class = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		log.debug("init {}", _class);
	}

	/**
	 * 插入对象
	 * 
	 * @param sql
	 * @param params
	 * @throws Exception
	 */
	public int add(String sql, Object... params) throws Exception {
		Connection conn = DbCtx.getConn(null);
		QueryRunner qr = new QueryRunner();
		int inserts = qr.update(conn, sql, params);
		return inserts;
	}

	/**
	 * 查找多个对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> query(String sql, Object... params) throws Exception {
		List<T> beans = null;
		Connection conn = DbCtx.getConn(null);
		QueryRunner qRunner = new QueryRunner();
		beans = (List<T>) qRunner.query(conn, sql, new BeanListHandler(_class),
				params);
		return beans;
	}

	/**
	 * 查找对象
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T get(String sql, Object... params) throws Exception {
		T obj = null;
		Connection conn = DbCtx.getConn(null);

		QueryRunner qRunner = new QueryRunner();

		List<T> litT = (List<T>) qRunner.query(conn, sql, new BeanListHandler(
				_class), params);

		if (litT != null && litT.size() > 0) {
			obj = litT.get(0);
		}
		return obj;
	}

	/**
	 * 执行更新的sql语句,插入,修改,删除
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean update(String sql) throws Exception {
		Connection conn = null;
		boolean flag = false;
		conn = DbCtx.getConn(null);
		QueryRunner qRunner = new QueryRunner();
		int i = qRunner.update(conn, sql);
		if (i > 0) {
			flag = true;
		}
		return flag;
	}
}