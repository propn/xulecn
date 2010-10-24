import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Test extends Thread {

	public void run() {
		try {
			new Bo().b1();
			Ctx.commit();
		} catch (Exception e) {
			Ctx.rollback();
		} finally {
			Ctx.close();
		}
	}

	public static void main(String[] args) {

		System.out.println(Thread.currentThread().getName() + "           "
				+ Thread.currentThread().getId());

		for (int i = 0; i < 5; i++) {
			Test t = new Test();
			t.start();
		}

		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Person> aa = new ArrayList<Person>();
		aa.addAll(Db.getDb());

		for (Iterator<Person> it = aa.iterator(); it.hasNext();) {
			Person p = (Person) it.next();
			System.out.println(p.getName() + "         " + p.getThreadName()
					+ "	     " + p.getThreadId());
		}

		System.out.println();
		System.out.println();
		System.out.println();

		List<Student> bb = new ArrayList<Student>();
		bb.addAll(Db.getDb2());

		for (Iterator<Student> it = bb.iterator(); it.hasNext();) {
			Student p = (Student) it.next();
			System.out.println(p.getName() + "         " + p.getThreadName()
					+ "	     " + p.getThreadId());
		}
		System.out.println();

		System.out.println(Thread.currentThread().getName() + "           "
				+ Thread.currentThread().getId());

	}
}
