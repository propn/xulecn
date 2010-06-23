package com.hwadee.demo.aop;

import java.sql.SQLException;

import com.hwadee.demo.TransactionHelper;

public class DAOImpl2 implements DAO {

	public void doWork() {

		System.out.println(this.getClass().getName() + "." + "doWork  Invoke");

		String sql = "insert into department values(2,'研发部')";

		try {
			TransactionHelper.executeNonQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		throw new RuntimeException("回滚");
	}
}