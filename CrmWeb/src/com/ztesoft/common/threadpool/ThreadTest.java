package com.ztesoft.common.threadpool;



public class ThreadTest implements Runnable{

	private static ThreadPool multiThreadPool=null;
	public static void main(String args[]){
		multiThreadPool.init(30, 2000);
		for(int i=0;i<30;i++){
			ThreadTest bo=new ThreadTest();
			Thread _t=new Thread(bo);
			_t.start();
		}
//		ThreadTest bo=new ThreadTest();
////		multiThreadPool.setInterTime(10);
//		bo.run();
////		multiThreadPool.setInterTime(1000);
////		bo.run();
////		multiThreadPool.setInterTime(10);
////		bo.run();
	}

	public void run() {
		// TODO Auto-generated method stub
		//�̰߳�װ��1
		class _MultiRun implements com.ztesoft.common.threadpool.ServiceExe{
			public Object execute() {
				long _msg=System.currentTimeMillis();
				System.out.println(_msg+"��ʼ");
				for(int i=0;i<900000000;i++){
					i--;
					i++;
				}
				System.out.println(_msg+"����");
				return null;
			}
		}
		//�̰߳�װ��2
		class _MultiRunCallback  implements CallBack{
			public void dealReturnVal(Object obj) throws Throwable{
			}
		}
		_MultiRun bo=new _MultiRun();
		_MultiRunCallback callback=new _MultiRunCallback();
		for(int i=0;i<2;i++){
			if(!multiThreadPool.isFull())
			multiThreadPool.exeRunableService(bo, callback);
			else{
				System.out.println("�Ѿ�����");
				try {
					Thread.currentThread().sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	
	
}
