package com.hwadee.demo.aop;

import java.sql.SQLException;

import com.hwadee.demo.TransactionHelper;

public class DAOImpl1 implements DAO {

	public void doWork() {
		
		System.out.println(this.getClass().getName() + "." + "doWork  Invoke");

		String sql = "insert into department values(1,'�г���')";

		try {
			TransactionHelper.executeNonQuery(sql);
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		
		// ����dao2
		DAO dao2 = (DAO) BeanFactory.getBean("dao2");
		dao2.doWork();
		
	}
}