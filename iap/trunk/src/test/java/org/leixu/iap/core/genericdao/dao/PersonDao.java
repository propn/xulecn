package org.leixu.iap.core.genericdao.dao;

import java.util.List;
import java.util.Iterator;

import org.leixu.iap.core.genericdao.GenericDao;
import org.leixu.iap.core.genericdao.domain.Person;

public interface PersonDao extends GenericDao<Person, Long> {
	List<Person> findByName(String name);

	Iterator<Person> iterateByWeight(int weight);
}
