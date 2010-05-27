package org.leixu.oa.person.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author zozoh(zozohtnt@gmail.com)
 *
 */
public class PersonOLAP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	// TODO Auto-generated method stub
		ApplicationContext ctx=new FileSystemXmlApplicationContext("target\\test-classes\\applicationContext.xml");
		
	}

}
