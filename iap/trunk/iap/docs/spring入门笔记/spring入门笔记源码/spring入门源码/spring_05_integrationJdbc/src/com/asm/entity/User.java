package com.asm.entity;

import org.springframework.jdbc.datasource.DataSourceTransactionManager;

public class User {
	private int id;
	private String name;

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
		new DataSourceTransactionManager();
	}

	public String toString() {
		return this.id + ": " + this.name;
	}
}
