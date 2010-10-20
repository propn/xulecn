/**
 * 
 */
package org.leixu.oa.person.entity;

import java.util.List;

import org.leixu.iap.dao.AbstracttDao;
import org.leixu.oa.person.dao.PersonDao;

/**
 * @author lei
 * 
 */
public class PersonDaoImp extends AbstracttDao implements PersonDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.leixu.oa.person.dao.PersonDao#createPerson(org.leixu.oa.person.model
	 * .Person)
	 */
	@Override
	public void createPerson(Person person) throws Exception {
		// TODO Auto-generated method stub
		getConn().commit();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.leixu.oa.person.dao.PersonDao#deletePerson(org.leixu.oa.person.model
	 * .Person)
	 */
	@Override
	public void deletePerson(Person person) throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.leixu.oa.person.dao.PersonDao#requirePerson(org.leixu.oa.person.model
	 * .Person)
	 */
	@Override
	public List<Person> requirePerson(Person person) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.leixu.oa.person.dao.PersonDao#updatePerson(org.leixu.oa.person.model
	 * .Person)
	 */
	@Override
	public int updatePerson(Person person) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}

}
