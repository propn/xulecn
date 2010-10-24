
public class Dao {

	public void addPerson(Person person){
		
		person.setThreadId(String.valueOf(Thread.currentThread().getId()));
		person.setThreadName(Thread.currentThread().getName());
		Db.add1(person);
	}
}
