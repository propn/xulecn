package com.hwadee.demo;

import java.sql.SQLException;

public class MainModule {
	
	public static void main(String[] args) {		
		try{
			
			insert1();
			
			insert2();
			
			//方法1和2都无异常，提交事务,任何一个方法出现异常都将导致事务回滚。
			TransactionHelper.commit();
		}catch(SQLException e){			
			TransactionHelper.rollback();
			throw new RuntimeException(e.getMessage(),e);
		}catch(RuntimeException e){			 
			TransactionHelper.rollback();
			throw new RuntimeException(e.getMessage(),e);
		}
	}
	
	
	static void insert1() throws SQLException{		
		String sql = "insert into department values(1,'市场部')";
		
		TransactionHelper.executeNonQuery(sql);		 
	}
	
	static void insert2() throws SQLException{		
		String sql = "insert into department values(2,'研发部')";
		
		TransactionHelper.executeNonQuery(sql);	
		
		throw new RuntimeException("回滚");		
	}
}
