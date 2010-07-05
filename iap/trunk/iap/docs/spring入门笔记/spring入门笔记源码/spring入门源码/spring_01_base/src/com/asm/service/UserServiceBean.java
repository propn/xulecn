package com.asm.service;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.asm.dao.UserDao;

public class UserServiceBean {
	private UserDao userDao;
	private String username;
	private int id;
	private List<String> list;
	private Set<String> set;
	private Map<String, String> map;
	private Properties pro;

	public UserServiceBean() {

	}

	public UserServiceBean(String username, UserDao userDao, Set<String> set) {
		this.username = username;
		this.userDao = userDao;
		this.set = set;
	}

	public void testSetterDI() {
		System.out.println("testSetterDI 开始测试注入----->：");
		userDao.save();
		System.out.println("获取注入的id属性：" + username);
		System.out.println("获取注入的username属性：" + id);
		for (String str : list) {
			System.out.println("list-->" + str);
		}
		for (String str : set) {
			System.out.println("set-->" + str);
		}
		for (String str : map.keySet()) {
			System.out.println(str + "=" + map.get(str));
		}
		pro.list(System.out);
	}

	public void testConstructorDI() {
		System.out.println("testConstructorDI方法 开始测试注入----->：");
		userDao.save();
		System.out.println("获取注入的username属性：" + id);
		for (String str : set) {
			System.out.println("set-->" + str);
		}

	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public void setSet(Set<String> set) {
		this.set = set;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public void setPro(Properties pro) {
		this.pro = pro;
	}

}
