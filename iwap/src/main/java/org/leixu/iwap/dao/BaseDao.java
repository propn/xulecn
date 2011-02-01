/**
 * 
 */
package org.leixu.iwap.dao;

import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.util.List;

import org.leixu.iwap.db.ConnUtils;
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

	private static final Logger log = LoggerFactory.getLogger(ConnUtils.class);

	private Class<T> _class;
	private String dateSource = _class.getSimpleName();

	@SuppressWarnings("unchecked")
	public BaseDao() {
		_class = (Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		log.debug("init Dao by VO {}", _class);
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @throws Exception
	 */
	public int add(String sql, Object... params) throws Exception  {
		Connection conn = ConnUtils.getConn(dateSource);
		QueryRunner qr = new QueryRunner();
		int inserts = qr.update(conn, sql, params);
		return inserts;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<T> query(String sql, Object... params) throws Exception {
		List<T> beans = null;
		Connection conn = ConnUtils.getConn(null);
		QueryRunner qRunner = new QueryRunner();
		beans = (List<T>) qRunner.query(conn, sql, new BeanListHandler(_class),
				params);
		return beans;
	}

	/**
	 * 
	 * @param sql
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T get(String sql, Object... params) throws Exception {
		T obj = null;
		Connection conn = ConnUtils.getConn(null);

		QueryRunner qRunner = new QueryRunner();

		List<T> litT = (List<T>) qRunner.query(conn, sql, new BeanListHandler(
				_class), params);

		if (litT != null && litT.size() > 0) {
			obj = litT.get(0);
		}
		return obj;
	}

	/**
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	public boolean update(String sql) throws Exception {
		Connection conn = null;
		boolean flag = false;
		conn = ConnUtils.getConn(null);
		QueryRunner qRunner = new QueryRunner();
		int i = qRunner.update(conn, sql);
		if (i > 0) {
			flag = true;
		}
		return flag;
	}
	
}