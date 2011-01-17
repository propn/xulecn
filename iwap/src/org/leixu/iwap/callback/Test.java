package org.leixu.iwap.callback;

public class Test {
	   public static void main(String[] args) {   
	        TestTool testTool = new TestTool();   
	        testTool.testTime(new ICallBack(){   
	           
	        	//定义execute方法   
	            public void execute(){   
	                //这里可以加放一个或多个要测试运行时间的方法   
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
