package com.asm.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public final class HibernateUtil {
	private static SessionFactory sf;

	private HibernateUtil() {

	}

	static {
		Configuration cfg = new Configuration();
		cfg.configure();
		sf = cfg.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sf;
	}

	public static Session getSession() {
		return sf.openSession();
	}
}
