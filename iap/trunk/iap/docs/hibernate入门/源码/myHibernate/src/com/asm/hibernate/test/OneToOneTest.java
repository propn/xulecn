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
			System.out.println("身份证有效期:" + p.getIdCard().getValidity());
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
			//System.out.println("人的名字：" + idCard.getPerson().getName());
			//去掉上一句注释后，发现会查询两次。
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

			// 分别注释掉以下两句，看程序执行情况
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
