package com.asm.hibernate.test;

import org.hibernate.Session;
import org.hibernate.Hibernate;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.Department;
import com.asm.hibernate.domain.Employee;
import com.asm.hibernate.utils.HibernateUtil;

public class ManyToOneTest {
	public static void main(String[] args) {
		add();
		query(1);

		// 第二种查询方法，注意区别第一种查询
		Employee emp = query2(1);
		System.out.println(emp.getDepart().getName());
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

	static Employee query2(int empId) {
		Session s = null;
		try {
			s = HibernateUtil.getSession();
			Employee emp = (Employee) s.get(Employee.class, empId);
			Hibernate.initialize(emp.getDepart());
			// 上一句作用后面会作说明，这里略作了解
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
			// 对象模型， 建立两个对象的关联，注释掉上句查询select * from employee;时会发现depart_id为null

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(emp);
			s.save(depart);
			// 交换以上两句的位置，看Hibernate执行的sql语句。会再增加一条更新操作。
			tx.commit();
			

		} finally {
			if (s != null)
				s.close();
		}
	}
}
