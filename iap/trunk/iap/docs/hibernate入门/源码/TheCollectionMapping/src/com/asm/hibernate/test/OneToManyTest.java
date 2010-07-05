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
		// Ϊ��ʵ�������Ч������Employee����д��toString����
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
			// �����������е����ݣ���ִ��Ч����Ҳ�������ݿ�ͻ��˲鿴��newem�����еġ�order_by���ֶ�
			depart.setEmps(emps);

			s = HibernateUtil.getSession();
			tx = s.beginTransaction();

			s.save(depart);
			s.save(emp1);
			s.save(emp2);
			tx.commit();
			// �����汣��ִ�к�Hibernate�����°ѷ��õ�depart�У�����
			// ��org.hibernate.collection.PersistentList������ý�ȥ������������ǽ��������ת�Ͳ����ͻ����
			// ���������ʵ����Depatment��emps����������ΪArrayList�࣬�����Ƿ���PersistentList�ͻ������Ϊ
			// org.hibernate.collection.PersistentListֻ��ʵ����java.util.List�ӿڣ������ǲ��ܷ��õá�
			ArrayList list = (ArrayList) depart.getEmps();
			// �ر�ע���Ͼ䣬��������new����ArrayList,���ǵ�Hibernate���в���ʱ�������ArrayList�������·�װ��
			// ����ʵ���Ѿ����ˣ�ͨ������ʱ�����������֪����
		} finally {
			if (s != null)
				s.close();
		}
	}
}
