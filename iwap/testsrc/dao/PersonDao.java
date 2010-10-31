package dao;

import db.JdbcTemplate;



public class PersonDao {

	public void addPerson(Person person){
		
		person.setThreadId(String.valueOf(Thread.currentThread().getId()));
		person.setThreadName(Thread.currentThread().getName());
		
		
		
		
	}
}
