package com.asm.hibernate.test;

import java.util.Date;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class UserTest2 {
	public static void main(String[] args) {
		addUser();
		User u = getUser(1);
		System.out.println("return type:" + u + "\t name=" + u.getName());
	}

	static User getUser(int id) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			User user = (User) s.load(User.class, id);

			// ���µ����ַ�ʽ��������������ȥ�����������ݿ⡣
			// Hibernate.initialize(user);
			//System.out.println(user.getName());
			//System.out.println(user.getId());//��ѯ id���������������ݿ�

			System.out.println("load--User:" + user.getClass());

			return user;
		} finally {
			if (s != null)
				s.close();
		}
	}

	static void addUser() {
		Session s = null;
		Transaction ts = null;
		try {
			User user = new User();
			user.setName("jack");
			user.setDate(new Date());
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
}
