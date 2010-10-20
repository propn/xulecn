/**
 * 
 */
package org.leixu.oa.person.dao;

import java.util.List;

import org.leixu.oa.person.model.Person;

/**
 * @author lei
 *
 */
public interface PersonDao {
	public void createPerson(Person person) throws Exception;
	public List<Person> requirePerson(Person person) throws Exception;
	public int updatePerson(Person person) throws Exception;
	public void deletePerson(Person person) throws Exception;

}
