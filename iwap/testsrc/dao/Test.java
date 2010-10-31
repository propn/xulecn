package dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import db.DbCtx;
import db.JdbcTemplate;

public class Test extends Thread {

	public void run() {
		try {
			new Bo().b1();
			DbCtx.commit();
		} catch (Exception e) {
			DbCtx.rollback();
		} finally {
			DbCtx.close();
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

		for (Iterator<Person> it = aa.iterator(); it.hasNext();) {
			Person p = (Person) it.next();
			System.out.println(p.getName() + "         " + p.getThreadName()
					+ "	     " + p.getThreadId());
		}

		System.out.println();
		System.out.println();
		System.out.println();

		List<Student> bb = new ArrayList<Student>();

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
