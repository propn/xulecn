package com.asm.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import com.asm.entity.User;
import com.asm.service.UserService;

public class UserServiceBean implements UserService {
	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void save(User user) {
		em.persist(user);
	}

	public void delete(Integer id) {
		em.remove(em.getReference(User.class, id));
	}
	
	@Transactional
	public void update(User user) {
		em.merge(user);
	}
	
	public User getUser(Integer id) {
		return em.find(User.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return em.createQuery("select u from User u").getResultList();
	}
}
