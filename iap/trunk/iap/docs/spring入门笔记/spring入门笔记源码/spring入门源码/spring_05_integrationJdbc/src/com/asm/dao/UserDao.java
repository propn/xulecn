package com.asm.dao;

import java.util.List;

import com.asm.entity.User;

public interface UserDao {
	void save(User user);
	void delete(User user);
	void update(User user);
	User get(int id);
	List<User> getUsers();
}
