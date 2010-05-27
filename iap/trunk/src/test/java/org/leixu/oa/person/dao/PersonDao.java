package org.leixu.oa.person.dao;



import java.util.List;

import org.leixu.iap.dao.BaseDAO;
import org.leixu.oa.person.mode.Person;

public interface PersonDao extends BaseDAO<Person>
{
    List<Person> findByName(String name);
}
