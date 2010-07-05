package com.asm.test;

import java.util.Date;

import com.asm.dao.UserDao;
import com.asm.daoImpl.UserDaoImpl;
import com.asm.domain.User;

public class UserDaoTest {
	public static void main(String[] args) {
		UserDao ud = new UserDaoImpl();
		User user = new User();
		user.setName("jack");
		user.setDate(new Date());

		ud.saveUser(user);
		System.out.println("ִ��main������" + ud.queryById(1).getName());
		System.out.println("ִ��main������" + ud.queryByName("jack").getId());

		user.setName("newname:richay");
		ud.update(user);
		System.out.println("ִ��main������" + ud.queryById(1).getName());

		user = ud.queryById(1);
		ud.delete(user);
		System.out.println("ִ��main������" + ud.queryById(1) == null ? "�鲻������"
				: "�鵽����");
	}

}
