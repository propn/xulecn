package com.asm.hibernate.test;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class UserTest2 {
	static void addUser(User user) {
		Session s = null;
		Transaction ts = null;
		try {
			s = HibernateUtil.getSession();
			ts = s.beginTransaction();
			s.save(user);
			ts.commit();
		} catch (HibernateException e) {
			if (ts != null)
				ts.rollback();
			throw e;
		} finally {
			if (s != null)
				s.close();
		}
	}

	static User getUser(int id) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			return (User) s.get(User.class, id);
			/*
			 * User user=(User) s.load(User.class,id);
			 * System.out.println("----load----"+user);
			 * System.out.println(user.getName());
			 * //load只是准备连接到数据库，当增加上面一句操作时表示有真正的数据库操作，这时它才会去连接数据库 return user;
			 */
		} finally {
			if (s != null)
				s.close();
		}
	}

	public static void main(String[] args) {
		User user = new User();
		user.setName("richie");
		user.setDate(new Date());
		addUser(user);
		User u = getUser(1);
		System.out.println("id=" + u.getId() + "\t name=" + u.getName());
	}
}
