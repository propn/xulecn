public class StudentDao {

	public void addStudent(Student student) {

		student.setThreadId(String.valueOf(Thread.currentThread().getId()));
		student.setThreadName(Thread.currentThread().getName());
		
		Db.add2(student);
	}
}
