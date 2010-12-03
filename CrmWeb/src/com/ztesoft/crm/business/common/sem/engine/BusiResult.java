package com.ztesoft.crm.business.common.sem.engine;

import java.util.LinkedList;
import java.util.List;

public class BusiResult {

	LinkedList results = new LinkedList();
	
	public void add(Object result) {
		results.addFirst(result);
	}
	public void remove(Object result) {
		results.remove(result);
	}
	
	public List list(){
		return results;
	}
	
	public Object first() {

		return results.getFirst();
	}
	public Object last() {
		return results.getLast();
	}
	
	public boolean empty() {

		return results.isEmpty();
	}
	/*public static void main(String[]args){
		
		String str="333333333";
		
		List list=new ArrayList();
		
		list.add("344");
		list.add("5544");
		
		BusiResult result=new BusiResult();
		
		result.add(str);
		
		result.add(list);
		
		Object object=result.last();
		
		System.out.println(object.getClass().getName());
	}*/
	


}
