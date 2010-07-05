package com.asm.service;

import java.util.List;

import com.asm.entity.User;

public interface UserService {

	public abstract void save(User user);

	public abstract void delete(Integer id);

	public abstract void update(User user);

	public abstract User getUser(Integer id);

	@SuppressWarnings("unchecked")
	public abstract List<User> getUsers();

}