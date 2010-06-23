package com.hwadee.demo;

public class DemoRun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ThreadTest t1 = new ThreadTest();  
        t1.setKey(1);  
        t1.setValue("ONE");  
        new Thread(t1).start();  
          
        ThreadTest t2 = new ThreadTest();  
        t2.setKey(2);  
        t2.setValue("TWO");  
        new Thread(t2).start();  
	}

}
