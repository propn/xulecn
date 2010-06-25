package org.leixu.iap.user.domain;

import java.io.Serializable;

public class User implements Serializable {
	private String id;
	private String name;
	private Integer weight;

	public User(String name, Integer weight) {
		this.name = name;
		this.weight = weight;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	// Default constructor needed by Hibernate
	protected User() {
	}
}
