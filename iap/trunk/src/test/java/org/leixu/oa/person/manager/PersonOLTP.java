package org.leixu.oa.person.manager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author zozoh(zozohtnt@gmail.com)
 *
 */
public class PersonOLTP {
	public static void main(String[] args){
		ApplicationContext ctx=new FileSystemXmlApplicationContext("target\\test-classes\\applicationContext.xml");
		
	}
	
}
