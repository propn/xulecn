package com.asm.action;

import javax.annotation.Resource;

import com.asm.entity.User;
import com.asm.service.UserService;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport {

	@Resource
	private UserService userService;
	private String username;

	@Override
	public String execute() throws Exception {
		User user=new User(username);
		System.out.println("-------------------"+username);
		userService.save(user);
		return "success";
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}