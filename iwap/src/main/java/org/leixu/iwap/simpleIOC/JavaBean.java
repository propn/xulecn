package org.leixu.iwap.simpleIOC;

import java.io.Serializable;

public class JavaBean implements Serializable {
	private String userName;

	private String password;

	public String getPassword() {
		return password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
