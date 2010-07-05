package com.asm.hibernate.test;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class VersionUserTest {
	public static void main(String[] args) {
		addUser();
		update(1);
	}

	static void update(int id) {
		Session s1 = null;
		Session s2 = null;
		Transaction tx1 = null;
		Transaction tx2 = null;
		try {
			s1 = HibernateUtil.getSession();
			tx1 = s1.beginTransaction();
			User user1 = (User) s1.get(User.class, id);
			System.out.println(user1.getName());
			s2 = HibernateUtil.getSession();
			tx2 = s2.beginTransaction();
			User user2 = (User) s2.get(User.class, id);

			user1.setName("user1Name");
			user2.setName("user2Name");

			tx1.commit();
			tx2.commit();
		} catch (HibernateException e) {
			if (tx1 != null)
				tx1.rollback();
			if (tx2 != null)
				tx2.rollback();
			throw e;
		} finally {
			if (s1 != null)
				s1.close();
			if (s2 != null)
				s2.close();
		}
	}

	static void addUser() {
		Session s = null;
		Transaction tx = null;
		try {
			User user = new User();
			user.setName("UserName");
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			s.save(user);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
