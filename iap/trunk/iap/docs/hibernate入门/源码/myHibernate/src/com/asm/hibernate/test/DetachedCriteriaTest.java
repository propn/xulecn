package com.asm.hibernate.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class DetachedCriteriaTest {
	public static void main(String[] args) {
		add();

	 DetachedCriteria dec = DetachedCriteria.forClass(User.class);
		String name = "jack";
		dec.add(Restrictions.eq("name", name));
		List<User> list = detaCri(dec);
		for (User u : list)
			System.out.println(u);
	}

	static List detaCri(DetachedCriteria dec) {
		Session s = HibernateUtil.getSession();
		Criteria cr = dec.getExecutableCriteria(s);
		List list = cr.list();
		s.close();
		return list;
	}

	static void add() {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		User u1 = new User();
		u1.setName("jack");
		u1.setDate(new Date());

		User u2 = new User();
		u2.setName("jack");
		u2.setDate(new Date());
		s.save(u1);
		s.save(u2);
		tx.commit();
		s.close();
	}
}
