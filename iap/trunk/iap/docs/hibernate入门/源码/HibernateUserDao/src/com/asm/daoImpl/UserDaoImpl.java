package com.asm.daoImpl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.asm.dao.UserDao;
import com.asm.domain.User;
import com.asm.util.HibernateUtil;

public class UserDaoImpl implements UserDao {

	public void delete(User user) {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			s.delete(user);
			tr.commit();
		} catch (HibernateException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}

	}

	public User queryById(int id) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			return (User) s.get(User.class, id);
		} finally {
			if (s != null)
				s.close();
		}
	}

	public User queryByName(String name) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Criteria cr = s.createCriteria(User.class);
			cr.add(Restrictions.eq("name", name));
			List<User> list = cr.list();
			return list.get(0);
			/*
			 * 说明:为了参照传递的name参数来查找数据，这里使用QBC条件查询，我们不能保证名字的唯一性，这里姑且这样处理：
			 * 在查到的结果列表，只返回list中的第一条记录。 当然如果我们能对数据库中的表进行一定限制来保证名字的唯一性，
			 * 则可以使用uniqueResult()来返回一个对象。
			 */
		} finally {
			if (s != null)
				s.close();
		}
	}

	// 增加一个新的方法：内部使用hql语句查询
	public User queryByName2(String name) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			String hql = "from User as user where name=:n";
			Query q = s.createQuery(hql);
			q.setString("n", name);
			List<User> list = q.list();
			return list.get(0);
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void saveUser(User user) {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			s.save(user);
			tr.commit();
		} catch (HibernateException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}
	}

	public void update(User user) {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			s.update(user);
			tr.commit();
		} catch (HibernateException e) {
			if (tr != null)
				tr.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}
	}
}
