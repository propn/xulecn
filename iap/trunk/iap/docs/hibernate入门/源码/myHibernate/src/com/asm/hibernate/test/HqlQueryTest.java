package com.asm.hibernate.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class HqlQueryTest {
	static void query(String name) {
		Session s = HibernateUtil.getSession();
		String hql = "from User as user where name=?";
		Query q = s.createQuery(hql);
		q.setString(0, name);
		List<User> list = q.list();
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

	static User query2(String name) {
		Session s = HibernateUtil.getSession();
		String hql = "from User as user where name=:n"; // :n���棿
		Query q = s.createQuery(hql);
		q.setString("n", name);
		Object obj = q.uniqueResult();// ����ȷ֪����ѯ�Ľ��ֻ��һ���ǿ���ʹ�ô˷���
		return (User) obj;
	}

	public static void main(String[] args) {
		User user = new User();
		user.setName("tom");
		user.setDate(new Date());
		HibernateUtil.add(user);

		query("tom");
		System.out.println(query2("tom").getName());
	}
}
