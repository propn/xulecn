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
	System.out.println("������"+myString);
	return "����������������ô�ӡ";
}
public void interc(String myString){
	if(myString==null)myString="����";
	System.out.println("������"+myString);
}
public void interc(){
	System.out.println("������ÿ����");
}

}
