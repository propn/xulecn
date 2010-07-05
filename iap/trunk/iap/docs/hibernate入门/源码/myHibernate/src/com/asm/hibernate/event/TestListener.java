package com.asm.hibernate.event;

import org.hibernate.Session;
import org.hibernate.Transaction;
import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class TestListener {
	public static void main(String[] args) {
		addUser();		
	}

	static void addUser() {
		Session s = null;
		Transaction tx = null;
		try {
			User u = new User();
			u.setName("jack");
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();
			s.save(u);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
	
	//д������������ã� deleteδ������������Ĭ�ϵ�delete��������Ч�����Ҳ��Ҫ��Ϊ��֤����
	//
	static void del() {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			User u = (User) s.get(User.class, 1);
			s.delete(u);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
