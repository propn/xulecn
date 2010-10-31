package aop.jdk;

public interface PersonService {
	public void save(String name);
	public void update(String name, Integer personid);
	public String getPersonName(Integer personid);
}
