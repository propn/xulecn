package com.asm.hibernate.utils;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtilOSV {
	private static SessionFactory sf;
	private static ThreadLocal session = new ThreadLocal();

	private HibernateUtilOSV() {

	}

	public static Session getThreadLocalSession() {
		Session s = (Session) session.get();
		if (s == null) {
			s = getSession();
			session.set(s);
		}
		return s;
	}

	public static void closeSession() {
		Session s = (Session) session.get();
		if (s != null) {
			s.close();
			session.set(null);
		}
	}

	static {
		Configuration cf = new Configuration();
		cf.configure();
		sf = cf.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}

	public static Session getSession() {
		return sf.openSession();
	}

	// ͳһ��������Ӳ���
	public static void add(Object obj) {
		Session s = null;
		Transaction ts = null;
		try {
			s = HibernateUtilOSV.getSession();
			ts = s.beginTransaction();
			s.save(obj);
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

	// ͳһ��ɾ������
	public static void delete(Object obj) {
		Session s = null;
		Transaction ts = null;
		try {
			s = HibernateUtilOSV.getSession();
			ts = s.beginTransaction();
			s.delete(obj);
			ts.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}

	// ͳһ�ĸ��²���
	public static void update(Object obj) {
		Session s = null;
		Transaction ts = null;
		try {
			s = HibernateUtilOSV.getSession();
			ts = s.beginTransaction();
			s.update(obj);
			ts.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
