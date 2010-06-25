package org.leixu.iap.person.dao;

import java.util.Iterator;
import java.util.List;

import org.leixu.iap.core.genericdao.GenericDao;
import org.leixu.iap.person.domain.Person;

public interface PersonDao extends GenericDao<Person, Long> {
	List<Person> findByName(String name);

	Iterator<Person> iterateByWeight(int weight);
}
