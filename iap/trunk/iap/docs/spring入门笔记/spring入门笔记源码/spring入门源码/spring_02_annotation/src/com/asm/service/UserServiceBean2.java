package com.asm.service;

import com.asm.dao.UserDao;

public class UserServiceBean2 {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public UserServiceBean2() {

	}

	public void test() {
		userDao.save();
	}

}
