package com.hwadee.demo;

import java.sql.SQLException;

public class MainModule {
	
	public static void main(String[] args) {		
		try{
			
			insert1();
			
			insert2();
			
			//����1��2�����쳣���ύ����,�κ�һ�����������쳣������������ع���
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
		String sql = "insert into department values(1,'�г���')";
		
		TransactionHelper.executeNonQuery(sql);		 
	}
	
	static void insert2() throws SQLException{		
		String sql = "insert into department values(2,'�з���')";
		
		TransactionHelper.executeNonQuery(sql);	
		
		throw new RuntimeException("�ع�");		
	}
}
