package com.asm.hibernate.test;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.asm.hibernate.domain.IdCard;
import com.asm.hibernate.domain.Person;
import com.asm.hibernate.utils.HibernateUtil;

public class OneToOneTest {

	public static void main(String[] args) {
		add();
		query(1);
		query2(1);
	}

	static Person query(int id) {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			Person p = (Person) s.get(Person.class, id);
			System.out.println("���֤��Ч��:" + p.getIdCard().getValidity());
			tr.commit();
			return p;
		} finally {
			if (s != null)
				s.close();
		}
	}

	static IdCard query2(int id) {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();
			IdCard idCard = (IdCard) s.get(IdCard.class, id);
			//System.out.println("�˵����֣�" + idCard.getPerson().getName());
			//ȥ����һ��ע�ͺ󣬷��ֻ��ѯ���Ρ�
			tr.commit();
			return idCard;
		} finally {
			if (s != null)
				s.close();
		}
	}

	static void add() {
		Session s = null;
		Transaction tr = null;
		try {
			s = HibernateUtil.getSession();
			tr = s.beginTransaction();

			Person person = new Person();
			person.setName("pName");
			IdCard idCard = new IdCard();
			idCard.setValidity(new Date());

			// �ֱ�ע�͵��������䣬������ִ�����
			person.setIdCard(idCard);
			idCard.setPerson(person);

			s.save(person);
			s.save(idCard);
			tr.commit();
		} finally {
			if (s != null)
				s.close();
		}
	}
}
