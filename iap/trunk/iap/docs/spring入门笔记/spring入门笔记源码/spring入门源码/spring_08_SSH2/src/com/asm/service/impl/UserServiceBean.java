package com.asm.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asm.entity.User;
import com.asm.service.UserService;

public class UserServiceBean implements UserService {
	@Resource
	private SessionFactory sf;

	@Transactional
	public void save(User user) {
		sf.getCurrentSession().persist(user);
	}

	@Transactional
	public void delete(Integer id) {
		sf.getCurrentSession().delete(sf.getCurrentSession().load(User.class, id));
	}

	@Transactional
	public void update(User user) {
		sf.getCurrentSession().merge(user);
	}

	@Transactional(propagation=Propagation.NESTED,readOnly=true)
	public User getUser(Integer id) {
		return (User) sf.getCurrentSession().get(User.class, id);
	}

	@Transactional(propagation=Propagation.NESTED,readOnly=true)
	@SuppressWarnings("unchecked")
	public List<User> getUsers() {
		return sf.getCurrentSession().createQuery("from User").list();
	}
}
