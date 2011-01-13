package test;

import java.sql.Connection;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

public class JTAtest {

	 /**
	  * @param args
	  */
	 public static InitialContext getContext(){
	  InitialContext ic = null;
	  try {
	   ic = new InitialContext();
	  } catch (NamingException e) {
	   e.printStackTrace();
	  }
	  return ic;
	  
	 }
	 
	 public static UserTransaction getUserTransaction(){
	  UserTransaction ut = null;
	  try {
	   ut = (UserTransaction) getContext().lookup("UserTransaction");
	  } catch (NamingException e) {
	   e.printStackTrace();
	  }
	  return ut;
	  
	 }
	 
	 public static void main(String[] args) {
	  InitialContext ic = null;
	  UserTransaction ut = null;
	  DataSource ds = null;
	  
	  try { 
	   ic = getContext();
	   ut = getUserTransaction();
	   ds = (DataSource) ic.lookup("jdbc/crm1");

	   ut.begin();
	  
	   Connection c = ds.getConnection();
	   Statement s = c.createStatement();
	   s.execute("insert into i_test values (8888)");
	   
	   ut.commit();
	   System.out.println("事务处理成功");
	   
	  } catch (Exception e) {
	   try {
	    ut.rollback();
	   } catch (IllegalStateException e1) {
	    e1.printStackTrace();
	   } catch (SecurityException e1) {
	    e1.printStackTrace();
	   } catch (SystemException e1) {
	    e1.printStackTrace();
	   }
	   e.printStackTrace();
	  } 

	 }

	}

