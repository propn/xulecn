package org.leixu.iap.core.fastm.samples.sql;

public class User {
	private int departmentId;
	public int getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	private Name name; 
	public Name getName() {
		return name;
	}
	public void setName(Name name) {
		this.name = name;
	}
}
