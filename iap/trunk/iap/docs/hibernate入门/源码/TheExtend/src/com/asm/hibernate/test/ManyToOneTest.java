package com.asm.hibernate.test;

import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.Department;
import com.asm.hibernate.domain.Employee;
import com.asm.hibernate.domain.Sale;
import com.asm.hibernate.domain.Skill;
import com.asm.hibernate.utils.HibernateUtil;

public class ManyToOneTest {
	public static void main(String[] args) {
		add();
		Employee emp = query(2);
		System.out.println("emp type:" + emp);
	}

	static Employee query(int empId) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Employee emp = (Employee) s.get(Employee.class, empId);
			System.out.println("Department Name:" + emp.getDepart().getName());
			return emp;
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

			Employee emp = new Employee();
			emp.setName("empName");
			emp.setDepart(depart);

			Sale emp2 = new Sale();
			emp2.setName("saleEmployee");
			emp2.setSignSale("saleName");
			emp2.setDepart(depart);

			Skill emp3 = new Skill();
			emp3.setName("skillEmployee");
			emp3.setSignSkill("skillName");
			emp3.setDepart(depart);

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(emp);
			s.save(emp2);
			s.save(emp3);
			s.save(depart);
			tx.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
