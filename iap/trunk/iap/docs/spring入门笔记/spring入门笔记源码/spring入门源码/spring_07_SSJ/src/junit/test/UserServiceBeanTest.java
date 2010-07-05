package junit.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import com.asm.entity.User;
import com.asm.service.UserService;

public class UserServiceBeanTest {
	private static UserService us;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		us = (UserService) new ClassPathXmlApplicationContext("beans.xml")
				.getBean("userServiceBean");
		new OpenEntityManagerInViewFilter();
	}

	@Test
	public void testSave() {
		us.save(new User("桲議議"));
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		User user = us.getUser(1);
		user.setName("桲議議");
		us.update(user);

	}

	@Test
	public void testGetUser() {
	}

	@Test
	public void testGetUsers() {
		for (User u : us.getUsers()) {
			System.out.println(u.getName());
		}
	}

}
