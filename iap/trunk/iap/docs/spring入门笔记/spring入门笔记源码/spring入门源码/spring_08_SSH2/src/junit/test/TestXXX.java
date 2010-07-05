package junit.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.asm.entity.User;
import com.asm.service.UserService;

public class TestXXX {
	public static void main(String[] args) {
		UserService us = null;
		 new ClassPathXmlApplicationContext();
		AbstractApplicationContext ctx = new ClassPathXmlApplicationContext("beans.xml");
		ctx.close();
		ctx.refresh();
		us = (UserService) ctx.getBean("userServiceBean");
		for (User u : us.getUsers()) {
			System.out.println(u.getName());
		}
	}
}
