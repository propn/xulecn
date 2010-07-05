package com.asm.hibernate.test.cacheTest;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class CacheSimulate {
	static Map cache = new HashMap();

	public static void main(String[] args) {
		addUser();
		// 第一次查询，会去连接数据库查询
		User u1 = getUser(1);
		// 第二次查询，直接从Map cache中取
		User u2 = getUser(1);
		// 第三次查询，同样从cache中直接取
		User u3 = getUser(1);

		// 获得统计信息：
		Statistics st = HibernateUtil.getSessionFactory().getStatistics();
		System.out.println(st);
	}

	static User getUser(int id) {
		String key = User.class.getName() + id;
		User user = (User) cache.get(key);
		if (user != null)
			return user;
		user = getUserFromDB(id);
		cache.put(key, user);
		return user;
	}

	static void addUser() {
		Session s = null;
		Transaction tr = null;
		try {
			User user = new User();
			user.setName("generalUser");
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			s.save(user);
			tr.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}

	static User getUserFromDB(int id) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			User u = (User) s.get(User.class, id);
			return u;
		} finally {
			if (s != null)
				s.close();
		}
	}
}
