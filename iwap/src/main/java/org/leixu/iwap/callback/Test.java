package org.leixu.iwap.callback;

public class Test {
	   public static void main(String[] args) {   
	        TestTool testTool = new TestTool();   
	        testTool.testTime(new ICallBack(){   
	           
	        	//����execute����   
	            public void execute(){   
	                //������Լӷ�һ������Ҫ��������ʱ��ķ���   
	                try {
						Thread.currentThread().sleep(10000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }
	            
	        });   
	    }
}
