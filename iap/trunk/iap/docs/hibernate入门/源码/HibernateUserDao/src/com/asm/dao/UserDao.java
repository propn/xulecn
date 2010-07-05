package com.asm.dao;

import com.asm.domain.User;

public interface UserDao {
	public void saveUser(User user);

	public User queryById(int id);

	public User queryByName(String name);

	public void update(User user);

	public void delete(User user);

}
