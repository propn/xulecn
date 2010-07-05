package com.asm.hibernate.test;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.Department;
import com.asm.hibernate.domain.Employee;
import com.asm.hibernate.utils.HibernateUtil;

public class OneToManyTest {
	public static void main(String[] args) {
		add();
		query(1);
	}

	static Department query(int departId) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Department depart = (Department) s.get(Department.class, departId);
			System.out.println("employee size:" + depart.getEmps().size());
			return depart;
		} finally {
			if (s != null)
				s.close();
		}
	}

	static void add() {
		Session s = null;
		Transaction tx = null;
		try {
			Department depart = new Department();
			depart.setName("departName");

			Employee emp1 = new Employee();
			emp1.setName("empName1");
			emp1.setDepart(depart);// Ա��ά������֮��Ĺ�ϵ

			Employee emp2 = new Employee();
			emp2.setName("empName2");
			emp2.setDepart(depart);// Ա��ά������֮��Ĺ�ϵ

			Set<Employee> emps = new HashSet<Employee>();
			emps.add(emp1);
			emps.add(emp2);
			depart.setEmps(emps); // ����ά������֮��Ĺ�ϵ

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(emp2);
			s.save(emp1);
			s.save(depart);
			// ���ϵĴ������д˳��������������.
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
