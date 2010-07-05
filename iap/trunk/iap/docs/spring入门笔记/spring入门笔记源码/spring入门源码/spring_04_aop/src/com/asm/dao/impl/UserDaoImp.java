package com.asm.dao.impl;

import com.asm.dao.UserDao;

public class UserDaoImp implements UserDao {
	private String username;

	public UserDaoImp() {

	}

	public UserDaoImp(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public void save() {
		int i=1/0;
		System.out.println("save method is called£º" + username);
	}

	@Override
	public void update() {
		System.out.println("update method is called" + username);
	}

	public String add(String name) {
		System.out.println("add method is called [" + name+"]");
		return "Ìí¼Ó³É¹¦";
	}
}
