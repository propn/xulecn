package org.leixu.iap.user.dao;

import java.util.Iterator;
import java.util.List;

import org.leixu.iap.core.genericdao.GenericDao;
import org.leixu.iap.user.domain.User;

public interface UserDao extends GenericDao<User, String> {
	List<User> findByName(String name);

	Iterator<User> iterateByWeight(int weight);
}
