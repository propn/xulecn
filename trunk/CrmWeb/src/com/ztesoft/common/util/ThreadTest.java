package com.ztesoft.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Random;

public class ThreadTest implements Runnable {
	static int count=100;
	public void run() {
		// TODO Auto-generated method stub
		int flag=0;
		synchronized(this){ 	
			while(flag<90){
				count += 1;
				flag += 1;
				System.out.println(Thread.currentThread().getName()+" count="+count);
			}
		}
		
	}
	public static void main(String args[]) throws FileNotFoundException {
		  
		Random r = new Random();
		int int_r = r.nextInt(100);
		System.out.println("int_r="+int_r);
		ThreadTest th = new ThreadTest();
		for(int i=0;i<10;i++){
			Thread t = new Thread(th);
			t.start();
		}
		}
}
