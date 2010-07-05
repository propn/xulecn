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
		// 1.创建Criteria对象
		Criteria cr = s.createCriteria(User.class);
		// 2.封装查询条件：传递的参数要等于User类（上步已将User类作为参数来创建了一个Criteria对象）的name属性
		Criterion cron = Restrictions.eq("name", sname);
		// 3.获得带查询条件的Criteria对象
		cr.add(cron);

		// 4.遍历查询结果
		List<User> list = cr.list();
		System.out.println("\n qbc查询结果如下：");
		for (User user : list) {
			System.out.println(user.getName());
		}
	}

	public static void main(String[] args) {
		User user = new User();
		user.setName("new name");
		user.setDate(new Date());
		HibernateUtil.add(user);
		// 再插入一条记录：
		user.setName("jack");
		HibernateUtil.add(user);

		cri("new name");
		// 说明如果注释掉cri方法的条件查询操作(2,3步)，则会查出两条结果：new name ,jack
	}
}
