package com.asm.dao.impl;


public class UserDaoImp2 {
	private String username;

	public UserDaoImp2() {

	}

	public UserDaoImp2(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void save() {
		System.out.println("save method is called£º" + username);
	}

	public void update() {
		System.out.println("update method is called" + username);
	}

}
