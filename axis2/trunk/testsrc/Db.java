import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Db {

	private static List<Person> db = Collections
			.synchronizedList(new ArrayList<Person>());

	private static List<Student> db2 = Collections
			.synchronizedList(new ArrayList<Student>());

	public static List<Person> getDb() {
		return db;
	}

	public static List<Student> getDb2() {
		return db2;
	}

	public synchronized static void add1(Person person) {
		getDb().add(person);
	}

	public synchronized static void add2(Student student) {
		getDb2().add(student);
	}

}
