package com.asm.hibernate.domain;

import java.util.List;

public class Department {
	private int id;
	private String name;
	private List<Employee> emps;
	public String toString(){
		return "id="+this.id+"name="+this.name;
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmps() {
		return emps;
	}

	public void setEmps(List<Employee> emps) {
		this.emps = emps;
	}
}
