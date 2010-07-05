package com.asm.hibernate.test;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.Department;
import com.asm.hibernate.domain.Employee;

public class OneToManyTest {
	public static void main(String[] args) {
		add();
		Department dep = (query(1));
		// 为了实现下面的效果，在Employee中重写了toString方法
		for (Employee emp : dep.getEmps())
			System.out.println(emp);
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
			emp1.setDepart(depart);
			Employee emp2 = new Employee();
			emp2.setName("empName2");
			emp2.setDepart(depart);

			List<Employee> emps = new ArrayList<Employee>();
			emps.add(emp1);
			emps.add(emp2);
			// 交换上面两行的内容，试执行效果，也可在数据库客户端查看“newem”表中的“order_by”字段
			depart.setEmps(emps);

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(depart);
			s.save(emp1);
			s.save(emp2);
			tx.commit();
			// 当上面保存执行后，Hibernate会重新把放置到depart中，即是
			// 把org.hibernate.collection.PersistentList对象放置进去。这样如果我们进行下面的转型操作就会出错，
			// 而且如果把实体类Depatment的emps的类型设置为ArrayList类，当我们放置PersistentList就会出错，因为
			// org.hibernate.collection.PersistentList只是实现了java.util.List接口，所以是不能放置得。
			ArrayList list = (ArrayList) depart.getEmps();
			// 特别注意上句，尽管我们new出了ArrayList,但是当Hibernate进行操作时，它会把ArrayList进行重新封装，
			// 这样实质已经变了，通过运行时报错情况可以知道。
		} finally {
			if (s != null)
				s.close();
		}
	}
}
