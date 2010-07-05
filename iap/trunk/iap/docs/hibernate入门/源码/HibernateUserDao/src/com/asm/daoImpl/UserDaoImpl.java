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
			 * ˵��:Ϊ�˲��մ��ݵ�name�������������ݣ�����ʹ��QBC������ѯ�����ǲ��ܱ�֤���ֵ�Ψһ�ԣ����������������
			 * �ڲ鵽�Ľ���б�ֻ����list�еĵ�һ����¼�� ��Ȼ��������ܶ����ݿ��еı����һ����������֤���ֵ�Ψһ�ԣ�
			 * �����ʹ��uniqueResult()������һ������
			 */
		} finally {
			if (s != null)
				s.close();
		}
	}

	// ����һ���µķ������ڲ�ʹ��hql����ѯ
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
