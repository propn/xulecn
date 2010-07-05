package com.asm.hibernate.test;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class JdbcSqlSelectTest {
	public static void main(String[] args) {
		addUsers();
		//query();
		
		//ÃüÃû²éÑ¯
		List rs=namedQuery(1);
		for(int i=0;i<rs.size();i++)
		System.out.println("-------------------"+rs.get(i).getClass());
	}

	static void query() {
		Session s = HibernateUtil.getSession();
		Query q = s.createSQLQuery("select * from user").addEntity(User.class);
		List<User> rs = q.list();
		for (User u : rs) {
			System.out.println("Result£º" + u.getName() + "---" + u.getDate());
		}
	}

	static void addUsers() {
		for (int i = 0; i < 3; i++) {
			User u = new User();
			u.setName("name" + i);
			addUser(u);
		}
	}

	static void addUser(User u) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		s.save(u);
		tx.commit();
		s.close();
	}
	
	static List namedQuery(int id) {
		Session s = HibernateUtil.getSession();
		Query q = s.getNamedQuery("selectUserbyId");
		//Query q=s.getNamedQuery("com.asm.hibernate.domain.User.selectUserbyIdTheSecond");
		q.setInteger("id", id);
		return q.list();
	}
	
	static List namedSqlQuery(int id) {
		Session s = HibernateUtil.getSession();
		Query q = s.getNamedQuery("selectUserSql");
		return q.list();
	}
}
