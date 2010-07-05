package com.asm.dao.impl;

import org.springframework.stereotype.Repository;

import com.asm.dao.UserDao;

@Repository()
public class UserDaoImpl implements UserDao {
	public UserDaoImpl() {
		//System.out.println("constructor is called");
	}

	public void init() {
		System.out.println("init method is called");
	}

	@Override
	public void save() {
		System.out.println("Ö´ÐÐsave·½·¨...");
	}
	
	public void destroy(){
		System.out.println("destroy method is called");
	}
}
