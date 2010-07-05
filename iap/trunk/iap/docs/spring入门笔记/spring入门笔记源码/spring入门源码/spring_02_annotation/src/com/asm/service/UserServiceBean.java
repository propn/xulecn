package com.asm.service;


import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.asm.dao.UserDao;

public class UserServiceBean {
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	private UserDao userDao2;
	
	@Autowired(required=true)
	@Qualifier("userDaoImpl")
	private UserDao userDao3;

	@Resource
	public void setUserDao2(UserDao userDao2) {
		this.userDao2 = userDao2;
	}

	public UserServiceBean() {

	}

	public void test() {
		userDao.save();
		userDao2.save();
		System.out.println(userDao3);
		//userDao3.save();
	}
}
