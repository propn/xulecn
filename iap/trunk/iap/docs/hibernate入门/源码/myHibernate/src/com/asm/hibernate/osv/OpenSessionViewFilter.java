package com.asm.hibernate.osv;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.utils.HibernateUtilOSV;

public class OpenSessionViewFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		Session s = null;
		Transaction tx = null;
		try {
			s = HibernateUtilOSV.getThreadLocalSession();
			tx = s.beginTransaction();

			arg2.doFilter(arg0, arg1);
			tx.commit();

		} catch (Exception e) {
			if (tx != null)
				tx.rollback();
		} finally {
			HibernateUtilOSV.closeSession();
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
