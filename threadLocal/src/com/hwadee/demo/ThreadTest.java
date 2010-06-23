package com.hwadee.demo;

import java.util.Map;

public class ThreadTest implements Runnable{
	 private int key;  
	    private String value;  
	      
	    public String getValue() {  
	        return value;  
	    }  
	  
	    public void setValue(String value) {  
	        this.value = value;  
	    }  
	  
	    public int getKey() {  
	        return key;  
	    }  
	  
	    public void setKey(int key) {  
	        this.key = key;  
	    }  
	  
	    public void run() {  
	        // TODO Auto-generated method stub  
	        Map m = TransactionHelper.getTestMap();  
	        m.put(key, value);  
	        System.out.println("key>> "+key);  
	        System.out.println("value>> "+value);  
	        System.out.println("m size>> "+m.size());  
	        System.out.println("m value>> "+m.get(1));  
	        System.out.println("*****************************");  
	    }  
}
