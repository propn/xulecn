package com.asm.hibernate.test.cacheTest;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.stat.Statistics;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class HibernateCacheTest {
	public static void main(String[] args) {
		addUser();
		getUser(1);

		// 获得缓存的信息
		Statistics st = HibernateUtil.getSessionFactory().getStatistics();
		System.out.println(st);
		System.out.println("put:" + st.getSecondLevelCachePutCount());
		System.out.println("hit:" + st.getSecondLevelCacheHitCount());
		System.out.println("miss:" + st.getSecondLevelCacheMissCount());

	}

	static User getUser(int id) {
		Session s = null;
		User user = null;

		// 清二级缓存：
		// HibernateUtil.getSessionFactory().evict(User.class);
		try {
			s = HibernateUtil.getSession();
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// session缓存，当session未关闭时，再查询直接从缓存中获得数据。
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// 如果我们清掉缓存，再查询时将会重新连库。
			s.evict(user);// 清掉指定的数据
			// s.clear();//清掉当前session缓存中的所有内容
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// 以下是让Query支持二级缓存。
			String hql = "from User where id=" + id;
			Query q = s.createQuery(hql);
			q.setCacheable(true);
			List list = q.list();
			System.out.println("query result:" + list.size());
		} finally {
			if (s != null)
				s.close();
		}

		// 当上面的session关闭后，如果想再获取前面查询的数据，必须重新查库。
		// 注意，在后面的操作中我们使用了二级缓存时，下面的查询将不会引起连库
		try {
			s = HibernateUtil.getSession();
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());
		} finally {
			if (s != null)
				s.close();
		}
		return user;
	}

	static void addUser() {
		User user = new User();
		user.setName("genName");
		HibernateUtil.add(user);
	}
}
