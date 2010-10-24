
public class Bo {
	
	public void b1(){
		
		Dao d1=new Dao();
		Person per1 = new Person();
		per1.setName("user1 dao1");
		d1.addPerson(per1);
		
		Dao d2=new Dao();
		Person per2 = new Person();
		per2.setName("user2 dao2");
		d2.addPerson(per2);
		
		Dao d3=new Dao();
		Person pe3 = new Person();
		pe3.setName("user3 dao3");
		d3.addPerson(pe3);
		
		

		StudentDao d4=new StudentDao();
		Student s1 = new Student();
		s1.setName("s1 d4");
		d4.addStudent(s1);
		
		StudentDao d5=new StudentDao();
		Student s2 = new Student();
		s2.setName("s4 d5");
		d5.addStudent(s2);
		
		StudentDao d6=new StudentDao();
		Student s3 = new Student();
		s3.setName("s3 d6");
		d6.addStudent(s3);
		
	}
	
}
