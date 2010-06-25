/**
 * 
 */
package org.leixu.iap.core;

import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author lei
 *
 */
public class TestXmlProperty {
	@Test
	public void readFromXML() throws SQLException {
		ApplicationContext factory = new ClassPathXmlApplicationContext(new String[] {"test-applicationContext.xml"});
		DataSource foo = (DataSource)factory.getBean("dataSource");
		
		assertEquals("org.h2.jdbc.JdbcConnection", foo.getConnection().getClass().getName());
	}
}
