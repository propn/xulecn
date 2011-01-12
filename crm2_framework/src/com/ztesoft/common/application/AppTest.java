package com.ztesoft.common.application;

import java.util.ArrayList;

public class AppTest {
private ArrayList bb=new ArrayList();

public static void main(String[]agrs){
	AppTest bo=new AppTest();
	System.out.print(bo.getClass().getName());
}
public AppTest(String a){
	//
}
public AppTest(){
	//
}
public void set(String item){
	bb.add(item);
}
public void get(){
	for(int i=0;i<bb.size();i++){
		System.out.println("\n"+(String)bb.get(i));
	}
}
public void print(){
	System.out.println("\ntest_method");
}
public void print(AppTest myString){
	System.out.println("AppTest");
}
public void bfprint(String myString){
	System.out.println("bfInterc");
}
public String print(String myString){
	System.out.println("正常的"+myString);
	return "方法后的拦截器调用打印";
}
public void interc(String myString){
	if(myString==null)myString="补充";
	System.out.println("拦截器"+myString);
}
public void interc(){
	System.out.println("拦截器每参数");
}

}
