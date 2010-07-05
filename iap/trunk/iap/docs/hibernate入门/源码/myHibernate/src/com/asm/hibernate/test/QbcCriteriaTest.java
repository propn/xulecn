package com.asm.hibernate.test;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import com.asm.hibernate.domain.User;
import com.asm.hibernate.utils.HibernateUtil;

public class QbcCriteriaTest {
	static void cri(String sname) {
		Session s = HibernateUtil.getSession();
		// 1.����Criteria����
		Criteria cr = s.createCriteria(User.class);
		// 2.��װ��ѯ���������ݵĲ���Ҫ����User�ࣨ�ϲ��ѽ�User����Ϊ������������һ��Criteria���󣩵�name����
		Criterion cron = Restrictions.eq("name", sname);
		// 3.��ô���ѯ������Criteria����
		cr.add(cron);

		// 4.������ѯ���
		List<User> list = cr.list();
		System.out.println("\n qbc��ѯ������£�");
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

	public static void main(String[] args) {
		User user = new User();
		user.setName("new name");
		user.setDate(new Date());
		HibernateUtil.add(user);
		// �ٲ���һ����¼��
		user.setName("jack");
		HibernateUtil.add(user);

		cri("new name");
		// ˵�����ע�͵�cri������������ѯ����(2,3��)����������������new name ,jack
	}
}
