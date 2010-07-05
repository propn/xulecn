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

		// ��û������Ϣ
		Statistics st = HibernateUtil.getSessionFactory().getStatistics();
		System.out.println(st);
		System.out.println("put:" + st.getSecondLevelCachePutCount());
		System.out.println("hit:" + st.getSecondLevelCacheHitCount());
		System.out.println("miss:" + st.getSecondLevelCacheMissCount());

	}

	static User getUser(int id) {
		Session s = null;
		User user = null;

		// ��������棺
		// HibernateUtil.getSessionFactory().evict(User.class);
		try {
			s = HibernateUtil.getSession();
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// session���棬��sessionδ�ر�ʱ���ٲ�ѯֱ�Ӵӻ����л�����ݡ�
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// �������������棬�ٲ�ѯʱ�����������⡣
			s.evict(user);// ���ָ��������
			// s.clear();//�����ǰsession�����е���������
			user = (User) s.get(User.class, id);
			System.out.println("userName:" + user.getName());

			// ��������Query֧�ֶ������档
			String hql = "from User where id=" + id;
			Query q = s.createQuery(hql);
			q.setCacheable(true);
			List list = q.list();
			System.out.println("query result:" + list.size());
		} finally {
			if (s != null)
				s.close();
		}

		// �������session�رպ�������ٻ�ȡǰ���ѯ�����ݣ��������²�⡣
		// ע�⣬�ں���Ĳ���������ʹ���˶�������ʱ������Ĳ�ѯ��������������
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
