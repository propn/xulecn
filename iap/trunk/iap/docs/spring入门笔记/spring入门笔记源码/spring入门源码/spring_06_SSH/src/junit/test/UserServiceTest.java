package junit.test;

import static org.junit.Assert.fail;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.asm.entity.User;
import com.asm.service.UserService;

public class UserServiceTest {
	private static UserService us;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ApplicationContext ctx=new ClassPathXmlApplicationContext("beans.xml");
		us=(UserService) ctx.getBean("userServiceBean");
	}

	@Test
	public void testGetUsers() {
		for(User u:us.getUsers()){
			System.out.println(u.getName());
		}
		
	}

	@Test
	public void testGetUser() {
		System.out.println(us.getUser(1).getName());
		System.out.println("关闭数据库服务");
		try {
			Thread.sleep(1000*40);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("关闭数据库准备从缓存中获取");
		System.out.println(us.getUser(1).getName());
	}

	@Test
	public void testSave() {
		us.save(new User("李某某"));
		System.out.println("------------");
	}

	@Test
	public void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	public void testUpdate() {
		fail("Not yet implemented");
	}

}
