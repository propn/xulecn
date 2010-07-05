package com.asm.hibernate.osv;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtilSelf;

public class UserTest {
	public static void main(String[] args) {
		User user = new User();
		user.setName("richie");
		user.setDate(new Date());
		addUser(user);
	}

	static void addUser(User user) {
		Session s = null;
		Transaction ts = null;
		try {
			s = HibernateUtilSelf.getThreadLocalSession();
			ts = s.beginTransaction();
			s.save(user);
			ts.commit();
		} catch (HibernateException e) {
			if (ts != null)
				ts.rollback();
			throw e;
		} finally {
			if (s != null)
				// s.close(); //ע�����ﲻ��ʹ��close,��ע������Ĵ�ӡ���
				System.out.println("s=" + s);
		}
	}
}
