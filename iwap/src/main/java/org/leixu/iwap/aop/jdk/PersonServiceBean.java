package org.leixu.iwap.aop.jdk;

public class PersonServiceBean implements PersonService {
	private String user = null;//�ṩһ���û�����String���ʹ���

	public String getUser() {
		return user;
	}

	public PersonServiceBean() {
	}

	public PersonServiceBean(String user) {
		this.user = user;
	}

	public String getPersonName(Integer personid) {
		System.out.println("����getPersonName()����");
		return "xxx";
	}

	public void save(String name) {
		System.out.println("����save()����");
	}

	public void update(String name, Integer personid) {
		System.out.println("����update()����");
	}

}
