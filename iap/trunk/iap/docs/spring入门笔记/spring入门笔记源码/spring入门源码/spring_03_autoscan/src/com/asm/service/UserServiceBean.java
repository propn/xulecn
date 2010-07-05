package com.asm.service;

import java.beans.ConstructorProperties;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.asm.dao.UserDao;

@Service("usb")@Scope("singleton")
public class UserServiceBean {
	@Resource(name = "userDaoImpl")
	private UserDao userDao;
	private UserDao userDao2;

	@Autowired(required = true)
	@Qualifier("userDaoImpl")
	private UserDao userDao3;

	@Resource
	public void setUserDao2(UserDao userDao2) {
		this.userDao2 = userDao2;
	}

	public UserServiceBean() {

	}

	@PostConstruct
	public void init() {
		System.out.println("init method is called");
	}

	public void test() {
		System.out.println("********************************");
		userDao.save();
		userDao2.save();
		System.out.println(userDao3);
		// userDao3.save();
		System.out.println("********************************");
	}
}
