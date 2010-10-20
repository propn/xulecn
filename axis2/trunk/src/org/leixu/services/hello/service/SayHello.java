/**
 * SayHello.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.leixu.services.hello.service;

import org.leixu.oa.person.model.Person;

public class SayHello {
	public String say(String name) {
		return "hello " + name;
	}

	public Person getPerson(String name, int age) {
		Person person = new Person();
		person.setAge(age);
		person.setName(name);
		return person;
	}
}
