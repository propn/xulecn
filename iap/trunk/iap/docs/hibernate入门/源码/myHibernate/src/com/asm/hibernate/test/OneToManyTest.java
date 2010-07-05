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
			emp1.setDepart(depart);// 员工维护它们之间的关系

			Employee emp2 = new Employee();
			emp2.setName("empName2");
			emp2.setDepart(depart);// 员工维护它们之间的关系

			Set<Employee> emps = new HashSet<Employee>();
			emps.add(emp1);
			emps.add(emp2);
			depart.setEmps(emps); // 部门维护它们之间的关系

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(emp2);
			s.save(emp1);
			s.save(depart);
			// 以上的代码的书写顺序分两种情况讨论.
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
