package com.asm.test;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.dao.UserDao;
import com.asm.entity.User;

public class TestSJ {
	private UserDao ud = (UserDao) new ClassPathXmlApplicationContext("beans.xml")
			.getBean("userDaoImp");

	public static void main(String[] args) {
		TestSJ sj = new TestSJ();
		sj.save();
		// sj.get();
		// sj.updte();
		// sj.delete();
		// sj.gets();
		
	}

	public void save() {
		User user = new User();
		user.setName("桲議議");
		ud.save(user);
	}

	public void delete() {
		User user = new User();
		user.setId(1);
		ud.delete(user);
	}

	public void updte() {
		User user = ud.get(1);
		user.setName("燠議議");
		user.setId(1);
		ud.update(user);
	}

	public void get() {
		System.out.println(ud.get(1));
	}

	public void gets() {
		for (User user : ud.getUsers())
			System.out.println(user);
	}
}
